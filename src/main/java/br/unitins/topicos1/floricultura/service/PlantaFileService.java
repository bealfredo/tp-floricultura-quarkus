package br.unitins.topicos1.floricultura.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.apache.tika.Tika;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.jboss.logging.Logger;

import br.unitins.topicos1.floricultura.validation.GeneralValidationException;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PlantaFileService implements FileService {

  private static final Logger LOG = Logger.getLogger(PlantaFileService.class);
  private static final Tika tika = new Tika();
  
  // URL base da API Gateway para S3
  private static final String S3_API_BASE_URL = "https://zi7nb7ftfa.execute-api.us-east-1.amazonaws.com/dev/bucked/";
  
  // Chave de API
  private static final String API_KEY = "UrDN8FXLlP2ah8H6uk2Dk6wNAGqL9pCX5icOn14r";
  
  // Local fallback path (caso necessário)
  private final String PATH_USER = System.getProperty("user.home") +
    File.separator + "quarkus" + 
    File.separator + "floricultura" +
    File.separator + "images" +
    File.separator + "planta" + File.separator;

  private static final List<String> SUPPORTED_MIME_TYPES =
    Arrays.asList("image/jpeg", "image/jpg", "image/png", "image/gif");

  private static final int MAX_FILE_SIZE = 1024 * 1024 * 10; // 10mb
  
  private final HttpClient httpClient = HttpClient.newBuilder()
      .version(HttpClient.Version.HTTP_2)
      .build();

  @Override
  public String salvar(Long idSubpasta, byte[] arquivo) throws IOException {
    LOG.info("Verificando o tamanho da imagem");
    verificarTamanhoImagem(arquivo);

    LOG.info("Verificando se o arquivo está vazio");
    verificarSeVazio(arquivo);
    
    LOG.info("Verificando o tipo da imagem");
    String mimeType = verificarTipoImagem(arquivo);

    // Determinar extensão do arquivo
    String extensao;
    try {
      extensao = MimeTypes.getDefaultMimeTypes().forName(mimeType).getExtension();
    } catch (MimeTypeException e) {
      throw new GeneralValidationException("Imagem planta", "Erro ao determinar a extensão do arquivo");
    }
    
    // Criar nome único para o arquivo usando idSubpasta como prefixo
    String nomeArquivo = idSubpasta + "_" + UUID.randomUUID() + extensao;
    
    LOG.info("Enviando imagem para AWS S3 via API Gateway: " + nomeArquivo);
    
    try {
      // Criando a URL completa
      String apiUrl = S3_API_BASE_URL + nomeArquivo;
      LOG.info("URL da API: " + apiUrl);
      
      // Criar requisição HTTP
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(apiUrl))
          .header("Content-Type", mimeType)
          .header("x-api-key", API_KEY)
          .POST(BodyPublishers.ofByteArray(arquivo))
          .build();
      
      // Enviar a requisição
      HttpResponse<String> response = httpClient.send(request, 
          HttpResponse.BodyHandlers.ofString());
      
      // Verificar resposta
      if (response.statusCode() >= 200 && response.statusCode() < 300) {
        LOG.info("Imagem enviada com sucesso para S3: " + nomeArquivo);
        return nomeArquivo;
      } else {
        LOG.error("Erro ao enviar imagem para S3. Status code: " + response.statusCode());
        LOG.error("Resposta: " + response.body());
        throw new GeneralValidationException("Imagem planta", 
            "Erro ao salvar imagem no S3: " + response.statusCode());
      }
    } catch (IOException | InterruptedException e) {
      LOG.error("Exceção ao enviar imagem para S3", e);
      throw new GeneralValidationException("Imagem planta", 
          "Erro na comunicação com S3: " + e.getMessage());
    }
  }
  
  @Override
  public File obter(Long idSubpasta, String nomeArquivo) {
    LOG.info("Solicitando download do arquivo do S3: " + nomeArquivo);
    
    // Criar diretório temporário para o download, se não existir
    File diretorio = new File(PATH_USER + idSubpasta.toString());
    if (!diretorio.exists()) {
      diretorio.mkdirs();
    }
    
    // Arquivo temporário para salvar o download
    File arquivoLocal = new File(diretorio, nomeArquivo);
    
    try {
      // Construir a URL para o download
      String apiUrl = S3_API_BASE_URL + nomeArquivo;
      LOG.info("URL para download: " + apiUrl);
      
      // Criar requisição HTTP GET
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(apiUrl))
          .header("x-api-key", API_KEY)
          .GET()
          .build();
      
      // Enviar a requisição e receber a resposta como array de bytes
      HttpResponse<byte[]> response = httpClient.send(request, 
          HttpResponse.BodyHandlers.ofByteArray());
      
      // Verificar resposta
      if (response.statusCode() >= 200 && response.statusCode() < 300) {
        // Salvar conteúdo baixado no arquivo local
        Files.write(arquivoLocal.toPath(), response.body());
        LOG.info("Arquivo baixado com sucesso de S3: " + nomeArquivo);
        return arquivoLocal;
      } else {
        LOG.error("Erro ao baixar arquivo do S3. Status code: " + response.statusCode());
        throw new GeneralValidationException("Imagem planta", 
            "Erro ao baixar imagem do S3: " + response.statusCode());
      }
    } catch (IOException | InterruptedException e) {
      LOG.error("Exceção ao baixar arquivo do S3", e);
      throw new GeneralValidationException("Imagem planta", 
          "Erro ao baixar arquivo do S3: " + e.getMessage());
    }
  }

  @Override
  public Boolean apagar(Long idSubpasta, String nomeArquivo) {
    LOG.info("Apagando imagem do S3: " + nomeArquivo);
    
    try {
      // URL para deleção
      String apiUrl = S3_API_BASE_URL + nomeArquivo;
      
      // Criar requisição HTTP DELETE
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(apiUrl))
          .header("x-api-key", API_KEY)
          .DELETE()
          .build();
      
      // Enviar a requisição
      HttpResponse<String> response = httpClient.send(request, 
          HttpResponse.BodyHandlers.ofString());
      
      // Verificar resposta
      if (response.statusCode() >= 200 && response.statusCode() < 300) {
        LOG.info("Imagem apagada com sucesso do S3: " + nomeArquivo);
        return true;
      } else {
        LOG.error("Erro ao apagar imagem do S3. Status code: " + response.statusCode());
        LOG.error("Resposta: " + response.body());
        throw new GeneralValidationException("Imagem planta", 
            "Erro ao apagar imagem do S3: " + response.statusCode());
      }
    } catch (IOException | InterruptedException e) {
      LOG.error("Exceção ao apagar imagem do S3", e);
      throw new GeneralValidationException("Imagem planta", 
          "Erro na comunicação com S3: " + e.getMessage());
    }
  }

  private void verificarSeVazio(byte[] arquivo) {
    if (arquivo.length == 0) {
      throw new GeneralValidationException("Imagem planta", "Arquivo vazio");
    }
  }

  private void verificarTamanhoImagem(byte[] arquivo) {
    if (arquivo.length > MAX_FILE_SIZE)
      throw new GeneralValidationException("Imagem planta", "Arquivo maior que 10mb");
  }

  private String verificarTipoImagem(byte[] arquivo) {
    try (InputStream is = new ByteArrayInputStream(arquivo)) {
      String mimeType = tika.detect(is);
      if (!SUPPORTED_MIME_TYPES.contains(mimeType))
        throw new GeneralValidationException("Imagem planta", "Tipo de imagem não suportada.");
      return mimeType;
    } catch (IOException e) {
      throw new GeneralValidationException("Imagem planta", "Erro ao verificar o tipo da imagem");
    }
  }
}

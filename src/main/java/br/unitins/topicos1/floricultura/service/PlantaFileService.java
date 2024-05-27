package br.unitins.topicos1.floricultura.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
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

  private final String PATH_USER = System.getProperty("user.home") +
    File.separator + "quarkus" + 
    File.separator + "floricultura" +
    File.separator + "images" +
    File.separator + "planta" + File.separator;

  private static final List<String> SUPPORTED_MIME_TYPES =
    Arrays.asList("image/jpeg", "image/jpg", "image/png", "image/gif");

  private static final int MAX_FILE_SIZE = 1024 * 1024 * 10; // 10mb

  @Override
  public String salvar(Long idSubpasta, byte[] arquivo) throws IOException {
    LOG.info("Verificando o tamanho da imagem");
    verificarTamanhoImagem(arquivo);

    LOG.info("Verificando se o arquivo está vazio");
    verificarSeVazio(arquivo);
    
    LOG.info("Verificando o tipo da imagem");
    String mimeType = verificarTipoImagem(arquivo);

    LOG.info("Criando o diretório caso não exista");
    Path diretorio = Paths.get(PATH_USER, idSubpasta.toString());
    Files.createDirectories(diretorio);

    LOG.info("Criando o nome do arquivo randomico para a imagem");
    String extensao;
    try {
      extensao = MimeTypes.getDefaultMimeTypes().forName(mimeType).getExtension();
    } catch (MimeTypeException e) {
      throw new GeneralValidationException("Imagem planta", "Erro ao determinar a extensão do arquivo");
    }

    String novoNomeArquivo;
    Path filePath;
    do {
      novoNomeArquivo = UUID.randomUUID() + extensao;
      filePath = diretorio.resolve(novoNomeArquivo);
    } while (filePath.toFile().exists());
    
    LOG.info("Caminho completo da imagem definido: " + filePath);

    LOG.info("Salvando a imagem.");
    try (FileOutputStream fos = new FileOutputStream(filePath.toFile())) {
      fos.write(arquivo);
    }

    LOG.info("Imagem salva com sucesso.");

    return filePath.toFile().getName();
  }
  
  @Override
  public File obter(Long idSubpasta, String nomeArquivo) {
    File file = new File(PATH_USER + idSubpasta.toString() + File.separator + nomeArquivo);
    return file;
  }

  @Override
  public Boolean apagar(Long idSubpasta, String nomeArquivo) {
    try {
      LOG.info("Apagando a imagem: " + nomeArquivo);
      Path filePath = Paths.get(PATH_USER, idSubpasta.toString(), nomeArquivo);
      Files.deleteIfExists(filePath);
      LOG.info("Imagem apagada com sucesso.");
      return true;
    } catch (IOException e) {
      LOG.error("Erro ao apagar a imagem: " + nomeArquivo);
      throw new GeneralValidationException("Imagem planta", "Erro ao apagar a imagem");
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

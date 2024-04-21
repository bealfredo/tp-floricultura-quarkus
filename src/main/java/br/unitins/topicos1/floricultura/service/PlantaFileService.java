package br.unitins.topicos1.floricultura.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.jboss.logging.Logger;

import br.unitins.topicos1.floricultura.resource.AuthResource;
import br.unitins.topicos1.floricultura.validation.GeneralValidationException;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PlantaFileService implements FileService {

  private static final Logger LOG = Logger.getLogger(AuthResource.class);

  private final String PATH_USER = System.getProperty("user.home") +
    File.separator + "quarkus" + 
    File.separator + "floricultura" +
    File.separator + "images" +
    File.separator + "planta" + File.separator;

  private static final List<String> SUPPORTED_MIME_TYPES =
    Arrays.asList("image/jpeg", "image/jpg", "image/png", "image/gif");

  private static final int MAX_FILE_SIZE = 1024 * 1024 * 10; // 10mb

  @Override
  public String salvar(String nomeArquivo, byte[] arquivo) throws IOException  {
    LOG.info("Verificando o tamanho da imagem");
    verificarTamanhoImagem(arquivo);

    LOG.info("Verificando se o arquivo está vazio");
    verificarSeVazio(arquivo);
    
    LOG.info("Verificando o tipo da imagem");
    verificarTipoImagem(nomeArquivo);

    LOG.info("Criando o diretório caso não exista");
    Path diretorio = Paths.get(PATH_USER);
    Files.createDirectories(diretorio);

    LOG.info("Criando o nome do arquivo randomico para a imagem");
    Path filePath;
    do {
      String mimeType = Files.probeContentType(Paths.get(nomeArquivo));
      String extensao = mimeType.substring(mimeType.lastIndexOf('/') + 1);
      String novoNomeArquivo = UUID.randomUUID() + "." + extensao;

      novoNomeArquivo = UUID.randomUUID() + "." + extensao;

      filePath = diretorio.resolve(novoNomeArquivo);

    } while (filePath.toFile().exists());
    
    LOG.info("Caminho completo da imagem definidio: " + filePath);

    LOG.info("Salvando a imagem.");
    try( FileOutputStream fos = new FileOutputStream(filePath.toFile())) {
      fos.write(arquivo);
    } 

    LOG.info("Imagem salva com sucesso.");

    return filePath.toFile().getName();
  }
  
  @Override
  public File obter(String nomeArquivo) {
    File file = new File(PATH_USER+nomeArquivo);
    return file;
  }

  @Override
  public Boolean apagar(String nomeArquivo) {
    try {
        LOG.info("Apagando a imagem: " + nomeArquivo);
        Path filePath = Paths.get(PATH_USER, nomeArquivo);
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

  private void verificarTipoImagem(String nomeArquivo) {
    try {
      String mimeType = Files.probeContentType(Paths.get(nomeArquivo));
      if(!SUPPORTED_MIME_TYPES.contains(mimeType))
        throw new GeneralValidationException("Imagem planta", "Tipo de imagem não suportada.");
    } catch (Exception e) {
      throw new GeneralValidationException("Imagem planta", "Erro ao verificar o tipo da imagem");
    }
  }
  
}

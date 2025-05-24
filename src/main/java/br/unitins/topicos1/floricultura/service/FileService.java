package br.unitins.topicos1.floricultura.service;

import java.io.File;
import java.io.IOException;

public interface FileService {
  
  String salvar(Long idSubpasta, byte[] arquivo) throws IOException;

  File obter(Long idSubpasta, String nomeArquivo);

  Boolean apagar(Long idSubpasta, String nomeArquivo);

}

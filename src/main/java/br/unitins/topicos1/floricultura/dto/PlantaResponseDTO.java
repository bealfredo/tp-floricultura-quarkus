package br.unitins.topicos1.floricultura.dto;

import java.util.List;

import br.unitins.topicos1.floricultura.model.NivelDificuldade;
import br.unitins.topicos1.floricultura.model.NivelToxidade;
import br.unitins.topicos1.floricultura.model.Planta;
import br.unitins.topicos1.floricultura.model.PortePlanta;
import br.unitins.topicos1.floricultura.model.StatusPlanta;

public record PlantaResponseDTO(

    Long id,
    String nomeComum,
    String nomeCientifico,
    String descricao,
    String codigo,
    String imagemPrincipal,
    String[] imagens,
    Double precoVenda,
    Double precoCusto,
    Double desconto,
    Integer quantidadeDisponivel,
    Integer quantidadeVendido,
    String origem,
    String tempoCrescimento,
    StatusPlanta statusPlanta,
    NivelDificuldade nivelDificuldade,
    NivelToxidade nivelToxidade,
    PortePlanta portePlanta,
    List<TagResponseDTO> tags,
    FornecedorResponseDTO fornecedor,
    CategoriaPlantaResponseDTO categoriaPlanta

  ) {
  public static PlantaResponseDTO valueOf(Planta planta) {
    return new PlantaResponseDTO(
      planta.getId(),
      planta.getNomeComum(),
      planta.getNomeCientifico(),
      planta.getDescricao(),
      planta.getCodigo(),
      planta.getImagemPrincipal(),
      planta.getImagens(),
      planta.getPrecoVenda(),
      planta.getPrecoCusto(),
      planta.getDesconto(),
      planta.getQuantidadeDisponivel(),
      planta.getQuantidadeVendido(),
      planta.getOrigem(),
      planta.getTempoCrescimento(),
      planta.getStatusPlanta(),
      planta.getNivelDificuldade(),
      planta.getNivelToxidade(),
      planta.getPortePlanta(),
      TagResponseDTO.valueOf(planta.getTags()),
      FornecedorResponseDTO.valueOf(planta.getFornecedor()),
      CategoriaPlantaResponseDTO.valueOf(planta.getCategoriaBiologica())
    );
  }

}

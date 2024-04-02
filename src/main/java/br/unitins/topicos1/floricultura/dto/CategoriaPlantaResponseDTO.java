package br.unitins.topicos1.floricultura.dto;

import br.unitins.topicos1.floricultura.model.CategoriaPlanta;
import br.unitins.topicos1.floricultura.model.TipoCategoria;

public record CategoriaPlantaResponseDTO(
    Long id,
    String nome,
    String descricao,
    Integer prioridade,
    Boolean ativa,
    TipoCategoria tipoCategoria) {

  public static CategoriaPlantaResponseDTO valueOf(CategoriaPlanta categoriaPlanta) {
    return new CategoriaPlantaResponseDTO(
        categoriaPlanta.getId(),
        categoriaPlanta.getNome(),
        categoriaPlanta.getDescricao(),
        categoriaPlanta.getPrioridade(),
        categoriaPlanta.getAtiva(),
        categoriaPlanta.getTipoCategoria());
  }

}

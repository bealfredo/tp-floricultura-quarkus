package br.unitins.topicos1.floricultura.dto;

import java.util.List;
import br.unitins.topicos1.floricultura.model.CategoriaPlanta;
import br.unitins.topicos1.floricultura.model.Tag;

public record TagResponseDTO(
    Long id,
    String nome,
    String descricao,
    Integer prioridade,
    Boolean ativa,
    CategoriaPlanta categoriaPlanta) {

  public static TagResponseDTO valueOf(Tag tag) {
    return new TagResponseDTO(
        tag.getId(),
        tag.getNome(),
        tag.getDescricao(),
        tag.getPrioridade(),
        tag.getAtiva(),
        tag.getCategoriaPlanta());
  }

  public static List<TagResponseDTO> valueOf(List<Tag> tag) {
    return tag.stream()
    .map((Tag t) -> TagResponseDTO.valueOf(t))
    .toList();
  }

}

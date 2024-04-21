package br.unitins.topicos1.floricultura.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.unitins.topicos1.floricultura.dto.NivelToxicidadeResponseDTO;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum NivelToxidade {
  
  NAOTOXICA(1, "Não Tóxica", "Esta planta é segura e não apresenta risco à saúde humana ou animal."),
  LEVEMENTETOXICA(2, "Levemente Tóxica", "Esta planta pode causar reações leves se ingerida ou ao contato com a pele. Recomenda-se cuidado, especialmente com crianças e animais."),
  MODERADAMENTETOXICA(3, "Moderadamente Tóxica", "Esta planta pode causar reações moderadas a graves se ingerida e pode irritar a pele e os olhos. É importante manter essa planta fora do alcance de crianças e animais."),
  ALTAMENTETOXICA(4, "Altamente Tóxica", "Esta planta é altamente tóxica e pode causar sintomas graves se ingerida, como náuseas, vômitos, diarreia e até mesmo problemas mais sérios como alterações cardíacas. Evite o contato direto e mantenha-a longe de crianças e animais."),
  EXTREMAMENTETOXICA(5, "Extremamente Tóxica", "Esta planta é extremamente perigosa e pode ser fatal se ingerida. Mesmo o contato superficial pode causar sintomas graves. Deve ser manuseada com extrema cautela e mantida em locais inacessíveis a crianças e animais.");

  private final Integer id;
  private final String label;
  private final String description;

  NivelToxidade(Integer id, String label, String description) {
    this.id = id;
    this.label = label;
    this.description = description;
  }

  public Integer getId() {
    return id;
  }

  public String getLabel() {
    return label;
  }

  public String getDescription() {
    return description;
  }

  public static NivelToxidade valueOf(Integer id) {
    if (id == null)
      return null;
    for (NivelToxidade item : NivelToxidade.values()) {
      if (item.getId().equals(id))
        return item;
    }
    return null;
  }

  public static List<NivelToxicidadeResponseDTO> listAll() {
        return Arrays.stream(NivelToxidade.values())
            .map(nivelToxicidade -> new NivelToxicidadeResponseDTO(nivelToxicidade.getId(), nivelToxicidade.getLabel(), nivelToxicidade.getDescription()))
            .collect(Collectors.toList());
    }
}

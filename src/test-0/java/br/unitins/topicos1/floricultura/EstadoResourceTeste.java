package br.unitins.topicos1.floricultura;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.Test;

import br.unitins.topicos1.floricultura.dto.EstadoDTO;
import br.unitins.topicos1.floricultura.dto.EstadoResponseDTO;
import br.unitins.topicos1.floricultura.service.EstadoService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import jakarta.inject.Inject;

@QuarkusTest
public class EstadoResourceTeste {

  @Inject
  EstadoService estadoService;
  // quando usa registra no banco
  // cuidado

  private EstadoDTO getOneDTO() {
    EstadoDTO dto = new EstadoDTO(
      "Estado para Teste",
      "ET"
    );

    return dto;
  }

  private EstadoResponseDTO getOneResponseDTO() {
    return estadoService.insert(getOneDTO());
  }

  @Test
  public void testInsert() {
    EstadoDTO dto = getOneDTO();

    ValidatableResponse validatableResponse = given()
      .contentType(ContentType.JSON)
      .body(dto)
      .when().post("/estados")
      .then()
      .statusCode(201)
      .body(
        "id", notNullValue(),
        "nome", is("Estado para Teste"),
        "sigla", is("ET")); 

      Integer idCriado = validatableResponse.extract().jsonPath().get("id");
      estadoService.delete(Long.valueOf(idCriado));
  }

  @Test
  public void testUpdate() {
    EstadoResponseDTO estadoTeste = getOneResponseDTO();
    Long id = estadoTeste.id();

    EstadoDTO dtoUpdate = new EstadoDTO(
      "Bostom",
      "BT"
    );

    given()
      .contentType(ContentType.JSON)
      .body(dtoUpdate)
      .when().put("/estados/" + id)
      .then()
      .statusCode(204); 

    EstadoResponseDTO dtoAdicionado = estadoService.findById(id);
    assertThat(dtoAdicionado.nome(), is("Bostom"));
    assertThat(dtoAdicionado.sigla(), is("BT"));

    estadoService.delete(id);
  }

  @Test
  public void testUpdateInexistente() {
    EstadoDTO dtoUpdate = new EstadoDTO(
      "Bostom",
      "BT"
    );

    // atualizando estado que não existe
    given()
      .contentType(ContentType.JSON)
      .body(dtoUpdate)
      .when().put("/estados/1245654321")
      .then()
      .statusCode(404); 
  }


  @Test
  public void testDelete() {
    EstadoResponseDTO estadoTeste = getOneResponseDTO();
    Long id = estadoTeste.id();

    given()
      .contentType(ContentType.JSON)
      .when().delete("/estados/" + id)
      .then()
      .statusCode(204);
  }

  @Test
  public void testDeleteInexistent() {
    // estado que não existe
    given()
      .contentType(ContentType.JSON)
      .when().delete("/estados/1234765432")
      .then()
      .statusCode(404);
  }

  @Test
  public void testFindAll() {
    given() 
      .when().get("/estados")
      .then()
      .statusCode(200);
  }

  @Test
  public void testFindById() {
    given()
      .when().get("/estados/1")
      .then()
      .statusCode(200);

    given()
      .when().get("/estados/100")
      .then()
      .statusCode(404);
  }

  @Test
  public void testFindByNome() {
    given()
      .when().get("/estados/search/nome/rio")
      .then()
      .statusCode(200)
      .body("size()", equalTo(1));

    given()
      .when().get("/estados/search/nome/rj")
      .then()
      .statusCode(200)
      .body("size()", equalTo(0));
  }

  @Test
  public void testFindBySigla() {
    given()
      .when().get("estados/search/sigla/rj")
      .then()
      .statusCode(200)
      .body("size()", equalTo(1));

    given()
      .when().get("estados/search/sigla/rio")
      .then()
      .statusCode(200)
      .body("size()", equalTo(0));
  }

  @Test
  public void testFindByNomeESigla() {
    given()
      .when().get("/estados/search/nomeESigla/rio")
      .then()
      .statusCode(200)
      .body("size()", equalTo(1));

    given()
      .when().get("/estados/search/nomeESigla/rj")
      .then()
      .statusCode(200)
      .body("size()", equalTo(1));;

    given()
      .when().get("/estados/search/nomeESigla/sasidhaiosduffasdosun")
      .then()
      .statusCode(200)
      .body("size()", equalTo(0));
  }

}
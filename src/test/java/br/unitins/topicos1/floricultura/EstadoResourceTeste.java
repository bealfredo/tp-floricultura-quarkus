package br.unitins.topicos1.floricultura;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import br.unitins.topicos1.floricultura.dto.EstadoDTO;
import br.unitins.topicos1.floricultura.dto.EstadoResponseDTO;
import br.unitins.topicos1.floricultura.service.EstadoService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

@QuarkusTest
public class EstadoResourceTeste {

  @Inject
  EstadoService estadoService;
  // quando usa registra no banco
  // cuidado

  @Test
  public void testeInsert() {
    EstadoDTO dto = new EstadoDTO(
      "Minas Gerais",
      "mg"
    );

    given()
      .contentType(ContentType.JSON)
      .body(dto)
      .when().post("/estados")
      .then()
      .statusCode(201)
      .body(
        "id", notNullValue(),
        "nome", is("Minas Gerais"),
        "sigla", is("MG")); 
  }

  @Test
  public void testeUpdate() {
    EstadoDTO dto = new EstadoDTO(
      "Bahiiiia",
      "bk"
    );

    EstadoResponseDTO estadoTeste = estadoService.insert(dto);
    Long id = estadoTeste.id();

    EstadoDTO dtoUpdate = new EstadoDTO(
      "Bahia",
      "bh"
    );

    given()
      .contentType(ContentType.JSON)
      .body(dtoUpdate)
      .when().put("/estados/" + id)
      .then()
      .statusCode(204); 

    EstadoResponseDTO dtoAdicionado = estadoService.findById(id);
    assertThat(dtoAdicionado.nome(), is("Bahia"));
    assertThat(dtoAdicionado.sigla(), is("BH"));

    // atualizando estado que não existe
    given()
      .contentType(ContentType.JSON)
      .body(dtoUpdate)
      .when().put("/estados/1245654321")
      .then()
      .statusCode(404); 
  }

  @Test
  public void testeDelete() {
    EstadoDTO dto = new EstadoDTO(
      "Bahiiiia",
      "bk"
    );

    EstadoResponseDTO estadoTeste = estadoService.insert(dto);
    Long id = estadoTeste.id();

    given()
      .contentType(ContentType.JSON)
      .when().delete("/estados/" + id)
      .then()
      .statusCode(204);

    // estado que não existe
    given()
      .contentType(ContentType.JSON)
      .when().delete("/estados/1234765432")
      .then()
      .statusCode(404);
  }

  @Test
  public void testeFindAll() {
    given() 
      .when().get("/estados")
      .then()
      .statusCode(200);
  }

  @Test
  public void testeFindById() {
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
  public void testeFindByNome() {
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
  public void testeFindBySigla() {
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
  public void testeFindByNomeESigla() {
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
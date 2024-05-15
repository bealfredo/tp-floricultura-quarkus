package br.unitins.topicos1.floricultura;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

import org.junit.jupiter.api.Test;

import br.unitins.topicos1.floricultura.dto.FornecedorDTO;
import br.unitins.topicos1.floricultura.dto.FornecedorResponseDTO;
import br.unitins.topicos1.floricultura.service.FornecedorService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import jakarta.inject.Inject;

@QuarkusTest
public class FornecedorResourceTeste {

  @Inject
  FornecedorService fornecedorService;

  private FornecedorDTO getOneDTO() {
    FornecedorDTO dto = new FornecedorDTO(
      "Flores da Ana",
      "floresanas@gmail.com",
      "63984334433",
      "00000000000000"
    );

    return dto;
  }

  private FornecedorResponseDTO getOneResponseDTO() {
    return fornecedorService.insert(getOneDTO());
  }

  @Test
  public void testInsert() {
    FornecedorDTO dto = getOneDTO();

    ValidatableResponse validatableResponse = given()
      .contentType(ContentType.JSON)
      .body(dto)
      .when().post("/fornecedores")
      .then()
      .statusCode(201)
      .body(
        "id", notNullValue(),
        "nome", is("Flores da Ana"),
        "email", is("floresanas@gmail.com"),
        "telefone", is("63984334433"),
        "cnpj", is("00000000000000")); 

    Integer idCriado = validatableResponse.extract().jsonPath().get("id");
    fornecedorService.delete(Long.valueOf(idCriado));
  }

  @Test
  public void testUpdate() {
    FornecedorResponseDTO fornecedorTeste = getOneResponseDTO();
    Long id = fornecedorTeste.id();

    FornecedorDTO dtoUpdate = new FornecedorDTO(
      "Flores verdinhas",
      "floresverdinhas@gmail.com",
      "999999999",
      "11111111111111"
    );

    given()
      .contentType(ContentType.JSON)
      .body(dtoUpdate)
      .when().put("/fornecedores/" + id)
      .then()
      .statusCode(204); 

    FornecedorResponseDTO dtoAdicionado = fornecedorService.findById(id);
    assertThat(dtoAdicionado.nome(), is("Flores verdinhas"));
    assertThat(dtoAdicionado.email(), is("floresverdinhas@gmail.com"));
    assertThat(dtoAdicionado.telefone(), is("999999999"));
    assertThat(dtoAdicionado.cnpj(), is("11111111111111"));

    fornecedorService.delete(id);
  }

  @Test
  public void testUpdateFornecedorInexistente() {
    FornecedorDTO dtoUpdate = new FornecedorDTO(
      "Flores verdinhas",
      "floresverdinhas@gmail.com",
      "999999999",
      "6664567890123"
    );

    // atualizando fornecedor que não existe
    given()
      .contentType(ContentType.JSON)
      .body(dtoUpdate)
      .when().put("/fornecedores/-1245654321")
      .then()
      .statusCode(404); 
  }

  @Test
  public void testDelete() {
    FornecedorResponseDTO fornecedorTeste = getOneResponseDTO();
    Long id = fornecedorTeste.id();

    given()
      .contentType(ContentType.JSON)
      .when().delete("/fornecedores/" + id)
      .then()
      .statusCode(204);

    // estado que não existe
    given()
      .contentType(ContentType.JSON)
      .when().delete("/fornecedores/1234765432")
      .then()
      .statusCode(404);
  }

  @Test
  public void testFindAll() {
    given() 
      .when().get("/fornecedores")
      .then()
      .statusCode(200);
  }

  @Test
  public void testFindById() {
    given()
      .when().get("/fornecedores/1")
      .then()
      .statusCode(200);

    // fornecedor que não existe
    given()
      .when().get("/fornecedores/100")
      .then()
      .statusCode(404);
  }

  @Test
  public void testFindByNome() {
    given()
      .when().get("/fornecedores/search/nome/natureza")
      .then()
      .statusCode(200)
      .body("size()", greaterThan(0));

    given()
      .when().get("/fornecedores/search/nome/rjsdfgnvsa")
      .then()
      .statusCode(200)
      .body("size()", equalTo(0));
  }

  @Test
  public void testFindByCnpj() {
    given()
      .when().get("/fornecedores/search/cnpj/111")
      .then()
      .statusCode(200)
      .body("size()", greaterThan(0));

    given()
      .when().get("/fornecedores/search/cnpj/981234567654")
      .then()
      .statusCode(200)
      .body("size()", equalTo(0));
  }

}
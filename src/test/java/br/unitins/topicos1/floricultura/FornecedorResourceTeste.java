package br.unitins.topicos1.floricultura;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import br.unitins.topicos1.floricultura.dto.FornecedorDTO;
import br.unitins.topicos1.floricultura.dto.FornecedorResponseDTO;
import br.unitins.topicos1.floricultura.service.FornecedorService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class FornecedorResourceTeste {

  @Inject
  FornecedorService fornecedorService;

  @Test
  public void testInsert() {
    FornecedorDTO dto = new FornecedorDTO(
      "Flores da Ana",
      "floresanas@gmail.com",
      "63984334433",
      "12345678901234"
    );

    given()
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
        "cnpj", is("12345678901234")); 
  }

  @Test
  public void testUpdate() {
    FornecedorDTO dto = new FornecedorDTO(
      "Flores da Anassssssssssssss",
      "aaaaaaaaaa@gmail.com",
      "00000000000",
      "00000000000000"
    );

    FornecedorResponseDTO fornecedorTeste = fornecedorService.insert(dto);
    Long id = fornecedorTeste.id();

    FornecedorDTO dtoUpdate = new FornecedorDTO(
      "Flores da Ana",
      "floresAnas@gmail.com",
      "63984334433",
      "6664567890123"
    );

    given()
      .contentType(ContentType.JSON)
      .body(dtoUpdate)
      .when().put("/fornecedores/" + id)
      .then()
      .statusCode(204); 

    FornecedorResponseDTO dtoAdicionado = fornecedorService.findById(id);
    assertThat(dtoAdicionado.nome(), is("Flores da Ana"));
    assertThat(dtoAdicionado.email(), is("floresAnas@gmail.com"));
    assertThat(dtoAdicionado.telefone(), is("63984334433"));
    assertThat(dtoAdicionado.cnpj(), is("6664567890123"));

    // atualizando fornecedor que não existe
    given()
      .contentType(ContentType.JSON)
      .body(dtoUpdate)
      .when().put("/fornecedores/1245654321")
      .then()
      .statusCode(404); 
  }

  @Test
  public void testDelete() {
    FornecedorDTO dto = new FornecedorDTO(
      "To delete",
      "todelete@gmail.com",
      "00000000000",
      "00000000000000"
    );

    FornecedorResponseDTO estadoTeste = fornecedorService.insert(dto);
    Long id = estadoTeste.id();

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
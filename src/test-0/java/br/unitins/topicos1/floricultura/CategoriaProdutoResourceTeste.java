package br.unitins.topicos1.floricultura;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

import org.junit.jupiter.api.Test;

import br.unitins.topicos1.floricultura.dto.CategoriaProdutoDTO;
import br.unitins.topicos1.floricultura.dto.CategoriaProdutoResponseDTO;
import br.unitins.topicos1.floricultura.service.CategoriaProdutoService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import jakarta.inject.Inject;

@QuarkusTest
public class CategoriaProdutoResourceTeste {

  @Inject
  CategoriaProdutoService categoriaProdutoService;

  private CategoriaProdutoDTO getOneDTO() {
    return new CategoriaProdutoDTO(
      "Decorações",
      "Decorações para o seu jardim"
    );
  }

  private CategoriaProdutoResponseDTO getOneResponseDTO() {
    return categoriaProdutoService.insert(getOneDTO());
  }

  @Test
  public void testInsert() {
    CategoriaProdutoDTO dto = getOneDTO();

    ValidatableResponse validatableResponse = given()
      .contentType(ContentType.JSON)
      .body(dto)
      .when().post("/categoriasproduto")
      .then()
      .statusCode(201)
      .body(
        "id", notNullValue(),
        "nome", is("Decorações"),
        "descricao", is("Decorações para o seu jardim")); 

    Integer idCriado = validatableResponse.extract().jsonPath().get("id");
    try {
      categoriaProdutoService.delete(Long.valueOf(idCriado));
    } catch (Exception e) {
    }
  
  }

  @Test
  public void testUpdate() {
    CategoriaProdutoResponseDTO categoriaProdutoTeste = getOneResponseDTO();
    Long id = categoriaProdutoTeste.id();

    CategoriaProdutoDTO dtoUpdate = new CategoriaProdutoDTO(
      "Natalinos",
      "Itens natalinos"
    );

    given()
      .contentType(ContentType.JSON)
      .body(dtoUpdate)
      .when().put("/categoriasproduto/" + id)
      .then()
      .statusCode(204); 

    CategoriaProdutoResponseDTO dtoAdicionado = categoriaProdutoService.findById(id);
    assertThat(dtoAdicionado.nome(), is("Natalinos"));
    assertThat(dtoAdicionado.descricao(), is("Itens natalinos"));

    // atualizando fornecedor que não existe
    given()
      .contentType(ContentType.JSON)
      .body(dtoUpdate)
      .when().put("/categoriasproduto/1245654321")
      .then()
      .statusCode(404); 

    try {
      categoriaProdutoService.delete(id);
    } catch (Exception e) {
    }
  }

  @Test
  public void testDelete() {
    CategoriaProdutoResponseDTO estadoTeste = getOneResponseDTO();
    Long id = estadoTeste.id();

    given()
      .contentType(ContentType.JSON)
      .when().delete("/categoriasproduto/" + id)
      .then()
      .statusCode(204);
  }

  @Test
  public void testDeleteInexistente() {
    // estado que não existe
    given()
      .contentType(ContentType.JSON)
      .when().delete("/categoriasproduto/1234765432")
      .then()
      .statusCode(404);
  }

  @Test
  public void testDeleteComTiposProdutoAssociados() {
    given()
      .contentType(ContentType.JSON)
      .when().delete("/categoriasproduto/1")
      .then()
      .statusCode(409);
  }

  @Test
  public void testFindAll() {
    given() 
      .when().get("/categoriasproduto")
      .then()
      .statusCode(200);
  }

  @Test
  public void testFindById() {
    given()
      .when().get("/categoriasproduto/1")
      .then()
      .statusCode(200);

    // categoriasproduto que não existe
    given()
      .when().get("/categoriasproduto/100")
      .then()
      .statusCode(404);
  }

  @Test
  public void testFindByNome() {
    given()
      .when().get("/categoriasproduto/search/nome/árvore")
      .then()
      .statusCode(200)
      .body("size()", greaterThan(0));

    given()
      .when().get("/categoriasproduto/search/nome/asdfggfd")
      .then()
      .statusCode(200)
      .body("size()", equalTo(0));
  }

}
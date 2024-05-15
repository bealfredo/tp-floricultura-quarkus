package br.unitins.topicos1.floricultura;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

import org.junit.jupiter.api.Test;

import br.unitins.topicos1.floricultura.dto.TipoProdutoDTO;
import br.unitins.topicos1.floricultura.dto.TipoProdutoResponseDTO;
import br.unitins.topicos1.floricultura.service.TipoProdutoService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import jakarta.inject.Inject;

@QuarkusTest
public class TipoProdutoResourceTeste {

  @Inject
  TipoProdutoService tipoProdutoService;

  private TipoProdutoDTO getOneDTO() {
    TipoProdutoDTO dto = new TipoProdutoDTO(
      "Árvores noturnas",
      "Árvores que aparecem de noite?",
      Long.valueOf(1)
    );

    return dto;
  }

  private TipoProdutoResponseDTO getOneResponseDTO() {
    try {
      return tipoProdutoService.insert(getOneDTO());
    } catch (Exception e) {
      return null;
    }
  }

  @Test
  public void testInsert() {
    TipoProdutoDTO dto = new TipoProdutoDTO(
      "Árvores noturnas",
      "Árvores que aparecem de noite?",
      Long.valueOf(1)
    );

    ValidatableResponse validatableResponse = given()
      .contentType(ContentType.JSON)
      .body(dto)
      .when().post("/tiposproduto")
      .then()
      .statusCode(201)
      .body(
        "id", notNullValue(),
        "nome", is("Árvores noturnas"),
        "descricao", is("Árvores que aparecem de noite?"),
        "categoriaProduto.id", is(1));

    Integer idCriado = validatableResponse.extract().jsonPath().get("id");
    tipoProdutoService.delete(Long.valueOf(idCriado));
  }

  @Test
  public void testUpdate() {
    TipoProdutoResponseDTO tipoProdutoTeste = getOneResponseDTO();

    Long id = tipoProdutoTeste.id();
  
    TipoProdutoDTO dtoUpdate = new TipoProdutoDTO(
      "Arvores Esquisitas Demais",
      "Realmente sinistas!!!!",
      Long.valueOf(1)
    );

    given()
      .contentType(ContentType.JSON)
      .body(dtoUpdate)
      .when().put("/tiposproduto/" + id)
      .then()
      .statusCode(204); 

    TipoProdutoResponseDTO dtoAdicionado = tipoProdutoService.findById(id);
    assertThat(dtoAdicionado.nome(), is("Arvores Esquisitas Demais"));
    assertThat(dtoAdicionado.descricao(), is("Realmente sinistas!!!!"));
    assertThat(dtoAdicionado.categoriaProduto(), notNullValue());

    // atualizando fornecedor que não existe
    given()
      .contentType(ContentType.JSON)
      .body(dtoUpdate)
      .when().put("/tiposproduto/1245654321")
      .then()
      .statusCode(404);

    tipoProdutoService.delete(id);
  }

  @Test
  public void testDelete() {
    TipoProdutoResponseDTO tipoProdutoTeste = getOneResponseDTO();

    Long id = tipoProdutoTeste.id();

    given()
      .contentType(ContentType.JSON)
      .when().delete("/tiposproduto/" + id)
      .then()
      .statusCode(204);

    // estado que não existe
    given()
      .contentType(ContentType.JSON)
      .when().delete("/tiposproduto/1234765432")
      .then()
      .statusCode(404);

  }

  @Test
  public void testFindAll() {
    given() 
      .when().get("/tiposproduto")
      .then()
      .statusCode(200);
  }

  @Test
  public void testFindById() {
    given()
      .when().get("/tiposproduto/1")
      .then()
      .statusCode(200);

    // categoriasproduto que não existe
    given()
      .when().get("/tiposproduto/100")
      .then()
      .statusCode(404);
  }

  @Test
  public void testFindByNome() {
    given()
      .when().get("/tiposproduto/search/nome/árvore")
      .then()
      .statusCode(200)
      .body("size()", greaterThan(0));

    given()
      .when().get("/tiposproduto/search/nome/asdfggfd")
      .then()
      .statusCode(200)
      .body("size()", equalTo(0));
  }

  @Test
  public void testFindByCategoriaProduto() {
    given()
      .when().get("/tiposproduto/search/categoriaproduto/1")
      .then()
      .statusCode(200)
      .body("size()", greaterThan(0));

    given()
      .when().get("/tiposproduto/search/categoriaproduto/12345")
      .then()
      .statusCode(404);
  }

}
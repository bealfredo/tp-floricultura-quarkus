package br.unitins.topicos1.floricultura;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import br.unitins.topicos1.floricultura.dto.CategoriaProdutoDTO;
import br.unitins.topicos1.floricultura.dto.CategoriaProdutoResponseDTO;
import br.unitins.topicos1.floricultura.dto.FornecedorDTO;
import br.unitins.topicos1.floricultura.dto.FornecedorResponseDTO;
import br.unitins.topicos1.floricultura.dto.TipoProdutoDTO;
import br.unitins.topicos1.floricultura.dto.TipoProdutoResponseDTO;
import br.unitins.topicos1.floricultura.model.CategoriaProduto;
import br.unitins.topicos1.floricultura.service.CategoriaProdutoService;
import br.unitins.topicos1.floricultura.service.FornecedorService;
import br.unitins.topicos1.floricultura.service.TipoProdutoService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class TipoProdutoResourceTeste {

  @Inject
  TipoProdutoService tipoProdutoService;

  @Test
  public void testInsert() {
    TipoProdutoDTO dto = new TipoProdutoDTO(
      "Árvores noturnas",
      "Árvores que aparecem de noite?",
      Long.valueOf(1)
    );

    given()
      .contentType(ContentType.JSON)
      .body(dto)
      .when().post("/tiposproduto")
      .then()
      .statusCode(201)
      .body(
        "id", notNullValue(),
        "nome", is("Árvores noturnas"),
        "descricao", is("Árvores que aparecem de noite?"),
        "categoriaProduto.id", is(1),
        "categoriaProduto.nome", is("Árvores"),
        "categoriaProduto.descricao", is("Árvores de diferentes espécies")); 
  }

  @Test
  public void testUpdate() {
    TipoProdutoDTO dto = new TipoProdutoDTO(
      "Árvores esquisitas",
      "muiuasisumaiuoaass?",
      Long.valueOf(1)
    );

    TipoProdutoResponseDTO fornecedorTeste = null;

    try {
      fornecedorTeste = tipoProdutoService.insert(dto);
    } catch (Exception e) {
    }
    
    Long id = fornecedorTeste.id();
  
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
  }

  @Test
  public void testDelete() {
    TipoProdutoDTO dto = new TipoProdutoDTO(
      "Árvores para apagar",
      "Precisa ser apagada",
      Long.valueOf(1)
    );

    TipoProdutoResponseDTO fornecedorTeste = null;

    try {
      fornecedorTeste = tipoProdutoService.insert(dto);
    } catch (Exception e) {
    }
    
    Long id = fornecedorTeste.id();

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
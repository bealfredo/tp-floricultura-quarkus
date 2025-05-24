package br.unitins.topicos1.floricultura;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import br.unitins.topicos1.floricultura.dto.ItemVendaDTO;
import br.unitins.topicos1.floricultura.dto.LoginDTO;
import br.unitins.topicos1.floricultura.dto.VendaDTO;
import br.unitins.topicos1.floricultura.service.CidadeService;
import br.unitins.topicos1.floricultura.service.VendaService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.inject.Inject;

@QuarkusTest
public class ProdutoResourceTeste {



  @Inject
  CidadeService cidadeService;


  private VendaDTO getOneDTO() {
    List<ItemVendaDTO> itens = new ArrayList<>();

    itens.add( new ItemVendaDTO(Integer.valueOf(1), Long.valueOf(1)));

    VendaDTO dto = new VendaDTO(
      Long.valueOf(1),
      itens
    );

    return dto;
  }

  private String obtertoken() {
    LoginDTO login = new LoginDTO("string", "string");

      Response response = given()
        .contentType(ContentType.JSON)
        .body(login)
        .when().post("/auth");

      response.then().statusCode(200);

      return response.getHeader("Authorization");
  }

  @Test
  public void testFindAll() {
    given() 
      .header("Authorization", "Bearer " + obtertoken())
      .contentType(ContentType.JSON)
      .when().get("/produtos")
      .then()
      .statusCode(200)
      .body("size()", greaterThan(0));
  }

  @Test
  public void testFindById() {
    given() 
      .header("Authorization", "Bearer " + obtertoken())
      .contentType(ContentType.JSON)
      .when().get("/produtos/1")
      .then()
      .statusCode(200)
      .body("id", is(1));
  }

  @Test
  public void testFindByIdFail() {
    given() 
      .header("Authorization", "Bearer " + obtertoken())
      .contentType(ContentType.JSON)
      .when().get("/produtos/100034")
      .then()
      .statusCode(404);
  }

  @Test
  public void testListAllStatusProduto() {
    given() 
      .header("Authorization", "Bearer " + obtertoken())
      .contentType(ContentType.JSON)
      .when().get("/produtos/statusproduto")
      .then()
      .statusCode(200)
      .body("size()", greaterThan(0));
  }

  @Test
  public void testFindByNome() {
    given() 
      .header("Authorization", "Bearer " + obtertoken())
      .contentType(ContentType.JSON)
      .when().get("/produtos/search/nome/a")
      .then()
      .statusCode(200)
      .body("size()", greaterThan(0));
  }

   @Test
    public void testFindByFornecedor() {
      given() 
        .header("Authorization", "Bearer " + obtertoken())
        .contentType(ContentType.JSON)
        .when().get("/produtos/search/fornecedor/1")
        .then()
        .statusCode(200)
        .body("size()", greaterThan(0));
    }



}

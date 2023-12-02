package br.unitins.topicos1.floricultura;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.Logger;
import org.junit.jupiter.api.Test;

import br.unitins.topicos1.floricultura.dto.ItemVendaDTO;
import br.unitins.topicos1.floricultura.dto.LoginDTO;
import br.unitins.topicos1.floricultura.dto.VendaDTO;
import br.unitins.topicos1.floricultura.dto.VendaUpdateStatusDTO;
import br.unitins.topicos1.floricultura.resource.AuthResource;
import br.unitins.topicos1.floricultura.service.VendaService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import jakarta.inject.Inject;

@QuarkusTest
public class VendaResourceTeste {

  private static final Logger LOG = Logger.getLogger(AuthResource.class);


  @Inject
  VendaService vendaService;


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
  public void testInsert() {

    ValidatableResponse validatableResponse = given()
      .header("Authorization", "Bearer " + obtertoken())
      .contentType(ContentType.JSON)
      .body(getOneDTO())
      .when().post("/vendas")
      .then()
      .statusCode(201)
      .body(
        "id", notNullValue(),
        "data", notNullValue(),
        "chavePix", notNullValue(),
        "totalVenda", is(76f),
        "lastStatus.statusVenda.id", is(1),
        "endereco", notNullValue(),
        "usuario.id", is(1),
        "itensVenda.size()", equalTo(1)
      );

  }

  @Test
  public void testFindAll() {
    given() 
      .header("Authorization", "Bearer " + obtertoken())
      .contentType(ContentType.JSON)
      .when().get("/vendas")
      .then()
      .statusCode(200)
      .body("size()", greaterThan(0));
  }

  @Test
  public void testListAllStatusVenda() {
    given() 
      .header("Authorization", "Bearer " + obtertoken())
      .contentType(ContentType.JSON)
      .when().get("/vendas/statusvenda")
      .then()
      .statusCode(200)
      .body("size()", greaterThan(0));
  }

  @Test
  public void updateStatusVenda() {
    VendaUpdateStatusDTO dto = new VendaUpdateStatusDTO(2);

    given() 
      .header("Authorization", "Bearer " + obtertoken())
      .contentType(ContentType.JSON)
      .body(dto)
      .when().patch("/vendas/1/status")
      .then()
      .statusCode(204);  
    }

    @Test
    public void testFindById() {
      given() 
        .header("Authorization", "Bearer " + obtertoken())
        .contentType(ContentType.JSON)
        .when().get("/vendas/1")
        .then()
        .statusCode(200)
        .body("id", is(1));
    }

    @Test
    public void testFindByIdFail() {
      given() 
        .header("Authorization", "Bearer " + obtertoken())
        .contentType(ContentType.JSON)
        .when().get("/vendas/1000")
        .then()
        .statusCode(404);
    }
    
}
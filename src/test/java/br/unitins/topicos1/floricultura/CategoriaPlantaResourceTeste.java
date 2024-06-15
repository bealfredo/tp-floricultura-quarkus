package br.unitins.topicos1.floricultura;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

import org.junit.jupiter.api.Test;

import br.unitins.topicos1.floricultura.dto.CategoriaPlantaDTO;
import br.unitins.topicos1.floricultura.dto.CategoriaPlantaResponseDTO;
import br.unitins.topicos1.floricultura.dto.CategoriaPlantaUpdateAtivaDTO;
import br.unitins.topicos1.floricultura.model.CategoriaPlanta;
import br.unitins.topicos1.floricultura.model.TipoCategoria;
import br.unitins.topicos1.floricultura.service.CategoriaPlantaService;
import br.unitins.topicos1.floricultura.service.JwtService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import jakarta.inject.Inject;


@QuarkusTest
public class CategoriaPlantaResourceTeste {

  @Inject
  CategoriaPlantaService categoriaPlantaService;

  @Inject
  JwtService jwtService;

  private CategoriaPlantaDTO getOneDTO() {
    return new CategoriaPlantaDTO(
      "Decorações",
      "Decorações para o seu jardim",
      1,
      true,
      1
    );
  }


  private CategoriaPlantaResponseDTO getOneResponseDTO() {
    return categoriaPlantaService.insert(getOneDTO());
  }

  // ! test all requests sem token
  @Test
  public void testAllRequestsSemToken() {
    CategoriaPlantaDTO dto = getOneDTO();

    given()
      .contentType(ContentType.JSON)
      .body(dto)
      .when().post("/categoriasplanta")
      .then()
      .statusCode(401);

    given()
      .contentType(ContentType.JSON)
      .body(dto)
      .when().put("/categoriasplanta/1")
      .then()
      .statusCode(401);

    given()
      .contentType(ContentType.JSON)
      .when().delete("/categoriasplanta/1")
      .then()
      .statusCode(401);

    given()
      .when().get("/categoriasplanta")
      .then()
      .statusCode(401);

    given()
      .when().get("/categoriasplanta/1")
      .then()
      .statusCode(200);

    given()
      .when().get("/categoriasplanta/search/nome/árvore")
      .then()
      .statusCode(200);

    given()
      .when().get("/categoriasplanta/search/ativa/true")
      .then()
      .statusCode(200);

    given()
      .when().get("/categoriasplanta/search/tipocategoria/1")
      .then()
      .statusCode(200);

    given()
      .when().get("/categoriasplanta/tipocategoria")
      .then()
      .statusCode(200);

    given()
      .contentType(ContentType.JSON)
      .body(new CategoriaPlantaUpdateAtivaDTO(true))
      .when().patch("/categoriasplanta/1/update/ativa")
      .then()
      .statusCode(401);
  }

  @Test
  public void testInsert() {
    CategoriaPlantaDTO dto = getOneDTO();

    ValidatableResponse validatableResponse = given()
      .header("Authorization", "Bearer " + jwtService.getTokenForTest())
      .contentType(ContentType.JSON)
      .body(dto)
      .when().post("/categoriasplanta")
      .then()
      .statusCode(201)
      .body(
        "id", notNullValue(),
        "nome", is(dto.nome()),
        "descricao", is(dto.descricao())); 

    Integer idCriado = validatableResponse.extract().jsonPath().get("id");
    try {
      categoriaPlantaService.delete(Long.valueOf(idCriado));
    } catch (Exception e) {
    }
  
  }

  @Test
  public void testUpdate() {
    CategoriaPlantaResponseDTO categoriaPlantaTeste = getOneResponseDTO();
    Long id = categoriaPlantaTeste.id();

    CategoriaPlantaDTO dtoUpdate = new CategoriaPlantaDTO(
      "Natalinos",
      "Itens natalinos",
      1,
      true,
      1
    );

    given()
      .header("Authorization", "Bearer " + jwtService.getTokenForTest())
      .contentType(ContentType.JSON)
      .body(dtoUpdate)
      .when().put("/categoriasplanta/" + id)
      .then()
      .statusCode(204); 

    CategoriaPlantaResponseDTO dtoAdicionado = categoriaPlantaService.findById(id);
    assertThat(dtoAdicionado.nome(), is(dtoUpdate.nome()));
    assertThat(dtoAdicionado.descricao(), is(dtoUpdate.descricao()));
    assertThat(dtoAdicionado.prioridade(), is(dtoUpdate.prioridade()));
    assertThat(dtoAdicionado.ativa(), is(dtoUpdate.ativa()));
    assertThat(dtoAdicionado.tipoCategoria(), is(TipoCategoria.valueOf(dtoUpdate.idTipoCategoria())));
  }

  @Test
  public void testUpdateInexistente() {
    CategoriaPlantaDTO dtoUpdate = new CategoriaPlantaDTO(
      "Natalinos",
      "Itens natalinos",
      1,
      true,
      1
    );

    given()
      .header("Authorization", "Bearer " + jwtService.getTokenForTest())
      .contentType(ContentType.JSON)
      .body(dtoUpdate)
      .when().put("/categoriasplanta/1234765432")
      .then()
      .statusCode(404); 
  }


  @Test
  public void testDelete() {
    CategoriaPlantaResponseDTO estadoTeste = getOneResponseDTO();
    Long id = estadoTeste.id();

    given()
      .header("Authorization", "Bearer " + jwtService.getTokenForTest())
      .contentType(ContentType.JSON)
      .when().delete("/categoriasplanta/" + id)
      .then()
      .statusCode(204);
  }

  @Test
  public void testDeleteInexistente() {
    given()
      .header("Authorization", "Bearer " + jwtService.getTokenForTest())
      .contentType(ContentType.JSON)
      .when().delete("/categoriasplanta/1234765432")
      .then()
      .statusCode(404);
  }

  @Test
  public void testDeleteComTiposProdutoAssociados() {
    given()
      .header("Authorization", "Bearer " + jwtService.getTokenForTest())
      .contentType(ContentType.JSON)
      .when().delete("/categoriasplanta/1")
      .then()
      .statusCode(409);
  }

  @Test
  public void testFindAll() {
    given() 
      .header("Authorization", "Bearer " + jwtService.getTokenForTest())
      .when().get("/categoriasplanta")
      .then()
      .statusCode(200);
  }

  @Test
  public void testFindById() {
    given()
      .header("Authorization", "Bearer " + jwtService.getTokenForTest())
      .when().get("/categoriasplanta/1")
      .then()
      .statusCode(200);
  }

  @Test
  public void testFindByIdInexistente() {
    given()
      .header("Authorization", "Bearer " + jwtService.getTokenForTest())
      .when().get("/categoriasplanta/123456789")
      .then()
      .statusCode(404);
  }

  @Test
  public void testFindByNome() {
    given()
      .header("Authorization", "Bearer " + jwtService.getTokenForTest())
      .when().get("/categoriasplanta/search/nome/árvore")
      .then()
      .statusCode(200)
      .body("size()", greaterThan(0));

    given()
      .header("Authorization", "Bearer " + jwtService.getTokenForTest())
      .when().get("/categoriasplanta/search/nome/asdfggfd")
      .then()
      .statusCode(200)
      .body("size()", equalTo(0));
  }

  @Test
  public void testFindByAtiva() {
    given()
      .header("Authorization", "Bearer " + jwtService.getTokenForTest())
      .when().get("/categoriasplanta/search/ativa/true")
      .then()
      .statusCode(200)
      .body("size()", greaterThan(0));

    given()
      .header("Authorization", "Bearer " + jwtService.getTokenForTest())
      .when().get("/categoriasplanta/search/ativa/false")
      .then()
      .statusCode(200)
      .body("size()", greaterThan(0));
  }

  @Test
  public void testFindByTipoCategoria() {
    given()
      .header("Authorization", "Bearer " + jwtService.getTokenForTest())
      .when().get("/categoriasplanta/search/tipocategoria/1")
      .then()
      .statusCode(200)
      .body("size()", greaterThan(0));
  }

  @Test
  public void testUpdateAtiva() {

    // get current active
    Long id = 1L;
    CategoriaPlantaResponseDTO categoria = categoriaPlantaService.findById(id);
    Boolean ativa = categoria.ativa();

    // update active
    CategoriaPlantaUpdateAtivaDTO dto = new CategoriaPlantaUpdateAtivaDTO(!ativa);
    given()
      .header("Authorization", "Bearer " + jwtService.getTokenForTest())
      .contentType(ContentType.JSON)
      .body(dto)
      .when().patch("/categoriasplanta/1/update/ativa")
      .then()
      .statusCode(204);

    // check if active was updated
    given()
      .header("Authorization", "Bearer " + jwtService.getTokenForTest())
      .when().get("/categoriasplanta/1")
      .then()
      .statusCode(200)
      .body("ativa", equalTo(!ativa));
  }

  @Test
  public void findTipoCategoria() {
    given()
      .header("Authorization", "Bearer " + jwtService.getTokenForTest())
      .when().get("/categoriasplanta/tipocategoria")
      .then()
      .statusCode(200);
  }
}
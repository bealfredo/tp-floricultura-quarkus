package br.unitins.topicos1.floricultura;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import br.unitins.topicos1.floricultura.service.EstadoService;

// import br.unitins.topicos1.floricultura.dto.TelefoneDTO;
// import br.unitins.topicos1.floricultura.dto.UsuarioDTO;
// import br.unitins.topicos1.floricultura.dto.UsuarioResponseDTO;
// import br.unitins.topicos1.floricultura.service.UsuarioService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.List;

@QuarkusTest
public class EstadoResourceTeste {

  @Inject
  EstadoService usuarioService;
  // quando usa registra no banco
  // cuidado

  @Test
  public void testeFindAll() {
    given()
      .when().get("/estados")
      .then()
      .statusCode(200);
  }

  // @Test
  // public void testInsert() {
  //   List<TelefoneDTO> telefones = new ArrayList<TelefoneDTO>(0);
  //   telefones.add(new TelefoneDTO("63", "5555-5555"));

  //   UsuarioDTO dto = new UsuarioDTO(
  //       "Markinho Zuk",
  //       "marquinho",
  //       "333",
  //       telefones);

  //   given()
  //       .contentType(ContentType.JSON)
  //       .body(dto)
  //       .when().post("/usuarios")
  //       .then()
  //       .statusCode(201)
  //       .body(
  //           "id", notNullValue(),
  //           "nome", is("Markinho Zuk"),
  //           "login", is("marquinho"));
  // }

  // @Test
  // public void testUpdate() throws Exception {
  //   List<TelefoneDTO> telefones = new ArrayList<TelefoneDTO>(0);
  //   telefones.add(new TelefoneDTO("63", "5555-5555"));

  //   UsuarioDTO dto = new UsuarioDTO(
  //       "Markinho Zuk",
  //       "marquinho2",
  //       "333",
  //       telefones);

  //   // inserindo um usuário
  //   UsuarioResponseDTO usuarioTest = usuarioService.insert(dto);
  //   Long id = usuarioTest.id();

  //   UsuarioDTO dtoUpdate = new UsuarioDTO(
  //       "Markinho Zuk",
  //       "mark",
  //       "555",
  //       telefones);

  //   given()
  //       .contentType(ContentType.JSON)
  //       .body(dtoUpdate)
  //       .when().put("/usuarios/" + id)
  //       .then()
  //       .statusCode(204);

  //   // verifcar alterações
  //   // porque 204 é noContent
  //   UsuarioResponseDTO usu = usuarioService.findById(id);
  //   assertThat(usu.login(), is("mark"));
  // }

}
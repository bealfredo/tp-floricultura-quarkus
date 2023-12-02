package br.unitins.topicos1.floricultura.resource;

import org.eclipse.microprofile.jwt.JsonWebToken;

import br.unitins.topicos1.floricultura.dto.UsuarioUpdateSenhaDTO;
import br.unitins.topicos1.floricultura.service.UsuarioLogadoService;
import br.unitins.topicos1.floricultura.service.UsuarioService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/usuariologado")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioLogadoResource {

  @Inject
  JsonWebToken jwt;
  
  @Inject
  UsuarioLogadoService usuarioLogadoService;


  @GET
  @RolesAllowed({ "Test", "Cliente", "Admin" })
  public Response getUsuario() {
    return Response.ok(usuarioLogadoService.getUsuario()).build();

  }

  @PATCH
  @Transactional
  @Path("/update/senha")
  @RolesAllowed({ "Test", "Cliente" })
  public Response updateSenha(UsuarioUpdateSenhaDTO dto) {
    usuarioLogadoService.updateSenha(dto);
    return Response.noContent().build();
  }

}

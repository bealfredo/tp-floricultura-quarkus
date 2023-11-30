package br.unitins.topicos1.floricultura.resource;

import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;

import br.unitins.topicos1.floricultura.dto.VendaDTO;
import br.unitins.topicos1.floricultura.dto.VendaResponseDTO;
import br.unitins.topicos1.floricultura.dto.VendaUpdateStatusDTO;
import br.unitins.topicos1.floricultura.model.StatusVenda;
import br.unitins.topicos1.floricultura.service.VendaService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/vendas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VendaResource {

  @Inject
  JsonWebToken jwt;

  @Inject
  VendaService service;

  @POST
  @Transactional
  @RolesAllowed({"User"})
  public Response insert(@Valid VendaDTO dto) {

    String login = jwt.getSubject();

    VendaResponseDTO retorno = service.insert(dto, login);
    return Response.status(Status.CREATED).entity(retorno).build();
  }

  @GET
  @RolesAllowed({"User", "Admin"})
  public Response findAll() {
    return Response.ok(service.findAll()).build();
  }

  @GET
  @Path("/statusvenda")
  public Response listAllStatusVenda() {
      return Response.ok(StatusVenda.listAll()).build();
  }

  @PATCH // ! jó admin
  @Transactional
  @Path("/{id}/status")
  public Response updateStatusVenda(@Valid VendaUpdateStatusDTO dto, @PathParam("id") Long id) {
    service.updateStatusVenda(dto, id);
    return Response.noContent().build();
  }

  // @PUT
  // @Path("/{id}")
  // public Response update(PedidoDTO dto, @PathParam("id") Long id) {
  //   service.update(dto, id);
  //   return Response.status(Status.NO_CONTENT).build();
  // }

  // @DELETE
  // @Path("/{id}")
  // public Response delete(@PathParam("id") Long id) {
  //   service.delete(id);
  //   return Response.status(Status.NO_CONTENT).build();
  // }



  @GET
  @Path("/{id}")  // /venda/id
  @RolesAllowed({"User"}) // !só admin
  public Response findById(@PathParam("id") Long id) {
    return Response.ok(service.findById(id)).build();
  }

  @GET
  @Path("/search/usuario/{id}")
  public Response findByUsuario(@PathParam("id") Long id) {
    try {
      List<VendaResponseDTO> retorno = service.findByUsuario(id);
      return Response.ok(retorno).build();
    } catch (Exception e) {
      return Response.status(Status.NOT_FOUND).entity("O Usuário informado não foi informado").build();
    }
  }

}

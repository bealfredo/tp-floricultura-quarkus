package br.unitins.topicos1.floricultura.resource;

import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;

import br.unitins.topicos1.floricultura.dto.ClienteResponseDTO;
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
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
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
  @RolesAllowed({"CUSTOMER"})
  public Response insert(@Valid VendaDTO dto) {

    String login = jwt.getSubject();

    VendaResponseDTO retorno = service.insert(dto, login);
    return Response.status(Status.CREATED).entity(retorno).build();
  }

  @GET
  @RolesAllowed({"EMPLOYEE", "OWNER"})
  public Response findAll() {
    return Response.ok(service.findAll()).build();
  }

  @GET
  @Path("/statusvenda")
  @RolesAllowed({"EMPLOYEE", "OWNER", "DELIVERYMAN"})
  public Response listAllStatusVenda() {
      return Response.ok(StatusVenda.listAll()).build();
  }

  @Transactional
  @RolesAllowed({"EMPLOYEE", "OWNER"})
  @Path("/{id}/status")
  public Response updateStatusVenda(@Valid VendaUpdateStatusDTO dto, @PathParam("id") Long id) {
    service.updateStatusVenda(dto, id);
    return Response.noContent().build();
  }

  @DELETE
  @RolesAllowed({"EMPLOYEE", "OWNER"})
  @Path("/{id}")
  public Response delete(@PathParam("id") Long id) {
    service.delete(id);
    return Response.status(Status.NO_CONTENT).build();
  }

  @GET
  @Path("/{id}")
  @RolesAllowed({"EMPLOYEE", "OWNER"})
  public Response findById(@PathParam("id") Long id) {
    return Response.ok(service.findById(id)).build();
  }

  @GET
  @RolesAllowed({"EMPLOYEE", "OWNER"})
  @Path("/search/usuario/{id}")
  public Response findByUsuario(@PathParam("id") Long id) {
    try {
      List<VendaResponseDTO> retorno = service.findByUsuario(id);
      return Response.ok(retorno).build();
    } catch (Exception e) {
      return Response.status(Status.NOT_FOUND).entity("O Usuário informado não foi informado").build();
    }
  }

  @GET
  @RolesAllowed({"EMPLOYEE", "OWNER"})
  @Path("/search/laststatus/{id}")
  public Response findByLastStatus(@PathParam("id") Integer idStatusVenda) {
    // try {
      List<VendaResponseDTO> retorno = service.findByLastStatus(idStatusVenda);
      return Response.ok(retorno).build();
    // } catch (Exception e) {
    //   return Response.status(Status.NOT_FOUND).entity("O Usuário informado não foi informado").build();
    // }
  }

  @GET
  @RolesAllowed({"CUSTOMER"})
  @Path("/minhascompras")
  public Response minhasCompras() {
    List<VendaResponseDTO> venda = service.minhasCompras();
    return Response.ok(venda).build();
  }

}

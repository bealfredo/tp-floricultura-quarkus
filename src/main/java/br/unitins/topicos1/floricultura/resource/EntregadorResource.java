package br.unitins.topicos1.floricultura.resource;

import br.unitins.topicos1.floricultura.dto.EntregadorDTO;
import br.unitins.topicos1.floricultura.dto.EntregadorResponseDTO;
import br.unitins.topicos1.floricultura.service.EntregadorService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/entregadors")
@Produces(MediaType.APPLICATION_JSON) // indica que todos serão assim
@Consumes(MediaType.APPLICATION_JSON)
public class EntregadorResource {

  @Inject
  EntregadorService service;

  @POST
  public Response insert(@Valid EntregadorDTO dto) throws Exception {
    EntregadorResponseDTO retorno = service.insert(dto);
    return Response.status(Status.CREATED).entity(retorno).build();
  }

  @PUT
  @Path("/{id}")
  public Response update(EntregadorDTO dto, @PathParam("id") Long id) throws Exception {
    service.update(dto, id);
    return Response.status(Status.NO_CONTENT).build();
  }

  @DELETE
  @Path("/{id}")
  public Response delete(@PathParam("id") Long id) {
    service.delete(id);
    return Response.status(Status.NO_CONTENT).build();
  }

  @GET
  public Response findAll() {
    return Response.ok(service.findByAll()).build();
  }

  @GET
  @Path("/{id}")  // /Entregador/id
  public Response findById(@PathParam("id") Long id) {
    return Response.ok(service.findById(id)).build();
  }
}
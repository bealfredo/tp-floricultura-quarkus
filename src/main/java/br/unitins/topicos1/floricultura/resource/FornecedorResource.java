package br.unitins.topicos1.floricultura.resource;

import br.unitins.topicos1.floricultura.dto.FornecedorDTO;
import br.unitins.topicos1.floricultura.dto.FornecedorResponseDTO;
import br.unitins.topicos1.floricultura.service.FornecedorService;
import jakarta.annotation.security.RolesAllowed;
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

@Path("/fornecedores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FornecedorResource {

  @Inject
  FornecedorService service;

  @POST
  @RolesAllowed({"OWNER", "EMPLOYEE"})
  public Response insert(@Valid FornecedorDTO dto) {
    FornecedorResponseDTO retorno = service.insert(dto);
    return Response.status(Status.CREATED).entity(retorno).build();
  }

  @PUT
  @RolesAllowed({"OWNER", "EMPLOYEE"})
  @Path("/{id}")
  public Response update(FornecedorDTO dto, @PathParam("id") Long id) {
    service.update(dto, id);
    return Response.status(Status.NO_CONTENT).build();
  }

  @DELETE
  @RolesAllowed({"OWNER", "EMPLOYEE"})
  @Path("/{id}")
  public Response delete(@PathParam("id") Long id) {
    service.delete(id);
    return Response.status(Status.NO_CONTENT).build();
  }

  @GET
  @RolesAllowed({"OWNER", "EMPLOYEE"})
  public Response findAll() {
    return Response.ok(service.findAll()).build();
  }

  @GET
  @RolesAllowed({"OWNER", "EMPLOYEE"})
  @Path("/{id}") 
  public Response findById(@PathParam("id") Long id) {
    return Response.ok(service.findById(id)).build();
  }

  @GET
  @RolesAllowed({"OWNER", "EMPLOYEE"})
  @Path("/search/nome/{nome}")
  public Response findByNome(@PathParam("nome") String nome) {
    return Response.ok(service.findByNome(nome)).build();
  }

  @GET
  @RolesAllowed({"OWNER", "EMPLOYEE"})
  @Path("/search/cnpj/{cnpj}")
  public Response findByCnpj(@PathParam("cnpj") String cnpj) {
    return Response.ok(service.findByCnpj(cnpj)).build();
  }

  @GET
  @RolesAllowed({"OWNER", "EMPLOYEE"})
  @Path("/search/email/{email}")
  public Response findByEmail(@PathParam("email") String email) {
    return Response.ok(service.findByEmail(email)).build();
  }
}

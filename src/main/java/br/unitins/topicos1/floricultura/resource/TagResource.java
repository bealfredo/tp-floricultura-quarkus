package br.unitins.topicos1.floricultura.resource;

import br.unitins.topicos1.floricultura.dto.CategoriaPlantaUpdateAtivaDTO;
import br.unitins.topicos1.floricultura.dto.TagDTO;
import br.unitins.topicos1.floricultura.dto.TagResponseDTO;
import br.unitins.topicos1.floricultura.service.TagService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/tags")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TagResource {

  @Inject
  TagService service;

  @POST
  @RolesAllowed({"OWNER", "EMPLOYEE"})
  public Response insert(@Valid TagDTO dto) {
    TagResponseDTO retorno = service.insert(dto);
    return Response.status(Status.CREATED).entity(retorno).build();
  }

  @PUT
  @RolesAllowed({"OWNER", "EMPLOYEE"})
  @Path("/{id}")
  public Response update(TagDTO dto, @PathParam("id") Long id) {
    service.update(dto, id);
    return Response.status(Status.NO_CONTENT).build();
  }

  @PATCH
  @RolesAllowed({"OWNER", "EMPLOYEE"})
  @Path("/{id}/update/ativa")
  public Response updateAtiva(CategoriaPlantaUpdateAtivaDTO dto, @PathParam("id") Long id) {
    service.updateAtiva(dto, id);
    return Response.status(Status.NO_CONTENT).build();
  }


  @DELETE
  @RolesAllowed({"OWNER", "EMPLOYEE"})
  @Path("/{id}")
  public Response delete(@PathParam("id") Long id) {
    try {
      service.delete(id);
      return Response.status(Status.NO_CONTENT).build();      
    } catch (NotFoundException e) {
      return Response.status(Status.NOT_FOUND).entity("A tag não foi encontrada").build();
    } catch (Exception e) {
      return Response.status(Status.CONFLICT).entity("Não é possível apagar a tag porque existem produtos associados a ela").build();
    }
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
  @Path("/search/ativa/{ativa}")
  public Response findByAtiva(@PathParam("ativa") Boolean ativa) {
    return Response.ok(service.findByAtiva(ativa)).build();
  }

  @GET
  @RolesAllowed({"OWNER", "EMPLOYEE"})
  @Path("/search/categoriaplanta/{categoriaplanta}")
  public Response findByCategoriaPlanta(@PathParam("categoriaplanta") Long categoriaplanta) {
    return Response.ok(service.findByCategoriaPlanta(categoriaplanta)).build();
  }

}

package br.unitins.topicos1.floricultura.resource;

import br.unitins.topicos1.floricultura.dto.CategoriaPlantaDTO;
import br.unitins.topicos1.floricultura.dto.CategoriaPlantaResponseDTO;
import br.unitins.topicos1.floricultura.service.CategoriaPlantaService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/categoriasplanta")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoriaPlantaResource {

  @Inject
  CategoriaPlantaService service;

  @POST
  public Response insert(@Valid CategoriaPlantaDTO dto) {
    CategoriaPlantaResponseDTO retorno = service.insert(dto);
    return Response.status(Status.CREATED).entity(retorno).build();
  }

  @PUT
  @Path("/{id}")
  public Response update(CategoriaPlantaDTO dto, @PathParam("id") Long id) {
    service.update(dto, id);
    return Response.status(Status.NO_CONTENT).build();
  }

  @DELETE
  @Path("/{id}")
  public Response delete(@PathParam("id") Long id) {
    try {
      service.delete(id);
      return Response.status(Status.NO_CONTENT).build();      
    } catch (NotFoundException e) {
      return Response.status(Status.NOT_FOUND).entity("A categoria não foi encontrada").build();
    } catch (Exception e) {
      return Response.status(Status.CONFLICT).entity("Não é possível apagar a categoria porque existem tiposdeproduto associados a ela").build();
    }
  }
  
  @GET
  public Response findAll() {
    return Response.ok(service.findAll()).build();
  }

  @GET
  @Path("/{id}") 
  public Response findById(@PathParam("id") Long id) {
    return Response.ok(service.findById(id)).build();
  }

  @GET
  @Path("/search/nome/{nome}")
  public Response findByNome(@PathParam("nome") String nome) {
    return Response.ok(service.findByNome(nome)).build();
  }

  @GET
  @Path("/search/ativa/{ativa}")
  public Response findByAtiva(@PathParam("ativa") Boolean ativa) {
    return Response.ok(service.findByAtiva(ativa)).build();
  }

  @GET
  @Path("/search/tipocategoria/{tipocategoria}")
  public Response findByTipoCategoria(@PathParam("tipocategoria") Integer tipocategoria) {
    return Response.ok(service.findByTipoCategoria(tipocategoria)).build();
  }

}

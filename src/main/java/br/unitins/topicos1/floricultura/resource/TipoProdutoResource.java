package br.unitins.topicos1.floricultura.resource;

import java.util.List;

import br.unitins.topicos1.floricultura.dto.TipoProdutoDTO;
import br.unitins.topicos1.floricultura.dto.TipoProdutoResponseDTO;
import br.unitins.topicos1.floricultura.service.TipoProdutoService;
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

@Path("/tiposproduto")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TipoProdutoResource {

  @Inject
  TipoProdutoService service;

  @POST
  public Response insert(@Valid TipoProdutoDTO dto) {
    try {
      TipoProdutoResponseDTO retorno = service.insert(dto);
      return Response.status(Status.CREATED).entity(retorno).build();
    } catch (Exception e) {
      return Response.status(Status.NOT_FOUND).entity("A categoria informada não foi encontrada").build();
    }
  }

  @PUT
  @Path("/{id}")
  public Response update(TipoProdutoDTO dto, @PathParam("id") Long id) {
    try {
      service.update(dto, id);
      return Response.status(Status.NO_CONTENT).build();
    } catch (Exception e) {
      return Response.status(Status.NOT_FOUND).entity("O tipocategoria não existe ou a categoria informada não foi encontrada").build();
    }

  }

  @DELETE
  @Path("/{id}")
  public Response delete(@PathParam("id") Long id) {
    service.delete(id);
    return Response.status(Status.NO_CONTENT).build();
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
  @Path("/search/categoriaproduto/{categoriaproduto}")
  public Response findByCategoriaProduto(@PathParam("categoriaproduto") Long categoriaproduto) {
    try {
      List<TipoProdutoResponseDTO> retorno = service.findByCategoriaProduto(categoriaproduto);
      return Response.ok(retorno).build();
    } catch (Exception e) {
      return Response.status(Status.NOT_FOUND).entity("A categoria informada não foi encontrada").build();
    }
  }

}

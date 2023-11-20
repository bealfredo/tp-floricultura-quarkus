package br.unitins.topicos1.floricultura.resource;

import br.unitins.topicos1.floricultura.dto.ProdutoDTO;
import br.unitins.topicos1.floricultura.model.StatusProduto;
import br.unitins.topicos1.floricultura.service.ProdutoService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;



@Path("/produtos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProdutoResource {

    @Inject
    ProdutoService service;

    @POST
    public Response insert(ProdutoDTO dto) {
        return Response.status(Status.CREATED).entity(service.insert(dto)).build();
    }

    @PUT
    @Transactional
    @Path("/{id}")
    public Response update(ProdutoDTO dto, @PathParam("id") Long id) {
        service.update(dto, id);
        return Response.noContent().build();
    }

    // @DELETE
    // @Transactional
    // @Path("/{id}")
    // public Response delete(@PathParam("id") Long id) {
    //     service.delete(id);
    //     return Response.noContent().build();
    // }

    @GET
    public Response findAll() {
        return Response.ok(service.findAll()).build();
    }

    @GET
    @Path("/statusproduto")
    public Response listAllStatusProduto() {
        return Response.ok(StatusProduto.listAll()).build();
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
    @Path("/search/codigo/{codigo}")
    public Response findByCodigo(@PathParam("codigo") String codigo) {
        return Response.ok(service.findByCodigo(codigo)).build();
    }
    
    @GET
    @Path("/search/tipoproduto/{tipoproduto}")
    public Response findByTipoProduto(@PathParam("tipoproduto") Long tipoproduto) {
        return Response.ok(service.findByTipoProduto(tipoproduto)).build();
    }

    @GET
    @Path("/search/fornecedor/{fornecedor}")
    public Response findByFornecedor(@PathParam("fornecedor") Long fornecedor) {
        return Response.ok(service.findByFornecedor(fornecedor)).build();
    }

    @GET
    @Path("/search/statusproduto/{statusproduto}")
    public Response findByFornecedor(@PathParam("statusproduto") Integer statusproduto) {
        return Response.ok(service.findByStatusProduto(statusproduto)).build();
    }

}

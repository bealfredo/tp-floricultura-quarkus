package br.unitins.topicos1.floricultura.resource;

import br.unitins.topicos1.floricultura.dto.CidadeDTO;
import br.unitins.topicos1.floricultura.dto.CidadeResponseDTO;
import br.unitins.topicos1.floricultura.dto.TipoProdutoResponseDTO;
import br.unitins.topicos1.floricultura.service.CidadeService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
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

@Path("/cidades")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CidadeResource {

    @Inject
    CidadeService service;

    @POST
    @RolesAllowed({"Test", "Admin"})
    public Response insert(CidadeDTO dto) {
        try {
            CidadeResponseDTO retorno = service.insert(dto);
            return Response.status(Status.CREATED).entity(retorno).build();
        } catch (Exception e) {
            return Response.status(Status.NOT_FOUND).entity("O estado informado não foi encontrado").build();
        }
    }

    @PUT
    @Transactional
    @Path("/{id}")
    @RolesAllowed({"Test", "Admin"})
    public Response update(CidadeDTO dto, @PathParam("id") Long id) {
        try {
            service.update(dto, id);
            return Response.status(Status.NO_CONTENT).build();
        } catch (Exception e) {
            return Response.status(Status.NOT_FOUND).entity("A cidade não existe ou o estado informado não foi encontrado").build();
        }
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    @RolesAllowed({"Test", "Admin"})
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }

    @GET
    @RolesAllowed({"Test", "Admin", "Cliente"})
    public Response findAll() {
        return Response.ok(service.findByAll()).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"Test", "Admin", "Cliente"})
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(service.findById(id)).build();
    }
    
    @GET
    @Path("/search/nome/{nome}")
    @RolesAllowed({"Test", "Admin", "Cliente"})
    public Response findByNome(@PathParam("nome") String nome) {
        return Response.ok(service.findByNome(nome)).build();
    }
}

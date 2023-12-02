package br.unitins.topicos1.floricultura.resource;

import br.unitins.topicos1.floricultura.dto.UsuarioDTO;
import br.unitins.topicos1.floricultura.dto.UsuarioUpdateInfoDTO;
import br.unitins.topicos1.floricultura.dto.UsuarioUpdateSenhaDTO;
import br.unitins.topicos1.floricultura.service.UsuarioService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;



@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject
    UsuarioService service;

    @POST
    @RolesAllowed({"Test", "Admin", "Cliente"})
    public Response insert(UsuarioDTO dto) {
        return Response.status(Status.CREATED).entity(service.insert(dto)).build();
    }

    @PATCH
    @Transactional
    @RolesAllowed({"Test", "Admin", "Cliente"})
    @Path("/{id}")
    public Response update(UsuarioUpdateInfoDTO dto, @PathParam("id") Long id) {
        service.update(dto, id);
        return Response.noContent().build();
    }

    @DELETE
    @Transactional
    @RolesAllowed({"Test", "Admin"})
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }

    @GET
    @RolesAllowed({"Test", "Admin"})
    public Response findAll() {
        return Response.ok(service.findAll()).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"Test", "Admin"})
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(service.findById(id)).build();
    }

    @GET
    @Path("/search/nome/{nome}")
    @RolesAllowed({"Test", "Admin"})
    public Response findByNome(@PathParam("nome") String nome) {
        return Response.ok(service.findByNome(nome)).build();
    }

    @GET
    @RolesAllowed({ "Test", "Cliente", "Admin" })
    @Path("/userInfo")
    public Response userInfo() {
        return Response.ok(service.userInfo()).build();
    }

    @GET
    @RolesAllowed({ "Test", "Cliente", "Admin" })
    @Path("/minhasCompras")
    public Response minhasCompras() {
        return Response.ok(service.minhasCompras()).build();
    }

    @PATCH
    @Transactional
    @Path("/update/senha")
    @RolesAllowed({"Test", "Admin", "Cliente"})
    public Response updateSenha(UsuarioUpdateSenhaDTO dto) {
        service.updateSenha(dto);
        return Response.noContent().build();
    }
    
}

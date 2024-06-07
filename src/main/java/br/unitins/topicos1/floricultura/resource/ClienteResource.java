package br.unitins.topicos1.floricultura.resource;
import br.unitins.topicos1.floricultura.dto.ClienteExistingUserDTO;
import br.unitins.topicos1.floricultura.dto.ClienteFastCreateDTO;
import br.unitins.topicos1.floricultura.dto.ClienteResponseDTO;
import br.unitins.topicos1.floricultura.dto.ClienteUpdateCarrinhoDTO;
import br.unitins.topicos1.floricultura.dto.ClienteUpdateDTO;
import br.unitins.topicos1.floricultura.service.ClienteService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteResource {

    @Inject
    ClienteService service;

    @POST
    public Response insert(ClienteFastCreateDTO dto) {
        String token = service.insert(dto);
        return Response.ok().header("Authorization", token).build();
    }

    @PATCH 
    @Path("/{id}")
    public Response update(ClienteUpdateDTO dto, @PathParam("id") Long id) {
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
    public Response findAll(
        @QueryParam("page") @DefaultValue("0") int page,
        @QueryParam("pageSize") @DefaultValue("100") int pageSize
    ) {
        return Response.ok(service.findByAll(page, pageSize)).build();
    }


    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(service.findById(id)).build();
    }

    @GET
    @Path("/count")
    public Response count(){
        return Response.ok(service.count()).build();
    }

    @POST
    @Path("/insertexistinguser")
    public Response insertExistingUser(ClienteExistingUserDTO dto) {
        String token = service.insertExistingUser(dto);
        return Response.ok().header("Authorization", token).build();
    }

    @PATCH
    @RolesAllowed({"CUSTOMER"})
    @Path("/updatecarrinho")
    public Response updateCarrinho(ClienteUpdateCarrinhoDTO dto) {
        service.updateCarrinho(dto);
        return Response.status(Status.NO_CONTENT).build();
    }

    @GET
    @RolesAllowed({"CUSTOMER"})
    @Path("/carrinho")
    public Response getCarrinho() {
        String carrinho = service.getCarrinho();
        return Response.ok(carrinho).build();
    }

    @GET
    @RolesAllowed({"CUSTOMER"})
    @Path("/findbytoken")
    public Response findByToken() {
        ClienteResponseDTO cliente = service.findByToken();
        return Response.ok(cliente).build();
    }

    // @GET
    // @RolesAllowed({"CUSTOMER"})
    // @Path("/carrinhoplantas")
    // public Response getCarrinhoPlantas() {
    //     String carrinho = service.getCarrinho();
    //     return Response.ok(carrinho).build();
    // }
    
    // @GET
    // @Path("/search/nome/{nome}")
    // public Response findByNome(@PathParam("nome") String nome) {
    //     return Response.ok(service.findByNome(nome)).build();
    // }

    // @GET
    // @Path("/count")
    // public long count(){
    //     return service.count();
    // }

}
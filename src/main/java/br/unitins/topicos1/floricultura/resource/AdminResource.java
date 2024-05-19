package br.unitins.topicos1.floricultura.resource;
import br.unitins.topicos1.floricultura.dto.AdminCreateDTO;
import br.unitins.topicos1.floricultura.dto.AdminResponseDTO;
import br.unitins.topicos1.floricultura.dto.AdminSelfUpdateDTO;
import br.unitins.topicos1.floricultura.dto.AdminUpdateDTO;
import br.unitins.topicos1.floricultura.dto.EmailAvailableDTO;
import br.unitins.topicos1.floricultura.dto.EmailAvailableResponseDTO;
import br.unitins.topicos1.floricultura.service.AdminService;
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

@Path("/admins")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdminResource {

    @Inject
    AdminService service;

    //check email availability
    @POST
    @Path("/emailavailable")
    public Response checkEmailAvailable(EmailAvailableDTO dto) {
        EmailAvailableResponseDTO retorno = service.checkEmailAvailable(dto);
        return Response.status(200).entity(retorno).build();
    }

    @POST
    public Response insert(AdminCreateDTO dto) {
        AdminResponseDTO retorno = service.insert(dto);
        return Response.status(Status.CREATED).entity(retorno).build();
    }

    @PATCH 
    @Path("/{id}")
    public Response update(AdminUpdateDTO dto, @PathParam("id") Long id) {
        service.update(dto, id);
        return Response.status(Status.NO_CONTENT).build();
    }

    @PATCH 
    @Path("/self/{id}")
    public Response selfUpdate(AdminSelfUpdateDTO dto, @PathParam("id") Long id) {
        service.selfUpdate(dto, id);
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
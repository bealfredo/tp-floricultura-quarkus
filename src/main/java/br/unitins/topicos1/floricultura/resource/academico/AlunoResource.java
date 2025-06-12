package br.unitins.topicos1.floricultura.resource.academico;
import br.unitins.topicos1.floricultura.dto.ClienteExistingUserDTO;
import br.unitins.topicos1.floricultura.dto.ClienteResponseDTO;
import br.unitins.topicos1.floricultura.dto.ClienteUpdateCarrinhoDTO;
import br.unitins.topicos1.floricultura.dto.ClienteUpdateDTO;
import br.unitins.topicos1.floricultura.dto.academico.aluno.AlunoFastCreateDTO;
import br.unitins.topicos1.floricultura.dto.academico.aluno.AlunoResponseDTO;
import br.unitins.topicos1.floricultura.dto.academico.aluno.AlunoUpdateDTO;
import br.unitins.topicos1.floricultura.dto.academico.aluno.RematriculaDTO;
import br.unitins.topicos1.floricultura.service.academico.aluno.AlunoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
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

@Path("/alunos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AlunoResource {

    @Inject
    AlunoService service;

    @POST
    public Response insert(AlunoFastCreateDTO dto) {
        String token = service.insert(dto);
        return Response.ok().header("Authorization", token).build();
    }

    @PATCH 
    // @RolesAllowed({"OWNER", "EMPLOYEE"})
    @Path("/{id}")
    public Response update(AlunoUpdateDTO dto, @PathParam("id") Long id) {
        service.update(dto, id);
        return Response.status(Status.NO_CONTENT).build();
    }

    @PATCH
    // @RolesAllowed({"CUSTOMER"})
    @Path("/selfupdate")
    public Response selfUpdate(AlunoUpdateDTO dto) {
        service.selfUpdate(dto);
        return Response.status(Status.NO_CONTENT).build();
    }

    @DELETE
    // @RolesAllowed({"OWNER", "EMPLOYEE"})
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }

    @GET
    // @RolesAllowed({"OWNER", "EMPLOYEE"})
    public Response findAll(
        @QueryParam("page") @DefaultValue("0") int page,
        @QueryParam("pageSize") @DefaultValue("100") int pageSize
    ) {
        return Response.ok(service.findByAll(page, pageSize)).build();
    }


    @GET
    // @RolesAllowed({"OWNER", "EMPLOYEE"})
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(service.findById(id)).build();
    }

    @GET
    // @RolesAllowed({"OWNER", "EMPLOYEE"})
    @Path("/count")
    public Response count(){
        return Response.ok(service.count()).build();
    }

    // @POST
    // @Path("/insertexistinguser")
    // public Response insertExistingUser(ClienteExistingUserDTO dto) {
    //     String token = service.insertExistingUser(dto);
    //     return Response.ok().header("Authorization", token).build();
    // }

    // @PATCH
    // @RolesAllowed({"CUSTOMER"})
    // @Path("/updatecarrinho")
    // public Response updateCarrinho(ClienteUpdateCarrinhoDTO dto) {
    //     service.updateCarrinho(dto);
    //     return Response.status(Status.NO_CONTENT).build();
    // }

    // @GET
    // @RolesAllowed({"CUSTOMER"})
    // @Path("/carrinho")
    // public Response getCarrinho() {
    //     String carrinho = service.getCarrinho();
    //     return Response.ok(carrinho).build();
    // }

    @GET
    // @RolesAllowed({"CUSTOMER"})
    @Path("/findbytoken")
    public Response findByToken() {
        AlunoResponseDTO aluno = service.findByToken();
        return Response.ok(aluno).build();
    }

    @POST
    @Path("/{id}/rematricula")
    @Transactional
    public Response rematricula(@Valid RematriculaDTO dto, @PathParam("id") Long id) {
        AlunoResponseDTO alunoResponse = service.rematricula(dto, id);
        return Response.ok(alunoResponse).build();
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
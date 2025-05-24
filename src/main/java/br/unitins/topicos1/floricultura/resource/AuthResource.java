package br.unitins.topicos1.floricultura.resource;

import br.unitins.topicos1.floricultura.dto.AuthUsuarioDTO;
import br.unitins.topicos1.floricultura.dto.EmailAvailableDTO;
import br.unitins.topicos1.floricultura.dto.EmailAvailableResponseDTO;
import br.unitins.topicos1.floricultura.dto.EmailTakenClienteResponseDTO;
import br.unitins.topicos1.floricultura.dto.UsuarioTiposPerfilByEmailResponseDTO;
import br.unitins.topicos1.floricultura.service.HashService;
import br.unitins.topicos1.floricultura.service.JwtService;
import br.unitins.topicos1.floricultura.service.UsuarioService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    UsuarioService service;

    @Inject
    HashService hashService;

    @Inject
    JwtService jwtService;
    
    //check email availability
    @POST
    @Path("/emailavailable")
    public Response checkEmailAvailable(@Valid EmailAvailableDTO dto) {
        EmailAvailableResponseDTO retorno = service.checkEmailAvailable(dto);
        return Response.status(200).entity(retorno).build();
    }

    @POST
    @Path("/emailtakencliente")
    public Response checkEmailTakenCliente(@Valid EmailAvailableDTO dto) {
        EmailTakenClienteResponseDTO retorno = service.checkEmailTakenCliente(dto);
        return Response.status(200).entity(retorno).build();
    }

    @POST
    @Path("/usuariotiposperfilbyemail")
    public Response suarioTiposPerfilByEmail(@Valid EmailAvailableDTO dto) {
        UsuarioTiposPerfilByEmailResponseDTO retorno = service.usuarioTiposPerfilByEmail(dto);
        return Response.status(200).entity(retorno).build();
    }

    @POST
    public Response login(@Valid AuthUsuarioDTO dto) {

        String token = service.login(dto);
        return Response.ok().header("Authorization", token).build();
    }

    @GET
    @RolesAllowed({ "OWNER", "EMPLOYEE", "CUSTOMER", "DELIVERY"})
    @Path("/userinfo")
    public Response userInfo(@Context SecurityContext securityContext) {
        if (securityContext.isUserInRole("OWNER")) {
            return Response.ok(service.userInfoAdmin()).build();
        } else if (securityContext.isUserInRole("EMPLOYEE")) {
            return Response.ok(service.userInfoAdmin()).build();
        } else if (securityContext.isUserInRole("CUSTOMER")) {
            return Response.ok(service.userInfoCliente()).build();
        } else if (securityContext.isUserInRole("DELIVERY")) {
            return Response.ok(service.userInfoEntregador()).build();
        } else {
            return Response.status(Response.Status.FORBIDDEN).entity("Access denied").build();
        }
    }

    // @POST
    // public Response login(@Valid AuthUsuarioDTO dto) {

    //     String hashSenha = hashService.getHashSenha(dto.senha());

    //     UsuarioResponseDTO result = service.findByLoginAndSenha(dto.login(), hashSenha);

    //     String token = jwtService.generateJwt(result);

    //     return Response.ok().header("Authorization", token).build();
    // }

    
    
}

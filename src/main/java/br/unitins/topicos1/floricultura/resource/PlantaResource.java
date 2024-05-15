package br.unitins.topicos1.floricultura.resource;

import java.io.File;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import br.unitins.topicos1.floricultura.dto.PlantaCriarRascunhoDTO;
import br.unitins.topicos1.floricultura.dto.PlantaDTO;
import br.unitins.topicos1.floricultura.dto.PlantaUpdateAddRemoveQuantidadeDTO;
import br.unitins.topicos1.floricultura.dto.PlantaUpdateStatusPlantaDTO;
import br.unitins.topicos1.floricultura.form.PlantaImageForm;
import br.unitins.topicos1.floricultura.model.NivelDificuldade;
import br.unitins.topicos1.floricultura.model.NivelToxidade;
import br.unitins.topicos1.floricultura.model.PortePlanta;
import br.unitins.topicos1.floricultura.model.StatusPlanta;
import br.unitins.topicos1.floricultura.model.TipoCategoria;
import br.unitins.topicos1.floricultura.service.PlantaService;
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
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.core.Response.Status;


@Path("/plantas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PlantaResource {

    @Inject
    PlantaService service;

    @POST
    public Response criarRascunho(PlantaCriarRascunhoDTO dto) {
        return Response.status(Status.CREATED).entity(service.criarRascunho(dto)).build();
    }

    // @POST
    // public Response insert(ProdutoDTO dto) {
    //     return Response.status(Status.CREATED).entity(service.insert(dto)).build();
    // }

    @PUT
    @Transactional
    @Path("/{id}")
    public Response update(PlantaDTO dto, @PathParam("id") Long id) {
        service.update(dto, id);
        return Response.noContent().build();
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }

    // @RolesAllowed({"Admin"})
    @PATCH
    @Path("/{id}/upload/imagem")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response adicionarImagem(@MultipartForm PlantaImageForm form, @PathParam("id") Long id) {
        return Response.ok(service.adicionarImagem(form, id)).build();
    }

    // // @RolesAllowed({"Test", "Cliente", "Admin"})
    @GET
    @Path("/{id}/download/imagem/{nomeImagem}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadImagem(@PathParam("id") Long id, @PathParam("nomeImagem") String nomeImagem){
        File imageFile = service.downloadImagem(nomeImagem ,id);
        ResponseBuilder response = Response.ok(imageFile);
        response.header("Content-Disposition", "attachment;filename=" + imageFile.getName());
        return response.build();
    }

    // @RolesAllowed({"Test", "Cliente", "Admin"})
    @PATCH
    @Path("/{id}/delete/imagem/{nomeImagem}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response deleteImagem(@PathParam("id") Long id, @PathParam("nomeImagem") String nomeImagem) {
        service.deleteImagem(nomeImagem, id);
        return Response.noContent().build();
    }

    @PATCH
    @Path("/{id}/update/imagemprincipal/{nomeImagem}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response definirImagemPrincipal(@PathParam("id") Long id, @PathParam("nomeImagem") String nomeImagem) {
        service.definirImagemPrincipal(nomeImagem, id);
        return Response.noContent().build();
    }
    

    // @RolesAllowed({"Test", "Cliente", "Admin"})
    @PATCH
    @Path("/{id}/update/statusplanta")
    public Response updateStatusplanta(PlantaUpdateStatusPlantaDTO dto, @PathParam("id") Long id) {
        service.updateStatusPlanta(dto, id);
        return Response.noContent().build();
    }

    @PATCH
    @Path("/{id}/update/addremovequantidade")
    public Response updateQuantidade(PlantaUpdateAddRemoveQuantidadeDTO dto, @PathParam("id") Long id) {
        service.updateAddRemoveQuantidade(dto, id);
        return Response.noContent().build();
    }

    @GET
    @Path("/statusplanta")
    public Response listAllStatusPlanta() {
        return Response.ok(StatusPlanta.listAll()).build();
    }

    @GET
    @Path("/niveldificuldade")
    public Response listAllNivelDificuldade() {
        return Response.ok(NivelDificuldade.listAll()).build();
    }

    @GET
    @Path("/niveltoxicidade")
    public Response listAllNivelToxicidade() {
        return Response.ok(NivelToxidade.listAll()).build();
    }

    @GET
    @Path("/porteplanta")
    public Response listAllPortePlanta() {
        return Response.ok(PortePlanta.listAll()).build();
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

    // both nomeComum and nomeCientifico
    @GET
    @Path("/search/nome/{nome}")
    public Response findByNome(@PathParam("nome") String nome) {
        return Response.ok(service.findByNome(nome)).build();
    }
    
    // @GET
    // @Path("/search/codigo/{codigo}")
    // public Response findByCodigo(@PathParam("codigo") String codigo) {
    //     return Response.ok(service.findByCodigo(codigo)).build();
    // }
    
    // @GET
    // @Path("/search/tipoproduto/{tipoproduto}")
    // public Response findByTipoProduto(@PathParam("tipoproduto") Long tipoproduto) {
    //     return Response.ok(service.findByTipoProduto(tipoproduto)).build();
    // }

    // @GET
    // @Path("/search/fornecedor/{fornecedor}")
    // public Response findByFornecedor(@PathParam("fornecedor") Long fornecedor) {
    //     return Response.ok(service.findByFornecedor(fornecedor)).build();
    // }

    // @GET
    // @Path("/search/statusproduto/{statusproduto}")
    // public Response findByFornecedor(@PathParam("statusproduto") Integer statusproduto) {
    //     return Response.ok(service.findByStatusProduto(statusproduto)).build();
    // }

}

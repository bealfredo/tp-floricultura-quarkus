package br.unitins.topicos1.floricultura.resource;

import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import br.unitins.topicos1.DataInitializer;

@Path("/init")
@Produces(MediaType.APPLICATION_JSON)
public class InitResource {
    
    private static final Logger LOGGER = Logger.getLogger(InitResource.class.getName());
    
    @Inject
    DataInitializer initializer;
    
    @POST
    @Transactional
    @Path("/seed")
    public Response init() {
        LOGGER.info("Endpoint de inicialização chamado");
        
        try {
            // Modificando o DataInitializer para executar via inicialização manual
            initializer.onStart(null);
            
            // Criando uma resposta mais estruturada em formato JSON
            Map<String, String> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Inicialização de dados realizada com sucesso");
            
            return Response.status(Status.OK).entity(response).build();
        } catch (Exception e) {
            LOGGER.severe("Erro na inicialização: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Erro ao inicializar dados: " + e.getMessage());
            
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(errorResponse)
                    .build();
        }
    }
}
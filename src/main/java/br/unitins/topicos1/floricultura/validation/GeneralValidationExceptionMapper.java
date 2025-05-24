package br.unitins.topicos1.floricultura.validation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class GeneralValidationExceptionMapper implements ExceptionMapper<GeneralValidationException> {

  @Override
  public Response toResponse(GeneralValidationException exception) {

    ValidationError validationError = new ValidationError("400", "Erro de validação.");
    validationError.addFieldError(exception.getErrorContext(), exception.getMessage());

    return Response.status(Status.BAD_REQUEST).entity(validationError).build();
  }
  
}

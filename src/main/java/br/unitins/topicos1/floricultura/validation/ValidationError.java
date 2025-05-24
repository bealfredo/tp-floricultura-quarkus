package br.unitins.topicos1.floricultura.validation;

import br.unitins.topicos1.floricultura.application.Error;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends Error {
  
  record FieldError(String errorContext, String message) {};
  private List<FieldError> errors = null;
  
  public ValidationError(String code, String message) {
    super(code, message);
  }

  public void addFieldError(String errorContext, String message) {
    if (errors == null)
      errors = new ArrayList<FieldError>();
    errors.add(new FieldError(errorContext, message));
  }

  public List<FieldError> getErrors() {
    return errors.stream().toList();
  }
}

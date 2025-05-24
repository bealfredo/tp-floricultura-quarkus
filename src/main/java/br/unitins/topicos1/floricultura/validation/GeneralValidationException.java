package br.unitins.topicos1.floricultura.validation;

public class GeneralValidationException extends RuntimeException {

    private String errorContext;

    public GeneralValidationException( String errorContext,String message) {
        super(message);
        this.errorContext = errorContext;
    }

    public String getErrorContext() {
        return errorContext;
    }
}
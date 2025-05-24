package br.unitins.topicos1.floricultura.dto;

public record EmailTakenClienteResponseDTO(
    String email,
    Boolean available,
    Boolean takenByCliente
) {
    public static EmailTakenClienteResponseDTO valueOf(String email, Boolean available, Boolean takenByCliente){
        return new EmailTakenClienteResponseDTO(
            email, 
            available,
            takenByCliente
        );
    }
}

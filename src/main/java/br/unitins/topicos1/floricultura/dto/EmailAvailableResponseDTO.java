package br.unitins.topicos1.floricultura.dto;

public record EmailAvailableResponseDTO(
    String email,
    Boolean available
) {
    public static EmailAvailableResponseDTO valueOf(String email, Boolean available){
        return new EmailAvailableResponseDTO(
            email, 
            available
        );
    }
}

package br.com.vygos.easychatapi.domain.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UsuarioInputDTO {

    private Long id;

    @NotBlank
    @NotNull
    private String email;

    @NotBlank
    @NotNull
    private String password;

    @Valid
    @NotNull
    private DadosPessoaisDTO dadosPessoais;
}

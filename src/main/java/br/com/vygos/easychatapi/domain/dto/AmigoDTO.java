package br.com.vygos.easychatapi.domain.dto;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AmigoDTO {

    private Long id;

    @NotBlank
    @NotNull
    private String email;

    private DadosPessoaisDTO dadosPessoais;
}

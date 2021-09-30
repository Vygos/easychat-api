package br.com.vygos.easychatapi.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class DadosPessoaisDTO {

    private Long id;

    @NotBlank
    @NotNull
    private String nome;

    @NotBlank
    @NotNull
    private String username;

    private LocalDateTime dtCadastro;

    private LocalDate dtNascimento;

    private String foto;
}

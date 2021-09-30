package br.com.vygos.easychatapi.domain.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UsuarioConversaDTO {

    private Long id;

    @NotBlank
    @NotNull
    private String email;

    private DadosPessoaisDTO dadosPessoais;
}

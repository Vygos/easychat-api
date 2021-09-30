package br.com.vygos.easychatapi.domain.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UsuarioDTO {

    private Long id;

    @NotBlank
    @NotNull
    private String email;

    List<AmigoDTO> amigos = new ArrayList<>();

    private DadosPessoaisDTO dadosPessoais;
}

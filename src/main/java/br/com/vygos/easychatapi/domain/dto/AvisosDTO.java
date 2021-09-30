package br.com.vygos.easychatapi.domain.dto;

import br.com.vygos.easychatapi.domain.enums.TipoAviso;
import com.sun.istack.NotNull;
import lombok.Data;

import javax.validation.Valid;

@Data
public class AvisosDTO {

    private Long id;

    @NotNull
    private TipoAviso tipo;

    @NotNull
    private String descricao;


    private boolean visto;

    @Valid
    @NotNull
    private UsuarioOutputDTO usuario;

    @Valid
    @NotNull
    private UsuarioOutputDTO contato;
}

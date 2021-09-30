package br.com.vygos.easychatapi.controller;

import br.com.vygos.easychatapi.domain.dto.DadosPessoaisDTO;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UsuarioOutputDTO {

    private Long id;

    private String email;

    private DadosPessoaisDTO dadosPessoais;
}

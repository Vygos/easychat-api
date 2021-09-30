package br.com.vygos.easychatapi.domain.dto;

import br.com.vygos.easychatapi.domain.entity.File;
import br.com.vygos.easychatapi.domain.entity.Usuario;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
public class MensagemDTO {


    private Long id;

    private String conteudo;

    private LocalDateTime dtMensagem;

    private ConversaThinDTO conversa;

    private UsuarioDTO usuario;

    private List<File> file;
}

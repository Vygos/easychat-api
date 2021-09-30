package br.com.vygos.easychatapi.domain.dto;

import br.com.vygos.easychatapi.domain.entity.Mensagem;
import br.com.vygos.easychatapi.domain.entity.Usuario;
import lombok.Data;

import java.util.*;

@Data
public class ConversaDTO {

    private Long id;

    private String nome;

    private List<MensagemDTO> mensagens = new ArrayList<>();

    private Set<UsuarioConversaDTO> usuarios = new HashSet<>();

    private Integer badge = 0;

}

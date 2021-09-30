package br.com.vygos.easychatapi.domain.miscellaneous;

import br.com.vygos.easychatapi.domain.dto.ConversaThinDTO;
import br.com.vygos.easychatapi.domain.dto.MensagemDTO;
import br.com.vygos.easychatapi.domain.dto.UsuarioDTO;
import br.com.vygos.easychatapi.domain.entity.Mensagem;
import org.modelmapper.AbstractConverter;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class MensagemToMensagemDTOConverter extends AbstractConverter<Mensagem, MensagemDTO> {

    @Override
    protected MensagemDTO convert(Mensagem mensagem) {

        ConversaThinDTO conversa = ConversaThinDTO.builder()
                .id(mensagem.getConversa().getId())
                .build();

        UsuarioDTO usuarioDTO = new UsuarioDTO();

        BeanUtils.copyProperties(mensagem.getUsuario(), usuarioDTO);

        return MensagemDTO.builder()
                .id(mensagem.getId())
                .dtMensagem(mensagem.getDtMensagem())
                .conteudo(mensagem.getConteudo())
                .conversa(conversa)
                .usuario(usuarioDTO)
                .build();
    }
}

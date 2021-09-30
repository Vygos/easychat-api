package br.com.vygos.easychatapi.domain.miscellaneous;

import br.com.vygos.easychatapi.controller.UsuarioOutputDTO;
import br.com.vygos.easychatapi.domain.dto.DadosPessoaisDTO;
import br.com.vygos.easychatapi.domain.entity.Usuario;
import org.modelmapper.AbstractConverter;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class UsuarioToUsuarioOutputDTOConverter extends AbstractConverter<Usuario, UsuarioOutputDTO> {

    @Override
    protected UsuarioOutputDTO convert(Usuario usuario) {
        DadosPessoaisDTO dadosPessoaisDTO = new DadosPessoaisDTO();
        BeanUtils.copyProperties(usuario.getDadosPessoais(), dadosPessoaisDTO);
        return UsuarioOutputDTO.builder()
                .id(usuario.getId())
                .email(usuario.getEmail())
                .dadosPessoais(dadosPessoaisDTO)
                .build();
    }
}

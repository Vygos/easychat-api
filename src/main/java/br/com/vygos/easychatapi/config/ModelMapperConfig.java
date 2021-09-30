package br.com.vygos.easychatapi.config;

import br.com.vygos.easychatapi.domain.dto.ConversaDTO;
import br.com.vygos.easychatapi.domain.entity.Conversa;
import br.com.vygos.easychatapi.domain.miscellaneous.MensagemToMensagemDTOConverter;
import br.com.vygos.easychatapi.domain.miscellaneous.UsuarioToUsuarioOutputDTOConverter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class ModelMapperConfig {

    private final MensagemToMensagemDTOConverter mensagemDTOConverter;
    private final UsuarioToUsuarioOutputDTOConverter usuarioToUsuarioOutputDTOConverter;

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration().setFieldMatchingEnabled(true);
        modelMapper.getConfiguration().setCollectionsMergeEnabled(true);
        modelMapper.addConverter(mensagemDTOConverter);
        modelMapper.addConverter(usuarioToUsuarioOutputDTOConverter);


        modelMapper.typeMap(Conversa.class, ConversaDTO.class)
                .addMappings(mapper -> {
                    mapper.map(Conversa::getUsuarios, ConversaDTO::setUsuarios);
                    mapper.map(Conversa::getMensagens, ConversaDTO::setMensagens);
                    }
                );

        return modelMapper;
    }
}

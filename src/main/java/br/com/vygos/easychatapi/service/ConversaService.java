package br.com.vygos.easychatapi.service;

import br.com.vygos.easychatapi.domain.dto.ConversaDTO;
import br.com.vygos.easychatapi.domain.dto.UsuarioConversaDTO;
import br.com.vygos.easychatapi.domain.entity.Conversa;
import br.com.vygos.easychatapi.domain.entity.Mensagem;
import br.com.vygos.easychatapi.domain.entity.Usuario;
import br.com.vygos.easychatapi.handler.NegocioException;
import br.com.vygos.easychatapi.repository.ConversaRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Service
public class ConversaService {

    private final ConversaRepository conversaRepository;
    private final ModelMapper modelMapper;

    public Conversa iniciarConversa(Usuario usuario1, Usuario usuario2) {

        List<Usuario> usuarios = Arrays.asList(usuario1, usuario2);

        Conversa conversa = Conversa.builder().nome("CONVERSA")
                .usuarios(usuarios)
                .build();

        return conversaRepository.save(conversa);
    }

    public ConversaDTO findById(Long id) {
        Conversa conversa = conversaRepository.findById(id).orElseThrow(() -> new NegocioException(HttpStatus.NOT_FOUND));
        return modelMapper.map(conversa, ConversaDTO.class);
    }

    @Transactional(readOnly = true)
    public List<ConversaDTO> findAllByIdUsuario(Long id) {
        List<Conversa> conversas = conversaRepository.findAllConversasByIdUsuario(id);

        if (!conversas.isEmpty()) {
            removerUsuarioSender(conversas, id);
        }

        return modelMapper.map(conversas, new TypeToken<List<ConversaDTO>>() {
        }.getType());
    }

    private void removerUsuarioSender(List<Conversa> conversas, Long id) {
        for (Conversa conversa : conversas) {
            conversa.getUsuarios().removeIf(usuario -> usuario.getId().equals(id));
        }
    }

    @Transactional
    public Mensagem pushMensagem(Mensagem mensagem) {
        Conversa conversa = conversaRepository.findById(mensagem.getConversa().getId())
                .orElseThrow(() -> new NegocioException(HttpStatus.NOT_FOUND));

        mensagem.setConversa(conversa);
        conversa.getMensagens().add(mensagem);

        return mensagem;
    }
}

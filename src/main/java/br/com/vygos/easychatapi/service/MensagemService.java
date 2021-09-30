package br.com.vygos.easychatapi.service;

import br.com.vygos.easychatapi.domain.dto.MensagemDTO;
import br.com.vygos.easychatapi.domain.entity.Mensagem;
import br.com.vygos.easychatapi.domain.entity.Usuario;
import br.com.vygos.easychatapi.repository.MensagemRepository;
import br.com.vygos.easychatapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MensagemService {

    private final MensagemRepository mensagemRepository;
    private final ConversaService conversaService;
    private final UsuarioRepository usuarioRepository;
    private final ModelMapper mapper;

    @Transactional
    public MensagemDTO save(Mensagem mensagem) {
        Usuario usuario = usuarioRepository.findById(mensagem.getUsuario().getId()).get();
        mensagem.setUsuario(usuario);
        conversaService.pushMensagem(mensagem);

        return mapper.map(mensagem, MensagemDTO.class);
    }
}

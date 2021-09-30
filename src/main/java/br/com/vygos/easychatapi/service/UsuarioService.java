package br.com.vygos.easychatapi.service;

import br.com.vygos.easychatapi.domain.dto.UsuarioOutputDTO;
import br.com.vygos.easychatapi.domain.dto.AvisosDTO;
import br.com.vygos.easychatapi.domain.dto.ConversaDTO;
import br.com.vygos.easychatapi.domain.dto.UsuarioDTO;
import br.com.vygos.easychatapi.domain.entity.Avisos;
import br.com.vygos.easychatapi.domain.entity.Conversa;
import br.com.vygos.easychatapi.domain.entity.DadosPessoais;
import br.com.vygos.easychatapi.domain.entity.Usuario;
import br.com.vygos.easychatapi.handler.NegocioException;
import br.com.vygos.easychatapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ConversaService conversaService;
    private final ModelMapper modelMapper;
    private final AvisosService avisosService;

    private final SimpMessagingTemplate template;

    @Transactional
    public UsuarioDTO cadastrar(Usuario usuario) {
        Optional<Usuario> usuarioBD = usuarioRepository.findByEmail(usuario.getEmail());

        if (usuarioBD.isPresent()) {
            throw new NegocioException(HttpStatus.BAD_REQUEST, "Email já existente na base de dados.");
        }

        BCryptPasswordEncoder enconder = new BCryptPasswordEncoder();
        String newPassword = enconder.encode(usuario.getPassword());
        usuario.setPassword(newPassword);

        usuario.getDadosPessoais().setDtCadastro(LocalDateTime.now());

        return modelMapper.map(usuarioRepository.save(usuario), UsuarioDTO.class);
    }

    @Transactional
    public ConversaDTO atualizarAmigos(Long id, Usuario usuario) {
        Usuario usuarioAtualizar = usuarioRepository.findById(id).get();
        Usuario novoAmigo = usuarioRepository.findById(usuario.getId()).get();
        usuarioAtualizar.getAmigos().add(novoAmigo);
        novoAmigo.getAmigos().add(usuarioAtualizar);

        Conversa conversa = conversaService.iniciarConversa(usuarioAtualizar, novoAmigo);
        ConversaDTO conversaDTO = modelMapper.map(conversa, ConversaDTO.class);

        notificarNovaConversa(novoAmigo, conversaDTO);

        return conversaDTO;
    }

    private void notificarNovaConversa(Usuario novoAmigo, ConversaDTO conversaDTO) {
        this.template.convertAndSend(
                "/topic/conversa." + novoAmigo.getDadosPessoais().getUsername(),
                conversaDTO
        );
    }

    public Boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public List<ConversaDTO> listAllConversas(Long id) {
        usuarioRepository.findById(id)
                .orElseThrow(() -> new NegocioException(HttpStatus.NOT_FOUND, "Usuario não encontrado"));

        return conversaService.findAllByIdUsuario(id);
    }

    public List<UsuarioOutputDTO> search(Long id, String username) {
        Usuario usuario = Usuario.builder()
                .dadosPessoais(
                        DadosPessoais.builder().username(username).build()
                ).build();

        Example<Usuario> usuarioExample = Example.of(
                usuario,
                ExampleMatcher.matchingAll()
                        .withIgnoreNullValues()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
        );

        List<Usuario> result = usuarioRepository.findAll(usuarioExample);

        removerUsuarioPrincipal(result, id);
        removerContatosJaAdicionados(result, id);

        return modelMapper.map(result, new TypeToken<List<UsuarioOutputDTO>>() {
        }.getType());
    }

    private void removerContatosJaAdicionados(List<Usuario> result, Long id) {
        Usuario usuarioPrincipal = usuarioRepository.findById(id).orElseThrow(() -> new NegocioException(HttpStatus.NOT_FOUND));

        result.removeIf(user ->
                usuarioPrincipal.getAmigos()
                        .stream().anyMatch(usuario -> usuario.getId().equals(user.getId()))
        );
    }

    private void removerUsuarioPrincipal(List<Usuario> result, Long id) {
        result.removeIf(usuario -> usuario.getId().equals(id));
    }

    public Usuario findById(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new NegocioException(HttpStatus.NOT_FOUND));

        return usuario;
    }

    @Transactional(readOnly = true)
    public List<AvisosDTO> listAllAvisosByIdUsuario(Long id) {
        List<Avisos> avisos = avisosService.listAllByIdUsuario(id);

        return modelMapper.map(avisos, new TypeToken<List<AvisosDTO>>() {
        }.getType());
    }
}

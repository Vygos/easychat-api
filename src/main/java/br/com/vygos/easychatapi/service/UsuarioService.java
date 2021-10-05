package br.com.vygos.easychatapi.service;

import br.com.vygos.easychatapi.EasyChatProperties;
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
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ConversaService conversaService;
    private final ModelMapper modelMapper;
    private final AvisosService avisosService;
    private final AmazonS3 amazonS3;
    private final EasyChatProperties easyChatProperties;

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
    public UsuarioOutputDTO atualizar(Long id, Usuario usuario) {

        if (!id.equals(usuario.getId())) {
            throw new NegocioException(HttpStatus.BAD_REQUEST, "ID's divergentes");
        }

        Usuario usuarioBD = usuarioRepository.findById(id)
                .orElseThrow(() -> new NegocioException(HttpStatus.NOT_FOUND));

        usuario.setPassword(usuarioBD.getPassword());
        usuario.getDadosPessoais().setDtCadastro(usuarioBD.getDadosPessoais().getDtCadastro());

        Usuario updatedUsuario = usuarioRepository.save(usuario);

        return modelMapper.map(updatedUsuario, UsuarioOutputDTO.class);
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
        usuario.getDadosPessoais().setFoto(recuperarFoto(usuario.getDadosPessoais().getFoto()));

        return usuario;
    }

    @Transactional(readOnly = true)
    public List<AvisosDTO> listAllAvisosByIdUsuario(Long id) {
        List<Avisos> avisos = avisosService.listAllByIdUsuario(id);

        return modelMapper.map(avisos, new TypeToken<List<AvisosDTO>>() {
        }.getType());
    }

    @Transactional
    public String upload(Long id, MultipartFile file) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new NegocioException(HttpStatus.NOT_FOUND));
        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            String hashName = DigestUtils.md5Hex(file.getOriginalFilename());
            usuario.getDadosPessoais().setFoto(hashName);
            this.amazonS3.putObject(
                    easyChatProperties.getAws().getBucketName(),
                    hashName,
                    file.getInputStream(),
                    objectMetadata);

            return hashName;
        } catch (SdkClientException | IOException e) {
            throw new NegocioException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    private String recuperarFoto(String foto) {
        if (foto == null) {
            return null;
        }

        S3Object object = amazonS3.getObject(easyChatProperties.getAws().getBucketName(), foto);
        S3ObjectInputStream objectContent = object.getObjectContent();

        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            objectContent.transferTo(bytes);

            String base64 = Base64.getEncoder().encodeToString(bytes.toByteArray());
            return "data:image/jpeg;base64," + base64;
        } catch (IOException e) {
            throw new NegocioException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}

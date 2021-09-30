package br.com.vygos.easychatapi.controller;

import br.com.vygos.easychatapi.domain.dto.*;
import br.com.vygos.easychatapi.domain.entity.Usuario;
import br.com.vygos.easychatapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("usuario")
public class UsuarioController {

    private final UsuarioService service;
    private final ModelMapper mapper;


    @GetMapping("{id}")
    public ResponseEntity<UsuarioOutputDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.map(service.findById(id), UsuarioOutputDTO.class));
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> cadastrar(@RequestBody @Valid UsuarioInputDTO usuario) {
        return ResponseEntity.ok(service.cadastrar(mapper.map(usuario, Usuario.class)));
    }

    @PostMapping("{id}/confirmar-amizade")
    public ResponseEntity<ConversaDTO> novoAmigo(@PathVariable Long id, @RequestBody @Valid UsuarioDTO novoAmigo) {
        return ResponseEntity.ok(service.atualizarAmigos(id, mapper.map(novoAmigo, Usuario.class)));
    }

    @GetMapping("existsByEmail")
    public ResponseEntity<Boolean> existsByEmail(@RequestParam String email) {
        return ResponseEntity.ok(service.existsByEmail(email));
    }

    @GetMapping("{id}/conversas")
    public ResponseEntity<List<ConversaDTO>> listAllConversas(@PathVariable Long id) {
        return ResponseEntity.ok(service.listAllConversas(id));
    }

    @GetMapping("{id}/search")
    public ResponseEntity<List<UsuarioOutputDTO>> search(@PathVariable Long id, @RequestParam String username) {
        return ResponseEntity.ok(service.search(id, username));
    }

    @GetMapping("{id}/avisos")
    public ResponseEntity<List<AvisosDTO>> listAll(@PathVariable Long id) {
        return ResponseEntity.ok(service.listAllAvisosByIdUsuario(id));
    }

}

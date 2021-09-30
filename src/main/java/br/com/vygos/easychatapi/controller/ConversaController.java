package br.com.vygos.easychatapi.controller;

import br.com.vygos.easychatapi.domain.dto.ConversaDTO;
import br.com.vygos.easychatapi.service.ConversaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("conversa")
public class ConversaController {

    private final ConversaService conversaService;

    @GetMapping
    public ResponseEntity<ConversaDTO> listAll(@PathVariable Long id) {
        return ResponseEntity.ok(conversaService.findById(id));
    }
}

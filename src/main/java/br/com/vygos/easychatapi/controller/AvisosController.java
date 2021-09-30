package br.com.vygos.easychatapi.controller;

import br.com.vygos.easychatapi.domain.dto.AvisosDTO;
import br.com.vygos.easychatapi.domain.entity.Avisos;
import br.com.vygos.easychatapi.service.AvisosService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("avisos")
public class AvisosController {

    private final AvisosService avisosService;
    private final ModelMapper mapper;

    @PostMapping
    public ResponseEntity<AvisosDTO> salvar(@RequestBody @Valid AvisosDTO aviso){
        return ResponseEntity.ok(avisosService.save(mapper.map(aviso, Avisos.class)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        avisosService.delete(id);
        return ResponseEntity.ok().build();
    }
}

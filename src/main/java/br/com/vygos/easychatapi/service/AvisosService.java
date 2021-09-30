package br.com.vygos.easychatapi.service;

import br.com.vygos.easychatapi.domain.dto.AvisosDTO;
import br.com.vygos.easychatapi.domain.entity.Avisos;
import br.com.vygos.easychatapi.repository.AvisosRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AvisosService {
    private final AvisosRepository avisosRepository;
    private final ModelMapper mapper;
    private final SimpMessagingTemplate template;

    @Transactional
    public AvisosDTO save(Avisos avisos) {
        AvisosDTO avisosDTO = mapper.map(avisosRepository.save(avisos), AvisosDTO.class);
        notificarUsuario(avisos);

        return avisosDTO;
    }

    private void notificarUsuario(Avisos avisos) {
        template.convertAndSend("/topic/avisos." + avisos.getUsuario().getDadosPessoais().getUsername(), avisos);

    }

    public List<Avisos> listAllByIdUsuario(Long id) {
        return avisosRepository.findAllByUsuarioId(id);
    }

    @Transactional
    public void delete(Long id) {
        avisosRepository.deleteById(id);
    }
}

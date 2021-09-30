package br.com.vygos.easychatapi.controller;

import br.com.vygos.easychatapi.domain.dto.MensagemDTO;
import br.com.vygos.easychatapi.domain.entity.Mensagem;
import br.com.vygos.easychatapi.service.MensagemService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller()
public class ChatController {

    private final MensagemService mensagemService;

    @MessageMapping("/chat.{user}")
    @SendTo("/topic/chat.{user}")
    public MensagemDTO salvarMensagem(@Payload Mensagem mensagem, @DestinationVariable String user) {
        return mensagemService.save(mensagem);
    }
}

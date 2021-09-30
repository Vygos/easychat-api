package br.com.vygos.easychatapi.repository;

import br.com.vygos.easychatapi.domain.entity.Mensagem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MensagemRepository extends JpaRepository<Mensagem, Long> {
}

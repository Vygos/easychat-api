package br.com.vygos.easychatapi.repository;

import br.com.vygos.easychatapi.domain.entity.Conversa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConversaRepository extends JpaRepository<Conversa, Long> {

    @Query("SELECT con FROM Conversa con JOIN con.usuarios usu WHERE usu.id = :id")
    List<Conversa> findAllConversasByIdUsuario(Long id);
}

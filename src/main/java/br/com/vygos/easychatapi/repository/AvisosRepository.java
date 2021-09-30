package br.com.vygos.easychatapi.repository;

import br.com.vygos.easychatapi.domain.entity.Avisos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvisosRepository extends JpaRepository<Avisos, Long> {

    List<Avisos> findAllByUsuarioId(Long id);
}

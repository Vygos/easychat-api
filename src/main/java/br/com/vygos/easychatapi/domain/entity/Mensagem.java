package br.com.vygos.easychatapi.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Data
@Entity
@Table(name = "MENSAGEM", schema = "EASYCHAT")
public class Mensagem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MENSAGEM_SEQ")
    @SequenceGenerator(name = "MENSAGEM_SEQ", sequenceName = "MENSAGEM_SEQ", allocationSize = 1)
    @Column(name = "ID_MENSAGEM")
    private Long id;

    @Column(name = "CONTEUDO")
    private String conteudo;

    @Column(name = "DT_MENSAGEM")
    private LocalDateTime dtMensagem;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {PERSIST, MERGE})
    @JoinColumn(name = "ID_CONVERSA")
    private Conversa conversa;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {PERSIST, MERGE})
    @JoinColumn(name = "ID_USUARIO_ENVIO")
    private Usuario usuario;

    @OneToMany(mappedBy = "mensagem")
    private List<File> file;
}

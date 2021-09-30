package br.com.vygos.easychatapi.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "CONVERSA", schema = "EASYCHAT")
public class Conversa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONVERSA_SEQ")
    @SequenceGenerator(name = "CONVERSA_SEQ", sequenceName = "CONVERSA_SEQ", allocationSize = 1)
    @Column(name = "ID_CONVERSA")
    private Long id;

    @Column(name = "NOME")
    private String nome;

    @OneToMany(mappedBy = "conversa", cascade = {PERSIST})
    private List<Mensagem> mensagens = new ArrayList<>();

    @ManyToMany(cascade = {PERSIST, MERGE})
    @JoinTable(name = "USUARIO_CONVERSA",
            joinColumns = @JoinColumn(name = "ID_CONVERSA"),
            inverseJoinColumns = @JoinColumn(name = "ID_USUARIO")
    )
    private List<Usuario> usuarios = new ArrayList<>();
}

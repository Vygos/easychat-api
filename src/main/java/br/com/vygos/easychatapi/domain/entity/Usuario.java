package br.com.vygos.easychatapi.domain.entity;

import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "USUARIO", schema = "EASYCHAT")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario {


    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USUARIO_SEQ")
    @SequenceGenerator(name = "USUARIO_SEQ", sequenceName = "USUARIO_SEQ", allocationSize = 1, schema = "EASYCHAT")
    @Column(name = "ID_USUARIO")
    private Long id;

    @Column(name = "EMAIL", unique = true)
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @OneToOne(cascade = {PERSIST, MERGE})
    @JoinColumn(name = "ID_DADOS_PESSOAIS")
    private DadosPessoais dadosPessoais;

    @ManyToMany(cascade = {PERSIST, MERGE})
    @JoinTable(name = "AMIGO_USUARIO",
        joinColumns = @JoinColumn(name = "ID_USUARIO_AMIGO"),
        inverseJoinColumns = @JoinColumn(name = "ID_USUARIO")
    )
    private Set<Usuario> amigos = new HashSet<>();


}

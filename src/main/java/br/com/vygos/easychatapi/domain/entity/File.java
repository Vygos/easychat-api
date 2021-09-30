package br.com.vygos.easychatapi.domain.entity;

import lombok.Data;

import javax.persistence.*;

import static javax.persistence.CascadeType.PERSIST;

@Data
@Entity
@Table(name = "FILE", schema = "EASYCHAT")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FILE_SEQ")
    @SequenceGenerator(name = "FILE_SEQ", sequenceName = "FILE_SEQ", allocationSize = 1)
    @Column(name = "ID_FILE")
    private Long id;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "OBJETO")
    private String objeto;

    @ManyToOne(cascade = {PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_MENSAGEM")
    private Mensagem mensagem;
}

package br.com.vygos.easychatapi.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "DADOS_PESSOAIS", schema = "EASYCHAT")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DadosPessoais {

    @Id
    @EqualsAndHashCode.Include
    @SequenceGenerator(name = "DADOS_PESSOAIS_SEQ", sequenceName = "DADOS_PESSOAIS_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DADOS_PESSOAIS_SEQ")
    @Column(name = "ID_DADOS_PESSOAIS")
    private Long id;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "USERNAME", unique = true)
    private String username;

    @Column(name = "DT_CADASTRO")
    private LocalDateTime dtCadastro;

    @Column(name = "DT_NASCIMENTO")
    private LocalDate dtNascimento;

    @Column(name = "FOTO")
    private String foto;

}

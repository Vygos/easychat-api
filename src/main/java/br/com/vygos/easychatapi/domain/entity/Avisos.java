package br.com.vygos.easychatapi.domain.entity;

import br.com.vygos.easychatapi.domain.converters.TipoAvisoConverter;
import br.com.vygos.easychatapi.domain.enums.TipoAviso;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "AVISOS", schema = "easychat")
public class Avisos {

    @Id
    @Column(name = "ID_AVISOS")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AVISOS_SEQ")
    @SequenceGenerator(name = "AVISO_SEQ", sequenceName = "AVISOS_SEQ", allocationSize = 1, schema = "EASYCHAT")
    private Long id;

    @Column(name = "TIPO")
    @Convert(converter = TipoAvisoConverter.class)
    private TipoAviso tipo;

    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "VISTO")
    private boolean visto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USUARIO")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CONTATO")
    private Usuario contato;

}

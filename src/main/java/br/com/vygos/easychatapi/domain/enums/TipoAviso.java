package br.com.vygos.easychatapi.domain.enums;


import java.util.Arrays;

public enum TipoAviso {
    PEDIDO_AMIZADE(1),
    CONVERSA(2);

    TipoAviso(Integer id) {
        this.id = id;
    }

    private final Integer id;

    public Integer getId() {
        return id;
    }

    public static TipoAviso fromId(Integer id) {
        return Arrays.asList(TipoAviso.values())
                .stream().filter(tipoAviso -> tipoAviso.getId().equals(id))
                .findFirst().get();
    }
}

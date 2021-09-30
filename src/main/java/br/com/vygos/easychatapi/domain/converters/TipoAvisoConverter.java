package br.com.vygos.easychatapi.domain.converters;

import br.com.vygos.easychatapi.domain.enums.TipoAviso;

import javax.persistence.AttributeConverter;

public class TipoAvisoConverter implements AttributeConverter<TipoAviso, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoAviso tipoAviso) {
        return tipoAviso.getId();
    }

    @Override
    public TipoAviso convertToEntityAttribute(Integer id) {
        return TipoAviso.fromId(id);
    }
}

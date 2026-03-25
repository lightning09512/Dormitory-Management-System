package com.ktx.model.converter;

import com.ktx.model.enums.TinhTrangTB;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TinhTrangTBConverter implements AttributeConverter<TinhTrangTB, String> {

    @Override
    public String convertToDatabaseColumn(TinhTrangTB attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getDescription();
    }

    @Override
    public TinhTrangTB convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        for (TinhTrangTB t : TinhTrangTB.values()) {
            if (t.getDescription().equals(dbData)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Unknown database value:" + dbData);
    }
}

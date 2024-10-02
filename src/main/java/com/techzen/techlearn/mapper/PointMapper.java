package com.techzen.techlearn.mapper;

import com.techzen.techlearn.dto.request.PointRequestDTO;
import com.techzen.techlearn.dto.response.PointResponseDTO;
import com.techzen.techlearn.entity.CurrencyEntity;
import com.techzen.techlearn.entity.PointEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PointMapper {

    @Mapping(source = "idCurrency", target = "currency.id")
    PointEntity toPointEntity(PointRequestDTO dto);

    @Mapping(source = "currency.id", target = "idCurrency")
    PointResponseDTO toPointResponseDTO(PointEntity point);

    default Integer mapCurrencyToId(CurrencyEntity currency) {
        return currency != null ? currency.getId() : null;
    }

    default CurrencyEntity mapIdToCurrency(Integer idCurrency) {
        if (idCurrency == null) {
            return null;
        }
        CurrencyEntity currency = new CurrencyEntity();
        currency.setId(idCurrency);
        return currency;
    }
}


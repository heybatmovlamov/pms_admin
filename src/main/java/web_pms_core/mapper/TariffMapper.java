package web_pms_core.mapper;


import org.mapstruct.*;
import web_pms_core.dao.entity.TariffsEntity;
import web_pms_core.model.request.tariff.TariffRequest;
import web_pms_core.model.response.TariffResponse;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = CONSTRUCTOR,
        unmappedSourcePolicy = IGNORE,
        unmappedTargetPolicy = IGNORE)
public interface TariffMapper extends  EntityMapper<TariffRequest, TariffResponse, TariffsEntity>{


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(
            TariffRequest request,
            @MappingTarget TariffsEntity entity
    );
}

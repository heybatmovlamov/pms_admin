package pms_core.mapper;


import org.mapstruct.*;
import pms_core.dao.entity.ParkingsEntity;
import pms_core.dao.entity.TariffsEntity;
import pms_core.model.request.ParkingRequest;
import pms_core.model.request.tariff.TariffRequest;
import pms_core.model.response.TariffResponse;

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

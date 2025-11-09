package pms_core.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import pms_core.dao.entity.SpacesEntity;
import pms_core.dao.entity.TariffsEntity;
import pms_core.model.request.SpaceRequest;
import pms_core.model.request.TariffRequest;
import pms_core.model.response.SpaceResponse;
import pms_core.model.response.TariffResponse;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = CONSTRUCTOR,
        unmappedSourcePolicy = IGNORE,
        unmappedTargetPolicy = IGNORE)
public interface TariffMapper extends  EntityMapper<TariffRequest, TariffResponse, TariffsEntity>{

}

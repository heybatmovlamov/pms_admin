package pms_core.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import pms_core.dao.entity.VehiclesEntity;
import pms_core.model.request.vehicle.VehicleRequest;
import pms_core.model.response.VehicleResponse;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = CONSTRUCTOR,
        unmappedSourcePolicy = IGNORE,
        unmappedTargetPolicy = IGNORE)
public interface VehicleMapper extends  EntityMapper<VehicleRequest, VehicleResponse, VehiclesEntity>{

}

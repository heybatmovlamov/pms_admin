package pms_core.mapper;


import org.mapstruct.*;
import pms_core.dao.entity.ParkingsEntity;
import pms_core.model.request.ParkingRequest;
import pms_core.model.response.ParkingResponse;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = CONSTRUCTOR,
        unmappedSourcePolicy = IGNORE,
        unmappedTargetPolicy = IGNORE)
public interface ParkingMapper extends  EntityMapper<ParkingRequest, ParkingResponse, ParkingsEntity>{

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(
            ParkingRequest request,
            @MappingTarget ParkingsEntity entity
    );
}

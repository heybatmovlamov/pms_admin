package pms_core.mapper;


import org.mapstruct.*;
import pms_core.dao.entity.OrganizationsEntity;
import pms_core.dao.entity.ParkingsEntity;
import pms_core.dao.entity.SpacesEntity;
import pms_core.model.request.OrganizationRequest;
import pms_core.model.request.ParkingRequest;
import pms_core.model.request.SpaceRequest;
import pms_core.model.response.ParkingResponse;
import pms_core.model.response.SpaceResponse;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = CONSTRUCTOR,
        unmappedSourcePolicy = IGNORE,
        unmappedTargetPolicy = IGNORE)
public interface SpaceMapper extends  EntityMapper<SpaceRequest, SpaceResponse, SpacesEntity>{

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(
            SpaceRequest request,
            @MappingTarget SpacesEntity entity
    );
}

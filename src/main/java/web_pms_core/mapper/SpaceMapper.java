package web_pms_core.mapper;


import org.mapstruct.*;
import web_pms_core.dao.entity.SpacesEntity;
import web_pms_core.model.request.SpaceRequest;
import web_pms_core.model.response.SpaceResponse;

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

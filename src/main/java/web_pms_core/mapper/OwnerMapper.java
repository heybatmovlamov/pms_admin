package web_pms_core.mapper;


import org.mapstruct.*;
import web_pms_core.dao.entity.OwnersEntity;
import web_pms_core.model.request.OwnerRequest;
import web_pms_core.model.response.OwnerResponse;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = CONSTRUCTOR,
        unmappedSourcePolicy = IGNORE,
        unmappedTargetPolicy = IGNORE)
public interface OwnerMapper extends  EntityMapper<OwnerRequest, OwnerResponse, OwnersEntity>{

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(
            OwnerRequest request,
            @MappingTarget OwnersEntity entity
    );
}

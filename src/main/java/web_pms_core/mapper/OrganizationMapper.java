package web_pms_core.mapper;


import org.mapstruct.*;
import web_pms_core.dao.entity.OrganizationsEntity;
import web_pms_core.model.request.OrganizationRequest;
import web_pms_core.model.response.OrganizationResponse;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = CONSTRUCTOR,
        unmappedSourcePolicy = IGNORE,
        unmappedTargetPolicy = IGNORE)
public interface OrganizationMapper extends EntityMapper<OrganizationRequest, OrganizationResponse, OrganizationsEntity> {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(
            OrganizationRequest request,
            @MappingTarget OrganizationsEntity entity
    );
}

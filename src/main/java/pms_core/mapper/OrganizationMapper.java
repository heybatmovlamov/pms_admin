package pms_core.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import pms_core.dao.entity.CamerasEntity;
import pms_core.dao.entity.OrganizationsEntity;
import pms_core.model.request.CameraRequest;
import pms_core.model.request.OrganizationRequest;
import pms_core.model.response.CameraResponse;
import pms_core.model.response.OrganizationResponse;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = CONSTRUCTOR,
        unmappedSourcePolicy = IGNORE,
        unmappedTargetPolicy = IGNORE)
public interface OrganizationMapper extends  EntityMapper<OrganizationRequest, OrganizationResponse, OrganizationsEntity>{

}

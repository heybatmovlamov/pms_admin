package web_pms_core.mapper;


import org.mapstruct.*;
import web_pms_core.dao.entity.UsersEntity;
import web_pms_core.model.request.UserRequest;
import web_pms_core.model.response.UserResponse;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = CONSTRUCTOR,
        unmappedSourcePolicy = IGNORE,
        unmappedTargetPolicy = IGNORE)
public interface UserMapper extends  EntityMapper<UserRequest, UserResponse, UsersEntity>{

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(
            UserRequest request,
            @MappingTarget UsersEntity entity
    );
}

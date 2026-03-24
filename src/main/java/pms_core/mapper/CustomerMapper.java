package pms_core.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import pms_core.dao.entity.CustomersEntity;
import pms_core.model.request.CustomerRequest;
import pms_core.model.response.CustomerResponse;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = IGNORE)
public interface CustomerMapper extends EntityMapper<CustomerRequest, CustomerResponse, CustomersEntity> {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(CustomerRequest request, @MappingTarget CustomersEntity entity);
}

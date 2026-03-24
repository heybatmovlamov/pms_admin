package pms_core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import pms_core.dao.entity.TransactionsEntity;
import pms_core.model.response.TransactionResponse;

import java.util.List;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = IGNORE)
public interface TransactionMapper {

    TransactionResponse toResponse(TransactionsEntity entity);

    List<TransactionResponse> toResponse(List<TransactionsEntity> entities);
}

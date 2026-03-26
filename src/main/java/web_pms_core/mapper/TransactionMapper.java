package web_pms_core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import web_pms_core.dao.entity.TransactionsEntity;
import web_pms_core.model.response.TransactionResponse;

import java.util.List;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = IGNORE)
public interface TransactionMapper {

    TransactionResponse toResponse(TransactionsEntity entity);

    List<TransactionResponse> toResponse(List<TransactionsEntity> entities);
}

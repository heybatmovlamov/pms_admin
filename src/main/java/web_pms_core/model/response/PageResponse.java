package web_pms_core.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import web_pms_core.dao.entity.TransactionsEntity;

import java.util.List;

@Data
@AllArgsConstructor
public class PageResponse {

    List<TransactionsEntity> entities;
    Long total;
}

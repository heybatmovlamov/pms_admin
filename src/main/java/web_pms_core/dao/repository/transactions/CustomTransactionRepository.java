package web_pms_core.dao.repository.transactions;

import web_pms_core.model.request.TransactionListRequest;
import web_pms_core.model.response.PageResponse;

public interface CustomTransactionRepository {

      PageResponse findAllSearching(TransactionListRequest request);
}

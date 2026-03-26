package pms_core.dao.repository.transactions;

import pms_core.model.request.TransactionListRequest;
import pms_core.model.response.PageResponse;

public interface CustomTransactionRepository {

      PageResponse findAllSearching(TransactionListRequest request);
}

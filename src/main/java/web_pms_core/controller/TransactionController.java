package web_pms_core.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web_pms_core.model.request.TransactionListRequest;
import web_pms_core.model.response.TransactionResponse;
import web_pms_core.service.TransactionsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionsService transactionsService;

    @PostMapping("/list")
    public ResponseEntity<Page<TransactionResponse>> list(@RequestBody(required = false) TransactionListRequest request) {
        return ResponseEntity.ok(transactionsService.findAll(request));
    }

    @GetMapping("/closing-day")
    public ResponseEntity<List<TransactionResponse>> closingDay() {
        return ResponseEntity.ok(transactionsService.findEndDayOfTransactions());
    }
}

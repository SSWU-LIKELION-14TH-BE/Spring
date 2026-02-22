package com.example.project.paging_transaction.controller;

import com.example.project.paging_transaction.service.PlainService;
import com.example.project.paging_transaction.service.TransactionalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TransactionTestController {
    private final PlainService plainService;
    private final TransactionalService transactionalService;
    @PostMapping("/no-tx")
    public void noTransaction(@RequestParam Long memberId) {
        plainService.saveWithoutTransaction(memberId);
    }
    @PostMapping("/with-tx")
    public void withTransaction(@RequestParam Long memberId) {
        transactionalService.saveWithTransaction(memberId);
    }
}

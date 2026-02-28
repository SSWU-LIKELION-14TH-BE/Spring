package com.likelion.session.controller;

import com.likelion.session.dto.CalculatorAddRequest;
import com.likelion.session.dto.CalculatorMultiplyRequest;
import com.likelion.session.service.CalculatorService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calculator")
public class CalculatorController {

    private final CalculatorService calculatorService;

    // 생성자 주입 (Constructor Injection)
    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    /**
     * ⚡GET 방식으로 두 숫자의 합을 구하는 API
     *  요청 : /api/calculator/add?number1=5&number2=10
     */
    @GetMapping("/add")
    public int addTwoNumbers(CalculatorAddRequest request) {
        return calculatorService.add(request.getNumber1(), request.getNumber2());
    }

    /**
     * ⚡POST 방식으로 두 숫자의 곱을 구하는 API
     *  요청 : POST /api/calculator/multiply
     */
    @PostMapping("/multiply")
    public int multiplyTwoNumbers(@RequestBody CalculatorMultiplyRequest request) {
        return calculatorService.multiply(request.getNumber1(), request.getNumber2());
    }
}

package com.likelion.session.controller;

import com.likelion.session.dto.CalculatorAddRequest;
import com.likelion.session.dto.CalculatorMultiplyRequest;
import org.springframework.web.bind.annotation.*;

@RestController
public class CalculatorController {

    /**
     * ⚡GET 방식으로 두 숫자의 합을 구하는 API(+ DTO)
     *  요청 : /add?number1=5&number2=10
     *  응답 : 15
     * ⚡@RequestParam 대신 DTO를 사용해 객체 바인딩
     */

    @GetMapping("/add")
    public int addTwoNumbers(CalculatorAddRequest request) {
        return request.getNumber1() + request.getNumber2();
    }

    /**
     * ⚡POST 방식으로 두 숫자의 곱을 구하는 API
     *  요청 : POST /multiply
     *  요청 바디 : { "number1" : 3, "number2" : 4 }
     *  응답 : 12
     */

    @PostMapping("/multiply")
    public int multiplyTwoNumbers(
            @RequestBody CalculatorMultiplyRequest request
    ) {
        return request.getNumber1() * request.getNumber2();
    }
}

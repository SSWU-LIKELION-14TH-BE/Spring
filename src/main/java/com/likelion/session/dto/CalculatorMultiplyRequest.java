package com.likelion.session.dto;

/**
 * ⚡CalculatorAddRequest (DTO)
 *  POST 요청에서 두 개의 숫자를 받기 위한 데이터 전송 객체
 */

public class CalculatorMultiplyRequest {
    private final int number1;  // 첫 번째 숫자
    private final int number2;  // 두 번째 숫자

    // 생성자
    public CalculatorMultiplyRequest(int number1, int number2)
    {
        this.number1 = number1;
        this.number2 = number2;
    }

    // Getter 메서드
    public int getNumber1() {
        return number1;
    }

    public int getNumber2() {
        return number2;
    }
}
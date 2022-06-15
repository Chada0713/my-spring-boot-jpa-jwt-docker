package com.example.devopsjava.springboot.project;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DevOpsJavaSpringProjectTests {

    Calculator calculatorTest = new Calculator();

    @Test
    void addNumberTest(){
        int result = calculatorTest.add(2, 1);
        assertThat(result).isEqualTo(3);
    }

    class Calculator{
        int add(int a, int b) {return a + b;};
    }
}

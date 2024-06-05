package com.example.atividades.atividade13;

import org.junit.Test;
import static org.junit.Assert.*;

public class FactorialTest {

    @Test
    public void testCalculate_withZero() {
        Factorial factorial = new Factorial();
        int result = factorial.calculate(0);
        assertEquals(1, result);
    }

    @Test
    public void testCalculate_withPositiveNumber() {
        Factorial factorial = new Factorial();
        int result = factorial.calculate(5);
        assertEquals(120, result);
    }

    @Test
    public void testCalculate_withOne() {
        Factorial factorial = new Factorial();
        int result = factorial.calculate(1);
        assertEquals(1, result);
    }

    @Test
    public void testCalculate_withLargeNumber() {
        Factorial factorial = new Factorial();
        int result = factorial.calculate(10);
        assertEquals(3628800, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculate_withNegativeNumber() {
        Factorial factorial = new Factorial();
        factorial.calculate(-1);
    }
}

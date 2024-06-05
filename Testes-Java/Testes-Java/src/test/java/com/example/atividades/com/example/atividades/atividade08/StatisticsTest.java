package com.example.atividades.atividade08;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StatisticsTest {

    @Test
    public void testCalculateAverage_withValidNumbers() {
        Statistics statistics = new Statistics();
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        
        double result = statistics.calculateAverage(numbers);
        
        assertEquals(3.0, result, 0.0001);
    }
    
    @Test
    public void testCalculateAverage_withNegativeNumbers() {
        Statistics statistics = new Statistics();
        List<Integer> numbers = Arrays.asList(-1, -2, -3, -4, -5);
        
        double result = statistics.calculateAverage(numbers);
        
        assertEquals(-3.0, result, 0.0001);
    }
    
    @Test
    public void testCalculateAverage_withMixedNumbers() {
        Statistics statistics = new Statistics();
        List<Integer> numbers = Arrays.asList(-1, 2, -3, 4, -5);
        
        double result = statistics.calculateAverage(numbers);
        
        assertEquals(-0.6, result, 0.0001);
    }

    @Test
    public void testCalculateAverage_withSingleNumber() {
        Statistics statistics = new Statistics();
        List<Integer> numbers = Collections.singletonList(10);
        
        double result = statistics.calculateAverage(numbers);
        
        assertEquals(10.0, result, 0.0001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateAverage_withEmptyList() {
        Statistics statistics = new Statistics();
        List<Integer> numbers = Collections.emptyList();
        
        statistics.calculateAverage(numbers);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateAverage_withNullList() {
        Statistics statistics = new Statistics();
        
        statistics.calculateAverage(null);
    }
}

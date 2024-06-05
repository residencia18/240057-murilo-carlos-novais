package com.example.atividades.atividade11;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CustomSortTest {

    @Test
    public void testCustomSort_withUnsortedList() {
        CustomSort customSort = new CustomSort();
        List<Integer> numbers = Arrays.asList(3, 1, 4, 1, 5, 9);
        List<Integer> sortedNumbers = customSort.customSort(numbers);
        
        List<Integer> expectedNumbers = Arrays.asList(9, 5, 4, 3, 1, 1);
        assertEquals(expectedNumbers, sortedNumbers);
    }

    @Test
    public void testCustomSort_withSortedList() {
        CustomSort customSort = new CustomSort();
        List<Integer> numbers = Arrays.asList(9, 5, 4, 3, 1, 1);
        List<Integer> sortedNumbers = customSort.customSort(numbers);
        
        List<Integer> expectedNumbers = Arrays.asList(9, 5, 4, 3, 1, 1);
        assertEquals(expectedNumbers, sortedNumbers);
    }

    @Test
    public void testCustomSort_withEmptyList() {
        CustomSort customSort = new CustomSort();
        List<Integer> numbers = Collections.emptyList();
        List<Integer> sortedNumbers = customSort.customSort(numbers);
        
        List<Integer> expectedNumbers = Collections.emptyList();
        assertEquals(expectedNumbers, sortedNumbers);
    }

    @Test
    public void testCustomSort_withSingleElementList() {
        CustomSort customSort = new CustomSort();
        List<Integer> numbers = Collections.singletonList(42);
        List<Integer> sortedNumbers = customSort.customSort(numbers);
        
        List<Integer> expectedNumbers = Collections.singletonList(42);
        assertEquals(expectedNumbers, sortedNumbers);
    }
}

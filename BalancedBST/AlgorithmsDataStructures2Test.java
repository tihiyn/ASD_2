package ru.hsp.Lesson_5;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class AlgorithmsDataStructures2Test {
    @Test
    void testGenerateBBSTArrayWithEmptyArray() {
        int[] inputArray = new int[0];
        assertArrayEquals(new int[0], AlgorithmsDataStructures2.GenerateBBSTArray(inputArray));
    }

    @Test
    void testGenerateBBSTArrayWithSingleElement() {
        int[] inputArray = new int[] {7};
        assertArrayEquals(new int[] {7}, AlgorithmsDataStructures2.GenerateBBSTArray(inputArray));
    }

    @Test
    void testGenerateBBSTArray() {
        int[] inputArray = new int[] {11, 7, 18, 4, 9, 15, 21, 1, 5, 8, 10, 12, 16, 19, 22};

        int[] balancedTreeArray = AlgorithmsDataStructures2.GenerateBBSTArray(inputArray);
        int[] expected = new int[] {11, 7, 18, 4, 9, 15, 21, 1, 5, 8, 10, 12, 16, 19, 22};

        assertArrayEquals(expected, balancedTreeArray);
    }
}
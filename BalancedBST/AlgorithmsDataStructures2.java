import java.util.*;

public class AlgorithmsDataStructures2
{
    public static int[] GenerateBBSTArray(int[] a)
    {
        int[] BBSTArray = new int[a.length];
        if (a.length == 0) {
            return BBSTArray;
        }

        Arrays.sort(a);
        generateBBSTArrayRecursive(a, BBSTArray, 0, a.length - 1, 0);

        return BBSTArray;
    }

    private static void generateBBSTArrayRecursive(int[] inputArray, int[] BBSTArray, int leftBound, int rightBound, int indexInBBSTArray) {
        if (rightBound == leftBound) {
            int node = inputArray[leftBound];
            BBSTArray[indexInBBSTArray] = node;
            return;
        }

        int nodeIndex = (rightBound - leftBound) / 2 + leftBound;
        int node = inputArray[nodeIndex];
        BBSTArray[indexInBBSTArray] = node;
        generateBBSTArrayRecursive(inputArray, BBSTArray, leftBound, nodeIndex - 1,  2 * indexInBBSTArray + 1);
        generateBBSTArrayRecursive(inputArray, BBSTArray, nodeIndex + 1, rightBound,  2 * indexInBBSTArray + 2);
    }
}


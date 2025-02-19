import java.util.*;

class Heap
{
    public int [] HeapArray;
    public int emptySlot;

    public Heap() { HeapArray = null; }

    public void MakeHeap(int[] a, int depth)
    {
        HeapArray = new int[(int) (Math.pow(2, depth + 1) - 1)];
        Arrays.fill(HeapArray, -1);
        Arrays.stream(a).forEach(this::Add);
    }

    public int GetMax()
    {
        if (emptySlot == 0) {
            return -1;
        }
        int max = HeapArray[0];

        HeapArray[0] = HeapArray[emptySlot - 1];
        HeapArray[emptySlot - 1] = -1;

        if (emptySlot == 1) {
            emptySlot--;
            return max;
        }

        int currentIndex = 0;
        int leftIndex = 1;
        int rightIndex = 2;
        for (int maxChildIndex = HeapArray[leftIndex] > HeapArray[rightIndex] ? leftIndex : rightIndex;
             HeapArray[currentIndex] < HeapArray[maxChildIndex];
             maxChildIndex = HeapArray[leftIndex] > HeapArray[rightIndex] ? leftIndex : rightIndex) {

            swapValues(currentIndex, maxChildIndex);
            currentIndex = maxChildIndex;
            leftIndex = currentIndex * 2 + 1;
            rightIndex = leftIndex + 1;

            if (leftIndex >= emptySlot) {
                break;
            }
        }

        emptySlot--;
        return max;
    }

    public boolean Add(int key)
    {
        if (emptySlot == HeapArray.length) {
            return false;
        }

        HeapArray[emptySlot] = key;

        int currentIndex = emptySlot;


        for (int nextIndex = (emptySlot - 1) / 2; nextIndex >= 0 && HeapArray[nextIndex] < HeapArray[currentIndex]; nextIndex = (nextIndex - 1) / 2) {
            swapValues(nextIndex, currentIndex);
            currentIndex = nextIndex;
        }

        emptySlot++;
        return true;
    }

    private void swapValues(int firstIndex, int secondIndex) {
        HeapArray[firstIndex] = -HeapArray[firstIndex] - HeapArray[secondIndex];
        HeapArray[secondIndex] = -HeapArray[firstIndex] - HeapArray[secondIndex];
        HeapArray[firstIndex] = -HeapArray[firstIndex] - HeapArray[secondIndex];
    }
}


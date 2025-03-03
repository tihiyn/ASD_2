public boolean isHeapCorrect() {
    if (emptySlot <= 1) {
        return true;
    }

    return isHeapCorrectRecursive(0);
}

private boolean isHeapCorrectRecursive(int nodeIndex) {
    int leftChildIndex = nodeIndex * 2 + 1;
    int rightChildIndex = nodeIndex * 2 + 2;

    if (!checkChild(nodeIndex, leftChildIndex)) {
        return false;
    }

    return checkChild(nodeIndex, rightChildIndex);
}

private boolean checkChild(int parentIndex, int childIndex) {
    if (childIndex >= HeapArray.length) {
        return true;
    }

    if (((childIndex >= emptySlot) == (HeapArray[childIndex] != -1))
            || (HeapArray[childIndex] >= HeapArray[parentIndex])) {
        return false;
    }

    return isHeapCorrectRecursive(childIndex);
}

/*
Не совсем понял про прерывание в цикле по массиву. Точнее, не понятно, какое значение считать "сильно большим".
Вероятно, имеется в виду (что и написано в рекомендованном решении) мы должны хорошо знать входные данные.
Если всё-таки решать задачу через цикл, то это можно сделать, например, вот так:
    Arrays.stream(HeapArray)
            .filter(node -> node >= leftBound && node <= rightBound)
            .max()
            .orElse(-1);
Но такое решение "в лоб", так как не учитывает свойства кучи.
 */
public int findMaxInRange(int leftBound, int rightBound) {
    if (leftBound > HeapArray[0]) {
        return -1;
    }

    if (rightBound >= HeapArray[0]) {
        return HeapArray[0];
    }

    AtomicInteger max = new AtomicInteger(-1);
    findMaxInRangeRecursive(0, max, leftBound, rightBound);

    return max.get();
}

private void findMaxInRangeRecursive(final int currentIndex, AtomicInteger max, final int leftBound, final int rightBound) {
    if (currentIndex >= emptySlot) {
        return;
    }

    if (HeapArray[currentIndex] >= leftBound && HeapArray[currentIndex] <= rightBound && HeapArray[currentIndex] > max.get()) {
        max.set(HeapArray[currentIndex]);
        return;
    }

    if (HeapArray[currentIndex] >= leftBound && HeapArray[currentIndex] <= rightBound) {
        return;
    }

    findMaxInRangeRecursive(currentIndex * 2 + 1, max, leftBound, rightBound);
    findMaxInRangeRecursive(currentIndex * 2 + 2, max, leftBound, rightBound);
}

/*
Немного запутался с больше/меньше максимального/минимального.
 */
public List<Integer> findAllGreaterThan(int boundValue) {
    List<Integer> greaterThanList = new ArrayList<>();
    findAllGreaterThanRecursive(greaterThanList, 0, boundValue);

    return greaterThanList;
}

private void findAllGreaterThanRecursive(List<Integer> greaterThanList, final int currentIndex, final int boundValue) {
    if (currentIndex >= emptySlot || HeapArray[currentIndex] <= boundValue) {
        return;
    }

    greaterThanList.add(HeapArray[currentIndex]);
    int leftChildIndex = currentIndex * 2 + 1;
    int rightChildIndex = currentIndex * 2 + 2;

    findAllGreaterThanRecursive(greaterThanList, leftChildIndex, boundValue);
    findAllGreaterThanRecursive(greaterThanList, rightChildIndex, boundValue);
}


/*
Тоже сразу подумал о "порче" кучи параметра. Получил новые знания о копировании объектов и методе clone
(что метод клон выполняет лишь поверхностное (shallow) копирование), так как до этого использовал только функции из
сторонних библиотек.
 */
public void union(Heap toMerge) {
    if (toMerge == null) {
        return;
    }

    toMerge = toMerge.clone();

    for (int i = toMerge.GetMax(); i != -1; i = toMerge.GetMax()) {
        this.Add(i);
    }
}

@Override
public Heap clone() {
    try {
        Heap clone = (Heap) super.clone();
        clone.HeapArray = HeapArray.clone();
        return clone;
    } catch (CloneNotSupportedException e) {
        throw new AssertionError();
    }
}


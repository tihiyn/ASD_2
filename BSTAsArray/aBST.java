import java.util.*;

class aBST
{
    public Integer Tree [];

    public aBST(int depth)
    {
        int tree_size = (int) (Math.pow(2, depth + 1) - 1);
        Tree = new Integer[ tree_size ];
        for(int i=0; i<tree_size; i++) Tree[i] = null;
    }

    public Integer FindKeyIndex(int key)
    {
        if (Tree[0] == null) {
            return 0;
        }

        return findKeyIndexRecursive(0, key);
    }

    private Integer findKeyIndexRecursive(int index, final int key) {
        if (Tree[index] == key) {
            return index;
        }

        int nextIndex = key < Tree[index] ? 2 * index + 1 : 2 * index + 2;
        if (nextIndex >= Tree.length) {
            return null;
        }

        if (Tree[nextIndex] == null) {
            return -nextIndex;
        }

        return findKeyIndexRecursive(nextIndex, key);
    }

    public int AddKey(int key)
    {
        Integer foundIndex = FindKeyIndex(key);
        if (foundIndex == null) {
            return -1;
        }

        if (foundIndex <= 0) {
            Tree[-foundIndex] = key;
            return -foundIndex;
        }

        return foundIndex;
    }

    public Integer findLCA(final int firstKey, final int secondKey) {
        if (Tree[0] == null
                || firstKey == Tree[0] || secondKey == Tree[0]
                || !isKeyExist(firstKey) || !isKeyExist(secondKey)) {
            return null;
        }

        return findLCARecursive(firstKey, secondKey, 0);
    }

    private Integer findLCARecursive(final Integer firstKey, final Integer secondKey, final int currentIndex) {
        int firstNextIndex = firstKey < Tree[currentIndex] ? 2 * currentIndex + 1 : 2 * currentIndex + 2;
        int secondNextIndex = secondKey < Tree[currentIndex] ? 2 * currentIndex + 1 : 2 * currentIndex + 2;

        if (firstNextIndex != secondNextIndex
                || Objects.equals(Tree[firstNextIndex], firstKey)
                || Objects.equals(Tree[secondNextIndex], secondKey)) {
            return Tree[currentIndex];
        }

        return findLCARecursive(firstKey, secondKey, firstNextIndex);
    }

    private boolean isKeyExist(int key) {
        Integer keyIndex = FindKeyIndex(key);

        return keyIndex != null && keyIndex >= 0;
    }

    public Integer[] wideAllNodes() {
        Integer[] wideArray = new Integer[Tree.length];
        if (Tree[0] == null) {
            return wideArray;
        }

        Integer[] currentLevelNodes = new Integer[1];
        currentLevelNodes[0] = Tree[0];
        wideAllNodesRecursive(wideArray, currentLevelNodes, 0);
        return wideArray;
    }

    private void wideAllNodesRecursive(Integer[] wideArray, Integer[] currentLevelNodes, final int level) {
        for (int i = 0; i < currentLevelNodes.length; i++) {
            wideArray[(int) (Math.pow(2, level) - 1 + i)] = currentLevelNodes[i];
        }

        if ((int) Math.pow(2, level + 1) - 1 == wideArray.length) {
            return;
        }

        Integer[] nextLevelNodes = new Integer[(int) Math.pow(2, level + 1)];
        int nextLevelNodesCounter = 0;
        for (int i = 0; i < currentLevelNodes.length; i++) {
            nextLevelNodes[nextLevelNodesCounter] = Tree[2 * (i + (int) Math.pow(2, level) - 1) + 1];
            nextLevelNodes[nextLevelNodesCounter + 1] = Tree[2 * (i + (int) Math.pow(2, level) - 1) + 2];
            nextLevelNodesCounter += 2;
        }

        wideAllNodesRecursive(wideArray, nextLevelNodes, level + 1);
    }

    public void deleteKey(int key) {
        deleteKeyHelper(key);

        if (isBalanced(0) >= 0) {
            return;
        }

        Integer[] treeArrayWithoutNullValues = Arrays.stream(Tree).filter(Objects::nonNull).toArray(Integer[]::new);
        Tree = balanceTree(treeArrayWithoutNullValues);
    }

    private void deleteKeyHelper(int key) {
        Integer keyIndex = FindKeyIndex(key);
        if (keyIndex < 0) {
            return;
        }

        int leftChildIndex = keyIndex * 2 + 1;
        int rightChildIndex = keyIndex * 2 + 2;
        if ((leftChildIndex >= Tree.length || Tree[leftChildIndex] == null)
                && (rightChildIndex >= Tree.length || Tree[rightChildIndex] == null)) {
            Tree[keyIndex] = null;
            return;
        }

        if (leftChildIndex >= Tree.length || Tree[leftChildIndex] == null
                || rightChildIndex >= Tree.length || Tree[rightChildIndex] == null) {
            deleteNodeWithOneChild(keyIndex, leftChildIndex, rightChildIndex);
            return;
        }

        int successorIndex = findSupplierIndex(rightChildIndex);
        if (successorIndex * 2 + 2 < Tree.length && Tree[successorIndex * 2 + 2] != null) {
            Tree[keyIndex] = Tree[successorIndex];
            Tree[successorIndex] = Tree[successorIndex * 2 + 2];
            Tree[successorIndex * 2 + 2] = null;
            return;
        }

        Tree[keyIndex] = Tree[successorIndex];
        Tree[successorIndex] = null;
    }

    private void deleteNodeWithOneChild(int nodeIndex, int leftChildIndex, int rightChildIndex) {
        if (leftChildIndex >= Tree.length || Tree[leftChildIndex] == null) {
            Tree[nodeIndex] = Tree[rightChildIndex];
            Tree[rightChildIndex] = null;
            return;
        }

        Tree[nodeIndex] = Tree[leftChildIndex];
        Tree[leftChildIndex] = null;
    }

    private int findSupplierIndex(int currentIndex) {
        int supplierIndex = currentIndex;
        for (; supplierIndex * 2 + 1 < Tree.length && Tree[supplierIndex * 2 + 1] != null; supplierIndex = supplierIndex * 2 + 1) {}

        return supplierIndex;
    }

    private int isBalanced(int currentIndex) {
        int leftChildIndex = currentIndex * 2 + 1;
        int rightChildIndex = currentIndex * 2 + 2;

        int leftDepth = leftChildIndex < Tree.length && Tree[leftChildIndex] != null ? isBalanced(leftChildIndex) : 0;
        int rightDepth = rightChildIndex < Tree.length && Tree[rightChildIndex] != null ? isBalanced(rightChildIndex) : 0;

        if (leftDepth < 0 || rightDepth < 0) {
            return -1;
        }

        if (Math.abs(leftDepth - rightDepth) <= 1) {
            return Math.max(leftDepth, rightDepth) + 1;
        }

        return -1;
    }

    private Integer[] balanceTree(Integer[] inputArray) {
        Arrays.sort(inputArray);
        Integer[] balancedTree = new Integer[calcBalancedTreeArrayLength(inputArray.length)];
        balanceTreeRecursive(balancedTree, inputArray, 0, 0, inputArray.length - 1);

        return balancedTree;
    }

    private void balanceTreeRecursive(Integer[] balancedTree, Integer[] inputArray, final int currentIndex,
                                      final int leftBound, final int rightBound) {
        if (leftBound > rightBound) {
            return;
        }

        if (leftBound == rightBound) {
            balancedTree[currentIndex] = inputArray[leftBound];
            return;
        }

        int centralIndex = (rightBound - leftBound) / 2 + leftBound;
        balancedTree[currentIndex] = inputArray[centralIndex];
        balanceTreeRecursive(balancedTree, inputArray, currentIndex * 2 + 1, leftBound, centralIndex - 1);
        balanceTreeRecursive(balancedTree, inputArray, currentIndex * 2 + 2, centralIndex + 1, rightBound);
    }

    private int calcBalancedTreeArrayLength(int oldLength) {
        return (int) Math.pow(2,
                BigDecimal.valueOf(Math.log(oldLength) / Math.log(2)).setScale(0, RoundingMode.UP).doubleValue()) - 1;
    }
}


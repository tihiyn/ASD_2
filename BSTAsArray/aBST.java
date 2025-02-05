import java.util.*;

class aBST
{
    public Integer Tree [];

    public aBST(int depth)
    {
        int tree_size = (int) (Math.pow(2, depth) - 1);
        Tree = new Integer[ tree_size ];
        for(int i=0; i<tree_size; i++) Tree[i] = null;
    }

    public Integer FindKeyIndex(int key)
    {
        if (Tree.length == 0) {
            return null;
        }

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
}


import java.util.*;

class BSTNode
{
    public int NodeKey;
    public BSTNode Parent;
    public BSTNode LeftChild;
    public BSTNode RightChild;
    public int     Level;

    public BSTNode(int key, BSTNode parent)
    {
        NodeKey = key;
        Parent = parent;
        LeftChild = null;
        RightChild = null;
    }
}

class BalancedBST
{
    public BSTNode Root;

    public BalancedBST()
    {
        Root = null;
    }

    public void GenerateTree(int[] a)
    {
        if (a == null || a.length == 0) {
            return;
        }

        Arrays.sort(a);
        Root = generateTreeRecursive(a, null, 0, a.length - 1, 0);
    }

    private BSTNode generateTreeRecursive(int[] sortedArray, BSTNode parent, final int leftBound, final int rightBound,
                                          final int currentLevel) {
        if (leftBound == rightBound) {
            BSTNode node = new BSTNode(sortedArray[leftBound], parent);
            node.Level = currentLevel;
            return node;
        }

        int centralIndex = leftBound + (rightBound - leftBound) / 2;
        int centralElement = sortedArray[centralIndex];

        BSTNode node = new BSTNode(centralElement, parent);
        node.Level = currentLevel;
        node.LeftChild = generateTreeRecursive(sortedArray, node, leftBound, centralIndex - 1, currentLevel + 1);
        node.RightChild = generateTreeRecursive(sortedArray, node, centralIndex + 1, rightBound, currentLevel + 1);

        return node;
    }

    public boolean IsBalanced(BSTNode root_node)
    {
        if (root_node == null) {
            return false;
        }

        return isBalancedRecursive(root_node) >= 0;
    }

    private int isBalancedRecursive(BSTNode current) {
        int leftDepth = current.LeftChild != null ? isBalancedRecursive(current.LeftChild) : 0;
        int rightDepth = current.RightChild != null ? isBalancedRecursive(current.RightChild) : 0;

        if (leftDepth < 0 || rightDepth < 0) {
            return -1;
        }

        if (Math.abs(leftDepth - rightDepth) <= 1) {
            return Math.max(leftDepth, rightDepth) + 1;
        }

        return -1;
    }
}


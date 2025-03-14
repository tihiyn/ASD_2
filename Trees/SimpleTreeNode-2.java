import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

class BSTNode
{
    public int NodeKey;
    public BSTNode Parent;
    public BSTNode LeftChild;
    public BSTNode RightChild;
    public int Level;

    public BSTNode(int key, BSTNode parent)
    {
        NodeKey = key;
        Parent = parent;
        LeftChild = null;
        RightChild = null;
    }
}

class EvenBST {
    public BSTNode Root;

    public EvenBST(BSTNode node)
    {
        Root = node;
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

    public ArrayList<BSTNode> inOrderTraversal() {
        ArrayList<BSTNode> allNodes = new ArrayList<>();
        if (Root == null) {
            return allNodes;
        }
        inOrderTraversalRecursive(allNodes, Root);

        return allNodes;
    }

    private void inOrderTraversalRecursive(ArrayList<BSTNode> nodes, BSTNode node) {
        addChildForInOrder(nodes, node.LeftChild);
        nodes.add(node);
        addChildForInOrder(nodes, node.RightChild);
    }

    private void addChildForInOrder(ArrayList<BSTNode> nodes, BSTNode child) {
        if (child != null) {
            inOrderTraversalRecursive(nodes, child);
        }
    }

    /*
    Задание 9.
    Задача 2.
    Сбалансировать чётное двоичное дерево.
    Временная сложность - O(N).
    Пространственная сложность - O(N).
     */
    public void balance() {
        ArrayList<BSTNode> inOrder = inOrderTraversal();
        Root = balanceRecursive(inOrder, null, 0, inOrder.size() - 1, 0);
    }

    private BSTNode balanceRecursive(ArrayList<BSTNode> sortedArray, BSTNode parent, final int leftBound, final int rightBound,
                                     final int currentLevel) {
        if (leftBound > rightBound) {
            return null;
        }

        if (leftBound == rightBound) {
            BSTNode node = new BSTNode(sortedArray.get(leftBound).NodeKey, parent);
            node.Level = currentLevel;
            return node;
        }

        int centralIndex = leftBound + (rightBound - leftBound) / 2;
        int centralElement = sortedArray.get(centralIndex).NodeKey;

        BSTNode node = new BSTNode(centralElement, parent);
        node.Level = currentLevel;
        node.LeftChild = balanceRecursive(sortedArray, node, leftBound, centralIndex - 1, currentLevel + 1);
        node.RightChild = balanceRecursive(sortedArray, node, centralIndex + 1, rightBound, currentLevel + 1);

        return node;
    }
}


class TreeNode<T>
{
    public T NodeValue;
    public TreeNode<T> Parent;
    public List<TreeNode<T>> Children;
    public int level;

    public TreeNode(T val, TreeNode<T> parent)
    {
        NodeValue = val;
        Parent = parent;
        Children = null;
    }
}

class Tree<T>
{
    public TreeNode<T> Root;

    public Tree(TreeNode<T> root)
    {
        Root = root;
    }

    public void AddChild(TreeNode<T> ParentNode, TreeNode<T> NewChild)
    {
        if (ParentNode == null) {
            Root = NewChild;
            NewChild.level = 0;
            return;
        }

        if (ParentNode.Children == null) {
            ParentNode.Children = new ArrayList<>();
        }

        NewChild.Parent = ParentNode;
        ParentNode.Children.add(NewChild);

        NewChild.level = ParentNode.level + 1;
        if (NewChild.Children == null) {
            return;
        }
        incrementChildrenLevels(NewChild);
    }

    private void incrementChildrenLevels(TreeNode<T> parent) {
        for (TreeNode<T> child: parent.Children) {
            child.level = parent.level + 1;

            if (child.Children != null) {
                incrementChildrenLevels(child);
            }
        }
    }

    public List<TreeNode<T>> GetAllNodes()
    {
        if (Root == null) {
            return new ArrayList<>();
        }

        return GetAllNodesRecursive(Root);
    }

    private List<TreeNode<T>> GetAllNodesRecursive(TreeNode<T> parent) {
        List<TreeNode<T>> nodes = new ArrayList<>();
        nodes.add(parent);

        if (parent.Children == null) {
            return nodes;
        }

        List<TreeNode<T>> children = parent.Children;
        children.forEach(child -> nodes.addAll(GetAllNodesRecursive(child)));

        return nodes;
    }

    public List<TreeNode<T>> FindNodesByValue(T val)
    {
        if (Root == null) {
            return new ArrayList<>();
        }

        return FindNodesByValueRecursive(Root, val);
    }

    private List<TreeNode<T>> FindNodesByValueRecursive(TreeNode<T> parent, final T val) {
        List<TreeNode<T>> equalsNodes = new ArrayList<>();
        if (parent.NodeValue.equals(val)) {
            equalsNodes.add(parent);
        }

        List<TreeNode<T>> children = parent.Children;
        if (children == null) {
            return equalsNodes;
        }

        children.forEach(child -> equalsNodes.addAll(FindNodesByValueRecursive(child, val)));

        return equalsNodes;
    }

    public int Count()
    {
        if (Root == null) {
            return 0;
        }

        return CountRecursive(Root);
    }

    private int CountRecursive(TreeNode<T> parent) {
        int counter = 1;
        if (parent.Children == null) {
            return counter;
        }

        for (TreeNode<T> child: parent.Children) {
            counter += CountRecursive(child);
        }

        return counter;
    }

    /*
    Задание 9.
    Задача 3.
    Определить кол-во чётных поддеревьев для заданного узла.
    Временная сложность - O(N^2)
    Пространственная сложность - O(N)
     */
    public int countEvenSubtrees(TreeNode<T> node) {
        if (node == null) {
            return 0;
        }

        AtomicInteger evenSubTreesCounter = new AtomicInteger(0);
        countEvenSubtreesRecursive(node, evenSubTreesCounter);

        return evenSubTreesCounter.get();
    }

    private void countEvenSubtreesRecursive(TreeNode<T> currentNode, AtomicInteger evenSubTreesCounter) {
        if (currentNode.Children == null) {
            return;
        }

        evenSubTreesCounter.getAndAdd((int) currentNode.Children.stream()
                .map(this::CountRecursive)
                .filter(subtreeCount -> subtreeCount > 0 && subtreeCount % 2 == 0)
                .count());

        currentNode.Children.forEach(child -> countEvenSubtreesRecursive(child, evenSubTreesCounter));
    }
}
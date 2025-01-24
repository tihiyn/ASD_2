import java.util.*;

class BSTNode<T>
{
    public int NodeKey;
    public T NodeValue;
    public BSTNode<T> Parent;
    public BSTNode<T> LeftChild;
    public BSTNode<T> RightChild;

    public BSTNode(int key, T val, BSTNode<T> parent)
    {
        NodeKey = key;
        NodeValue = val;
        Parent = parent;
        LeftChild = null;
        RightChild = null;
    }
}

class BSTFind<T>
{
    public BSTNode<T> Node;

    public boolean NodeHasKey;

    public boolean ToLeft;

    public BSTFind() { Node = null; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BSTFind<?> bstFind = (BSTFind<?>) o;
        return NodeHasKey == bstFind.NodeHasKey && ToLeft == bstFind.ToLeft && Objects.equals(Node, bstFind.Node);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Node, NodeHasKey, ToLeft);
    }
}

class BST<T>
{
    BSTNode<T> Root;

    public BST(BSTNode<T> node)
    {
        Root = node;
    }

    public BSTFind<T> FindNodeByKey(int key)
    {
        if (Root == null) {
            BSTFind<T> notExist = new BSTFind<>();
            notExist.Node = null;
            return notExist;
        }

        return FindNodeByKeyRecursive(Root, key);
    }

    private BSTFind<T> FindNodeByKeyRecursive(BSTNode<T> current, final int key) {
        if (key == current.NodeKey) {
            BSTFind<T> found = new BSTFind<>();
            found.Node = current;
            found.NodeHasKey = true;
            return found;
        }

        BSTNode<T> next = key < current.NodeKey ? current.LeftChild : current.RightChild;
        if (next == null) {
            BSTFind<T> notFound = new BSTFind<>();
            notFound.Node = current;
            notFound.NodeHasKey = false;
            notFound.ToLeft = key < current.NodeKey;

            return notFound;
        }

        return FindNodeByKeyRecursive(next, key);
    }

    public boolean AddKeyValue(int key, T val)
    {
        BSTFind<T> found = FindNodeByKey(key);
        if (found.Node == null) {
            Root = new BSTNode<>(key, val, null);
            return true;
        }

        if (found.NodeHasKey) {
            return false;
        }

        if (found.ToLeft) {
            found.Node.LeftChild = new BSTNode<>(key, val, found.Node);
            return true;
        }

        found.Node.RightChild = new BSTNode<>(key, val, found.Node);
        return true;
    }

    public BSTNode<T> FinMinMax(BSTNode<T> FromNode, boolean FindMax)
    {
        if (Root == null) {
            return null;
        }

        BSTNode<T> node = FromNode;
        for (; FindMax ? node.RightChild != null : node.LeftChild != null; node = FindMax ? node.RightChild : node.LeftChild) { }

        return node;
    }

    public boolean DeleteNodeByKey(int key)
    {
        BSTFind<T> found = FindNodeByKey(key);
        if (found.Node == null || !found.NodeHasKey) {
            return false;
        }

        BSTNode<T> nodeToDelete = found.Node;

        if (nodeToDelete.LeftChild == null && nodeToDelete.RightChild == null) {
            deleteChildFromParent(nodeToDelete);
            return true;
        }

        if (nodeToDelete.LeftChild == null) {
            copyKeyValue(nodeToDelete.RightChild, nodeToDelete);
            nodeToDelete.RightChild = null;
            return true;
        }

        if (nodeToDelete.RightChild == null) {
            copyKeyValue(nodeToDelete.LeftChild, nodeToDelete);
            nodeToDelete.LeftChild = null;
            return true;
        }

        BSTNode<T> successor = FinMinMax(nodeToDelete.RightChild, false);
        if (successor.RightChild == null) {
            copyKeyValue(successor, nodeToDelete);
            successor.Parent.LeftChild = null;
            return true;
        }

        copyKeyValue(successor, nodeToDelete);
        copyKeyValue(successor.RightChild, successor);
        successor.RightChild = null;
        return true;
    }

    private void deleteChildFromParent(BSTNode<T> nodeToDelete) {
        if (nodeToDelete.NodeKey < nodeToDelete.Parent.NodeKey) {
            nodeToDelete.Parent.LeftChild = null;
            return;
        }

        nodeToDelete.Parent.RightChild = null;
    }

    private void copyKeyValue(BSTNode<T> from, BSTNode<T> to) {
        to.NodeKey = from.NodeKey;
        to.NodeValue = from.NodeValue;
    }

    public int Count()
    {
        if (Root == null) {
            return 0;
        }

        return countRecursive(Root);
    }

    private int countRecursive(BSTNode<T> node) {
        int nodeCounter = 1;

        if (node.LeftChild != null) {
            nodeCounter += countRecursive(node.LeftChild);
        }

        if (node.RightChild != null) {
            nodeCounter += countRecursive(node.RightChild);
        }

        return nodeCounter;
    }
}


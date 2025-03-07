import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

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
        if (successor.NodeKey == nodeToDelete.RightChild.NodeKey) {
            copyKeyValue(successor, nodeToDelete);
            nodeToDelete.RightChild = null;
            return true;
        }

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
        if (nodeToDelete.Parent == null) {
            Root = null;
            return;
        }

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
        return countRecursive(Root);
    }

    private int countRecursive(BSTNode<T> node) {
        if (node == null) {
            return 0;
        }

        return 1 + countRecursive(node.LeftChild) + countRecursive(node.RightChild);
    }

    public boolean isEquivalent(BST<T> anotherTree) {
        return isNodesEqualsRecursive(this.Root, anotherTree.Root);
    }

    private boolean isNodesEqualsRecursive(BSTNode<T> firstNode, BSTNode<T> secondNode) {
        if ((firstNode == null) != (secondNode == null)) {
            return false;
        }

        if (firstNode == null) {
            return true;
        }

        if (firstNode.NodeKey != secondNode.NodeKey || !firstNode.NodeValue.equals(secondNode.NodeValue)) {
            return false;
        }

        if (!isNodesEqualsRecursive(firstNode.LeftChild, secondNode.LeftChild)) {
            return false;
        }

        return isNodesEqualsRecursive(firstNode.RightChild, secondNode.RightChild);
    }

    public List<List<BSTNode<T>>> getRoutesOfReqLength(int reqLength) {
        List<List<BSTNode<T>>> routesList = new ArrayList<>((int) Math.pow(2, reqLength));
        if (Root == null) {
            return routesList;
        }

        getRoutesOfReqLengthRecursive(routesList, new ArrayList<>(), Root, 0, reqLength);

        return routesList;
    }

    private void getRoutesOfReqLengthRecursive(List<List<BSTNode<T>>> routesList, List<BSTNode<T>> currentRoute, BSTNode<T> node,
                                               final int currentLength, final int reqLength) {
        currentRoute.add(node);
        if (currentLength == reqLength) {
            routesList.add(currentRoute);
            return;
        }

        processChildForReqLength(routesList, currentRoute, node.LeftChild, currentLength, reqLength);
        processChildForReqLength(routesList, currentRoute, node.RightChild, currentLength, reqLength);
    }

    private void processChildForReqLength(List<List<BSTNode<T>>> routesList, List<BSTNode<T>> currentRoute,
                              BSTNode<T> childNode, final int currentLength, final int reqLength) {
        if (childNode != null) {
            getRoutesOfReqLengthRecursive(routesList, new ArrayList<>(currentRoute), childNode, currentLength + 1, reqLength);
        }
    }

    public <U extends Number> List<List<BSTNode<U>>> getRoutesWithMaxNodesValueSum() {
        List<List<BSTNode<U>>> routesList = new ArrayList<>();
        if (Root == null) {
            return routesList;
        }

        try {
            getRoutesWithMaxNodesValueSumRecursive(routesList, new ArrayList<>(), (BSTNode<U>) Root, 0,
                    new AtomicInteger(0));
        }
        catch (ClassCastException e) {
            System.out.println("Значения узлов дерева должны быть числами");
            throw e;
        }

        return routesList;
    }

    private <U extends Number> void getRoutesWithMaxNodesValueSumRecursive(List<List<BSTNode<U>>> routesList,
                                                                           List<BSTNode<U>> currentRoute, BSTNode<U> node,
                                                                           int currentSum, AtomicInteger maxSum) {
        currentRoute.add(node);
        currentSum += node.NodeValue.intValue();

        boolean isNodeLeaf = node.LeftChild == null && node.RightChild == null;
        if (isNodeLeaf && currentSum > maxSum.get()) {
            maxSum.set(currentSum);
            routesList.clear();
            routesList.add(currentRoute);
            return;
        }

        if (isNodeLeaf && currentSum == maxSum.get()) {
            routesList.add(currentRoute);
        }

        processChildForMaxNodesSum(routesList, currentRoute, node.LeftChild, currentSum, maxSum);
        processChildForMaxNodesSum(routesList, currentRoute, node.RightChild, currentSum, maxSum);
    }

    private <U extends Number> void processChildForMaxNodesSum(List<List<BSTNode<U>>> routesList, List<BSTNode<U>> currentRoute,
                                                               BSTNode<U> childNode, int currentSum, AtomicInteger maxSum) {
        if (childNode != null) {
            getRoutesWithMaxNodesValueSumRecursive(routesList, new ArrayList<>(currentRoute), childNode, currentSum, maxSum);
        }
    }

    public boolean isSymmetrical() {
        if (Root == null) {
            return true;
        }

        return isSymmetricalRecursive(Root.LeftChild, Root.RightChild);
    }

    private boolean isSymmetricalRecursive(BSTNode<T> firstNode, BSTNode<T> secondNode) {
        if ((firstNode == null) != (secondNode == null)) {
            return false;
        }

        if (firstNode == null) {
            return true;
        }

        if (!firstNode.NodeValue.equals(secondNode.NodeValue)) {
            return false;
        }

        if (!isSymmetricalRecursive(firstNode.LeftChild, secondNode.RightChild)) {
            return false;
        }

        return isSymmetricalRecursive(firstNode.RightChild, secondNode.LeftChild);
    }

    public ArrayList<BSTNode> WideAllNodes() {
        ArrayList<BSTNode> allNodes = new ArrayList<>();
        if (Root == null) {
            return allNodes;
        }

        allNodes.add(Root);
        wideAllNodesRecursive(allNodes, new ArrayList<>(List.of(Root.LeftChild, Root.RightChild)));
        return allNodes;
    }

    private void wideAllNodesRecursive(ArrayList<BSTNode> allNodes, ArrayList<BSTNode> nodesAtLevel) {
        if (nodesAtLevel.isEmpty()) {
            return;
        }

        ArrayList<BSTNode> nodesAtNextLevel = new ArrayList<>();
        for (BSTNode node: nodesAtLevel) {
            allNodes.add(node);
            addChildToNextLevelList(nodesAtNextLevel, node.LeftChild);
            addChildToNextLevelList(nodesAtNextLevel, node.RightChild);
        }

        wideAllNodesRecursive(allNodes, nodesAtNextLevel);
    }

    private void addChildToNextLevelList(ArrayList<BSTNode> nodesAtNextLevel, BSTNode child) {
        if (child != null) {
            nodesAtNextLevel.add(child);
        }
    }

    public ArrayList<BSTNode> DeepAllNodes(int order) {
        ArrayList<BSTNode> allNodes = new ArrayList<>();
        if (Root == null) {
            return allNodes;
        }

        switch (order) {
            case 0 -> deepAllNodesInOrder(allNodes, Root);
            case 1 -> deepAllNodesPostOrder(allNodes, Root);
            case 2 -> deepAllNodesPreOrder(allNodes, Root);
        }

        return allNodes;
    }

    private void deepAllNodesInOrder(ArrayList<BSTNode> nodes, BSTNode node) {
        addChildForInOrder(nodes, node.LeftChild);
        nodes.add(node);
        addChildForInOrder(nodes, node.RightChild);
    }

    private void addChildForInOrder(ArrayList<BSTNode> nodes, BSTNode child) {
        if (child != null) {
            deepAllNodesInOrder(nodes, child);
        }
    }

    private void deepAllNodesPostOrder(ArrayList<BSTNode> nodes, BSTNode node) {
        addChildForPostOrder(nodes, node.LeftChild);
        addChildForPostOrder(nodes, node.RightChild);
        nodes.add(node);
    }

    private void addChildForPostOrder(ArrayList<BSTNode> nodes, BSTNode child) {
        if (child != null) {
            deepAllNodesPostOrder(nodes, child);
        }
    }

    private void deepAllNodesPreOrder(ArrayList<BSTNode> nodes, BSTNode node) {
        nodes.add(node);
        addChildForPreOrder(nodes, node.LeftChild);
        addChildForPreOrder(nodes, node.RightChild);
    }

    private void addChildForPreOrder(ArrayList<BSTNode> nodes, BSTNode child) {
        if (child != null) {
            deepAllNodesPreOrder(nodes, child);
        }
    }
    
    public void invert() {
        if (Root == null) {
            return;
        }

        invertRecursive(Root);
    }

    private void invertRecursive(BSTNode<T> node) {
        BSTNode<T> tmpToSwap = node.LeftChild;
        node.LeftChild = node.RightChild;
        node.RightChild = tmpToSwap;

        callInvertWithCheck(node.LeftChild);
        callInvertWithCheck(node.RightChild);
    }

    private void callInvertWithCheck(BSTNode<T> node) {
        if (node != null) {
            invertRecursive(node);
        }
    }

    public Integer findMaxSumLevel() {
        if (Root == null) {
            return null;
        }

        try {
            AtomicInteger maxSum = new AtomicInteger((Integer) Root.NodeValue);
            List<BSTNode<T>> nodesAtNextLevel = new ArrayList<>();
            addChildToNextLevelList(Root.LeftChild, nodesAtNextLevel);
            addChildToNextLevelList(Root.RightChild, nodesAtNextLevel);

            return findMaxSumLevelRecursive(nodesAtNextLevel, maxSum, 1, 0);
        } catch (ClassCastException e) {
            System.out.println("Значения узлов должны быть числами");
            throw e;
        }
    }

    private int findMaxSumLevelRecursive(List<BSTNode<T>> nodesAtCurrentLevel, AtomicInteger maxSum, final int level, int maxLevel) {
        if (nodesAtCurrentLevel.isEmpty()) {
            return maxLevel;
        }

        List<BSTNode<T>> nodesAtNextLevel = new ArrayList<>();
        int maxSumAtCurrentLevel = 0;

        for (BSTNode<T> node: nodesAtCurrentLevel) {
            maxSumAtCurrentLevel += (int) node.NodeValue;
            addChildToNextLevelList(node.LeftChild, nodesAtNextLevel);
            addChildToNextLevelList(node.RightChild, nodesAtNextLevel);
        }

        if (maxSumAtCurrentLevel > maxSum.get()) {
            maxSum.set(maxSumAtCurrentLevel);
            maxLevel = level;
        }

        return findMaxSumLevelRecursive(nodesAtNextLevel, maxSum, level + 1, maxLevel);
    }

    private void addChildToNextLevelList(BSTNode<T> child, List<BSTNode<T>> nodesAtNextLevel) {
        if (child != null) {
            nodesAtNextLevel.add(child);
        }
    }
}


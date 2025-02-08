import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TreeRestorer<T> {
    public BST<T> restoreTree(List<T> inOrder, List<T> preOrder) {
        if (inOrder.size() != preOrder.size() || inOrder.isEmpty()) {
            return new BST<>(null);
        }

        BSTNode<T> root = restoreTreeRecursive(inOrder, preOrder, null, new AtomicInteger(0), -1);

        return new BST<>(root);
    }

    private BSTNode<T> restoreTreeRecursive(List<T> inOrder, List<T> preOrder, BSTNode<T> previousNode,
                                            AtomicInteger indexInPreOrder, final int previousIndex) {
        T nodeValue = preOrder.get(indexInPreOrder.get());
        int indexInOrderList = inOrder.indexOf(nodeValue);
        BSTNode<T> node = new BSTNode<>(indexInOrderList, nodeValue, previousNode);
        indexInPreOrder.incrementAndGet();

        if (Math.abs(indexInOrderList - previousIndex) == 1) {
            return node;
        }

        node.LeftChild = restoreTreeRecursive(inOrder, preOrder, node, indexInPreOrder, indexInOrderList);
        node.RightChild = restoreTreeRecursive(inOrder, preOrder, node, indexInPreOrder, indexInOrderList);

        return node;
    }
}


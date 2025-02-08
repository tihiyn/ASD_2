import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

class TreeRestorerTest {

    @Test
    void testRestoreEmptyTree() {
        TreeRestorer<Character> treeRestorer = new TreeRestorer<>();
        BST<Character> tree = treeRestorer.restoreTree(new ArrayList<>(), new ArrayList<>());
        assertNull(tree.Root);
    }

    @Test
    void testRestoreTreeByIncorrectLists() {
        List<Integer> preOrder = List.of(5, 12, 4);
        List<Integer> inOrder = List.of(5, 12);

        TreeRestorer<Integer> treeRestorer = new TreeRestorer<>();
        BST<Integer> tree = treeRestorer.restoreTree(inOrder, preOrder);
        assertNull(tree.Root);
    }

    @Test
    void restoreSingleNodeTree() {
        List<String> preOrder = List.of("fff");
        List<String> inOrder = List.of("fff");

        TreeRestorer<String> treeRestorer = new TreeRestorer<>();
        BST<String> tree = treeRestorer.restoreTree(inOrder, preOrder);
        assertEquals(0, tree.Root.NodeKey);
        assertEquals("fff", tree.Root.NodeValue);
        assertNull(tree.Root.Parent);
        assertNull(tree.Root.LeftChild);
        assertNull(tree.Root.RightChild);
    }

    @Test
    void restoreSimpleTree() {
        List<Integer> preOrderBefore = List.of(5, 3, 8);
        List<Integer> inOrderBefore = List.of(3, 5, 8);

        TreeRestorer<Integer> treeRestorer = new TreeRestorer<>();
        BST<Integer> tree = treeRestorer.restoreTree(inOrderBefore, preOrderBefore);

        ArrayList<BSTNode> preOrderAfter = tree.DeepAllNodes(2);
        ArrayList<BSTNode> inOrderAfter = tree.DeepAllNodes(0);
        for (int i = 0; i < preOrderBefore.size(); i++) {
            assertEquals(preOrderBefore.get(i), preOrderAfter.get(i).NodeValue);
            assertEquals(inOrderBefore.get(i), inOrderAfter.get(i).NodeValue);
        }
    }

    @Test
    void testRestoreTree() {
        List<Integer> preOrderBefore = List.of(1, 2, 4, 5, 3, 6, 7);
        List<Integer> inOrderBefore = List.of(4, 2, 5, 1, 6, 3, 7);

        TreeRestorer<Integer> treeRestorer = new TreeRestorer<>();
        BST<Integer> tree = treeRestorer.restoreTree(inOrderBefore, preOrderBefore);

        ArrayList<BSTNode> preOrderAfter = tree.DeepAllNodes(2);
        ArrayList<BSTNode> inOrderAfter = tree.DeepAllNodes(0);
        for (int i = 0; i < preOrderBefore.size(); i++) {
            assertEquals(preOrderBefore.get(i), preOrderAfter.get(i).NodeValue);
            assertEquals(inOrderBefore.get(i), inOrderAfter.get(i).NodeValue);
        }
    }
}


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

class BalancedBSTTest {
    @Test
    void testGenerateEmptyTree() {
        BalancedBST emptyTree = new BalancedBST();
        assertNull(emptyTree.Root);

        emptyTree.GenerateTree(new int[0]);

        assertNull(emptyTree.Root);
    }

    @Test
    void testGenerateSingleNodeTree() {
        BalancedBST tree = new BalancedBST();
        int[] inputArray = new int[] {5};
        tree.GenerateTree(inputArray);

        assertEquals(5, tree.Root.NodeKey);
        assertEquals(0, tree.Root.Level);
        assertNull(tree.Root.LeftChild);
        assertNull(tree.Root.RightChild);
    }

    @Test
    void testGenerateTwoLevelTree() {
        BalancedBST tree = new BalancedBST();
        int[] inputArray = new int[] {8, 12, 5};
        tree.GenerateTree(inputArray);

        assertEquals(8, tree.Root.NodeKey);
        assertEquals(0, tree.Root.Level);

        assertEquals(5, tree.Root.LeftChild.NodeKey);
        assertEquals(1, tree.Root.LeftChild.Level);
        assertEquals(tree.Root.NodeKey, tree.Root.LeftChild.Parent.NodeKey);
        assertNull(tree.Root.LeftChild.LeftChild);
        assertNull(tree.Root.LeftChild.RightChild);

        assertEquals(12, tree.Root.RightChild.NodeKey);
        assertEquals(1, tree.Root.RightChild.Level);
        assertEquals(tree.Root.NodeKey, tree.Root.RightChild.Parent.NodeKey);
        assertNull(tree.Root.RightChild.LeftChild);
        assertNull(tree.Root.RightChild.RightChild);
    }

    @Test
    void testGenerateThreeLevelTree() {
        BalancedBST tree = new BalancedBST();
        int[] input = new int[] {14, 6, 12, 10, 3, 18, 11};
        tree.GenerateTree(input);

        assertEquals(11, tree.Root.NodeKey);
        assertEquals(0, tree.Root.Level);

        assertEquals(6, tree.Root.LeftChild.NodeKey);
        assertEquals(1, tree.Root.LeftChild.Level);
        assertEquals(11, tree.Root.LeftChild.Parent.NodeKey);

        assertEquals(3, tree.Root.LeftChild.LeftChild.NodeKey);
        assertEquals(2, tree.Root.LeftChild.LeftChild.Level);
        assertEquals(6, tree.Root.LeftChild.LeftChild.Parent.NodeKey);

        assertEquals(10, tree.Root.LeftChild.RightChild.NodeKey);
        assertEquals(2, tree.Root.LeftChild.RightChild.Level);
        assertEquals(6, tree.Root.LeftChild.RightChild.Parent.NodeKey);

        assertEquals(14, tree.Root.RightChild.NodeKey);
        assertEquals(1, tree.Root.RightChild.Level);
        assertEquals(11, tree.Root.RightChild.Parent.NodeKey);

        assertEquals(12, tree.Root.RightChild.LeftChild.NodeKey);
        assertEquals(2, tree.Root.RightChild.LeftChild.Level);
        assertEquals(14, tree.Root.RightChild.LeftChild.Parent.NodeKey);

        assertEquals(18, tree.Root.RightChild.RightChild.NodeKey);
        assertEquals(2, tree.Root.RightChild.RightChild.Level);
        assertEquals(14, tree.Root.RightChild.RightChild.Parent.NodeKey);
    }

    @Test
    void testIsEmptyTreeNotBalanced() {
        BalancedBST emptyTree = new BalancedBST();
        emptyTree.GenerateTree(new int[0]);

        assertFalse(emptyTree.IsBalanced(emptyTree.Root));
    }

    @Test
    void testIsSingleNodeTreeBalanced() {
        BalancedBST tree = new BalancedBST();
        int[] inputArray = new int[] {5};
        tree.GenerateTree(inputArray);

        assertTrue(tree.IsBalanced(tree.Root));
    }

    @Test
    void testIsTwoLevelTreeBalanced() {
        BalancedBST tree = new BalancedBST();
        int[] inputArray = new int[] {8, 12, 5};
        tree.GenerateTree(inputArray);

        assertTrue(tree.IsBalanced(tree.Root));
    }

    @Test
    void isThreeLevelTreeBalanced() {
        BalancedBST tree = new BalancedBST();
        int[] input = new int[] {14, 6, 12, 10, 3, 18, 11};
        tree.GenerateTree(input);

        assertTrue(tree.IsBalanced(tree.Root));
    }

    @Test
    void isTwoNodeTreeBalanced() {
        BalancedBST tree = new BalancedBST();
        BSTNode root = new BSTNode(11, null);
        tree.Root = root;

        BSTNode leftChild = new BSTNode(6, root);
        root.LeftChild = leftChild;

        assertTrue(tree.IsBalanced(root));
    }

    @Test
    void testIsNotBalanced() {
        BalancedBST tree = new BalancedBST();
        BSTNode root = new BSTNode(11, null);
        tree.Root = root;

        BSTNode leftChild = new BSTNode(6, root);
        BSTNode rightChild = new BSTNode(14, root);
        root.LeftChild = leftChild; root.RightChild = rightChild;

        BSTNode leftLeftChild = new BSTNode(3, leftChild);
        BSTNode rightLeftChild = new BSTNode(10, leftChild);
        leftChild.LeftChild = leftLeftChild; leftChild.RightChild = rightLeftChild;

        BSTNode leftRightLeftChild = new BSTNode(7, rightLeftChild);
        rightLeftChild.LeftChild = leftRightLeftChild;

        assertFalse(tree.IsBalanced(root));
    }

    @Test
    void testIsEmptyTreeBST() {
        BalancedBST emptyTree = new BalancedBST();
        emptyTree.GenerateTree(new int[0]);

        assertFalse(emptyTree.isBST());
    }

    @Test
    void isSingleNodeTreeBST() {
        BalancedBST tree = new BalancedBST();
        int[] inputArray = new int[] {5};
        tree.GenerateTree(inputArray);

        assertTrue(tree.isBST());
    }

    @Test
    void isTwoLevelTreeBST() {
        BalancedBST tree = new BalancedBST();
        int[] inputArray = new int[] {8, 12, 5};
        tree.GenerateTree(inputArray);

        assertTrue(tree.isBST());
    }

    @Test
    void isThreeLevelTreeBST() {
        BalancedBST tree = new BalancedBST();
        int[] input = new int[] {14, 6, 12, 10, 3, 18, 11};
        tree.GenerateTree(input);

        assertTrue(tree.isBST());
    }

    @Test
    void isNotFullTreeBST() {
        BalancedBST tree = new BalancedBST();
        BSTNode root = new BSTNode(11, null);
        tree.Root = root;

        BSTNode leftChild = new BSTNode(6, root);
        root.LeftChild = leftChild;

        assertTrue(tree.isBST());
    }

    @Test
    void isNotFullTreeNotBST() {
        BalancedBST tree = new BalancedBST();
        BSTNode root = new BSTNode(11, null);
        tree.Root = root;

        BSTNode leftChild = new BSTNode(11, root);
        root.LeftChild = leftChild;

        assertFalse(tree.isBST());
    }

    @Test
    void testTwoLevelTreeIsNotBST() {
        BalancedBST tree = new BalancedBST();
        BSTNode root = new BSTNode(11, null);
        tree.Root = root;

        BSTNode leftChild = new BSTNode(14, root);
        BSTNode rightChild = new BSTNode(6, root);
        root.LeftChild = leftChild; root.RightChild = rightChild;

        assertFalse(tree.isBST());
    }

    @Test
    void testIsNotBST() {
        BalancedBST tree = new BalancedBST();
        BSTNode root = new BSTNode(11, null);
        tree.Root = root;

        BSTNode leftChild = new BSTNode(6, root);
        BSTNode rightChild = new BSTNode(14, root);
        root.LeftChild = leftChild; root.RightChild = rightChild;

        BSTNode leftLeftChild = new BSTNode(3, leftChild);
        BSTNode rightLeftChild = new BSTNode(13, leftChild);
        leftChild.LeftChild = leftLeftChild; leftChild.RightChild = rightLeftChild;

        assertFalse(tree.isBST());
    }
}


import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BSTTest {

    @Test
    void testFindMissingNodeByKeyToLeft() {
        BSTNode<Integer> root = new BSTNode<>(10, 10, null);
        BST<Integer> bst = new BST<>(root);
        BSTNode<Integer> leftNode = new BSTNode<>(7, 7, root);
        BSTNode<Integer> rightNode = new BSTNode<>(15, 15, root);
        root.LeftChild = leftNode; root.RightChild = rightNode;
        BSTNode<Integer> leftLeftNode = new BSTNode<>(6, 6, leftNode);
        BSTNode<Integer> rightLeftNode = new BSTNode<>(8, 8, leftNode);
        leftNode.LeftChild = leftLeftNode; leftNode.RightChild = rightLeftNode;
        BSTNode<Integer> leftRightNode = new BSTNode<>(12, 12, rightNode);
        BSTNode<Integer> rightRightNode = new BSTNode<>(16, 16, rightNode);
        rightNode.LeftChild = leftRightNode; rightNode.RightChild = rightRightNode;

        BSTFind<Integer> expectedResult = new BSTFind<>();
        expectedResult.Node = leftRightNode;
        expectedResult.NodeHasKey = false;
        expectedResult.ToLeft = true;

        int keyToFind = 11;
        assertEquals(expectedResult, bst.FindNodeByKey(keyToFind));
    }

    @Test
    void testFindMissingNodeByKeyToRight() {
        BSTNode<Integer> root = new BSTNode<>(10, 10, null);
        BST<Integer> bst = new BST<>(root);
        BSTNode<Integer> leftNode = new BSTNode<>(7, 7, root);
        BSTNode<Integer> rightNode = new BSTNode<>(15, 15, root);
        root.LeftChild = leftNode; root.RightChild = rightNode;
        BSTNode<Integer> leftLeftNode = new BSTNode<>(6, 6, leftNode);
        BSTNode<Integer> rightLeftNode = new BSTNode<>(8, 8, leftNode);
        leftNode.LeftChild = leftLeftNode; leftNode.RightChild = rightLeftNode;
        BSTNode<Integer> leftRightNode = new BSTNode<>(12, 12, rightNode);
        BSTNode<Integer> rightRightNode = new BSTNode<>(16, 16, rightNode);
        rightNode.LeftChild = leftRightNode; rightNode.RightChild = rightRightNode;

        BSTFind<Integer> expectedResult = new BSTFind<>();
        expectedResult.Node = rightLeftNode;
        expectedResult.NodeHasKey = false;
        expectedResult.ToLeft = false;

        int keyToFind = 9;
        assertEquals(expectedResult, bst.FindNodeByKey(keyToFind));
    }

    @Test
    void testFindNodeByKey() {
        BSTNode<Integer> root = new BSTNode<>(10, 10, null);
        BST<Integer> bst = new BST<>(root);
        BSTNode<Integer> leftNode = new BSTNode<>(7, 7, root);
        BSTNode<Integer> rightNode = new BSTNode<>(15, 15, root);
        root.LeftChild = leftNode; root.RightChild = rightNode;
        BSTNode<Integer> leftLeftNode = new BSTNode<>(6, 6, leftNode);
        BSTNode<Integer> rightLeftNode = new BSTNode<>(8, 8, leftNode);
        leftNode.LeftChild = leftLeftNode; leftNode.RightChild = rightLeftNode;
        BSTNode<Integer> leftRightNode = new BSTNode<>(12, 12, rightNode);
        BSTNode<Integer> rightRightNode = new BSTNode<>(16, 16, rightNode);
        rightNode.LeftChild = leftRightNode; rightNode.RightChild = rightRightNode;

        BSTFind<Integer> expectedResult = new BSTFind<>();
        expectedResult.Node = leftRightNode;
        expectedResult.NodeHasKey = true;

        int keyToFind = 12;
        assertEquals(expectedResult, bst.FindNodeByKey(keyToFind));
    }

    @Test
    void testAddMissingKeyValueToLeft() {
        BSTNode<Integer> root = new BSTNode<>(10, 10, null);
        BST<Integer> bst = new BST<>(root);
        BSTNode<Integer> leftNode = new BSTNode<>(7, 7, root);
        BSTNode<Integer> rightNode = new BSTNode<>(15, 15, root);
        root.LeftChild = leftNode; root.RightChild = rightNode;
        BSTNode<Integer> leftLeftNode = new BSTNode<>(6, 6, leftNode);
        BSTNode<Integer> rightLeftNode = new BSTNode<>(8, 8, leftNode);
        leftNode.LeftChild = leftLeftNode; leftNode.RightChild = rightLeftNode;
        BSTNode<Integer> leftRightNode = new BSTNode<>(12, 12, rightNode);
        BSTNode<Integer> rightRightNode = new BSTNode<>(16, 16, rightNode);
        rightNode.LeftChild = leftRightNode; rightNode.RightChild = rightRightNode;

        int keyToAdd = 11;
        assertFalse(bst.FindNodeByKey(keyToAdd).NodeHasKey);
        assertTrue(bst.AddKeyValue(keyToAdd, 11));
        BSTFind<Integer> findResultAfterAdd = bst.FindNodeByKey(keyToAdd);
        assertEquals(leftRightNode, findResultAfterAdd.Node.Parent);
        assertEquals(findResultAfterAdd.Node, leftRightNode.LeftChild);
        assertTrue(findResultAfterAdd.NodeHasKey);
    }

    @Test
    void testAddMissingKeyValueToRight() {
        BSTNode<Integer> root = new BSTNode<>(10, 10, null);
        BST<Integer> bst = new BST<>(root);
        BSTNode<Integer> leftNode = new BSTNode<>(7, 7, root);
        BSTNode<Integer> rightNode = new BSTNode<>(15, 15, root);
        root.LeftChild = leftNode; root.RightChild = rightNode;
        BSTNode<Integer> leftLeftNode = new BSTNode<>(6, 6, leftNode);
        BSTNode<Integer> rightLeftNode = new BSTNode<>(8, 8, leftNode);
        leftNode.LeftChild = leftLeftNode; leftNode.RightChild = rightLeftNode;
        BSTNode<Integer> leftRightNode = new BSTNode<>(12, 12, rightNode);
        BSTNode<Integer> rightRightNode = new BSTNode<>(16, 16, rightNode);
        rightNode.LeftChild = leftRightNode; rightNode.RightChild = rightRightNode;

        int keyToAdd = 18;
        assertFalse(bst.FindNodeByKey(keyToAdd).NodeHasKey);
        assertTrue(bst.AddKeyValue(keyToAdd, 18));
        BSTFind<Integer> findResultAfterAdd = bst.FindNodeByKey(keyToAdd);
        assertEquals(rightRightNode, findResultAfterAdd.Node.Parent);
        assertEquals(findResultAfterAdd.Node, rightRightNode.RightChild);
        assertTrue(findResultAfterAdd.NodeHasKey);
    }

    @Test
    void testAddExistingKeyValue() {
        BSTNode<Integer> root = new BSTNode<>(10, 10, null);
        BST<Integer> bst = new BST<>(root);
        BSTNode<Integer> leftNode = new BSTNode<>(7, 7, root);
        BSTNode<Integer> rightNode = new BSTNode<>(15, 15, root);
        root.LeftChild = leftNode; root.RightChild = rightNode;
        BSTNode<Integer> leftLeftNode = new BSTNode<>(6, 6, leftNode);
        BSTNode<Integer> rightLeftNode = new BSTNode<>(8, 8, leftNode);
        leftNode.LeftChild = leftLeftNode; leftNode.RightChild = rightLeftNode;
        BSTNode<Integer> leftRightNode = new BSTNode<>(12, 12, rightNode);
        BSTNode<Integer> rightRightNode = new BSTNode<>(16, 16, rightNode);
        rightNode.LeftChild = leftRightNode; rightNode.RightChild = rightRightNode;

        int keyToAdd = 8;
        assertTrue(bst.FindNodeByKey(keyToAdd).NodeHasKey);
        assertFalse(bst.AddKeyValue(keyToAdd, 8));
        assertTrue(bst.FindNodeByKey(keyToAdd).NodeHasKey);
    }

    @Test
    void deleteNodeWithoutChildrenByKey() {
        BSTNode<Integer> root = new BSTNode<>(10, 10, null);
        BST<Integer> bst = new BST<>(root);
        BSTNode<Integer> leftNode = new BSTNode<>(7, 7, root);
        BSTNode<Integer> rightNode = new BSTNode<>(15, 15, root);
        root.LeftChild = leftNode; root.RightChild = rightNode;
        BSTNode<Integer> leftLeftNode = new BSTNode<>(6, 6, leftNode);
        BSTNode<Integer> rightLeftNode = new BSTNode<>(8, 8, leftNode);
        leftNode.LeftChild = leftLeftNode; leftNode.RightChild = rightLeftNode;
        BSTNode<Integer> leftRightNode = new BSTNode<>(12, 12, rightNode);
        BSTNode<Integer> rightRightNode = new BSTNode<>(16, 16, rightNode);
        rightNode.LeftChild = leftRightNode; rightNode.RightChild = rightRightNode;

        int keyToDelete = 8;
        assertEquals(rightLeftNode.NodeKey, leftNode.RightChild.NodeKey);
        assertTrue(bst.DeleteNodeByKey(keyToDelete));
        assertNull(leftNode.RightChild);
    }

    @Test
    void deleteNodeWithOneChildByKey() {
        BSTNode<Integer> root = new BSTNode<>(10, 10, null);
        BST<Integer> bst = new BST<>(root);
        BSTNode<Integer> leftNode = new BSTNode<>(7, 7, root);
        BSTNode<Integer> rightNode = new BSTNode<>(15, 15, root);
        root.LeftChild = leftNode; root.RightChild = rightNode;
        BSTNode<Integer> leftLeftNode = new BSTNode<>(6, 6, leftNode);
        BSTNode<Integer> rightLeftNode = new BSTNode<>(8, 8, leftNode);
        leftNode.LeftChild = leftLeftNode; leftNode.RightChild = rightLeftNode;
        BSTNode<Integer> leftRightNode = new BSTNode<>(12, 12, rightNode);
        BSTNode<Integer> rightRightNode = new BSTNode<>(16, 16, rightNode);
        rightNode.LeftChild = leftRightNode; rightNode.RightChild = rightRightNode;
        BSTNode<Integer> rightLeftRightNode = new BSTNode<>(14, 14, leftRightNode);
        leftRightNode.RightChild = rightLeftRightNode;

        int keyToDelete = 12;
        assertEquals(leftRightNode.NodeKey, rightNode.LeftChild.NodeKey);
        assertTrue(bst.DeleteNodeByKey(keyToDelete));
        assertEquals(rightLeftRightNode.NodeKey, rightNode.LeftChild.NodeKey);
    }@Test
    void deleteNodeWithChildrenByKey() {
        BSTNode<Integer> root = new BSTNode<>(10, 10, null);
        BST<Integer> bst = new BST<>(root);
        BSTNode<Integer> leftNode = new BSTNode<>(7, 7, root);
        BSTNode<Integer> rightNode = new BSTNode<>(15, 15, root);
        root.LeftChild = leftNode; root.RightChild = rightNode;
        BSTNode<Integer> leftLeftNode = new BSTNode<>(6, 6, leftNode);
        BSTNode<Integer> rightLeftNode = new BSTNode<>(8, 8, leftNode);
        leftNode.LeftChild = leftLeftNode; leftNode.RightChild = rightLeftNode;
        BSTNode<Integer> leftRightNode = new BSTNode<>(12, 12, rightNode);
        BSTNode<Integer> rightRightNode = new BSTNode<>(16, 16, rightNode);
        rightNode.LeftChild = leftRightNode; rightNode.RightChild = rightRightNode;
        BSTNode<Integer> rightLeftRightNode = new BSTNode<>(14, 14, leftRightNode);
        leftRightNode.RightChild = rightLeftRightNode;

        int keyToDelete = 10;
        assertEquals(keyToDelete, root.NodeKey);
        assertTrue(bst.DeleteNodeByKey(keyToDelete));
        assertEquals(12, bst.Root.NodeKey);
        assertEquals(15, bst.Root.RightChild.NodeKey);
        assertEquals(14, bst.Root.RightChild.LeftChild.NodeKey);
        assertEquals(16, bst.Root.RightChild.RightChild.NodeKey);
    }

    @Test
    void testDeleteLastNodeByKey() {
        BSTNode<Integer> root = new BSTNode<>(10, 10, null);
        BST<Integer> bst = new BST<>(root);
        assertTrue(bst.FindNodeByKey(root.NodeKey).NodeHasKey);
        assertTrue(bst.DeleteNodeByKey(root.NodeKey));
        assertNull(bst.Root);
        assertNull(bst.FindNodeByKey(root.NodeKey).Node);
    }

    @Test
    void testDeleteRootWithLeftChild() {
        BSTNode<Integer> root = new BSTNode<>(10, 10, null);
        BST<Integer> bst = new BST<>(root);
        BSTNode<Integer> rightNode = new BSTNode<>(15, 15, root);
        root.RightChild = rightNode;

        assertTrue(bst.FindNodeByKey(root.NodeKey).NodeHasKey);
        assertTrue(bst.DeleteNodeByKey(root.NodeKey));
        assertEquals(15, bst.Root.NodeKey);
        assertNull(bst.Root.Parent);
        assertNull(bst.Root.LeftChild);
        assertNull(bst.Root.RightChild);
    }

    @Test
    void testDeleteNodeWithLeafsChildrenByKey() {
        BSTNode<Integer> root = new BSTNode<>(10, 10, null);
        BST<Integer> bst = new BST<>(root);
        BSTNode<Integer> leftNode = new BSTNode<>(7, 7, root);
        BSTNode<Integer> rightNode = new BSTNode<>(15, 15, root);
        root.LeftChild = leftNode; root.RightChild = rightNode;

        assertTrue(bst.FindNodeByKey(root.NodeKey).NodeHasKey);
        assertTrue(bst.DeleteNodeByKey(root.NodeKey));
        assertEquals(15, bst.Root.NodeKey);
        assertNull(bst.Root.Parent);
        assertEquals(7, bst.Root.LeftChild.NodeKey);
        assertNull(bst.Root.RightChild);
    }

    @Test
    void testDeleteRootWithRightChild() {
        BSTNode<Integer> root = new BSTNode<>(10, 10, null);
        BST<Integer> bst = new BST<>(root);
        BSTNode<Integer> leftNode = new BSTNode<>(7, 7, root);
        root.LeftChild = leftNode;

        assertTrue(bst.FindNodeByKey(root.NodeKey).NodeHasKey);
        assertTrue(bst.DeleteNodeByKey(root.NodeKey));
        assertEquals(7, bst.Root.NodeKey);
        assertNull(bst.Root.Parent);
        assertNull(bst.Root.LeftChild);
        assertNull(bst.Root.RightChild);
    }

    @Test
    void count() {
        BSTNode<Integer> root = new BSTNode<>(10, 10, null);
        BST<Integer> bst = new BST<>(root);
        BSTNode<Integer> leftNode = new BSTNode<>(7, 7, root);
        BSTNode<Integer> rightNode = new BSTNode<>(15, 15, root);
        root.LeftChild = leftNode; root.RightChild = rightNode;
        BSTNode<Integer> leftLeftNode = new BSTNode<>(6, 6, leftNode);
        BSTNode<Integer> rightLeftNode = new BSTNode<>(8, 8, leftNode);
        leftNode.LeftChild = leftLeftNode; leftNode.RightChild = rightLeftNode;
        BSTNode<Integer> leftRightNode = new BSTNode<>(12, 12, rightNode);
        BSTNode<Integer> rightRightNode = new BSTNode<>(16, 16, rightNode);
        rightNode.LeftChild = leftRightNode; rightNode.RightChild = rightRightNode;
        BSTNode<Integer> rightLeftRightNode = new BSTNode<>(14, 14, leftRightNode);
        leftRightNode.RightChild = rightLeftRightNode;

        assertEquals(8, bst.Count());
    }

    @Test
    void findMinFromRoot() {
        BSTNode<Integer> root = new BSTNode<>(10, 10, null);
        BST<Integer> bst = new BST<>(root);
        BSTNode<Integer> leftNode = new BSTNode<>(7, 7, root);
        BSTNode<Integer> rightNode = new BSTNode<>(15, 15, root);
        root.LeftChild = leftNode; root.RightChild = rightNode;
        BSTNode<Integer> leftLeftNode = new BSTNode<>(6, 6, leftNode);
        BSTNode<Integer> rightLeftNode = new BSTNode<>(8, 8, leftNode);
        leftNode.LeftChild = leftLeftNode; leftNode.RightChild = rightLeftNode;
        BSTNode<Integer> leftRightNode = new BSTNode<>(12, 12, rightNode);
        BSTNode<Integer> rightRightNode = new BSTNode<>(16, 16, rightNode);
        rightNode.LeftChild = leftRightNode; rightNode.RightChild = rightRightNode;
        BSTNode<Integer> rightLeftRightNode = new BSTNode<>(14, 14, leftRightNode);
        leftRightNode.RightChild = rightLeftRightNode;

        assertEquals(6, bst.FinMinMax(root, false).NodeKey);
    }

    @Test
    void findMinFromNode() {
        BSTNode<Integer> root = new BSTNode<>(10, 10, null);
        BST<Integer> bst = new BST<>(root);
        BSTNode<Integer> leftNode = new BSTNode<>(7, 7, root);
        BSTNode<Integer> rightNode = new BSTNode<>(15, 15, root);
        root.LeftChild = leftNode; root.RightChild = rightNode;
        BSTNode<Integer> leftLeftNode = new BSTNode<>(6, 6, leftNode);
        BSTNode<Integer> rightLeftNode = new BSTNode<>(8, 8, leftNode);
        leftNode.LeftChild = leftLeftNode; leftNode.RightChild = rightLeftNode;
        BSTNode<Integer> leftRightNode = new BSTNode<>(12, 12, rightNode);
        BSTNode<Integer> rightRightNode = new BSTNode<>(16, 16, rightNode);
        rightNode.LeftChild = leftRightNode; rightNode.RightChild = rightRightNode;
        BSTNode<Integer> rightLeftRightNode = new BSTNode<>(14, 14, leftRightNode);
        leftRightNode.RightChild = rightLeftRightNode;

        assertEquals(12, bst.FinMinMax(rightNode, false).NodeKey);
    }

    @Test
    void findMaxFromRoot() {
        BSTNode<Integer> root = new BSTNode<>(10, 10, null);
        BST<Integer> bst = new BST<>(root);
        BSTNode<Integer> leftNode = new BSTNode<>(7, 7, root);
        BSTNode<Integer> rightNode = new BSTNode<>(15, 15, root);
        root.LeftChild = leftNode; root.RightChild = rightNode;
        BSTNode<Integer> leftLeftNode = new BSTNode<>(6, 6, leftNode);
        BSTNode<Integer> rightLeftNode = new BSTNode<>(8, 8, leftNode);
        leftNode.LeftChild = leftLeftNode; leftNode.RightChild = rightLeftNode;
        BSTNode<Integer> leftRightNode = new BSTNode<>(12, 12, rightNode);
        BSTNode<Integer> rightRightNode = new BSTNode<>(16, 16, rightNode);
        rightNode.LeftChild = leftRightNode; rightNode.RightChild = rightRightNode;
        BSTNode<Integer> rightLeftRightNode = new BSTNode<>(14, 14, leftRightNode);
        leftRightNode.RightChild = rightLeftRightNode;

        assertEquals(rightRightNode.NodeKey, bst.FinMinMax(root, true).NodeKey);
    }

    @Test
    void findMaxFromNode() {
        BSTNode<Integer> root = new BSTNode<>(10, 10, null);
        BST<Integer> bst = new BST<>(root);
        BSTNode<Integer> leftNode = new BSTNode<>(7, 7, root);
        BSTNode<Integer> rightNode = new BSTNode<>(15, 15, root);
        root.LeftChild = leftNode; root.RightChild = rightNode;
        BSTNode<Integer> leftLeftNode = new BSTNode<>(6, 6, leftNode);
        BSTNode<Integer> rightLeftNode = new BSTNode<>(8, 8, leftNode);
        leftNode.LeftChild = leftLeftNode; leftNode.RightChild = rightLeftNode;
        BSTNode<Integer> leftRightNode = new BSTNode<>(12, 12, rightNode);
        BSTNode<Integer> rightRightNode = new BSTNode<>(16, 16, rightNode);
        rightNode.LeftChild = leftRightNode; rightNode.RightChild = rightRightNode;
        BSTNode<Integer> rightLeftRightNode = new BSTNode<>(14, 14, leftRightNode);
        leftRightNode.RightChild = rightLeftRightNode;
        BSTNode<Integer> rightRightRightNode = new BSTNode<>(20, 20, rightRightNode);
        rightRightNode.RightChild = rightRightRightNode;

        assertEquals(rightRightRightNode.NodeKey, bst.FinMinMax(rightNode, true).NodeKey);
    }

    @Test
    void testIsEmptyTreesEquivalent() {
        BST<Boolean> firstEmptyTree = new BST<>(null);
        BST<Boolean> secondEmptyTree = new BST<>(null);

        assertTrue(firstEmptyTree.isEquivalent(firstEmptyTree));
        assertTrue(secondEmptyTree.isEquivalent(secondEmptyTree));
        assertTrue(firstEmptyTree.isEquivalent(secondEmptyTree));
        assertTrue(secondEmptyTree.isEquivalent(firstEmptyTree));
    }

    @Test
    void testIsSingleNodeTreesEquivalent() {
        BSTNode<Long> firstRoot = new BSTNode<>(1, 1L, null);
        BSTNode<Long> secondRoot = new BSTNode<>(1, 1L, null);

        BST<Long> firstTree = new BST<>(firstRoot);
        BST<Long> secondTree = new BST<>(secondRoot);

        assertTrue(firstTree.isEquivalent(firstTree));
        assertTrue(secondTree.isEquivalent(secondTree));
        assertTrue(firstTree.isEquivalent(secondTree));
        assertTrue(secondTree.isEquivalent(firstTree));
    }

    @Test
    void testIsSingleNodeTreesNotEquivalent() {
        BSTNode<Long> firstRoot = new BSTNode<>(1, 1L, null);
        BSTNode<Long> secondRoot = new BSTNode<>(2, 4L, null);

        BST<Long> firstTree = new BST<>(firstRoot);
        BST<Long> secondTree = new BST<>(secondRoot);

        assertTrue(firstTree.isEquivalent(firstTree));
        assertTrue(secondTree.isEquivalent(secondTree));
        assertFalse(firstTree.isEquivalent(secondTree));
        assertFalse(secondTree.isEquivalent(firstTree));
    }

    @Test
    void testIsTreesWithRootAndOneSideChildEquivalent() {
        BSTNode<Long> firstRoot = new BSTNode<>(1, 1L, null);
        BSTNode<Long> secondRoot = new BSTNode<>(1, 1L, null);

        BST<Long> firstTree = new BST<>(firstRoot);
        BST<Long> secondTree = new BST<>(secondRoot);

        BSTNode<Long> firstChild = new BSTNode<>(2, 4L, firstRoot);
        BSTNode<Long> secondChild = new BSTNode<>(2, 4L, secondRoot);
        firstRoot.LeftChild = firstChild; secondRoot.LeftChild = secondChild;

        assertTrue(firstTree.isEquivalent(firstTree));
        assertTrue(secondTree.isEquivalent(secondTree));
        assertTrue(firstTree.isEquivalent(secondTree));
        assertTrue(secondTree.isEquivalent(firstTree));
    }

    @Test
    void testIsTreesWithRootAndDiffSideChildNotEquivalent() {
        BSTNode<Long> firstRoot = new BSTNode<>(1, 1L, null);
        BSTNode<Long> secondRoot = new BSTNode<>(1, 1L, null);

        BST<Long> firstTree = new BST<>(firstRoot);
        BST<Long> secondTree = new BST<>(secondRoot);

        BSTNode<Long> firstChild = new BSTNode<>(2, 4L, firstRoot);
        BSTNode<Long> secondChild = new BSTNode<>(2, 4L, secondRoot);
        firstRoot.LeftChild = firstChild; secondRoot.RightChild = secondChild;

        assertTrue(firstTree.isEquivalent(firstTree));
        assertTrue(secondTree.isEquivalent(secondTree));
        assertFalse(firstTree.isEquivalent(secondTree));
        assertFalse(secondTree.isEquivalent(firstTree));
    }

    @Test
    void testIsTripleNodeTreesEquivalent() {
        BSTNode<Long> firstRoot = new BSTNode<>(1, 1L, null);
        BSTNode<Long> secondRoot = new BSTNode<>(1, 1L, null);

        BST<Long> firstTree = new BST<>(firstRoot);
        BST<Long> secondTree = new BST<>(secondRoot);

        BSTNode<Long> leftChildOfFirstRoot = new BSTNode<>(2, 4L, firstRoot);
        BSTNode<Long> leftChildOfSecondRoot = new BSTNode<>(2, 4L, secondRoot);
        firstRoot.LeftChild = leftChildOfFirstRoot; secondRoot.LeftChild = leftChildOfSecondRoot;
        BSTNode<Long> rightChildOfFirstRoot = new BSTNode<>(3, 9L, firstRoot);
        BSTNode<Long> rightChildOfSecondRoot = new BSTNode<>(3, 9L, secondRoot);
        firstRoot.LeftChild = rightChildOfFirstRoot; secondRoot.LeftChild = rightChildOfSecondRoot;

        assertTrue(firstTree.isEquivalent(firstTree));
        assertTrue(secondTree.isEquivalent(secondTree));
        assertTrue(firstTree.isEquivalent(secondTree));
        assertTrue(secondTree.isEquivalent(firstTree));
    }

    @Test
    void testIsTripleNodeTreesNotEquivalent() {
        BSTNode<Long> firstRoot = new BSTNode<>(1, 1L, null);
        BSTNode<Long> secondRoot = new BSTNode<>(1, 1L, null);

        BST<Long> firstTree = new BST<>(firstRoot);
        BST<Long> secondTree = new BST<>(secondRoot);

        BSTNode<Long> leftChildOfFirstRoot = new BSTNode<>(2, 4L, firstRoot);
        BSTNode<Long> leftChildOfSecondRoot = new BSTNode<>(3, 9L, secondRoot);
        firstRoot.LeftChild = leftChildOfFirstRoot; secondRoot.LeftChild = leftChildOfSecondRoot;
        BSTNode<Long> rightChildOfFirstRoot = new BSTNode<>(3, 9L, firstRoot);
        BSTNode<Long> rightChildOfSecondRoot = new BSTNode<>(2, 4L, secondRoot);
        firstRoot.LeftChild = rightChildOfFirstRoot; secondRoot.LeftChild = rightChildOfSecondRoot;

        assertTrue(firstTree.isEquivalent(firstTree));
        assertTrue(secondTree.isEquivalent(secondTree));
        assertFalse(firstTree.isEquivalent(secondTree));
        assertFalse(secondTree.isEquivalent(firstTree));
    }

    @Test
    void isMultiNodeTreesEquivalent() {
        BSTNode<Integer> firstRoot = new BSTNode<>(10, 10, null);
        BST<Integer> firstTree = new BST<>(firstRoot);
        BSTNode<Integer> leftNode = new BSTNode<>(7, 7, firstRoot);
        BSTNode<Integer> rightNode = new BSTNode<>(15, 15, firstRoot);
        firstRoot.LeftChild = leftNode; firstRoot.RightChild = rightNode;
        BSTNode<Integer> leftLeftNode = new BSTNode<>(6, 6, leftNode);
        BSTNode<Integer> rightLeftNode = new BSTNode<>(8, 8, leftNode);
        leftNode.LeftChild = leftLeftNode; leftNode.RightChild = rightLeftNode;
        BSTNode<Integer> leftRightNode = new BSTNode<>(12, 12, rightNode);
        BSTNode<Integer> rightRightNode = new BSTNode<>(16, 16, rightNode);
        rightNode.LeftChild = leftRightNode; rightNode.RightChild = rightRightNode;
        BSTNode<Integer> rightLeftRightNode = new BSTNode<>(14, 14, leftRightNode);
        leftRightNode.RightChild = rightLeftRightNode;
        BSTNode<Integer> rightRightRightNode = new BSTNode<>(20, 20, rightRightNode);
        rightRightNode.RightChild = rightRightRightNode;

        BSTNode<Integer> secondRoot = new BSTNode<>(10, 10, null);
        BST<Integer> secondTree = new BST<>(secondRoot);
        secondTree.AddKeyValue(7, 7);
        secondTree.AddKeyValue(15, 15);
        secondTree.AddKeyValue(6, 6);
        secondTree.AddKeyValue(8, 8);
        secondTree.AddKeyValue(12, 12);
        secondTree.AddKeyValue(16, 16);
        secondTree.AddKeyValue(14, 14);
        secondTree.AddKeyValue(20, 20);

        assertTrue(firstTree.isEquivalent(firstTree));
        assertTrue(secondTree.isEquivalent(secondTree));
        assertTrue(firstTree.isEquivalent(secondTree));
        assertTrue(secondTree.isEquivalent(firstTree));
    }

    @Test
    void isMultiNodeTreesNotEquivalent() {
        BSTNode<Integer> firstRoot = new BSTNode<>(10, 10, null);
        BST<Integer> firstTree = new BST<>(firstRoot);
        BSTNode<Integer> leftNode = new BSTNode<>(7, 7, firstRoot);
        BSTNode<Integer> rightNode = new BSTNode<>(15, 15, firstRoot);
        firstRoot.LeftChild = leftNode; firstRoot.RightChild = rightNode;
        BSTNode<Integer> leftLeftNode = new BSTNode<>(6, 6, leftNode);
        BSTNode<Integer> rightLeftNode = new BSTNode<>(8, 8, leftNode);
        leftNode.LeftChild = leftLeftNode; leftNode.RightChild = rightLeftNode;
        BSTNode<Integer> leftRightNode = new BSTNode<>(12, 12, rightNode);
        BSTNode<Integer> rightRightNode = new BSTNode<>(16, 16, rightNode);
        rightNode.LeftChild = leftRightNode; rightNode.RightChild = rightRightNode;
        BSTNode<Integer> rightLeftRightNode = new BSTNode<>(14, 14, leftRightNode);
        leftRightNode.RightChild = rightLeftRightNode;
        BSTNode<Integer> rightRightRightNode = new BSTNode<>(20, 20, rightRightNode);
        rightRightNode.RightChild = rightRightRightNode;

        BSTNode<Integer> secondRoot = new BSTNode<>(10, 10, null);
        BST<Integer> secondTree = new BST<>(secondRoot);
        secondTree.AddKeyValue(7, 7);
        secondTree.AddKeyValue(15, 15);
        secondTree.AddKeyValue(5, 5);
        secondTree.AddKeyValue(8, 8);
        secondTree.AddKeyValue(12, 12);
        secondTree.AddKeyValue(16, 16);
        secondTree.AddKeyValue(13, 13);
        secondTree.AddKeyValue(20, 20);

        assertTrue(firstTree.isEquivalent(firstTree));
        assertTrue(secondTree.isEquivalent(secondTree));
        assertFalse(firstTree.isEquivalent(secondTree));
        assertFalse(secondTree.isEquivalent(firstTree));
    }

    @Test
    void testGetRoutesOfReqLengthInEmptyTree() {
        BST<Double> emptyTree = new BST<>(null);

        assertTrue(emptyTree.getRoutesOfReqLength(0).isEmpty());
        assertTrue(emptyTree.getRoutesOfReqLength(1).isEmpty());
    }

    @Test
    void testGetRoutesOfReqLengthInSingleNodeTree() {
        BSTNode<BigDecimal> root = new BSTNode<>(3, BigDecimal.ONE, null);
        BST<BigDecimal> tree = new BST<>(root);

        List<List<BSTNode<BigDecimal>>> expectedRoutesList = List.of(List.of(root));
        assertEquals(expectedRoutesList, tree.getRoutesOfReqLength(0));
        assertTrue(tree.getRoutesOfReqLength(3).isEmpty());
    }

    @Test
    void testGetRoutesOfReqLength() {
        BSTNode<Integer> root = new BSTNode<>(10, 10, null);
        BST<Integer> tree = new BST<>(root);
        BSTNode<Integer> leftNode = new BSTNode<>(7, 7, root);
        BSTNode<Integer> rightNode = new BSTNode<>(15, 15, root);
        root.LeftChild = leftNode; root.RightChild = rightNode;
        BSTNode<Integer> leftLeftNode = new BSTNode<>(6, 6, leftNode);
        BSTNode<Integer> rightLeftNode = new BSTNode<>(8, 8, leftNode);
        leftNode.LeftChild = leftLeftNode; leftNode.RightChild = rightLeftNode;
        BSTNode<Integer> leftRightNode = new BSTNode<>(12, 12, rightNode);
        BSTNode<Integer> rightRightNode = new BSTNode<>(16, 16, rightNode);
        rightNode.LeftChild = leftRightNode; rightNode.RightChild = rightRightNode;
        BSTNode<Integer> rightLeftRightNode = new BSTNode<>(14, 14, leftRightNode);
        leftRightNode.RightChild = rightLeftRightNode;
        BSTNode<Integer> rightRightRightNode = new BSTNode<>(20, 20, rightRightNode);
        rightRightNode.RightChild = rightRightRightNode;

        List<List<BSTNode<Integer>>> routesWithLengthZero = List.of(List.of(root));
        List<List<BSTNode<Integer>>> routesWithLengthOne = List.of(List.of(root, leftNode), List.of(root, rightNode));
        List<List<BSTNode<Integer>>> routesWithLengthTwo = List.of(
                List.of(root, leftNode, leftLeftNode),
                List.of(root, leftNode, rightLeftNode),
                List.of(root, rightNode, leftRightNode),
                List.of(root, rightNode, rightRightNode)
        );
        List<List<BSTNode<Integer>>> routesWithLengthThree = List.of(
                List.of(root, rightNode, leftRightNode, rightLeftRightNode),
                List.of(root, rightNode, rightRightNode, rightRightRightNode)
        );

        assertEquals(routesWithLengthZero, tree.getRoutesOfReqLength(0));
        assertEquals(routesWithLengthOne, tree.getRoutesOfReqLength(1));
        assertEquals(routesWithLengthTwo, tree.getRoutesOfReqLength(2));
        assertEquals(routesWithLengthThree, tree.getRoutesOfReqLength(3));
        assertTrue(tree.getRoutesOfReqLength(4).isEmpty());
    }

    @Test
    void testGetRoutesWithMaxNodesValueSumInEmptyTree() {
        BST<Double> emptyTree = new BST<>(null);
        assertTrue(emptyTree.getRoutesWithMaxNodesValueSum().isEmpty());
    }

    @Test
    void testGetRoutsWithMaxNodesValueSumInSingleNodeTree() {
        BSTNode<Long> root = new BSTNode<>(8, 3L, null);
        BST<Long> tree = new BST<>(root);

        List<List<BSTNode<Long>>> expectedRoutesList = List.of(List.of(root));
        assertEquals(expectedRoutesList, tree.getRoutesWithMaxNodesValueSum());
    }

    @Test
    void testGetRoutesWithMaxNodesValueSumWithUnsupportedType() {
        BSTNode<String> root = new BSTNode<>(3, "as", null);
        BST<String> tree = new BST<>(root);
        assertThrows(ClassCastException.class, tree::getRoutesWithMaxNodesValueSum);
    }

    @Test
    void testGetRoutesWithMaxNodesValueSum() {
        BSTNode<Integer> root = new BSTNode<>(10, 2, null);
        BST<Integer> tree = new BST<>(root);
        BSTNode<Integer> leftNode = new BSTNode<>(7, 6, root);
        BSTNode<Integer> rightNode = new BSTNode<>(15, 1, root);
        root.LeftChild = leftNode; root.RightChild = rightNode;
        BSTNode<Integer> leftLeftNode = new BSTNode<>(6, 1, leftNode);
        BSTNode<Integer> rightLeftNode = new BSTNode<>(8, 2, leftNode);
        leftNode.LeftChild = leftLeftNode; leftNode.RightChild = rightLeftNode;
        BSTNode<Integer> leftRightNode = new BSTNode<>(12, 5, rightNode);
        BSTNode<Integer> rightRightNode = new BSTNode<>(16, 1, rightNode);
        rightNode.LeftChild = leftRightNode; rightNode.RightChild = rightRightNode;
        BSTNode<Integer> rightLeftRightNode = new BSTNode<>(14, 2, leftRightNode);
        leftRightNode.RightChild = rightLeftRightNode;
        BSTNode<Integer> rightRightRightNode = new BSTNode<>(20, 1, rightRightNode);
        rightRightNode.RightChild = rightRightRightNode;

        List<List<BSTNode<Integer>>> expectedRouteslist = List.of(List.of(root, leftNode, rightLeftNode),
                List.of(root, rightNode, leftRightNode, rightLeftRightNode));
        assertEquals(expectedRouteslist, tree.getRoutesWithMaxNodesValueSum());
    }

    @Test
    void isEmptyTreeSymmetrical() {
        BST<Integer> emptyTree = new BST<>(null);
        assertTrue(emptyTree.isSymmetrical());
    }

    @Test
    void isSingleNodeTreeSymmetrical() {
        BSTNode<String> root = new BSTNode<>(3, "asd", null);
        BST<String> tree = new BST<>(root);

        assertTrue(tree.isSymmetrical());
    }

    @Test
    void isTreeNotSymmetricalByValues() {
        BSTNode<Integer> root = new BSTNode<>(10, 10, null);
        BST<Integer> tree = new BST<>(root);
        BSTNode<Integer> leftNode = new BSTNode<>(7, 7, root);
        BSTNode<Integer> rightNode = new BSTNode<>(15, 15, root);
        root.LeftChild = leftNode; root.RightChild = rightNode;

        assertFalse(tree.isSymmetrical());
    }

    @Test
    void isTreeNotSymmetricalByStructure() {
        BSTNode<Integer> root = new BSTNode<>(10, 10, null);
        BST<Integer> tree = new BST<>(root);
        BSTNode<Integer> leftNode = new BSTNode<>(7, 3, root);
        BSTNode<Integer> rightNode = new BSTNode<>(15, 3, root);
        root.LeftChild = leftNode; root.RightChild = rightNode;
        BSTNode<Integer> leftLeftNode = new BSTNode<>(6, 2, leftNode);
        leftNode.LeftChild = leftLeftNode;

        assertFalse(tree.isSymmetrical());
    }

    @Test
    void testIsSymmetrical() {
        BSTNode<Integer> root = new BSTNode<>(10, 10, null);
        BST<Integer> tree = new BST<>(root);
        BSTNode<Integer> leftNode = new BSTNode<>(7, 5, root);
        BSTNode<Integer> rightNode = new BSTNode<>(15, 5, root);
        root.LeftChild = leftNode; root.RightChild = rightNode;
        BSTNode<Integer> leftLeftNode = new BSTNode<>(6, 2, leftNode);
        BSTNode<Integer> rightLeftNode = new BSTNode<>(9, 8, leftNode);
        leftNode.LeftChild = leftLeftNode; leftNode.RightChild = rightLeftNode;
        BSTNode<Integer> leftRightNode = new BSTNode<>(12, 8, rightNode);
        BSTNode<Integer> rightRightNode = new BSTNode<>(16, 2, rightNode);
        rightNode.LeftChild = leftRightNode; rightNode.RightChild = rightRightNode;
        BSTNode<Integer> leftRightLeftNode = new BSTNode<>(8, 1, rightLeftNode);
        BSTNode<Integer> rightLeftRightNode = new BSTNode<>(13, 1, leftRightNode);
        rightLeftNode.LeftChild = leftRightLeftNode; leftRightNode.RightChild = rightLeftRightNode;

        assertTrue(tree.isSymmetrical());
    }

    @Test
    void testWideAllNodesForEmptyTree() {
        BST<Boolean> tree = new BST<>(null);
        assertEquals(new ArrayList<>(), tree.WideAllNodes());
    }

    @Test
    void testWideAllNodesForSingleNodeTree() {
        BSTNode<String> root = new BSTNode<>(10, "test", null);
        BST<String> tree = new BST<>(root);

        assertEquals(new ArrayList<>(List.of(root)), tree.WideAllNodes());
    }

    @Test
    void testWideAllNodes() {
        BSTNode<Integer> root = new BSTNode<>(10, 2, null);
        BST<Integer> tree = new BST<>(root);
        BSTNode<Integer> leftNode = new BSTNode<>(7, 6, root);
        BSTNode<Integer> rightNode = new BSTNode<>(15, 1, root);
        root.LeftChild = leftNode; root.RightChild = rightNode;
        BSTNode<Integer> leftLeftNode = new BSTNode<>(6, 1, leftNode);
        BSTNode<Integer> rightLeftNode = new BSTNode<>(8, 2, leftNode);
        leftNode.LeftChild = leftLeftNode; leftNode.RightChild = rightLeftNode;
        BSTNode<Integer> leftRightNode = new BSTNode<>(12, 5, rightNode);
        BSTNode<Integer> rightRightNode = new BSTNode<>(16, 1, rightNode);
        rightNode.LeftChild = leftRightNode; rightNode.RightChild = rightRightNode;
        BSTNode<Integer> rightLeftRightNode = new BSTNode<>(14, 2, leftRightNode);
        leftRightNode.RightChild = rightLeftRightNode;
        BSTNode<Integer> rightRightRightNode = new BSTNode<>(20, 1, rightRightNode);
        rightRightNode.RightChild = rightRightRightNode;

        ArrayList<BSTNode> expectedRes = new ArrayList<>(List.of(root, leftNode, rightNode, leftLeftNode, rightLeftNode,
                leftRightNode, rightRightNode, rightLeftRightNode, rightRightRightNode));
        ArrayList<BSTNode> actualRes = tree.WideAllNodes();

        assertEquals(expectedRes, actualRes);
    }

    @Test
    void testDeepAllNodesInOderForEmptyList() {
        BST<Long> tree = new BST<>(null);
        assertEquals(new ArrayList<>(), tree.DeepAllNodes(0));
    }

    @Test
    void testDeepAllNodesInOrderForSingleNodeTree() {
        BSTNode<Double> root = new BSTNode<>(10, 0.2, null);
        BST<Double> tree = new BST<>(root);

        assertEquals(new ArrayList<>(List.of(root)), tree.WideAllNodes());
    }

    @Test
    void testDeepAllNodesInOrder() {
        BSTNode<Integer> root = new BSTNode<>(10, 2, null);
        BST<Integer> tree = new BST<>(root);
        BSTNode<Integer> leftNode = new BSTNode<>(7, 6, root);
        BSTNode<Integer> rightNode = new BSTNode<>(15, 1, root);
        root.LeftChild = leftNode; root.RightChild = rightNode;
        BSTNode<Integer> leftLeftNode = new BSTNode<>(6, 1, leftNode);
        BSTNode<Integer> rightLeftNode = new BSTNode<>(8, 2, leftNode);
        leftNode.LeftChild = leftLeftNode; leftNode.RightChild = rightLeftNode;
        BSTNode<Integer> leftRightNode = new BSTNode<>(12, 5, rightNode);
        BSTNode<Integer> rightRightNode = new BSTNode<>(16, 1, rightNode);
        rightNode.LeftChild = leftRightNode; rightNode.RightChild = rightRightNode;
        BSTNode<Integer> rightLeftRightNode = new BSTNode<>(14, 2, leftRightNode);
        leftRightNode.RightChild = rightLeftRightNode;
        BSTNode<Integer> rightRightRightNode = new BSTNode<>(20, 1, rightRightNode);
        rightRightNode.RightChild = rightRightRightNode;

        ArrayList<BSTNode> expectedRes = new ArrayList<>(List.of(leftLeftNode, leftNode, rightLeftNode, root,
                leftRightNode, rightLeftRightNode, rightNode, rightRightNode, rightRightRightNode));
        ArrayList<BSTNode> actualRes = tree.DeepAllNodes(0);

        assertEquals(expectedRes, actualRes);
    }

    @Test
    void testDeepAllNodesPostOrderForEmptyTree() {
        BST<Byte> tree = new BST<>(null);

        assertEquals(new ArrayList<>(), tree.DeepAllNodes(1));
    }

    @Test
    void testDeepAllNodesPostOrderForSingleNodeTree() {
        BSTNode<Character> root = new BSTNode<>(23, 'h', null);
        BST<Character> tree = new BST<>(root);

        assertEquals(new ArrayList<>(List.of(root)), tree.DeepAllNodes(1));
    }

    @Test
    void testDeepAllNodesPostOrder() {
        BSTNode<Integer> root = new BSTNode<>(10, 2, null);
        BST<Integer> tree = new BST<>(root);
        BSTNode<Integer> leftNode = new BSTNode<>(7, 6, root);
        BSTNode<Integer> rightNode = new BSTNode<>(15, 1, root);
        root.LeftChild = leftNode; root.RightChild = rightNode;
        BSTNode<Integer> leftLeftNode = new BSTNode<>(6, 1, leftNode);
        BSTNode<Integer> rightLeftNode = new BSTNode<>(8, 2, leftNode);
        leftNode.LeftChild = leftLeftNode; leftNode.RightChild = rightLeftNode;
        BSTNode<Integer> leftRightNode = new BSTNode<>(12, 5, rightNode);
        BSTNode<Integer> rightRightNode = new BSTNode<>(16, 1, rightNode);
        rightNode.LeftChild = leftRightNode; rightNode.RightChild = rightRightNode;
        BSTNode<Integer> rightLeftRightNode = new BSTNode<>(14, 2, leftRightNode);
        leftRightNode.RightChild = rightLeftRightNode;
        BSTNode<Integer> rightRightRightNode = new BSTNode<>(20, 1, rightRightNode);
        rightRightNode.RightChild = rightRightRightNode;

        ArrayList<BSTNode> expectedRes = new ArrayList<>(List.of(leftLeftNode, rightLeftNode, leftNode, rightLeftRightNode,
                leftRightNode, rightRightRightNode, rightRightNode, rightNode, root));
        ArrayList<BSTNode> actualRes = tree.DeepAllNodes(1);

        assertEquals(expectedRes, actualRes);
    }

    @Test
    void testDeepAllNodesPreOrderForEmptyTree() {
        BST<Byte> tree = new BST<>(null);
        assertEquals(new ArrayList<>(), tree.DeepAllNodes(2));
    }

    @Test
    void testDeepAllNodesPreOrderForSingleNodeTree() {
        BSTNode<String> root = new BSTNode<>(3, "3", null);
        BST<String> tree = new BST<>(root);

        assertEquals(new ArrayList<>(List.of(root)), tree.DeepAllNodes(2));
    }

    @Test
    void testDeepAllNodesPreOrder() {
        BSTNode<Integer> root = new BSTNode<>(10, 2, null);
        BST<Integer> tree = new BST<>(root);
        BSTNode<Integer> leftNode = new BSTNode<>(7, 6, root);
        BSTNode<Integer> rightNode = new BSTNode<>(15, 1, root);
        root.LeftChild = leftNode; root.RightChild = rightNode;
        BSTNode<Integer> leftLeftNode = new BSTNode<>(6, 1, leftNode);
        BSTNode<Integer> rightLeftNode = new BSTNode<>(8, 2, leftNode);
        leftNode.LeftChild = leftLeftNode; leftNode.RightChild = rightLeftNode;
        BSTNode<Integer> leftRightNode = new BSTNode<>(12, 5, rightNode);
        BSTNode<Integer> rightRightNode = new BSTNode<>(16, 1, rightNode);
        rightNode.LeftChild = leftRightNode; rightNode.RightChild = rightRightNode;
        BSTNode<Integer> rightLeftRightNode = new BSTNode<>(14, 2, leftRightNode);
        leftRightNode.RightChild = rightLeftRightNode;
        BSTNode<Integer> rightRightRightNode = new BSTNode<>(20, 1, rightRightNode);
        rightRightNode.RightChild = rightRightRightNode;

        ArrayList<BSTNode> expectedRes = new ArrayList<>(List.of(root, leftNode, leftLeftNode, rightLeftNode, rightNode, leftRightNode, rightLeftRightNode, rightRightNode, rightRightRightNode));
        ArrayList<BSTNode> actualRes = tree.DeepAllNodes(2);

        assertEquals(expectedRes, actualRes);
    }

    @Test
    void testInvertEmptyTree() {
        BST<Short> tree = new BST<>(null);
        tree.invert();
        assertNull(tree.Root);
    }

    @Test
    void testInvertSimpleTree() {
        BSTNode<String> root = new BSTNode<>(5, "5", null);
        BST<String> tree = new BST<>(root);
        BSTNode<String> leftChild = new BSTNode<>(3, "3", root);
        BSTNode<String> rightChild = new BSTNode<>(8, "8", root);
        root.LeftChild = leftChild; root.RightChild = rightChild;

        tree.invert();

        assertEquals(5, tree.Root.NodeKey);
        assertEquals(8, tree.Root.LeftChild.NodeKey);
        assertEquals(3, tree.Root.RightChild.NodeKey);
    }

    @Test
    void testInvert() {
        BSTNode<Integer> root = new BSTNode<>(10, 2, null);
        BST<Integer> tree = new BST<>(root);
        BSTNode<Integer> leftNode = new BSTNode<>(7, 6, root);
        BSTNode<Integer> rightNode = new BSTNode<>(15, 1, root);
        root.LeftChild = leftNode; root.RightChild = rightNode;
        BSTNode<Integer> leftLeftNode = new BSTNode<>(6, 1, leftNode);
        BSTNode<Integer> rightLeftNode = new BSTNode<>(8, 2, leftNode);
        leftNode.LeftChild = leftLeftNode; leftNode.RightChild = rightLeftNode;
        BSTNode<Integer> leftRightNode = new BSTNode<>(12, 5, rightNode);
        BSTNode<Integer> rightRightNode = new BSTNode<>(16, 1, rightNode);
        rightNode.LeftChild = leftRightNode; rightNode.RightChild = rightRightNode;
        BSTNode<Integer> rightLeftRightNode = new BSTNode<>(14, 2, leftRightNode);
        leftRightNode.RightChild = rightLeftRightNode;
        BSTNode<Integer> rightRightRightNode = new BSTNode<>(20, 1, rightRightNode);
        rightRightNode.RightChild = rightRightRightNode;

        ArrayList<BSTNode> inOrderBefore = tree.DeepAllNodes(0);
        for (int i = 0; i < inOrderBefore.size() / 2; i++) {
            BSTNode tmpToSwap = inOrderBefore.get(i);
            inOrderBefore.set(i, inOrderBefore.get(inOrderBefore.size() - i - 1));
            inOrderBefore.set(inOrderBefore.size() - i - 1, tmpToSwap);
        }

        tree.invert();

        ArrayList<BSTNode> inOrderAfter = tree.DeepAllNodes(0);
        assertEquals(inOrderBefore, inOrderAfter);
    }

    @Test
    void testFindMaxSumLevelInEmptyTree() {
        BST<Boolean> tree = new BST<>(null);
        assertNull(tree.findMaxSumLevel());
    }

    @Test
    void testFindMaxSumLevelInSingleNodeTree() {
        BSTNode<Integer> root = new BSTNode<>(5, 5, null);
        BST<Integer> tree = new BST<>(root);

        assertEquals(0, tree.findMaxSumLevel());
    }

    @Test
    void testFindMaxSumLevelInNotNumberTree() {
        BSTNode<String> root = new BSTNode<>(5, "5", null);
        BST<String> tree = new BST<>(root);

        assertThrows(ClassCastException.class, tree::findMaxSumLevel);
    }

    @Test
    void testFindMaxSumLevelWithEqualsSum() {
        BSTNode<Integer> root = new BSTNode<>(10, 4, null);
        BST<Integer> tree = new BST<>(root);
        BSTNode<Integer> leftNode = new BSTNode<>(5, 3, root);
        BSTNode<Integer> rightNode = new BSTNode<>(13, 1, root);
        root.LeftChild = leftNode; root.RightChild = rightNode;

        assertEquals(0, tree.findMaxSumLevel());
    }

    @Test
    void testFindMaxSumLevel() {
        BSTNode<Integer> root = new BSTNode<>(10, 2, null);
        BST<Integer> tree = new BST<>(root);
        BSTNode<Integer> leftNode = new BSTNode<>(7, 3, root);
        BSTNode<Integer> rightNode = new BSTNode<>(15, 1, root);
        root.LeftChild = leftNode; root.RightChild = rightNode;
        BSTNode<Integer> leftLeftNode = new BSTNode<>(6, 1, leftNode);
        BSTNode<Integer> rightLeftNode = new BSTNode<>(8, 2, leftNode);
        leftNode.LeftChild = leftLeftNode; leftNode.RightChild = rightLeftNode;
        BSTNode<Integer> leftRightNode = new BSTNode<>(12, 5, rightNode);
        BSTNode<Integer> rightRightNode = new BSTNode<>(16, 1, rightNode);
        rightNode.LeftChild = leftRightNode; rightNode.RightChild = rightRightNode;
        BSTNode<Integer> rightLeftRightNode = new BSTNode<>(14, 2, leftRightNode);
        leftRightNode.RightChild = rightLeftRightNode;
        BSTNode<Integer> rightRightRightNode = new BSTNode<>(20, 1, rightRightNode);
        rightRightNode.RightChild = rightRightRightNode;

        assertEquals(2, tree.findMaxSumLevel());
    }
}


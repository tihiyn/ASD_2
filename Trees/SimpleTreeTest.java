import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class SimpleTreeTest {

    @Test
    void testAddRoot() {
        SimpleTree<Integer> tree = new SimpleTree<>(null);
        SimpleTreeNode<Integer> root = new SimpleTreeNode<>(5, null);

        int nodesCounter = tree.Count();

        tree.AddChild(null, root);

        assertEquals(root, tree.Root);
        assertNull(tree.Root.Parent);
        assertNull(tree.Root.Children);
        assertEquals(1, tree.FindNodesByValue(5).size());
        assertEquals(nodesCounter + 1, tree.Count());
        assertEquals(0, root.level);
    }

    @Test
    void testAddChild() {
        SimpleTreeNode<Boolean> root = new SimpleTreeNode<>(true, null);
        SimpleTree<Boolean> tree = new SimpleTree<>(root);
        SimpleTreeNode<Boolean> nodeToAdd = new SimpleTreeNode<>(false, null);
        List<SimpleTreeNode<Boolean>> rootChildren = new ArrayList<>(List.of(nodeToAdd));

        int nodesCounter = tree.Count();

        tree.AddChild(root, nodeToAdd);

        assertNotNull(tree.Root.Children);
        assertEquals(rootChildren, tree.Root.Children);
        assertEquals(root, nodeToAdd.Parent);
        assertEquals(1, tree.FindNodesByValue(false).size());
        assertEquals(nodesCounter + 1, tree.Count());
        assertEquals(0, root.level);
        assertEquals(1, nodeToAdd.level);
    }

    @Test
    void testDeleteNodeWithoutChild() {
        SimpleTreeNode<String> root = new SimpleTreeNode<>("a", null);
        SimpleTree<String> tree = new SimpleTree<>(root);
        SimpleTreeNode<String> node = new SimpleTreeNode<>("b", null);

        tree.AddChild(root, node);
        int nodesCounter = tree.Count();

        tree.DeleteNode(node);
        assertNull(root.Children);
        assertTrue(tree.FindNodesByValue("b").isEmpty());
        assertEquals(nodesCounter - 1, tree.Count());
    }

    @Test
    void testDeleteNodeWithChild() {
        SimpleTreeNode<String> root = new SimpleTreeNode<>("a", null);
        SimpleTree<String> tree = new SimpleTree<>(root);
        SimpleTreeNode<String> parent = new SimpleTreeNode<>("b", null);
        SimpleTreeNode<String> firstChild = new SimpleTreeNode<>("c", null);
        SimpleTreeNode<String> secondChild = new SimpleTreeNode<>("d", null);

        tree.AddChild(root, parent);
        tree.AddChild(parent, firstChild);
        tree.AddChild(parent, secondChild);
        int nodesCounter = tree.Count();

        tree.DeleteNode(parent);
        assertNull(root.Children);
        assertTrue(tree.FindNodesByValue("b").isEmpty());
        assertTrue(tree.FindNodesByValue("c").isEmpty());
        assertTrue(tree.FindNodesByValue("d").isEmpty());
        assertEquals(nodesCounter - 3, tree.Count());
    }

    @Test
    void testGetAllNodesInEmptyTree() {
        SimpleTree<Integer> tree = new SimpleTree<>(null);
        assertTrue(tree.GetAllNodes().isEmpty());
    }

    @Test
    void testGetAllNodesInSingleNodeTree() {
        SimpleTreeNode<Boolean> root = new SimpleTreeNode<>(true, null);
        SimpleTree<Boolean> tree = new SimpleTree<>(root);

        List<SimpleTreeNode<Boolean>> expectedNodeList = new ArrayList<>(List.of(root));
        assertEquals(expectedNodeList, tree.GetAllNodes());
    }

    @Test
    void testGetAllNodes() {
        SimpleTreeNode<String> root = new SimpleTreeNode<>("a", null);
        SimpleTree<String> tree = new SimpleTree<>(root);
        SimpleTreeNode<String> leaf = new SimpleTreeNode<>("b", null);
        SimpleTreeNode<String> parent = new SimpleTreeNode<>("c", null);
        SimpleTreeNode<String> firstChild = new SimpleTreeNode<>("d", null);
        SimpleTreeNode<String> secondChild = new SimpleTreeNode<>("e", null);

        tree.AddChild(root, leaf);
        tree.AddChild(root, parent);
        tree.AddChild(parent, firstChild);
        tree.AddChild(parent, secondChild);

        List<SimpleTreeNode<String>> expectedNodeList = new ArrayList<>(List.of(root, leaf, parent, firstChild, secondChild));
        assertEquals(expectedNodeList, tree.GetAllNodes());
    }

    @Test
    void findNodesByValueInEmptyTree() {
        SimpleTree<String> tree = new SimpleTree<>(null);
        assertTrue(tree.FindNodesByValue("test").isEmpty());
    }

    static Stream<Arguments> findNodesByUniqueValueDataProvider() {
        return Stream.of(
                Arguments.of("a", 0),
                Arguments.of("b", 1),
                Arguments.of("c", 2),
                Arguments.of("d", 3),
                Arguments.of("e", 4)
        );
    }

    @ParameterizedTest
    @MethodSource("findNodesByUniqueValueDataProvider")
    void findNodesByUniqueValue(String value, int index) {
        SimpleTreeNode<String> root = new SimpleTreeNode<>("a", null);
        SimpleTree<String> tree = new SimpleTree<>(root);
        SimpleTreeNode<String> leaf = new SimpleTreeNode<>("b", null);
        SimpleTreeNode<String> parent = new SimpleTreeNode<>("c", null);
        SimpleTreeNode<String> firstChild = new SimpleTreeNode<>("d", null);
        SimpleTreeNode<String> secondChild = new SimpleTreeNode<>("e", null);

        tree.AddChild(root, leaf);
        tree.AddChild(root, parent);
        tree.AddChild(parent, firstChild);
        tree.AddChild(parent, secondChild);

        List<SimpleTreeNode<String>> nodeList = tree.GetAllNodes();

        assertEquals(1, tree.FindNodesByValue(value).size());
        assertEquals(nodeList.get(index), tree.FindNodesByValue(value).get(0));
    }

    @Test
    void testFindNodesByValue() {
        SimpleTreeNode<String> root = new SimpleTreeNode<>("a", null);
        SimpleTree<String> tree = new SimpleTree<>(root);
        SimpleTreeNode<String> leaf = new SimpleTreeNode<>("a", null);
        SimpleTreeNode<String> parent = new SimpleTreeNode<>("c", null);
        SimpleTreeNode<String> firstChild = new SimpleTreeNode<>("a", null);
        SimpleTreeNode<String> secondChild = new SimpleTreeNode<>("a", null);

        tree.AddChild(root, leaf);
        tree.AddChild(root, parent);
        tree.AddChild(parent, firstChild);
        tree.AddChild(parent, secondChild);

        List<SimpleTreeNode<String>> equalsNodes = new ArrayList<>(List.of(root, leaf, firstChild, secondChild));
        assertEquals(equalsNodes, tree.FindNodesByValue("a"));
    }

    @Test
    void moveNodeToLeaf() {
        SimpleTreeNode<String> root = new SimpleTreeNode<>("a", null);
        SimpleTree<String> tree = new SimpleTree<>(root);
        SimpleTreeNode<String> leaf = new SimpleTreeNode<>("a", null);
        SimpleTreeNode<String> parent = new SimpleTreeNode<>("c", null);
        SimpleTreeNode<String> firstChild = new SimpleTreeNode<>("a", null);
        SimpleTreeNode<String> secondChild = new SimpleTreeNode<>("a", null);

        tree.AddChild(root, leaf);
        tree.AddChild(root, parent);
        tree.AddChild(parent, firstChild);
        tree.AddChild(parent, secondChild);

        tree.MoveNode(parent, leaf);
        assertNotNull(leaf.Children);
        assertEquals(leaf, parent.Parent);
        assertEquals(new ArrayList<>(List.of(leaf)), root.Children);
        assertEquals(new ArrayList<>(List.of(parent)), leaf.Children);
        assertEquals(2, parent.level);
        assertEquals(3, firstChild.level);
        assertEquals(3, secondChild.level);
    }

    @Test
    void testMoveNode() {
        SimpleTreeNode<String> root = new SimpleTreeNode<>("a", null);
        SimpleTree<String> tree = new SimpleTree<>(root);
        SimpleTreeNode<String> firstParentNode = new SimpleTreeNode<>("b", null);
        SimpleTreeNode<String> secondParentNode = new SimpleTreeNode<>("c", null);
        SimpleTreeNode<String> firstChild = new SimpleTreeNode<>("d", null);
        SimpleTreeNode<String> secondChild = new SimpleTreeNode<>("e", null);
        SimpleTreeNode<String> thirdChild = new SimpleTreeNode<>("f", null);
        SimpleTreeNode<String> fourthChild = new SimpleTreeNode<>("g", null);

        tree.AddChild(root, firstParentNode);
        tree.AddChild(root, secondParentNode);
        tree.AddChild(firstParentNode, firstChild);
        tree.AddChild(firstParentNode, secondChild);
        tree.AddChild(secondParentNode, thirdChild);
        tree.AddChild(secondParentNode, fourthChild);

        tree.MoveNode(secondParentNode, firstParentNode);
        assertEquals(new ArrayList<>(List.of(firstChild, secondChild, secondParentNode)), firstParentNode.Children);
        assertEquals(firstParentNode, secondParentNode.Parent);
        assertEquals(new ArrayList<>(List.of(firstParentNode)), root.Children);
        assertEquals(2, secondParentNode.level);
        assertEquals(3, thirdChild.level);
        assertEquals(3, fourthChild.level);
    }

    @Test
    void testCountForEmptyTree() {
        SimpleTree<BigDecimal> tree = new SimpleTree<>(null);
        assertEquals(0, tree.Count());
    }

    @Test
    void testCountForSingleNodeTree() {
        SimpleTreeNode<Long> root = new SimpleTreeNode<>(5L, null);
        SimpleTree<Long> tree = new SimpleTree<>(root);

        assertEquals(1, tree.Count());
    }

    @Test
    void testCount() {
        SimpleTreeNode<String> root = new SimpleTreeNode<>("a", null);
        SimpleTree<String> tree = new SimpleTree<>(root);
        SimpleTreeNode<String> firstParentNode = new SimpleTreeNode<>("b", null);
        SimpleTreeNode<String> secondParentNode = new SimpleTreeNode<>("c", null);
        SimpleTreeNode<String> firstChild = new SimpleTreeNode<>("d", null);
        SimpleTreeNode<String> secondChild = new SimpleTreeNode<>("e", null);
        SimpleTreeNode<String> thirdChild = new SimpleTreeNode<>("f", null);
        SimpleTreeNode<String> fourthChild = new SimpleTreeNode<>("g", null);

        tree.AddChild(root, firstParentNode);
        tree.AddChild(root, secondParentNode);
        tree.AddChild(firstParentNode, firstChild);
        tree.AddChild(firstParentNode, secondChild);
        tree.AddChild(secondParentNode, thirdChild);
        tree.AddChild(secondParentNode, fourthChild);

        assertEquals(7, tree.Count());
    }

    @Test
    void leafCountForEmptyTree() {
        SimpleTree<BigDecimal> tree = new SimpleTree<>(null);
        assertEquals(0, tree.LeafCount());
    }

    @Test
    void leafCountForSingleNodeTree() {
        SimpleTreeNode<Long> root = new SimpleTreeNode<>(5L, null);
        SimpleTree<Long> tree = new SimpleTree<>(root);

        assertEquals(1, tree.LeafCount());
    }

    @Test
    void testLeafCount() {
        SimpleTreeNode<String> root = new SimpleTreeNode<>("a", null);
        SimpleTree<String> tree = new SimpleTree<>(root);
        SimpleTreeNode<String> firstParentNode = new SimpleTreeNode<>("b", null);
        SimpleTreeNode<String> secondParentNode = new SimpleTreeNode<>("c", null);
        SimpleTreeNode<String> firstChild = new SimpleTreeNode<>("d", null);
        SimpleTreeNode<String> secondChild = new SimpleTreeNode<>("e", null);
        SimpleTreeNode<String> thirdChild = new SimpleTreeNode<>("f", null);
        SimpleTreeNode<String> fourthChild = new SimpleTreeNode<>("g", null);

        tree.AddChild(root, firstParentNode);
        tree.AddChild(root, secondParentNode);
        tree.AddChild(firstParentNode, firstChild);
        tree.AddChild(firstParentNode, secondChild);
        tree.AddChild(secondParentNode, thirdChild);
        tree.AddChild(secondParentNode, fourthChild);

        assertEquals(4, tree.LeafCount());
    }

    @Test
    void testIsEmptyTreeSymmetrical() {
        SimpleTree<String> tree = new SimpleTree<>(null);
        assertTrue(tree.isSymmetrical());
    }

    @Test
    void testIsSingleNodeTreeSymmetrical() {
        SimpleTreeNode<Integer> root = new SimpleTreeNode<>(0, null);
        SimpleTree<Integer> tree = new SimpleTree<>(root);

        assertTrue(tree.isSymmetrical());
    }

    @Test
    void testIsTripleNodeTreeSymmetricalWithNotEqualsValues() {
        SimpleTreeNode<Integer> root = new SimpleTreeNode<>(0, null);
        SimpleTree<Integer> tree = new SimpleTree<>(root);
        SimpleTreeNode<Integer> firstChild = new SimpleTreeNode<>(1, null);
        SimpleTreeNode<Integer> secondChild = new SimpleTreeNode<>(2, null);

        tree.AddChild(root, firstChild);
        tree.AddChild(root, secondChild);

        assertFalse(tree.isSymmetrical());
    }

    @Test
    void testIsTripleNodeTreeSymmetricalWithEqualsValues() {
        SimpleTreeNode<Integer> root = new SimpleTreeNode<>(0, null);
        SimpleTree<Integer> tree = new SimpleTree<>(root);
        SimpleTreeNode<Integer> firstChild = new SimpleTreeNode<>(1, null);
        SimpleTreeNode<Integer> secondChild = new SimpleTreeNode<>(1, null);

        tree.AddChild(root, firstChild);
        tree.AddChild(root, secondChild);

        assertTrue(tree.isSymmetrical());
    }

    @Test
    void testIsSymmetricalForTreeWithNotOddChildrenOfRoot() {
        SimpleTreeNode<Integer> root = new SimpleTreeNode<>(0, null);
        SimpleTree<Integer> tree = new SimpleTree<>(root);
        SimpleTreeNode<Integer> firstChild = new SimpleTreeNode<>(1, null);
        SimpleTreeNode<Integer> secondChild = new SimpleTreeNode<>(5, null);
        SimpleTreeNode<Integer> thirdChild = new SimpleTreeNode<>(1, null);

        tree.AddChild(root, firstChild);
        tree.AddChild(root, secondChild);
        tree.AddChild(root, thirdChild);

        assertTrue(tree.isSymmetrical());
    }

    @Test
    void testIsSymmetricalForTreeWithNotSymmetricalChildren() {
        SimpleTreeNode<Integer> root = new SimpleTreeNode<>(0, null);
        SimpleTree<Integer> tree = new SimpleTree<>(root);
        SimpleTreeNode<Integer> firstChild = new SimpleTreeNode<>(1, null);
        SimpleTreeNode<Integer> secondChild = new SimpleTreeNode<>(5, null);
        SimpleTreeNode<Integer> thirdChild = new SimpleTreeNode<>(1, null);
        SimpleTreeNode<Integer> childOfFirstChild = new SimpleTreeNode<>(2, null);

        tree.AddChild(root, firstChild);
        tree.AddChild(firstChild, childOfFirstChild);
        tree.AddChild(root, secondChild);
        tree.AddChild(root, thirdChild);

        assertFalse(tree.isSymmetrical());
    }

    @Test
    void testIsSymmetricalForTreeWithSymmetricalChildren() {
        SimpleTreeNode<Integer> root = new SimpleTreeNode<>(0, null);
        SimpleTree<Integer> tree = new SimpleTree<>(root);
        SimpleTreeNode<Integer> firstChild = new SimpleTreeNode<>(1, null);
        SimpleTreeNode<Integer> secondChild = new SimpleTreeNode<>(5, null);
        SimpleTreeNode<Integer> thirdChild = new SimpleTreeNode<>(1, null);
        SimpleTreeNode<Integer> childOfFirstChild = new SimpleTreeNode<>(2, null);
        SimpleTreeNode<Integer> childOfThirdChild = new SimpleTreeNode<>(2, null);

        tree.AddChild(root, firstChild);
        tree.AddChild(firstChild, childOfFirstChild);
        tree.AddChild(root, secondChild);
        tree.AddChild(root, thirdChild);
        tree.AddChild(thirdChild, childOfThirdChild);

        assertTrue(tree.isSymmetrical());
    }

    @Test
    void testIsSymmetricalForTreeAsLinkedList() {
        SimpleTreeNode<Integer> root = new SimpleTreeNode<>(0, null);
        SimpleTree<Integer> tree = new SimpleTree<>(root);
        SimpleTreeNode<Integer> firstChild = new SimpleTreeNode<>(1, null);
        SimpleTreeNode<Integer> secondChild = new SimpleTreeNode<>(2, null);
        SimpleTreeNode<Integer> thirdChild = new SimpleTreeNode<>(3, null);

        tree.AddChild(root, firstChild);
        tree.AddChild(firstChild, secondChild);
        tree.AddChild(secondChild, thirdChild);

        assertTrue(tree.isSymmetrical());
    }
}

class LevelsAssignerTest {
    @Test
    void testAssignLevelsInEmptyTree() {
        SimpleTree<String> tree = new SimpleTree<>(null);
        LevelsAssigner<String> levelsAssigner = new LevelsAssigner<>();
        assertEquals(new HashMap<>(), levelsAssigner.assignLevels(tree));
    }

    @Test
    void testAssignLevelsInSingleNodeTree() {
        SimpleTreeNode<String> root = new SimpleTreeNode<>("a", null);
        SimpleTree<String> tree = new SimpleTree<>(root);

        Map<SimpleTreeNode<String>, Integer> expectedLevelsMap = new HashMap<>();
        expectedLevelsMap.put(root, 0);

        LevelsAssigner<String> levelsAssigner = new LevelsAssigner<>();
        assertEquals(expectedLevelsMap, levelsAssigner.assignLevels(tree));
    }

    @Test
    void testAssignLevels() {
        SimpleTreeNode<String> root = new SimpleTreeNode<>("a", null);
        SimpleTree<String> tree = new SimpleTree<>(root);
        SimpleTreeNode<String> firstParentNode = new SimpleTreeNode<>("b", null);
        SimpleTreeNode<String> secondParentNode = new SimpleTreeNode<>("c", null);
        SimpleTreeNode<String> firstChild = new SimpleTreeNode<>("d", null);
        SimpleTreeNode<String> secondChild = new SimpleTreeNode<>("d", null);
        SimpleTreeNode<String> thirdChild = new SimpleTreeNode<>("f", null);
        SimpleTreeNode<String> fourthChild = new SimpleTreeNode<>("g", null);

        tree.AddChild(root, firstParentNode);
        tree.AddChild(root, secondParentNode);
        tree.AddChild(firstParentNode, firstChild);
        tree.AddChild(firstParentNode, secondChild);
        tree.AddChild(secondParentNode, thirdChild);
        tree.AddChild(secondParentNode, fourthChild);

        Map<SimpleTreeNode<String>, Integer> expectedLevelsMap = new HashMap<>();
        expectedLevelsMap.put(root, 0);
        expectedLevelsMap.put(firstParentNode, 1);
        expectedLevelsMap.put(secondParentNode, 1);
        expectedLevelsMap.put(firstChild, 2);
        expectedLevelsMap.put(secondChild, 2);
        expectedLevelsMap.put(thirdChild, 2);
        expectedLevelsMap.put(fourthChild, 2);

        LevelsAssigner<String> levelsAssigner = new LevelsAssigner<>();
        assertEquals(expectedLevelsMap, levelsAssigner.assignLevels(tree));
    }
}


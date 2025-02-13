import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class aBSTTest {
    @Test
    void testFindKeyIndexInEmptyTree() {
        aBST emptyTree = new aBST(0);
        assertEquals(0, emptyTree.FindKeyIndex(4));
    }

    @Test
    void testFindKeyIndexInFullSingleNodeTree() {
        aBST tree = new aBST(0);
        tree.AddKey(7);

        assertEquals(0, tree.FindKeyIndex(7));
        assertNull(tree.FindKeyIndex(4));
        assertNull(tree.FindKeyIndex(11));
    }

    @Test
    void testFindKeyIndexInSingleNodeTree() {
        aBST tree = new aBST(1);
        tree.AddKey(7);

        assertEquals(0, tree.FindKeyIndex(7));
        assertEquals(-1, tree.FindKeyIndex(4));
        assertEquals(-2, tree.FindKeyIndex(11));
    }


    @Test
    void testFindKeyIndex() {
        aBST tree = new aBST(3);
        tree.AddKey(10);
        tree.AddKey(7);
        tree.AddKey(15);
        tree.AddKey(4);
        tree.AddKey(9);
        tree.AddKey(13);
        tree.AddKey(18);
        tree.AddKey(3);
        tree.AddKey(5);
        tree.AddKey(8);
        tree.AddKey(16);
        tree.AddKey(19);

        assertEquals(0, tree.FindKeyIndex(10));
        assertEquals(3, tree.FindKeyIndex(4));
        assertEquals(-11, tree.FindKeyIndex(12));
        assertNull(tree.FindKeyIndex(21));
    }

    @Test
    void testAddKeyInEmptyTree() {
        aBST emptyTree = new aBST(0);
        assertEquals(0, emptyTree.AddKey(4));
        assertEquals(-1, (emptyTree.AddKey(2)));
        assertEquals(-1, (emptyTree.AddKey(5)));
    }

    @Test
    void testAddKeyInFullSingleNodeTree() {
        aBST tree = new aBST(0);
        tree.Tree[0] = 7;

        assertEquals(-1, tree.AddKey(4));
        assertEquals(-1, tree.AddKey(11));
    }

    @Test
    void testAddKeyInSingleNodeTree() {
        aBST tree = new aBST(1);
        tree.Tree[0] = 7;

        assertEquals(1, tree.AddKey(4));
        assertEquals(2, tree.AddKey(11));
    }

    @Test
    void testAddKey() {
        aBST tree = new aBST(3);
        tree.AddKey(10);
        tree.AddKey(7);
        tree.AddKey(15);
        tree.AddKey(4);
        tree.AddKey(9);
        tree.AddKey(13);
        tree.AddKey(18);
        tree.AddKey(3);
        tree.AddKey(5);
        tree.AddKey(8);
        tree.AddKey(16);
        tree.AddKey(19);

        Integer[] expectedArray = new Integer[] {10, 7, 15, 4, 9, 13, 18, 3, 5, 8, null, null, null, 16, 19};
        Integer[] actualArray = tree.Tree;

        assertArrayEquals(expectedArray, actualArray);
    }

    @Test
    void testFindLCAInEmptyTree() {
        aBST emptyTree = new aBST(0);
        assertNull(emptyTree.findLCA(4, 5));
    }

    @Test
    void testFindLCAInFullSingleNodeTree() {
        aBST tree = new aBST(0);
        tree.AddKey(7);

        assertNull(tree.findLCA(7, 7));
        assertNull(tree.findLCA(7, 3));
        assertNull(tree.findLCA(9, 3));
        assertNull(tree.findLCA(1, 2));
        assertNull(tree.findLCA(2, 2));
    }

    @Test
    void testFindLCAInSingleNodeTree() {
        aBST tree = new aBST(2);
        tree.AddKey(7);

        assertNull(tree.findLCA(7, 7));
        assertNull(tree.findLCA(7, 3));
        assertNull(tree.findLCA(3, 7));
        assertNull(tree.findLCA(9, 7));
        assertNull(tree.findLCA(7, 9));
        assertNull(tree.findLCA(5, 1));
        assertNull(tree.findLCA(9, 1));
        assertNull(tree.findLCA(9, 9));
    }

    @Test
    void testFindLCA() {
        aBST tree = new aBST(3);
        tree.AddKey(10);
        tree.AddKey(7);
        tree.AddKey(15);
        tree.AddKey(4);
        tree.AddKey(9);
        tree.AddKey(13);
        tree.AddKey(18);
        tree.AddKey(3);
        tree.AddKey(5);
        tree.AddKey(8);
        tree.AddKey(16);
        tree.AddKey(19);

        assertEquals(10, tree.findLCA(3, 13));
        assertEquals(7, tree.findLCA(3, 9));
        assertEquals(18, tree.findLCA(16, 19));
        assertEquals(7, tree.findLCA(8, 9));
        assertEquals(10, tree.findLCA(3, 7));
        assertEquals(7, tree.findLCA(4, 4));
        assertNull(tree.findLCA(10, 10));
        assertNull(tree.findLCA(10, 16));
        assertNull(tree.findLCA(3, 12));
    }

    @Test
    void testWideAllNodesInEmptyTree() {
        aBST emptyTree = new aBST(0);
        assertArrayEquals(new Integer[] {null}, emptyTree.wideAllNodes());
    }

    @Test
    void testWideAllNodesInFullSingleNodeTree() {
        aBST tree = new aBST(0);
        tree.AddKey(4);
        assertArrayEquals(new Integer[] {4}, tree.wideAllNodes());
    }

    @Test
    void testWideAllNodesInSingleNodeTree() {
        aBST tree = new aBST(1);
        tree.AddKey(4);
        assertArrayEquals(new Integer[] {4, null, null}, tree.wideAllNodes());
    }

    @Test
    void testWideAllNodes() {
        aBST tree = new aBST(3);
        tree.AddKey(10);
        tree.AddKey(7);
        tree.AddKey(15);
        tree.AddKey(4);
        tree.AddKey(9);
        tree.AddKey(13);
        tree.AddKey(18);
        tree.AddKey(3);
        tree.AddKey(5);
        tree.AddKey(8);
        tree.AddKey(16);
        tree.AddKey(19);

        Integer[] res = tree.wideAllNodes();
        assertArrayEquals(tree.Tree, res);
    }
}


import java.util.*;
import java.util.stream.Collectors;

class Vertex
{
    public int Value;
    public boolean Hit;

    public Vertex(int val)
    {
        Value = val;
        Hit = false;
    }
}

class SimpleGraph
{
    Vertex [] vertex;
    int [][] m_adjacency;
    int max_vertex;
    int emptySlot;
    Stack<Integer> stackToDFS;
    Queue<SimpleTreeNode<Integer>> queueToBFS;

    public SimpleGraph(int size)
    {
        max_vertex = size;
        m_adjacency = new int [size][size];
        vertex = new Vertex[size];
        emptySlot = 0;
        stackToDFS = new Stack<>();
        queueToBFS = new Queue<>();
    }

    public void AddVertex(int value)
    {
        if (emptySlot == max_vertex) {
            return;
        }

        Vertex newVertex = new Vertex(value);
        vertex[emptySlot] = newVertex;

         for (int i = emptySlot + 1; i < max_vertex; i++) {
             if (vertex[i] == null) {
                 emptySlot = i;
                 return;
             }
         }

         emptySlot = max_vertex;
    }

    public void RemoveVertex(int v)
    {
        if (emptySlot == 0 || vertex[v] == null) {
            return;
        }

        for (int j = 0; j < max_vertex; j++) {
            m_adjacency[v][j] = 0;
            m_adjacency[j][v] = 0;
        }

        vertex[v] = null;
        if (v < emptySlot) {
            emptySlot = v;
        }
    }

    public boolean IsEdge(int v1, int v2)
    {
        return m_adjacency[v1][v2] == 1 && m_adjacency[v2][v1] == 1;
    }

    public void AddEdge(int v1, int v2)
    {
        if (vertex[v1] == null || vertex[v2] == null) {
            return;
        }

        m_adjacency[v1][v2] = 1;
        m_adjacency[v2][v1] = 1;
    }

    public void RemoveEdge(int v1, int v2)
    {
        m_adjacency[v1][v2] = 0;
        m_adjacency[v2][v1] = 0;
    }

    public ArrayList<Vertex> DepthFirstSearch(int VFrom, int VTo)
    {
        if (max_vertex == 0 || VFrom < 0 || VFrom >= max_vertex || VTo < 0 || VTo >= max_vertex) {
            return new ArrayList<>();
        }

        Arrays.stream(vertex).forEach(v -> v.Hit = false);
        stackToDFS.clear();

        depthFirstSearchRecursive(VFrom, vertex[VTo].Value);
        return (ArrayList<Vertex>) stackToDFS.stream()
                .map(vertexIndex -> vertex[vertexIndex])
                .collect(Collectors.toList());
    }

    private void depthFirstSearchRecursive(final int currentVertexIndex, final int targetVertexValue) {
        vertex[currentVertexIndex].Hit = true;
        stackToDFS.push(currentVertexIndex);

        for (int i = 0; i < max_vertex; i++) {
            if (m_adjacency[currentVertexIndex][i] == 1 && vertex[i].Value == targetVertexValue) {
                stackToDFS.push(i);
                return;
            }
        }

        for (int i = 0; i < max_vertex; i++) {
            if (m_adjacency[currentVertexIndex][i] == 1 && !vertex[i].Hit) {
                depthFirstSearchRecursive(i, targetVertexValue);
                return;
            }
        }

        stackToDFS.pop();
        if (stackToDFS.isEmpty()) {
            return;
        }

        depthFirstSearchRecursive(stackToDFS.pop(), targetVertexValue);
    }

    public boolean isConnected() {
        int startVertexIndex = -1;
        for (int i = 0; i < max_vertex; i++) {
            if (vertex[i] != null) {
                startVertexIndex = i;
                break;
            }
        }
        
        if (startVertexIndex == -1) {
            return false;
        }
        
        for (int i = startVertexIndex + 1; i < max_vertex; i++) {
            if (vertex[i] != null && DepthFirstSearch(startVertexIndex, i).isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public ArrayList<Vertex> BreadthFirstSearch(int VFrom, int VTo)
    {
        if (max_vertex == 0 || VFrom < 0 || VFrom >= max_vertex || VTo < 0 || VTo >= max_vertex ||
                vertex[VFrom] == null || vertex[VTo] == null) {
            return new ArrayList<>();
        }

        queueToBFS.clear();
        Arrays.stream(vertex).filter(Objects::nonNull).forEach(v -> v.Hit = false);

        SimpleTreeNode<Integer> startVertex = new SimpleTreeNode<>(VFrom, null);
        vertex[VFrom].Hit = true;
        
        return (ArrayList<Vertex>) breadthFirstSearchRecursive(startVertex, vertex[VTo].Value, new SimpleTree<>(startVertex))
                .stream()
                .map(index -> vertex[index])
                .collect(Collectors.toList());
    }

    private List<Integer> breadthFirstSearchRecursive(SimpleTreeNode<Integer> currentVertex, final int targetVertexValue,
                                                           SimpleTree<Integer> pathsTree) {
        SimpleTreeNode<Integer> neighbor;
        for (int i = 0; i < max_vertex; i++) {
            if (vertex == null || m_adjacency[currentVertex.NodeValue][i] == 0) {
                continue;
            }
            neighbor = new SimpleTreeNode<>(i, currentVertex);
            if (vertex[i].Value == targetVertexValue) {
                return buildPath(neighbor);
            }
            vertex[i].Hit = true;
            queueToBFS.enqueue(neighbor);
            pathsTree.AddChild(currentVertex, neighbor);
        }

        if (queueToBFS.size() == 0) {
            return new ArrayList<>();
        }

        return breadthFirstSearchRecursive(queueToBFS.dequeue(), targetVertexValue, pathsTree);
    }

    private List<Integer> buildPath(SimpleTreeNode<Integer> endVertex) {
        List<Integer> path = new ArrayList<>();
        for (SimpleTreeNode<Integer> node = endVertex; node != null; node = node.Parent) {
            path.add(0, node.NodeValue);
        }

        return path;
    }

    public ArrayList<Vertex> WeakVertices()
    {
        Arrays.stream(vertex).filter(Objects::nonNull).forEach(v -> v.Hit = false);

        ArrayList<Vertex> notBelongTriangleList = new ArrayList<>();
        Set<Integer> belongTriangleSet = new HashSet<>();
        Queue<Integer> queueToWeakVertices = new Queue<>();

        for (int i = 0; i < max_vertex; i++) {
            if (vertex[i] != null && !vertex[i].Hit) {
                vertex[i].Hit = true;
                weakVerticesRecursive(notBelongTriangleList, belongTriangleSet, queueToWeakVertices, i);
            }
        }

        return notBelongTriangleList;
    }

    private void weakVerticesRecursive(ArrayList<Vertex> notBelongTriangleList, Set<Integer> belongTriangleSet,
                                       Queue<Integer> queueToWeakVertices, int currentVertexIndex) {
        List<Integer> neighboursIndexes = getNeighbours(currentVertexIndex);
        if (!belongTriangleSet.contains(currentVertexIndex) &&
                !isBelongTriangle(belongTriangleSet, neighboursIndexes, currentVertexIndex)) {
            notBelongTriangleList.add(vertex[currentVertexIndex]);
        }

        for (Integer neighboursIndex: neighboursIndexes) {
            if (!vertex[neighboursIndex].Hit) {
                vertex[neighboursIndex].Hit = true;
                queueToWeakVertices.enqueue(neighboursIndex);
            }
        }

        if (queueToWeakVertices.size() == 0) {
            return;
        }

        weakVerticesRecursive(notBelongTriangleList, belongTriangleSet, queueToWeakVertices, queueToWeakVertices.dequeue());
    }

    private List<Integer> getNeighbours(int currentVertexIndex) {
        List<Integer> neighboursIndexes = new ArrayList<>();
        for (int i = 0; i < max_vertex; i++) {
            if (vertex[i] != null && m_adjacency[currentVertexIndex][i] == 1) {
                neighboursIndexes.add(i);
            }
        }

        return neighboursIndexes;
    }

    private boolean isBelongTriangle(Set<Integer> belongTriangleSet, List<Integer> neighboursIndexes, int currentVrtexIndex) {
        int neighboursCount = neighboursIndexes.size();
        for (int i = 0; i < neighboursCount - 1; i++) {
            for (int j = i + 1; j < neighboursCount; j++) {
                if (m_adjacency[neighboursIndexes.get(i)][neighboursIndexes.get(j)] == 1) {
                    belongTriangleSet.add(currentVrtexIndex);
                    belongTriangleSet.add(neighboursIndexes.get(i));
                    belongTriangleSet.add(neighboursIndexes.get(j));
                    return true;
                }
            }
        }

        return false;
    }
}

class SimpleTreeNode<T>
{
    public T NodeValue;
    public SimpleTreeNode<T> Parent;
    public List<SimpleTreeNode<T>> Children;
    public int level;

    public SimpleTreeNode(T val, SimpleTreeNode<T> parent)
    {
        NodeValue = val;
        Parent = parent;
        Children = null;
    }
}

class SimpleTree<T>
{
    public SimpleTreeNode<T> Root;

    public SimpleTree(SimpleTreeNode<T> root)
    {
        Root = root;
    }

    public void AddChild(SimpleTreeNode<T> ParentNode, SimpleTreeNode<T> NewChild)
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

    private void incrementChildrenLevels(SimpleTreeNode<T> parent) {
        for (SimpleTreeNode<T> child: parent.Children) {
            child.level = parent.level + 1;

            if (child.Children != null) {
                incrementChildrenLevels(child);
            }
        }
    }
}

class Queue<T>
{
    List<T> storage;

    public Queue()
    {
        storage = new ArrayList<>();
    }

    public void enqueue(T item)
    {
        storage.add(0, item);
    }

    public T dequeue()
    {
        if (storage.isEmpty()) {
            return null;
        }

        return storage.remove(storage.size() - 1);
    }

    public int size()
    {
        return storage.size();
    }

    public void clear() {
        storage.clear();
    }
}


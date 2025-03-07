import java.util.*;
import java.util.stream.Collectors;

class Vertex
{
    public int Value;
    public boolean hit;

    public Vertex(int val)
    {
        Value = val;
        hit = false;
    }
}

class SimpleGraph
{
    Vertex [] vertex;
    int [][] m_adjacency;
    int max_vertex;
    int emptySlot;
    Stack<Integer> stackToDFS;

    public SimpleGraph(int size)
    {
        max_vertex = size;
        m_adjacency = new int [size][size];
        vertex = new Vertex[size];
        emptySlot = 0;
        stackToDFS = new Stack<>();
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
        stackToDFS.clear();
        Arrays.stream(vertex).forEach(v -> v.hit = false);
        List<Integer> path =  DepthFirstSearchRecursive(VFrom, vertex[VTo].Value);

        return (ArrayList<Vertex>) path.stream().map(index -> vertex[index]).collect(Collectors.toList());
    }

    private List<Integer> DepthFirstSearchRecursive(final int currentVertexIndex, final int targetVertexValue) {
        vertex[currentVertexIndex].hit = true;

        stackToDFS.push(currentVertexIndex);

        for (int i = 0; i < max_vertex; i++) {
            if (m_adjacency[currentVertexIndex][i] == 1 && vertex[i].Value == targetVertexValue) {
                stackToDFS.push(i);
                return new ArrayList<>(stackToDFS);
            }
        }

        for (int i = 0; i < max_vertex; i++) {
            if (m_adjacency[currentVertexIndex][i] == 1 && !vertex[i].hit) {
                return DepthFirstSearchRecursive(i, targetVertexValue);
            }
        }

        stackToDFS.pop();
        if (stackToDFS.isEmpty()) {
            return new ArrayList<>();
        }

        return DepthFirstSearchRecursive(stackToDFS.pop(), targetVertexValue);
    }
}


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
}


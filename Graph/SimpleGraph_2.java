public class DirectedGraph {
    Vertex [] vertex;
    int [][] m_adjacency;
    int max_vertex;
    int emptySlot;

    public DirectedGraph(int size)
    {
        max_vertex = size;
        m_adjacency = new int [size][size];
        vertex = new Vertex[size];
        emptySlot = 0;
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

    public void AddEdge(int v1, int v2)
    {
        if (vertex[v1] == null || vertex[v2] == null) {
            return;
        }

        m_adjacency[v1][v2] = 1;
    }

    /*
    Задание 8
    Задача 2
    Является ли ор. граф циклическим
    Временная сложность - O(V^2)
    Пространственная сложность - O(V)
     */
    public boolean isCyclic() {
        boolean[] visited = new boolean[max_vertex];
        boolean[] path = new boolean[max_vertex];

        for (int vertex = 0; vertex < max_vertex; vertex++) {
            if (visited[vertex]) {
                continue;
            }

            if (isCyclicRecursive(vertex, visited, path)) {
                return true;
            }
        }
        return false;
    }

    private boolean isCyclicRecursive(int vertex, boolean[] visited, boolean[] path) {
        visited[vertex] = true;
        path[vertex] = true;

        for (int neighbor = 0; neighbor < max_vertex; neighbor++) {
            if (m_adjacency[vertex][neighbor] == 0) {
                continue;
            }

            if (visited[neighbor] && path[neighbor]) {
                return true;
            }

            if (visited[neighbor]) {
                continue;
            }

            if (isCyclicRecursive(neighbor, visited, path)) {
                return true;
            }
        }

        path[vertex] = false;
        return false;
    }
}

/*
Задание 10
Задача 2
Найти самый длинный простой путь в ор. графе
Временная сложность - O(V^2)
Пространственная сложность - O(V)
 */
public int findLongestSimplePath() {
    maxLength = 0;

    for (int i = 0; i < max_vertex; i++) {
        findLongestSimplePathRecursive(i, new HashSet<>(), 0);
    }

    return maxLength;
}

private void findLongestSimplePathRecursive(final int currentIndex, Set<Integer> path, final int length) {
    if (path.contains(currentIndex)) {
        return;
    }

    path.add(currentIndex);
    maxLength = Math.max(maxLength, length);

    for (int i = 0; i < max_vertex; i++) {
        if (m_adjacency[currentIndex][i] == 1) {
            findLongestSimplePathRecursive(i, path, length + 1);
        }
    }

    path.remove(currentIndex);
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

/*
Задание 10
Задача 1
Является ли граф связным
Временная сложность - O(V^2)
Пространственная слдожность - O(V)
 */
public boolean isConnected() {
    boolean isConnected = true;
    for (int i = 0; i < max_vertex; i++) {
        isConnected = !DepthFirstSearch(0, i).isEmpty();
    }

    return isConnected;
}


import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

class Graph
{
    Vertex [] vertex;
    int [][] m_adjacency;
    int max_vertex;
    int emptySlot;
    Stack<Integer> stackToDFS;

    public Graph(int size)
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
        // если размер массива вершин равен 0 или один из индексов выходит за границы массива, то возвращаем пустой список
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

    /*
    Задание 10.
    Задача 1.
    Является ли неориентированный граф связным.
    Временная сложность - O(V * (V + E)).
    Пространственная сложность - O(V).
     */
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

    /*
    Задание 11.
    Задача 3.
    Найти все циклы в неориентированном графе, используя BFS.
    Временная сложность - O(2^V).
    Пространственная сложность - O(2^V).
     */
    public List<List<Integer>> findAllCycles() {
        List<List<Integer>> cycles = new ArrayList<>();

        for (int i = 0; i < max_vertex; i++) {
            Queue<List<Integer>> queue = new Queue<>();
            queue.enqueue(Collections.singletonList(i));

            while (queue.size() != 0) {
                List<Integer> path = queue.dequeue();
                int lastNode = path.get(path.size() - 1);

                for (int j = 0; j < max_vertex; j++) {
                    if (m_adjacency[lastNode][j] == 0) {
                        continue;
                    }

                    if (path.size() > 2 && j == path.get(0)) {
                        List<Integer> cycle = new ArrayList<>();
                        for (int node : path) {
                            cycle.add(vertex[node].Value);
                        }
                        cycle.add(vertex[j].Value);

                        if (cycles.stream().noneMatch(c -> new HashSet<>(c).equals(new HashSet<>(cycle)))) {
                            cycles.add(cycle);
                        }
                        continue;
                    }

                    if (!path.contains(j)) {
                        List<Integer> newPath = new ArrayList<>(path);
                        newPath.add(j);
                        queue.enqueue(newPath);
                    }
                }
            }
        }
        return cycles;
    }
}

class DirectedGraph {
    Vertex [] vertex;
    int [][] m_adjacency;
    int max_vertex;
    int emptySlot;
    Stack<Integer> stackToDFS;

    public DirectedGraph(int size)
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

    public void AddEdge(int v1, int v2)
    {
        if (vertex[v1] == null || vertex[v2] == null) {
            return;
        }

        m_adjacency[v1][v2] = 1;
    }

    public boolean IsEdge(int v1, int v2)
    {
        return m_adjacency[v1][v2] == 1;
    }

    /*
    Задание 8.
    Задача 2.
    Является ли ориентированный граф циклическим.
    Временная сложность - O(V + E).
    Пространственная сложность - O(V).

    Рефлексия:
    Кажется, что это очень похоже на моё решение. Только я использовал вместо пометки вершин "в процессе обработки"
    добавление их в множество. Плюс подхода с пометкой в том, что он будет немножко быстрее, так как получение поля может
    быть эффективнее, если в множестве возникнет коллизия, хотя временные сложности у обоих O(1). Но зато во варианте с
    пометкой нам нужно постоянно хранить дополнительное поле в классе Vertex, даже если мы не используем метод isCyclic(),
    и сбрасывать значение этого поля для всех вершин при каждом использовании метода (что убивает выигрыш по времени у метода contains).
    Только вот не могу понять логику с тем, чтобы делать матрицу ассиметричной. Да, мы тогда из неориентированного графа
    получим ориентированный, но тогда полученный таким образом граф будет всегда ациклическим, потому что рёбра будут только
    выходить из вершин, а чтобы в графе был цикл нужно, чтобы хотя бы одно ребро было входящим.
     */
    public boolean isCyclic() {
        if (Arrays.stream(vertex).allMatch(Objects::isNull)) {
            return false;
        }

        Arrays.stream(vertex).forEach(v -> v.Hit = false);

        for (int i = 0; i < max_vertex; i++) {
            if (!vertex[i].Hit && isCyclicRecursive(new HashSet<>(), i)) {
                return true;
            }
        }

        return false;
    }

    private boolean isCyclicRecursive(Set<Integer> path, int currentVertexIndex) {
        if (path.contains(currentVertexIndex)) {
            return true;
        }

        vertex[currentVertexIndex].Hit = true;
        path.add(currentVertexIndex);

        for (int i = 0; i < max_vertex; i++) {
            if (m_adjacency[currentVertexIndex][i] == 0) {
                continue;
            }

            if (!vertex[i].Hit && isCyclicRecursive(path, i)) {
                return true;
            }

            if (vertex[i].Hit && path.contains(i)) {
                return true;
            }
        }

        path.remove(currentVertexIndex);
        return false;
    }

    /*
    Задание 10.
    Задача 2.
    Найти самый длинный простой путь в ориентированном графе
    Временная сложность - O(V * (V + E)).
    Пространственная сложность - O(V).
     */
    public int findLongestSimplePath() {
        if (Arrays.stream(vertex).allMatch(Objects::isNull)) {
            return 0;
        }

        Arrays.stream(vertex).filter(Objects::nonNull).forEach(v -> v.Hit = false);

        AtomicInteger maxLength = new AtomicInteger(0);
        for (int i = 0; i < max_vertex; i++) {
            if (vertex[i] != null) {
                findLongestSimplePathRecursive(new HashSet<>(), i, 0, maxLength);
            }
        }

        return maxLength.get();
    }

    private void findLongestSimplePathRecursive(Set<Integer> path, final int currentVertexIndex, final int currentLength,
                                                AtomicInteger maxLength) {
        if (path.contains(currentVertexIndex)) {
            return;
        }

        path.add(currentVertexIndex);
        if (currentLength > maxLength.get()) {
            maxLength.set(currentLength);
        }

        for (int i = 0; i < max_vertex; i++) {
            if (vertex[i] != null && IsEdge(currentVertexIndex, i)) {
                findLongestSimplePathRecursive(path, i, currentLength + 1, maxLength);
            }
        }

        path.remove(currentVertexIndex);
    }
}

class TreeNode<T>
{
    public T NodeValue;
    public TreeNode<T> Parent;
    public List<TreeNode<T>> Children;
    public int level;

    public TreeNode(T val, TreeNode<T> parent)
    {
        NodeValue = val;
        Parent = parent;
        Children = null;
    }
}

class Tree<T>
{
    public TreeNode<T> Root;
    public Queue<TreeNode<T>> queueToBFS;

    public Tree(TreeNode<T> root)
    {
        Root = root;
        queueToBFS = new Queue<>();
    }

    public void AddChild(TreeNode<T> ParentNode, TreeNode<T> NewChild)
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

    private void incrementChildrenLevels(TreeNode<T> parent) {
        for (TreeNode<T> child: parent.Children) {
            child.level = parent.level + 1;

            if (child.Children != null) {
                incrementChildrenLevels(child);
            }
        }
    }

    /*
    Задание 11.
    Задача 2.
    Найти расстояние между двумя наиболее удалёнными узлами в дереве, используя BFS.
    Временная сложность: O(V).
    Пространственная сложность: O(V).
     */
    public int findLongestSimplePathLength() {
        if (Root == null) {
            return 0;
        }

        queueToBFS.clear();

        TreeNode<T> startNode;
        for (startNode = Root; startNode.Children.size() <= 1; startNode = startNode.Children.get(0)) {
            if (startNode.Children.isEmpty()) {
                return startNode.level;
            }
        }

        List<Integer> childrenPathsLength = new ArrayList<>();
        for (TreeNode<T> child: startNode.Children) {
            childrenPathsLength.add(findLongestSimplePathLengthRecursive(child, new HashSet<>(Set.of(child.NodeValue))));
        }

        int startNodeLevel = startNode.level;
        childrenPathsLength = childrenPathsLength.stream()
                .map(pathLength -> pathLength - startNodeLevel)
                .collect(Collectors.toList());

        int firstMax = childrenPathsLength.stream().max(Integer::compareTo).orElse(0);
        if (firstMax <= startNodeLevel) {
            return firstMax + startNodeLevel;
        }
        childrenPathsLength.remove(firstMax);
        int secondMax = childrenPathsLength.stream().max(Integer::compareTo).orElse(0);

        return firstMax + secondMax;
    }

    private int findLongestSimplePathLengthRecursive(TreeNode<T> currentNode, Set<T> visitedNodes) {
        for (TreeNode<T> child: currentNode.Children) {
            if (child.Children.isEmpty()) {
                return child.level;
            }

            visitedNodes.add(child.NodeValue);
            queueToBFS.enqueue(child);
        }

        if (queueToBFS.size() == 0) {
            return 0;
        }

        return findLongestSimplePathLengthRecursive(queueToBFS.dequeue(), visitedNodes);
    }
}


import Queue;

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
    Queue<TreeNode<Integer>> queueToBFS;

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

    Рефлексия:
    Не додумался видоизменить DFS под текущую задачу, чтобы не строить маршрут в конкретную точку и выходить из алгоритма
    при достижении этой точки, а просто обходить дерево до тех пор, пока стек не будет пуст. Из-за этого увеличилась
    временная сложность. Но идею с достижимостью реализовал.
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

    Рефлексия:
    Это единственная задача на курсе, с которой я не справился. Решение ниже из интернета. После отправки стал решать сам.
    Написал своё решение (метод findCycles). Написал тесты. Один тест те проходит. Этот же тест написал и для решения из
    интернета, но всё равно тест не проходит. Начал смотреть эталонное решение, но не нашёл в нём ответа на главный вопрос,
    как формировать и сохранять циклы. Фраза "Запоминаем его некоторым образом (например как текущий путь)" лишь говорит
    о том, что для формирования цикла нужно сохранить путь, но сам путь не будет являться циклом (из-за специфики BFS).
    В целом, пришёл к выводу, что для этой задачи лучше использовать DFS.
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

    public List<List<Integer>> findCycles() {
        queueToBFS.clear();
        Arrays.stream(vertex).filter(Objects::nonNull).forEach(v -> v.Hit = false);

        int startVertexIndex = -1;
        for (int i = 0; i < max_vertex; i++) {
            if (vertex[i] != null) {
                startVertexIndex = i;
                break;
            }
        }

        List<List<Integer>> cycles = new ArrayList<>();
        if (startVertexIndex == -1) {
            return cycles;
        }

        TreeNode<Integer> startVertex = new TreeNode<>(startVertexIndex, null);
        vertex[startVertexIndex].Hit = true;
        queueToBFS.enqueue(startVertex);
        Tree<Integer> pathsTree = new Tree<>(startVertex);
        findCyclesRecursive(pathsTree);

        for (int i = 0; i < max_vertex; i++) {
            if (vertex[i] == null) {
                continue;
            }

            List<TreeNode<Integer>> nodesInCycle = pathsTree.FindNodesByValue(i);
            if (nodesInCycle.size() > 1) {
                addCycle(cycles, nodesInCycle);
            }
        }

        return cycles;
    }

    private void addCycle(List<List<Integer>> cycles, List<TreeNode<Integer>> nodesInCycle) {
        for (int i = 0; i < nodesInCycle.size() - 1; i++) {
            for (int j = i + 1; j < nodesInCycle.size(); j++) {
                List<Integer> cycle = buildCycle(nodesInCycle.get(i), nodesInCycle.get(j));
                if (isNewCycle(cycles, cycle)) {
                    cycles.add(cycle);
                }
            }
        }
    }

    private List<Integer> buildCycle(TreeNode<Integer> start, TreeNode<Integer> finish) {
        Deque<Integer> cycle = new ArrayDeque<>();

        List<Integer> startToRoot = new ArrayList<>();
        for (TreeNode<Integer> current = start; current.Parent != null; current = current.Parent) {
            startToRoot.add(current.NodeValue);
        }

        List<Integer> finishToRoot = new ArrayList<>();
        for (TreeNode<Integer> current = finish.Parent; current.Parent != null; current = current.Parent) {
            finishToRoot.add(current.NodeValue);
        }

        int commonMax = findGreatestCommonMax(startToRoot, finishToRoot);
        for (TreeNode<Integer> current = start; current.Parent != null; current = current.Parent) {
            cycle.addLast(current.NodeValue);
            if (current.NodeValue == commonMax) {
                break;
            }
        }

        for (TreeNode<Integer> current = finish.Parent; current.NodeValue != commonMax; current = current.Parent) {
            cycle.addFirst(current.NodeValue);
        }

        return new ArrayList<>(cycle);
    }

    private int findGreatestCommonMax(List<Integer> firstList, List<Integer> secondList) {
        Set<Integer> set1 = new HashSet<>(firstList);
        Set<Integer> set2 = new HashSet<>(secondList);
        set1.retainAll(set2);

        return Collections.max(set1);
    }

    private boolean isNewCycle(List<List<Integer>> cycles, List<Integer> newCycle) {
        for (List<Integer> cycle: cycles) {
            if (Set.of(cycle).equals(Set.of(newCycle))) {
                return false;
            }
        }

        return true;
    }

    private void findCyclesRecursive(Tree<Integer> pathsTree) {
        TreeNode<Integer> currentVertex = queueToBFS.dequeue();
        int previousVertexIndex = currentVertex.Parent == null ? -1 : currentVertex.Parent.NodeValue;

        TreeNode<Integer> neighbour;
        for (int i = 0; i < max_vertex; i++) {
            if (vertex[i] == null || m_adjacency[currentVertex.NodeValue][i] == 0) {
                continue;
            }

            neighbour = new TreeNode<>(i, currentVertex);
            if (!vertex[i].Hit) {
                vertex[i].Hit = true;
                queueToBFS.enqueue(neighbour);
                pathsTree.AddChild(currentVertex, neighbour);
                continue;
            }

            if (i != previousVertexIndex) {
                pathsTree.AddChild(currentVertex, neighbour);
            }
        }

        if (queueToBFS.size() == 0) {
            return;
        }

        findCyclesRecursive(pathsTree);
    }

    /*
    Задание 12.
    Задача 1.
    Подсчитать общее число треугольников в графе.
    Временная сложность - O(V^3).
    Пространственная сложность - O(V).

    Рефлексия:
    Идея с делением количества на 3 или 6 не очень нравится, потому что не надёжно и зависит от реализации. А вот идея
    со множеством из строк красивая! Печально, конечно, что всё равно O(V^3).
     */
    public int countTriangle() {
        Arrays.stream(vertex).filter(Objects::nonNull).forEach(v -> v.Hit = false);

        AtomicInteger triangleCounter = new AtomicInteger(0);
        Set<Integer> belongTriangleSet = new HashSet<>();
        Queue<Integer> queueToWeakVertices = new Queue<>();

        for (int i = 0; i < max_vertex; i++) {
            if (vertex[i] != null && !vertex[i].Hit) {
                vertex[i].Hit = true;
                countTriangleRecursive(triangleCounter, belongTriangleSet, queueToWeakVertices, i);
            }
        }

        return triangleCounter.get();
    }

    private void countTriangleRecursive(AtomicInteger triangleCounter, Set<Integer> belongTriangleSet,
                                        Queue<Integer> queueToWeakVertices, int currentVertexIndex) {
        List<Integer> neighboursIndexes = getNeighbours(currentVertexIndex);
        if (!belongTriangleSet.contains(currentVertexIndex) &&
                isBelongTriangle(belongTriangleSet, neighboursIndexes, currentVertexIndex)) {
            triangleCounter.incrementAndGet();
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

        countTriangleRecursive(triangleCounter, belongTriangleSet, queueToWeakVertices, queueToWeakVertices.dequeue());
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

    /*
    Задание 12.
    Задача 2.
    Найти вершины не входящие в треугольники, используя только интерфейс графа.
    Временная сложность - O(2^V).
    Пространственная сложность - O(2^V).

    Рефлексия:
    Как будто не совсем честно, что мы изменяем возвращаемое значение метода из предыдущей задачи. Либо я не правильно
    понимаю значение "только через интерфейс класса". Предложенное решение лучше моего, потому что в своём решении
    я использую метод для поиска всех циклом в графе, временная сложность которого экспоненциальная, а здесь используется
    метод подсчёта треугольников, временная сложность которого O(V^3).
     */
    public List<Integer> findVerticesNotInTriangles() {
        List<Integer> vertexValues = new ArrayList<>();
        Arrays.stream(vertex)
                .filter(Objects::nonNull)
                .forEach(v -> vertexValues.add(v.Value));

        List<List<Integer>> cycles = findAllCycles();
        cycles.stream()
                .filter(cycle -> cycle.size() == 4)
                .flatMap(List::stream)
                .forEach(vertexValues::remove);

        return vertexValues;
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

    Рефлексия:
    Решение совпадает с предложенным. Понравилось название для удаления вершины из списка посещённых - backtracking!
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

    Рефлексия:
    Эталонное решение более понятное, чем моё, и оно более универсальное (можно распространить на произвольный граф).
    Но, кажется, что в этом решении мы 2 раза обойдём всё дерево, а в моём решении всего один раз, но после прохода
    выполняются ещё дополнительные вычисления для поиска 2 максимумов. Как бы то ни было, в обоих случаях временная
    сложность O(V).
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

    public List<TreeNode<T>> FindNodesByValue(T val)
    {
        if (Root == null) {
            return new ArrayList<>();
        }

        return FindNodesByValueRecursive(Root, val);
    }

    private List<TreeNode<T>> FindNodesByValueRecursive(TreeNode<T> parent, final T val) {
        List<TreeNode<T>> equalsNodes = new ArrayList<>();
        if (parent.NodeValue.equals(val)) {
            equalsNodes.add(parent);
        }

        List<TreeNode<T>> children = parent.Children;
        if (children == null) {
            return equalsNodes;
        }

        children.forEach(child -> equalsNodes.addAll(FindNodesByValueRecursive(child, val)));

        return equalsNodes;
    }
}


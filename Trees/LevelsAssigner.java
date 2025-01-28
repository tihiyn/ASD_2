import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LevelsAssigner<T> {
    public Map<SimpleTreeNode<T>, Integer> assignLevels(SimpleTree<T> tree) {
        if (tree.Root == null) {
            return new HashMap<>();
        }

        return assignLevelsRecursive(tree.Root, 0);
    }

    private Map<SimpleTreeNode<T>, Integer> assignLevelsRecursive(SimpleTreeNode<T> parent, int level) {
        Map<SimpleTreeNode<T>, Integer> nodesLevelsMap = new HashMap<>();
        nodesLevelsMap.put(parent, level);

        List<SimpleTreeNode<T>> children = parent.Children;
        if (children == null) {
            return nodesLevelsMap;
        }

        for (SimpleTreeNode<T> child: children) {
            nodesLevelsMap.putAll(assignLevelsRecursive(child, level + 1));
        }

        return nodesLevelsMap;
    }
}


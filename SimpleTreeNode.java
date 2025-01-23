import java.util.*;

public class SimpleTreeNode<T>
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

    public void DeleteNode(SimpleTreeNode<T> NodeToDelete)
    {
        SimpleTreeNode<T> parentNode = NodeToDelete.Parent;
        parentNode.Children.remove(NodeToDelete);
        if (parentNode.Children.isEmpty()) {
            parentNode.Children = null;
        }
    }

    public List<SimpleTreeNode<T>> GetAllNodes()
    {
        if (Root == null) {
            return new ArrayList<>();
        }

        return GetAllNodesRecursive(Root);
    }

    private List<SimpleTreeNode<T>> GetAllNodesRecursive(SimpleTreeNode<T> parent) {
        List<SimpleTreeNode<T>> nodes = new ArrayList<>();
        nodes.add(parent);

        if (parent.Children == null) {
            return nodes;
        }

        List<SimpleTreeNode<T>> children = parent.Children;
        children.forEach(child -> nodes.addAll(GetAllNodesRecursive(child)));

        return nodes;
    }

    public List<SimpleTreeNode<T>> FindNodesByValue(T val)
    {
        if (Root == null) {
            return new ArrayList<>();
        }

        return FindNodesByValueRecursive(Root, val);
    }

    private List<SimpleTreeNode<T>> FindNodesByValueRecursive(SimpleTreeNode<T> parent, final T val) {
        List<SimpleTreeNode<T>> equalsNodes = new ArrayList<>();
        if (parent.NodeValue.equals(val)) {
            equalsNodes.add(parent);
        }

        List<SimpleTreeNode<T>> children = parent.Children;
        if (children == null) {
            return equalsNodes;
        }

        children.forEach(child -> equalsNodes.addAll(FindNodesByValueRecursive(child, val)));

        return equalsNodes;
    }

    public void MoveNode(SimpleTreeNode<T> OriginalNode, SimpleTreeNode<T> NewParent)
    {
        DeleteNode(OriginalNode);
        AddChild(NewParent, OriginalNode);
    }

    public int Count()
    {
        if (Root == null) {
            return 0;
        }

        return CountRecursive(Root);
    }

    private int CountRecursive(SimpleTreeNode<T> parent) {
        int counter = 1;
        if (parent.Children == null) {
            return counter;
        }

        List<SimpleTreeNode<T>> children = parent.Children;
        for (SimpleTreeNode<T> child: children) {
            counter += CountRecursive(child);
        }

        return counter;
    }

    public int LeafCount()
    {
        if (Root == null) {
            return 0;
        }

        return LeafCountRecursive(Root);
    }

    private int LeafCountRecursive(SimpleTreeNode<T> parent) {
        int counter = 0;
        if (parent.Children == null) {
            return counter + 1;
        }

        List<SimpleTreeNode<T>> children = parent.Children;

        for (SimpleTreeNode<T> child: children) {
            counter += LeafCountRecursive(child);
        }

        return counter;
    }

    public boolean isSymmetrical() {
        if (Root == null) {
            return true;
        }

        return checkSymmetry(Root);
    }

    private boolean checkSymmetry(SimpleTreeNode<T> parent) {
        if (parent.Children == null) {
            return true;
        }

        boolean isSymmetrical;
        for (int i = 0; i < parent.Children.size() / 2; i++) {
            isSymmetrical = checkPairSymmetry(parent.Children.get(i), parent.Children.get(parent.Children.size() - 1 - i));
            if (!isSymmetrical) {
                return false;
            }
        }

        if (parent.Children.size() % 2 != 0) {
            return checkSymmetry(parent.Children.get(parent.Children.size() / 2));
        }

        return true;
    }

    private boolean checkPairSymmetry(SimpleTreeNode<T> first, SimpleTreeNode<T> second) {
        if (!Objects.equals(first.NodeValue, second.NodeValue)) {
            return false;
        }

        if ((first.Children == null) != (second.Children == null)) {
            return false;
        }

        if (first.Children == null) {
            return true;
        }

        if (first.Children.size() != second.Children.size()) {
            return false;
        }

        List<SimpleTreeNode<T>> childrenBoth = new ArrayList<>();
        childrenBoth.addAll(first.Children);
        childrenBoth.addAll(second.Children);

        boolean isEquals;
        for (int i = 0; i < childrenBoth.size() / 2; i++) {
            isEquals = checkPairSymmetry(childrenBoth.get(i), childrenBoth.get(childrenBoth.size() - 1 - i));
            if (!isEquals) {
                return false;
            }
        }

        return true;
    }
}


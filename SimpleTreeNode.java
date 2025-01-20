import java.util.*;

public class SimpleTreeNode<T>
{
    public T NodeValue;
    public SimpleTreeNode<T> Parent;
    public List<SimpleTreeNode<T>> Children;

    public SimpleTreeNode(T val, SimpleTreeNode<T> parent)
    {
        NodeValue = val;
        Parent = parent;
        Children = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleTreeNode<?> that = (SimpleTreeNode<?>) o;
        return Objects.equals(NodeValue, that.NodeValue) && Objects.equals(Parent, that.Parent) && Objects.equals(Children, that.Children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(NodeValue, Parent, Children);
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
            return;
        }

        if (ParentNode.Children == null) {
            ParentNode.Children = new ArrayList<>();
        }

        NewChild.Parent = ParentNode;
        ParentNode.Children.add(NewChild);
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
}


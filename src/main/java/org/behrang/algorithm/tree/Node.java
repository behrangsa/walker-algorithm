package org.behrang.algorithm.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Node<T> {

    private static final double DEFAULT_MODIFIER = 0.0;

    private static final double DEFAULT_PRELIM = 0.0;

    private Node<T> parent;

    private final List<Node<T>> children;

    private T value;

    private double x;

    private double y;

    private double width;

    private double height;

    private double prelim = DEFAULT_PRELIM;

    private double modifier = DEFAULT_MODIFIER;

    private Node<T> leftNeighbor;

    public Node() {
        this(null);
    }

    public Node(T value) {
        this.value = value;
        this.children = new ArrayList<>();
    }

    public Node<T> getParent() {
        return parent;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public boolean hasParent() {
        return getParent() != null;
    }

    public List<Node<T>> getChildren() {
        return children;
    }

    public boolean hasChildren() {
        return !getChildren().isEmpty();
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getPrelim() {
        return prelim;
    }

    public void setPrelim(double prelim) {
        this.prelim = prelim;
    }

    public double getModifier() {
        return modifier;
    }

    public void setModifier(double modifier) {
        this.modifier = modifier;
    }

    public Node<T> getLeftNeighbor() {
        return leftNeighbor;
    }

    public void setLeftNeighbor(Node<T> leftNeighbor) {
        this.leftNeighbor = leftNeighbor;
    }

    public boolean isLeaf() {
        return this.children.isEmpty();
    }

    public int getLevel() {
        int level = 0;
        Node<T> node = this;

        while (node.getParent() != null) {
            level += 1;
            node = node.getParent();
        }

        return level;
    }

    public Node<T> getLeftSibling() {
        if (parent == null) {
            return null;
        }

        List<Node<T>> siblings = parent.getChildren();
        int nodeIndex = siblings.indexOf(this);
        if (nodeIndex == 0) {
            return null;
        }

        return siblings.get(nodeIndex - 1);
    }

    public boolean hasLeftSibling() {
        return getLeftSibling() != null;
    }

    public Node<T> getRightSibling() {
        if (parent == null) {
            return null;
        }

        List<Node<T>> siblings = parent.getChildren();
        int nodeIndex = siblings.indexOf(this);
        if (nodeIndex == siblings.size() - 1) {
            return null;
        }

        return siblings.get(nodeIndex + 1);
    }

    public boolean hasRightSibling() {
        return getRightSibling() != null;
    }

    public Node<T> getLeftChild() {
        if (children.isEmpty()) {
            return null;
        }

        return children.get(0);
    }

    public Node<T> getRightChild() {
        if (children.isEmpty()) {
            return null;
        }

        return children.get(children.size() - 1);
    }

    public Node<T> getLeftmost(int targetLevel) {
        targetLevel = targetLevel == -1 ? Integer.MAX_VALUE : targetLevel;
        if (targetLevel <= this.getLevel() && targetLevel != -1) {
            throw new IllegalArgumentException("Parameter targetLevel must be greater than the current level");
        }

        Stack<Node<T>> visitNext = new Stack<>();
        visitNext.push(this);
        while (!visitNext.empty()) {
            Node<T> head = visitNext.pop();
            if (head.getLevel() == targetLevel) {
                return head;
            }

            if (head.children != null) {
                for (int i = 0; i < head.children.size(); i++) {
                    int index = head.children.size() - i - 1;
                    Node<T> child = head.children.get(index);
                    visitNext.push(child);
                }
            }
        }

        return null;
    }

    public void incrementPrelimBy(double delta) {
        setPrelim(prelim + delta);
    }

    public void incrementModifierBy(double delta) {
        setModifier(modifier + delta);
    }

    public int getMaxDepth() {
        Node<T> tree = this;
        if (tree.getChildren().isEmpty()) {
            return 1;
        }

        return tree.getChildren()
                .stream()
                .mapToInt(Node::getMaxDepth)
                .max()
                .orElse(0) + 1;
    }

    @Override
    public String toString() {
        if (value == null) {
            return "<null>";
        }

        return value.toString();
    }
}

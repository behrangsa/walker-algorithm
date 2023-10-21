package org.behrang.algorithm.tree;

import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Node<T> {

    private static final float DEFAULT_MODIFIER = 0.0f;

    private static final float DEFAULT_PRELIM = 0.0f;

    private static final float DEFAULT_WIDTH = 2.0f;

    private static final float DEFAULT_HEIGHT = 2.0f;

    private Node<T> parent;

    private final List<Node<T>> children;

    private T value;

    private float x;

    private float y;

    private float width;

    private float height;

    private float prelim = DEFAULT_PRELIM;

    private float modifier = DEFAULT_MODIFIER;

    private Node<T> leftNeighbor;

    public Node() {
        this(null);
    }

    public Node(T value) {
        this(value, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public Node(T value, float width, float height) {
        Validate.notNull(value, "value must be non-null");
        Validate.isTrue(width > 0);
        Validate.isTrue(height > 0);

        this.value = value;
        this.width = width;
        this.height = height;
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

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getPrelim() {
        return prelim;
    }

    public void setPrelim(float prelim) {
        this.prelim = prelim;
    }

    public float getModifier() {
        return modifier;
    }

    public void setModifier(float modifier) {
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

    public void incrementPrelimBy(float delta) {
        setPrelim(prelim + delta);
    }

    public void incrementModifierBy(float delta) {
        setModifier(modifier + delta);
    }

    public int getDepth() {
        Node<T> tree = this;
        if (tree.getChildren().isEmpty()) {
            return 1;
        }

        return tree.getChildren()
            .stream()
            .mapToInt(Node::getDepth)
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

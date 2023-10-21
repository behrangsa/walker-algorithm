package org.behrang.algorithm.tree;

import java.util.function.Consumer;

public final class Functions {
    private Functions() {
        throw new RuntimeException();
    }

    public static Node<?> leftmostDescendantAtDepth(Node<?> node, int depth) {
        return leftmostDescendantAtDepth(node, 0, depth);
    }

    private static Node<?> leftmostDescendantAtDepth(Node<?> node, int level, int depth) {
        if (level >= depth) {
            return node;
        } else if (node.isLeaf()) {
            return null;
        } else {
            var leftChild = node.getLeftChild();
            var leftmost = leftmostDescendantAtDepth(leftChild, level + 1, depth);
            while (leftmost == null && leftChild.hasRightSibling()) {
                leftChild = leftChild.getRightSibling();
                leftmost = leftmostDescendantAtDepth(leftChild, level + 1, depth);
            }

            return leftmost;
        }
    }

    public static <T> void prePostOrder(Node<T> node, Consumer<Node<T>> preConsumer, Consumer<Node<T>> postConsumer) {
        preConsumer.accept(node);

        node.getChildren().forEach(n -> {
            prePostOrder(n, preConsumer, postConsumer);
        });

        postConsumer.accept(node);
    }
}

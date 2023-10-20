package org.behrang.algorithm.tree;

import org.behrang.algorithm.tree.Node;

import java.util.function.Consumer;

public class TreeTraversal {
    public static <T> void preorder(Node<T> root, Consumer<Node<T>> consumer) {
        consumer.accept(root);

        root.getChildren().forEach(ch -> {
            preorder(ch, consumer);
        });
    }
}

package org.behrang.algorithm.tree.data;

import org.behrang.algorithm.tree.Node;

public interface NodeGenerator<T> {
    Node<T> generate(T value);
}

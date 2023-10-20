package org.behrang.algorithm.tree.demo;

import org.behrang.algorithm.tree.Node;

public final class SampleTree {

    private SampleTree() {
    }

    public static Node<String> newUniformInstance() {
        return newUniformInstance(2, 2);
    }

    public static Node<String> newUniformInstance(double nodeWidth, double nodeHeight) {
        Node<String> a, b, c, d, e, f, g, h, i, j, k, l, m, n, o;

        o = node("O", nodeWidth, nodeHeight);
        e = node("E", nodeWidth, nodeHeight);
        f = node("F", nodeWidth, nodeHeight);
        n = node("N", nodeWidth, nodeHeight);
        link(o, e, f, n);

        a = node("A", nodeWidth, nodeHeight);
        d = node("D", nodeWidth, nodeHeight);
        link(e, a, d);

        b = node("B", nodeWidth, nodeHeight);
        c = node("C", nodeWidth, nodeHeight);
        link(d, b, c);

        g = node("G", nodeWidth, nodeHeight);
        m = node("M", nodeWidth, nodeHeight);
        link(n, g, m);

        h = node("H", nodeWidth, nodeHeight);
        i = node("I", nodeWidth, nodeHeight);
        j = node("J", nodeWidth, nodeHeight);
        k = node("K", nodeWidth, nodeHeight);
        l = node("L", nodeWidth, nodeHeight);
        link(m, h, i, j, k, l);

        return o;
    }

    static Node<String> node(String value, double nodeWidth, double nodeHeight) {
        Node<String> node = new Node<>(value);
        node.setWidth(nodeWidth);
        node.setHeight(nodeHeight);

        return node;
    }

    @SafeVarargs
    static void link(Node<String> parent, Node<String>... children) {
        for (var child : children) {
            parent.getChildren().add(child);
            child.setParent(parent);
        }
    }
}

package org.behrang.algorithm.tree.data;

import org.behrang.algorithm.tree.Node;

public final class SampleTreeGenerator {

    private SampleTreeGenerator() {
    }

    public static Node<String> newUniformInstance() {
        return newUniformInstance(2, 2);
    }

    public static Node<String> newUniformInstance(float nodeWidth, float nodeHeight) {
        return newInstance(new FixedDimensionNodeGenerator<>(nodeWidth, nodeHeight));
    }

    public static Node<String> newMultiformInstance(float minWidth, float maxWidth, float minHeight, float maxHeight) {
        return newInstance(new RandomDimensionNodeGenerator<>(minWidth, maxWidth, minHeight, maxHeight));
    }

    static Node<String> newInstance(NodeGenerator<String> generator) {
        Node<String> a, b, c, d, e, f, g, h, i, j, k, l, m, n, o;

        o = generator.generate("O");
        e = generator.generate("E");
        f = generator.generate("F");
        n = generator.generate("N");
        link(o, e, f, n);

        a = generator.generate("A");
        d = generator.generate("D");
        link(e, a, d);

        b = generator.generate("B");
        c = generator.generate("C");
        link(d, b, c);

        g = generator.generate("G");
        m = generator.generate("M");
        link(n, g, m);

        h = generator.generate("H");
        i = generator.generate("I");
        j = generator.generate("J");
        k = generator.generate("K");
        l = generator.generate("L");
        link(m, h, i, j, k, l);

        return o;
    }

    @SafeVarargs
    static void link(Node<String> parent, Node<String>... children) {
        for (var child : children) {
            parent.getChildren().add(child);
            child.setParent(parent);
        }
    }
}

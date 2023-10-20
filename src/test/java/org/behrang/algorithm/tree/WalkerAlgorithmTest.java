package org.behrang.algorithm.tree;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.geom.Dimension2D;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class WalkerAlgorithmTest {
    Node<String> a, b, c, d, e, f, g, h, i, j, k, l, m, n, o;

    WalkerAlgorithm<String> algorithm;

    @BeforeEach()
    void beforeEach() {
        algorithm = new WalkerAlgorithm<>(
                4,
                4,
                0,
                0,
                4
        );

        o = node("O");
        e = node("E");
        f = node("F");
        n = node("N");
        link(o, e, f, n);

        a = node("A");
        d = node("D");
        link(e, a, d);

        b = node("B");
        c = node("C");
        link(d, b, c);

        g = node("G");
        m = node("M");
        link(n, g, m);

        h = node("H");
        i = node("I");
        j = node("J");
        k = node("K");
        l = node("L");
        link(m, h, i, j, k, l);
    }

    @Test
    void testGetLeftmostDescendant() {
        var walker = new WalkerAlgorithm<String>();
        Node<String> leftmost;

        leftmost = walker.getLeftmostDescendant(o, 0, 0);
        Assertions.assertSame(o, leftmost);

        leftmost = walker.getLeftmostDescendant(o, 0, 1);
        Assertions.assertSame(e, leftmost);

        leftmost = walker.getLeftmostDescendant(o, 0, 2);
        Assertions.assertSame(a, leftmost);

        leftmost = walker.getLeftmostDescendant(o, 0, 3);
        Assertions.assertSame(b, leftmost);

        leftmost = walker.getLeftmostDescendant(f, 1, 1);
        Assertions.assertSame(f, leftmost);

        leftmost = walker.getLeftmostDescendant(f, 1, 2);
        Assertions.assertNull(leftmost);

        leftmost = walker.getLeftmostDescendant(n, 1, 1);
        Assertions.assertSame(n, leftmost);

        leftmost = walker.getLeftmostDescendant(n, 1, 2);
        Assertions.assertSame(g, leftmost);

        leftmost = walker.getLeftmostDescendant(n, 1, 3);
        Assertions.assertSame(h, leftmost);

        leftmost = walker.getLeftmostDescendant(g, 2, 2);
        Assertions.assertSame(g, leftmost);

        leftmost = walker.getLeftmostDescendant(g, 2, 3);
        Assertions.assertNull(leftmost);

        leftmost = walker.getLeftmostDescendant(m, 2, 2);
        Assertions.assertSame(m, leftmost);

        leftmost = walker.getLeftmostDescendant(m, 2, 3);
        Assertions.assertSame(h, leftmost);
    }

    @Test
    void testPosition() {
        algorithm.position(o);
        Dimension2D dimension2D = DimensionCalculator.calculateTreeDimension(o);
        System.out.println(dimension2D);

        assertNode("O", 13.5, o);

        var e = o.getChildren().get(0);
        assertNode("E", 3.0, e);

        var a = e.getChildren().get(0);
        assertNode("A", 0.0, a);

        var d = e.getChildren().get(1);
        assertNode("D", 6, d);

        var b = d.getChildren().get(0);
        assertNode("B", 3, b);

        var c = d.getChildren().get(1);
        assertNode("C", 9.0, c);

        var f = o.getChildren().get(1);
        assertNode("F", 13.5, f);

        var n = o.getChildren().get(2);
        assertNode("N", 24.0, n);

        var g = n.getChildren().get(0);
        assertNode("G", 21, g);

        var m = n.getChildren().get(1);
        assertNode("M", 27, m);

        var h = m.getChildren().get(0);
        assertNode("H", 15, h);

        var i = m.getChildren().get(1);
        assertNode("I", 21, i);

        var j = m.getChildren().get(2);
        assertNode("J", 27, j);

        var k = m.getChildren().get(3);
        assertNode("K", 33, k);

        var l = m.getChildren().get(4);
        assertNode("L", 39, l);
    }

    static void assertNode(String value, double x, Node<String> node) {
        assertAll(() -> {
            assertEquals(value, node.getValue());
        }, () -> {
            assertEquals(x, node.getX());
        });
    }

    static Node<String> node(String value) {
        Node<String> node = new Node<>(value);
        node.setWidth(2);
        node.setHeight(2);

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
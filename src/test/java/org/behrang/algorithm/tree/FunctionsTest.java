package org.behrang.algorithm.tree;

import org.behrang.algorithm.tree.data.SampleTreeGenerator;
import org.junit.jupiter.api.Test;

import static org.behrang.algorithm.tree.Functions.leftmostDescendantAtDepth;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FunctionsTest {

    @Test
    void testLeftmostDescendantAtDepth() {
        var o = SampleTreeGenerator.newUniformInstance();

        assertEquals("O", leftmostDescendantAtDepth(o, 0).getValue());
        assertEquals("E", leftmostDescendantAtDepth(o, 1).getValue());
        assertEquals("A", leftmostDescendantAtDepth(o, 2).getValue());
        assertEquals("B", leftmostDescendantAtDepth(o, 3).getValue());

        var n = o.getChildren().get(2);

        assertEquals("N", leftmostDescendantAtDepth(n, 0).getValue());
        assertEquals("G", leftmostDescendantAtDepth(n, 1).getValue());
        assertEquals("H", leftmostDescendantAtDepth(n, 2).getValue());
    }

}
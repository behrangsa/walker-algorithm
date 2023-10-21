package org.behrang.algorithm.tree;

import org.behrang.algorithm.tree.data.SampleTreeGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DimensionCalculatorTest {
    @Test
    void testCalculateDimensions() {
        var root = SampleTreeGenerator.newUniformInstance();
        var algorithm = new WalkerAlgorithm<String>(4, 4, 0, 0, 4);

        algorithm.position(root);

        var dim = DimensionCalculator.calculateTreeDimension(root);
        assertEquals(41, dim.getWidth());
        assertEquals(14, dim.getHeight());
    }

    @Test
    void testCalculateMaxNodeDimension() {
        var root = SampleTreeGenerator.newUniformInstance();
        var algorithm = new WalkerAlgorithm<String>(4, 4, 0, 0, 4);

        algorithm.position(root);

        var dim = DimensionCalculator.calculateMaxNodeDimension(root);
        assertEquals(2, dim.getWidth());
        assertEquals(2, dim.getHeight());
    }
}
package org.behrang.algorithm.tree.data;

import org.apache.commons.lang3.Validate;
import org.behrang.algorithm.tree.Node;

public class FixedDimensionNodeGenerator<T> implements NodeGenerator<T> {

    private float width;

    private float height;

    public FixedDimensionNodeGenerator(float width, float height) {
        Validate.notNaN(width);
        Validate.notNaN(height);

        Validate.isTrue(width > 0);
        Validate.isTrue(height > 0);

        this.width = width;
        this.height = height;
    }

    @Override
    public Node<T> generate(T value) {
        Validate.notNull(value, "value must be non-null");

        return new Node<>(value, width, height);
    }
}

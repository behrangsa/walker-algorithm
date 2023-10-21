package org.behrang.algorithm.tree.data;

import org.apache.commons.lang3.Validate;
import org.behrang.algorithm.tree.Node;

import java.util.Random;

public class RandomDimensionNodeGenerator<T> implements NodeGenerator<T> {
    private final Random random;

    private float minWidth;

    private float maxWidth;

    private float minHeight;

    private float maxHeight;

    public RandomDimensionNodeGenerator(Random random, float minWidth, float maxWidth, float minHeight, float maxHeight) {
        Validate.notNull(random, "random must be non-null");

        Validate.notNaN(minWidth);
        Validate.notNaN(maxWidth);
        Validate.notNaN(minHeight);
        Validate.notNaN(maxHeight);

        Validate.isTrue(minWidth > 0);
        Validate.isTrue(maxWidth > minWidth);

        Validate.isTrue(minHeight > 0);
        Validate.isTrue(maxHeight > minHeight);

        this.random = random;
        this.minWidth = minWidth;
        this.maxWidth = maxWidth;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
    }

    public RandomDimensionNodeGenerator(float minWidth, float maxWidth, float minHeight, float maxHeight) {
        this(new Random(), minWidth, maxWidth, minHeight, maxHeight);
    }

    @Override
    public Node<T> generate(T value) {
        Validate.notNull(value, "value must be non-null");

        float width = random.nextFloat(minWidth, maxWidth);
        float height = random.nextFloat(minHeight, maxHeight);

        return new Node<>(value, width, height);
    }
}

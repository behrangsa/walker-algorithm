package org.behrang.algorithm.tree;

import java.awt.*;
import java.awt.geom.Dimension2D;
import java.util.concurrent.atomic.AtomicReference;

public class DimensionCalculator {
    public static <T> Dimension2D calculateTreeDimension(Node<T> root) {
        final AtomicReference<Float> minX = new AtomicReference<>(Float.MAX_VALUE);
        final AtomicReference<Float> minY = new AtomicReference<>(Float.MAX_VALUE);
        final AtomicReference<Float> maxX = new AtomicReference<>(Float.MIN_VALUE);
        final AtomicReference<Float> maxY = new AtomicReference<>(Float.MIN_VALUE);

        TreeTraversal.preorder(root, n -> {
            if (n.getX() < minX.get()) {
                minX.set(n.getX());
            }

            if (n.getY() < minY.get()) {
                minY.set(n.getY());
            }

            float x2 = n.getX() + n.getWidth();
            if (x2 > maxX.get()) {
                maxX.set(x2);
            }

            float y2 = n.getY() + n.getHeight();
            if (y2 > maxY.get()) {
                maxY.set(y2);
            }
        });

        float width = (maxX.get() - minX.get());
        float height = (maxY.get() - minY.get());

        Dimension dim = new Dimension();
        dim.setSize(width, height);

        return dim;
    }

    public static <T> Dimension2D calculateMaxNodeDimension(Node<T> root) {
        final AtomicReference<Float> width = new AtomicReference<>(Float.MIN_VALUE);
        final AtomicReference<Float> height = new AtomicReference<>(Float.MIN_VALUE);

        TreeTraversal.preorder(root, n -> {
            if (width.get() < n.getWidth()) {
                width.set(n.getWidth());
            }

            if (height.get() < n.getHeight()) {
                height.set(n.getHeight());
            }
        });

        Dimension dim = new Dimension();
        dim.setSize(width.get(), height.get());

        return dim;
    }

}

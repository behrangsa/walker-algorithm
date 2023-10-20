package org.behrang.algorithm.tree;

import java.awt.*;
import java.awt.geom.Dimension2D;
import java.util.concurrent.atomic.AtomicReference;

public class DimensionCalculator {
    public static <T> Dimension2D calculateTreeDimension(Node<T> root) {
        final AtomicReference<Double> minX = new AtomicReference<>(Double.MAX_VALUE);
        final AtomicReference<Double> minY = new AtomicReference<>(Double.MAX_VALUE);
        final AtomicReference<Double> maxX = new AtomicReference<>(Double.MIN_VALUE);
        final AtomicReference<Double> maxY = new AtomicReference<>(Double.MIN_VALUE);

        TreeTraversal.preorder(root, n -> {
            if (n.getX() < minX.get()) {
                minX.set(n.getX());
            }

            if (n.getY() < minY.get()) {
                minY.set(n.getY());
            }

            double x2 = n.getX() + n.getWidth();
            if (x2 > maxX.get()) {
                maxX.set(x2);
            }

            double y2 = n.getY() + n.getHeight();
            if (y2 > maxY.get()) {
                maxY.set(y2);
            }
        });

        double width = (maxX.get() - minX.get());
        double height = (maxY.get() - minY.get());

        Dimension dim = new Dimension();
        dim.setSize(width, height);

        return dim;
    }

    public static <T> Dimension2D calculateMaxNodeDimension(Node<T> root) {
        final AtomicReference<Double> width = new AtomicReference<>(Double.MIN_VALUE);
        final AtomicReference<Double> height = new AtomicReference<>(Double.MIN_VALUE);

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

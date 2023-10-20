package org.behrang.algorithm.tree.demo;

import org.behrang.algorithm.tree.Node;
import org.behrang.algorithm.tree.TreeTraversal;
import org.behrang.algorithm.tree.WalkerAlgorithm;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import static org.behrang.algorithm.tree.DimensionCalculator.calculateMaxNodeDimension;

public class TreePanel<T> extends JPanel {

    private Node<T> root;

    public TreePanel(Node<T> root) {
        setLayout(null);
        setRoot(root);
    }

    public void setRoot(Node<T> root) {
        this.root = root;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (root == null) {
            return;
        }

        var dim = calculateMaxNodeDimension(root);

        var walker = new WalkerAlgorithm<T>(
                dim.getWidth(),
                dim.getWidth(),
                20,
                20,
                dim.getHeight() * 2
        );

        walker.position(root);

        var g2 = (Graphics2D) g;
        TreeTraversal.preorder(root, n -> {
            drawNode(g2, n);
        });
    }

    void drawNode(Graphics2D g, Node<T> node) {
        double labelMarginX = 12.0;
        double labelMarginY = 1.0;

        g.setColor(Color.BLACK);
        Rectangle2D rect = new Rectangle2D.Double(
                node.getX(),
                node.getY(),
                node.getWidth(),
                node.getHeight()
        );
        g.draw(rect);

        String label = node.getValue().toString();

        g.setColor(Color.DARK_GRAY);
        Font font = new Font(Font.SERIF, Font.ITALIC, 24);
        g.setFont(font);

        FontMetrics fontMetrics = g.getFontMetrics(font);
        Rectangle2D labelBounds = fontMetrics.getStringBounds(label, g);

        g.drawString(label,
                (float) (node.getX() + labelMarginX),
                (float) (node.getY() + labelMarginY + labelBounds.getHeight())
        );

        if (node.hasParent()) {
            Node<T> parent = node.getParent();
            double x0 = parent.getX() + parent.getWidth() / 2;
            double y0 = parent.getY() + parent.getHeight();
            double x1 = node.getX() + node.getWidth() / 2;
            double y1 = node.getY();
            Line2D line2D = new Line2D.Double(x0, y0, x1, y1);
            g.draw(line2D);
        }
    }
}

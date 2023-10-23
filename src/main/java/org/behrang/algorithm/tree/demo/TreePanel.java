package org.behrang.algorithm.tree.demo;

import org.behrang.algorithm.tree.Functions;
import org.behrang.algorithm.tree.Node;
import org.behrang.algorithm.tree.WalkerAlgorithm;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.KEY_TEXT_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;
import static java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_GASP;
import static org.behrang.algorithm.tree.DimensionCalculator.calculateMaxNodeDimension;

public class TreePanel<T> extends JPanel {

    private static final float PADDING = 5.0f;

    private Node<T> root;

    private WalkerAlgorithm<T> walker;

    public TreePanel(Node<T> root) {
        setLayout(null);
        setRoot(root);

        initWalker();
    }

    public void setRoot(Node<T> root) {
        this.root = root;
        initWalker();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (root == null) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(KEY_TEXT_ANTIALIASING, VALUE_TEXT_ANTIALIAS_GASP);

        walker.position(root);

        Functions.preorder(root, n -> {
            drawNode(g2, n);
        });
    }

    void initWalker() {
        var dim = calculateMaxNodeDimension(root);

        walker = new WalkerAlgorithm<>(
            (float) dim.getWidth(),
            (float) dim.getWidth(),
            20,
            20,
            (float) (dim.getHeight() * 3)
        );
    }

    void drawNode(Graphics2D g, Node<T> node) {
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
        Font font = new Font(Font.SERIF, Font.PLAIN, 24);
        g.setFont(font);

        FontMetrics fontMetrics = g.getFontMetrics(font);
        float x = node.getX() + PADDING;
        float y = node.getY() + fontMetrics.getAscent();

        g.drawString(label, x, y);

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

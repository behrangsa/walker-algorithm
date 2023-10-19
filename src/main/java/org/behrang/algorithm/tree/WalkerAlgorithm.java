package org.behrang.algorithm.tree;

import java.util.HashMap;
import java.util.Map;

public class WalkerAlgorithm<T> {

    private static final long MAX_DEPTH = Long.MAX_VALUE;

    /**
     * During the operation of the algorithm, we walk the tree two times.
     * <p>
     * Whenever we visit a new node {@code N} at a level {@code L}, we store it in this map.
     * <p>
     * Then whenever we need to look up a node's neighboring node to the left, we can consult this map.
     * <p>
     * For example, consider the following tree:
     * <pre>
     *     A
     * ┌───┼───┐
     * B   C   D
     *     │   │
     *     E   F
     * </pre>
     * <p>
     * As you can see, node {@code A} is at level 0, nodes {@code B}, {@code C}, {@code D} are at level 1, and nodes
     * {@code E} and {@code F} are at level 2.
     * <p>
     * When we arrive at node {@code F}, we can consult to look up its left neighbor {@code E} using this table:
     * <pre>{@code
     *      // leftNeighbor will resolve to node "E"
     *      var leftNeighbor = previousNodeAtLevel.get(2);
     * }</pre>
     * <p>
     * Similarly, when we arrive at node {@code C}:
     * <pre>{@code
     *      // leftNeighbor will resolve to node "B"
     *      var leftNeighbor = previousNodeAtLevel.get(1);
     * }</pre>
     */
    private final Map<Integer, Node<T>> previousNodeAtLevel;

    private final double siblingSeparation;

    private final double subtreeSeparation;

    private final double levelSeparation;

    private final double xTopAdjustment;

    private final double yTopAdjustment;

    public WalkerAlgorithm(double siblingSeparation, double subtreeSeparation, double xTopAdjustment, double yTopAdjustment, double levelSeparation) {
        this.previousNodeAtLevel = new HashMap<>();
        this.siblingSeparation = siblingSeparation;
        this.subtreeSeparation = subtreeSeparation;
        this.xTopAdjustment = xTopAdjustment;
        this.yTopAdjustment = yTopAdjustment;
        this.levelSeparation = levelSeparation;
    }

    public WalkerAlgorithm() {
        this(0, 0, 0, 0, 0);
    }

    public void position(Node<T> node) {
        if (node != null) {
            previousNodeAtLevel.clear();
            firstWalk(node, 0);
            secondWalk(node, 0, 0);
        }
    }

    void firstWalk(Node<T> thisNode, int currentLevel) {
        Node<T> leftNeighbor = getPreviousNodeAtLevel(currentLevel);
        thisNode.setLeftNeighbor(leftNeighbor);
        setPreviousNodeAtLevel(currentLevel, thisNode);
        thisNode.getChildren().forEach(child -> {
            firstWalk(child, currentLevel + 1);
        });

        if (thisNode.isLeaf() || currentLevel == MAX_DEPTH) {
            if (thisNode.hasLeftSibling()) {
                thisNode.setPrelim(thisNode.getLeftSibling().getPrelim() + siblingSeparation + meanWidth(thisNode.getLeftSibling(), thisNode));
            } else {
                thisNode.setPrelim(0);
            }
        } else {
            double midpoint = (thisNode.getLeftChild().getPrelim() + thisNode.getRightChild().getPrelim()) / 2;

            if (thisNode.hasLeftSibling()) {
                thisNode.setPrelim(thisNode.getLeftSibling().getPrelim() + siblingSeparation + meanWidth(thisNode.getLeftSibling(), thisNode));
                thisNode.setModifier(thisNode.getPrelim() - midpoint);

                apportion(thisNode, currentLevel);
            } else {
                thisNode.setPrelim(midpoint);
            }
        }
    }

    void secondWalk(Node<T> node, int level, double modSum) {
        double xTemp = xTopAdjustment + node.getPrelim() + modSum;
        double yTemp = yTopAdjustment + (level * levelSeparation);

        node.setX(xTemp);
        node.setY(yTemp);

        for (Node<T> child : node.getChildren()) {
            secondWalk(child, level + 1, modSum + node.getModifier());
        }
    }

    void apportion(Node<T> treeNode, int baseLevel) {
        Node<T> leftMost = treeNode.getLeftChild();
        Node<T> leftNeighbor;

        for (int level = baseLevel; level < treeNode.getMaxDepth(); level++) {
            leftNeighbor = leftMost.getLeftNeighbor();

            if (leftMost == null || leftNeighbor == null) {
                return;
            }

            double leftModSum = 0.0;
            double rightModSum = 0.0;
            Node<T> ancestorLeftmost = leftMost;
            Node<T> ancestorNeighbor = leftNeighbor;
            while (ancestorLeftmost != treeNode) {
                ancestorLeftmost = ancestorLeftmost.getParent();
                ancestorNeighbor = ancestorNeighbor.getParent();
                rightModSum += ancestorLeftmost.getModifier();
                leftModSum += ancestorNeighbor.getModifier();
            }

            double moveDistance = (
                    leftNeighbor.getPrelim() +
                            leftModSum +
                            subtreeSeparation +
                            meanWidth(treeNode.getLeftSibling(), treeNode)
            ) - (leftMost.getPrelim() + rightModSum);

            if (moveDistance > 0) {
                Node<T> tempNode = treeNode;
                int numLeftSiblings = 0;

                while (tempNode != null && tempNode != ancestorNeighbor) {
                    numLeftSiblings += 1;
                    tempNode = tempNode.getLeftSibling();
                }

                if (tempNode != null) {
                    double portion = moveDistance / numLeftSiblings;
                    tempNode = treeNode;

                    while (tempNode != ancestorNeighbor) {
                        tempNode.incrementPrelimBy(moveDistance);
                        tempNode.incrementModifierBy(moveDistance);
                        moveDistance -= portion;
                        tempNode = tempNode.getLeftSibling();
                    }
                } else {
                    return;
                }
            }


            if (leftMost.getChildren().isEmpty()) {
                leftMost = treeNode.getLeftmost(level + 2);
            } else {
                leftMost = leftMost.getLeftChild();
            }
        }
    }

    Node<T> getPreviousNodeAtLevel(int level) {
        return previousNodeAtLevel.getOrDefault(level, null);
    }

    void setPreviousNodeAtLevel(int level, Node<T> node) {
        previousNodeAtLevel.put(level, node);
    }

    Node<T> getLeftmostDescendant(Node<T> node, int level, int depth) {
        if (level >= depth) {
            return node;
        }

        if (node.isLeaf()) {
            return null;
        }

        Node<T> rightmost = node.getLeftChild();
        Node<T> leftmost = getLeftmostDescendant(rightmost, level + 1, depth);

        while (leftmost == null && rightmost.hasRightSibling()) {
            rightmost = rightmost.getRightSibling();
            leftmost = getLeftmostDescendant(rightmost, level + 1, depth);
        }

        return leftmost;
    }

    double meanWidth(Node<T> n1, Node<T> n2) {
        return (n1.getWidth() + n2.getWidth()) / 2.0;
    }
}

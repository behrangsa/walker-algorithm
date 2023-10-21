package org.behrang.algorithm.tree;

import java.util.HashMap;
import java.util.Map;

public class WalkerAlgorithm<T> {

    private final Map<Integer, Node<T>> previousNodeAtLevel;

    private final float siblingSeparation;

    private final float subtreeSeparation;

    private final float levelSeparation;

    private final float xTopAdjustment;

    private final float yTopAdjustment;

    public WalkerAlgorithm(float siblingSeparation, float subtreeSeparation, float xTopAdjustment, float yTopAdjustment, float levelSeparation) {
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
        // We need to maintain
        Node<T> leftNeighbor = getPreviousNodeAtLevel(currentLevel);
        thisNode.setLeftNeighbor(leftNeighbor);
        setPreviousNodeAtLevel(currentLevel, thisNode);
        thisNode.getChildren().forEach(child -> {
            firstWalk(child, currentLevel + 1);
        });

        if (thisNode.isLeaf()) {
            if (thisNode.hasLeftSibling()) {
                thisNode.setPrelim(thisNode.getLeftSibling().getPrelim() + siblingSeparation + meanWidth(thisNode.getLeftSibling(), thisNode));
            } else {
                thisNode.setPrelim(0);
            }
        } else {
            float midpoint = (thisNode.getLeftChild().getPrelim() + thisNode.getRightChild().getPrelim()) / 2;

            if (thisNode.hasLeftSibling()) {
                thisNode.setPrelim(thisNode.getLeftSibling().getPrelim() + siblingSeparation + meanWidth(thisNode.getLeftSibling(), thisNode));
                thisNode.setModifier(thisNode.getPrelim() - midpoint);

                apportion(thisNode, currentLevel);
            } else {
                thisNode.setPrelim(midpoint);
            }
        }
    }

    void secondWalk(Node<T> node, int level, float modSum) {
        float xTemp = xTopAdjustment + node.getPrelim() + modSum;
        float yTemp = yTopAdjustment + (level * levelSeparation);

        node.setX(xTemp);
        node.setY(yTemp);

        for (Node<T> child : node.getChildren()) {
            secondWalk(child, level + 1, modSum + node.getModifier());
        }
    }

    void apportion(Node<T> treeNode, int baseLevel) {
        Node<T> leftMost = treeNode.getLeftChild();
        Node<T> leftNeighbor;

        for (int level = baseLevel; level < treeNode.getDepth(); level++) {
            leftNeighbor = leftMost.getLeftNeighbor();

            if (leftMost == null || leftNeighbor == null) {
                return;
            }

            float leftModSum = 0.0f;
            float rightModSum = 0.0f;
            Node<T> ancestorLeftmost = leftMost;
            Node<T> ancestorNeighbor = leftNeighbor;
            while (ancestorLeftmost != treeNode) {
                ancestorLeftmost = ancestorLeftmost.getParent();
                ancestorNeighbor = ancestorNeighbor.getParent();
                rightModSum += ancestorLeftmost.getModifier();
                leftModSum += ancestorNeighbor.getModifier();
            }

            float moveDistance = (
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
                    float portion = moveDistance / numLeftSiblings;
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

    float meanWidth(Node<T> n1, Node<T> n2) {
        return (n1.getWidth() + n2.getWidth()) / 2.0f;
    }
}

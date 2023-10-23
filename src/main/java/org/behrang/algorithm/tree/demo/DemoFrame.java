package org.behrang.algorithm.tree.demo;

import org.behrang.algorithm.tree.Node;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;

import static org.behrang.algorithm.tree.data.SampleTreeGenerator.newMultiformInstance;

public class DemoFrame extends JFrame {

    private JPanel mainPanel;

    public DemoFrame(Node<String> root) {
        mainPanel = new TreePanel<>(root);
        add(mainPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        var root = newMultiformInstance(40, 80, 40, 80);
        var demoFrame = new DemoFrame(root);

        demoFrame.setTitle("Walker Algorithm Demo");
        demoFrame.setSize(1280, 1024);
        demoFrame.setResizable(false);
        demoFrame.setLocationByPlatform(true);
        demoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        demoFrame.setVisible(true);
    }
}

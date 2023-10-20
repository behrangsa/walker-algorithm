package org.behrang.algorithm.tree.demo;

import javax.swing.*;
import java.awt.*;

public class DemoFrame extends JFrame {

    private JPanel mainPanel;

    public DemoFrame() {
        mainPanel = new TreePanel<>(SampleTree.newUniformInstance(64, 64));
        add(mainPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        DemoFrame demoFrame = new DemoFrame();
        demoFrame.setTitle("Walker Algorithm Demo");
        demoFrame.setSize(1024, 768);
        demoFrame.setResizable(false);
        demoFrame.setLocationByPlatform(true);
        demoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        demoFrame.setVisible(true);
    }
}

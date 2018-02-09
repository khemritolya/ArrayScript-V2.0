package org.lnar.ui;

import javax.swing.*;

public class OutputPane extends JFrame {

    private JTextArea textArea;

    public OutputPane(String name) {
        super("Output for " + name);

        setIconImage(new ImageIcon("res\\ICO.png").getImage());

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        JScrollPane scroll = new JScrollPane (textArea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scroll);

        setSize(400, 500);
        setResizable(false);
        setVisible(true);
    }

    public void write(String s) {
        textArea.append(s);
    }
}

package org.lnar.ui;

import org.lnar.lang.Interpreter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class UI extends JFrame {
    private JButton open;
    private JButton run;
    private JTextArea field;

    private File selected;
    private UI ui;

    public UI() {
        super("ArrayScript Editor");
        ui = this;

        init();
        setVisible(true);
    }

    private void init() {
        setSize(400, 500);
        setResizable(false);
        setIconImage(new ImageIcon("res\\ICO.png").getImage());

        setLayout(new BorderLayout());

        field = new JTextArea();
        field.setEditable(true);
        field.setLineWrap(true);
        JScrollPane scroll = new JScrollPane(field,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scroll);

        open = new JButton("Open File");
        open.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(selected == null ?
                        System.getProperty("user.home") : selected.getPath()));
                int result = fileChooser.showOpenDialog(ui);
                if (result == JFileChooser.APPROVE_OPTION) {
                    selected = fileChooser.getSelectedFile();
                    field.setText(read(selected));
                    ui.setTitle("ArrayScript Editor - " + selected.getName());
                }
            }
        });
        add(open, BorderLayout.BEFORE_FIRST_LINE);

        run = new JButton("Save & Run File");
        run.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                write(field.getText(), selected);
                try {
                    OutputPane p =  new OutputPane(selected.getName());
                    Interpreter.interpret(selected, p);
                } catch (IOException io) {
                    io.printStackTrace();
                }
            }
        });
        add(run, BorderLayout.PAGE_END);
    }

    private void write(String s, File f) {
        try {
            PrintWriter pw = new PrintWriter(new PrintStream(f));
            pw.write(s);
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String read(File f) {
        try {
            Scanner s = new Scanner(f);
            StringBuilder sb = new StringBuilder();

            while (s.hasNext()) {
                sb.append(s.nextLine()).append("\n");
            }

            s.close();

            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "COULD NOT READ FILE!!!";
        }
    }
}

package org.lnar;

import org.lnar.ui.UI;

import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        UI ui = new UI();
        ui.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}

package tp03;

import javax.swing.SwingUtilities;

import tp03.gui.GraphicTester;

public class Calc {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GraphicTester().display());
    }
}

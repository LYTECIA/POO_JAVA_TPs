package tp06;

import javax.swing.SwingUtilities;

import tp06.gui.GraphicTester;

public class Tester {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GraphicTester().display());
    }
}

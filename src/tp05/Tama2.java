package tp05;

import javax.swing.SwingUtilities;

import tp05.gui.TamaTester;

public class Tama2 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TamaTester().display());
    }
}

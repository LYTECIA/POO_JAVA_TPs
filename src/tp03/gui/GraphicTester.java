package tp03.gui;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import tp03.view.GraphicCalculator;
import tp03.view.ObservableCalculator;

public class GraphicTester {
    
    // ATTRIBUTS DE CLASSE
    
    private static final String NL = System.getProperty("line.separator");
    
    // ATTRIBUTS D'INSTANCE
    
    private final JFrame frame;
    private final GraphicCalculator calc;
    private final JList<Formula> memory;
    
    // CONSTRUCTEURS
    
    public GraphicTester() {
        // VUE
        frame = new JFrame("Calculette");
        calc = new GraphicCalculator();
        memory = new JList<>(new DefaultListModel<>());
        
        placeComponents();
        
        // CONTRÔLEUR
        connectControllers();
    }
    
    // COMMANDES
    
    public void display() {
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    // OUTILS
    
    private void placeComponents() {
        frame.add(calc, BorderLayout.NORTH);
        frame.add(new JScrollPane(memory), BorderLayout.CENTER);
    }
    
    private void connectControllers() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        calc.getModel().addChangeListener(e -> observeModel());
        
        memory.addListSelectionListener(e -> selectMem());
    }
    
    private void observeModel() {
        ObservableCalculator model = calc.getModel();
        if (model.hasFailed()) {
            error(calc.getRawFormula(), model.errorCause());
        } else {
            String exp = model.normalizedFormula();
            int val = model.computedValue();
            Formula f = new Formula(exp, val);
            ((DefaultListModel<Formula>) memory.getModel()).addElement(f);
        }
        calc.requestFocusInWindow();
    }
    
    private void error(String formula, String errorCause) {
        JOptionPane.showMessageDialog(frame,
            errorCause + ", l'expression" + NL
                + formula + NL
                + "n'est pas correcte",
            "Erreur",
            JOptionPane.ERROR_MESSAGE);
    }

    private void selectMem() {
        Formula f = memory.getSelectedValue();
        if (f != null) {
            calc.setRawFormula(f.exp());
        }
        calc.requestFocusInWindow();
    }
    
    private record Formula(String exp, int val) {
        @Override
        public String toString() {
            return exp + " = " + val;
        }
    }
}

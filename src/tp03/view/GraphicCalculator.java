package tp03.view;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextField;

import tp03.util.GBC;

public class GraphicCalculator extends JComponent {
    
    // ATTRIBUTS DE CLASSE
    
    private static final String[] DIGITS = new String[] {
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"
    };

    private static final String[] OPS = new String[] {
            "+", "-", "×", "/", "%"
    };
    
    private static final int SPACE = 0;
    private static final int DEL = 1;
    private static final int CLEAR = 2;
    private static final int EVAL = 3;
    private static final String[] CMDS = new String[] {
            "\u2423", // 2423 : open box
            "\u21B6", // 21B6 : anticlockwise top semicircle arrow
            "C",
            "="
    };
    
    // ATTRIBUTS D'INSTANCE
    
    private final ObservableCalculator model;
    private final JButton[] digits;
    private final JButton[] ops;
    private final JButton[] cmds;
    private final JTextField formula;
    
    // CONSTRUCTEURS
    
    public GraphicCalculator() {
        model = new ObservableCalculator();
        
        digits = createButtons(DIGITS);
        ops = createButtons(OPS);
        cmds = createButtons(CMDS);
        
        formula = new JTextField(15);
        formula.setCaretColor(Color.RED);
                
        placeComponents();
        
        connectControllers();
    }

    // REQUETES
    

    public ObservableCalculator getModel() {
        return model;
    }
    
    public String getRawFormula() {
        return formula.getText();
    }
    
    // COMMANDES
    
    @Override
    public boolean requestFocusInWindow() {
        return formula.requestFocusInWindow();
    }
    
    public void setRawFormula(String text) {
        formula.setText(text);
    }
    
    // OUTILS
    
    private JButton[] createButtons(String[] names) {
        JButton[] result = new JButton[names.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = new JButton(names[i]);
        }
        return result;
    }
    
    private void placeComponents() {
        // formula
        GBC f = new GBC(0, 0, 4, 1).fill(GBC.BOTH);
        // digits
        GBC[] d = new GBC[] {
                new GBC(1, 4).fill(GBC.BOTH),
                new GBC(0, 1).fill(GBC.BOTH),
                new GBC(1, 1).fill(GBC.BOTH),
                new GBC(2, 1).fill(GBC.BOTH),
                new GBC(0, 2).fill(GBC.BOTH),
                new GBC(1, 2).fill(GBC.BOTH),
                new GBC(2, 2).fill(GBC.BOTH),
                new GBC(0, 3).fill(GBC.BOTH),
                new GBC(1, 3).fill(GBC.BOTH),
                new GBC(2, 3).fill(GBC.BOTH),
        };
        // ops
        GBC[] o = new GBC[] {
                new GBC(3, 1).fill(GBC.BOTH),
                new GBC(3, 2).fill(GBC.BOTH),
                new GBC(3, 3).fill(GBC.BOTH),
                new GBC(3, 4).fill(GBC.BOTH),
                new GBC(2, 4).fill(GBC.BOTH),
        };
        // cmds
        GBC[] c = new GBC[] {
                new GBC(0, 4).fill(GBC.BOTH),
                new GBC(3, 5).fill(GBC.BOTH),
                new GBC(0, 5).fill(GBC.BOTH),
                new GBC(1, 5, 2, 1).fill(GBC.BOTH),
        };
        setLayout(new GridBagLayout());
        { //--
            add(formula, f);
            for (int i = 0; i < d.length; i++) {
                add(digits[i], d[i]);
            }
            for (int i = 0; i < o.length; i++) {
                add(ops[i], o[i]);
            }
            for (int i = 0; i < c.length; i++) {
                add(cmds[i], c[i]);
            }
        } //--
    }
    
    private void connectControllers() {
        model.addChangeListener(e -> observeModel());
        
        for (JButton b : digits) {
            b.addActionListener(this::writeToken);
        }
        
        for (JButton b : ops) {
            b.addActionListener(this::writeToken);
        }
        
        cmds[SPACE].addActionListener(
            (e) -> setFormulaText(formula.getText() + " "));
        
        cmds[DEL].addActionListener(e -> writeBackspace());
        
        cmds[CLEAR].addActionListener((e) -> setFormulaText(""));
        
        cmds[EVAL].addActionListener((e) -> model.compute(formula.getText()));
        
        formula.addActionListener((e) -> model.compute(formula.getText()));
        
        formula.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char key = e.getKeyChar();
                if (!accept(key)) {
                    e.consume();
                }
            }
            
            private boolean accept(char k) {
                return '0' <= k &&  k <= '9'
                        || k == ' '
                        || k == '\b'
                        || "+-*×/÷%".indexOf(k) >= 0;
            }
        });
    }
    
    private void observeModel() {
        if (!model.hasFailed()) {
            int v = model.computedValue();
            setFormulaText(String.valueOf(v));
            formula.selectAll();
        }
    }
    
    private void setFormulaText(String text) {
        formula.setText(text);
        requestFocusInWindow();
    }
    
    private void writeToken(ActionEvent e) {
        JButton b = (JButton) e.getSource();
        setFormulaText(formula.getText() + b.getText());
    }
    
    private void writeBackspace() {
        String text = formula.getText();
        if (!text.isEmpty()) {
            setFormulaText(text.substring(0, text.length() - 1));
        }
    }
}

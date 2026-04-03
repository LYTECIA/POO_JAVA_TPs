package tp05.gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import tp05.view.GraphicTama;
import tp05.view.ObservableTamagotchi;

public class TamaTester {

    // ATTRIBUTS STATIQUES

    private static final int DELAY = 1000;
    private static final int MAX_MEAL = 3;
    private static final int INIT_NRJ = 5;
    private static final int INIT_MAX_AGE = 20;
    private static final int INIT_STOMACH_SIZE = 20;
    private static final String TITLE =
            "The Return of The Tamagotchi "
            + "(Tamagotchi always strikes two times)";

    // ATTRIBUTS D'INSTANCE

    private final ObservableTamagotchi model;
    private final GraphicTama tama;
    private final JButton feed;
    private final JButton oneStep;
    private final JCheckBox stepByStep;
    private final Timer timer;
    private final JFrame frame;
    private final JLabel description;

    // CONSTRUCTEURS

    public TamaTester() {
        // MODELE
        model = new ObservableTamagotchi(
                INIT_NRJ, INIT_MAX_AGE, INIT_STOMACH_SIZE);
        
        // VUE
        frame = new JFrame(TITLE);
        tama = new GraphicTama(model);
        feed = new JButton("Feed");
        oneStep = new JButton("One Step");
        stepByStep = new JCheckBox("Step by step", true);
        description = new JLabel("");

        placeComponents();
        
        // CONTRÔLEUR
        connectControllers();
        timer = createTimer();
    }

    // COMMANDES

    public void display() {
        description.setText(model.describe());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // OUTILS

    private void placeComponents() {
        JPanel p = new JPanel();
        { //--
            p.add(stepByStep);
            p.add(oneStep);
            p.add(feed);
            p.add(new JLabel("          Image(s) by suta-raito.com"));
        } //--
        frame.add(p, BorderLayout.NORTH);
        frame.add(tama, BorderLayout.CENTER);
        frame.add(description, BorderLayout.SOUTH);
    }

    private void connectControllers() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        model.addChangeListener((e) -> observeModel());
        
        feed.addActionListener((e) -> model.eat(randomMiam()));
        
        oneStep.addActionListener((e) -> model.evolve());
        
        stepByStep.addActionListener((e) -> switchStepByStep());
    }
    
    private void observeModel() {
        description.setText(model.describe());
        feed.setEnabled(model.isAlive() && model.stomachIsWorking());
        if (!model.isAlive()) {
            stepByStep.setEnabled(false);
            oneStep.setEnabled(false);
            SwingUtilities.invokeLater(this::displayResult);
        }
    }

    private void displayResult() {
        int msgType;
        String title;
        String msg;
        if (model.isVenerable() && model.stomachIsWorking()) {
            msgType = JOptionPane.INFORMATION_MESSAGE;
            title = "Vous avez gagné :-)";
            msg = "Félicitations !!!\nVotre tamagotchi est mort "
                    + "vieux et en bonne santé.";
        } else {
            msgType = JOptionPane.ERROR_MESSAGE;
            title = "Vous avez perdu :'(";
            msg = "Bouuuh !!!\nVotre tamagotchi est mort ";
            if (model.isVenerable()) {
                msg += "vieux mais avec des problèmes de santé !";
            } else {
                if (model.stomachIsWorking()) {
                    msg += "en bonne santé mais trop jeune";
                } else {
                    msg += "trop jeune et avec des problèmes d'estomac";
                }
            }
        }
        JOptionPane.showMessageDialog(frame, msg, title, msgType);
    }
    
    private int randomMiam() {
        return (int) (Math.random() * MAX_MEAL) + 1;
    }
    
    private void switchStepByStep() {
        boolean manual = stepByStep.isSelected();
        oneStep.setEnabled(manual);
        if (manual) {
            timer.stop();
        } else {
            timer.restart();
        }
    }

    private Timer createTimer() {
        return new Timer(DELAY,
            (e) -> {
                if (model.isAlive()) {
                    model.evolve();
                } else {
                    timer.stop();
                }
            });
    }
}

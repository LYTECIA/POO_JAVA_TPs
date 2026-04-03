package tp03.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import tp03.model.Calculator;
import tp03.model.ICalculator;
import tp03.model.SyntaxException;

/**
 * Les modèles générateurs de ChangeEvents.
 * 
 * Rq : cette classe n'existe que pour des raisons pédagogiques liées au sujet
 *  de TP. Dans une "vraie" application, cette classe ne devrait pas exister,
 *  son code serait plutôt directement intégré à la classe Calculator.
 */
public class ObservableCalculator {

    private final List<ChangeListener> changeListeners;
    private final ChangeEvent event;
    
    private final ICalculator calc;
    
    // donne la dernière valeur calculée
    private int lastComputedValue;
    // donne la dernière formule évaluée, mise sous forme normalisée
    private String lastNormalizedFormula;
    // donne la raison d'un échec
    private String errorCause;

    // CONSTRUCTEURS
    
    public ObservableCalculator() {
        calc = new Calculator();
        lastNormalizedFormula = "";
        lastComputedValue = 0;
        errorCause = null;
        changeListeners = new ArrayList<ChangeListener>();
        event = new ChangeEvent(this);
    }
    
    // REQUÊTES
    
    public String errorCause() {
        if (!hasFailed()) {
            throw new AssertionError();
        }
        
        return errorCause;
    }
    
    public int computedValue() {
        if (hasFailed()) {
            throw new AssertionError();
        }
        
        return lastComputedValue;
    }
    
    public String normalizedFormula() {
        if (hasFailed()) {
            throw new AssertionError();
        }
        
        return lastNormalizedFormula;
    }
    
    public boolean hasFailed() {
        return errorCause != null;
    }
    
    // COMMANDES
    
    public void compute(String f) {
        if (f == null) {
            throw new AssertionError();
        }
        
        try {
            errorCause = null;
            lastComputedValue = calc.evaluation(f);
            lastNormalizedFormula = calc.getLastFormula();
        } catch (SyntaxException e) {
            errorCause = "Erreur de syntaxe";
        } catch (ArithmeticException e) {
            errorCause = "Erreur de calcul";
        }
        fireStateChanged();
    }
    
    public void addChangeListener(ChangeListener cl) {
        if (cl == null) {
            throw new AssertionError();
        }

        changeListeners.add(cl);
    }

    public void removeChangeListener(ChangeListener cl) {
        if (cl == null) {
            throw new AssertionError();
        }

        changeListeners.remove(cl);
    }
    
    // OUTILS
    
    private void fireStateChanged() {
        for (ChangeListener lst: changeListeners) {
            lst.stateChanged(event);
        }
    }
}

package tp05.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import tp05.model.ITamagotchi;
import tp05.model.Tamagotchi;
import util.Contract;

/**
 * Les tamagotchis générateurs d'événements.
 * 
 * Rq : cette classe n'existe que pour des raisons pédagogiques liées au sujet
 *  de TP. Dans une "vraie" application, cette classe ne devrait pas exister,
 *  son code serait plutôt directement intégré à la classe Tamagotchi.
 */
public class ObservableTamagotchi {

    // ATTRIBUTS

    private final ITamagotchi tama;
    private final List<ChangeListener> listeners;
    private final ChangeEvent event;

    // CONSTRUCTEURS

    public ObservableTamagotchi(int energyInit, int maxLife, int stomachSize) {
        Contract.checkCondition(energyInit >= 0);
        Contract.checkCondition(maxLife > 0);
        Contract.checkCondition(stomachSize > 0);

        tama = new Tamagotchi(energyInit, maxLife, stomachSize);
        event = new ChangeEvent(this);
        listeners = new ArrayList<>();
    }

    // REQUETES

    public String describe() {
        return tama.describe();
    }

    public boolean isAlive() {
        return tama.isAlive();
    }

    public boolean isStarved() {
        return tama.isStarved();
    }

    public boolean isVenerable() {
        return tama.isVenerable();
    }

    public boolean stomachHasExploded() {
        return tama.stomachHasExploded();
    }

    public boolean stomachIsWorking() {
        return tama.stomachIsWorking();
    }

    // COMMANDES

    public void addChangeListener(ChangeListener lst) {
        Contract.checkCondition(lst != null);
        
        listeners.add(lst);
    }

    public void eat(int mealNb) {
        Contract.checkCondition(isAlive());
        Contract.checkCondition(stomachIsWorking());
        Contract.checkCondition(mealNb > 0);
        
        tama.eat(mealNb);
        fireStateChange();
    }

    public void evolve() {
        Contract.checkCondition(isAlive());

        tama.evolve();
        fireStateChange();
    }
    
    // OUTILS

    private void fireStateChange() {
        for (ChangeListener cl : listeners) {
            cl.stateChanged(event);
        }
    }
}

package tp06.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import tp06.model.Feature;
import tp06.model.Model;
import tp06.model.Property;
import util.Contract;

public class ObservableModel extends Model {
    
    // ATTRIBUTS
    
    private final List<ChangeListener> listeners;
    private final ChangeEvent event;
    
    // CONSTRUCTEURS
    
    public ObservableModel(List<Property> data) {
        listeners = new ArrayList<>();
        event = new ChangeEvent(this);
        data.forEach(super::addProperty);
    }
    
    // COMMANDES
    
    @Override
    public void addProperty(Property r) {
        super.addProperty(r);
        fireStateChanged();
    }
    
    public void addChangeListener(ChangeListener lnr) {
        Contract.checkCondition(lnr != null);
        
        listeners.add(lnr);
    }
    
    @Override
    public void resetFeature(Feature f) {
        super.resetFeature(f);
        fireStateChanged();
    }
    
    @Override
    public void resetAllFeatures() {
        super.resetAllFeatures();
        fireStateChanged();
    }
    
    @Override
    public void reverseFeature(Feature f) {
        super.reverseFeature(f);
        fireStateChanged();
    }
    
    @Override
    public void sortBy(List<Feature> features) {
        super.sortBy(features);
        fireStateChanged();
    }
    
    @Override
    public void addSelectedFeature(Feature f) {
        super.addSelectedFeature(f);
        fireStateChanged();
    }
    
    @Override
    public void clearSelectedFeatures() {
        super.clearSelectedFeatures();
        fireStateChanged();
    }

    
    // OUTILS
    
    private void fireStateChanged() {
        for (ChangeListener cl : listeners) {
            cl.stateChanged(event);
        }
    }
}

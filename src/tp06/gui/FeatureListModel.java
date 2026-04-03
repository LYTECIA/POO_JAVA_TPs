package tp06.gui;

import javax.swing.AbstractListModel;

import tp06.model.Feature;
import util.Contract;

public class FeatureListModel extends AbstractListModel<Feature> {

    // ATTRIBUTS
    
    private final ObservableModel model;

    // CONSTRUCTEURS
    
    public FeatureListModel(ObservableModel m) {
        Contract.checkCondition(m != null);
        
        model = m;
        model.addChangeListener(
                (e) -> fireContentsChanged(this,  0, getSize() - 1));
    }
    
    // REQUETES
    
    @Override
    public Feature getElementAt(int index) {
        if (!isValidIndex(index)) {
            return null;
        }
        return model.getFeature(index);
    }
    
    @Override
    public int getSize() {
        return model.featureCount();
    }
    
    public boolean isValidIndex(int index) {
        return 0 <= index && index < getSize();
    }
}

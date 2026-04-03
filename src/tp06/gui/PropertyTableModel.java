package tp06.gui;

import java.util.function.Function;

import javax.swing.table.AbstractTableModel;

import tp06.model.Feature;
import tp06.model.Property;
import util.Contract;

public class PropertyTableModel extends AbstractTableModel {
    
    // ATTRIBUTS
    
    private final ObservableModel model;

    // CONSTRUCTEURS
    
    public PropertyTableModel(ObservableModel m) {
        Contract.checkCondition(m != null);
        
        model = m;
        model.addChangeListener((e) -> fireTableDataChanged());
    }

    // REQUETES
    
    @Override
    public Class<?> getColumnClass(int i) {
        if (!isValidCol(i)) {
            return Object.class;
        }
        return model.getFeature(i).type();
    }
    
    @Override
    public int getColumnCount() {
        return model.featureCount();
    }

    @Override
    public String getColumnName(int i) {
        if (!isValidCol(i)) {
            return "";
        }
        return model.getFeature(i).toString();
    }
    
    @Override
    public int getRowCount() {
        return model.propertyCount();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (!isValidRow(rowIndex) || !isValidCol(columnIndex)) {
            return null;
        }
        Property p = model.getProperty(rowIndex);
        Feature f = model.getFeature(columnIndex);
        Function<Property, ?> extractor = f.keyExtractor();
        return extractor.apply(p);
    }
    
    public boolean isValidCol(int c) {
        return 0 <= c && c < getColumnCount();
    }
    
    public boolean isValidRow(int r) {
        return 0 <= r && r < getRowCount();
    }
}

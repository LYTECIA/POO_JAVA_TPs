package tp06.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import tp06.model.Feature;
import tp06.model.Property;
import tp06.util.PropertyGenerator;

/**
 * Il est possible de trier les biens selon plusieurs critères (feature) :
 *  adresse, surface habitable, nombre de pièces, prix et prix au mètre carré.
 * Cliquer sur l'entête de la colonne trie les données selon le critère
 *  considéré.
 * 
 * Il est possible aussi de modifier les critères (croissant ou décroissant).
 * Pour cela, on édite les critères en les simple cliquant (l'un après l'autre)
 *  dans la liste graphique.
 * Cliquer sur "Inverser le critère" change le sens de croissance du critère
 *  sélectionné.
 * Un critère croissant est affiché avec un "+", décroissant avec un "-".
 * Cliquer sur "Réinitialiser tous" rend tous les critères croissant.
 * 
 * En double cliquant un critère dans la liste graphique, on l'ajoute à une
 *  liste interne de critères.
 * Le contenu de cette liste est affiché dans le champ de texte sous le titre
 *  "Composition des critères".
 * Cliquer sur "Vider" vide cette liste.
 * Cliquer sur "Appliquer" applique les critères de cette liste, par composition
 *  du premier jusqu'au dernier.
 */
public class GraphicTester {

    // ATTRIBUTS DE CLASSE
    
    private static final int MAX_LINES_NB = 10;
    
    // ATTRIBUTS
    
    private final ObservableModel model;
    
    private final JFrame frame;
    private final JTable allProperties;
    private final JList<Feature> allFeatures;
    private final JTextField composition;
    private final JButton reverse;
    private final JButton reinit;
    private final JButton empty;
    private final JButton apply;

    // CONSTRUCTEURS
    
    public GraphicTester() {
        // MODELE
        model = createObservableModel();
        
        // VUE
        frame = new JFrame("Gestion Immobilière");
        allProperties = createTable(model);
        allFeatures = createList(model);
        composition = createField();
        reverse = new JButton("Inverser le critère");
        reinit = new JButton("Réinitialiser tous");
        empty = new JButton("Vider");
        apply = new JButton("Appliquer");
        
        placeComponents();
        
        // CONTROLEUR
        connectControllers();
    }

    // COMMANDES
    
    public void display() {
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // OUTILS
    
    private ObservableModel createObservableModel() {
        List<Property> properties = PropertyGenerator.generateProperties();
        return new ObservableModel(properties);
    }
    
    private JTable createTable(ObservableModel m) {
        PropertyTableModel ptm = new PropertyTableModel(m);
        return new JTable(ptm);
    }
    
    private JList<Feature> createList(ObservableModel m) {
        FeatureListModel flm = new FeatureListModel(m);
        JList<Feature> list = new JList<>(flm);
        list.setVisibleRowCount(Math.min(MAX_LINES_NB, flm.getSize()));
        list.setCellRenderer(new Renderer());
        return list;
    }
    
    private JTextField createField() {
        JTextField text = new JTextField(30);
        text.setEditable(false);
        return text;
    }
    
    private JPanel createTitledPanel(String title) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createTitledBorder(title));
        return p;
    }
    
    private void placeComponents() {
        JPanel p = createTitledPanel("Visualisation des biens");
        { //--
            p.add(new JScrollPane(allProperties));
        } //--
        frame.add(p);
        p = createTitledPanel("Critères de tri");
        { //--
            JPanel q = new JPanel(new BorderLayout());
            { //--
                q.add(new JScrollPane(allFeatures));
                JPanel r = new JPanel();
                { //--
                    r.add(reverse);
                    r.add(reinit);
                } //--
                q.add(r, BorderLayout.SOUTH);
            } //--
            p.add(q, BorderLayout.NORTH);
            q = createTitledPanel("Sélection d'une conjonction de critères");
            { //--
                q.add(composition, BorderLayout.NORTH);
                JPanel r = new JPanel();
                { //--
                    r.add(empty);
                    r.add(apply);
                } //--
                q.add(r, BorderLayout.SOUTH);
            } //--
            p.add(q, BorderLayout.SOUTH);
        } //--
        frame.add(p, BorderLayout.SOUTH);
    }
    
    private void connectControllers() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        model.addChangeListener(
                (e) -> composition.setText(joinedSelectedFeatures()));
        
        JTableHeader jth = allProperties.getTableHeader();
        jth.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int viewIndex = jth.columnAtPoint(e.getPoint());
                TableColumnModel tmc = allProperties.getColumnModel();
                int modelIndex = tmc.getColumn(viewIndex).getModelIndex();
                Feature f = model.getFeature(modelIndex);
                model.sortBy(List.of(f));
            }
        });
        
        allFeatures.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Feature f = allFeatures.getSelectedValue();
                if (f != null) {
                    int clickNb = e.getClickCount();
                    if (clickNb == 2) {
                        model.addSelectedFeature(f);
                    }
                }
            }
        });
        
        reverse.addActionListener((e) -> reverseSelectedFeature());
        
        reinit.addActionListener((e) -> model.resetAllFeatures());
        
        empty.addActionListener((e) -> model.clearSelectedFeatures());
        
        apply.addActionListener(
            (e) -> model.sortBy(model.getSelectedFeatures())); 
    }
    
    private void reverseSelectedFeature() {
        Feature f = allFeatures.getSelectedValue();
        if (f != null) {
            model.reverseFeature(f);
        }
    }
    
    private String joinedSelectedFeatures() {
        return model.getSelectedFeatures().stream()
              .map(model::featureDescription)
              .collect(Collectors.joining(" & "));
    }
    
    // TYPES IMBRIQUES
    
    private class Renderer implements ListCellRenderer<Feature> {
        private DefaultListCellRenderer delegate;
        private Renderer() {
            delegate = new DefaultListCellRenderer();
        }
        @Override
        public Component getListCellRendererComponent(
                JList<? extends Feature> list, Feature value, int index,
                boolean isSelected, boolean cellHasFocus) {
            delegate.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);
            String text = model.featureDescription(value);
            delegate.setText(text);
            return delegate;
        }
    }
}

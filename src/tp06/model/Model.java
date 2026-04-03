package tp06.model;

import java.util.List;
import java.util.Set;

public class Model implements IModel {

	private static final List<Feature> FEATURES =List.of(Feature.values());
	//ATTRIBUTS
	private List<Feature> SelectedFeatures ;
	private List<Property> properties ;
	private Set<Feature>AscendedFeatures ;
	
	//CONSTRUCTEUR
	public Model() {
		properties.isEmpty();
		SelectedFeatures.isEmpty();
		
	}
	
	/**
     * Le nombre de biens immobiliers gérés par ce modèle. 
     */
    public int propertyCount() {
    	return properties.size();
    }
    
    /**
     * Indique si le modèle contient ou non une propriété.
     */
    public boolean containsProperty(Property p) {
    	return properties.contains(p);
    }
    
    /**
     * La liste des caractéristiques gérées par ce modèle.
     * Cette liste est non modifiable.
     */
    public List<Feature> getAllFeatures(){
    	return FEATURES;
    }
    /**
    un attributs statique : feature 
    private static final List<Feature> FEATURES =List.of(Feature.value());
    3 attributs : liste de carcateriqtiuqe selectionne 
    liste de fr
    ensemble qui conytient   les cratersitoique qui sont trie dans l'ordre croissant

    avoir une methode get veut pas dire forcement avoir des attributs 

}**/

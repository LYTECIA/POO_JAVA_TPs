package tp06.model;

import java.util.List;

/**
 * Un modèle pour une application (rudimentaire) de gestion de biens
 *  immobiliers.
 * Un bien immobilier (property) est identifié à un ensemble de critères
 *  (features).
 * Un modèle tient à jour une liste de biens immobiliers qui est réordonnée à
 *  la demande. Cette réorganisation se produit à chaque appel de la méthode
 *  sortBy. Cette dernière prend en paramètre une liste de caractéristiques
 *  c1, c2, ... et trie les biens d'abord selon c1, puis (pour les éléments
 *  égaux vis-à-vis de c1) selon c2, puis ....
 * Un modèle permet également de sélectionner certaines caractéristiques dans
 *  une liste, pour créer une séquence utilisable par l'opération sortBy.
 * Enfin, à chaque caractéristique le modèle associe un ordre qui sera utilisé
 *  lors du tri : croissant ou décroissant.
 *   
 * @inv <pre>
 *     forall f in Feature :
 *         featureDescription(f).equals(
 *             (isAscending(f) ? UP_ARROW : DOWN_ARROW) + f.label())
 *     featureCount() == |Feature.values()|
 *     getAllFeatures() contient les mêmes éléments que Feature.values()
 *     forall i, 0 <= i < featureCount() :
 *         getFeature(i) == getAllFeatures().get(i)
 *     getAllFeatures().containsAll(getSelectedFeatures())
 *     forall p in Property :
 *         containsProperty(p) ==
 *             (exist i, 0 <= i < propertyCount() : p.equals(getProperty(i)))
 *     forall i, 0 <= i < propertyCount() : getProperty(i) != null </pre>
 *     
 * @cons <pre>
 *     $DESC$ Création d'un modèle
 *     $ARGS$ -
 *     $POST$
 *         propertyCount() == 0
 *         getSelectedFeatures().isEmpty() </pre>
 */
public interface IModel {
    
    // CONSTANTES STATIQUES
    
    String UP_ARROW = "\u2191";
    String DOWN_ARROW = "\u2193";
    
    // REQUETES

    /**
     * Indique si le modèle contient ou non une propriété.
     */
    boolean containsProperty(Property p);
    
    /**
     * L'intitulé de la caractéristique f précédé du signe UP_ARROW ou
     *  DOWN_ARROW selon qu'elle est associée à l'ordre croissant ou
     *  décroissant.
     */
    String featureDescription(Feature f);

    /**
     * Le nombre de caractéristiques gérées par ce modèle. 
     */
    int featureCount();
    
    /**
     * La liste des caractéristiques gérées par ce modèle.
     * Cette liste est non modifiable.
     */
    List<Feature> getAllFeatures();
    
    /**
     * La ième caractéristique.
     * @pre <pre>
     *     0 <= i < featureCount() </pre>
     */
    Feature getFeature(int i);
    
    /**
     * Le ième bien immobilier dans l'ordre actuel.
     * @pre <pre>
     *     0 <= i < propertyCount() </pre>
     */
    Property getProperty(int i);

    /**
     * Indique si f est considérée comme croissante dans ce modèle. 
     * @pre
     *     f != null
     */
    boolean isAscending(Feature f);
    
    /**
     * Le nombre de biens immobiliers gérés par ce modèle. 
     */
    int propertyCount();

    /**
     * La liste des caractéristiques actuellement sélectionnées.
     */
    List<Feature> getSelectedFeatures();
    
    // COMMANDES

    /**
     * Ajoute un bien immobilier à ce modèle.
     * @pre <pre>
     *     p != null </pre>
     * @post <pre>
     *     old !containsProperty(p)
     *         ==> Let n ::= old propertyCount()
     *             propertyCount() == n + 1
     *             forall i, 0 <= i < n : getProperty(i) == old getProperty(i)
     *             getProperty(n) == p </pre>
     */
    void addProperty(Property p);
    
    /**
     * Associe l'ordre croissant à la caractéristique f.
     * @post <pre>
     *     isAscendingFeature(f) </pre>
     */
    void resetFeature(Feature f);
    
    /**
     * Associe l'ordre croissant à toutes les caractéristiques.
     * @post <pre>
     *     forall f in Feature : isAscendingFeature(f) </pre>
     */
    void resetAllFeatures();
    
    /**
     * Inverse l'ordre associé à la caractéristique f.
     * @post <pre>
     *     isAscendingFeature(f) == old !isAscendingFeature(f) </pre>
     */
    void reverseFeature(Feature f);

    /**
     * Trie la liste des biens immobiliers selon les caractéristiques de la
     *  liste features en respectant l'ordre qui leur est associé et leur rang
     *  dans la liste. 
     * @pre <pre>
     *     features != null </pre>
     * @post <pre
     *     Let <: ::= l'ordre (total au sens large) défini par features
     *     forall (i, j), 0 <= i < propertyCount(), 0 <= j < propertyCount() :
     *         i < j ==> getProperty(i) <: getProperty(j) </pre>
     */
    void sortBy(List<Feature> features);
    
    /**
     * Ajoute f à la liste des caractéristiques sélectionnées.
     * @post <pre>
     *     Let sf ::= getSelectedFeatures()
     *     old !sf.contains(f)
     *         ==> sf.size() == old sf.size() + 1
     *             forall i, 0 <= i < sf.size() - 1 : sf.get(i) == old sf.get(i)
     *             sf.get(sf.size() - 1) == f </pre>
     */
    void addSelectedFeature(Feature f);
    
    /**
     * Vide la liste des caractéristiques sélectionnées.
     * @post <pre>
     *     getSelectedFeatures().isEmpty() </pre>
     */
    void clearSelectedFeatures();
}

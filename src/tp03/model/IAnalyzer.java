package tp03.model;

/**
 * Spécifie un analyseur lexical dont l'activité consiste à produire une
 *  séquence d'unités lexicales (Token), à partir d'une séquence de
 *  caractères.
 * La séquence de caractères est fournie à l'analyseur via la méthode
 *  setInput.
 * L'analyseur produit les tokens un par un, à chaque appel de la méthode
 *  toNext().
 * La méthode current() permet d'obtenir le dernier Token produit.
 * Au fil du processus, l'analyseur peut "nettoyer" la séquence de caractères
 *  initiale pour en offrir une forme normalisée (exactement un espace entre
 *  deux tokens) avec la méthode getNormalizedInput().
 *
 * @inv
 *     getNormalizedInput() != null
 *     current() != null <==> l'analyseur a démarré
 *     !hasNext() <==> l'analyseur a terminé
 */
interface IAnalyzer {

    // REQUETES
    
    /**
     * Le dernier token produit par l'analyseur.
     * Retourne null tant que l'analyseur n'a pas démarré.
     */
    Token current();
    
    /**
     * Version normalisée de la partie déjà parcourue de la formule à analyser.
     * Retourne donc la chaîne vide tant que l'analyseur n'a pas démarré.
     */
    String getNormalizedInput();
    
    /**
     * Indique s'il reste des tokens à produire à partir de la formule à
     *  analyser.
     */
    boolean hasNext();
    
    /**
     * Indique si s est bien constituée uniquement de chiffres, espaces et
     *  opérateurs.
     */
    boolean isValidInput(String s);
    
    // COMMANDES
    
    /**
     * Fixe la formule à analyser.
     * @pre
     *     s != null
     *     isValidInput(s)
     * @post
     *     getNormalizedInput().isEmpty()
     *     current() == null
     */
    void setInput(String s);
    
    /**
     * Produit le prochain token en poursuivant la lecture de la formule à
     *  analyser.
     * @pre
     *     hasNext()
     * @post
     *     current() a changé pour le token qui suit old current()
     *     getNormalizedInput().equals(
     *         old getNormalizedInput() + " " + current())
     */
    void toNext();
}

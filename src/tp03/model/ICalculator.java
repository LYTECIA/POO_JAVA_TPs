package tp03.model;

/**
 * Spécifie un calculateur pour des expressions arithmétiques simples (portant
 *  sur des entiers uniquement) écrites de manière postfixe (les opérateurs sont
 *  fournis après les opérandes sur lesquels ils agissent).
 *  
 * @inv
 *     getLastFormula() != null
 */
public interface ICalculator {
    
    // REQUETES
    
    /**
     * @pre
     *     formula != null
     *     formula est une formule contenant uniquement des entiers, des
     *         opérateurs et des espaces
     * @post
     *     result est la valeur de formula
     *     getLastFormula() est la formule normalisée
     * @throws SyntaxException
     *     si formula n'est pas syntaxiquement correcte
     */
    int evaluation(String formula) throws SyntaxException;

    /**
     * La dernière formule évaluée par le calculateur, sous forme normalisée.
     * Initialement la chaîne vide.
     */
    String getLastFormula();
}

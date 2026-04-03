package tp03.model;

/**
 * @inv arity() >= 0
 */
interface Token {

    /**
     * Le nombre d'opérandes nécessaires pour évaluer ce token.
     */
    int arity();
    
    /**
     * La représentation textuelle de ce token.
     */
    @Override
    String toString();
    
    /**
     * La valeur entière de ce token sur les opérandes donnés.
     * 
     * @pre |operands| == arity()
     */
    int value(int... operands);
}

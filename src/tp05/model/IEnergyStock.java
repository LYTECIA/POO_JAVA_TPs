package tp05.model;

/**
 * Modélise un stock d'énergie pour les tamagotchis.
 * Un réservoir d'énergie permet de simuler le stockage de l'énergie au sein du
 *  corps d'un tamagotchi.
 * On peut toujours rajouter de l'énergie à un tel réservoir et on peut en
 *  consommer tant qu'il lui en reste.
 * @inv <pre>
 *     describe() != null
 *     crtLevel() >= 0
 *     isEmpty() <==> (crtLevel() == 0) </pre>
 * @cons <pre>
 *     $DESC$ Un stock d'énergie de niveau courant nrj.
 *     $ARGS$ int nrj
 *     $PRE$
 *         nrj >= 0
 *     $POST$
 *         crtLevel() == nrj </pre>
 */
public interface IEnergyStock {

    // REQUETES

    /**
     * Le niveau courant de ce stock d'énergie.
     */
    int crtLevel();

    /**
     * Décrit ce stock d'énergie à l'aide d'une courte chaîne de caractères, du
     *  genre "nrjLevel: xxx".
     */
    String describe();

    /**
     * Indique si ce stock d'énergie est vide.
     */
    boolean isEmpty();

    // COMMANDES

    /**
     * Consomme de l'énergie de ce stock d'énergie.
     * @pre <pre>
     *     0 <= qty <= crtLevel() </pre>
     * @post <pre>
     *     crtLevel() == old crtLevel() - qty </pre>
     */
    void consume(int qty);

    /**
     * Augmente le niveau courant de ce stock d'énergie.
     * @pre <pre>
     *     qty >= 0 </pre>
     * @post <pre>
     *     crtLevel() == old crtLevel() + qty </pre>
     */
    void raiseLevel(int qty);
}

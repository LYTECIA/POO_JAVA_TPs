package tp04;

/**
 * Modélise un dé à jouer.
 *
 * @inv <pre>
 *     nbOfSides() >= MIN_SIDES_NB
 *     nbOfSides() >= value() >= 1 </pre>
 * @cons <pre>
 *     $DESC$ Création d'un dé d'au moins MIN_SIDES_NB faces.
 *     $ARGS$ int n
 *     $PRE$  n >= MIN_SIDES_NB
 *     $POST$
 *         !isTaken()
 *         value() == 1
 *         nbOfSides() == n </pre>
 */
public interface IDice {

    // ATTRIBUTS STATIQUES

    int MIN_SIDES_NB = 2;

    // REQUETES

    /**
     * Le nombre de faces du dé.
     */
    int nbOfSides();

    /**
     * Indique si le dé est pris par le lanceur.
     */
    boolean isTaken();

    /**
     * La valeur consultable portée par la face supérieure du dé.
     * @pre <pre>
     *     !isTaken() </pre>
     */
    int value();

    // COMMANDES

    /**
     * Le dé roule suite au lancer effectué par son lanceur.
     * @pre <pre>
     *     isTaken() </pre>
     * @post <pre>
     *     !isTaken()
     *     nbOfSides() == old nbOfSides()
     *     value() a changé de valeur aléatoirement </pre>
     */
    void roll();

    /**
     * Le dé est pris par son lanceur.
     * @pre <pre>
     *     !isTaken() </pre>
     * @post <pre>
     *     nbOfSides() == old nbOfSides()
     *     isTaken() </pre>
     */
    void take();
}

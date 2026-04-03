package tp04;

/**
 * Modélise un joueur.
 *
 * @inv <pre>
 *     maxGain() >= lastGain() >= minGain() >= 0
 *     score() >= 0 </pre>
 *     
 * @cons <pre>
 *     $DESC$ Création d'un joueur muni de dés dont les nombres de faces sont
 *            décrits par sidesNumbers.
 *     $ARGS$ int[] sidesNumbers
 *     $PRE$
 *         sidesNumbers != null && sidesNumbers.length > 0
 *         forall f in sidesNumbers : f >= IDice.MIN_SIDES_NB
 *     $POST$
 *         !hasEverPlayed()
 *         minGain() == sidesNumbers.length
 *         maxGain() == sum(f in sidesNumbers ; f)
 *         score() == 0 </pre>
 */
public interface IPlayer {

    // REQUETES

    /**
     * Indique si le joueur a déjà joué ou non.
     */
    boolean hasEverPlayed();

    /**
     * Valeur du gain obtenu par le joueur lors du dernier tour de jeu.
     * @pre <pre>
     *     hasEverPlayed() </pre>
     */
    int lastGain();

    /**
     * Valeur maximale du gain du joueur.
     */
    int maxGain();

    /**
     * Valeur minimale du gain du joueur.
     */
    int minGain();

    /**
     * Score courant du joueur.
     */
    int score();

    // COMMANDES

    /**
     * Le joueur ajoute la valeur de son dernier gain à son score.
     * @pre <pre>
     *     hasEverPlayed() </pre>
     * @post <pre>
     *     hasEverPlayed()
     *     score() == old score() + lastGain()
     *     lastGain() == old lastGain()
     *     maxGain() == old maxGain()
     *     minGain() == old minGain() </pre>
     */
    void addToScore();

    /**
     * Le joueur joue un tour de jeu, ce qui modifie son dernier gain.
     * @post <pre>
     *     hasEverPlayed()
     *     score() == old score()
     *     lastGain() a changé de valeur
     *     maxGain() == old maxGain()
     *     minGain() == old minGain() </pre>
     */
    void playOnce();

    /**
     * Le joueur remet à zéro le total de ses points.
     * @post <pre>
     *     !hasEverPlayed()
     *     score() == 0
     *     maxGain() == old maxGain()
     *     minGain() == old minGain() </pre>
     */
    void resetScore();
}

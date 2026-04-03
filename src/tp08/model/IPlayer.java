package tp08.model;

/**
 * Modélise un joueur.
 * Un joueur possède :
 * - des points de vie (hit points), qui représentent aussi sa force ;
 * - des points de mana (power level) ;
 * - un ressenti (mood) ;
 * Il peut :
 * - gagner des points de vie ;
 * - perdre ou gagner des points de mana ; 
 * - entrer dans une salle ;
 * - mourir ;
 * - quitter le jeu.
 * 
 * Quand il entre dans une salle, celle-ci affecte ses caractéristiques :
 * - une salle magique lui fait gagner du mana ;
 * - une salle maudite lui fait perdre du mana ou le tue, s'il n'a pas de mana ;
 * - toutes les autres salles font baisser son mana, s'il en a ;
 * - une salle remplie de monstres le tue s'il n'a pas assez de points de vie ou
 *  lui en fait gagner.
 *  
 * Il modifie aussi l'état de certaines salle dans lesquelles il pénètre :
 * - s'il pourfend les monstres d'une salle, celle-ci n'en contient plus ;
 *  
 * Enfin, chaque salle modifie son ressenti. Il s'agit, en fait, d'une
 *  description littérale du vécu du personnage, permettant une immersion totale
 *  du joueur IRL qui peut s'identifier à son héros avec un maximum de réalisme.
 * 
 * @inv <pre>
 *     getRoom() != null
 *     getMood() != null
 *     getHitPoints() >= 0
 *     isDead() <==> (getHitPoints() == 0)
 *     isDead() ==> hasLeft()
 *     getPowerLevel() >= 0 </pre>
 *     
 * @cons <pre>
 *     $DESC$ Un joueur qui n'a pas quitté la partie.
 *     $ARGS$ IRoom r, int hp
 *     $PRE$
 *         r != null
 *         hp > 0
 *     $POST$
 *         getRoom() == r
 *         getMood().isEmpty()
 *         getHitPoints() == hp
 *         getPowerLevel() == 0
 *         !hasLeft() </pre>
 */
public interface IPlayer {

    // REQUETES

    /**
     * Le nombre de points de vie de ce joueur.
     */
    int getHitPoints();

    /**
     * L'état d'esprit actuel du joueur (reflet imprécis de son état).
     */
    String getMood();
    
    /**
     * La quantité de mana du joueur.
     */
    int getPowerLevel();

    /**
     * La salle dans laquelle se trouve le joueur.
     */
    IRoom getRoom();

    /**
     * Indique si le joueur a arrêté la partie.
     */
    boolean hasLeft();

    /**
     * Indique si le joueur est mort.
     */
    boolean isDead();

    // COMMANDES

    /**
     * Tue le joueur.
     * @pre
     *     !hasLeft()
     * @post <pre>
     *     getRoom() == old getRoom()
     *     getMood() == old getMood()
     *     getPowerLevel() == old getPowerLevel()
     *     isDead() </pre>
     */
    void die();

    /**
     * Fait s'arrêter le joueur.
     * @pre <pre>
     *     !hasLeft() </pre>
     * @post <pre>
     *     getRoom() == old getRoom()
     *     getMood() == old getMood()
     *     getHitPoints() == old getHitPoints()
     *     getPowerLevel() == old getPowerLevel()
     *     hasLeft() </pre>
     */
    void leave();

    /**
     * Modifie les super-pouvoirs du joueur.
     * @pre <pre>
     *     !hasLeft() </pre>
     * @post <pre>
     *     getRoom() == old getRoom()
     *     getMood() == old getMood()
     *     getHitPoints() == old getHitPoints()
     *     getPowerLevel() == max(0, old getPowerLevel() + delta)
     *     !hasLeft() </pre>
     */
    void changePowerLevel(int delta);

    /**
     * Associe le joueur à la salle <code>r</code> après l'avoir retiré de la
     *  salle où il était auparavant.
     * @pre <pre>
     *     r != null
     *     getRoom() != r
     *     !hasLeft() </pre>
     * @post <pre>
     *     getRoom() == r
     *     getMood().equals(old r.describeEffect(this))
     *     le reste de l'état de this dépend de l'effet produit par son entrée
     *       dans r </pre>
     */
    void setRoom(IRoom r);

    /**
     * Augmente le nombre de points d'attaque de ce joueur.
     * @pre <pre>
     *     q >= 0
     *     !hasLeft() </pre>
     * @post <pre>
     *     getRoom() == old getRoom()
     *     getMood() == old getMood()
     *     getHitPoints() == old getHitPoints() + q
     *     getPowerLevel() == old getPowerLevel()
     *     !hasLeft() </pre>
     */
    void strengthen(int q);
}

package tp08.model.room;

import tp08.model.IPlayer;

/**
 * Modélise les salles qui contiennent des monstres qui attaqueront le joueur
 *  entrant.
 * 
 * @inv <pre>
 *     forall p in IPlayer :
 *         Let x ::= monstersNb()
 *             y ::= p.getHitPoints()
 *         0 < x <= y
 *             ==> describeEffect().contains(
 *                     "Vous combattez victorieusement !")
 *         x > y
 *             ==> describeEffect().contains(
 *                     "Vous succombez dans un râle affreux...")
 *     monstersNb() >= 0 </pre>
 */
public interface IMonsterRoom extends INormalRoom {

    // REQUETES

    @Override
    String describeEffect(IPlayer p);

    /**
     * Le nombre de monstres actuellement présents dans cette salle.
     */
    int monstersNb();

    // COMMANDES

    /**
     * @post <pre>
     *     Let x ::= old monstersNb()
     *         y ::= old p.getHitPoints()
     *     x == 0
     *         ==> p.getHitPoints() == old p.getHitPoints()
     *             !p.hasLeft()
     *     0 < x <= y
     *         ==> monstersNb() == 0
     *             p.getHitPoints() == y + x / 2
     *             !p.hasLeft()
     *     x > y
     *         ==> monstersNb() == x + y / 2
     *             p.isDead() </pre>
     */
    @Override
    void doEffect(IPlayer p);
}

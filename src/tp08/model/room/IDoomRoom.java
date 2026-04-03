package tp08.model.room;

import tp08.model.IPlayer;
import tp08.model.IRoom;

/**
 * Modélise les temples maudits qui tuent le joueur qui y entre s'il ne possède
 *  pas de mana.
 * 
 * @inv
 *     forall p in IPlayer :
 *         p.getPowerLevel() == 0
 *             ==> describeEffect(p).contains(
 *                     "Vous mourez dans d'atroces souffrances.")
 *         p.getPowerLevel() > 0
 *             ==> describeEffect(p).contains(
 *                     "Vous sentez que seul un super héros sortira d'ici.")
 */
public interface IDoomRoom extends IRoom {
    
    // REQUETE

    @Override
    String describeEffect(IPlayer p);

    // COMMANDES

    /**
     * @post <pre>
     *     p.getPowerLevel() == 0
     *     p.isDead() <==> (old p.getPowerLevel() == 0)
     *     !p.isDead()
     *         ==> p.getHitPoints() == old p.getHitPoints()
     *             !p.hasLeft() </pre>
     */
    @Override
    void doEffect(IPlayer p);
}

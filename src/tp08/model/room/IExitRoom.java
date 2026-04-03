package tp08.model.room;

import tp08.model.IPlayer;

/**
 * Modélise les salles qui permettent au joueur de sortir du labyrinthe.
 * 
 * @inv
 *     forall p in IPlayer :
 *         describeEffect(p).contains(
 *             "Vous sortez du labyrinthe.")
 */
public interface IExitRoom extends INormalRoom {
    
    // REQUETE

    @Override
    String describeEffect(IPlayer p);

    // COMMANDES

    /**
     * @post <pre>
     *     p.getHitPoints() == old p.getHitPoints()
     *     p.hasLeft() </pre>
     */
    @Override
    void doEffect(IPlayer p);
}

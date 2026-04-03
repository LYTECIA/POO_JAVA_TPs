package tp08.model.room;

import tp08.model.IPlayer;
import tp08.model.IRoom;

/**
 * Modélise les salles magiques qui rechargent le joueur en mana.
 * 
 * @inv
 *     forall p in IPlayer :
 *         describeEffect(p).contains(
 *             "Vous sentez un flot d'énergie positive vous envahir !")
 */
public interface IMagicRoom extends IRoom {

    // CONSTANTES STATIQUES

    int MIN = 5;
    int MAX = 10;
    
    // REQUETE

    @Override
    String describeEffect(IPlayer p);

    // COMMANDES

    /**
     * @post <pre>
     *     !p.hasLeft()
     *     p.getHitPoints() == old p.getHitPoints()
     *     Let x ::= old p.getPowerLevel()
     *     x + MIN <= p.getPowerLevel() <= x + MAX </pre>
     */
    @Override
    void doEffect(IPlayer p);
}

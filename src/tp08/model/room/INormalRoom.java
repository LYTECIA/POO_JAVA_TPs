package tp08.model.room;

import tp08.model.IPlayer;
import tp08.model.IRoom;

/**
 * Modélise les salles normales qui diminuent un peu le mana du joueur entrant.
 * 
 * @inv
 *     forall p in IPlayer :
 *         describeEffect(p).contains("Vous perdez un peu de mana...")
 */
public interface INormalRoom extends IRoom {

    // CONSTANTES STATIQUES

    int POWER_LOSS = 1;
    
    // REQUETE

    @Override
    String describeEffect(IPlayer p);

    // COMMANDES

    /**
     * @post <pre>
     *     Let x ::= old p.getPowerLevel()
     *     p.getPowerLevel() == max(0, x - POWER_LOSS) </pre>
     */
    @Override
    void doEffect(IPlayer p);
}

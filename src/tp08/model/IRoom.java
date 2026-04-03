package tp08.model;

/**
 * Modélise les salles du labyrinthe qui ont un effet sur les joueurs.
 * 
 * @inv
 *     forall p in IPlayer : describeEffect(p) != null
 */
public interface IRoom {
    
    // REQUETE
    
    /**
     * Ce que ressentira p en entrant dans la salle quand on lui appliquera
     *  doEffect().
     * @pre <pre>
     *     p != null
     *     !p.hasLeft()
     *     p.getRoom() != this </pre>
     */
    String describeEffect(IPlayer p);
    
    // COMMANDE
    
    /**
     * Produit un effet sur p.
     * @pre <pre>
     *     p != null
     *     !p.hasLeft()
     *     p.getRoom() == this </pre>
     * @post <pre>
     *     p.getRoom() == old getRoom()
     *     p.getMood() == old getMood()
     *     le reste de l'état de p et l'état de this peuvent avoir changé </pre>
     */
    void doEffect(IPlayer p);
}

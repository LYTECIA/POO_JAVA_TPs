package tp08.model;

import java.util.HashMap;
import java.util.Map;

import tp08.util.Direction;
import util.Contract;

class RoomNetwork implements IRoomNetwork {

    // ATTRIBUTS D'INSTANCE

    /**
     * Le réseau de connexion entre les différentes salles.
     * A chaque salle r connectée, il correspond une map qui indique à quelle
     *  autre salle est connectée r, et à partir de quelle direction.
     */
    private final Map<IRoom, Map<Direction, IRoom>> network;

    // CONSTRUCTEURS

    RoomNetwork() {
        network = new HashMap<>();
    }

    // REQUETES

    @Override
    public boolean canExit(IRoom r, Direction d) {
        Contract.checkCondition(r != null);
        Contract.checkCondition(d != null);

        return getRoomImpl(r, d) != null;
    }

    @Override
    public IRoom getRoom(IRoom r, Direction d) {
        Contract.checkCondition(r != null);
        Contract.checkCondition(d != null);

        return getRoomImpl(r, d);
    }

    // COMMANDES

    @Override
    public void clear() {
        network.clear();
    }

    @Override
    public void connect(IRoom r1, Direction d, IRoom r2) {
        Contract.checkCondition(r1 != null);
        Contract.checkCondition(d != null);
        Contract.checkCondition(r2 != null);

        /*
         * x == getRoomImpl(r1, d) && y == getRoomImpl(r2, d.opposite())
         * SI x == r2 ALORS
         *     // invariant : y == r1
         *     // rien à faire car r1 et r2 sont déjà en relation symétrique
         * SINON
         *     // invariant : y != null ==> y != r1
         *     SI x != null ALORS
         *         supprimer la relation de x vers r1
         *     FIN SI
         *     établir une relation de r1 vers r2
         *     // ce qui supprime l'éventuelle relation de r1 vers x
         *     SI y != null ALORS
         *         supprimer la relation de y vers r2
         *     FIN SI
         *     établir une relation de r2 vers r1
         *     // ce qui supprime l'éventuelle relation de r2 vers y
         * FIN SI
         */
        IRoom x = getRoomImpl(r1, d);
        if (x != r2) {
            if (x != null) {
                unsetRelationInOneWay(x, d.opposite());
            }
            setRelationInOneWay(r1, d, r2);
            IRoom y = getRoomImpl(r2, d.opposite());
            if (y != null) {
                unsetRelationInOneWay(y, d);
            }
            setRelationInOneWay(r2, d.opposite(), r1);
        }
    }

    // OUTILS

    /**
     * La salle à laquelle on accède à partir de r, dans la direction d.
     * NB : result peut être null, cela signifie qu'il n'y a pas de salle
     *  associée à r dans la Direction d.
     * @pre
     *     r != null && d != null
     * @post
     *     data.get(r) == null || data.get(r).get(d) == null
     *         ==> result == null
     *     data.get(r) != null && data.get(r).get(d) != null
     *         ==> result == data.get(r).get(d)
     */
    private IRoom getRoomImpl(IRoom r, Direction d) {
        assert r != null && d != null;

        Map<Direction, IRoom> m = network.get(r);
        return m == null ? null: m.get(d);
    }

    /**
     * Etablit une relation entre src et dest, dans la direction d en partant
     *  de src.
     * L'invariant n'est pas requis en entrée et n'est pas respecté en sortie !
     * @pre
     *     src != null && d != null && dest != null
     *     getRoom(src, d) == null
     * @post
     *     getRoom(src, d) == dest
     */
    private void setRelationInOneWay(IRoom src, Direction d, IRoom dest) {
        assert src != null && d != null && dest != null;
        assert getRoomImpl(src, d) == null;

        Map<Direction, IRoom> m = network.get(src);
        if (m == null) {
            m = new HashMap<>();
            network.put(src, m);
        }
        m.put(d, dest);
    }

    /**
     * Sachant qu'il existe une relation à partir de r dans la direction d,
     *  supprime uniquement cette relation.
     * L'invariant n'est pas requis en entrée et n'est pas respecté en sortie !
     * @pre
     *     r != null && d != null
     *     getRoom(r, d) != null
     * @post
     *     getRoom(r, d) == null
     */
    private void unsetRelationInOneWay(IRoom r, Direction d) {
        assert r != null && d != null;
        assert getRoomImpl(r, d) != null;

        Map<Direction, IRoom> m = network.get(r);
        m.remove(d);
        if (m.isEmpty()) {
            network.remove(r);
        }
    }
}

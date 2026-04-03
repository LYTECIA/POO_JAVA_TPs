package tp08.view;

import tp08.model.IPlayer;
import util.Contract;

class PlayerDescriptor implements IDescriptor {

    // ATTRIBUTS DE CLASSE

    private static final String LIVE_PLAYER = "@";
    private static final String DEAD_PLAYER = "#";
    private static final String SUPER_PLAYER = "$";

    // ATTRIBUTS D'INSTANCE

    private final IPlayer player;

    // CONSTRUCTEURS

    PlayerDescriptor(IPlayer p) {
        Contract.checkCondition(p != null);
        
        player = p;
    }

    // REQUETES

    @Override
    public String describe(boolean light) {
        if (player.isDead()) {
            return DEAD_PLAYER;
        }
        if (player.getPowerLevel() > 0) {
            return SUPER_PLAYER;
        } else {
            return LIVE_PLAYER;
        }
    }
}

package tp08.util.cmd;

import tp08.model.IPlayer;
import tp08.model.IRoom;
import tp08.model.IRoomNetwork;
import tp08.model.RoomNetworkFactory;
import tp08.util.Direction;
import util.Contract;

/**
 * @inv <pre>
 *     argsNb() == 1
 *     getArgument(0) instanceof Direction </pre>
 */
class GoCommand extends Command {

    // ATTRIBUTS STATIQUES

    private static final String INDENT = "     ";

    // CONSTRUCTEURS

    /**
     * @pre <pre>
     *     args != null
     *     args.length > 0 </pre>
     * @post <pre>
     *     getArgument(0) == Direction.valueOf(args[0])
     * </pre>
     */
    GoCommand(String[] args) {
        super(convertArgs(args));
    }
    
    // COMMANDES

    @Override
    public void executeFor(IPlayer p) {
        Contract.checkCondition(p != null);
        Contract.checkCondition(!p.hasLeft());

        // Essayer de quitter la pièce courante.
        Direction dir = (Direction) getArgument(0);
        if (dir == null) {
            setDescription(
                    "Aller... oui, mais où ? Ce n'est pas une direction !"
            );
        } else {
            IRoomNetwork net = RoomNetworkFactory.get();
            IRoom r = p.getRoom();
            if (!net.canExit(r, dir)) {
                // il n'y a pas de passage
                setDescription("Aïe ! Il n'y a pas de passage par là !");
            } else {
                // il y a un passage
                IRoom nextRoom = net.getRoom(r, dir);
                p.setRoom(nextRoom);
                String description = p.getMood();
                setDescription(indent(description));
            }
        }
    }

    // OUTILS

    private static String indent(String message) {
        String result = "Vous avez changé de pièce.";
        String[] parts = message.split("\n");
        for (int i = 0; i < parts.length; i++) {
            String s = parts[i].trim();
            if (!s.isEmpty()) {
                result += "\n" + INDENT + s;
            }
        }
        return result;
    }

    private static Object[] convertArgs(String[] args) {
        Contract.checkCondition(args != null);
        Contract.checkCondition(args.length > 0);
        
        return new Object[] { Direction.valueOf(args[0]) };
    }
}

package tp08.util.cmd;

import tp08.model.IPlayer;
import util.Contract;

/**
 * @inv <pre>
 *     argsNb() == 0 </pre>
 */
class QuitCommand extends Command {

    // CONSTRUCTEURS

    /**
     * Une commande permettant de quitter la partie.
     * Puisque ce type de commandes ne possède pas de paramètre, args est
     *  ignoré.
     */
    QuitCommand(String[] args) {
        super();
    }

    // COMMANDES

    @Override
    public void executeFor(IPlayer p) {
        Contract.checkCondition(p != null);
        Contract.checkCondition(!p.hasLeft());

        p.leave();
        setDescription("Vous arrêtez de jouer !");
    }
}

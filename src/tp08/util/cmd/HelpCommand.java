package tp08.util.cmd;

import tp08.model.IPlayer;
import util.Contract;

/**
 * @inv <pre>
 *     argsNb() == 0 </pre>
 */
class HelpCommand extends Command {

    // CONSTRUCTEURS

    /**
     * Une commande rappelant le nom de toutes les commandes disponibles.
     * Puisque ce type de commandes ne possède pas de paramètre, args est
     *  ignoré.
     */
    HelpCommand(String[] args) {
        super();
    }

    // COMMANDES
    
    @Override
    public void executeFor(IPlayer p) {
        Contract.checkCondition(p != null);
        Contract.checkCondition(!p.hasLeft());

        setDescription("Vous êtes perdu dans pyland ? Ordonnez "
                + CommandFactory.getAllCommands()
        );
    }
}

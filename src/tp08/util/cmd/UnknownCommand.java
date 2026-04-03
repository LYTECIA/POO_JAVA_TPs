package tp08.util.cmd;

import tp08.model.IPlayer;
import util.Contract;

/**
 * @inv <pre>
 *     argsNb() == 1
 *     getArgument(0) instanceof String
 *     getArgument(0) est la phrase non reconnue, tapée par le joueur humain
 *     getArgument(0) se termine par le message d'erreur obtenu lors de la
 *       construction de cette commande inconnue </pre>
 */
class UnknownCommand extends Command {

    // CONSTRUCTERUS

    /**
     * Une commande inconnue dont l'unique argument est le texte tapé par le
     *  joueur humain et non reconnu par l'analyseur.
     * Le dernier élément de args contient le message d'erreur obtenu lors de la
     *  construction de cette commande inconnue.
     * @pre <pre>
     *     args != null
     *     |args| > 0 </pre>
     * @post <pre>
     *     getArgument(0) est la concaténation des éléments de args </pre>
     */
    UnknownCommand(String[] args) {
        super(convertArgs(args));
    }
    
    // COMMANDES

    @Override
    public void executeFor(IPlayer p) {
        Contract.checkCondition(p != null);
        Contract.checkCondition(!p.hasLeft());

        setDescription(getArgument(0) + "... ?!");
    }

    // OUTILS

    private static Object[] convertArgs(String[] args) {
        Contract.checkCondition(args != null);
        Contract.checkCondition(args.length > 0);

        return new Object[] { String.join(" ", args) };
    }
}

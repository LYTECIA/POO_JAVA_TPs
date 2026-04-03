package tp08.util.cmd;

import tp08.model.IPlayer;

/**
 * Modélise les commandes.
 *
 * @inv <pre>
 *    argsNb() >= 0
 *    describeLastAction() != null
 *    (getName() != null) && !getName().isEmpty() </pre>
 *
 * @cons <pre>
 * $DESC$
 *     Une commande munie d'une séquence d'arguments dont la représentation
 *      textuelle se trouve dans args.
 * $ARGS$
 *     String[] args
 * $PRE$
 *     args != null
 * $POST$
 *     Let min ::= min(argsNb(), |args|)
 *     forall i, 0 <= i < min :
 *         getArgument(i) est l'objet que représente la chaîne args[i]
 *     forall i, min <= i < argsNb() :
 *         getArgument(i) == null
 *     describeLastAction().equals("") </pre>
 */
public interface ICommand {
    
    // REQUETES

    /**
     * Le nombre d'arguments de la commande.
     */
    int argsNb();

    /**
     * Décrit le résultat de la dernière exécution de la commande.
     */
    String describeLastAction();

    /**
     * Le ième argument de la commande.
     * @pre <pre>
     *     0 <= i < argsNb() </pre>
     */
    Object getArgument(int i);

    /**
     * Le nom de la commande (construit à partir du nom de la classe).
     */
    String getName();

    // COMMANDES

    /**
     * Exécute la commande pour le joueur <code>p</code>.
     * @pre <pre>
     *     p != null
     *     !p.hasLeft() </pre>
     * @post <pre>
     *     argsNb() == old argsNb()
     *     forall i, 0 <= i < argsNb() : getArgument(i) == old getArgument(i)
     *     getName() == old getName()
     *     l'environnement a été modifié en accord avec le type de la commande
     *     describeLastAction() reflète l'exécution de la commande </pre>
     */
    void executeFor(IPlayer p);
}

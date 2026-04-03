package tp08.util.cmd;

import util.Contract;

/**
 * Implantation partielle du type des commandes.
 * Le nom des commandes doit être construit sous la forme :
 *  <code>UnNom + "Command"</code>.
 *  
 * @inv <pre>
 *     getName() correspond au nom de la classe privé du suffixe "Command"
 *     describeLastAction().startsWith(getName() + " : ") </pre>
 */
abstract class Command implements ICommand {
    
    // ATTRIBUTS D'INSTANCE

    private final Object[] args;
    private String description;

    // CONSTRUCTEURS

    /**
     * Une commande munie d'une séquence d'arguments <code>args</code>.
     * Ce sera au constructeur de la sous-classe de fournir un tableau adapté.
     * @pre <pre>
     *     args != null </pre>
     * @post <pre>
     *     argsNb() == |args|
     *     forall i, 0 <= i < |args| : getArgument(i).equals(args[i])
     *     describeLastAction().equals(getName() + " : ") </pre>
     */
    protected Command(Object[] args) {
        Contract.checkCondition(args != null,
                "Le tableau d'arguments n'existe pas");

        this.args = new Object[args.length];
        System.arraycopy(args, 0, this.args, 0, args.length);
        description = "";
    }

    /**
     * Une commande sans argument.
     * @post <pre>
     *     argsNb() == 0
     *     describeLastAction().equals(getName() + " : ") </pre>
     */
    protected Command() {
        this(new Object[0]);
    }

    // REQUETES

    @Override
    public int argsNb() {
        return args.length;
    }

    @Override
    public String describeLastAction() {
        return getName() + " : " + description;
    }

    @Override
    public Object getArgument(int i) {
        Contract.checkCondition(0 <= i && i < args.length);

        return args[i];
    }

    @Override
    public String getName() {
        String[] cmd = this.getClass().getName().split("\\.");
        cmd = cmd[cmd.length - 1].split("Command");
        return cmd[0];
    }

    // COMMANDES

    /**
     * Fixe la description de la dernière exécution de la commande.
     * @pre <pre>
     *     desc != null </pre>
     * @post <pre>
     *     argsNb() == old argsNb()
     *     forall i, 0 <= i < argsNb() : getArgument(i) == old getArgument(i)
     *     getName() == old getName()
     *     describeLastAction().equals(getName() + " : " + desc) </pre>
     */
    protected void setDescription(String desc) {
        assert desc != null;

        description = desc;
    }
}

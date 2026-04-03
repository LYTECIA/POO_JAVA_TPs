package tp08.util.cmd;

import java.lang.reflect.InvocationTargetException;

/**
 * Cette usine à commandes crée des commandes à la demande de l'analyseur.
 */
public final class CommandFactory {
    
    private static final String CLASS_PREFIX =
            CommandFactory.class.getPackage().getName();

    // CONSTRUCTEURS

    private CommandFactory() {
        // pas d'instanciation externe possible
    }

    /**
     * Pour obtenir une instance de commande en fonction du nom proposé.
     */
    public static ICommand getInstance(String name, String[] args) {
        if ((name == null) || (name.isEmpty())) {
            return new UnknownCommand(args);
        }
        String className = CLASS_PREFIX + "." + name;
        ICommand cmd = null;
        String error = "";
        try {
            if (args == null) {
                args = new String[0];
            }
            Class<?> stringTabClass = Class.forName("[Ljava.lang.String;");
            cmd = (ICommand) Class.forName(className)
                    .getDeclaredConstructor(new Class<?>[] { stringTabClass })
                        .newInstance(new Object[] { args });
        } catch (IllegalArgumentException e) {
            error = "Type des paramètres du constructeur non valide : "
                    + e.getMessage();
        } catch (SecurityException e) {
            error = "Accès à la classe de commande impossible : "
                    + e.getMessage();
        } catch (InstantiationException e) {
            error = "Classe de commande non instanciable : " + e.getMessage();
        } catch (IllegalAccessException e) {
            error = "Accès au constructeur impossible : " + e.getMessage();
        } catch (InvocationTargetException e) {
            error = "Exception levée par le constructeur : " + e.getMessage();
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            error = "Constructeur de commande non défini : " + e.getMessage();
        } catch (ClassNotFoundException e) {
            error = "Commande inexistante : " + e.getMessage();
        }
        if (cmd == null) {
            String[] newArgs = new String[args.length + 2];
            newArgs[0] = name;
            System.arraycopy(args, 0, newArgs, 1, args.length);
            newArgs[newArgs.length - 1] = "(" + error + ")";
            return new UnknownCommand(newArgs);
        } else {
            return cmd;
        }
    }

    /**
     * Chaîne de toutes les commandes disponibles avec leur syntaxe.
     */
    public static String getAllCommands() {
        return "go <dir> | help | quit";
    }
}

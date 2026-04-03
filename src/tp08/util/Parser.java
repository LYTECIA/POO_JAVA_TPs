package tp08.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import tp08.util.cmd.CommandFactory;
import tp08.util.cmd.ICommand;
import util.Contract;

/**
 * Un analyseur qui lit les caractères tapés par l'utilisateur et les
 *  interprète comme des commandes.
 * Chaque appel à <code>readCommandFromInput()</code> permet de lire une
 *  ligne sur le canal d'entrée et de l'interpréter comme une commande à
 *  laquelle on pourra accéder par <code>getCurrentCommand()</code>.
 */
public class Parser {

    // ATTRIBUTS STATIQUES

    private static final String DIRS = "NnEeSsWw";
    private static final Map<String, String> CMD_NAMES;
    static {
        CMD_NAMES = new HashMap<>();
        CMD_NAMES.put("g", "GoCommand");
        CMD_NAMES.put("h", "HelpCommand");
        CMD_NAMES.put("q", "QuitCommand");
    }
    private static final Map<String, String> DIR_NAMES;
    static {
        DIR_NAMES = new HashMap<>();
        DIR_NAMES.put("n", "NORTH");
        DIR_NAMES.put("e", "EAST");
        DIR_NAMES.put("s", "SOUTH");
        DIR_NAMES.put("w", "WEST");
    }

    // ATTRIBUTS

    private final BufferedReader input;
    private ICommand command;
    private String name;
    private String[] args;

    // CONSTRUCTEURS

    /**
     * @pre <pre>
     *     input != null </pre>
     */
    public Parser(Reader input) {
        Contract.checkCondition(input != null,
                "Le canal d'entrée n'existe pas");

        this.input = new BufferedReader(input);
        command = null;
        name = null;
        args = null;
    }

    // REQUETES

    /**
     * La dernière commande lue.
     */
    public ICommand getCurrentCommand() {
        return command;
    }

    // COMMANDES

    /**
     * Lit la première ligne non vide du canal d'entrée.
     * Cette ligne est de la forme : w_0 ... w_n (n ≥ 0).
     * w_0 est interprété comme  le nom de la commande.
     * w_i (i > 0) est interprété comme le i-ème argument de la commande.
     * S'il manque des arguments, les manquants sont positionnés à null.
     * 
     * @post <pre>
     *     Let c ::= getCurrentCommand()
     *     c == null
     *         <==> le canal d'entrée était vide
     *              || le canal d'entrée ne contenait que des lignes vides
     *     c != null
     *         ==> Let an ::= c.argsNb()
     *                 ai ::= c.getArgument(i)
     *             c.getName() est la commande représentée par < w_0 >
     *             forall i, 1 <= i <= min(n, an) :
     *                 ai est l'objet représenté par < w_i >
     *             forall i, min(n, an) < i <= an :
     *                 ai == null </pre>
     * 
     * @throws IOException
     *     si un problème d'entrée / sortie est survenu durant la lecture du
     *       canal d'entrée
     */
    public void readCommandFromInput() throws IOException {
        command = null;
        String line = "";
        while (line != null && line.isEmpty()) {
            line = input.readLine();
            if (line != null) {
                line = line.trim();
            }
        }
        if (line != null) {
            buildNameAndArgsFrom(line);
            command = CommandFactory.getInstance(name, args);
        }
    }

    // OUTILS

    /**
     * @pre
     *     line != null && !line.isEmpty()
     * @post
     *     name in CMD_NAMES.values() ou name == null
     *     args est le tableau des arguments de la commande, éventuellement
     *       complété à null
     *     
     * @throws IOException
     *     si line ne représente pas une commande syntaxiquement bien formée
     */
    private void buildNameAndArgsFrom(String line) {
        assert line != null && !line.isEmpty();

        String[] words = line.split("\\s+");
        int startIndex;
        if (words.length == 1 && words[0].length() == 1
                && DIRS.indexOf(words[0].charAt(0)) != -1) {
            // n -> go north, e -> go east, s -> go south, w -> go west
            name = "g";
            startIndex = 0;
        } else {
            name = String.valueOf(words[0].charAt(0)).toLowerCase();
            startIndex = 1;
        }
        name = CMD_NAMES.get(name);
        if (name == null) {
            args = words;
            return;
        }
        switch (name) {
            case "GoCommand" -> {
                if (startIndex >= words.length) {
                    name = null;
                    args = words;
                } else {
                    char firstChar = words[startIndex].charAt(0);
                    String dir = String.valueOf(firstChar).toLowerCase();
                    args = new String[] { DIR_NAMES.get(dir) };
                }
            }
            case "HelpCommand", "QuitCommand" -> 
                args = new String[0];
            default -> {
                name = null;
                args = words;
            }
        }
    }
}

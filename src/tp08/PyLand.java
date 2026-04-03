package tp08;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import tp08.model.IPlayer;
import tp08.model.Player;
import tp08.util.Parser;
import tp08.util.cmd.ICommand;
import tp08.view.Maze;
import tp08.view.RoomNotFoundException;
import util.Contract;

public class PyLand {

    // ATTRIBUTS STATIQUES

    private static final String SUPER = "+";
    private static final int HP = 10;
    private static final int MIN = Maze.MIN_SIZE;

    // ATTRIBUTS D'INSTANCE

    private final Maze maze;
    private final Parser parser;
    private final PrintStream output;

    private String lastReport;

    // CONSTRUCTEURS

    public PyLand(int n, int m) {
        Contract.checkCondition(n >= MIN);
        Contract.checkCondition(m >= MIN);

        maze = new Maze(n, m);
        parser = new Parser(new InputStreamReader(System.in));
        output = System.out;
        lastReport = "";
    }

    // COMMANDES

    public void play() {
        maze.build();
        IPlayer player = new Player(maze.entry(), HP);
        maze.recordPlayer(player);
        markCurrentRoom();
        output.println(maze.describe());
        while (!player.hasLeft()) {
            output.print(getPrompt(player));
            readNextCommandFromInput();
            executeCommandAndBuildLastReport(player);
            output.println(lastReport);
            output.println(maze.describe());
        }
        printFinalReport(player);
        printStats();
    }

    // OUTILS
    
    private void markCurrentRoom() {
        try {
            maze.markCurrentRoom();
        } catch (RoomNotFoundException e) {
            System.err.println(
                    "Erreur interne : " + e.getMessage());
            System.exit(1);
        }
    }
    
    private String getPrompt(IPlayer p) {
        String res = "(" + p.getHitPoints() + ")[";
        for (int i = 0; i < p.getPowerLevel(); i++) {
            res += SUPER;
        }
        return res + "]> ";
    }

    private void readNextCommandFromInput() {
        try {
            parser.readCommandFromInput();
        } catch (IOException e) {
            System.err.println(
                    "Erreur en cours de lecture : " + e.getMessage());
        }
    }

    private void executeCommandAndBuildLastReport(IPlayer p) {
        lastReport = "";
        ICommand cmd = parser.getCurrentCommand();
        if (cmd != null) {
            cmd.executeFor(p);
            if (p.hasLeft()) {
                maze.lightOn();
            } else {
                markCurrentRoom();
            }
            lastReport = cmd.describeLastAction();
        }
    }

    private void printFinalReport(IPlayer p) {
        if (p.getRoom() != maze.exit()) {
            output.println("Bouh le lâche ! Vous avez abandonné !");
        } else {
            output.println("Bravo ! Vous avez gagné, vous êtes sorti"
                    + " des griffes de PY le maléfique !"
            );
        }
    }
    
    private void printStats() {
        int n = maze.rowsNb() * maze.colsNb();
        int v = maze.visitedRooms();
        
        output.println();
        output.println(String.format(
                "Pourcentage de salles visitées : %1$.2f %1$%",
                v * 100.0 / n));
    }

    // POINT D'ENTREE

    public static void main(String[] args) {
        new PyLand(5, 8).play();
    }
}

package tp04;

public final class Test {

    private Test() {
        // pas d'instanciation possible hors du texte de la classe
    }

    /**
     * Modélise un jeu à 2 joueurs, le premier ayant un dé à 6 faces et le
     *  second un dé à 7 faces, où le résultat est obtenu après n coups.
     * Chaque joueur joue à tour de rôle et incrémente son score du gain qu'il
     *  a réalisé.
     * Au bout de n coups, on compare les résultats et le meilleur score
     *  a gagné.
     */
    public static void game1(int n) {
        /***************/
        /* A COMPLETER */
        /***************/
    }

    /**
     * Modélise un jeu à 10 joueurs, tous dotés d'un dé à 6 faces.
     * Chaque joueur joue à tour de rôle, marquant des points en fonction de
     *  son gain.
     * Tant qu'aucun joueur n'a obtenu un certain nombre max de points,
     *  on recommence un tour de jeu.
     * Les premiers obtenant max points ont gagné.
     * Notez bien que même si l'un des joueurs atteint ou dépasse le max,
     *  on finit le tour avant de regarder quels sont les heureux gagnants...
     */
    public static void game2(int max) {
        /***************/
        /* A COMPLETER */
        /***************/
    }

    public static void main(String[] args) {
        final int turnsNb = 3;
        final int maxValue = 20;
        game1(turnsNb);
        System.out.println("---------------------");
        game2(maxValue);
    }
}

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
        int[] a = new int[]{6};
        int[] b = new int[]{7};

        IPlayer[] players = new Player[]{new Player(a), new Player(b)};

        int tour = 1;

        while (n > 0) {

            for (int i = 0; i < players.length; i++) {
                players[i].playOnce();
                players[i].addToScore();

                System.out.println(
                    tour + " Score de player[" + i + "] : " + players[i].score()
                );
            }

            n--;
            tour++;
        }

        if (players[0].score() > players[1].score()) {
            System.out.println("Le joueur 0 a gagné avec un score de " + players[0].score());
        } else if (players[1].score() > players[0].score()) {
            System.out.println("Le joueur 1 a gagné avec un score de " + players[1].score());
        } else {
            System.out.println("Égalité !");
        }
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

        IPlayer[] players = new Player[10];
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player(new int[]{6});
        }

        boolean finished = false;
        int tour = 1;

        while (!finished) {

            int maxScore = 0;

            for (int i = 0; i < players.length; i++) {

                players[i].playOnce();
                players[i].addToScore();

                System.out.println("Score " + tour + " de player[" + i + "] : " + players[i].score());

                if (players[i].score() > maxScore) {
                    maxScore = players[i].score();
                }
            }

            if (maxScore >= max) {
                finished = true;
            }

            tour++;
        }

        int bestScore = 0;
        for (int i = 0; i < players.length; i++) {
            if (players[i].score() > bestScore) {
                bestScore = players[i].score();
            }
        }

        for (int i = 0; i < players.length; i++) {
            if (players[i].score() == bestScore) {
                System.out.println("Score final de player[" + i + "] : " + players[i].score() + " gagnant !!!");
            } else {
                System.out.println("Score final de player[" + i + "] : " + players[i].score());
            }
        }
    }

    public static void main(String[] args) {
        final int turnsNb = 3;
        final int maxValue = 20;
        game1(turnsNb);
        System.out.println("---------------------");
        game2(maxValue);
    }
}

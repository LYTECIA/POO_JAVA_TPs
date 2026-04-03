package tp08.view;

interface IDescriptor {

    // CONSTANTES STATIQUES

    String EMPTY1 = " ";
    String EMPTY3 = "   ";
    String VISITED1 = ".";
    String VISITED3 = " . ";

    // REQUETES

    /**
     * Retourne la représentation textuelle de l'élément (joueur, salle ou
     *  bordure) décrit.
     * Le paramètre indique si le labyrinthe doit être éclairé (le contenu de
     *  toutes les salles est visible) ou non (seul le contenu des salles
     *  visitées, ou limitrophes d'un joueur à super-pouvoirs, est visible).
     */
    String describe(boolean withLight);
}

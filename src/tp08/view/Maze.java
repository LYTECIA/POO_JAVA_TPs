package tp08.view;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import tp08.model.IPlayer;
import tp08.model.IRoom;
import tp08.model.IRoomNetwork;
import tp08.model.RoomNetworkFactory;
import tp08.util.Direction;
import util.Contract;

/**
 * Le labyrinthe des pièces, rectangulaire (rows * cols) parfait (toute
 *  pièce est accessible) et 1-connexe (toute pièce est accessible par un
 *  seul chemin).
 * @inv <pre>
 *     colsNb() >= MIN_SIZE
 *     rowsNb() >= MIN_SIZE
 *     entry() != null
 *     exit() != null </pre>
 */
public class Maze {

    // ATTRIBUTS DE CLASSE

    public static final int MIN_SIZE = 5;

    private static final float SUB_TYPE_RATIO = 0.4f;
    private static final float MORE_CONNEXIONS = 0.15f;

    // ATTRIBUTS D'INSTANCE

    private MazeRoom[][] rooms; // le tableau des salles
    private final int rows; // nb de lignes
    private final int cols; // nb de colonnes
    private IDescriptor mazeDesc;
    private IDescriptor playerDesc;
    private boolean enlightened; // le labyrinthe est-il révélé ?
    private int visitedRooms; // nb de salles visitées par le joueur
    private IPlayer currentPlayer;

    // CONSTRUCTEURS

    /**
     * Un labyrinthe à n lignes et m colonnes.
     * @pre <pre>
     *     n >= MIN_SIZE && m >= MIN_SIZE </pre>
     * @post <pre>
     *     rowsNb() == n
     *     colsNb() == m </pre>
     */
    public Maze(int n, int m) {
        Contract.checkCondition(n >= MIN_SIZE);
        Contract.checkCondition(m >= MIN_SIZE);

        rows = n;
        cols = m;
        rooms = null;
        visitedRooms = 0;
        mazeDesc = null;
        playerDesc = null;
        currentPlayer = null;
        enlightened = false;
    }

    // REQUETES

    public int rowsNb() {
        return rows;
    }

    public int colsNb() {
        return cols;
    }

    /**
     * L'entrée du labyrinthe (la salle en bas à gauche).
     */
    public IRoom entry() {
        return rooms == null ? null : rooms[rows - 1][0].getRoom();
    }

    /**
     * La sortie du labyrinthe : la salle en haut à droite.
     */
    public IRoom exit() {
        return rooms == null ? null : rooms[0][cols - 1].getRoom();
    }
    
    public IDescriptor getPlayerDescriptorFor(MazeRoom r) {
        IRoom currentRoom = currentPlayer.getRoom();
        if (currentRoom == r.getRoom()) {
            return playerDesc;
        }
        return null;
    }
    
//    public IRoom currentRoom() {
//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < cols; j++) {
//                IRoom r = rooms[i][j].getRoom();
//                if (r.getVisitor() != null) {
//                    return r;
//                }
//            }
//        }
//        return null;
//    }
//    public IRoom currentRoom() {
//        Stream<Pair<Pos, IRoom>> res = findElements(
//                rows, cols,
//                pos -> new Pair<Pos, IRoom>(pos, rooms[pos.row()][pos.col()].getRoom()),
//                pair -> pair.second(),
//                room -> room.getVisitor() != null);
//        Pair<Pos, IRoom> p = res.findFirst().orElse(null);
//        return p == null ? null : p.second();
//    }

    /**
     * Une chaîne qui décrit le labyrinthe.
     */
    public String describe() {
        return mazeDesc.describe(enlightened);
    }
    
    public int visitedRooms() {
        return visitedRooms;
    }
//    public int visitedRooms() {
//        Stream<Pos> res = findPositions(
//                rows, cols,
//                pos -> rooms[pos.row()][pos.col()],
//                r -> r.isVisited());
//        return (int) res.count();
//    }
//    private static Stream<Pos> findPositions(
//            int rows, int cols,
//            Function<Pos, MazeRoom> accessor,
//            Predicate<MazeRoom> predicate) {
//        return IntStream.range(0, rows).boxed()
//            .flatMap(i -> IntStream.range(0, cols).mapToObj(j -> new Pos(i, j)))
//            .filter(pos -> predicate.test(accessor.apply(pos)));
//    }
//    // version générique utilisant la version générique de findPositions
//    public int visitedRooms() {
//        Stream<Pos> res = findPositions(
//                rows, cols,
//                pos -> rooms[pos.row()][pos.col()],
//                room -> room.isVisited());
//        return (int) res.count();
//    }
//    // version générique utilisant findElements
//    private int visitedRooms() {
//        Stream<Pair<Pos, MazeRoom>> res = findElements(
//                rows, cols,
//                pos -> new Pair<>(pos, rooms[pos.row()][pos.col()]),
//                pair -> pair.second(),
//                room -> room.isVisited());
//        return (int) res.count();
//    }
    

    // COMMANDES

    /**
     * Eteint le labyrinthe (on ne voit que la partie déjà visitée du
     *  labyrinthe).
     */
    public void lightOff() {
        enlightened = false;
    }

    /**
     * Allume le labyrinthe (on voit tout le labyrinthe).
     */
    public void lightOn() {
        enlightened = true;
    }

    /**
     * Construit un nouveau labyrinthe.
     * @post <pre>
     *     Le labyrinthe est constitué de nouvelles pièces dont les parois ont
     *      éventuellement été creusées pour constituer un labyrinthe parfait
     *      1-connexe. </pre>
     */
    public void build() {
        initRooms();
        buildMaze();
        visitedRooms = 0;
        mazeDesc = new MazeDescriptor(this);
    }

    /**
     * Mémorise p comme joueur courant.
     */
    public void recordPlayer(IPlayer p) {
        currentPlayer = p;
        playerDesc = new PlayerDescriptor(p);
    }

    /**
     * La salle qui contient le joueur courant est marquée
     *  comme visitée (utilisé lors de l'affichage du labyrinthe).
     * @throws RoomNotFoundException
     *     si la salle contenant le joueur courant ne se trouve pas dans le
     *       labyrinthe
     */
    public void markCurrentRoom() throws RoomNotFoundException {
        IRoom r = currentPlayer.getRoom();
        Pos pos = find(r);
        if (pos == null) {
            throw new RoomNotFoundException("la salle qui contient le joueur"
                    + " n'est pas dans le labyrinthe !");
        }

        MazeRoom mr = rooms[pos.row()][pos.col()];
        if (!mr.isVisited()) {
            visitedRooms  += 1;
            mr.setVisited();
        }
    }

    // OUTILS

    MazeRoom get(int r, int c) {
        return rooms[r][c];
    }

    boolean powerPlayerAround(int r, int c) {
        for (int i = -1; i <= +1; i++) {
            for (int j = -1; j <= +1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                int row = r + i;
                int col = c + j;
                if (isValid(row, col) && powerPlayerIsHere(row, col)) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean isValid(int r, int c) {
        return 0 <= r && r < rowsNb()
                && 0 <= c && c < colsNb();
    }
    
    boolean powerPlayerIsHere(int r, int c) {
        return currentPlayer != null
                && currentPlayer.getPowerLevel() > 0
                && currentPlayer.getRoom() == rooms[r][c].getRoom();
    }
    
    /**
     * Détecte les coordonnées (dans rooms) de la pièce r.
     * Retourne null si pas trouvée.
     */
    private Pos find(IRoom r) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (rooms[i][j].getRoom() == r) {
                    return new Pos(i, j);
                }
            }
        }
        return null;
    }
//    // version ad'hoc
//    private Pos find(IRoom r) {
//        Stream<Pos> res = findPositions(
//                rows, cols,
//                pos -> rooms[pos.row()][pos.col()].getRoom(),
//                room -> room == r);
//        return res.findFirst().orElse(null);
//    }
//    private static Stream<Pos> findPositions(
//            int rows, int cols,
//            Function<Pos, IRoom> accessor,
//            Predicate<IRoom> predicate) {
//        return IntStream.range(0, rows).boxed()
//            .flatMap(i -> IntStream.range(0, cols).mapToObj(j -> new Pos(i, j)))
//            .filter(pos -> predicate.test(accessor.apply(pos)));
//    }
//    
//    // version générique réutilisable pour visitedRooms
//    private Pos find(IRoom r) {
//        Stream<Pos> res = findPositions(rows, cols,
//                pos -> rooms[pos.row()][pos.col()].getRoom(),
//                room -> room == r);
//        return res.findFirst().orElse(null);
//    }
//    private static <A> Stream<Pos> findPositions(int rows, int cols,
//            Function<Pos, A> accessor,
//            Predicate<A> predicate) {
//        return IntStream.range(0, rows).boxed()
//                .flatMap(i -> IntStream.range(0, cols).mapToObj(j -> new Pos(i, j)))
//                .filter(pos -> predicate.test(accessor.apply(pos)));
//    }
//    
//    // version générique réutilisable pour visitedRooms et currentRoom
//    private Pos find(IRoom r) {
//        Stream<Pair<Pos, IRoom>> res = findElements(
//                rows, cols,
//                pos -> new Pair<>(pos, rooms[pos.row()][pos.col()].getRoom()),
//                pair -> pair.second(),
//                room -> room == r);
//        Pair<Pos, IRoom> p = res.findFirst().orElse(null);
//        return p == null ? null : p.first();
//    }
//    private static <A, B> Stream<A> findElements(
//            int rows, int cols,
//            Function<Pos, A> enpack,
//            Function<A, B> depack,
//            Predicate<B> pred) {
//        return IntStream.range(0, rows).boxed()
//            .flatMap(i -> IntStream.range(0, cols).mapToObj(j -> new Pos(i, j)))
//            .map(pos -> enpack.apply(pos))
//            .filter(a -> pred.test(depack.apply(a)));
//    }
//    private static record Pair<A, B>(A first, B second) {}

    /**
     * Alloue des salles toutes neuves au tableau maze en vue de la construction
     *  du labyrinthe.
     */
    private void initRooms() {
        List<Set<Integer>> specials = getSpecialNumbers();
        rooms = new MazeRoom[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int x = i * cols + j;
                if (specials.get(0).contains(x)) {
                    rooms[i][j] = MazeRoom.createMagicRoom(this, i, j);
                } else if (specials.get(1).contains(x)) {
                    rooms[i][j] = MazeRoom.createDoomRoom(this, i, j);
                } else if (specials.get(2).contains(x)) {
                    rooms[i][j] = MazeRoom.createMonsterRoom(this, i, j);
                } else {
                    rooms[i][j] = MazeRoom.createNormalRoom(this, i, j);
                }
            }
        }
        rooms[0][cols - 1] = MazeRoom.createExitRoom(this, 0, cols - 1);
    }
    
    /**
     * Retourne 3 ensembles de coordonnées aléatoires (sous forme d'entiers).
     * Chaque élément entier n devra être décodé ainsi :
     * - row = n / cols
     * - col = n % cols
     * Les ensembles sont deux à deux disjoints.
     * Le premier donne les coordonnées des MagicRooms.
     * Le deuxième donne les coordonnées des DoomRooms.
     * Le dernier donne les coordonnées des MonsterRooms.
     */
    private List<Set<Integer>> getSpecialNumbers() {
        final int size = rows * cols;
        final int entry = size - cols;
        final int exit = cols - 1;

        int specialRoomsNb = (int) (size * SUB_TYPE_RATIO);
        final int[] numbers = new int[] {
                4 * specialRoomsNb / 10,
                specialRoomsNb / 5,
                4 * specialRoomsNb / 10
        };
        List<Set<Integer>> result = new ArrayList<>(numbers.length);
        specialRoomsNb = 0;
        for (int i = 0; i < numbers.length; i++) {
            result.add(new HashSet<>(numbers[i]));
            specialRoomsNb += numbers[i];
        }

        Set<Integer> temp = new HashSet<>(specialRoomsNb);
        for (int i = 0; i < numbers.length; i++) {
            while (result.get(i).size() != numbers[i]) {
                int k = (int) (Math.random() * size);
                if (k != entry && k != exit && !temp.contains(k)
                        && (i == 0 || !nearEntry(k))) {
                    temp.add(k);
                    result.get(i).add(k);
                }
            }
        }
        return result;
    }

    private boolean nearEntry(int x) {
        final int entry = (rows - 1) * cols;
        return x == entry + 1 || x == entry - cols;
    }

    
    /**
     * Casse les murs aléatoirement pour construire un labyrinthe parfait
     *  1-connexe.
     * Puis casse encore quelques cloisons pour que ce soit plus fun.
     */
    private void buildMaze() {
        // la pile qui mémorise le chemin parcouru
        Queue<Pos> stack = Collections.asLifoQueue(new ArrayDeque<>());
        // coordonnées de la pièce courante (en haut à gauche)
        Pos crt = new Pos(0, 0);
        // connecter la pièce courante
        connect(crt);
        // tant que l'exploration n'est pas terminée
        while (crt != null) {
            // obtenir ses voisines déconnectées (nord, est, sud, ouest)
            List<Pos> neighbours = getDisconnectedNeighbours(crt);
            if (neighbours.size() > 0) {
                // il y a des pièces voisines déconnectées
                // en choisir une au hasard
                int randomIndex = (int) (Math.random() * neighbours.size());
                Pos selected = neighbours.get(randomIndex);
                // la connecter
                connect(selected);
                // casser le mur entre la pièce courante et cette voisine
                dig(crt, selected);
                // rajouter la pièce courante au chemin parcouru
                stack.add(crt);
                // aller dans la pièce voisine
                crt = selected;
            } else {
                // il n'y a pas de pièce voisine
                if (!stack.isEmpty()) {
                    // on n'est pas revenu au point de départ :
                    // on recule d'une pièce
                    crt = stack.remove();
                } else {
                    // la pile est vide : l'exploration est terminée
                    crt = null;
                }
            }
        }
        createSomeMoreConnexions();
    }

    /**
     * Casse quelques cloisons en plus entre les salles pour avoir un labyrinthe
     *  imparfait, c'est-à-dire qu'une salle peut être accédée par plusieurs
     *  chemins.
     * C'est moins parfait, mais c'est plus sympa.
     */
    private void createSomeMoreConnexions() {
        final int n = (int) (MORE_CONNEXIONS * rows * cols);
        int i = 0;
        while (i < n) {
            int x = (int) (Math.random() * rows * cols);
            Pos crt = new Pos(x / cols, x % cols);
            List<Pos> neighbours = getPossibleNeighbours(crt);
            if (neighbours.size() > 0) {
                int index = (int) (Math.random() * neighbours.size());
                Pos neighbour = neighbours.get(index);
                dig(crt, neighbour);
                i += 1;
            }
        }
    }

    /**
     * Retourne la liste des voisines de la salle située en p, dont les
     *  cloisons avec cette salle ne sont pas encore percées.
     */
    private List<Pos> getPossibleNeighbours(Pos p) {
        IRoomNetwork net = RoomNetworkFactory.get();
        int r = p.row();
        int c = p.col();
        List<Pos> result = new ArrayList<>();
        IRoom room = rooms[r][c].getRoom();
        // voisine au nord
        if (r > 0 && !net.canExit(room, Direction.NORTH)) {
            result.add(new Pos(r - 1, c));
        }
        // voisine à l'est
        if (c < cols - 1 && !net.canExit(room, Direction.EAST)) {
            result.add(new Pos(r, c + 1));
        }
        // voisine au sud
        if (r < rows - 1 && !net.canExit(room, Direction.SOUTH)) {
            result.add(new Pos(r + 1, c));
        }
        // voisine à l'ouest
        if (c > 0 && !net.canExit(room, Direction.WEST)) {
            result.add(new Pos(r, c - 1));
        }
        return result;
    }

    /**
     * Connecte la pièce en coordonnées p.
     */
    private void connect(Pos p) {
        rooms[p.row()][p.col()].setConnected();
    }

    /**
     * Retourne la liste des pièces voisines de p non connectées à
     *  l'intérieur du labyrinthe.
     */
    private List<Pos> getDisconnectedNeighbours(Pos p) {
        List<Pos> result = new ArrayList<>();
        int r = p.row();
        int c = p.col();
        // voisine au nord
        if ((r > 0) && (!rooms[r - 1][c].isConnected())) {
            result.add(new Pos(r - 1, c));
        }
        // voisine à l'est
        if ((c < cols - 1) && (!rooms[r][c + 1].isConnected())) {
            result.add(new Pos(r, c + 1));
        }
        // voisine au sud
        if ((r < rows - 1) && (!rooms[r + 1][c].isConnected())) {
            result.add(new Pos(r + 1, c));
        }
        // voisine à l'ouest
        if ((c > 0) && (!rooms[r][c - 1].isConnected())) {
            result.add(new Pos(r, c - 1));
        }
        return result;
    }

    /**
     * Creuse un passage entre les salles aux positions src et dest sur
     *  le labyrinthe.
     */
    private void dig(Pos src, Pos dest) {
        int sr = src.row();
        int sc = src.col();
        int dr = dest.row();
        int dc = dest.col();
        Direction dir = null;
        int deltaRow = dr - sr;
        if (deltaRow < 0) {
            dir = Direction.NORTH;
        } else if (deltaRow > 0) {
            dir = Direction.SOUTH;
        } else {
            dir = (dc - sc > 0) ? Direction.EAST : Direction.WEST;
        }
        IRoom s = rooms[sr][sc].getRoom();
        IRoom d = rooms[dr][dc].getRoom();
        IRoomNetwork net = RoomNetworkFactory.get();
        net.connect(s, dir, d);
    }
    
    // TYPES IMBRIQUÉS
    
    private static record Pos(int row, int col) {}
}

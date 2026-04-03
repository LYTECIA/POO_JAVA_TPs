package tp08.view;

import tp08.model.IRoom;
import tp08.model.IRoomNetwork;
import tp08.model.RoomNetworkFactory;
import tp08.util.Direction;

/**
 * Fabrique des descripteurs de bordures pour dessiner le labyrinthe.
 * Chaque case du labyrinthe possède 4 bordures de coin, 2 bordures de mur et
 *  2 bordures de plancher.
 * Les cases des bords externes du labyrinthe possèdent moins de bordures que
 *  cela.
 */
final class BorderFactory {

    // ATTRIBUTS DE CLASSE

    private static final String CORNER = "+";
    private static final String FLOOR = "---";
    private static final String WALL = "|";
    private static final String CROSSING = "~~~";

    // CONSTRUCTEURS

    static IDescriptor createCorner(MazeRoom ne, MazeRoom se,
            MazeRoom sw, MazeRoom nw) {
        return new AbstractDescriptor(ne, se, sw, nw) {
            @Override
            protected String fullVision() {
                return CORNER;
            }
            @Override
            protected String noVision() {
                return EMPTY1;
            }
        };
    }

    static IDescriptor createFloor(MazeRoom n, MazeRoom s) {
        return new AbstractDescriptor(n, s) {
            @Override
            protected String fullVision() {
                return isPath(Orientation.VERTICAL) ? EMPTY3 : FLOOR;
            }
            @Override
            protected String noVision() {
                return EMPTY3;
            }
        };
    }

    static IDescriptor createWall(MazeRoom e, MazeRoom w) {
        return new AbstractDescriptor(e, w) {
            @Override
            protected String fullVision() {
                return isPath(Orientation.HORIZONTAL) ? EMPTY1 : WALL;
            }
            @Override
            protected String noVision() {
                return EMPTY1;
            }
        };
    }

    static IDescriptor createEmpty() {
        return new IDescriptor() {
            @Override
            public String describe(boolean withLight) {
                return EMPTY3;
            }
        };
    }

    static IDescriptor createCrossing() {
        return new IDescriptor() {
            @Override
            public String describe(boolean withLight) {
                return CROSSING;
            }
        };
    }

    private BorderFactory() {
        // pas d'instanciation possible
    }

    // TYPES IMBRIQUES
    
    private enum Orientation { HORIZONTAL, VERTICAL }

    private abstract static class AbstractDescriptor implements IDescriptor {

        private final MazeRoom[] neighborhood;

        private AbstractDescriptor(MazeRoom... rooms) {
            neighborhood = rooms;
        }

        @Override
        public String describe(boolean withLight) {
            if (isExternalBorder() || neighborhoodVisited() || withLight) {
                return fullVision();
            } else {
                return noVision();
            }
        }

        /**
         * La représentation textuelle de la bordure quand on la voit.
         */
        protected abstract String fullVision();

        /**
         * La représentation textuelle de la bordure quand on ne la voit pas.
         */
        protected abstract String noVision();

        /**
         * Cette bordure est un bord externe du labyrinthe.
         */
        protected boolean isExternalBorder() {
            for (int i = 0; i < neighborhood.length; i++) {
                if (neighborhood[i] == null) {
                    return true;
                }
            }
            return false;
        }

        /**
         * Un joueur se promène dans une salle voisine de cette bordure.
         */
        protected boolean neighborhoodVisited() {
            for (int i = 0; i < neighborhood.length; i++) {
                if (neighborhood[i].isVisited()) {
                    return true;
                }
            }
            return false;
        }

        /**
         * Il y a une salle orientée vers <code>orientation</code> dotée d'une
         *  ouverture permettant de traverser cette bordure.
         * 
         * @pre
         *     orientation != null
         */
        protected boolean isPath(Orientation orientation) {
            assert orientation != null;
            
            if (isExternalBorder()) {
                return false;
            }
            
            IRoomNetwork net = RoomNetworkFactory.get();
            if (orientation == Orientation.VERTICAL) {
                IRoom n = neighborhood[0].getRoom();
                IRoom s = neighborhood[1].getRoom();
                return net.canExit(n, Direction.SOUTH)
                        || net.canExit(s, Direction.NORTH);
            } else {
                IRoom e = neighborhood[0].getRoom();
                IRoom w = neighborhood[1].getRoom();
                return net.canExit(e, Direction.WEST)
                        || net.canExit(w, Direction.EAST);
            }
        }
    }
}

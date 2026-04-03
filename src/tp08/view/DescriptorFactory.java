package tp08.view;

import tp08.model.IRoom;
import tp08.model.room.IMonsterRoom;

/**
 * Fabrique des descripteurs de salles pour dessiner le labyrinthe.
 * Chaque case du labyrinthe contient une salle entre ses 4 bordures.
 */
final class DescriptorFactory {
    
    // ATTRIBUTS DE CLASSE

    private static final String DOOM = "x";
    private static final String MAGIC = "!";
    private static final String EXIT = "S";
    
    private static final String INFINITY = "\u221e";
    
    // CONSTRUCTEURS

    static IDescriptor createDoom(MazeRoom r, Maze m, int i, int j) {
        return new AbstractDescriptor(r, m, i, j) {
            @Override
            protected String deepVision() {
                return EMPTY1 + DOOM + EMPTY1;
            }
        };
    }

    static IDescriptor createExit(MazeRoom r, Maze m, int i, int j) {
        return new AbstractDescriptor(r, m, i, j) {
            @Override
            protected String deepVision() {
                return EMPTY1 + EXIT + EMPTY1;
            }
            @Override
            protected String troubleVision() {
                return deepVision();
            }
        };
    }

    static IDescriptor createMagic(MazeRoom r, Maze m, int i, int j) {
        return new AbstractDescriptor(r, m, i, j) {
            @Override
            protected String deepVision() {
                return EMPTY1 + MAGIC + EMPTY1;
            }
            @Override
            protected String troubleVision() {
                return deepVision();
            }
        };
    }

    static IDescriptor createMonster(MazeRoom r, Maze m, int i, int j) {
        return new AbstractDescriptor(r, m, i, j) {
            @Override
            protected String deepVision() {
                int n = ((IMonsterRoom) room()).monstersNb();
                String res;
                if (n <= 9) {
                    res = String.valueOf(n);
                } else if (n <= 35) {
                    res = String.valueOf((char) ('a' + (n - 10)));
                } else {
                    res = INFINITY;
                }
                return EMPTY1 + res + EMPTY1;
            }
        };
    }

    static IDescriptor createNormal(MazeRoom r, Maze m, int i, int j) {
        return new AbstractDescriptor(r, m, i, j) {
            @Override
            protected String deepVision() {
                return isVisited() ? VISITED3 : EMPTY3;
            }
        };
    }

    private DescriptorFactory() {
        // pas d'instanciation possible
    }
    
    // TYPES IMBRIQUES

    private abstract static class AbstractDescriptor implements IDescriptor {
        private final MazeRoom room;
        private final int row;
        private final int col;
        private final Maze maze;

        /**
         * i/j est la ligne/colonne de la salle r du labyrinthe m.
         */
        private AbstractDescriptor(MazeRoom r, Maze m, int i, int j) {
            room = r;
            row = i;
            col = j;
            maze = m;
        }
        
        @Override
        public String describe(boolean withLight) {
            IDescriptor p = maze.getPlayerDescriptorFor(room);
            if (p != null) {
                return EMPTY1 + p.describe(withLight) + EMPTY1;
            } else {
                if (withLight || maze.powerPlayerAround(row, col)
                        || isVisited()) {
                    return deepVision();
                } else {
                    return troubleVision();
                }
            }
        }
        
        /**
         * Représentation lorsque la salle est éclairée.
         */
        protected abstract String deepVision();
        
        /**
         * Représentation lorsque la salle n'est pas éclairée.
         */
        protected String troubleVision() {
            return EMPTY3;
        }
        
        protected IRoom room() {
            return room.getRoom();
        }

        /**
         * La salle a déjà été visitée par le joueur.
         */
        protected boolean isVisited() {
            return room.isVisited();
        }
    }
}

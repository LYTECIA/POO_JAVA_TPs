package tp08.util;

/**
 * Enumère toutes les directions connues de Pyland :
 *     NORTH, EAST, SOUTH, WEST
 * @inv <pre>
 *     NORTH.opposite() == SOUTH
 *     EAST.opposite() == WEST
 *     SOUTH.opposite() == NORTH
 *     WEST.opposite() == EAST </pre>
 */
public enum Direction {
    NORTH, EAST, SOUTH, WEST;
    
    private static final Direction[] VALUES = Direction.values();
    
    /**
     * La direction opposée de this.
     */
    public Direction opposite() {
        return VALUES[(ordinal() + 2) % VALUES.length];
    }
}

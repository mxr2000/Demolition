package demolition.util;

import java.util.Random;

/**
 * The helper class for storing the directions
 */
public enum Direction {
    RIGHT("right"),
    LEFT("left"),
    UP("up"),
    NULL("null"),
    DOWN("down");
    private String value;
    Direction(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    /**
     * get the next direction clockwise
     * @return
     */
    public Direction next() {
        switch (this) {
            case RIGHT: return DOWN;
            case DOWN: return LEFT;
            case LEFT: return UP;
            case UP: return RIGHT;
        }
        return NULL;
    }
    private static final Random random = new Random(0);

    /** get a random direction
     *
     * @return
     */
    public static Direction generateRandomDirection() {
        switch (random.nextInt(4)) {
            case 0: return RIGHT;
            case 1: return LEFT;
            case 2: return UP;
            case 3: return DOWN;
        }
        return NULL;
    }
}

package demolition.util;

import java.util.Objects;

/**
 * The helper class which memories the x and y index in the grid
 */
public class Position {
    public final int x;
    public final int y;
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public Position left() {
        return new Position(x, y - 1);
    }
    public Position right() {
        return new Position(x, y + 1);
    }
    public Position up() {
        return new Position(x - 1, y);
    }
    public Position down() {
        return new Position(x + 1, y);
    }

    public boolean isRightOf(Position position) {
        return position.x == x && position.y < y;
    }
    public boolean isLeftOf(Position position) {
        return position.x == x && position.y > y;
    }
    public boolean isUpOf(Position position) {
        return position.y == y && position.x > x;
    }
    public boolean isDownOf(Position position) {
        return position.y == y && position.x < x;
    }
    public Position getPosition(Direction direction) {
        switch (direction) {
            case DOWN: return down();
            case UP: return up();
            case RIGHT: return right();
            case LEFT: return left();
        }
        return this;
    }
}

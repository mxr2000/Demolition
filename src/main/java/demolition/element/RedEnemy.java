package demolition.element;

import demolition.Grid;
import demolition.place.Base;
import demolition.util.Direction;

import java.util.List;

/**
 * Red enemy, which changes to a random direction when facing obstacles
 */
public class RedEnemy extends Enemy{
    public RedEnemy(Grid grid) {
        super(grid, "red");
        setMovingStrategy(position -> {
            Base targetBase = grid.getBase(position.getPosition(currentDirection));
            if (targetBase.canElementEnter(this)) {
                return currentDirection;
            }
            currentDirection = Direction.generateRandomDirection();
            return Direction.NULL;
        });
    }
}

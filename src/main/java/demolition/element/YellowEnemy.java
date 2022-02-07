package demolition.element;

import demolition.Grid;
import demolition.place.Base;
import demolition.util.Direction;
import demolition.util.EnemyMoveStrategy;
import demolition.util.Position;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Yellow enemy, which changes direction in clockwise
 */
public class YellowEnemy extends Enemy {
    public YellowEnemy(Grid grid) {
        super(grid, "yellow");
        setMovingStrategy(position -> {
            Base targetBase = grid.getBase(position.getPosition(currentDirection));
            if (targetBase.canElementEnter(this)) {
                return currentDirection;
            }
            currentDirection = currentDirection.next();
            return Direction.NULL;
        });
    }
}

package demolition.element;

import demolition.Grid;
import demolition.util.Direction;
import demolition.util.EnemyMoveStrategy;
import demolition.util.TimeOutHandler;

import java.util.List;

/**
 * The base class for the red enemy and the yellow enemy
 * registered events for changing the image and move in the grid
 */
public class Enemy extends Element{
    protected EnemyMoveStrategy strategy;
    protected Direction currentDirection = Direction.DOWN;
    protected String name;
    protected int currentIndex;
    public Enemy(Grid grid, String name) {
        super(grid);
        this.name = name;
        registerTimerEvent(grid.getTimer(), 12, new TimeOutHandler() {
            @Override
            public boolean onTimeOut() {
                currentIndex = (currentIndex + 1) % 4;
                return true;
            }

            @Override
            public int getTimeOut() {
                return 12;
            }
        });
        registerTimerEvent(grid.getTimer(), 60, new TimeOutHandler() {
            @Override
            public boolean onTimeOut() {
                grid.moveEnemy(Enemy.this, strategy);
                return true;
            }

            @Override
            public int getTimeOut() {
                return 60;
            }
        });
    }
    public void setMovingStrategy(EnemyMoveStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public String getCurrentImageKey() {
        return name + "_" + currentDirection + (currentIndex + 1);
    }

    @Override
    public boolean isHarmful() {
        return true;
    }
}

package demolition.element;

import demolition.Grid;
import demolition.util.Direction;
import demolition.util.TimeOutHandler;
import demolition.util.Timer;

/**
 * The class of the bomb guy, it registers a timeout event to regularly update its image key
 */
public class BombGuy extends Element{
    public static int FRAMES = 20;
    public static int FRAME_COUNT = 4;
    public BombGuy(Grid grid) {
        super(null);
        registerTimerEvent(grid.getTimer(), FRAMES, new TimeOutHandler() {
            @Override
            public boolean onTimeOut() {
                currentImageIndex = (currentImageIndex + 1) % FRAME_COUNT;
                return true;
            }
            @Override
            public int getTimeOut() {
                return FRAMES;
            }
        });
    }
    private Direction currentDirection = Direction.DOWN;
    private int currentImageIndex = 0;

    @Override
    public String getCurrentImageKey() {
        int index = currentImageIndex + 1;
        return currentDirection == Direction.DOWN ?
                "player" + index :
                "player_" + currentDirection + index;
    }

    /**
     * Change its facing direction to change the image key
     * @param direction the target direction it is going to change to
     */
    public void changeDirection(Direction direction) {
        currentDirection = direction;
    }
}

package demolition.element;

import demolition.Grid;
import demolition.util.TimeOutHandler;
import demolition.util.Timer;

/**
 * The element class for the bomb
 */
public class Bomb extends Element{
    public static int FRAMES = 100;
    public static int FRAME_COUNT = 8;
    private int currentFrameIndex = 0;
    public Bomb(Grid grid) {
        super(grid);
        Timer timer = grid.getTimer();
        registerTimerEvent(timer, FRAMES, () -> {
            currentFrameIndex++;
            if (currentFrameIndex == FRAME_COUNT - 1) {
                grid.explode(this);
                return false;
            }
            return true;
        });
    }

    @Override
    public String getCurrentImageKey() {
        return "bomb" + (currentFrameIndex + 1);
    }
}

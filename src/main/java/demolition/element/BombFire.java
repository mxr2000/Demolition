package demolition.element;

import demolition.Grid;

/**
 * The bomb fire class
 */
public class BombFire extends Element{
    private final String imageKey;
    public static int FRAMES = 30;
    public BombFire(Grid grid, String imageKey) {
        super(grid);
        this.imageKey = imageKey;
        registerTimerEvent(grid.getTimer(), FRAMES, () -> {
            grid.removeElement(this);
            return false;
        });
    }

    @Override
    public String getCurrentImageKey() {
        return imageKey;
    }

    @Override
    public boolean isHarmful() {
        return true;
    }
}

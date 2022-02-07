package demolition.place;

import demolition.element.Element;

/**
 * The class of the goal tile
 */
public class GoalTile extends Base{
    @Override
    public boolean canElementEnter(Element element) {
        return true;
    }

    @Override
    public boolean canBeDestroyed() {
        return false;
    }

    @Override
    public String getImageKey() {
        return "goal";
    }

    @Override
    public boolean isTarget() {
        return true;
    }
}

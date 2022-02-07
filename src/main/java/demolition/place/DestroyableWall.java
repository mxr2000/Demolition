package demolition.place;

import demolition.element.Element;

/**
 * The class for the broken wall
 */
public class DestroyableWall extends Base{
    @Override
    public boolean canElementEnter(Element element) {
        return false;
    }

    @Override
    public boolean canBeDestroyed() {
        return true;
    }

    @Override
    public String getImageKey() {
        return "broken";
    }
}

package demolition.place;

import demolition.element.Element;

/**
 * Nothing can get through
 */
public class SolidWall extends Base{
    @Override
    public boolean canElementEnter(Element element) {
        return false;
    }

    @Override
    public boolean canBeDestroyed() {
        return false;
    }

    @Override
    public String getImageKey() {
        return "solid";
    }
}

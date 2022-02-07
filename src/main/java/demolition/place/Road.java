package demolition.place;

import demolition.element.Element;

/**
 * The class of road which anything can walk on it
 */
public class Road extends Base{
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
        return "empty";
    }
}

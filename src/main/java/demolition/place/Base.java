package demolition.place;

import demolition.element.Element;

import java.util.List;

/**
 * The base class for all backgrounds
 */
public abstract class Base {
    /**
     * The element can enter road, cannot enter others
     * @param element the elemet that is going to enter
     * @return true if it is able to enter
     */
    public abstract boolean canElementEnter(Element element);

    /**
     * The broken wall can be destroyed by the bomb
     * @return
     */
    public abstract boolean canBeDestroyed();

    /**
     * what will happen when element enters here
     * @param element
     */
    public void onElementEnter(Element element) {}

    /**
     * Return the image key to draw
     * @return
     */
    public abstract String getImageKey();

    /**
     * Return if it is the target of this level
     * @return
     */
    public boolean isTarget() { return false; }
}

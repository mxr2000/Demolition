package demolition.util;

import demolition.element.Element;

/**
 * The timer interface which register all timeout events
 */
public interface Timer {
    void register(int interval, Element element, TimeOutHandler timeOutEvent);
}

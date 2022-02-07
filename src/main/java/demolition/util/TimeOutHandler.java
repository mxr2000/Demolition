package demolition.util;

/**
 * The handler when the given time passed
 */
public interface TimeOutHandler {
    /**
     * The handler when the time passed
     * @return true if the element continues to exist after the animation terminates
     */
    boolean onTimeOut();

    /**
     * return the timeout time
     * @return o for default
     */
    default int getTimeOut() {
        return 0;
    }
}

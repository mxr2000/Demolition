package demolition.view;

/**
 * For different conditions, give different ways to draw the canvas and handle the user's key stroke
 */
public interface ViewStrategy {
    /**
     * draw the canvas
     */
    void draw();

    /**
     * handle the key stroke
     * @param code the key code
     */
    void keyPressed(int code);
}

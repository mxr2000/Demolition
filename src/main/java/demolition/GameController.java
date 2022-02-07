package demolition;

import demolition.view.FailedStrategy;
import demolition.view.GameViewStrategy;
import demolition.view.ViewStrategy;
import demolition.view.WonViewStrategy;
import processing.core.PApplet;

/**
 * The controller class which accepts the draw or key press events from the applet and call the corresponding view strategy class to handle it
 */
public class GameController {
    private final PApplet pApplet;
    private ViewStrategy currentViewStrategy;
    public GameController(PApplet pApplet) {
        this.pApplet = pApplet;
        changeViewStrategy("running");
    }

    /**
     * Use the current strategy to draw the applet
     */
    public void draw() {
        currentViewStrategy.draw();
    }

    /**
     * Call the corresponding strategy to handle the key press event
     * @param code from the keyboard
     */
    public void keyPress(int code) {
        currentViewStrategy.keyPressed(code);
    }

    /**
     * Change to another strategy, for example, when the bomb guy reached the target in the final level, it changes to win strategy to show the win message
     * @param type the target strategy key
     */
    public void changeViewStrategy(String type) {
        System.out.println(type);
        switch (type) {
            case "running":
                currentViewStrategy = new GameViewStrategy(pApplet, this);
                break;
            case "failed":
                currentViewStrategy = new FailedStrategy(pApplet, this);
                break;
            case "won":
                currentViewStrategy = new WonViewStrategy(pApplet, this);
                break;
        }
    }


}

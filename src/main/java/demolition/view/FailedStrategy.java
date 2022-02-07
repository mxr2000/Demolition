package demolition.view;

import demolition.GameController;
import demolition.util.FontCollection;
import processing.core.PApplet;

/**
 * The view strategy class when  the player failed the game
 */
public class FailedStrategy implements ViewStrategy{
    @Override
    public void draw() {
        String text = "GAME OVER";
        pApplet.fill(255, 128, 0);
        pApplet.rect(0, 0, pApplet.width, pApplet.height);
        pApplet.fill(0, 0, 0);
        pApplet.textFont(FontCollection.font);
        int left = (pApplet.width - 32 * 9) / 2;
        pApplet.text(text, left, 32 * 6);
    }

    @Override
    public void keyPressed(int code) {
        gameController.changeViewStrategy("running");
    }

    public FailedStrategy(PApplet pApplet, GameController gameController) {
        this.pApplet = pApplet;
        this.gameController = gameController;
    }

    private final PApplet pApplet;
    private final GameController gameController;
}

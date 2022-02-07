package demolition.view;

import demolition.GameController;
import demolition.util.FontCollection;
import processing.core.PApplet;
import processing.core.PFont;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * The view strategy when the user won the game
 */
public class WonViewStrategy implements ViewStrategy{
    @Override
    public void draw() {
        String text = "YOU WIN";
        pApplet.fill(255, 128, 0);
        pApplet.rect(0, 0, pApplet.width, pApplet.height);
        pApplet.fill(0, 0, 0);
        pApplet.textFont(FontCollection.font);
        int left = (pApplet.width - 32 * 7) / 2;
        pApplet.text(text, left, 32 * 6);
    }

    @Override
    public void keyPressed(int code) {

    }

    private final PApplet pApplet;
    private final GameController gameController;
    public WonViewStrategy(PApplet pApplet, GameController gameController) {
        this.pApplet = pApplet;
        this.gameController = gameController;
    }
}

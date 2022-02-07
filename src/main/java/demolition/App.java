package demolition;

import demolition.util.FontCollection;
import demolition.util.ImageCollection;
import demolition.view.ViewStrategy;
import processing.core.PApplet;
import processing.event.KeyEvent;

import java.util.HashMap;
import java.util.Map;


/**
 * The main Entry of the program
 */
public class App extends PApplet {
    public static final int WIDTH = 480;
    public static final int HEIGHT = 480;

    public static final int FPS = 60;
    private final GameController gameController;
    private final Map<String, ViewStrategy> strategyMap = new HashMap<>();


    @Override
    public void keyPressed(KeyEvent event) {
        gameController.keyPress(event.getKeyCode());
        System.out.println(event.getKeyCode());
    }

    @Override
    public void draw() {
        gameController.draw();
    }

    public App() {
        this.gameController = new GameController(this);
    }

    public static void main(String[] args) {
        PApplet.main("demolition.App");
    }

    @Override
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * init the settings, including the fps, load images, fonts
     */
    @Override
    public void setup() {
        frameRate(FPS);
        ImageCollection.initImages(this);
        FontCollection.font = createFont("PressStart2P-Regular.ttf", 32);
        FontCollection.digitFont = createFont("PressStart2P-Regular.ttf", 20);
    }
}

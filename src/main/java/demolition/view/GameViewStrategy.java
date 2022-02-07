package demolition.view;

import demolition.GameController;
import demolition.Grid;
import demolition.GridFactory;
import demolition.element.Element;
import demolition.place.Base;
import demolition.util.*;
import processing.core.PApplet;
import processing.data.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * The view strategy class for the running time of the game
 */
public class GameViewStrategy implements ViewStrategy{
    private final PApplet pApplet;
    private final GameController gameController;
    private Grid grid;
    private int lifeCount;
    private int currentLevel = 0;
    private int remainedSeconds = 0;
    private GameConfiguration gameConfiguration;
    public GameViewStrategy(PApplet pApplet, GameController gameController) {
        this.pApplet = pApplet;
        this.gameController = gameController;
        init();
    }

    /**
     * load the config.json file and init the settings into the variable gameConfiguration and remainedSeconds and lifeCount
     */
    private void init() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("build/resources/main/config.json"));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            String ls = System.getProperty("line.separator");
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            reader.close();

            String content = stringBuilder.toString();
            gameConfiguration = new GameConfiguration(JSONObject.parse(content));
        } catch (Exception e) {
            e.printStackTrace();
        }
        lifeCount = gameConfiguration.getLifeCount();
        startNewGame();

    }

    /**
     *
     * @return if all the levels are passed, return false indicating the player won the game, or start the next level
     */
    private boolean startNewGame() {
        if (currentLevel == gameConfiguration.getGridSettings().size()) {
            return false;
        }
        GameConfiguration.GridSetting gridSetting = gameConfiguration.getGridSettings().get(currentLevel);
        String fileName = gridSetting.getFilename();
        grid = (new GridFactory(fileName)).getGrid();
        remainedSeconds = gridSetting.getTime() * 60;
        return true;
    }

    /**
     * Loop for the game running, incluing calculating the remained time, handle all registered timeout events, and draw the
     * background, elements, and the information panel, which includes the remained lives and time.
     */
    @Override
    public void draw() {
        drawInfoPanel();
        handleTimeOut();
        handleRemainedTime();
        int itemWidth, itemHeight;
        itemHeight = itemWidth = 32;
        int heightOffset = 480 - grid.getRowCount() * 32;
        for (int i = 0; i < grid.getRowCount(); i ++) {
            for (int j = 0; j < grid.getColumnCount(); j++) {
                Position position = new Position(i, j);
                Base place = grid.getBase(position);
                pApplet.image(ImageCollection.getImage(place.getImageKey()), itemWidth * j, heightOffset + itemHeight * i, 32, 32);
            }
        }
        for (Element element : grid.getElements()) {
            Position position = grid.getElementPosition(element);
            pApplet.image(ImageCollection.getImage(element.getCurrentImageKey()),
                    itemWidth * position.y,
                    itemHeight * position.x + heightOffset,
                    itemWidth,
                    itemHeight);
        }
        switch (grid.getGameResult()) {
            case PASSED:
                currentLevel++;
                if (!startNewGame()) {
                    gameController.changeViewStrategy("won");
                }
                break;
            case DEAD:
                lifeCount--;
                if (lifeCount == 0) {
                    gameController.changeViewStrategy("failed");
                } else {
                    startNewGame();
                }
                break;
        }
    }


    @Override
    public void keyPressed(int code) {
        switch (code) {
            case 37: grid.tryMoveBombGuy(Direction.LEFT);break;
            case 39: grid.tryMoveBombGuy(Direction.RIGHT);break;
            case 38: grid.tryMoveBombGuy(Direction.UP);break;
            case 40: grid.tryMoveBombGuy(Direction.DOWN);break;
            case 32: grid.putBomb();break;
        }
    }


    private void handleTimeOut() {
        Map<TimeOutHandler, Integer> handlerMap = grid.getHandlerMap();
        List<TimeOutHandler> removed = new ArrayList<>();
        for (TimeOutHandler handler : handlerMap.keySet()) {
            if (!handlerMap.containsKey(handler)) {
                continue;
            }
            int value = handlerMap.get(handler);
            if (value == 0) {
                if (handler.onTimeOut()) {
                    handlerMap.put(handler, handler.getTimeOut());
                } else {
                    removed.add(handler);
                }
            } else {
                handlerMap.put(handler, value - 1);
            }
        }
        removed.forEach(handler -> grid.removeElementByTimeoutHandler(handler));
    }

    private void drawInfoPanel() {
        pApplet.fill(255, 128, 0);
        pApplet.rect(0, 0, 32 * 15, 32 * 2);
        pApplet.image(ImageCollection.getImage("player"), 32 * 4, 16, 32, 32);
        pApplet.image(ImageCollection.getImage("clock"), 32 * 8, 16, 32, 32);
        pApplet.textFont(FontCollection.digitFont);
        pApplet.fill(0, 0, 0);
        pApplet.text("" + lifeCount, 32 * 5 + 8, 44);
        pApplet.text("" + (remainedSeconds / 60), 32 * 9 + 8, 44);
    }

    private void handleRemainedTime() {
        remainedSeconds--;
        if (remainedSeconds == 0) {
            lifeCount--;
            if (lifeCount == 0) {
                gameController.changeViewStrategy("failed");
            } else {
                startNewGame();
            }
        }
    }


}

package demolition.util;

import processing.core.PApplet;
import processing.core.PImage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The helper function which loads in all the needed images, and return image ny the key
 */
public class ImageCollection {
    private static final Map<String, PImage> images = new HashMap<>();
    public static PImage getImage(String key) {
        return images.get(key);
    }

    public static String EXPLOSION_HORIZONTAL = "horizontal";
    public static String EXPLOSION_VERTICAL = "vertical";
    public static String EXPLOSION_CENTER = "centre";
    public static String EXPLOSION_RIGHT = "end_right";
    public static String EXPLOSION_LEFT = "end_left";
    public static String EXPLOSION_UP = "end_top";
    public static String EXPLOSION_DOWN = "end_bottom";

    public static String DIR_BOMB = "bomb";
    public static String DIR_BROKEN = "broken";
    public static String DIR_EMPTY = "empty";
    public static String DIR_EXPLOSION = "explosion";
    public static String DIR_GOAL = "goal";
    public static String DIR_ICON = "icons";
    public static String DIR_PLAYER = "player";
    public static String DIR_RED_ENEMY = "red_enemy";
    public static String DIR_YELLOW_ENEMY = "yellow_enemy";
    public static String DIR_WALL = "wall";

    public static void initImages(PApplet pApplet){
        putImage(pApplet, DIR_EXPLOSION, EXPLOSION_CENTER);
        putImage(pApplet, DIR_EXPLOSION, EXPLOSION_DOWN);
        putImage(pApplet, DIR_EXPLOSION, EXPLOSION_LEFT);
        putImage(pApplet, DIR_EXPLOSION, EXPLOSION_RIGHT);
        putImage(pApplet, DIR_EXPLOSION, EXPLOSION_UP);
        putImage(pApplet, DIR_EXPLOSION, EXPLOSION_HORIZONTAL);
        putImage(pApplet, DIR_EXPLOSION, EXPLOSION_VERTICAL);

        putImage(pApplet, DIR_BROKEN, "broken");
        putImage(pApplet, DIR_EMPTY, "empty");
        putImage(pApplet, DIR_GOAL, "goal");
        putImage(pApplet, DIR_WALL, "solid");

        List<String> directions = Arrays.asList("down", "left", "right", "up");
        putImage(pApplet, DIR_RED_ENEMY, "red", directions, 4);
        putImage(pApplet, DIR_YELLOW_ENEMY, "yellow", directions, 4);

        putImage(pApplet, DIR_BOMB, "bomb", 8);

        putImage(pApplet, DIR_ICON, "clock");
        putImage(pApplet, DIR_ICON, "player");

        putImage(pApplet, DIR_PLAYER, "player", 4);
        putImage(pApplet, DIR_PLAYER, "player_left", 4);
        putImage(pApplet, DIR_PLAYER, "player_right", 4);
        putImage(pApplet, DIR_PLAYER, "player_up", 4);
    }

    public static void putImage(PApplet pApplet, String dir, String name) {
        images.put(name, pApplet.loadImage(dir + "/" + name + ".png"));
    }
    public static void putImage(PApplet pApplet, String dir, String name, List<String> directions, int count) {
        for (String direction : directions) {
            for(int i = 1; i <= count; i++) {
                String key = name + "_" + direction + i;
                images.put(key, pApplet.loadImage(dir + "/" + key + ".png"));
            }
        }
    }
    public static void putImage(PApplet pApplet, String dir, String name, int count) {
        for(int i = 1; i <= count; i++) {
            String key = name + i;
            images.put(key, pApplet.loadImage(dir + "/" + key + ".png"));
        }
    }
}

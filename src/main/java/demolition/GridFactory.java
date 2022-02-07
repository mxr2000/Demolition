package demolition;

import demolition.element.RedEnemy;
import demolition.element.YellowEnemy;
import demolition.place.DestroyableWall;
import demolition.place.GoalTile;
import demolition.place.SolidWall;
import demolition.util.Position;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * The factory class to create a grid, which is for dividing the responsibilities of the Grid class and its creation
 */
public class GridFactory {
    private Grid grid;

    /**
     * Read a map file and create a grid
     * @param filename the filename of the map file
     */
    public GridFactory(String filename) {
        List<String> inputs = new ArrayList<>();
        int rowCount = 0;
        int columnCount = 0;
        try {
            System.out.println(filename);
            BufferedReader reader = new BufferedReader(new FileReader("build/resources/main/map/" + filename));
            String input;

            while ((input = reader.readLine()) != null) {
                inputs.add(input);
                rowCount++;
                columnCount = input.length();
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        grid = new Grid(rowCount, columnCount);
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                setPosition(i, j, inputs.get(i).charAt(j));
            }
        }
    }

    /**
     * Set the background or the element in the specified place
     * @param i the first index of the position in the grid
     * @param j the second index of the position in the grid
     * @param ch the char from the file which is transformed int a kind of background or element.
     */
    public void setPosition(int i, int j, char ch) {
        Position position = new Position(i, j);
        switch (ch) {
            case 'W': grid.setBase(position, new SolidWall());break;
            case 'B': grid.setBase(position, new DestroyableWall());break;
            case 'G': grid.setBase(position, new GoalTile());break;
            case 'R': grid.putElement(new RedEnemy(grid), position);break;
            case 'Y': grid.putElement(new YellowEnemy(grid), position);break;
            case 'P': grid.initPlayer(position);
        }
    }

    public Grid getGrid() {
        return grid;
    }

}

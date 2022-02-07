package demolition;

import demolition.element.BombGuy;
import demolition.util.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test if a grid is correctly generated from the grid file
 */
public class GridTest {
    @Test
    void shouldCreateGrid() {
        GridFactory gridFactory = new GridFactory("level1.txt");
        Grid grid = gridFactory.getGrid();
        Assertions.assertEquals(grid.getRowCount(), 13);
        Assertions.assertEquals(grid.getColumnCount(), 15);
        Assertions.assertEquals(grid.getBombGuy().getClass(), BombGuy.class);
        Assertions.assertEquals(grid.getBase(new Position(0, 0)).getImageKey(), "empty");
        Assertions.assertEquals(grid.getBase(new Position(1, 13)).getImageKey(), "solid");
        Assertions.assertEquals(grid.getBase(new Position(11, 13)).getImageKey(), "goal");
    }
}

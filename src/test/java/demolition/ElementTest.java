package demolition;

import demolition.element.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * test if elements bahave normally
 */
public class ElementTest {
    @Test
    void shouldEnemyBehave() {
        Grid grid = new Grid(0, 0);
        Enemy red = new RedEnemy(grid);
        Enemy yellow = new YellowEnemy(grid);
        Assertions.assertEquals(red.getCurrentImageKey(), "red_right1");
        Assertions.assertEquals(yellow.getCurrentImageKey(), "yellow_right1");
        Assertions.assertEquals(red.isHarmful(), true);
        Assertions.assertEquals(yellow.isHarmful(), true);

    }
    @Test
    void shouldBombBehave() {
        Grid grid = new Grid(0, 0);
        Bomb bomb = new Bomb(grid);
        Assertions.assertEquals(bomb.getCurrentImageKey(), "bomb1");
        Assertions.assertEquals(bomb.isHarmful(), false);
    }
    @Test
    void shouldBombGuyBehave() {
        Grid grid = new Grid(0, 0);
        BombGuy bombGuy = new BombGuy(grid);
        Assertions.assertEquals(bombGuy.getCurrentImageKey(), "player1");
        Assertions.assertEquals(bombGuy.isHarmful(), false);
    }
}

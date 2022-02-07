package demolition;

import demolition.util.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/** check if position class bahave normally
 *
 */
public class PositionTest {
    @Test
    void shouldPositionBahaveNormally(){
        Position center = new Position(1, 1);
        Position left = new Position(1, 0);
        Position right = new Position(1, 2);
        Position up = new Position(0, 1);
        Position down = new Position(2, 1);
        Assertions.assertEquals(center, new Position(1, 1));
        Assertions.assertEquals(down.isDownOf(center), true);
        Assertions.assertEquals(up.isUpOf(center), true);
        Assertions.assertEquals(right.isRightOf(center), true);
        Assertions.assertEquals(left.isLeftOf(center), true);
    }

}

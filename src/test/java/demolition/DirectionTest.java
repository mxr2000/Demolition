package demolition;

import demolition.util.Direction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * test the helper class Direction works as expecetd
 */
public class DirectionTest {
    @Test
    void shouldDirectionBeahave() {
        Direction direction = Direction.RIGHT;
        Direction next = direction.next();
        Assertions.assertEquals(next, Direction.DOWN);
        next = next.next();
        Assertions.assertEquals(next, Direction.LEFT);
        next = next.next();
        Assertions.assertEquals(next, Direction.UP);
        next = next.next();
        Assertions.assertEquals(next, Direction.RIGHT);
    }
}

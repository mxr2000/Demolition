package demolition.util;

import demolition.place.Base;

/**
 * The strategy when an enemy reached an obstacle
 */
public interface EnemyMoveStrategy {
    Direction moveAndMayChangeDirection(Position position);
}

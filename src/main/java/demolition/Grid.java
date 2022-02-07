package demolition;

import demolition.element.Bomb;
import demolition.element.BombFire;
import demolition.element.BombGuy;
import demolition.element.Element;
import demolition.place.Base;
import demolition.place.GoalTile;
import demolition.place.Road;
import demolition.util.*;
import demolition.util.Timer;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Handler;

/**
 * The class to save the game states in one level of a game
 */
public class Grid {
    private final Map<Position, List<Element>> elements = new HashMap<>();
    private final Map<Element, Position> positionMap = new HashMap<>();
    private final Base[][] bases;
    private Position bombGuyPosition;
    private int rowCount;
    private int columnCount;
    private GameResult gameResult = GameResult.RUNNING;

    /**
     * Init the grid
     * @param rowCount the count of rows
     * @param columnCount the count of columns
     */
    public Grid(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        bases = new Base[rowCount][columnCount];
        for(int i = 0; i < rowCount; i++) {
            for(int j = 0; j < columnCount; j++) {
                elements.put(new Position(i, j), new ArrayList<>());
                bases[i][j] = new Road();
            }
        }
    }

    public Set<Element> getElements() {
        return positionMap.keySet();
    }

    public Position getElementPosition(Element element) {
        return positionMap.get(element);
    }

    public void setBase(Position position, Base base) {
        bases[position.x][position.y] = base;
    }

    /**
     * If the bomb guy could move to the direction, then move, else simply change the direction
     * @param direction the target direction
     */
    public void tryMoveBombGuy(Direction direction) {
        Position position = bombGuyPosition.getPosition(direction);
        Base base = getBase(position);
        if (base.canElementEnter(getBombGuy())) {
            moveBombGuy(direction);
            onBombGuyEntered();
        }
        getBombGuy().changeDirection(direction);
    }

    /**
     * Move the bomb guy to a target direction
     * @param direction
     */
    public void moveBombGuy(Direction direction) {
        Position newPosition = bombGuyPosition.getPosition(direction);
        moveElement(getBombGuy(), newPosition);
        bombGuyPosition = newPosition;
    }

    /**
     * Called when the bomb guy enters a piece of background
     */
    protected void onBombGuyEntered() {
        elements.get(bombGuyPosition).forEach(e -> {
            if (e.isHarmful()) {
                gameResult = GameResult.DEAD;
            } else if (getBase(bombGuyPosition).isTarget()) {
                gameResult = GameResult.PASSED;
            }
        });
    }

    public GameResult getGameResult() {
        return gameResult;
    }

    public void initPlayer(Position position) {
        BombGuy bombGuy = new BombGuy(this);
        putElement(bombGuy, position);
        bombGuyPosition = position;
    }

    /**
     * Move an enemy towards a direction
     * @param element the element of the enemy
     * @param strategy the moving strategy, red ones and yellow ones have different strategies when thay face obstacles.
     */
    public void moveEnemy(Element element, EnemyMoveStrategy strategy) {
        Direction direction = strategy.moveAndMayChangeDirection(getElementPosition(element));
        Position newPosition = positionMap.get(element).getPosition(direction);
        moveElement(element, newPosition);
    }

    /**
     * Move a element to a new position
     * @param element
     * @param newPosition
     */
    protected void moveElement(Element element, Position newPosition) {
        Position oldPosition = positionMap.get(element);
        if (oldPosition == null) {
            return;
        }
        List<Element> oldElements = elements.computeIfAbsent(oldPosition, k -> new ArrayList<>());
        oldElements.remove(element);
        positionMap.remove(element);
        positionMap.put(element, newPosition);
        List<Element> newElements = elements.computeIfAbsent(newPosition, k -> new ArrayList<>());
        newElements.add(element);

    }

    /**
     * Remove the element from the grid
     * @param element
     */
    public void removeElement(Element element) {
        Position position = positionMap.get(element);
        positionMap.remove(element);
        if (position != null) {
            elements.get(position).remove(element);
        }
        removeRegisteredEventHandlers(element);
    }

    /**
     * Find the element which owns the handler, and removes it
     * @param handler
     */
    public void removeElementByTimeoutHandler(TimeOutHandler handler) {
        Element element = null;
        for (Element e : elementHandlersMap.keySet()) {
            if (elementHandlersMap.get(e).contains(handler)) {
                element = e;
                break;
            }
        }
        if (element != null) {
            removeElement(element);
        }
    }

    /**
     * Put an element in a certain position
     * @param element
     * @param position
     */
    public void putElement(Element element, Position position) {
        positionMap.put(element, position);
        elements.get(position).add(element);
    }

    /**
     * Get the bomb guy
     * @return
     */
    protected BombGuy getBombGuy() {
        final Element[] element = new Element[1];
        elements.get(bombGuyPosition).stream().forEach(e -> {
            if (e instanceof BombGuy) {
                element[0] = e;
            }
        });
        return (BombGuy) element[0];
    }

    /**
     * Put a bomb in the pace of the guy
     */
    public void putBomb() {
        Bomb bomb = new Bomb(this);
        elements.get(bombGuyPosition).add(bomb);
        positionMap.put(bomb, bombGuyPosition);
    }

    /**
     * After the bomb explodes, put the dire on anywhere affected
     * @param position where the bomb explodes
     * @param affectedPositions anywhere affected by the bomb
     */
    public void putBombFire(Position position, List<Position> affectedPositions) {
        putElement(new BombFire(this, "centre"), position);
        affectedPositions.forEach(p -> {
            if (p.isRightOf(position)) {
                if (!affectedPositions.contains(p.right())) {
                    putElement(new BombFire(this, ImageCollection.EXPLOSION_RIGHT), p);
                } else {
                    putElement(new BombFire(this, ImageCollection.EXPLOSION_HORIZONTAL), p);
                }
            } else if (p.isLeftOf(position)) {
                if (!affectedPositions.contains(p.left())) {
                    putElement(new BombFire(this, ImageCollection.EXPLOSION_LEFT), p);
                } else {
                    putElement(new BombFire(this, ImageCollection.EXPLOSION_HORIZONTAL), p);
                }
            } else if (p.isUpOf(position)) {
                if (!affectedPositions.contains(p.up())) {
                    putElement(new BombFire(this, ImageCollection.EXPLOSION_UP), p);
                } else {
                    putElement(new BombFire(this, ImageCollection.EXPLOSION_VERTICAL), p);
                }
            } else if (p.isDownOf(position)) {
                if (!affectedPositions.contains(p.down())) {
                    putElement(new BombFire(this, ImageCollection.EXPLOSION_DOWN), p);
                } else {
                    putElement(new BombFire(this, ImageCollection.EXPLOSION_VERTICAL), p);
                }
            } else if(p == position) {
                putElement(new BombFire(this, ImageCollection.EXPLOSION_CENTER), p);
            }
        });
    }

    private final Map<TimeOutHandler, Integer> handlerMap = new ConcurrentHashMap<>();
    private final Map<Element, List<TimeOutHandler>> elementHandlersMap = new ConcurrentHashMap<>();
    private final Timer timer = (interval, element, handler) -> {
        handlerMap.put(handler, interval);
        elementHandlersMap.computeIfAbsent(element, k -> new ArrayList<>());
        elementHandlersMap.get(element).add(handler);
    };

    public Map<TimeOutHandler, Integer> getHandlerMap() {
        return handlerMap;
    }

    public Timer getTimer() {
        return timer;
    }

    /**
     * Explode the bomb and eliminate everything nearby
     * @param bomb the bomb element
     */
    public void explode(Element bomb) {
        Position position = positionMap.get(bomb);
        elements.get(position).remove(bomb);
        List<Position> affectedPositions = getAffectedPositions(position);
        affectedPositions.forEach(p -> {
            Base base = getBase(p);
            if (base.canBeDestroyed()) {
                replaceBaseWithRoad(p);
            }
            List<Element> elements = this.elements.get(p);
            elements.forEach(e -> {
                e.onBombed();
                positionMap.remove(e);
                removeRegisteredEventHandlers(e);
            });
            elements.clear();

        });
        putBombFire(position, affectedPositions);
        onBombGuyEntered();
    }

    /**
     * After an element is removed from the grid, its registered event handlers should also be removed
     * @param element
     */
    protected void removeRegisteredEventHandlers(Element element) {
        List<TimeOutHandler> handlers = elementHandlersMap.get(element);
        if (handlers != null) {
            handlers.forEach(handlerMap::remove);
        }
        elementHandlersMap.remove(element);
    }

    protected List<Position> getAffectedPositions(Position position) {
        List<Position> positions = new ArrayList<>();
        positions.add(position);
        int range = 2;
        Position current = position;
        BombGuy bombGuy = getBombGuy();
        for (int i = 0; i < range; i++) {
            current = current.right();
            if (getBase(current).canElementEnter(bombGuy)) {
                positions.add(current);
            } else if (getBase(current).canBeDestroyed()) {
                positions.add(current);
                break;
            } else {
                break;
            }
        }
        current = position;
        for (int i = 0; i < range; i++) {
            current = current.left();
            if (getBase(current).canElementEnter(bombGuy)) {
                positions.add(current);
            } else if (getBase(current).canBeDestroyed()) {
                positions.add(current);
                break;
            } else {
                break;
            }
        }
        current = position;
        for (int i = 0; i < range; i++) {
            current = current.up();
            if (getBase(current).canElementEnter(bombGuy)) {
                positions.add(current);
            } else if (getBase(current).canBeDestroyed()) {
                positions.add(current);
                break;
            } else {
                break;
            }
        }
        current = position;
        for (int i = 0; i < range; i++) {
            current = current.down();
            if (getBase(current).canElementEnter(bombGuy)) {
                positions.add(current);
            } else if (getBase(current).canBeDestroyed()) {
                positions.add(current);
                break;
            } else {
                break;
            }
        }
        return positions;
    }

    /**
     * Get the background according to its position
     * @param position
     * @return
     */
    public Base getBase(Position position) {
        return bases[position.x][position.y];
    }

    /**
     * After a broken wall is destroyed by the bomb, replace it with road
     * @param position
     */
    protected void replaceBaseWithRoad(Position position) {
        bases[position.x][position.y] = new Road();
    }

    public int getColumnCount() {
        return columnCount;
    }

    public int getRowCount() {
        return rowCount;
    }
}

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SnakeGame extends AudGameWindow{
    private int score = 0;
    private final int width;
    private final int height;
    private long lastSnakeUpdate;

    private int applesCreated = 0;
    private Apple apple;

    public static final int SQUARE_SIZE = 16;
    public static final int STEP_TIME = 100;

    private final Snake snake;

    private final List<GameItem> wall = new ArrayList<>();

    private final Grid grid;

    private final boolean TOGGLE_GRID = true; // TOGGLE GRID

    @Override
    public void paintGame(AudGraphics g) {
        g.setColor(AudColor.WHITE);
        g.fillRect(0, 0, getGameAreaWidth(), getGameAreaHeight());

        snake.draw(g);

        for (GameItem gi: wall) {
            gi.draw(g);
        }

        apple.draw(g);

        if (TOGGLE_GRID) grid.draw(g);
    }

    @Override
    public void handleInput(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_DOWN:
                snake.setNextDirection(Snake.Direction.DOWN);
                break;

            case KeyEvent.VK_UP:
                snake.setNextDirection(Snake.Direction.UP);
                break;

            case KeyEvent.VK_RIGHT:
                snake.setNextDirection(Snake.Direction.RIGHT);
                break;

            case KeyEvent.VK_LEFT:
                snake.setNextDirection(Snake.Direction.LEFT);
                break;

            default:
                break;
        }
    }

    @Override
    public void updateGame(long time) {
        for (int i = 0; i < (time - lastSnakeUpdate) / STEP_TIME; i++) {
            snake.step();
        }

        checkCollisions();

        lastSnakeUpdate = time;
    }

    public static void main(String[] args) {
        SnakeGame this_ = new SnakeGame();
        this_.start();
    }

    public SnakeGame() {
        super(STEP_TIME);

        setTitle("Snake – Score: " + score);

        width = getGameAreaWidth() / SQUARE_SIZE;
        setGameAreaWidth(SQUARE_SIZE * width);

        height = getGameAreaHeight() / SQUARE_SIZE;
        setGameAreaHeight(SQUARE_SIZE * height);

        System.out.println("Width: " + width);
        System.out.println("Height: " + height);

        snake = new Snake(1, new Point(width / 2, height / 2));

        lastSnakeUpdate = System.currentTimeMillis();

        for (int i = 0; i < width; i++) {
            wall.add(new Brick(i, 0));
            wall.add(new Brick(i, height-1));
        }

        for (int i = 1; i < height-1; i++) {
            wall.add(new Brick(0, i));
            wall.add(new Brick(width-1, i));
        }

        grid = new Grid(width, height);

        createNewApple();
    }

    private void checkCollisions () {
        boolean collides = false;

        for (GameItem gameItem : wall) {
            if (snake.collidesWith(gameItem)) {
                collides = true;
                break;
            }
        }
        if (snake.collidesWithSelf()) collides = true;

        if (collides) {
            stop();
            showDialog("You died! Score: " + score);
            return;
        }

        if (snake.collidesWith(apple)) {
            snake.grow();
            score += apple.getValue();
            createNewApple();
            setTitle("Snake – Score: " + score);
        }
    }

    private void createNewApple() {
        Random random = new Random();
        int x = Math.round(random.nextInt(width-2) + 1);
        int y = Math.round(random.nextInt(height-2) + 1);

        while (snake.collidesWith(x, y)) {
            x = Math.round(random.nextInt(width-2) + 1);
            y = Math.round(random.nextInt(height-2) + 1);
        }

        apple = new Apple(x, y, applesCreated + 1);
        applesCreated++;
    }
}

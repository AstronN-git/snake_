import java.util.ArrayList;
import java.util.List;

public class SnakeGame extends AudGameWindow{
    private int score = 0;
    private int width;
    private int height;
    private long lastSnakeUpdate;


    public static final int SQUARE_SIZE = 16;
    public static final int STEP_TIME = 500;

    private Snake snake;

    private List<GameItem> wall = new ArrayList<>();

    @Override
    public void paintGame(AudGraphics g) {
        g.setColor(AudColor.WHITE);
        g.fillRect(0, 0, getGameAreaWidth(), getGameAreaHeight());

        snake.draw(g);

        for (GameItem gi: wall) {
            gi.draw(g);
        }
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
        long stepsTime = time - lastSnakeUpdate;
        for (int i = 0; i < (time - lastSnakeUpdate) / STEP_TIME; i++) {
            snake.step();
        }

        lastSnakeUpdate = time;
    }

    public static void main(String[] args) {
        SnakeGame this_ = new SnakeGame();
        this_.start();
    }

    public SnakeGame() {
        super(STEP_TIME);

        setTitle("AuD-Snake – Score: " + score);

        width = (int)getGameAreaWidth() / SQUARE_SIZE;
        setGameAreaWidth(SQUARE_SIZE * width);

        height = (int) (getGameAreaHeight() / SQUARE_SIZE);
        setGameAreaHeight(SQUARE_SIZE * height);

        System.out.println("Width: " + width);
        System.out.println("Height: " + height);

        snake = new Snake(1, new Point((int) (width / 2), (int) (height / 2)));

        lastSnakeUpdate = System.currentTimeMillis();

        for (int i = 0; i < width; i++) {
            wall.add(new Brick(i, 0));
            wall.add(new Brick(i, height-1));
        }

        for (int i = 1; i < height-1; i++) {
            wall.add(new Brick(0, i));
            wall.add(new Brick(width-1, i));
        }
    }

    private boolean checkCollisions () {
        for (GameItem gameItem : wall) {
            if (snake.collidesWith(gameItem)) return true;
        }
        if (snake.collidesWithSelf()) return true;
        return false;
    }
}

public class Brick extends GameItem{
    private final AudColor color = AudColor.lightGray;

    public Brick(int x, int y) {
        super(x, y);
    }

    public void draw(AudGraphics g) {
        g.setColor(color);
        g.fillRect(pos.getX() * SnakeGame.SQUARE_SIZE, pos.getY() * SnakeGame.SQUARE_SIZE, SnakeGame.SQUARE_SIZE, SnakeGame.SQUARE_SIZE);
    }
}

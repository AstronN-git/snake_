public class Apple extends GameItem{

    private final AudColor color = AudColor.red;
    private final int Value;

    @Override
    public void draw(AudGraphics g) {
        g.setColor(color);
        g.fillRect(pos.getX() * SnakeGame.SQUARE_SIZE, pos.getY() * SnakeGame.SQUARE_SIZE, SnakeGame.SQUARE_SIZE, SnakeGame.SQUARE_SIZE);
    }

    public int getValue() {
        return Value;
    }

    public Apple(int x, int y, int value) {
        super (x, y);
        Value = value;
    }
}

public class Grid {
    private final Point size;
    private final AudColor color = AudColor.gray;

    public Grid(int x, int y) {
        size = new Point(x, y);
    }

    public void draw(AudGraphics g) {
        g.setColor(color);
        for (int i = 0; i < size.getX(); i++) {
            for (int j = 0; j < size.getY(); j++) {
                g.drawRect(i * SnakeGame.SQUARE_SIZE, j * SnakeGame.SQUARE_SIZE, SnakeGame.SQUARE_SIZE, SnakeGame.SQUARE_SIZE);
            }
        }
    }
}

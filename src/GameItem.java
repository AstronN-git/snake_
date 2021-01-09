public abstract class GameItem {
    protected Point pos;

    public GameItem (int x, int y) {
        pos = new Point(x, y);
    }

    public int getX () {
        return pos.getX();
    }

    public int getY () {
        return pos.getY();
    }

    public abstract void draw(AudGraphics g);
}

import java.util.ArrayList;
import java.util.List;

public class Snake {
    private         List<Point> points;

    private final   AudColor    color           = AudColor.blue;
    private final   AudColor    headColor       = AudColor.green;
    private         Direction   nextDirection   = Direction.RIGHT;
    private         Direction   lastDirection   = Direction.RIGHT;

    public enum Direction {
        RIGHT, LEFT, UP, DOWN
    }

    public Snake(int length, Point start) {
        points = new ArrayList<>();

        if (length <= 0) throw new IllegalArgumentException();

        points.add(start);
    }

    public Snake(int x, int y) {
        this(1, new Point(x, y));
    }

    public void draw(AudGraphics g) {
        for (Point p_: points) {
            g.setColor(color);
            g.fillRect(p_.getX() * SnakeGame.SQUARE_SIZE, p_.getY() * SnakeGame.SQUARE_SIZE, SnakeGame.SQUARE_SIZE, SnakeGame.SQUARE_SIZE);
        }
        g.setColor(headColor);
        g.fillRect(points.get(0).getX() * SnakeGame.SQUARE_SIZE, points.get(0).getY() * SnakeGame.SQUARE_SIZE, SnakeGame.SQUARE_SIZE, SnakeGame.SQUARE_SIZE);
    }

    public void step() {
        List<Point> sublist;
        try {
            sublist = points.subList(0, points.size()-1);
        } catch (IllegalArgumentException e) {
            sublist = new ArrayList<>();
        }

        Point headPoint = new Point();
        Point headPointNow = points.get(0);
        switch (nextDirection) {
            case RIGHT:
                if (lastDirection != Direction.LEFT){
                    headPoint = new Point(headPointNow.getX() + 1, headPointNow.getY());
                    lastDirection = nextDirection;
                } else headPoint = new Point(headPointNow.getX() - 1, headPointNow.getY());
                break;

            case LEFT:
                if (lastDirection != Direction.RIGHT){
                    headPoint = new Point(headPointNow.getX() - 1, headPointNow.getY());
                    lastDirection = nextDirection;
                } else headPoint = new Point(headPointNow.getX() + 1, headPointNow.getY());
                break;

            case UP:
                if (lastDirection != Direction.DOWN){
                    headPoint = new Point(headPointNow.getX(), headPointNow.getY() - 1);
                    lastDirection = nextDirection;
                } else headPoint = new Point(headPointNow.getX(), headPointNow.getY() + 1);
                break;

            case DOWN:
                if (lastDirection != Direction.UP){
                    headPoint = new Point(headPointNow.getX(), headPointNow.getY() + 1);
                    lastDirection = nextDirection;
                } else headPoint = new Point(headPointNow.getX(), headPointNow.getY() - 1);
                break;
        }

        points = new ArrayList<>();
        points.add(headPoint);
        points.addAll(sublist);
    }

    public void setNextDirection(Direction nextDirection) {
        this.nextDirection = nextDirection;
    }

    public boolean collidesWith (GameItem gi) {
        return collidesWith(gi.getX(), gi.getY());
    }

    public boolean collidesWith (int x, int y) {
        for (Point point : points) {
            if (point.getX() == x && point.getY() == y) return true;
        }
        return false;
    }

    public boolean collidesWithSelf () {
        for (int i = 1; i < points.size(); i++) {
            if (points.get(i).getX() == points.get(0).getX() && points.get(i).getY() == points.get(0).getY()) {
                return true;
            }
        } return false;
    }

    public void grow() {
        List<Point> temp = points;
        Point headPoint = points.get(0);
        points = new ArrayList<>();
        switch(nextDirection) {
            case RIGHT:
                points.add(new Point(headPoint.getX() + 1, headPoint.getY()));
                break;
            case LEFT:
                points.add(new Point(headPoint.getX() - 1, headPoint.getY()));
                break;
            case UP:
                points.add(new Point(headPoint.getX(), headPoint.getY() - 1));
                break;
            case DOWN:
                points.add(new Point(headPoint.getX(), headPoint.getY() + 1));
                break;
        }

        points.addAll(temp);
    }
}

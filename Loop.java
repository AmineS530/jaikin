import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.nio.channels.ServerSocketChannel;
import java.util.ArrayList;
import java.util.List;

public class Loop extends JPanel {
    private List<Point> points = new ArrayList<>();
    static final int MAX_ITERATIONS = 7;
    private int currentIteration = 0;
    private long lastUpdate = System.currentTimeMillis() / 1000;
    private boolean ENTER_KEY_PRESSED = false;

    private List<Point> pointsChaikin = new ArrayList<>();

    public List<Point> getPoints() {
        return this.points;
    }

    public List<Point> getPointsChaikin() {
        return this.pointsChaikin;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public void setPointsChaikin(List<Point> points) {
        this.pointsChaikin = points;
    }

    public Loop() {
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (ENTER_KEY_PRESSED) {
                    return;
                }
                points.add(e.getPoint());
                // System.out.println("the points are: " + getPoints());
                // setPointsChaikin(getPoints());
                repaint();
            }
        });

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    ENTER_KEY_PRESSED = true;
                    setPointsChaikin(getPoints()); 
                    repaint();
                }

                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }

                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    setPoints(new ArrayList<>());
                    reset();
                    repaint();
                    ENTER_KEY_PRESSED = false;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

    }

    public void update() {

        if (currentIteration < 7 && getPoints().size() > 2 && ENTER_KEY_PRESSED) {
            long now = System.currentTimeMillis() / 1000;
            if (now - lastUpdate > 2) {
                List<Pair<Point, Point>> newLinesChaikin = Utils.drawLines(getPoints());
                List<Point> newPointsChaikin = Utils.Chaikin(newLinesChaikin);
                setPointsChaikin(newPointsChaikin);
                currentIteration++;
                lastUpdate = now;
            }
        }
        if (currentIteration == 7) {
            reset();
        }

    }

    public void drawPoints(Graphics g) {
        for (Point point : getPoints()) {
            g.setColor(Color.PINK);
            g.fillOval(point.x - 3, point.y - 3, 6, 6);
        }
    }

    public void reset() {
        setPointsChaikin(points);
        currentIteration = 0;
    }

    public void drawLine(Graphics g) {
        List<Pair<Point, Point>> lines = Utils.drawLines(getPointsChaikin());

        for (Pair<Point, Point> line : lines) {
            g.setColor(Color.BLUE);
            g.drawLine((int) line.getFirst().getX(), (int) line.getFirst().getY(), (int) line.getSecond().getX(),
                    (int) line.getSecond().getY());

        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawPoints(g);

        if (ENTER_KEY_PRESSED) {
            drawLine(g);
            repaint();

        }
        update();

    }

}

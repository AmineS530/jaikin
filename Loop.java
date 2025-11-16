import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Loop extends JPanel {
    private List<Point> points = new ArrayList<>();
    static final int MAX_ITERATIONS = 7;
    private int currentIteration = 0;
    private long lastUpdate = System.currentTimeMillis() / 1000;
    private boolean ENTER_KEY_PRESSED = false;
    private List<Pair<Point, Point>> lines = new ArrayList<>();

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

    public List<Pair<Point, Point>> lines() {
        return this.lines;
    }

    public void setLines(List<Pair<Point, Point>> newLines) {
        this.lines = newLines;
    }

    public Loop() {

        // Animation timer (e.g., 30 FPS => ~33 ms)
        Timer timer = new Timer(60, e -> {
            update();
            repaint();
        });

        timer.start();

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (ENTER_KEY_PRESSED && getPoints().size() >= 2) {
                    return;
                }
                points.add(e.getPoint());
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
                    setLines(Utils.drawLines(getPoints()));
                    setPointsChaikin(getPoints());
                    repaint();
                }

                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }

                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    setPoints(new ArrayList<>());
                    reset();
                    ENTER_KEY_PRESSED = false;
                    repaint();
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
            if (now - lastUpdate > 1) {
                setLines(Utils.drawLines(getPointsChaikin()));
                List<Point> newPointsChaikin = Utils.Chaikin(lines);
                setPointsChaikin(newPointsChaikin);
                currentIteration++;
                lastUpdate = now;
            }
        }

        if (currentIteration == MAX_ITERATIONS) {
            lines = Utils.drawLines(getPoints());
            // ENTER_KEY_PRESSED = true;
            pointsChaikin.clear();
            setPointsChaikin(points);
            setLines(Utils.drawLines(getPoints()));
            currentIteration = 0;
        }

        System.out.println(points.size());
        System.out.println(this.pointsChaikin.size());
        System.out.println(lines.size());

    }

    public void drawPoints(Graphics g) {
        for (Point point : getPoints()) {
            g.setColor(Color.WHITE);
            g.drawOval(point.x - 4, point.y - 4, 8, 8);

        }
    }

    public void reset() {
        currentIteration = 0;
        pointsChaikin.clear();
        lastUpdate = System.currentTimeMillis() / 1000;

    }

    public void drawLine(Graphics g) {
        for (Pair<Point, Point> line : lines) {
            g.setColor(Color.PINK);
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
        }

    }

}

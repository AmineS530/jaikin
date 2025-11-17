package JaikinUtils;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Loop extends JPanel {

    private List<MyPoint> points = new ArrayList<>();
    static final int MAX_ITERATIONS = 7;
    private int currentIteration = 0;
    private long lastUpdate = System.currentTimeMillis();
    private boolean ENTER_KEY_PRESSED = false;
    private int dragIndex = -1;
    private static final int DRAG_RADIUS = 15;
    private static final int DRAW_INTERVAL = 1000; // milliseconds per step

    private ChaikinCache pointsCache = new ChaikinCache(MAX_ITERATIONS);

    public List<MyPoint> getPoints() {
        return this.points;
    }

    public Loop() {
        // Animation timer
        Timer timer = new Timer(33, e -> {
            update();
            repaint();
        });
        timer.start();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!SwingUtilities.isLeftMouseButton(e)) {
                    return;
                }
                if (!ENTER_KEY_PRESSED) {
                    // Check if we are dragging an existing point
                    dragIndex = findPointIndex(e.getX(), e.getY(), DRAG_RADIUS);
                    if (dragIndex >= 0) {
                        return;
                    }
                    // Adding new point
                    points.add(MyPoint.fromAwt(e.getPoint()));
                    reset();
                    repaint();
                }
            }

            @Override
            // stop dragging the point
            public void mouseReleased(MouseEvent e) {
                if (dragIndex >= 0) {
                    dragIndex = -1;
                    reset();
                    repaint();
                }
            }
        });

        //dragging points
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragIndex >= 0) {
                    MyPoint p = points.get(dragIndex);
                    p.x = e.getX();
                    p.y = e.getY();
                    repaint();
                }
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ENTER -> {
                        int pSize = points.size();
                        if (!ENTER_KEY_PRESSED && pSize >= 2) {
                            ENTER_KEY_PRESSED = true;
                            pointsCache.invalidate(points);
                            // avoiding calculating if only 2points
                            if (pSize > 2) {
                                pointsCache.computeAll();
                            }
                            currentIteration = 0;
                            lastUpdate = System.currentTimeMillis();
                            repaint();
                        } else if (pSize < 2) {
                            JOptionPane.showMessageDialog(Loop.this,
                                    "Need at least 2 points to draw a line, 3 points to start the Program",
                                    "Insufficient Points",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    case KeyEvent.VK_ESCAPE ->
                        System.exit(0);
                    case KeyEvent.VK_DELETE -> {
                        points.clear();
                        reset();
                        ENTER_KEY_PRESSED = false;
                        repaint();
                    }
                }
            }
        });

    }

    public void update() {
        if (!ENTER_KEY_PRESSED || points.size() <= 2) {
            return;
        }

        long now = System.currentTimeMillis();
        if (now - lastUpdate >= DRAW_INTERVAL) {
            currentIteration++;
            if (currentIteration > MAX_ITERATIONS) {
                currentIteration = 0;
            }
            lastUpdate = now;
            repaint();
        }
    }

    public void drawPoints(Graphics g) {
        for (MyPoint point : getPoints()) {
            g.setColor(Color.WHITE);
            g.drawOval((int) point.x - 4, (int) point.y - 4, 8, 8);
        }
    }

    public void reset() {
        currentIteration = 0;
        pointsCache.invalidate(points);
        lastUpdate = System.currentTimeMillis();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawPoints(g);

        if (ENTER_KEY_PRESSED) {
            List<MyPoint> iter = pointsCache.get(currentIteration);
            if (iter != null && iter.size() >= 2) {
                int n = iter.size();
                int[] xs = new int[n], ys = new int[n];
                for (int i = 0; i < n; i++) {
                    xs[i] = Math.round(iter.get(i).x);
                    ys[i] = Math.round(iter.get(i).y);
                }
                g.setColor(Color.PINK);
                g.drawPolyline(xs, ys, n);
                g.setColor(Color.WHITE);
                g.setFont(g.getFont().deriveFont(14f));
                g.drawString("| Step: " + currentIteration + " / " + MAX_ITERATIONS + " |", 10, 20);
            }
        }
    }

    private int findPointIndex(int mx, int my, int radius) {
        int idx = -1;
        int r2 = radius * radius;

        for (int i = 0; i < points.size(); i++) {
            MyPoint p = points.get(i);
            float dx = p.x - mx;
            float dy = p.y - my;
            if (dx * dx + dy * dy <= r2) {
                idx = i;
                break;
            }
        }
        return idx;
    }
}

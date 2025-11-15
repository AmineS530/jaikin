package jaikin;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class DrawingPanel extends JPanel {

    private List<Vec2> pointsList = new ArrayList<>();
    private List<List<Vec2>> animationSteps = new ArrayList<>();
    private int currentStepIndex = 0;
    private double stepTimer = 0.0;
    
    private long lastUpdateTime; 

    private final double STEP_DURATION = 0.5;
    private final int TOTAL_ANIMATION_STEPS = 7;
    private final int TARGET_FPS = 60;

    public DrawingPanel() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                if (animationSteps.isEmpty() && mouseEvent.getButton() == MouseEvent.BUTTON1) {
                    pointsList.add(new Vec2(mouseEvent.getX(), mouseEvent.getY()));
                    repaint(); 
                }
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                switch (keyEvent.getKeyCode()) {
                    case KeyEvent.VK_ESCAPE:
                        System.exit(0);
                        break;
                    
                    case KeyEvent.VK_C:
                        pointsList.clear();
                        animationSteps.clear();
                        currentStepIndex = 0;
                        stepTimer = 0.0;
                        repaint();
                        break;
                    
                    case KeyEvent.VK_ENTER:
                        if (pointsList.size() >= 2 && animationSteps.isEmpty()) {
                            animationSteps = generateAnimationSteps(pointsList, TOTAL_ANIMATION_STEPS);
                            currentStepIndex = 0;
                            stepTimer = 0.0;
                        }
                        break;
                }
            }
        });

        setFocusable(true);
        lastUpdateTime = System.nanoTime();

        int delay = 1000 / TARGET_FPS; 
        new javax.swing.Timer(delay, e -> {
            updateAnimation();
            repaint(); 
        }).start();
    }

    private void updateAnimation() {
        long now = System.nanoTime();
        double deltaTime = (now - lastUpdateTime) / 1_000_000_000.0;
        lastUpdateTime = now;

        if (!animationSteps.isEmpty()) {
            stepTimer += deltaTime;
            if (stepTimer > STEP_DURATION) {
                currentStepIndex = (currentStepIndex + 1) % animationSteps.size();
                stepTimer = 0.0; 
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(1.0f));
        for (Vec2 point : pointsList) {
            g2d.drawOval((int)(point.x - 3), (int)(point.y - 3), 6, 6);
        }

        if (!animationSteps.isEmpty()) {
            drawLines(g2d, animationSteps.get(currentStepIndex), Color.GREEN, 3.0f);
        }
    }

    private void drawLines(Graphics2D g2d, List<Vec2> linePoints, Color color, float strokeWidth) {
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(strokeWidth));
        for (int i = 0; i < linePoints.size() - 1; i++) {
            Vec2 startPoint = linePoints.get(i);
            Vec2 endPoint = linePoints.get(i + 1);
            g2d.drawLine((int)startPoint.x, (int)startPoint.y, (int)endPoint.x, (int)endPoint.y);
        }
    }

    private List<Vec2> JaikinStep(List<Vec2> currentPoints) {
        if (currentPoints.size() < 2) {
            return new ArrayList<>(currentPoints);
        }

        List<Vec2> newPoints = new ArrayList<>();
        newPoints.add(currentPoints.get(0));

        for (int i = 0; i < currentPoints.size() - 1; i++) {
            Vec2 p = currentPoints.get(i);
            Vec2 q = currentPoints.get(i + 1);
            newPoints.add(p.add(q.sub(p).scale(0.25)));
            newPoints.add(p.add(q.sub(p).scale(0.75)));
        }

        newPoints.add(currentPoints.get(currentPoints.size() - 1));
        return newPoints;   
    }

    private List<List<Vec2>> generateAnimationSteps(List<Vec2> startPoints, int stepCount) {
        List<List<Vec2>> allSteps = new ArrayList<>();
        allSteps.add(new ArrayList<>(startPoints));

        List<Vec2> currentPoints = new ArrayList<>(startPoints);
        for (int i = 0; i < stepCount; i++) {
            currentPoints = JaikinStep(currentPoints);
            allSteps.add(currentPoints);
        }

        return allSteps;
    }
}

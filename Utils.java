import java.util.*;

import java.awt.Point;

// there is no tuples in java so we created a custom type ;) 

public class Utils {

    // reimplement the draw of lines using points
    private static List<Pair<Point, Point>> lines = new ArrayList<>();

    public static List<Pair<Point, Point>> drawLines(List<Point> points) {
        lines.clear();
        for (int i = 0; i < points.size(); i++) {
            if (i + 1 < points.size()) {
                Point p1 = points.get(i);
                Point p2 = points.get(i + 1);
                Pair<Point, Point> line = new Pair<Point, Point>(p1, p2);
                lines.add(line);
            }
        }

        return lines;

    }

    // each times we need the current points (so we'll be passing the lines each
    // times to get the q and r between each line)
    public static List<Point> Chaikin(List<Pair<Point, Point>> lines) {
        Pair<Point, Point> firstLine = lines.get(0);
        List<Point> points = new ArrayList<>();
        points.add(firstLine.getFirst());

        for (Pair<Point, Point> tuple : lines) {
            Point p1 = tuple.getFirst();
            Point p2 = tuple.getSecond();
            Point pointQ = new Point((int) (0.75 * p1.getX() + 0.25 * p2.getX()),
                    (int) (0.75 * p1.getY() + 0.25 * p2.getY()));
            points.add(pointQ);

            Point pointR = new Point((int) (0.25 * p1.getX() + 0.75 * p2.getX()),
                    (int) (0.25 * p1.getY() + 0.75 * p2.getY()));

            points.add(pointR);
        }
        Pair<Point, Point> lastLine = lines.get(lines.size() - 1);
        points.add(lastLine.getSecond());
        return points;

    }

}

// each times we need the current points (so we'll be passing the lines each
// times to get the q and r between each line)
// pub fn chaikin_iteration(points: Vec<(Point, Point)>) -> Vec<Point> {
// let mut lines_iteration = Vec::new();
// let (start, _) = points[0];
// lines_iteration.push(start);
// for (p1, p2) in &points {
// let point_q = Point(0.75 * p1.0 + 0.25 * p2.0, 0.75 * p1.1 + 0.25 * p2.1);
// let point_r = Point(0.25 * p1.0 + 0.75 * p2.0, 0.25 * p1.1 + 0.75 * p2.1);
// lines_iteration.push(point_q);
// lines_iteration.push(point_r);
// }
// let (_, end) = points[points.len() - 1];
// lines_iteration.push(end);
// lines_iteration
// }

package JaikinUtils;

import java.util.*;

// there is no tuples in java so we created a custom type ;) 
public class Utils {

    // reimplement the draw of lines using points
    private static final List<Pair<MyPoint, MyPoint>> lines = new ArrayList<>();

    public static List<Pair<MyPoint, MyPoint>> drawLines(List<MyPoint> points) {
        lines.clear();
        for (int i = 0; i < points.size(); i++) {
            if (i + 1 < points.size()) {
                MyPoint p1 = points.get(i);
                MyPoint p2 = points.get(i + 1);
                Pair<MyPoint, MyPoint> line = new Pair<>(p1, p2);
                lines.add(line);
            }
        }

        return lines;
    }

    // each times we need the current points (so we'll be passing the lines each
    // times to get the q and r between each line)
    public static List<MyPoint> Chaikin(List<Pair<MyPoint, MyPoint>> lines) {
        Pair<MyPoint, MyPoint> firstLine = lines.get(0);
        List<MyPoint> points = new ArrayList<>();
        points.add(firstLine.getFirst());

        for (Pair<MyPoint, MyPoint> tuple : lines) {
            MyPoint p1 = tuple.getFirst();
            MyPoint p2 = tuple.getSecond();
            MyPoint pointQ = new MyPoint((int) (0.75 * p1.getX() + 0.25 * p2.getX()),
                    (int) (0.75 * p1.getY() + 0.25 * p2.getY()));
            points.add(pointQ);

            MyPoint pointR = new MyPoint((int) (0.25 * p1.getX() + 0.75 * p2.getX()),
                    (int) (0.25 * p1.getY() + 0.75 * p2.getY()));

            points.add(pointR);
        }
        Pair<MyPoint, MyPoint> lastLine = lines.get(lines.size() - 1);
        points.add(lastLine.getSecond());
        return points;

    }

}

package JaikinUtils;

import java.util.ArrayList;
import java.util.List;


public class ChaikinCache {
    private final List<List<MyPoint>> cache = new ArrayList<>();
    private final int maxIter;

    public ChaikinCache(int maxIterations) {
        this.maxIter = maxIterations;
    }

    // copy base points into first cache idx
    public void invalidate(List<MyPoint> basePoints) {
        cache.clear();
        cache.add(copyPoints(basePoints));
    }

    // compute all iterations
    public void computeAll() {
        if (cache.isEmpty()) return;
        while (cache.size() <= maxIter) {
            List<MyPoint> prev = cache.get(cache.size() - 1);
            List<Pair<MyPoint, MyPoint>> lines = Utils.drawLines(prev);
            List<MyPoint> next = Utils.Chaikin(lines);
            cache.add(copyPoints(next));
        }
    }

    // get points at iteration k
    public List<MyPoint> get(int k) {
        if (k < 0 || k >= cache.size()) return null;
        return cache.get(k);
    }

    // deep copy of points
    private static List<MyPoint> copyPoints(List<MyPoint> src) {
        List<MyPoint> copy = new ArrayList<>(src.size());
        for (MyPoint p : src) copy.add(new MyPoint(p.x, p.y));
        return copy;
    }
}

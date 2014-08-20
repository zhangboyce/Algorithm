package structure.graph;

import structure.list.IArrayList;
import structure.list.IList;

/**
 * Created by boyce on 2014/8/20.
 */
public class IGraph<T> {

   private IList<Point<T>> points;

    public IGraph() {
        points = new IArrayList<Point<T>>();
    }

    /**
     * add a single point to graph
     * @param element
     */
    public void addPoint(T element) {
        if (null == element) return;

        Point point = new Point(element);
        if (this.points.contains(point))
            return;

        this.points.add(point);
    }

    /**
     * Graph point
     * @param <T>
     */
    private static class Point<T> {
        //point data
        private T element;
        //this point's adjacent points list
        private IList<Point<T>> adjPoints;

        private Point(T element) {
            this.element = element;
            this.adjPoints = new IArrayList<Point<T>>();
        }

        private void addAdjPoint(Point point) {
            this.adjPoints.add(point);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Point)) return false;

            Point point = (Point) o;

            if (!element.equals(point.element)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return element.hashCode();
        }
    }
}

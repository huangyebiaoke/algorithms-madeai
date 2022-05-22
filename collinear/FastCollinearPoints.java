/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.LinkedList;

public class FastCollinearPoints {
    private final LineSegment[] lineSegments;

    public FastCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException();
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException();
        }
        Point[] sorted = points.clone();
        Arrays.sort(sorted);
        for (int i = 0; i < points.length - 1; i++) {
            if (sorted[i].compareTo(sorted[i + 1]) == 0)
                throw new IllegalArgumentException();
        }
        LinkedList<LineSegment> segmentsList = new LinkedList<>();
        Point[] sortedBySlope = points.clone();
        for (int i = 0; i < sorted.length; i++) {
            Arrays.sort(sortedBySlope, sorted[i].slopeOrder().thenComparing(Point::compareTo));
            int count = 1;
            Point linebegin = null;
            for (int j = 0; j < sortedBySlope.length - 1; j++) {
                if (sortedBySlope[j].slopeTo(sorted[i]) == sortedBySlope[j + 1].slopeTo(sorted[i])) {
                    count++;
                    if (count == 2) {
                        linebegin = sortedBySlope[j];
                        count++;
                    } else if (count >= 4 && j + 1 == sortedBySlope.length - 1) {
                        if (linebegin.compareTo(sorted[i]) > 0) {
                            segmentsList.add(new LineSegment(sorted[i], sortedBySlope[j+1]));
                        }
                        count = 1;
                    }
                } else if (count >= 4) {
                    if (linebegin.compareTo(sorted[i]) > 0) {
                        segmentsList.add(new LineSegment(sorted[i], sortedBySlope[j]));
                    }
                    count = 1;
                } else count = 1;
            }
        }
        lineSegments = segmentsList.toArray(new LineSegment[segmentsList.size()]);
    }

    public int numberOfSegments() {
        return lineSegments.length;
    }

    public LineSegment[] segments() {
        return lineSegments;
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}

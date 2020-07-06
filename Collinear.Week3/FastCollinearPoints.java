
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class FastCollinearPoints
{
    private LineSegment[] lineSegments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points)
    {

        CheckNullArr(points);
        CheckNullElem(points);

        final List<LineSegment> maxLineSegments = new LinkedList<>();
        int len = points.length;

        Point[] pointsCopy = new Point[len];
        System.arraycopy(points, 0, pointsCopy, 0, len);
        Arrays.sort(pointsCopy, 0, len);

        CheckDuplicatesArr(pointsCopy);

        for (int i = 0; i < len; i++)
        {

            Point p = pointsCopy[i];
            Point[] pointsSlope = pointsCopy.clone();
            Arrays.sort(pointsSlope, p.slopeOrder());

            int x = 1;
            while (x < len)
            {

                LinkedList<Point> candidates = new LinkedList<>();
                final double slope = p.slopeTo(pointsSlope[x]);
                do {
                    candidates.add(pointsSlope[x++]);
                } while (x < len && p.slopeTo(pointsSlope[x]) == slope);


                if (candidates.size() >= 3
                        && p.compareTo(candidates.peek()) < 0) {
                    Point min = p;
                    Point max = candidates.removeLast();
                    maxLineSegments.add(new LineSegment(min, max));
                }
            }
        }
        lineSegments = maxLineSegments.toArray(new LineSegment[0]);

       }

    //Check if array is null
    private void CheckNullArr(Point[] points)
    {
        if(points == null)
            throw new NullPointerException("Arr of points has Null parameter");
    }


    //Check if array has null element
    private void CheckNullElem(Point[] points)
    {

        for ( int l = 0; l < points.length; l++)
        {
            if (points[l] == null)
            {
                throw new java.lang.NullPointerException("One of the point is null");
            }
        }
    }

    //Checking duplications in points array
    private void CheckDuplicatesArr(Point[] pointsCopy)
    {
        for (int i = 0; i < pointsCopy.length - 1; i++) {
            if (pointsCopy[i].compareTo(pointsCopy[i + 1]) == 0) {
                throw new IllegalArgumentException("Array has duplicates");
            }
        }

    }


    // the number of line segments
    public int numberOfSegments()
    {
        return lineSegments.length;
    }

    // the line segments
    public LineSegment[] segments()
    {
        return lineSegments.clone();
    }

}
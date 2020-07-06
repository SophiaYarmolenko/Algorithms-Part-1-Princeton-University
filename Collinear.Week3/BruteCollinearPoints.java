

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints
{

    private ArrayList<LineSegment> segmentsList = new ArrayList<LineSegment>();
    private double eps = 0.00000001;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points)
    {

        CheckNullArr(points);
        CheckNullElem(points);

        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsCopy);

        CheckDuplicatesArr(pointsCopy);
        double slope1;
        double slope2;
        double slope3;

        int len = pointsCopy.length;

        for (int i = 0; i < len - 3; ++i)
        {
            for (int j = i + 1; j < len - 2; ++j)
            {
                for (int k = j + 1; k < len - 1; ++k)
                {
                    for (int p = k + 1; p < len; ++p)
                    {
                        slope1 = pointsCopy[i].slopeTo(pointsCopy[j]);
                        slope2 = pointsCopy[i].slopeTo(pointsCopy[k]);
                        slope3 = pointsCopy[i].slopeTo(pointsCopy[p]);

                        if (Math.abs(slope1 - slope2) < eps && Math.abs(slope1 - slope3) < eps) {
                            segmentsList.add(new LineSegment(pointsCopy[i], pointsCopy[p]));
                        } else if (slope1 == Double.POSITIVE_INFINITY && slope2 == Double.POSITIVE_INFINITY
                                && slope3 == Double.POSITIVE_INFINITY) {
                            segmentsList.add(new LineSegment(pointsCopy[i], pointsCopy[p]));
                        }
                    }
                }
            }

        }

    }

    private void CheckNullArr(Point[] points)
    {
        if(points == null)
            throw new NullPointerException("Arr of points has Null parameter");
    }

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
    private void CheckDuplicatesArr(Point[] points)
    {
        for (int i = 0; i < points.length - 1; ++i)
            for (int j = i + 1; j < points.length; ++j) {
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException("Repeated points detected.");
            }

    }

    // the number of line segments
    public int numberOfSegments()
    {
        return segmentsList.size();
    }

    // the line segments*/
    public LineSegment[] segments()
    {
        return segmentsList.toArray(new LineSegment[segmentsList.size()]);
    }
}

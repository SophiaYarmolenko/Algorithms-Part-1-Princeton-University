//package com.company;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeSet;

public class PointSET
{
    private TreeSet <Point2D> pointTreeSet;
    //construct an empty set of points
    public PointSET()
    {
        pointTreeSet = new TreeSet<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty()
    {
        return pointTreeSet.isEmpty();
    }

    // number of points in the set
    public int size()
    {
        return pointTreeSet.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p)
    {
        checkNull(p);
        if(!pointTreeSet.contains(p))
            pointTreeSet.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p)
    {
        checkNull(p);
        return pointTreeSet.contains(p);
    }

    // draw all points to standard draw
    public void draw()
    {
        Iterator<Point2D> elements = pointTreeSet.iterator();
        while (elements.hasNext())
        {
            elements.next().draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect)
    {
        checkNull(rect);
        LinkedList <Point2D> points = new LinkedList<Point2D>();
        Iterator<Point2D> elements = pointTreeSet.iterator();

        while (elements.hasNext())
        {
            Point2D element = elements.next();
            if(rect.contains(element))
                points.add(element);
        }

        return points;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p)
    {
        checkNull(p);
        if(isEmpty())
            return null;
        return nearest(p);
    }

    private void checkNull(Object element)
    {
        if ( element == null )
            throw new IllegalArgumentException("Argument is null");
    }

    // unit testing of the methods (optional)
    public static void main(String[] args)
    {
        /*
        RectHV rect = new RectHV(0.0, 0.0, 0.5, 0.5);
        StdDraw.enableDoubleBuffering();
        StdDraw.setPenColor(0, 128, 0);
        StdDraw.setPenRadius(0.02);
        PointSET kdtree = new PointSET();
        while (true) {
            if (StdDraw.isMousePressed())
            {
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();
                StdOut.printf("%8.6f %8.6f\n", x, y);
                Point2D p = new Point2D(x, y);
                if (rect.contains(p)) {
                     StdOut.printf("%8.6f %8.6f\n", x, y);
                     StdOut.printf("Contains");
                     kdtree.insert(p);
                     StdDraw.clear();
                     kdtree.draw();
                     StdDraw.show();
                }
                    }
                   StdDraw.pause(20);
                }*/
    }
}
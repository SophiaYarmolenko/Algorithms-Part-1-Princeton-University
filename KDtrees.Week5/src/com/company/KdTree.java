//package com.company;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;
import java.util.Iterator;
import java.util.LinkedList;


public class KdTree
{
    private Node root;
    private static final boolean RED = true;
    private static final boolean BLUE = false;
    private Node nearestPoint = root;
   // private double color = 255;
    private double minimalDistance;

    // construct an empty set of points
    public KdTree()
    {};
    private class Node
    {
        Point2D point;
        boolean color;
        int counter = 0;//number of children + 1
        Node left;
        Node right;
        public Node(Point2D point, boolean color)
        {
            checkNull(point);
            this.point = point;
            this.color = color;
        }
    }
    // is the set empty?
    public boolean isEmpty()
    {
        return size() == 0;
    }

    // number of points in the set
    public int size()
    {
        return size(root);
    }
    private int size(Node x)
    {
        if( x == null)
            return 0;
        return x.counter;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D point)
    {
        checkNull(point);
        root = put(root, point, RED);
    }
    private Node put(Node node, Point2D point, boolean color)
    {
        if(node == null)
        {
            node = new Node(point, color);
            node.counter = 1 + size(node.left) +size(node.right);
            return node;
        }

        double previousCoordinate;//the old coordinate to compare with
        double currentlyCoordinate;//the new coordinate
        double anotherPreviousCoordinate;
        double anotherCurrentlyCoordinate;

        if(color == RED)
        {
            previousCoordinate = node.point.x();
            currentlyCoordinate = point.x();
            anotherCurrentlyCoordinate = point.y();
            anotherPreviousCoordinate = node.point.y();
        }
        else
            {
                previousCoordinate = node.point.y();
                currentlyCoordinate = point.y();
                anotherCurrentlyCoordinate = point.x();
                anotherPreviousCoordinate = node.point.x();
            }
        if ( currentlyCoordinate == previousCoordinate && anotherCurrentlyCoordinate == anotherPreviousCoordinate)
            node.point = point;
        else if( currentlyCoordinate < previousCoordinate )
            node.left = put(node.left, point, !color);
        else if ( currentlyCoordinate >= previousCoordinate )
            node.right = put(node.right, point, !color);
        node.counter = 1 + size(node.left) +size(node.right);
        return node;
    }

    // does the set contain point p?
    public boolean contains(Point2D p)
    {
        checkNull(p);
        Node node = root;

        double ownCoordinate ;
        double possibleCoordinate;
        double anotherOwnCoordinate;
        double anotherPossibleCoordinate;

        while ( node != null)
        {
            if(node.color == RED)
            {
                ownCoordinate = p.x();
                possibleCoordinate = node.point.x();
                anotherOwnCoordinate = p.y();
                anotherPossibleCoordinate = node.point.y();
            }
            else
            {
                ownCoordinate = p.y();
                possibleCoordinate = node.point.y();
                anotherOwnCoordinate = p.x();
                anotherPossibleCoordinate = node.point.x();
            }

            if( ownCoordinate == possibleCoordinate && anotherOwnCoordinate == anotherPossibleCoordinate)
                return true;
            else if( ownCoordinate < possibleCoordinate )
                node = node.left;
            else if ( ownCoordinate >= possibleCoordinate)
                node = node.right;

        }
        return false;
    }

    // draw all points to standard draw
    public void draw()
    {
        Iterator<Node> iterator = iterator();
        while (iterator.hasNext())
        {
            Node pointNode = iterator.next();

            if(pointNode.color == RED)
                drowLine(new Point2D(pointNode.point.x()-1, pointNode.point.y()),
                        new Point2D(pointNode.point.x()+1, pointNode.point.y()),
                        RED);
            else
                drowLine(new Point2D(pointNode.point.x(), pointNode.point.y() - 1),
                        new Point2D(pointNode.point.x(), pointNode.point.y() + 1),
                        BLUE);

            StdDraw.setPenColor(0 , 0, 0);
            StdDraw.setPenRadius(0.02);
            pointNode.point.draw();
        }
    }
    private void drowLine (Point2D pointLeft, Point2D pointRight, boolean color)
    {
        if(color == RED)
            StdDraw.setPenColor(255 , 0, 0);
        else
            StdDraw.setPenColor(0 , 0, 255);
        StdDraw.setPenRadius(0.005);
        StdDraw.line(pointLeft.x(), pointLeft.y(), pointRight.x(), pointRight.y());
    }
    private Iterator <Node> iterator()
    {
       LinkedList<Node> q = new LinkedList<Node>();
        inorder(root, q);
        return q.iterator();
    }
    private void inorder(Node x, LinkedList<Node> q)
    {
        if(x == null)
            return;
        inorder(x.left, q);
        q.add(x);
        inorder(x.right, q);
    }


    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect)
    {
        checkNull(rect);
        LinkedList <Point2D> pointsInRect = new LinkedList<Point2D>();
        InRectSearch(rect, pointsInRect, root);

        return pointsInRect;
    }
    private void InRectSearch(RectHV rect, LinkedList<Point2D> pointsInRect, Node node)
    {
        double ownCoordinate;
        double rectCoordinateMin;
        double rectCoordinateMax;

        if(node != null)
        {
            if (rect.contains(node.point))
                pointsInRect.add(node.point);

            if(node.color == RED)
            {
                ownCoordinate = node.point.x();
                rectCoordinateMin = rect.xmin();
                rectCoordinateMax = rect.xmax();
            }
            else
            {
                ownCoordinate = node.point.y();
                rectCoordinateMin = rect.ymin();
                rectCoordinateMax = rect.ymax();
            }

            if( ownCoordinate > rectCoordinateMax )
            {
                InRectSearch(rect, pointsInRect, node.left);
            }
            else if ( ownCoordinate < rectCoordinateMin)
            {
                InRectSearch(rect, pointsInRect, node.right);
            }
            else
            {
                InRectSearch(rect, pointsInRect, node.right);
                InRectSearch(rect, pointsInRect, node.left);
            }
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p)
    {
        checkNull(p);
        if(isEmpty())
            return null;
        minimalDistance = p.distanceTo(root.point);
        return nearest(root, p);
    }
    private Point2D nearest(Node node, Point2D p)
    {
        if( node == null)
            return nearestPoint.point;
        double distanceToEdge;
        double distanceToPoint = p.distanceTo(node.point);
        double currentCoordinate;
        double nearestCoordinate;

        if(distanceToPoint <= minimalDistance)
        {
            minimalDistance = distanceToPoint;
            nearestPoint = node;
           // System.out.println("new minimal = " + nearestPoint.point.toString());
            //StdDraw.setPenColor(0 , (int) color, 0);
            //nearestPoint.point.draw();
            //color -= 40;
        }

        if(node.color == RED)
        {
            distanceToEdge = Math.sqrt(p.distanceSquaredTo(node.point) - Math.pow(node.point.y()-p.y(), 2));
            currentCoordinate = node.point.x();
            nearestCoordinate = nearestPoint.point.x();
        }
        else
            {
                distanceToEdge = Math.sqrt(p.distanceSquaredTo(node.point) - Math.pow(node.point.x()-p.x(), 2));
                currentCoordinate = node.point.y();
                nearestCoordinate = nearestPoint.point.y();
            }

        if(distanceToEdge > minimalDistance)
            if(nearestCoordinate < currentCoordinate)
                nearest(node.left, p);
            else
                nearest(node.right, p);
        else
            {
                nearest(node.right, p);
                nearest(node.left, p);
                //System.out.println("Nearest = " + nearestPoint.point.toString());
            }

        return nearestPoint.point;
    }

    private void checkNull(Object element)
    {
        if ( element == null )
            throw new IllegalArgumentException("Argument is null");
    }

    // unit testing of the methods (optional)
    public static void main(String[] args)
    {
        KdTree tree = new KdTree();
        tree.insert(new Point2D(0.8125, 0.0625));
        tree.insert(new Point2D(0.40625, 0.21875));
        tree.insert(new Point2D(0.625 , 0.15625));
        tree.insert(new Point2D(0.34375, 0.1875));

        tree.insert(new Point2D(0.21875, 0.0));
        tree.insert(new Point2D(0.25, 0.5625));
        tree.insert(new Point2D(0.1875, 0.28125));
        tree.insert(new Point2D(0.53125, 0.6875));
        tree.insert(new Point2D(0.84375, 0.59375));
        tree.insert(new Point2D(0.4375, 0.09375));
        tree.insert(new Point2D(0.5, 0.96875));
        tree.insert(new Point2D(0.96875, 0.625));
        tree.insert(new Point2D(0.375, 0.34375));
        tree.insert(new Point2D(0.6875, 0.375));
        tree.insert(new Point2D(0.3125, 0.8125));
        tree.insert(new Point2D(0.0, 0.65625));
        tree.insert(new Point2D(0.71875, 0.53125));
        tree.insert(new Point2D(0.09375, 0.46875));
        tree.insert(new Point2D(0.46875, 0.75));
        tree.insert(new Point2D(0.75, 0.4375));
        tree.draw();
        RectHV rect = new RectHV(0.0, 0.0, 0.5, 0.5);
        System.out.println("size of the tree = " + tree.size());
        System.out.println("contains (0.0, 0.2)? =  " + tree.contains(new Point2D(0.0, 0.2)));
        for(Point2D point : tree.range(rect))
            System.out.println(point.toString());
        StdDraw.setPenColor(0 , 0, 225);
        tree.nearest(new Point2D(1.0, 0.40625)).draw();
        //System.out.println("nearest point = " + tree.nearest(new Point2D(1.0, 0.40625)));
        StdDraw.setPenColor(100 , 0, 100);
        new Point2D(1.0, 0.40625).draw();
    }
}

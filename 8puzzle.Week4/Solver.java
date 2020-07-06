//package com.company;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Scanner;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver
{
    private SearchNode solution;
    //private  int n = 0;
    private class SearchNode implements Comparable<SearchNode>
    {
        private Board board;
        private SearchNode boardBefore;
        private int moves;
        private int priority;

        SearchNode(Board board, SearchNode boardBefore)
        {
            this.board = board;
            this.boardBefore = boardBefore;
            if ( boardBefore == null)
            {
                this.moves = 0;
            }
            else
                this.moves = boardBefore.moves + 1;
            this.priority = this.moves+this.board.manhattan();
        }

        public void insertNeighbors ( MinPQ<SearchNode> priorityQueue)
        {
            for (Board neighbor : board.neighbors())
            {
                if (boardBefore != null && neighbor.equals(boardBefore.board))
                    continue;
                SearchNode node = new SearchNode(neighbor, this);
                priorityQueue.insert(node);
            }

        }

        public int compareTo(SearchNode that)
        {
            return this.priority - that.priority ;
        }
    }


    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial)
    {
        CheckInitial(initial);
        MinPQ<SearchNode> solutionPQ = new MinPQ<SearchNode>();
        MinPQ<SearchNode> twinPQ = new MinPQ<SearchNode>();

        SearchNode initialNode = new SearchNode(initial, null);
        solutionPQ.insert(initialNode);

        SearchNode initialTwinNode = new SearchNode(initial.twin(), null);
        twinPQ.insert(initialTwinNode);

        while (true)
        {
            SearchNode solutionNode = solutionPQ.delMin();
            SearchNode twinNode = twinPQ.delMin();

            if (solutionNode.board.isGoal())
            {
                this.solution = solutionNode;
                break;
            }
            else if (twinNode.board.isGoal())
            {
                this.solution = null;
                break;
            }

            solutionNode.insertNeighbors(solutionPQ);
            twinNode.insertNeighbors(twinPQ);

            //System.out.println("InitialBoard\n"+solutionNode.board.toString());
            //System.out.println("TwinBoard\n"+twinNode.board.toString());
            //n++;
        }
    }


    private void CheckInitial(Board initial )
    {
        if( initial == null )
        {
            throw new IllegalArgumentException("The initial board is null");
        }
    }
    // is the initial board solvable? (see below)
    public boolean isSolvable()
    {
        return solution != null;
    }

    // min number of moves to solve initial board
    public int moves()
    {
        if (isSolvable())
        {
            return solution.moves;
        }
        else
            {
                return -1;
            }
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution()
    {
        if ( isSolvable() )
        {
            LinkedList <Board> solutionList = new LinkedList<>();
            SearchNode nextNode = solution;
            while (nextNode != null)
            {
                solutionList.addFirst(nextNode.board);
                nextNode = nextNode.boardBefore;
            }

            return solutionList;
        }
        else
            {
                return null;
            }
    }

    // test client (see below)
    public static void main(String[] args)
    {
        /*
        int n ;
        Scanner scan = new Scanner(System.in);
        n = scan.nextInt();
        int [][] boad = new int [n][n];


        for( int i = 0; i < n; i++ )
        {
            for(int j = 0; j < n; j++)
            {
                boad[i][j] = scan.nextInt();
            }

        }
        Board board = new Board(boad);
        Solver solver = new Solver(board);
            System.out.println(solver.moves());
         */
    }
}
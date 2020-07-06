//package com.company;

import java.util.LinkedList;


public class Board
{
    private int [][] BOARD;
    private int N;
    private LinkedList<Board> neighbors;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles)
    {
        CheckArray ( tiles ) ;
        N = (int) (tiles.length);

        BOARD = new int [N][N];

        for ( int i = 0; i < N; i++ )
        {
            for ( int j = 0; j < N; j++)
            {
                 BOARD[i][j] = tiles[i][j];
            }
        }

    }

    private void CheckArray ( int [][] arr)
    {
        if ( arr == null )
            throw new NullPointerException("Given array is null");
    }

    // string representation of this board
    public String toString()
    {
        String tostring = N+"\n";

        for( int i = 0; i < N; i++ )
        {
            for(int j = 0; j < N; j++)
            {
                tostring += " " ;
                tostring += BOARD[i][j] ;
            }
            if ( i != N - 1 )
                tostring += "\n";
        }
        return tostring;
    }

    // board dimension n
    public int dimension()
    {
        return N;
    }

    // number of tiles out of place
    public int hamming()
    {
        int tilesOutPlace = 0;

        for ( int i = 0; i < N; i++)
        {
            for(int j = 0; j < N; j++)
            {
               if(BOARD[i][j] != 0)
               {
                   if(BOARD[i][j] != i*N+j+1)
                       tilesOutPlace++;
               }
            }
        }

        return tilesOutPlace;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan()
    {
       int sumOfManhattan = 0;

       for ( int i = 0; i < N; i++ )
       {
           for (int j = 0; j < N; j++)
           {

               int needRow;
               int needCol;

               if ( BOARD[i][j] == 0)
               {
                  continue;
               }
               else
                   {
                       int needVal = BOARD[i][j] - 1;
                       needRow = needVal / N;
                       needCol = needVal % N;
                       /* if ( BOARD[i][j] % N == 0)
                        {
                            needRow = BOARD[i][j] / N - 1;
                            needCol = N-1;
                        }
                        else
                        {
                            needRow = BOARD[i][j] / N ;
                            needCol = BOARD[i][j] % N - 1;
                        }*/
                   }
               sumOfManhattan += Math.abs(i-needRow) + Math.abs(j-needCol);
           }
       }
       return sumOfManhattan;
    }

    // is this board the goal board?
    public boolean isGoal()
    {
        return ( manhattan() == 0 );
    }

    // does this board equal y?
    public boolean equals(Object y)
    {
        if (y == null)
            return false;
        if (this.getClass() != y.getClass())
            return false;

        Board anotherBoard = (Board) y;

        if (dimension() != anotherBoard.dimension())
            return false;

        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (BOARD[i][j] != anotherBoard.BOARD[i][j]) return false;
            }
        }

        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors()
    {
        neighbors  = new LinkedList<Board>();

        int zerocol = 0;
        int zerorow = 0;

        outerLoop:
        for (int i = 0; i < dimension(); i++)
        {
            for (int j = 0; j < dimension(); j++)
            {
                if (BOARD[i][j] == 0)
                {
                    zerocol = j;
                    zerorow = i;
                    break outerLoop;
                }
            }
        }

        for (int k = 0; k < 4; k++)
        {
            int neighborsCol = zerocol;
            int neighborsRow = zerorow;

            switch (k)
            {
                case 0: neighborsCol++;
                    break;
                case 1: neighborsCol--;
                    break;
                case 2: neighborsRow++;
                    break;
                case 3: neighborsRow--;
                    break;
            }

            if (neighborsCol < 0 || neighborsCol >= N || neighborsRow < 0 || neighborsRow >= N)
                continue;

            int[][] Blocks = blocksCopy();
            swap(Blocks, zerorow, zerocol, neighborsRow, neighborsCol);

            Board neighbor = new Board(Blocks);
            neighbors.add(neighbor);
        }

        return neighbors;
    }

    private int[][] blocksCopy() {
        int[][] blocksCopy = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                blocksCopy[i][j] = BOARD[i][j];
            }
        }

        return blocksCopy;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin()
    {
        int[][] cloned = blocksCopy();
        if (BOARD[0][0] != 0 )
        {
            if( BOARD[0][1] != 0 )
                swap(cloned, 0, 0, 0, 1);
            else
                swap(cloned, 0, 0, 1, 0);
        }
        else
            {
            swap(cloned, 1, 0, 1, 1);
            }
        return new Board(cloned);
    }

    private void swap(int[][] arr, int row1, int col1, int row2, int col2)
    {
        int element1 = arr[row1][col1];
        arr[row1][col1] = arr[row2][col2];
        arr[row2][col2] = element1;
    }

    // unit testing (not graded)
    public static void main(String[] args)
    {

        /*int [][] boad = new int [4][4];
        int n = 4;
        Scanner scan = new Scanner(System.in);
        for( int i = 0; i < n; i++ )
        {
            for(int j = 0; j < n; j++)
            {
                boad[i][j] = scan.nextInt();
            }

        }
        Board board = new Board(boad);
        for(Board b:board.neighbors())
            System.out.println(b.toString()+"\n");*/

    }
}

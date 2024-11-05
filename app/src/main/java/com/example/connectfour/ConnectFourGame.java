package com.example.connectfour;

import java.util.Random;

public class ConnectFourGame {
    // constant fields
    public static final int ROW = 7;
    public static final int COL = 6;
    public static final int EMPTY = 0;
    public static final int BLUE = 1;
    public static final int RED = 2;
    public static final int DISCS = 42;

    // members
    private int[][] boardGrid;
    private int player = BLUE;

    public String getPlayer()
    {
       return (player == BLUE) ? "Blue" : "Red";
    }

    public ConnectFourGame() {

        boardGrid = new int[ROW][COL];
        newGame();
    }

    public void newGame() {
        // set blue as first player
        player = BLUE;

        // initialize the board to be empty
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                boardGrid[row][col] = EMPTY;
            }
        }
    }

    public String getState() {
        StringBuilder boardString = new StringBuilder();
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                boardString.append(boardGrid[row][col]);
            }
        }

        return boardString.toString();
    }

    public int getDisc(int row, int col) {
        return boardGrid[row][col];
    }

    // checks to see if there are any winning conditions met or if there are still tiles to fill
    public boolean isGameOver()
    {
        for (int row = 0; row < ROW; row++)
        {
            for (int col = 0; col < COL; col++)
            {
                if (isWin())
                {
                    return true;
                }

                if (boardGrid[row][col] == EMPTY)
                {
                    return false;
                }


            }
        }

        return true;
    }

    public boolean isWin()
    {
        if (checkHorizontal() || checkVertical() || checkDiagonal())
            return true;
        else
            return false;
    }

    // this method checks the for winning horizontally
    private boolean checkHorizontal() {
            for (int i = 0; i < ROW; i++)
            {
                for (int j = 0; j <= COL - 4; j++)
                {
                    // check if the current tile and the 3 to the right are the same
                    if (boardGrid[i][j] != EMPTY &&
                        boardGrid[i][j+1] == boardGrid[i][j] &&
                        boardGrid[i][j+2] == boardGrid[i][j] &&
                        boardGrid[i][j+3] == boardGrid[i][j])
                        return true;
                }
            }


            return false;
    }

    // this method checks the for winning vertically
    private boolean checkVertical()
    {
        for (int i = 0; i <= ROW - 4; i++)
        {
            for (int j = 0; j < COL; j++)
            {
                // check if the current tile and the 3 above it are the same
                if (boardGrid[i][j] != EMPTY &&
                    boardGrid[i+1][j] == boardGrid[i][j] &&
                    boardGrid[i+2][j] == boardGrid[i][j] &&
                    boardGrid[i+3][j] == boardGrid[i][j]) {
                        return true;
                }
            }
        }

        return false;
    }

    // this method checks the diagonals and anti-diagonals for winning
    private boolean checkDiagonal()
    {
        int numConsecutive, prevColor;

        // checking diagonals (left to right)
        for (int i = 0; i <= ROW - 4; i++)
        {
            for (int j = 0; j <= COL - 4; j++)
            {
                if (boardGrid[i][j] != EMPTY &&
                    boardGrid[i][j] == boardGrid[i+1][j+1] &&
                    boardGrid[i][j] == boardGrid[i+2][j+2] &&
                    boardGrid[i][j] == boardGrid[i+3][j+3]) {
                    return true;
                }
            }
        }

        // checking anti diagonals (right to left)
        for (int i = 0; i <= ROW - 4; i++)
        {
            for (int j = 3; j < COL; j++)
            {
                if (boardGrid[i][j] != EMPTY &&
                    boardGrid[i][j] == boardGrid[i+1][j-1] &&
                    boardGrid[i][j] == boardGrid[i+2][j-2] &&
                    boardGrid[i][j] == boardGrid[i+3][j-3]) {
                        return true;
                }
            }
        }

        return false;
    }

    public void setState(String gameState)
    {
         int index = 0;

         for (int row = 0; row < ROW; row++)
         {
             for (int col = 0; col < COL; col++)
             {
                 boardGrid[row][col] = gameState.charAt(index);
                 index++;
             }
         }
    }

    // this method selects the highest possible tile in the column specified
    public void selectDisc(int row, int col)
    {
        // checking top to bottom
        for (int i = ROW - 1; i >= 0; i--)
        {
            if (boardGrid[i][col] == EMPTY)
            {
                boardGrid[i][col] = player;
                switchPlayer();
                break;
            }
        }
    }

    // this method sets the player to the opposite color;
    private void switchPlayer()
    {
        player = (player == BLUE) ? RED : BLUE;
    }

}

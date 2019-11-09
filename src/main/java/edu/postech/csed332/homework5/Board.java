package edu.postech.csed332.homework5;

import org.jetbrains.annotations.NotNull;

/**
 * An even-odd Sudoku board
 */
public class Board
{
    // TODO: add private member variables for Board
    private Cell[][] cells;

    /**
     * Creates an even-odd Sudoku board
     *
     * @param game an initial configuration
     */
    Board(@NotNull GameInstance game)
    {
        // TODO: implement this done
         cells = new Cell[9][9];
         for(int i = 0; i < 9; i++)
         {
             for(int j = 0; j < 9; j++)
             {
                 if (game.isEven(i, j)) cells[i][j] = new Cell(Cell.Type.EVEN);
                 else cells[i][j] = new Cell(Cell.Type.ODD);
                 if (game.getNumbers(i, j).isPresent()) cells[i][j].setNumber(game.getNumbers(i, j).get());
             }
         }

        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                Group rowGroup = this.getRowGroup(i);
                Group columnGroup = this.getColGroup(j);
                Group squareGroup = this.getSquareGroup(i / 3, j / 3);

                for(int n = 1; n <= 9; n++)
                {
                    if (!rowGroup.isAvailable(n) || !columnGroup.isAvailable(n) || !squareGroup.isAvailable(n))
                    {
                        cells[i][j].removePossibility(n);
                    }
                }
            }
        }
    }

    /**
     * Returns a cell in the (i+1)-th row of (j+1)-th column, where 0 <= i, j < 9.
     *
     * @param i a row index
     * @param j a column index
     * @return a cell in the (i+1)-th row of (j+1)-th column
     */
    @NotNull
    Cell getCell(int i, int j)
    {
        // TODO: implement this done
        return cells[i][j];
    }

    /**
     * Returns a group for the (i+1)-th row, where 0 <= i < 9.
     *
     * @param i a row index
     * @return a group for the (i+1)-th row
     */
    @NotNull
    Group getRowGroup(int i)
    {
        // TODO: implement this done
        Group rowGroup = new Group();

        for(int j = 0; j < 9; j++)
        {
            rowGroup.addCell(cells[i][j]);
        }
        return rowGroup;
    }

    /**
     * Returns a group for the (j+1)-th column, where 0 <= j < 9.
     *
     * @param j a column index
     * @return a group for the (j+1)-th column
     */
    @NotNull
    Group getColGroup(int j)
    {
        // TODO: implement this done
        Group colGroup = new Group();

        for(int i = 0; i < 9; i++)
        {
            colGroup.addCell(cells[i][j]);
        }
        return colGroup;
    }

    /**
     * Returns a square group for the (n+1)-th row of (m+1)-th column, where 1 <= n, m <= 3
     *
     * @param n a square row index
     * @param m a square column index
     * @return a square group for the (n+1)-th row of (m+1)-th column
     */
    @NotNull
    Group getSquareGroup(int n, int m)
    {
        // TODO: implement this done
        Group squareGroup = new Group();
        for(int i = n * 3; i < (n + 1) * 3; i++)
        {
            for(int j = m * 3; j < (m + 1) * 3; j++)
            {
                squareGroup.addCell(cells[i][j]);
            }
        }
        return squareGroup;
    }
}

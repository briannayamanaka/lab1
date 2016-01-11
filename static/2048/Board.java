/*
 * * Name: Brianna Torres Yamanaka
 * * Login: cs8bahu 
 * * Date: January 25, 2015
 * * File: Board.java
 * * Sources of Help: Prof Leo, TA's, tutors, textbooks, piazza, java api
 * *
 * * Describe:this class contains constructors for the two  boards we
 * * can create. i.e a board with no input file previously selected and the 
 * * board with an input file to grab the board from. Contains methods to save
 * * the board to a file and add a random tile to a board.
 * */

/*
//------------------------------------------------------------------//
// Board.java                                                       //
//                                                                  //
// Class used to represent a 2048 game board                        //
//                                                                  //
// Author:  Brandon Williams                                        //
// Date:    1/17/15                                                 //
//------------------------------------------------------------------//

 *      0   1   2   3
 *  0   -   -   -   -
 *  1   -   -   -   -
 *  2   -   -   -   -
 *  3   -   -   -   -
 *
 *  The sample board shows the index values for the columns and rows
 */

import java.util.*;
import java.io.*;


/* Class:Board.java
 * Purpose: This cass contains two Board constructors, one of which
 * constructs a board from sratch and another which just loads an 
 * already existing board.This class also contains various methods
 * that allow boards to be printed, saved, and properly played with.
 * Paramater: none
 * Return: void
 */

public class Board
{
  public final int NUM_START_TILES = 2;
  public final int TWO_PROBABILITY = 90;
  public final int GRID_SIZE;

  //instance variables
  private final Random random;
  private int[][] grid;
  private int score;

  // Constructs a fresh board with random tiles //NO INPUTFILE
  public Board(int boardSize, Random random)
  {
    //assign parameter "random" to final variable random
    this.random = random;

    //assign parameter "boardSize" to final variable "GRID_SIZE"
    GRID_SIZE = boardSize; 

    //set the grid sizes to be the final int GRID_SIZE
    grid = new int[GRID_SIZE][GRID_SIZE];

    //score will initially be zero
    score = 0; 

    //loop through board twice and add a random tile each time
    for (int i = 0; i < NUM_START_TILES; i++)
    {
      addRandomTile();
    }
  }    

  // Construct a board based off of an input file 
  public Board(String inputBoard, Random random) throws IOException
  {
    //inputBoard specifies the file from which we're grabbing our board from
    File inputFile = new File(inputBoard);

    //create a scanner to get score, size and grid from input file
    Scanner scanner = new Scanner (inputFile);

    //initialize GRID_SIZE to be the very first int
    GRID_SIZE = scanner.nextInt();

    //initialize score to be the second int
    score = scanner.nextInt();

    //initialize grid withthe right gridsizes
    grid = new int[GRID_SIZE][GRID_SIZE];

    //initialize grid to be every value after that
    for (int i = 0; i < grid.length; i++)
    {
      for (int j = 0; j < grid.length; j++)
      {
        grid[i][j] = scanner.nextInt();
      } 
    }
    scanner.close();

    //initialize random
    this.random = random;
  }

  /* Method: public void saveBoard()
   * Purpose: this method saves the current board to a file by
   * creating a new file that takes in outputboard using the 
   * PrintWriter object. 
   * Paramater:String outputBoard
   * Return: void
   */

  public void saveBoard(String outputBoard) throws IOException
  {
    //the file we will save the board to
    File outputFile = new File(outputBoard);

    //create printwriter object
    PrintWriter printWriter = new PrintWriter(outputFile);

    //method println prints objects & strings to this new file, outputBoard
    printWriter.println(GRID_SIZE);
    printWriter.println(score);

    //go through the grid that you want to print out  
    for (int i = 0; i < grid.length; i++)
    {
      for (int j = 0; j < grid.length; j++)
      {
        //print each tile in the grid and then a space after
        printWriter.print(grid[i][j] + " ");
      }
      //print each row on a new line
      printWriter.println();
    }
    //you need to close the printWriter
    printWriter.close();
  }

  /* Method: public void addRandomTile() 
   * Purpose:this method loops through every element on
   * our board, and counts how many tiles are empty.Then it
   * randomly chooses one of those tiles and changes it's value
   * from zero to either two or four. If there are no empty tiles
   * then the method returns without changing the board. 
   * Paramater: none
   * Return: void
   */

  public void addRandomTile()
  {
    //initialize count, which keeps track of how many empty tiles you have
    int count = 0;

    //initialize the variables we'll use later in this method
    int location;
    int value;

    //loop through the grid to look for empty tiles
    for (int i = 0; i < grid.length; i++)
    {
      for (int j = 0; j < grid.length; j++)
      {
        if (grid[i][j] == 0)
        {
          //if while looping you encounter an empty tile, add it to the count
          count++;
        }
      }
    }
    //if you have no empty tiles after looping through the whole thing, exit
    if (count == 0)
    {
      return;
    } 
    //however, if you do have available tiles (count = how many empty tiles)
    else
    {
      //initialize new counter that's gonna compare EACH empty tile
      int count1 = 0;

      //my location (empty spot where a new 2/4 is gonna spawn
      location = random.nextInt(count);

      //get a random int called value between 0 and 99
      value = random.nextInt(100);

      //go through array to find that location so we can put a 2/4 in there
      for (int i = 0; i < grid.length; i++)
      {
        for (int j = 0; j < grid.length; j++)
        {
          if (grid[i][j] == 0)
          {
            if (count1 == location)
            {
              if (value < TWO_PROBABILITY)
              {
                grid[i][j] = 2;
                return;
              }
              else
              {
                grid[i][j] = 4;
                return;
              }
            }
            count1++;
          }
        } 
      }
    }
  }

  // Return the score
  public int getScore()
  {
    return score;
  } 

  /* Method: isGameOver()
   * Purpose:isGameOver checks to see if the game is over by
   * looping through the board and checking to see if any of the 
   * tiles can move. If the tiles can't be moved in any of the
   * four directions, game is over. 
   * Paramater: none
   * Return: boolean 
   */

  public boolean isGameOver()
  {
    //if you can't move in any of the four directions
    if (this.canMoveLeft() == false && this.canMoveUp() == false && 
        this.canMoveDown() == false && this.canMoveRight() == false)
    {
      //print out game over
      System.out.println ("Game Over");
      return true;
    }
    else
    {
      return false;
    }
  }

  /* Method: canMove()
   * Purpose: canMove checks to see if the board can move in the 
   * user's specified direction. We do this by checking each tile
   * that has another tile to its direction that the user inputs
   * and seeing if it can either merge with another tile that has
   * the same value or slide across if a tile is empty.
   * Paramater:Direction direction 
   * Return:boolean 
   */

  public boolean canMove(Direction direction)
  {
    if (direction.equals(Direction.UP))
    {
      if (this.canMoveUp() == true)
      {
        return true;
      }
    }
    else if (direction.equals(Direction.DOWN))
    {
      if (this.canMoveDown() ==true)
      {
        return true;
      }
    } 
    else if (direction.equals(Direction.LEFT))
    {
      if (this.canMoveLeft() == true)
      {
        return true;
      }
    }
    else if (direction.equals(Direction.RIGHT))
    {
      if (this.canMoveRight() == true)
      {
        return true;
      }    
    }
    return false;
  }

  /*
   * * Name: Helper methods for canMove() 
   * * Purpose: these methods check to see of tiles can move in any one 
   * * specific direction. 
   * * Parameter: none 
   * * Return: boolean. ie, if the tiles on the board can move up, method will 
   * * return true.
   * */

  //Determine if we can move up
  private boolean canMoveUp()
  {
    for (int i = 1; i < grid.length; i++)
    {
      for (int j = 0; j < grid.length; j++)
      {
        if ((grid[i][j] != 0 && grid[i][j] == grid[i-1][j]) || (grid[i][j] != 0 
              && grid[i-1][j] == 0))
        {
          return true;
        }
      }
    }
    return false;
  }


  //Determine if we can move down 
  private boolean canMoveDown()
  {
    for (int i = 0; i < grid.length -1; i++)
    {
      for (int j = 0; j < grid.length; j++)
      {
        if ((grid[i][j] != 0 && grid[i][j] == grid[i+1][j]) || (grid[i][j] != 0  
              && grid[i+1][j] == 0))
        {
          return true;
        }
      }
    }
    return false;
  }

  //Determine if we can move left
  private boolean canMoveLeft()
  {
    for (int i = 0; i < grid.length; i++)
    {
      for (int j = 1; j < grid.length; j++)
      {
        if ((grid[i][j] != 0 && grid[i][j] == grid[i][j-1]) || (grid[i][j] != 0 
              && grid[i][j-1] == 0))
        {
          return true;
        }
      }
    }
    return false;
  }

  //Determine if we can move right
  private boolean canMoveRight()
  {
    for (int i = 0; i < grid.length; i++)
    {
      for (int j = 0; j < grid.length -1; j++)
      {
        if ((grid[i][j] != 0 && grid[i][j] == grid[i][j+1]) || (grid[i][j] != 0 
              && grid[i][j+1] == 0))
        {
          return true;
        }
      }
    }
    return false;
  }

  /*
   * * Name: move()
   * * Purpose: this method performs the actual movement of the tiles. It
   * * either combines tiles to increase your score or shifts tiles over in
   * * the case of empty elements in the grid.
   * * Parameters: none 
   * * Return: returns a boolean. if the board can move, it returns true and
   * * moves to specified direction.
   * */

  public boolean move(Direction direction)
  {
    if (direction.equals (Direction.RIGHT))
    {
      this.moveRight();
      return true;
    }

    else if (direction.equals (Direction.LEFT))
    {
      this.moveLeft();
      return true;
    }

    else if (direction.equals (Direction.UP))
    {
      this.moveUp();
      return true;
    }
    else if (direction.equals (Direction.DOWN))
    {
      this.moveDown();
      return true;
    }
    return false;
  }

  /*
   * * Name: moveDirection() 
   * * Purpose: these helper methods move the tiles on the board 
   * * in one of the four specified directions 
   * * Parameters: moveRight(), moveLeft(), moveUp(), and moveDown()
   * * take no parameters 
   * * Return: void
   * */
  private boolean moveRight()
  {
    if (this.canMoveRight() == true)
    {
      for (int i = 0; i < grid.length; i++)
      {
        this.shiftRight(i);
        for (int j = grid.length -1; j > 0; j--)
        {
          if (grid[i][j] == grid[i][j-1])
          {
            grid[i][j] *= 2;

            grid[i][j-1] = 0;

            this.shiftRight(i);

            score += grid[i][j];
          }
        }
      }
    }
    return true;
  }

  private boolean moveLeft()
  {
    if (this.canMoveLeft() == true)
    {
      for (int i = 0; i < grid.length; i++)
      {
        this.shiftLeft(i);
        for (int j = 0; j < grid.length -1; j++)
        {
          if (grid[i][j] == grid[i][j+1])
          {
            grid[i][j] *= 2;

            grid [i][j+1] = 0;

            this.shiftLeft(i);

            score += grid[i][j];
          }
        }
      }
    }
    return true;
  }

  private boolean moveUp()
  {
    if (this.canMoveUp() == true)
    {
      for (int j = 0; j < grid.length; j++)
      {
        this.shiftUp(j);
        for (int i = 0; i < grid.length-1; i++)
        {
          if (grid[i][j] == grid[i+1][j])
          {
            grid[i][j] *= 2;

            grid[i+1][j] = 0;

            this.shiftUp(j);

            score += grid[i][j];
          }
        }
      }
    }
    return true;
  }

  private boolean moveDown()
  {
    if (this.canMoveDown() == true)
    {
      for (int j = 0; j < grid.length; j++)
      {
        this.shiftDown(j);
        for (int i = grid.length-1; i > 0; i--)
        {
          if (grid[i][j] == grid[i-1][j])
          {
            grid[i][j] *= 2;

            grid[i-1][j] = 0;

            this.shiftDown(j);

            score += grid[i][j];
          }
        }
      }
    }
    return true;
  }

  /*
   * * Name: shiftDirection(int i) 
   * * Purpose: Purpose of this method is to shift all of the tiles
   * * in one of the four directions to eliminate any empty tiles.
   * * Paramaters: int i (the row) 
   * * Return: void 
   * */ 

  private boolean shiftRight(int i)
  {
    for (int j = grid.length -1; j > 0; j--)
    {
      for (int index = j-1; index >= 0; index--)
      {
        if(grid[i][j] == 0 && grid[i][index] !=0)
        {
          grid[i][j] = grid[i][index];
          grid[i][index] = 0;

        }
      }
    }
    return true; 
  }

  private boolean shiftLeft(int i)
  {
    for (int j = 0; j < grid.length -1; j++)
    {
      for (int index = j+1; index < grid.length; index++)
      {
        if (grid[i][j] == 0 && grid[i][index] != 0)
        {
          grid[i][j] = grid[i][index];
          grid[i][index] = 0;
        }
      }  
    }
    return true;
  }

  private boolean shiftUp(int j)
  {
    for (int i = 0; i < grid.length-1; i++)
    {
      for (int index = i+1; index < grid.length; index++)
      {
        if (grid[i][j] == 0 && grid[index][j] != 0)
        {
          grid [i][j] = grid[index][j];
          grid[index][j] = 0;
        }
      }
    }
    return true;
  }

  private boolean shiftDown(int j)
  {
    for (int i = grid.length-1; i > 0; i--)
    {
      for (int index = i-1; index >= 0; index--)
      {
        if (grid[i][j] == 0 && grid[index][j] != 0)
        {
          grid[i][j] = grid[index][j];
          grid[index][j] = 0;
        }
      }
    }
    return true;
  }

  // Return the reference to the 2048 Grid
  public int[][] getGrid()
  {
    return grid;
  }

  @Override
  public String toString()
  {
    StringBuilder outputString = new StringBuilder();
    outputString.append(String.format("Score: %d\n", score));
    for (int row = 0; row < GRID_SIZE; row++)
    {
      for (int column = 0; column < GRID_SIZE; column++)
        outputString.append(grid[row][column] == 0 ? "    -" :
            String.format("%5d", grid[row][column]));

      outputString.append("\n");
    }
    return outputString.toString();
  }
}


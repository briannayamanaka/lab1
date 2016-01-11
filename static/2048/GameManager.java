/*
 * * Name: Brianna Torres Yamanaka
 * * Login: cs8bahu 
 * * Date: January 25, 2015
 * * File: GameManager.java
 * * Sources of Help: Prof Leo, TA's, tutors, textbooks, piazza, java api
 * * Describe: Program creates a game that takes in input from user and
 * * and if valid, will perform indicated moves until game is won, lost
 * * or quit and saved. 
 * */

//------------------------------------------------------------------//
// GameManager.java                                                 //
//                                                                  //
// Game Manager for 2048                                            //
//                                                                  //
// Author:  Brandon Williams                                        //
// Date:    1/17/15                                                 //
//------------------------------------------------------------------//

import java.util.*;
import java.io.*;


/* Class: GameManager.java 
 * Purpose:This class contains two GameManager constructors which
 * allow us to start a game with either a file that already exists
 * or start a game from scratch. It also lets us save our game 
 * so that we can come back to it later. In this class we initialize
 * constructors so we can use them elsewhere and we create the play
 * and printControls methods. 
 * Paramater: none
 * Return: void
 */

public class GameManager
{
  // Instance variables
  private Board board;    // The actual 2048 board
  private String outputFileName;  // File to save the board to when exiting

  // GameManager Constructor
  // Generates new game
  GameManager(int boardSize, String outputBoard, Random random)
  {
    //assign outputBoard to the instance variable outputFilename (initialize)
    System.out.println(outputBoard);
    outputFileName = outputBoard;

    //if board size is  2x2 or greater
    if (boardSize >=2){

      //initialize instance variable board, create a board
      board = new Board(boardSize, random);
    }
    else
    {
      //let user know the number they put in is too small
      System.out.println ("Your board size has to be >= 2");
    }
  }

  // GameManager Constructor
  // Load a saved game
  GameManager(String inputBoard, String outputBoard, Random random)
    throws IOException
  {
    //inititialize instance variable board
    // load a board using the filename passed in via the inputBoard parameter
    board = new Board(inputBoard, random); 

    //assign outputBoard to outputFileName
    outputFileName = outputBoard;
  }

  /* Method: public void play()
   * Purpose: this method prints out a list of the controls and the
   *          current 2048 board. Then it prompts the user for a command
   *          and checks to see if its valid and performs the move and 
   *          adds a new random tile to the board.It repeapts this process
   *          until game is over or the user inputs "q" which will quit
   *          and save the board. 
   * Paramater: none
   * Return: void
   */

  public void play() throws IOException
  {
    // prints the controls used to operate the game.
    printControls();

    //then prints out current state of the 2048 board to the console
    System.out.println(board.toString());

    //prompt user for command using scanner to read input from console
    Scanner scanner = new Scanner(System.in);

    //infinite loop: while true is true: 
    while(true)
    {    
      //name the user input "input" 
      String input = scanner.next();

      // if user inputs w
      if (input.equals("w"))
      {
        // and if board can move in w's direction
        if(board.canMove(Direction.UP))
        {
          //then move it in the w direction
          board.move(Direction.UP);

          //and add a random tile to the board
          board.addRandomTile(); 

          //print out the board
          System.out.println(board.toString()); 
        }
      }
      //if user inputs s
      else if (input.equals("s"))
      {
        //and if board can move in s's direction
        if(board.canMove(Direction.DOWN))
        {
          //then move the tiles in the s direction
          board.move(Direction.DOWN); 

          //and add a random tile to the board
          board.addRandomTile();  

          //print out the board
          System.out.println(board.toString()); 
        }
      }
      //if user inputs a
      else if (input.equals("a"))
      {
        //and if tiles can move in the a direction
        if (board.canMove(Direction.LEFT))
        {
          //then move the tiles in the a direction
          board.move(Direction.LEFT);

          //and add a random tile to the board
          board.addRandomTile();  

          //print out the board
          System.out.println(board.toString()); 
        }
      }
      //if user inputs d
      else if (input.equals("d"))
      {
        //and if the board can move in the d direction
        if (board.canMove(Direction.RIGHT) == true)
        {
          //then move the tiles to the d direction
          board.move(Direction.RIGHT); 

          //and add a random tile to the board
          board.addRandomTile();  

          //print out the board
          System.out.println(board.toString()); 
        }
      }
      //if user inputs q
      else if (input.equals("q"))
      {
        //then save the board with whatever filename the user indicates  
        board.saveBoard(outputFileName); 

        //exit cause game's being quit and saved
        break;
      }
      //if user inputs anything other than s, a, d, q, or w
      else
      { 
        //let user know they need to put in either s, a d, q, or w
        System.out.println("Invalid entry. Below are valid commands.");
        printControls();
      }
      if (board.isGameOver() == true)
      {
        System.out.println ("Game Over");
      }
    }
  }

  /* Method: private void printControls() 
   * Purpose: this method prints out a list of the controls that are valid 
   * Paramater: none
   * Return: void
   */

  private void printControls()
  {
    System.out.println("  Controls:");
    System.out.println("    w - Move Up");
    System.out.println("    s - Move Down");
    System.out.println("    a - Move Left");
    System.out.println("    d - Move Right");
    System.out.println("    q - Quit and Save Board");
    System.out.println();
  }
}

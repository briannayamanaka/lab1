/*
 * Name: Brianna Torres Yamanaka
 * Login: cs8bahu
 * Date: March 2, 2015
 * File: Gui2048.java
 * Sources of Help: Prof Leo, TAs, tutors, textbooks,
 * piazza, java api
 * Describe:Gui4048.java is a file that contains public
 * class Gui2048 and an inner class that handles the
 * events (directional arrow key pressing)
 */

import javafx.application.*;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import java.util.*;
import java.io.*;

/*
 * Class name:Gui2048
 * Purpose:this class contains the code that uses
 * the javafx methods and objects to create a
 * graphical user interface that immitates the look
 * and functionality of the original 2048 game.
 * Parameters:none
 * Return:none
 */
public class Gui2048 extends Application
{
  ////////////////////////////Given Instance Variables//////////////////////////

  private String outputBoard;
  private Board board;
  private int[][] grid;
  private Rectangle[][] rectangleArray;
  private Text[][] textArray;
  private int[][] updatedGrid;
  private Text title;
  private Text score;
  private GridPane pane;
  private Scene scene;
  private static final Color COLOR_EMPTY = Color.rgb(238, 228, 218, 0.35);
  private static final Color COLOR_2 = Color.rgb(238, 228, 218);
  private static final Color COLOR_4 = Color.rgb(237, 224, 200);
  private static final Color COLOR_8 = Color.rgb(242, 177, 121);
  private static final Color COLOR_16 = Color.rgb(245, 149, 99);
  private static final Color COLOR_32 = Color.rgb(246, 124, 95);
  private static final Color COLOR_64 = Color.rgb(246, 94, 59);
  private static final Color COLOR_128 = Color.rgb(237, 207, 114);
  private static final Color COLOR_256 = Color.rgb(237, 204, 97);
  private static final Color COLOR_512 = Color.rgb(237, 200, 80);
  private static final Color COLOR_1024 = Color.rgb(237, 197, 63);
  private static final Color COLOR_2048 = Color.rgb(237, 194, 46);
  private static final Color COLOR_OTHER = Color.BLACK;
  private static final Color COLOR_GAME_OVER = Color.rgb(238, 228, 218, 0.73);
  private static final Color COLOR_VALUE_LIGHT = Color.rgb(249, 246, 242);
  private static final Color COLOR_VALUE_DARK = Color.rgb(119, 110, 101);
  private static final Color COLOR_BACKGROUND = Color.rgb(187,173,160);
  private static final Color COLOR_GAMEOVER = Color.rgb(187,173,160,.65);
  private static final Color COLOR_WHITE = Color.rgb(255,255,255);

  //////////////////////////////My Instance Variables//////////////////////////

  private final int two = 2;
  private final int three = 3;
  private final int four = 4;
  private final int five = 5;
  private final int six = 6;
  private final int eight = 8;
  private final int ten = 10;
  private final int sixteen = 16;
  private final int twenty = 20;
  private final int twentyfive = 25;
  private final int thirty = 30;
  private final int thirtytwo = 32;
  private final int sixtyfour = 64;
  private final int seventy = 70;
  private final int onehundredtwentyeight = 128;
  private final int twohundredfiftysix = 256;
  private final int fourhundred = 400;
  private final int fivehundred = 500;
  private final int fivehundredtwelve = 512;
  private final int onethousandtwentyfour = 1024;
  private final int twothousandfortyeight = 2048;
  private final double insetp1 = 11.5;
  private final double insetp2 = 12.5;
  private final double insetp3 = 13.5;
  private final double insetp4 = 14.5;

  /*
   * Name:start
   * Purpose:start method is the main entry point
   * for our JavaFX application. Called after
   * system is ready for app to being running.
   * Parameters:Stage primaryStage
   * Return:void
   */
  @Override
    public void start(Stage primaryStage)
    {
      //set textArray to not be null
      rectangleArray = new Rectangle[sixteen][sixteen];

      //set textArray to not be null
      textArray = new Text[sixteen][sixteen];

      // Process Arguments and Initialize the Game Board
      processArgs(getParameters().getRaw().toArray(new String[0]));

      //create a gridpane
      pane = new GridPane();

      //create a scene that will contain the pane
      scene = new Scene(pane,fourhundred,fourhundred);

      //make the pane pretty
      pane.setStyle("-fx-background-color: rgb(187,173,160)");
      pane.setAlignment(Pos.CENTER);
      pane.setPadding(new Insets(25, insetp4, insetp4, insetp4));
      pane.setHgap(five);
      pane.setVgap(five);

      //pane.setGridLinesVisible(true);

      //make a new grid that's thats actually your board's grid
      grid = this.board.getGrid();

      //create text that reads 2048 and add it to pane
      title = new Text("2048");
      title.setFont(Font.font("Verdana", FontPosture.ITALIC,thirty));
      pane.add(title,0,0,two,1);

      //create text that reads score and the score and add it to pane
      score = new Text("Score: " + this.board.getScore());
      score.setFont(Font.font("Verdana",twentyfive));
      pane.add(score,two,0,three,1);

      //sets the scene to have a handler that handles key presses 
      scene.setOnKeyPressed(new KeyHandler());

      //loop through board and create a new rect and text at every grid element
      for(int i = 0; i < board.GRID_SIZE; i++)
      {
        for(int j = 0; j < board.GRID_SIZE; j++)
        {
          //create sixteen new rectangles and make them pretty
          rectangleArray[i][j] = new Rectangle(seventy,seventy,seventy,seventy);
          rectangleArray[i][j].setArcWidth(ten);
          rectangleArray[i][j].setArcHeight(ten);

          //add rectangle to grid
          pane.add(rectangleArray[i][j], j, i+1);
          pane.setAlignment(Pos.CENTER);

          //create a text on every index that is the number on the tile
          textArray[i][j] = new Text("" + grid[i][j]);

          //set color of rectangles and texts on indicated grid indeces
          assignColor(rectangleArray[i][j], textArray[i][j], grid[i][j]);

          //add text to grid in front of rectangle
          pane.add(textArray[i][j], j, i+1);
          textArray[i][j].setFont(Font.font("Verdana",FontWeight.BOLD,sixteen));
          textArray[i][j].setTextAlignment(TextAlignment.CENTER);

          //put scene on stage and show everything
          primaryStage.setScene(scene);
          primaryStage.setTitle("2048");
          //primaryStage.setResizable(false);
          primaryStage.show();

          //just so i can see it whats goin on, on the terminal
          System.out.println(grid[i][j]);
          System.out.println(board);
        }
      }
    }

  //////////////////////////////My Instance Methods//////////////////////////

  /*
   * Name:assignColor
   * Purpose:assign color checks the value of every tile
   * on the grid and colors the rectangle depending on what
   * number it contains.
   * Parameters:Rectangle rectangle, Text text, int value
   * Return:void
   */
  private void assignColor(Rectangle rectangle, Text text, int value)
  {
    if(value == 0)
    {
      String string = Integer.toString(value);
      text.setText(" ");
      text.setFill(Color.rgb(238, 228, 218,.35));
      rectangle.setStroke(Color.rgb(238, 228, 218,.35));
      rectangle.setFill(Color.rgb(238, 228, 218,.35));
    }
    else if(value == two)
    {
      text.setFill(COLOR_VALUE_DARK);
      GridPane.setHalignment(text,HPos.CENTER);
      GridPane.setHalignment(text,HPos.CENTER);
      rectangle.setStroke(COLOR_2);
      rectangle.setFill(COLOR_2);
    }
    else if(value == four)
    {
      text.setFill(COLOR_VALUE_DARK);
      GridPane.setHalignment(text,HPos.CENTER);
      GridPane.setHalignment(text,HPos.CENTER);
      rectangle.setStroke(COLOR_4);
      rectangle.setFill(COLOR_4);
    }
    else if(value == eight)
    {
      text.setFill(COLOR_WHITE);
      GridPane.setHalignment(text,HPos.CENTER);
      GridPane.setHalignment(text,HPos.CENTER);
      rectangle.setStroke(COLOR_8);
      rectangle.setFill(COLOR_8);
    }
    else if(value == sixteen)
    {
      text.setFill(COLOR_WHITE);
      GridPane.setHalignment(text,HPos.CENTER);
      GridPane.setHalignment(text,HPos.CENTER);
      rectangle.setStroke(COLOR_16);
      rectangle.setFill(COLOR_16);
    }
    else if(value == thirtytwo)
    {
      text.setFill(COLOR_WHITE);
      GridPane.setHalignment(text,HPos.CENTER);
      GridPane.setHalignment(text,HPos.CENTER);
      rectangle.setStroke(COLOR_32);
      rectangle.setFill(COLOR_32);
    }
    else if(value == sixtyfour)
    {
      text.setFill(COLOR_WHITE);
      GridPane.setHalignment(text,HPos.CENTER);
      GridPane.setHalignment(text,HPos.CENTER);
      rectangle.setStroke(COLOR_64);
      rectangle.setFill(COLOR_64);
    }
    else if(value == onehundredtwentyeight)
    {
      text.setFill(COLOR_WHITE);
      GridPane.setHalignment(text,HPos.CENTER);
      GridPane.setHalignment(text,HPos.CENTER);
      rectangle.setStroke(COLOR_128);
      rectangle.setFill(COLOR_128);
    }
    else if(value == twohundredfiftysix)
    {
      text.setFill(COLOR_WHITE);
      GridPane.setHalignment(text,HPos.CENTER);
      GridPane.setHalignment(text,HPos.CENTER);
      rectangle.setStroke(COLOR_256);
      rectangle.setFill(COLOR_256);
    }
    else if(value == fivehundredtwelve)
    {
      text.setFill(COLOR_WHITE);
      GridPane.setHalignment(text,HPos.CENTER);
      GridPane.setHalignment(text,HPos.CENTER);
      rectangle.setStroke(COLOR_512);
      rectangle.setFill(COLOR_512);
    }
    else if(value == onethousandtwentyfour)
    {
      text.setFill(COLOR_WHITE);
      GridPane.setHalignment(text,HPos.CENTER);
      GridPane.setHalignment(text,HPos.CENTER);
      rectangle.setStroke(COLOR_1024);
      rectangle.setFill(COLOR_1024);
    }
    else if(value == twothousandfortyeight)
    {
      text.setFill(COLOR_WHITE);
      GridPane.setHalignment(text,HPos.CENTER);
      GridPane.setHalignment(text,HPos.CENTER);
      rectangle.setStroke(COLOR_2048);
      rectangle.setFill(COLOR_2048);
    }
  }

  /*
   * Class name:KeyHandler
   * Purpose:KeyHandler is a private inner class that
   * implements EventHandler and handles a KeyEvent.
   * This class allows key presses to be registered
   * and properly handled so that our gui reflects the
   * the changes in our board when 2048 is played.
   * Parameters:none
   * Return:none
   */
  private class KeyHandler implements EventHandler<KeyEvent>
  {
    /*
     * Name:handle
     * Purpose:this method handles keypresses and updates
     * the grid to reflect the changes in our board when
     * 2048 is played.
     * Parameters:KeyEvent e
     * Return:void
     */
    @Override
      public void handle(KeyEvent e)
      {
        if(!board.isGameOver())
        {
          if(e.getCode() == KeyCode.DOWN)
          {
            if(board.canMove(Direction.DOWN))
            {
              //update the board and create an updated grid
              board.move(Direction.DOWN);
              board.addRandomTile();
              score.setText("Score: " + board.getScore());
              System.out.println("Moving <Down>");
              updatedGrid = board.getGrid();

              //update the gui grid
              for(int i = 0; i < board.GRID_SIZE; i++)
              {
                for(int j = 0; j < board.GRID_SIZE; j++)
                {
                  textArray[i][j].setText(" " + updatedGrid[i][j]);
                  assignColor(rectangleArray[i][j], textArray[i][j], updatedGrid[i][j]);
                }
              }
            }
          }
          else if(e.getCode() == KeyCode.UP)
          {
            if(board.canMove(Direction.UP))
            {
              //update the board and create and updated grid
              board.move(Direction.UP);
              board.addRandomTile();
              score.setText("Score: " + board.getScore());
              System.out.println("Moving <Up>");
              updatedGrid = board.getGrid();

              //update the gui grid
              for(int i = 0; i < board.GRID_SIZE; i++)
              {
                for(int j = 0; j < board.GRID_SIZE; j++)
                {
                  textArray[i][j].setText(" " + updatedGrid[i][j]);
                  assignColor(rectangleArray[i][j], textArray[i][j], updatedGrid[i][j]);
                }
              }
            }
          }

          else if(e.getCode() == KeyCode.RIGHT)
          {
            if(board.canMove(Direction.RIGHT))
            {
              //update the board and create an updated grid
              board.move(Direction.RIGHT);
              board.addRandomTile();
              score.setText("Score: " + board.getScore());
              System.out.println("Moving <Right>");
              updatedGrid = board.getGrid();

              //update the gui grid
              for(int i = 0; i < board.GRID_SIZE; i++)
              {
                for(int j = 0; j < board.GRID_SIZE; j++)
                {
                  textArray[i][j].setText(" " + updatedGrid[i][j]);
                  assignColor(rectangleArray[i][j], textArray[i][j], updatedGrid[i][j]);
                }
              }
            }
          }

          else if(e.getCode() == KeyCode.LEFT)
          {
            if(board.canMove(Direction.LEFT))
            {
              //update the board and create an updated grid
              board.move(Direction.LEFT);
              board.addRandomTile();
              score.setText("Score: " + board.getScore());
              System.out.println("Moving <Left>");
              updatedGrid = board.getGrid();

              //update the gui grid
              for(int i = 0; i < board.GRID_SIZE; i++)
              {
                for(int j = 0; j < board.GRID_SIZE; j++)
                {
                  textArray[i][j].setText(" " + updatedGrid[i][j]);
                  assignColor(rectangleArray[i][j], textArray[i][j], updatedGrid[i][j]);
                }
              }
            }
          }

          else if(e.getCode() == KeyCode.S)
          {
            System.out.println("Saving board to <" + outputBoard + ">");
            try
            {
              //save board to output Board
              board.saveBoard(outputBoard);
            }
            catch (Exception b){}
          }
        }
        else
        {
          scene.setOnKeyPressed(null);
          System.out.println("Game Over");

          //let a playa know the game's over
          Rectangle gameOverRectangle = new Rectangle(fourhundred,fivehundred);
          gameOverRectangle.setFill(COLOR_GAMEOVER);
          pane.add(gameOverRectangle,0,0,six,six);
          Text gameOverText = new Text("     Game Over");
          gameOverText.setTextAlignment(TextAlignment.CENTER);
          gameOverText.setFont(Font.font("Verdana",thirtytwo));
          pane.add(gameOverText,0,0,six,six);
          return;
        }
      }
  }

  /** DO NOT EDIT BELOW */

  // The method used to process the command line arguments
  private void processArgs(String[] args)
  {
    String inputBoard = null;   // The filename for where to load the Board
    int boardSize = 0;          // The Size of the Board

    // Arguments must come in pairs
    if((args.length % 2) != 0)
    {
      printUsage();
      System.exit(-1);
    }

    // Process all the arguments 
    for(int i = 0; i < args.length; i += 2)
    {
      if(args[i].equals("-i"))
      {   // We are processing the argument that specifies
        // the input file to be used to set the board
        inputBoard = args[i + 1];
      }
      else if(args[i].equals("-o"))
      {   // We are processing the argument that specifies
        // the output file to be used to save the board
        outputBoard = args[i + 1];
      }
      else if(args[i].equals("-s"))
      {   // We are processing the argument that specifies
        // the size of the Board
        boardSize = Integer.parseInt(args[i + 1]);
      }
      else
      {   // Incorrect Argument 
        printUsage();
        System.exit(-1);
      }
    }

    // Set the default output file if none specified
    if(outputBoard == null)
      outputBoard = "2048.board";
    // Set the default Board size if none specified or less than 2
    if(boardSize < 2)
      boardSize = 4;

    // Initialize the Game Board
    try{
      if(inputBoard != null)
        board = new Board(inputBoard, new Random());
      else
        board = new Board(boardSize, new Random());
    }
    catch (Exception e)
    {
      System.out.println(e.getClass().getName() + " was thrown while creating a " +
          "Board from file " + inputBoard);
      System.out.println("Either your Board(String, Random) " +
          "Constructor is broken or the file isn't " +
          "formated correctly");
      System.exit(-1);
    }
  }

  // Print the Usage Message 
  private static void printUsage()
  {
    System.out.println("Gui2048");
    System.out.println("Usage:  Gui2048 [-i|o file ...]");
    System.out.println();
    System.out.println("  Command line arguments come in pairs of the form: <command> <argument>");
    System.out.println();
    System.out.println("  -i [file]  -> Specifies a 2048 board that should be loaded");
    System.out.println();
    System.out.println("  -o [file]  -> Specifies a file that should be used to save the 2048 board");
    System.out.println("                If none specified then the default \"2048.board\" file will be used");
    System.out.println("  -s [size]  -> Specifies the size of the 2048 board if an input file hasn't been");
    System.out.println("                specified.  If both -s and -i are used, then the size of the board");
    System.out.println("                will be determined by the input file. The default size is 4.");
  }
}

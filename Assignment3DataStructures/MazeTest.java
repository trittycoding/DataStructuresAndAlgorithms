import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/// <summary>
/// Maze.Test - A class for testing the Maze class
///
/// Assignment:     #3
/// Course:         ADEV-3001
/// Date Created:   March 20th, 2018
///
/// Revision Log
/// Who         When        Reason
/// ----------- ----------- ---------------
///
/// @author: Scott Wachal
/// @version 1.0
/// </summary>
public class MazeTest
{
    // Put the maze files inside of a directory called: src/maze_files/
    private static String mazeWithExitPath = "simpleWithExit.maze";
    private static String mazeWithoutExitPath = "simpleWithoutExit.maze";

    private Point startingPoint;
    private Point endingPoint;

    String basicMazeOutput =
            "WWWWWWWWWWWWW\n" +
            "W     W     W\n" +
            "W WWW W WWW W\n" +
            "W W       W W\n" +
            "W WWWWWWW WWW\n" +
            "W   W   W   W\n" +
            "WWW W WWW   W\n" +
            "W     W   WEW\n" +
            "W WWWWW W WWW\n" +
            "W       W   W\n" +
            "WWWWWWWWWWWWW";

    String basicMazeBreadthSearched =   
            "WWWWWWWWWWWWW\n" +
            "W.....WVVVVVW\n" +
            "WVWWW.WVWWWVW\n" +
            "WVWVV.....W W\n" +
            "WVWWWWWWW.WWW\n" +
            "WVVVWVVVW.VVW\n" +
            "WWWVWVWWW...W\n" +
            "WVVVVVWVVVWEW\n" +
            "WVWWWWW WVWWW\n" +
            "WVVVVV  WV  W\n" +
            "WWWWWWWWWWWWW";

    String basicMazeNoExitSearched =
            "No exit found in maze!\n\n" +
            "WWWWWWWWWWWWW\n" +
            "WVVVVVWVVVVVW\n" +
            "WVWWWVWVWWWVW\n" +
            "WVWVVVVVVVWVW\n" +
            "WVWWWWWWWVWWW\n" +
            "WVVVWVVVWVVVW\n" +
            "WWWVWVWWWVVVW\n" +
            "WVVVVVWVVVWVW\n" +
            "WVWWWWWVWVWWW\n" +
            "WVVVVVVVWVVVW\n" +
            "WWWWWWWWWWWWW";

    String breadthStringPath = 
        "Path to follow from Start [1, 1] to Exit [7, 11] - 17 steps:\n" +
        "[1, 1]\n[1, 2]\n[1, 3]\n[1, 4]\n[1, 5]\n[2, 5]\n[3, 5]\n[3, 6]\n" +
        "[3, 7]\n[3, 8]\n[3, 9]\n[4, 9]\n[5, 9]\n[6, 9]\n[6, 10]" +
        "\n[6, 11]\n[7, 11]\n";

    Stack<Point> breathStackPath;

    char[][] basicMaze;
    char[][] basicMazeNoExit;

    /// <summary>
    /// Sets up the mazes used for the tests.
    /// </summary>
    @BeforeEach
    void setup()
    {
        basicMaze = new char[11][];

        startingPoint = new Point(1, 1);
        endingPoint = new Point(7, 11);
            
        basicMaze[0] =  "WWWWWWWWWWWWW".toCharArray();
        basicMaze[1] =  "W     W     W".toCharArray();
        basicMaze[2] =  "W WWW W WWW W".toCharArray();
        basicMaze[3] =  "W W       W W".toCharArray();
        basicMaze[4] =  "W WWWWWWW WWW".toCharArray();
        basicMaze[5] =  "W   W   W   W".toCharArray();
        basicMaze[6] =  "WWW W WWW   W".toCharArray();
        basicMaze[7] =  "W     W   WEW".toCharArray();
        basicMaze[8] =  "W WWWWW W WWW".toCharArray();
        basicMaze[9] =  "W       W   W".toCharArray();
        basicMaze[10] = "WWWWWWWWWWWWW".toCharArray();


        basicMazeNoExit = new char[11][];
        basicMazeNoExit[0] =  "WWWWWWWWWWWWW".toCharArray();
        basicMazeNoExit[1] =  "W     W     W".toCharArray();
        basicMazeNoExit[2] =  "W WWW W WWW W".toCharArray();
        basicMazeNoExit[3] =  "W W       W W".toCharArray();
        basicMazeNoExit[4] =  "W WWWWWWW WWW".toCharArray();
        basicMazeNoExit[5] =  "W   W   W   W".toCharArray();
        basicMazeNoExit[6] =  "WWW W WWW   W".toCharArray();
        basicMazeNoExit[7] =  "W     W   W W".toCharArray();
        basicMazeNoExit[8] =  "W WWWWW W WWW".toCharArray();
        basicMazeNoExit[9] =  "W       W   W".toCharArray();
        basicMazeNoExit[10] = "WWWWWWWWWWWWW".toCharArray();

        resetReverseStack();        
    }

    private void resetReverseStack() {
        breathStackPath = new Stack<Point>();
        breathStackPath.push(new Point(7, 11));
        breathStackPath.push(new Point(6, 11));
        breathStackPath.push(new Point(6, 10));
        breathStackPath.push(new Point(6, 9));
        breathStackPath.push(new Point(5, 9));
        breathStackPath.push(new Point(4, 9));
        breathStackPath.push(new Point(3, 9));
        breathStackPath.push(new Point(3, 8));
        breathStackPath.push(new Point(3, 7));
        breathStackPath.push(new Point(3, 6));
        breathStackPath.push(new Point(3, 5));
        breathStackPath.push(new Point(2, 5));
        breathStackPath.push(new Point(1, 5));
        breathStackPath.push(new Point(1, 4));
        breathStackPath.push(new Point(1, 3));
        breathStackPath.push(new Point(1, 2));
        breathStackPath.push(new Point(1, 1));
    }

    //region Constructor Tests
    /// <summary>
    /// Checks to make sure the constructor instantializes the appropriate variables
    /// </summary>
    @Test
    public void Maze_Constructor_existing_maze_Test()
    {
        Maze maze = new Maze(startingPoint.getRow(), startingPoint.getColumn(), basicMaze);

        assertEquals(maze.getRowLength(), basicMaze.length);
        assertEquals(maze.getColumnLength(), basicMaze[0].length);
        assertEquals(maze.getStartingPoint().getRow(), startingPoint.getRow());
        assertEquals(maze.getStartingPoint().getColumn(), startingPoint.getColumn());

        char[][] existingMaze = maze.getMaze();

        for (int i = 0; i < existingMaze.length; i++)
        {
            for (int k = 0; k < existingMaze[i].length; k++)
            {
                assertEquals(existingMaze[i][k], basicMaze[i][k]);
            }
        }
    }

    /// <summary>
    /// If the existing maze has an invalid Row, throw an exception
    /// </summary>
    @Test
    public void Maze_Constructor_existingMaze_throws_error_on_negative_row_Test()
    {
        assertThrows(IndexOutOfBoundsException.class, () -> new Maze(-1, startingPoint.getColumn(), basicMaze));
    }
    /// <summary>
    /// If the existing maze has an invalid Column, throw an exception
    /// </summary>
    @Test
    public void Maze_Constructor_existingMaze_throws_error_on_negative_column_Test()
    {
        assertThrows(IndexOutOfBoundsException.class, () -> new Maze(startingPoint.getRow() ,- 1, basicMaze));
    }
    /// <summary>
    /// If the existing maze has an invalid Row, throw an exception
    /// </summary>
    @Test
    public void Maze_Constructor_existingMaze_throws_error_on_row_value_greater_than_maze_Test()
    {
        assertThrows(IndexOutOfBoundsException.class, () -> new Maze(basicMaze.length, startingPoint.getColumn(), basicMaze));
    }
    /// <summary>
    /// If the existing maze has an invalid Column, throw an exception
    /// </summary>
    @Test
    public void Maze_Constructor_existingMaze_throws_error_on_column_value_greater_than_maze_Test()
    {
        assertThrows(IndexOutOfBoundsException.class, () -> new Maze(startingPoint.getRow(), basicMaze[0].length, basicMaze));
    }

    /// <summary>
    /// Checks to make sure the constructor instantializes the appropriate variables from the file
    /// </summary>
    @Test
    public void Maze_Constructor_file_maze_Test()
    {
        Maze maze = new Maze(mazeWithExitPath);

        // note that the file is the same maze as the hardcoded char array basicMaze
        assertEquals(maze.getRowLength(), basicMaze.length);
        assertEquals(maze.getColumnLength(), basicMaze[0].length);
        assertEquals(maze.getStartingPoint().getRow(), startingPoint.getRow());
        assertEquals(maze.getStartingPoint().getColumn(), startingPoint.getColumn());

        char[][] fileMaze = maze.getMaze();

        for (int i = 0; i < fileMaze.length; i++)
        {
            for (int k = 0; k < fileMaze[i].length; k++)
            {
                assertEquals(fileMaze[i][k], basicMaze[i][k]);
            }
        }
    }

    //endregion

    //region PrintMaze()
    ///// <summary>
    ///// Tests PrintMaze() returns a string version of the maze.
    ///// </summary>
    @Test
    public void PrintMaze_Test()
    {
        Maze maze = new Maze(startingPoint.getRow(), startingPoint.getColumn(), basicMaze);

        String mazeOutput = maze.printMaze();

        assertEquals(mazeOutput, basicMazeOutput);
    }
    //endregion

    //region BreadthFirstSearch()
    ///// <summary>
    ///// Tests BreadthFirstSearch() returns appropriate error string when used on a maze without an exit.
    ///// </summary>
    @Test
    public void BreadthFirstSearch_maze_with_no_exit_Test()
    {
        Maze maze = new Maze(startingPoint.getRow(), startingPoint.getColumn(), basicMazeNoExit);

        String mazeOutput = maze.breadthFirstSearch();
        assertEquals(mazeOutput, basicMazeNoExitSearched);
    }

    ///// <summary>
    ///// Tests BreadthFirstSearch() returns path to follow string when used on a maze with an exit.
    /////  NOTE: this is the one test that may be off, check your result, if it's reasonable output, 
    /////  it will pass my milestone
    ///// </summary>
    @Test
    public void BreadthFirstSearch_maze_with_exit_Test()
    {
        Maze maze = new Maze(startingPoint.getRow(), startingPoint.getColumn(), basicMaze);

        String mazeOutput = maze.breadthFirstSearch();
        assertEquals(mazeOutput, breadthStringPath + basicMazeBreadthSearched);
    }
    //endregion

    //region GetPathToFollow()
    ///// <summary>
    ///// Tests GetPathToFollow() throws an exception when we have not yet searched the maze.
    ///// </summary>
    @Test
    public void GetPathToFollow_before_search_throws_exception_Test()
    {
        Maze maze = new Maze(startingPoint.getRow(), startingPoint.getColumn(), basicMaze);
        assertThrows(UnsupportedOperationException.class, () -> maze.getPathToFollow());
    }

    ///// <summary>
    ///// Tests GetPathToFollow() returns an empty stack when no exit found.
    ///// </summary>
    @Test
    public void GetPathToFollow_breadthfirst_on_maze_with_no_exit_returns_empty_stack_Test()
    {
        Maze maze = new Maze(startingPoint.getRow(), startingPoint.getColumn(), basicMazeNoExit);
        maze.breadthFirstSearch();
        Stack<Point> path = maze.getPathToFollow();
        assertTrue(path.isEmpty());
    }

    ///// <summary>
    ///// Tests GetPathToFollow() returns a stack containing the path used from start (at Top()) to end (at bottom).
    ///// </summary>
    @Test
    public void GetPathToFollow_breadthfirst_on_maze_with_exit_returns_ordered_stack_path_Test()
    {
        Maze maze = new Maze(startingPoint.getRow(), startingPoint.getColumn(), basicMaze);
        maze.breadthFirstSearch();
        Stack<Point> path = maze.getPathToFollow();
        assertFalse(path.isEmpty());

        while (!path.isEmpty())
        {
            assertEquals(path.pop().toString(), breathStackPath.pop().toString());
        }
    }

    ///// <summary>
    ///// Tests GetPathToFollow() returns a stack containing the path used from start (at Top()) to end (at bottom), will work when run twice in a row!
    ////  Note: this test is to ensure you are not destroying your created Stack after running GetPathToFollow() the first time.
    ////  You should be returning a copy of the stack.
    ///// </summary>
    @Test
    public void GetPathToFollow_works_the_same_way_when_run_twice_in_a_row_Test()
    {
        Maze maze = new Maze(startingPoint.getRow(), startingPoint.getColumn(), basicMaze);
        maze.breadthFirstSearch();
        Stack<Point> path = maze.getPathToFollow();
        assertFalse(path.isEmpty());

        while (!path.isEmpty())
        {
            assertEquals(path.pop().toString(), breathStackPath.pop().toString());
        }

        resetReverseStack();

        Stack<Point> path2 = maze.getPathToFollow();
        assertFalse(path2.isEmpty());

        while (!path2.isEmpty())
        {
            assertEquals(path2.pop().toString(), breathStackPath.pop().toString());
        }
    }
    //endregion
}
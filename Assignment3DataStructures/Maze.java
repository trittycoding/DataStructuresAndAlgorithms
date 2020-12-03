import jdk.jshell.spi.ExecutionControl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Maze {
    private Point startingPoint;
    private char[][] charMaze;
    private Stack<Point> stack;
    boolean exitFound = false;
    private Queue<Point> queue;

    // Constructor - must take in maze plus starting values for row and column
    public Maze(int startingColumn, int startingRow, char[][] charMaze){
        this.charMaze = charMaze;
        this.startingPoint = new Point(startingRow, startingColumn);
        mazeValidations(charMaze.length, charMaze[0].length, startingRow, startingColumn);
    }

    // Constructor - must take in a filename of pre-established maze
    public Maze(String fileName){
        BufferedReader buffReader = null;
        String dimensions;
        String startingParams;
        int counter = 0;
        int rows = 0;
        int columns = 0;
        int startingRow = 0;
        int startingColumn = 0;

        // Must open and read file
        try {
            buffReader = new BufferedReader(new FileReader(fileName));
        }
        catch (Exception e){
            e.printStackTrace();
        }

        // Read the first two lines of the maze, representing rows + columns, size of rows/columns
        try {
            dimensions = buffReader.readLine();
            String dimensionsArray[] = dimensions.split(" ");
            startingParams = buffReader.readLine();
            String startingParamsArray[] = startingParams.split(" ");

            // Parsing the information and applying to character array (maze)
            try {
                rows = Integer.parseInt(dimensionsArray[0]);
                columns = Integer.parseInt(dimensionsArray[1]);
                startingRow = Integer.parseInt(startingParamsArray[0]);
                startingColumn = Integer.parseInt(startingParamsArray[1]);
            } catch (Exception e) {
                e.printStackTrace();
            }

            startingPoint = new Point(startingRow, startingColumn);
            charMaze = new char[rows][];
            String line;
            while ((line = buffReader.readLine()) != null) {
                charMaze[counter] = line.toCharArray();
                counter++;
            }
            // Maze validations
            mazeValidations(rows, columns, startingColumn, startingRow);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    // Accessor Methods

    // Returns starting point of maze
    public Point getStartingPoint(){return startingPoint;}

    /* Returns row length of maze -
    charMaze.length returns the length of the rows in the array */
    public int getRowLength(){return charMaze.length;}

    /* Returns column length of maze -
    charMaze[0].length accesses the first row of the array and finds the length of the columns.
     Since the mazes are rectangles, all columns should be of same size. */
    public int getColumnLength(){return charMaze[0].length;}

    // Returns the charMaze array
    public char[][] getMaze(){ return charMaze;}

    // Prints the maze from the charArray
    public String printMaze(){
        StringBuilder stringBuilder = new StringBuilder(300);

        // Outer loop - processing rows
        for (int i = 0; i < charMaze.length; i++) {

            // Inner loop - processing columns
            for(int j = 0; j < charMaze[i].length; j++){
                stringBuilder.append(charMaze[i][j]);
            }

            // If it gets to end of maze, don't insert new line character
            if(i < charMaze.length-1) {
                stringBuilder.append("\n");
            }
        }

        return stringBuilder.toString();
    }

    // Finds the exit in a maze
    public String depthFirstSearch() throws CloneNotSupportedException {
        // Navigation of the maze goes in this order - S, E, W, N
        // Push starting point to top of stack, start @ 1 steps representing the starting point
        stack = new Stack<Point>();
        int numberOfSteps = 1;
        stack.push(startingPoint);

        // Check to see if starting point is the exit, print out point if exit
        if(compareCharacters(startingPoint.getRow(), startingPoint.getColumn(), "E")){
            return stack.pop().toString();
        }

        String path = "";

        // While the exit is not found, process
        while(!exitFound){
            // Current point is the top of the stack
//            System.out.println(printMaze());
            Point currentPoint = stack.top();

            // Found exit
            if(charMaze[currentPoint.getRow()][currentPoint.getColumn()] == 'E'){
                exitFound = true;
            }

            // Mark point as part of path to exit
            else {
                    setPathMarkerForPoint(currentPoint.getRow(), currentPoint.getColumn());
                // --South Logic
                // If you can push south, go south
                // If the next point is an available space to move, add to stack
                if (charMaze[currentPoint.getRow() + 1][currentPoint.getColumn()] == ' ' || charMaze[currentPoint.getRow() + 1][currentPoint.getColumn()] == 'E') {
                    stack.push(new Point(currentPoint.getRow() + 1, currentPoint.getColumn()));
                    numberOfSteps++;
                }

                // --East Logic
                else if(charMaze[currentPoint.getRow()][currentPoint.getColumn()+1] == ' ' || charMaze[currentPoint.getRow()][currentPoint.getColumn()+1] == 'E'){
                    stack.push(new Point(currentPoint.getRow(), currentPoint.getColumn()+1));
                    numberOfSteps++;
                }

                // --West Logic
                else if(charMaze[currentPoint.getRow()][currentPoint.getColumn()-1] == ' ' || charMaze[currentPoint.getRow()][currentPoint.getColumn()-1] == 'E'){
                    stack.push(new Point(currentPoint.getRow(), currentPoint.getColumn()-1));
                    numberOfSteps++;
                }

                // -- North Logic
                else if(charMaze[currentPoint.getRow()-1][currentPoint.getColumn()] == ' ' || charMaze[currentPoint.getRow()-1][currentPoint.getColumn()] == 'E'){
                    stack.push(new Point(currentPoint.getRow()-1, currentPoint.getColumn()));
                    numberOfSteps++;
                }

                // Cannot move, pop off, mark point as visited
                else{
                    setVisitedForPoint(currentPoint.getRow(), currentPoint.getColumn());
                    stack.pop();
                    numberOfSteps--;

                    // If stack is empty, exit is not found
                    if(stack.isEmpty()){
                        return "No exit found in maze!" + "\n" + "\n" + printMaze();
                    }
                }
            }
        }

        int stackSize = stack.getSize();
        Stack<Point> orderedStack = getPathToFollow();
        path = "Path to follow from Start ";

        for(int i = 0; i < stackSize; i++){
            if(i == 0){
                path = path + orderedStack.top().toString() + " to Exit " + stack.top().toString() + " - " + numberOfSteps + " steps:" + "\n";
            }
            path = path + orderedStack.top().toString() + "\n";
            System.out.println(orderedStack.top().toString());
            orderedStack.pop();
        }
        // Added maze to bottom
        path = path + printMaze();
        return path;
    }

    // Performs validations on constructors to ensure valid maze
    public void mazeValidations(int rows, int columns, int startingColumn, int startingRow){

        // If rows, or columns are less than zero
        if(rows < 0 || columns < 0|| startingColumn < 0 || startingRow < 0){
            throw new IndexOutOfBoundsException("Rows or columns are less than zero");
        }

        // if starting point is invalid i.e. on a wall or exit, must be a space
        else if(!(compareCharacters(startingRow, startingColumn, " "))){
            throw new UnsupportedOperationException("Error - invalid starting point");
        }

        // If starting column or row is out of bounds
        else if(startingColumn > columns || startingRow > rows){
            throw new IndexOutOfBoundsException("Starting columns or rows are out of maze");
        }
    }

    // Returns the correct path to the exit, in proper order from starting point to exit
    public Stack<Point> getPathToFollow(){
        if(stack == null) {
            throw new UnsupportedOperationException("Stack is null");
        }

        return stack.copy();
    }

    // Compares the character in the charMaze to a given value
    public boolean compareCharacters(int row, int column, String value){
        return Character.toString(charMaze[row][column]).equals(value);
    }


    // Sets the given point in the maze to "marked", denoted by 'V'
    public void setVisitedForPoint(int row, int column){
        charMaze[row][column] = 'V';
    }

    // Sets the given point in the maze to point travelled to exit, denoted by "."
    public void setPathMarkerForPoint(int row, int column){
        charMaze[row][column] = '.';
    }

    // Conducts a breadth first search in a maze - finds the fastest path to exit
    public String breadthFirstSearch(){
        // Navigation of the maze goes in this order - S, E, W, N
        // Push starting point to queue, start @ 1 steps representing the starting point
        queue = new Queue<Point>();
        stack = new Stack<Point>();
        Point exitPoint = null;
        queue.enqueue(startingPoint);
        Point currentPoint = null;

        // Check to see if starting point is the exit, print out point if exit
        if(compareCharacters(startingPoint.getRow(), startingPoint.getColumn(), "E")){
            return queue.back().toString();
        }

        // Dequeue the starting point, mark it as visited
        String path = "";
        setVisitedForPoint(startingPoint.getRow(), startingPoint.getColumn());

        // While the exit is not found, process
        while(exitFound == false && !queue.isEmpty()){
                currentPoint = queue.dequeue();
                Point newPoint = new Point(currentPoint.getRow() + 1, currentPoint.getColumn(), currentPoint);

                // --South Logic
                // Check next southern point, if not exit then mark as visited and enqueue, new point will include parent point param
                // If the next point is the exit, end loop
                if (charMaze[newPoint.getRow()][newPoint.getColumn()] == ' ' || charMaze[newPoint.getRow()][newPoint.getColumn()] == 'E') {
                    queue.enqueue(newPoint);
                    if(charMaze[newPoint.getRow()][newPoint.getColumn()] == 'E'){
                        exitPoint = newPoint;
                        exitFound = true;
                    }
                    setVisitedForPoint(newPoint.getRow(), newPoint.getColumn());
                }

            newPoint = new Point(currentPoint.getRow(), currentPoint.getColumn()+1, currentPoint);

            // --East Logic
            // Check next southern point, if not exit then mark as visited and enqueue, new point will include parent point param
            // If the next point is the exit, end loop
            if (charMaze[newPoint.getRow()][newPoint.getColumn()] == ' ' || charMaze[newPoint.getRow()][newPoint.getColumn()] == 'E') {
                queue.enqueue(newPoint);
                if(charMaze[newPoint.getRow()][newPoint.getColumn()] == 'E'){
                    exitPoint = newPoint;
                    exitFound = true;
                }
                setVisitedForPoint(newPoint.getRow(), newPoint.getColumn());
            }

            newPoint = new Point(currentPoint.getRow(), currentPoint.getColumn()-1, currentPoint);

            // --South Logic
            // Check next southern point, if not exit then mark as visited and enqueue, new point will include parent point param
            // If the next point is the exit, end loop
            if (charMaze[newPoint.getRow()][newPoint.getColumn()] == ' ' || charMaze[newPoint.getRow()][newPoint.getColumn()] == 'E') {
                queue.enqueue(newPoint);
                if(charMaze[newPoint.getRow()][newPoint.getColumn()] == 'E'){
                    exitPoint = newPoint;
                    exitFound = true;
                }
                setVisitedForPoint(newPoint.getRow(), newPoint.getColumn());
            }

            newPoint = new Point(currentPoint.getRow() - 1, currentPoint.getColumn(), currentPoint);

            // --South Logic
            // Check next southern point, if not exit then mark as visited and enqueue, new point will include parent point param
            // If the next point is the exit, end loop
            if (charMaze[newPoint.getRow()][newPoint.getColumn()] == ' ' || charMaze[newPoint.getRow()][newPoint.getColumn()] == 'E') {
                queue.enqueue(newPoint);
                if(charMaze[newPoint.getRow()][newPoint.getColumn()] == 'E'){
                    exitPoint = newPoint;
                    exitFound = true;
                }
                setVisitedForPoint(newPoint.getRow(), newPoint.getColumn());
            }
        }

        // Processing queue for correct path - set parent point as '.' instead of 'V'
        if(exitPoint != null) {
            currentPoint = exitPoint;
            while (currentPoint != null) {

                // If the point is exit, don't overwrite
                if(currentPoint != exitPoint) {
                    setPathMarkerForPoint(currentPoint.getRow(), currentPoint.getColumn());
                }
                else{
                    charMaze[currentPoint.getRow()][currentPoint.getColumn()] = 'E';
                }
                stack.push(currentPoint);
                path = currentPoint.toString() + "\n" + path;
                currentPoint = currentPoint.getParent();
            }
            Stack<Point> orderedStack = getPathToFollow();
            int stackSize = orderedStack.getSize();

            path = "Path to follow from Start " + startingPoint.toString() + " to Exit " + exitPoint.toString() + " - " + stack.getSize() + " steps:" + "\n" + path;

            // Added maze to bottom
            path = path + printMaze();
            return path;
        }

        // If queue is empty, exit is not found
        else{
            setVisitedForPoint(startingPoint.getRow(), startingPoint.getColumn());
            return "No exit found in maze!" + "\n" + "\n" + printMaze();
        }
    }
}

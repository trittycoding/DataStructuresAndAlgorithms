public class Point {
private int row;
private int column;
private Point parent;

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        if(row >= 0) {
            this.row = row;
        }
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        if(column >= 0){
            this.column = column;
        }
    }

    public void setParent(Point parent){
        this.parent = parent;
    }

    public Point getParent(){
        return parent;
    }

    public Point(int row, int column) {
        setColumn(column);
        setRow(row);
    }

    public Point(int row, int column, Point parent){
        setRow(row);
        setColumn(column);
        setParent(parent);
    }

    @Override
    public String toString() {
        return "["+getRow()+", "+getColumn()+"]";
    }
}

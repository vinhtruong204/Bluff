package core;

public class PointMatrix {
    private int row;
    private int col;
    private int prevRow; // previous row
    private int prevCol; // previous column

    public PointMatrix(int row, int col, int prevRow, int prevCol) {
        this.row = row;
        this.col = col;
        this.prevRow = prevRow;
        this.prevCol = prevCol;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getPrevRow() {
        return prevRow;
    }

    public void setPrevRow(int prevRow) {
        this.prevRow = prevRow;
    }

    public int getPrevCol() {
        return prevCol;
    }

    public void setPrevCol(int prevCol) {
        this.prevCol = prevCol;
    }

}

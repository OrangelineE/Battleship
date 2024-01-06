package edu.duke.jw844.battleship;
/**
 * This class handles the Coordinate on a board
 * It specifies the position on a board with given parameters
 * and check they are valid or not
 */
public class Coordinate {
    private final int row;
    private final int column;
    /**
     * This method obtains the row number of this coordinate
     */
    public int getRow() {
        return this.row;
    }
    /**
     * This method obtains the column number of this coordinate
     */
    public int getColumn() { return this.column;}
    /**
     * Constructs a Coordinate on the board.
     * @param r is the row it should be
     * @param c is the column it should be
     * @throws IllegalArgumentException if the row or column is less than zero
     */
    public Coordinate(int r, int c) {
        if (r < 0) {
            throw new IllegalArgumentException("Coordinate's row must be positive but is " + r);
        }
        if (c < 0) {
            throw new IllegalArgumentException("Coordinate's column must be positive but is " + c);
        }
        this.row = r;
        this.column = c;
    }
    /**
     * Constructs a Coordinate on the board.
     * @param descrb should have the length of 2, the first one is the row, the second one is the column
     * @throws IllegalArgumentException if the row < 'A' or > 'Z', or the column < '0' or > '9', or descrb's length > 2
     */
    public Coordinate(String descrb) {
        if (descrb.length() != 2) throw new IllegalArgumentException("The Coordinate is invalid as " + descrb);
        descrb = descrb.toUpperCase();
        if (descrb.charAt(0) >= 'A' && descrb.charAt(0) <= 'Z') {
            this.row = descrb.charAt(0) - 'A';
        }
        else {
            throw new IllegalArgumentException("Coordinate's row is invalid : " + descrb.charAt(0));
        }
        if (descrb.charAt(1) >= '0' && descrb.charAt(1) <= '9') {
            this.column = descrb.charAt(1) - '0';
        }
        else  throw new IllegalArgumentException("Coordinate's column is invalid : " + descrb.charAt(1));
    }
    /**
     * Justify whether two coordinates are equal
     * @param o the other Coordinate
     * @return true if the two columns and rows are equal
     */
    @Override
    public boolean equals(Object o) {
        if (o.getClass().equals(getClass())) {
            Coordinate c = (Coordinate) o;
            return row == c.row && column == c.column;
        }
        return false;
    }
    /**
     * Convert the coordinate into String
     * @return String contains row and column
     */
    @Override
    public String toString() {
        return "("+row+", " + column+")";
    }
    /**
     * Convert the coordinate into hash code
     * @return hashcode contains row and column
     */
    @Override
    public int hashCode() {
        return toString().hashCode();
    }

}

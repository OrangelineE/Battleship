package edu.duke.jw844.battleship;
/**
 * This class is to place ships on a board
 */
public class Placement {
    private final Coordinate where;
    private final char orientation;
    /**
     * get the coordinate
     */
    public Coordinate getCoordinate() {
        return this.where;
    }
    /**
     * get the orientation
     */
    public char getOrientation() {
        return this.orientation;
    }
    /**
     * Constructs a placement which takes in the coordinate and the orientation
     * @param w represents the coordinate
     * @param o represents the orientation
     */
    public Placement (Coordinate w, char o) {
        o = Character.toUpperCase(o);
        if (o == 'V' || o == 'H' || o == 'U' || o == 'R' || o =='D' || o == 'L') {
            this.orientation = o;
        }
        else throw new IllegalArgumentException("Coordinate's direction is invalid : " + o);
        this.where = w;
    }
    /**
     * Constructs a placement which takes in a string
     * @param descr represents the coordinates and orientation
     *              the first two characters are the coordinate,
     *              and the second one is the orientation ('h' | 'r')
     */
    public Placement (String descr) {
        if (descr.length() != 3) throw new IllegalArgumentException("The string descr 's length is invalid as " + descr.length());
        descr = descr.toUpperCase();
        where = new Coordinate(descr.substring(0,2));
        if (descr.charAt(2) != 'V' && descr.charAt(2) != 'H' && descr.charAt(2) != 'U' && descr.charAt(2) != 'R' && descr.charAt(2) != 'D'&& descr.charAt(2) != 'L') {
            throw new IllegalArgumentException("Coordinate's direction is invalid : " + descr.charAt(2));
        }
        else {
            orientation = descr.charAt(2);
        }

    }
    /**
     * Justify whether two placements are equal
     * @param o the other placement
     * @return true if the two columns ,rows and orientations are equal
     */
    @Override
    public boolean equals(Object o) {
        if (o.getClass().equals(getClass())) {
            Placement p = (Placement) o;
            return where.getColumn() == p.where.getColumn() && where.getRow() == p.where.getRow() && orientation == p.orientation;
        }
        return false;
    }
    /**
     * Convert the placement into String
     * @return String contains placement information
     */
    @Override
    public String toString() {
        return "(" + where.getRow()+", " + where.getColumn()+", " + orientation + ")";
    }
    /**
     * Convert the String into the hashcode
     * @return int which is the hashcode of the string
     */
    @Override
    public int hashCode() {
        return toString().hashCode();
    }

}

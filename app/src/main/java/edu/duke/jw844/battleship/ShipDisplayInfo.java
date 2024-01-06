package edu.duke.jw844.battleship;
/**
 * This interface represents the display information of the ship
 */
public interface ShipDisplayInfo<T> {
    /**
     * Get the display pattern of the input Coordinate
     * @param where is the Coordinate to check what the display pattern is
     * @param hit is whether this coordinate is hit or not
     * @return corresponding pattern at this coordinate
     */
    public T getInfo(Coordinate where, boolean hit);
}
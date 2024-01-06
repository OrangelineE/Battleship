package edu.duke.jw844.battleship;
/**
 * This class handles display information of basic ships
 * if the position is hit by others, return hit pattern
 * Otherwise, it returns data pattern
 */
public class SimpleShipDisplayInfo <T> implements ShipDisplayInfo<T>{
    private T myData;
    private T onHit;
    /**
     * Constructs a SimpleShipDisplayInfo
     * @param mD represents my Data pattern
     * @param oH  represents hit pattern
     */
    public SimpleShipDisplayInfo(T mD, T oH) {
        this.myData = mD;
        this.onHit = oH;
    }
    /**
     * To get the display information at the given coordinate
     * @param where represents the position we want to display
     * @param hit represents if this position is hit or not
     */
    @Override
    public T getInfo(Coordinate where, boolean hit) {
        if(hit) return onHit;
        else return myData;
    }
}

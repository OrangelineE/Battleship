package edu.duke.jw844.battleship;

import java.util.HashMap;

/**
 * This class handles information of basic ships
 * It records where it is hit and check whether it is sunk
 * display the pattern of the ship due to different views and if it is hit
 */
public abstract class BasicShip<T> implements Ship<T>{
    protected ShipDisplayInfo<T> myDisplayInfo;
    protected ShipDisplayInfo<T> enemyDisplayInfo;
    protected HashMap<Integer, Boolean> myPieces;
    protected HashMap<Integer, Coordinate> Components;
    /**
     * Constructs a Ship based on the display information and the coordinate
     * @param myDisplayInfo contains the data and hit symbol in player's owm view
     * @param enemyDisplayInfo contains the data and hit symbol in player's enemy view
     */
    public BasicShip(ShipDisplayInfo<T> myDisplayInfo, ShipDisplayInfo<T> enemyDisplayInfo, HashMap<Integer, Coordinate> cpn) {
        this.myPieces = new HashMap<Integer, Boolean>();
        for(Integer c : cpn.keySet()) {
            myPieces.put(c,false);
        }
        this.myDisplayInfo = myDisplayInfo;
        this.enemyDisplayInfo = enemyDisplayInfo;
        this.Components = new HashMap<Integer, Coordinate>();
        Components.putAll(cpn);
    }

    protected void checkPiecesInThisShip(Coordinate c) {
        if (! Components.containsValue(c)) {
            throw new IllegalArgumentException("the Coordinate (" + c.getRow() + "," + c.getColumn() + ") is not a part of the ship\n");
        }
    }

    public void makeMovement(HashMap<Integer, Coordinate> newOne) {
        this.Components = new HashMap<Integer, Coordinate>();
        Components.putAll(newOne);
    }

    /**
     * Check whether there is a ship at this coordinate
     * @param where indicates the coordinate position
     * @return return true if it contains
     */
    @Override
    public boolean occupiesCoordinates(Coordinate where) {
        return Components.containsValue(where);
    }
    /**
     * To show if the ship is sunk
     * @return return true if its coordinates are all hit
     */
    @Override
    public boolean isSunk() {
        for(Integer c : myPieces.keySet()){
            if(!myPieces.get(c)) {
                return false;
            }
        }
        return true;
    }
    /**
     * To record hit position
     * @param where the position need to be recorded
     */
    @Override
    public void recordHitAt(Coordinate where) {
        checkPiecesInThisShip(where);
        for(Integer i : Components.keySet()) {
            if (Components.get(i).equals(where)) {
                myPieces.put(i, true);
            }
        }
    }
    /**
     * To check whether this position is hit
     * @param where the position need to be checked
     * @return true if it is hit
     */
    @Override
    public boolean wasHitAt(Coordinate where) {
        checkPiecesInThisShip(where);
        for(Integer i : Components.keySet()) {
            if (Components.get(i).equals(where)) {
                return myPieces.get(i);
            }
        }
        return false;
    }
    /**
     * To get the display information at the coordinate
     * @param where the position we want to check
     * @param myShip true if we want display the ship in our own view
     * @return display information depends on the position and the viewer
     */
    @Override
    public T getDisplayInfoAt(Coordinate where, boolean myShip) {
        checkPiecesInThisShip(where);
        if (myShip) return myDisplayInfo.getInfo(where, wasHitAt(where));
        else return enemyDisplayInfo.getInfo(where,wasHitAt(where));
    }
    /**
     * To get the upper left Coordinate
     * @return A coordinate at the upper left position
     */
    @Override
    public HashMap<Integer, Coordinate> getComponent() {
        return this.Components;
    }


}

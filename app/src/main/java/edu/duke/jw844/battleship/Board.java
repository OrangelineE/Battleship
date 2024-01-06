package edu.duke.jw844.battleship;

import java.util.HashSet;

public interface Board<T> {
    /**
     * get the width of the board
     */
    public int getWidth();
    /**
     * get the hight of the board
     */
    public int getHeight();
    /**
     * add the ship to the list and
     * if there is any problem related to placement, return to the problem represented in string.
     * Otherwise, return to null.
     * @param toAdd is the ship we want to Add
     * @return if there is no problem, return to null. Otherwise, return to the string.
     * */
    public String tryAddShip(Ship<T> toAdd);
    /**
     * This method takes a Coordinate, and sees which (if any) Ship
     * occupies that my own coordinate.  If one is found, we return whatever
     * displayInfo it has at those coordinates.  If none is found, we return null.
     * @param where the Coordinate we want to check if ship occupies this position
     * @return return to corresponding display pattern
     */
    public T whatIsAtForSelf(Coordinate where);
    /**
     * This method takes a Coordinate, and sees which (if any) Ship
     * occupies that Enemy's coordinate.  If one is found,which means that the player hits the opponent's ship, we return whatever
     * displayInfo it has at those coordinates.  If none is found,which means that player A misses the ship, we return 'X'.
     * @param where the Coordinate we want to check if ship occupies this position
     * @return return to corresponding display pattern
     */
    public T whatIsAtForEnemy(Coordinate where);
    /**
     * This method takes a Coordinate, and sees if enemy hits my own ship or not. If a ship is hit, we return its
     * displayInfo at those coordinates.  If none is hit,which means that player A misses the ship, we add the coordinates
     * into the set and return null.
     * @param c the Coordinate we want to check if ship occupies this position. if yes, the ship is hit.
     *          If not, we record this coordinate and return null
     * @return return to corresponding display pattern or null
     */
    public Ship<T> fireAt(Coordinate c);
    /**
     * This method is to check whether the player lose by checking if all the ships are sunk, he/she loses.
     * @return if all the ships are sunk, return true.
     */
    public boolean isLose();
    /**
     * This method is to locate the ship based on the input
     * @return the ship if it contains the given coordinate
     */
    public Ship<T> findAShip(String input);
    /**
     * This method is to check the placement
     * @return the corresponding problem string
     */
    public String CheckShipPlacement(Ship<T> toCheck);
    /**
     * This method is to obtain the ship occurrence information within the coverage indicated by the given input
     * @return the ship occurrence information
     */
    public String sonarScan(String input);
    /**
     * This method is to get the coordinates that enemy misses
     * @return the coordinates that enemy misses
     */
    public HashSet<Coordinate> getEnemyMiss();
}

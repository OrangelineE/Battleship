package edu.duke.jw844.battleship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * This class handles operations on a Board (i.e., generate a board and try to add ships on a board).
 * It supports get the size of a board, add ships on a board,
 * fire at the coordinate and record it if it doesn't hit a ship
 */
public class BattleShipBoard<T> implements Board<T>{
    private final int width;
    private final int height;
    private final PlacementRuleChecker<T> placementChecker;
    private final T missInfo;
    public final ArrayList<Ship<T>> myShips;
    private HashMap<String, String> shipFullName;
    public HashSet<Coordinate> enemyMisses;
    public HashMap<String, Integer> shipOccurrence;

    public int getWidth() {
        return this.width;
    }
    public int getHeight() {
        return this.height;
    }
    /**
     * To link the ship name with its display character
     */
    protected void setupShipNameMap() {
        this.shipFullName.put("s", "Submarines");
        this.shipFullName.put("d", "Destroyers");
        this.shipFullName.put("b", "Battleships");
        this.shipFullName.put("c", "Carriers");
    }
    protected void setupShipOccur() {
        this.shipOccurrence.put("s", 0);
        this.shipOccurrence.put("d", 0);
        this.shipOccurrence.put("b", 0);
        this.shipOccurrence.put("c", 0);
    }

    /**
     * Constructs a BattleShipBoard with the specified width and height
     * @param w is the width of the newly constructed board.
     * @param h is the height of the newly constructed board.
     * @param p the placement rule checker to place if the placement is valid
     * @param info represents the miss pattern
     */
    public BattleShipBoard(int w, int h, PlacementRuleChecker p, T info) {
        if (w <= 0) {
            throw new IllegalArgumentException("BattleShipBoard's width must be positive but is " + w);
        }
        if (h <= 0) {
            throw new IllegalArgumentException("BattleShipBoard's height must be positive but is " + h);
        }
        this.width = w;
        this.height = h;
        myShips = new ArrayList<>();
        this.placementChecker = p;
        enemyMisses = new HashSet<Coordinate>();
        this.missInfo = info;
        this.shipFullName = new HashMap<>();
        setupShipNameMap();
        this.shipOccurrence = new HashMap<>();
        setupShipOccur();
    }
    /**
     * Constructs a BattleShipBoard with the specified width and height
     * @param w is the width of the newly constructed board.
     * @param h is the height of the newly constructed board.
     * @param info represents the miss pattern
     */
    public BattleShipBoard(int w, int h, T info) {
        this(w, h, new InBoundsRuleChecker<T>(new NoColliSionRuleChecker<T>(null)),info);
    }
    /**
     * add the ship to the list and
     * if there is any problem related to placement, return to the problem represented in string.
     * Otherwise, return to null.
     * @param toAdd is the ship we want to Add
     * @return if there is no problem, return to null. Otherwise, return to the string.
     * */
    public String tryAddShip(Ship<T> toAdd) {
        String placementProblem = CheckShipPlacement(toAdd);
        if (placementProblem == null) {
            myShips.add(toAdd);
            return null;
        }
        return placementProblem;
    }
    /**
     * This method takes a Coordinate, and sees which (if any) Ship
     * occupies that my own coordinate.  If one is found, we return whatever
     * displayInfo it has at those coordinates.  If none is found, we return null.
     * @param where the Coordinate we want to check if ship occupies this position
     * @return return to corresponding display pattern
     */
    public T whatIsAtForSelf(Coordinate where) {
        return whatIsAt(where, true);
    }
    /**
     * This method takes a Coordinate, and sees which (if any) Ship
     * occupies that Enemy's coordinate.  If one is found,which means that the player hits the opponent's ship, we return whatever
     * displayInfo it has at those coordinates.  If none is found,which means that player A misses the ship, we return 'X'.
     * @param where the Coordinate we want to check if ship occupies this position
     * @return return to corresponding display pattern
     */
    public T whatIsAtForEnemy(Coordinate where) {
        return whatIsAt(where, false);
    }
    /**
     * This method takes a Coordinate, and sees if enemy hits my own ship or not. If a ship is hit, we return its
     * displayInfo at those coordinates.  If none is hit,which means that player A misses the ship, we add the coordinates
     * into the set and return null.
     * @param c the Coordinate we want to check if ship occupies this position. if yes, the ship is hit.
     *          If not, we record this coordinate and return null
     * @return return to corresponding display pattern or null
     */
    public Ship<T> fireAt(Coordinate c) {
        T fire = whatIsAtForSelf(c);
        if (fire != null) {
            for(Ship<T> s:myShips) {
                if (s.occupiesCoordinates(c)) {
                    s.recordHitAt(c);
                    return s;
                }
            }
        }
        enemyMisses.add(c);
        return null;
    }
    /**
     * This method takes a Coordinate, and sees which (if any) Ship occupies that my own or Enemy's coordinate.
     * If one is found, we return whatever displayInfo it has at those coordinates.  If none is found, we return null.
     * @param where the Coordinate we want to check if ship occupies this position.
     * @param isSelf true:my own board, false: the enemy's board
     * @return return to corresponding display information or null
     */
    protected T whatIsAt(Coordinate where, boolean isSelf) {
        for (Ship<T> s: myShips) {
            if (s.occupiesCoordinates(where)){
                return s.getDisplayInfoAt(where, isSelf);
            }
            else if(!isSelf && enemyMisses.contains(where)) {
                return missInfo;
            }
        }
        return null;
    }
    /**
     * This method is to check whether the player lose by checking if all the ships are sunk, he/she loses.
     * @return if all the ships are sunk, return true.
     */
    public boolean isLose() {
        for(Ship<T> s : myShips) {
            if(!s.isSunk()) return false;
        }
        return true;
    }
    /**
     * Make sure the placement of the ship is valid
     * @param toCheck is the ship we want to check
     * @return if there is no problem, return to null. Otherwise, return to the string.
     * */
    public String CheckShipPlacement(Ship<T> toCheck) {
        String placementProblem = placementChecker.checkPlacement(toCheck, this);
        return placementProblem;
    }
    /**
     * This method is to locate the ship based on the input
     * @return the ship if it contains the given coordinate
     */
    public Ship<T> findAShip(String input){
        Coordinate source = new Coordinate(input);
        for(Ship<T> s:myShips) {
            if (s.occupiesCoordinates(source)) {
                return s;
            }
        }
        return null;
    }
    /**
     * This method is to get all the coordinates within the sonar scan
     * @param input indicate the center coordinate
     * @return the coordinate set that the sonar scan covers
     */
    public HashSet<Coordinate> sonarCoverage(String input) {
        HashSet<Coordinate> sonarRange = new HashSet<>();
        Coordinate c = new Coordinate(input);
        if (c.getRow() >= this.getHeight() || c.getColumn() >= this.getWidth()) {
            throw new IllegalArgumentException("the center of the sonar scanning is out of the board! \n");
        }
        for (int i = -3; i <= 3; i++) {
            for(int j = 0 ; j <= 3 && (i + j <= 3 && j - i <= 3 && j - i >= -3 && j + i >= -3); j++ ) {
                int xCoord = c.getRow() + i;
                int yCoord1 = c.getColumn() + j;
                int yCoord2 = c.getColumn() - j;
                if (xCoord >= 0 && xCoord < this.getHeight()) {
                    if (yCoord1 >= 0 && yCoord1 < this.getWidth()) {
                        sonarRange.add(new Coordinate(xCoord, yCoord1));
                    }
                    if (yCoord2 >= 0 && yCoord2 < this.getWidth()) {
                        sonarRange.add(new Coordinate(xCoord, yCoord2));
                    }
                }
            }
        }
        return sonarRange;
    }
    /**
     * This method is to count the ship number in the sonar scan coverage
     * @param input indicate the center coordinate
     */
    public void setShipOccurrence(String input) {
        setupShipOccur();
        HashSet<Coordinate> sonarRange = sonarCoverage(input);
        for(Coordinate c : sonarRange) {
            T keyInfo = whatIsAt(c, true);
            if (keyInfo != null) {
                shipOccurrence.put(keyInfo.toString(),  shipOccurrence.get(keyInfo.toString()) + 1);
            }
        }
    }
    /**
     * This method is to count the ship number in the sonar scan coverage
     * @param input indicate the center coordinate
     * @return the string that contains ship occurrence information
     */
    public String sonarScan(String input) {
        setShipOccurrence(input);
        StringBuilder ans = new StringBuilder("");
        for(String info : shipOccurrence.keySet()) {
            ans.append(shipFullName.get(info) + " occupy " + shipOccurrence.get(info) + " squares \n");
        }
        return ans.toString();
    }
    /**
     * This method is to get the coordinates that enemy misses
     * @return the coordinates that enemy misses
     */
    public HashSet<Coordinate> getEnemyMiss() {
        return this.enemyMisses;
    }
}

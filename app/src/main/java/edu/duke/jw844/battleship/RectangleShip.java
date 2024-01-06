package edu.duke.jw844.battleship;

import java.util.HashMap;
import java.util.HashSet;

public class RectangleShip<T> extends BasicShip<T> {
    final String name;
    /**
     * To collect the coordinates that ship occupies
     * @param upperLeft the upper left position of the ship
     * @param width the width of the ship
     * @param height the height of the ship
     * @return the set of coordinates that ship occupies
     */
    static HashMap<Integer, Coordinate> makeCoords(Coordinate upperLeft, int width, int height, char o) {
        HashMap<Integer, Coordinate> h = new HashMap<Integer, Coordinate>();
            int count = 0;
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (o == 'V') {
                        h.put( count, new Coordinate(upperLeft.getRow() + i, upperLeft.getColumn() + j));
                    }
                    else if (o == 'H') {
                        h.put(count, new Coordinate(upperLeft.getRow() + j, upperLeft.getColumn() + i));
                    }
                    else {
                        throw new IllegalArgumentException("The orientation is invalid! \n");
                    }
                    count++;
                }
            }
        return h;
    }
    /**
     * To obtain the ship name
     */
    public String getName() {
        return name;
    }
    /**
     * To get the coordinates the ship occupies
     */
    @Override
    public Iterable<Coordinate> getCoordinates() {
        return Components.values();
    }
    /**
     * Constructs a RectangleShip with given parameters
     * @param name the name of the ship
     * @param upperLeft the coordinate we want to put
     * @param width the width of this ship
     * @param height the height of this ship
     * @param myDisplayInfo contains data pattern and hit pattern in player's own view
     * @param enemyDisplayInfo contains data pattern and hit pattern in enemy's view
     * @param o represents the ship orientation
     */
    public RectangleShip(String name, Coordinate upperLeft, int width, int height, ShipDisplayInfo<T> myDisplayInfo, ShipDisplayInfo<T> enemyDisplayInfo, char o) {
        super(myDisplayInfo, enemyDisplayInfo, makeCoords(upperLeft, width, height, o));
        this.name = name;
    }

    /**
     * Constructs a RectangleShip with given parameters
     * @param name the name of the ship
     * @param upperLeft the coordinate we want to put
     * @param width the width of this ship
     * @param height the height of this ship
     * @param data represents the data pattern
     * @param onHit represents the hit pattern
     * @param o represents the ship orientation
     */
    public RectangleShip(String name, Coordinate upperLeft, int width, int height, T data, T onHit, char o) {
        this(name, upperLeft, width, height, new SimpleShipDisplayInfo<T>(data, onHit), new SimpleShipDisplayInfo<T>(null, data), o);
    }
    /**
     * Constructs a RectangleShip with given parameters, and it is easy to test with this constructor
     * @param upperLeft the coordinate we want to put
     * @param data represents the data pattern
     * @param onHit represents the hit pattern
     */
    public RectangleShip(Coordinate upperLeft, T data, T onHit) {
        this("testship", upperLeft, 1, 1, data, onHit, 'H');
    }

}


package edu.duke.jw844.battleship;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;

import static edu.duke.jw844.battleship.RectangleShip.makeCoords;
import static org.junit.jupiter.api.Assertions.*;
public class RectangleShipTest {
    @Test
    public  void test_makeCoords() {
        Coordinate upperLeft = new Coordinate(1, 2);
        HashMap<Integer, Coordinate> ans;
        ans = makeCoords(upperLeft, 1, 3, 'H');
        HashSet<Coordinate> expected = new HashSet<>();
        expected.add(new Coordinate(1, 2));
        expected.add(new Coordinate(2, 2));
        expected.add(new Coordinate(3, 2));
        assertEquals(expected, ans.values());
    }
    @Test
    public void test_occupy() {
        Coordinate upperLeft = new Coordinate(1, 2);
        Ship<Character> r = new RectangleShip<Character>("testShip",upperLeft, 1, 3, 's', '*','H');
        assertTrue(r.occupiesCoordinates(new Coordinate(1, 2)));
        assertTrue(r.occupiesCoordinates(new Coordinate(2, 2)));
        assertTrue(r.occupiesCoordinates(new Coordinate(3, 2)));
        assertFalse(r.occupiesCoordinates(new Coordinate(4, 2)));
    }
    @Test
    public void test_getName() {
        Coordinate upperLeft = new Coordinate(1, 2);
        RectangleShip<Character> r = new RectangleShip<Character>("testShip",upperLeft, 1, 3, 's', '*','H');
        assertEquals("testShip", r.getName());
    }

    @Test
    public void test_hit() {
        Coordinate upperLeft = new Coordinate(1, 2);
        RectangleShip<Character> r = new RectangleShip<Character>("testShip",upperLeft, 1, 3, 's', '*', 'H');
        r.recordHitAt(new Coordinate(2, 2));

        assertThrows(IllegalArgumentException.class, ()->new RectangleShip<Character>("testShip",new Coordinate(1, 2), 1, 3, 's', '*','H').recordHitAt(new Coordinate(1, 4)));
        assertTrue(r.wasHitAt(new Coordinate(2, 2)));
        assertFalse(r.wasHitAt(new Coordinate(1, 2)));
    }
    @Test
    public void test_display() {
       RectangleShip<Character> r = new RectangleShip<Character>("testShip",new Coordinate(1, 2),1,3, 's','*','H');
       r.recordHitAt(new Coordinate(2, 2));
       assertEquals('*', r.getDisplayInfoAt(new Coordinate(2,2),true));
       assertEquals('s', r.getDisplayInfoAt(new Coordinate(1,2),true));
       assertEquals('s', r.getDisplayInfoAt(new Coordinate(2,2),false));
       assertEquals(null, r.getDisplayInfoAt(new Coordinate(1,2),false));


    }


}

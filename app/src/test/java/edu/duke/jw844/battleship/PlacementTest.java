package edu.duke.jw844.battleship;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlacementTest {
    @Test
    public void test_invalid_dimensions() {
        assertThrows(IllegalArgumentException.class, () -> new Placement(new Coordinate(10, 5), 'k'));
        assertThrows(IllegalArgumentException.class, () -> new Placement(new Coordinate(10, -1), 'H'));
    }
    @Test
    public void test_row_and_column() {
        Coordinate c1 = new Coordinate(10, 5);
        assertEquals(c1, new Placement(new Coordinate(10, 5), 'h').getCoordinate());
        assertEquals('H', new Placement(new Coordinate(10, 5), 'h').getOrientation());
    }
    @Test
    public void test_equals() {
        Coordinate c1 = new Coordinate(10, 5);
        Coordinate c2 = new Coordinate(10, 5);
        Coordinate c3 = new Coordinate(5, 10);
        Placement p1 = new Placement(c1, 'v');
        Placement p2 = new Placement(c2, 'V');
        Placement p3 = new Placement(c3, 'H');
        Placement p4 = new Placement(c3, 'h');
        assertEquals(p1, p2);   //equals should be reflexive
        assertEquals(p2, p2);   //different objects bu same contents
        assertEquals(p3, p4);   //different objects bu same contents
        assertNotEquals(p1, p3);  //different contents
        assertNotEquals(p1, p4);
        assertNotEquals(p1, p3);
    }
    @Test
    public void test_hashCode() {
        Coordinate c1 = new Coordinate(10, 5);
        Coordinate c2 = new Coordinate(10, 5);
        Coordinate c3 = new Coordinate(5, 10);
        Placement p1 = new Placement(c1, 'v');
        Placement p2 = new Placement(c2, 'V');
        Placement p3 = new Placement(c3, 'H');
        Placement p4 = new Placement(c3, 'h');

        assertEquals(p1.hashCode(), p2.hashCode());   //equals should be reflexive
        assertNotEquals(p1.hashCode(), p3.hashCode());  //different contents
        assertNotEquals(p1.hashCode(), p4.hashCode());
    }

    @Test
    void test_string_constructor_valid_cases() {
        Coordinate c1 = new Coordinate("B3");
        Placement p1 = new Placement("B3H");
        assertEquals(c1, p1.getCoordinate());
        assertEquals('H', p1.getOrientation());
        Coordinate c2 = new Coordinate("D5");
        Placement p2 = new Placement("D5h");
        assertEquals(c2, p2.getCoordinate());
        assertEquals('H', p2.getOrientation());
        Coordinate c3 = new Coordinate("A9");
        Placement p3 = new Placement("A9v");
        assertEquals(c3, p3.getCoordinate());
        assertEquals('V', p3.getOrientation());
        Coordinate c4 = new Coordinate("Z0");
        Placement p4 = new Placement("Z0V");
        assertEquals(c4, p4.getCoordinate());
        assertEquals('V', p4.getOrientation());
    }
    @Test
    public void test_string_constructor_error_cases() {
        assertThrows(IllegalArgumentException.class, () -> new Placement("00H"));
        assertThrows(IllegalArgumentException.class, () -> new Placement("AAV"));
        assertThrows(IllegalArgumentException.class, () -> new Placement("@0L"));
        assertThrows(IllegalArgumentException.class, () -> new Placement("[0}"));
        assertThrows(IllegalArgumentException.class, () -> new Placement("A22"));
        assertThrows(IllegalArgumentException.class, () -> new Placement("A2w"));
        assertThrows(IllegalArgumentException.class, () -> new Placement("AA/QA"));
        assertThrows(IllegalArgumentException.class, () -> new Placement("A1"));
    }

}

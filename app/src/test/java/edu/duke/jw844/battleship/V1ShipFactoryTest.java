package edu.duke.jw844.battleship;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class V1ShipFactoryTest {

    private void checkShip(Ship<Character> testShip, String expectedName, char expectedLetter, Coordinate... expectedLocs) {
        assertEquals(expectedName, testShip.getName());
        for (Coordinate c : expectedLocs) {
            assertEquals(expectedLetter, testShip.getDisplayInfoAt(c,true));
        }
    }

//    @Test
//    public void checkMakeShip() {
//        V1ShipFactory f = new V1ShipFactory();
//        Placement v1_2 = new Placement(new Coordinate(1, 2), 'V');
//        Ship<Character> dst = f.makeDestroyer(v1_2);
//        checkShip(dst, "Destroyer", 'd', new Coordinate(1, 2), new Coordinate(2, 2), new Coordinate(3, 2));
//    }
}
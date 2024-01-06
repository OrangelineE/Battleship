package edu.duke.jw844.battleship;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BattleShipBoardTest {
    private <Character> void checkWhatIsAtBoard(BattleShipBoard<Character> b, Character[][] expected) {
        for (int i = 0; i < b.getWidth(); i++) {
            for (int j = 0; j < b.getHeight(); j++) {
                Character temp = b.whatIsAtForSelf(new Coordinate(i, j));
                if (temp != expected[i][j]) {
                    throw new IllegalArgumentException("expected value"+expected[i][j]+"at ("+i+","+j+") the real value "+ temp +"\n");
                }
            }
        }
    }
    @Test
    public void test_invalid_dimensions() {
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard(10, 0,'X'));
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard(0, 20,'X'));
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard(10, -5,'X'));
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard(-8, 20,'X'));
    }
    @Test
    public void test_width_and_height() {
        Board<Character> b1 = new BattleShipBoard(10, 20, 'X');
        assertEquals(10, b1.getWidth());
        assertEquals(20, b1.getHeight());
    }
    @Test
    public void test_placement() {
        Character [][] expected = new Character [2][2];
        BattleShipBoard<Character> b1 = new BattleShipBoard<Character>(2, 2, 'X');
        checkWhatIsAtBoard(b1, new Character[][]{{null, null}, {null, null}});
        b1.tryAddShip(new RectangleShip<Character>(new Coordinate(1, 1), 's', '*'));
        checkWhatIsAtBoard(b1,new Character[][]{{null,null}, {null,'s'}});
    }
    @Test
    public void test_rules() {
        BattleShipBoard b = new BattleShipBoard<>(3, 3,'X');
        V2ShipFactory v1 = new V2ShipFactory();
        Placement p1 = new Placement("a0v");
        Ship<Character> dest =  v1.makeDestroyer(p1);
        assertNull(b.tryAddShip(dest));
        //Try to add a ship that collides with others but in bound
        Placement p2 = new Placement("a0h");
        Ship<Character> newDes =  v1.makeDestroyer(p2);
        assertEquals("That placement is invalid: the ship overlaps another ship.\n", b.tryAddShip(newDes));
        //Try to add a ship that out of bound without collision
        Placement p3 = new Placement("a3h");
        Ship<Character> newS =  v1.makeSubmarine(p3);
        assertEquals("That placement is invalid: the ship goes off the right of the board.\n", b.tryAddShip(newS));
    }
    @Test
    public void test_fire_at() {
        BattleShipBoard b = new BattleShipBoard<>(3, 3,'X');
        V2ShipFactory v1 = new V2ShipFactory();
        Placement p1 = new Placement("a0h");
        Ship<Character> dest =  v1.makeDestroyer(p1);
        b.tryAddShip(dest);
//        assertNull(b.tryAddShip(dest));
        b.fireAt(new Coordinate("a0"));
        BoardTextView view = new BoardTextView(b);
        System.out.println(view.displayMyOwnBoard());
        assertEquals("  0|1|2\n" +
                "A *|d|d A\n" +
                "B  | |  B\n" +
                "C  | |  C\n" +
                "  0|1|2\n", view.displayMyOwnBoard().toString());
        System.out.println(view.displayEnemyBoard());

        assertFalse(dest.isSunk());
        b.fireAt(new Coordinate("a1"));
        assertFalse(dest.isSunk());
        b.fireAt(new Coordinate("a2"));
        assertTrue(dest.isSunk());

    }
    @Test
    public void test_isWin() {
        BattleShipBoard b = new BattleShipBoard<>(3, 3,'X');
        V2ShipFactory v1 = new V2ShipFactory();
        Placement p1 = new Placement("a0h");
        Ship<Character> dest =  v1.makeDestroyer(p1);
        b.tryAddShip(dest);
        b.fireAt(new Coordinate("a0"));
        b.fireAt(new Coordinate("a1"));
        assertFalse(b.isLose());
        b.fireAt(new Coordinate("a2"));
        assertTrue(b.isLose());
    }

    @Test
    public void test_sonarRang() {
        BattleShipBoard b = new BattleShipBoard<>(10, 20,'X');
        b.sonarCoverage("k4");
    }

}

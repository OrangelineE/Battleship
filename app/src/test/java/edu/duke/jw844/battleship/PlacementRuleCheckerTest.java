package edu.duke.jw844.battleship;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlacementRuleCheckerTest {
    @Test
    public void test_out_bound() {
        BattleShipBoard b = new BattleShipBoard<>(3, 3,'X');
        V2ShipFactory v1 = new V2ShipFactory();
//        V1ShipFactory v1 = new V1ShipFactory();

        Placement p1 = new Placement("a0h");
        Ship<Character> dest =  v1.makeDestroyer(p1);
        InBoundsRuleChecker<Character> bR = new InBoundsRuleChecker<Character>(null);
        assertNull (bR.checkMyRule(dest, b));
        b.tryAddShip(dest);
        Placement p2 = new Placement("a2h");
        Ship<Character> newDes =  v1.makeDestroyer(p2);
        assertEquals("That placement is invalid: the ship goes off the right of the board.\n", bR.checkMyRule(newDes, b));
    }
    @Test
    public void test_no_collision() {
        BattleShipBoard b = new BattleShipBoard<>(10, 20,'X');
//        V2ShipFactory v1 = new V2ShipFactory();
        V2ShipFactory v1 = new V2ShipFactory();
        Placement p1 = new Placement("a0u");
        Ship<Character> dest =  v1.makeCarrier(p1);
        InBoundsRuleChecker<Character> bR = new InBoundsRuleChecker<Character>(null);
        NoColliSionRuleChecker<Character> nC = new NoColliSionRuleChecker<Character>(null);
        //Follow the two rules
        assertNull (nC.checkMyRule(dest, b));
        assertNull (bR.checkMyRule(dest, b));
        b.tryAddShip(dest);
        //Collide with others but in bound
        Placement p2 = new Placement("a0l");
        Ship<Character> newDes =  v1.makeBattleship(p2);
        assertEquals("That placement is invalid: the ship overlaps another ship.\n", nC.checkMyRule(newDes, b));

        assertNull (bR.checkMyRule(newDes, b));
        //Out of bound but no collision
        Placement p3 = new Placement("a9h");
        Ship<Character> newS =  v1.makeSubmarine(p3);
        assertNull (nC.checkMyRule(newS, b));
        assertEquals("That placement is invalid: the ship goes off the right of the board.\n", bR.checkMyRule(newS, b));
    }

}
package edu.duke.jw844.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleShipDisplayInfoTest {
    @Test
    public void test_simpleShipDisplayInfo(){
        SimpleShipDisplayInfo<Character> ShipDisplay=new SimpleShipDisplayInfo<Character>('s','*');
        assertEquals('*',ShipDisplay.getInfo(new Coordinate(1,2),true));
        assertEquals('s',ShipDisplay.getInfo(new Coordinate(1,2),false));
    }
}
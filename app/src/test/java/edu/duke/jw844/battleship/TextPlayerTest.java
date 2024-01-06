package edu.duke.jw844.battleship;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TextPlayerTest {
    private TextPlayer createTextPlayer(int w, int h, String inputData, OutputStream bytes,String name) {
        BufferedReader input = new BufferedReader(new StringReader(inputData));
        PrintStream output = new PrintStream(bytes, true);
        Board<Character> board = new BattleShipBoard<Character>(w, h,'X');
        Board<Character> b2 = new BattleShipBoard<Character>(w, h,'X');
//        V1ShipFactory shipFactory = new V1ShipFactory();
        V2ShipFactory shipFactory = new V2ShipFactory();
        return new TextPlayer(board, input, output, shipFactory,name,b2);
    }
    private TextPlayer createTextPlayerWithEnemy(int w, int h, String inputData, OutputStream bytes,String name, Board enemyBoard) {
        BufferedReader input = new BufferedReader(new StringReader(inputData));
        PrintStream output = new PrintStream(bytes, true);
        Board<Character> board = new BattleShipBoard<Character>(w, h,'X');
//        V1ShipFactory shipFactory = new V1ShipFactory();
        V2ShipFactory shipFy = new V2ShipFactory();
        return new TextPlayer(board, input, output, shipFy,name,enemyBoard);
    }
//    @Test
//    void test_read_placement() throws IOException {
//        String sr = "B2V\nb3h\nb4v\n";
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        TextPlayer tp = createTextPlayer (10, 10, sr, bytes, "A");
//        String prompt = "where do you want to place a Destroyer?";
//        Placement[] expected = new Placement[3];
//        expected[0] = new Placement(new Coordinate(1, 2), 'V');
//        expected[1] = new Placement(new Coordinate(1, 3), 'H');
//        expected[2] = new Placement(new Coordinate(1, 4), 'V');
//
//        for (int i = 0; i < expected.length; i++) {
//            Placement p = tp.readPlacement(prompt);
//            assertEquals(p, expected[i]); //did we get the right Placement back
////            assertEquals("Player A " + prompt + System.lineSeparator(), bytes.toString()); //should have printed prompt and newline
//            bytes.reset(); //clear out bytes for next time around
//        }
//    }
//    @Test
//    void test_read_EOF() throws IOException {
//        String sr = "";
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        TextPlayer tp = createTextPlayer (10, 10, sr, bytes, "A");
//        assertThrows(IllegalArgumentException.class, tp::doPlacementPhase);
//    }
//
//    @Test
//    void test_onePhase() throws IOException {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        TextPlayer tp = createTextPlayer (10, 10, "a4V\nc6H\nh6h\ni2h\nb2v\n", bytes, "A");
//        assertThrows(IllegalArgumentException.class,()->new TextPlayer().doPlacementPhase());
////        assertEquals(bytes.toString(), "Player A: you are going to place the following ships (which are all rectangular). For each ship, type the coordinate of the upper left side of the ship, followed by either H (for horizontal) or V (for vertical).  For example M4H would place a ship horizontally starting at M4 and going to the right.  You have\n" +
////                "2 \"Submarines\" ships that are 1x2\n" +
////                "3 \"Destroyers\" that are 1x3\n" +
////                "3 \"Battleships\" that are 1x4\n" +
////                "2 \"Carriers\" that are 1x6\nPlayer A where do you want to place a Destroyer?\n" +
////                "  0|1|2|3|4|5|6|7|8|9\n" +
////                "A  | | | |d| | | | |  A\n" +
////                "B  | | | |d| | | | |  B\n" +
////                "C  | | | |d| | | | |  C\n" +
////                "D  | | | | | | | | |  D\n" +
////                "E  | | | | | | | | |  E\n" +
////                "F  | | | | | | | | |  F\n" +
////                "G  | | | | | | | | |  G\n" +
////                "H  | | | | | | | | |  H\n" +
////                "I  | | | | | | | | |  I\n" +
////                "J  | | | | | | | | |  J\n" +
////                "  0|1|2|3|4|5|6|7|8|9\n");
//                bytes.reset(); //clear out bytes for next time around
//    }
//
//    @Test
//    public void test_oneTurn () throws IOException {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        PrintStream output = new PrintStream(bytes, true);
//        BufferedReader input = new BufferedReader(new StringReader("a4V\n" + "c6H\n" + "h6h\n" + "i2h\n" + "b2v\n" +
//                "m4h\n" + "d2h\ng1h\nl2h\nt1h\n"));
////        V1ShipFactory vE = new V1ShipFactory();
//        V2ShipFactory vE = new V2ShipFactory();
//
//        Board<Character> eBoard = new BattleShipBoard<Character>(10, 20,'X');
//        TextPlayer A = createTextPlayerWithEnemy(10,20, "a4V\n" + "c6H\n" + "h6h\n" + "i2h\n" + "b2v\n" +
//                "m4h\n" + "d2h\ng1h\nl2h\nt1h\n",bytes,"A", eBoard);
//        TextPlayer B = new TextPlayer(eBoard,input, output, vE,"B",A.theBoard );
//        A.doPlacementPhase();
//        B.doPlacementPhase();
////        A.playOneTurn();
//}
//    @Test
//    public void test_movement () throws IOException {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        PrintStream output = new PrintStream(bytes, true);
//        BufferedReader input = new BufferedReader(new StringReader("a4V\n" + "c6H\n" + "h6h\n" + "i2h\n" + "b2v\n" +
//                "k4r\n" + "d2u\ng1d\nl2u\nr1l\n"));
////        V1ShipFactory vE = new V1ShipFactory();
//        V2ShipFactory vE = new V2ShipFactory();
//
//        Board<Character> eBoard = new BattleShipBoard<Character>(10, 20,'X');
////        TextPlayer A = createTextPlayerWithEnemy(10,20, "a4U\n" + "c6U\n" +"Q3r\n"+"m\n" + "a5\n" + "k4r\n",bytes,"A", eBoard);
////        TextPlayer A = createTextPlayerWithEnemy(10,20, "a4r\n" + "c6U\n" +"m\n" + "a5\n" + "s3l\n",bytes,"A", eBoard);
//        TextPlayer A = createTextPlayerWithEnemy(10,20, "a4V\n" + "c6H\n" + "h6h\n" + "i2h\n" + "b2v\n" +
//                "m4u\n" + "d2u\n" +
//                "g1u\n" +
//                "l2u\n" +
//                "r1l\n" +
//                "s\n" +
//                "j4\n" +
//                "s\n" +
//                "j4\n",bytes,"A", eBoard);
//
//        TextPlayer B = new TextPlayer(eBoard,input, output, vE,"B",A.theBoard );
//        A.doPlacementPhase();
//        B.doPlacementPhase();
//        A.playOneTurn();
//        A.playOneTurn();
//    }
    @Test
    public void test_rules () throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(bytes, true);
        BufferedReader input = new BufferedReader(new StringReader("a4V\n" + "c6H\n" + "h6h\n" + "i2h\n" + "b2v\n" +
                "k4r\n" + "d2u\ng1d\nl2u\nr1l\n"));
//        V1ShipFactory vE = new V1ShipFactory();
        V2ShipFactory vE = new V2ShipFactory();

        Board<Character> eBoard = new BattleShipBoard<Character>(10, 20,'X');
//        TextPlayer A = createTextPlayerWithEnemy(10,20, "a4U\n" + "c6U\n" +"Q3r\n"+"m\n" + "a5\n" + "k4r\n",bytes,"A", eBoard);
//        TextPlayer A = createTextPlayerWithEnemy(10,20, "a4r\n" + "c6U\n" +"m\n" + "a5\n" + "s3l\n",bytes,"A", eBoard);
        TextPlayer A = createTextPlayerWithEnemy(10,20, "a4V\n" + "c6H\n" + "h6h\n" + "i2h\n" + "b2v\n" +
                "m4u\n" + "d2u\n" +
                "g1u\n" +
                "l2u\n" +
                "r1l\n" +
                "m\n" +
                "a4\n" +
                "o9h\n"+
                "a4\n" + "o7h\n"
                ,bytes,"A", eBoard);

        TextPlayer B = new TextPlayer(eBoard,input, output, vE,"B",A.theBoard );
        A.doPlacementPhase();
        B.doPlacementPhase();
        A.playOneTurn();
    }

}



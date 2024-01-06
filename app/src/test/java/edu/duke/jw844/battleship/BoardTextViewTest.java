package edu.duke.jw844.battleship;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
public class BoardTextViewTest {

    private void emptyBoardHelper(int w, int h, String expectedHeader, String expectedBody){
        Board<Character> b1 = new BattleShipBoard<Character>(w, h,'X');
        BoardTextView view = new BoardTextView(b1);
        assertEquals(expectedHeader, view.makeHeader());
        String expected = expectedHeader + expectedBody + expectedHeader;
        assertEquals(expected, view.displayMyOwnBoard());
    }

    @Test
    public void test_display_empty_2by2() {
        Board<Character> b1 = new BattleShipBoard<Character>(2, 2,'X');
        BoardTextView view = new BoardTextView(b1);
        String expectedHeader = "  0|1\n";
        String expectedBody = "A  |  A\n"+ "B  |  B\n";
        emptyBoardHelper(2,2, expectedHeader, expectedBody);

    }
    @Test
    public void  test_invalid_board_size() {
        Board<Character> wideBoard = new BattleShipBoard<Character>(11,20,'X');
        Board<Character> tallBoard = new BattleShipBoard<Character>(10, 27,'X');
        assertThrows(IllegalArgumentException.class, ()-> new BoardTextView(wideBoard));
        assertThrows(IllegalArgumentException.class, ()-> new BoardTextView(tallBoard));
    }
    @Test
    public void test_display_empty_3by2() {
        Board<Character> b2 = new BattleShipBoard<Character>(2, 3,'X');
        BoardTextView view = new BoardTextView(b2);
        String expectedHeader = "  0|1\n";
        String expectedBody = "A  |  A\n"+ "B  |  B\n" + "C  |  C\n";
        emptyBoardHelper(2,3, expectedHeader, expectedBody);
    }
    @Test
    public void test_display_empty_3by5() {
        Board<Character> b2 = new BattleShipBoard<Character>(5, 3,'X');
        BoardTextView view = new BoardTextView(b2);
        String expectedHeader = "  0|1|2|3|4\n";
        String expectedBody = "A  | | | |  A\n"+ "B  | | | |  B\n" + "C  | | | |  C\n";
        emptyBoardHelper(5,3, expectedHeader, expectedBody);
    }
    @Test
    public void test_display_ships_3by4() {
        Board<Character> b = new BattleShipBoard<Character>(3, 4,'X');
        BasicShip b1 = new RectangleShip<Character>(new Coordinate(1, 1), 's','*');
        BasicShip b2 = new RectangleShip(new Coordinate(1, 0), 's','*');

        b.tryAddShip(b1);
        b.tryAddShip(b2);
        //add ship to (1,1)
        BoardTextView view = new BoardTextView(b);
        assertEquals(view.makeHeader() +
                        "A  | |  A\n" +
                        "B s|s|  B\n" +
                        "C  | |  C\n" +
                        "D  | |  D\n" + view.makeHeader(),view.displayMyOwnBoard());

    }
    @Test
    public void test_display_destroyer() {
        Board<Character> b = new BattleShipBoard<Character>(3, 4,'X');
        V2ShipFactory f = new V2ShipFactory();
        Placement v1_2 = new Placement(new Coordinate(1, 2), 'V');
        Ship<Character> dst = f.makeDestroyer(v1_2);
        System.out.println(b.tryAddShip(dst));
        //add a Destroyer to (1,2)
        BoardTextView view = new BoardTextView(b);
        assertEquals(view.makeHeader() +
                "A  | |  A\n" +
                "B  | |d B\n" +
                "C  | |d C\n" +
                "D  | |d D\n" + view.makeHeader(),view.displayMyOwnBoard());
        b.fireAt(new Coordinate(1,2));
        assertEquals(view.makeHeader() +
                "A  | |  A\n" +
                "B  | |d B\n" +
                "C  | |  C\n" +
                "D  | |  D\n" + view.makeHeader(),view.displayEnemyBoard());
        assertEquals(view.makeHeader() +
                "A  | |  A\n" +
                "B  | |* B\n" +
                "C  | |d C\n" +
                "D  | |d D\n" + view.makeHeader(),view.displayMyOwnBoard());

    }
    @Test
    public void test_display2Boards() {
        Board<Character> b1 = new BattleShipBoard<Character>(10, 20,'X');
        Board<Character> b2 = new BattleShipBoard<Character>(10, 20,'X');
        V2ShipFactory f = new V2ShipFactory();
        Placement v1_2 = new Placement(new Coordinate(1, 2), 'V');
        Ship<Character> dst = f.makeDestroyer(v1_2);
        b1.tryAddShip(dst);
        b2.tryAddShip(dst);
        b2.fireAt(new Coordinate(1, 2));
        b2.fireAt(new Coordinate(5, 2));

        //add a submarine to (1,2)
        BoardTextView view1 = new BoardTextView(b1);
        BoardTextView view2 = new BoardTextView(b2);
        System.out.println(view1.displayMyBoardWithEnemyNextToIt(view2, "Your ocean", "PlayerB's ocean"));

    }

}

package edu.duke.jw844.battleship;

import java.util.function.Function;

/**
 * This class handles textual display of a Board (i.e., converting it to a string to show to the user).
 * It supports two ways to display the Board:
 * one for the player's own board, and one for the enemy's board.
 */
public class BoardTextView {
    /**
     * The Board to display
     */
    private final Board<Character> toDisplay;
    /**
     * Constructs a BoardView, given the board it will display.
     * @param toDisplay is the Board to display
     * @throws IllegalArgumentException if the board is larger than 10x26.
     */
    public BoardTextView(Board<Character> toDisplay) {
        this.toDisplay = toDisplay;
        if(toDisplay.getWidth() > 10 || toDisplay.getHeight() > 26) {
            throw new IllegalArgumentException(
                "Board must be no larger than 10x26, but is" + toDisplay.getWidth() + "x" + toDisplay.getHeight());
        }
    }
    /**
     * Display the board from my own view
     */
    public String displayMyOwnBoard() {
        return displayAnyBoard((c)->toDisplay.whatIsAtForSelf(c));
    }
    /**
     * Display the board from enemy's view
     */
    public String displayEnemyBoard() {
        return displayAnyBoard((c)->toDisplay.whatIsAtForEnemy(c));
    }
    /**
     * This makes the header line, e.g. 0|1|2|3|4\n
     * @return the String that is the header line for the given board
     */
    String makeHeader() {
        StringBuilder ans = new StringBuilder("  ");
        String sep = "";
        for (int i = 0; i < toDisplay.getWidth(); i++) {
            ans.append(sep);
            ans.append(i);
            sep = "|";
        }
        ans.append("\n");
        return ans.toString();
    }
    /**
     * This makes the whole board
     * @param getSquareFn the function is to take the Coordinate as the argument and return as a Character
     * @return the String that is the whole board
     */
    protected String displayAnyBoard(Function<Coordinate, Character> getSquareFn) {
        StringBuilder ans = new StringBuilder();
        ans.append(makeHeader());
        for (int i = 0; i < toDisplay.getHeight(); i++) {
            char alp = (char) ('A' + i);
            ans.append(alp);
            ans.append(" ");
            String sep = "";
            for (int j = 0; j < toDisplay.getWidth() ; j++) {
                ans.append(sep);
                Character tmp = getSquareFn.apply(new Coordinate(i , j)) ;
                tmp = (tmp == null) ? ' ' : tmp;
                ans.append(tmp);
                sep = "|";
            }
            ans.append(" ");
            ans.append(alp);
            ans.append("\n");
        }
        ans.append(makeHeader());
        return ans.toString();
    }
    /**
     * This display my own board and enemy's board together
     * @param enemyView BoardTextView of enemy's
     * @param myHeader String that contains my own header context
     * @param enemyHeader String that contains enemy's context
     * @return the String contains the two boards
     */
    public String displayMyBoardWithEnemyNextToIt(BoardTextView enemyView, String myHeader, String enemyHeader) {
        StringBuilder res = new StringBuilder();
        int i = 0;
        while(i < 2 * toDisplay.getWidth() + 22) {
            if (i == 5) {
                res.append(myHeader);
                i += myHeader.length();
            }
            else {
                res.append(" ");
                i++;
            }
        }
        res.append(enemyHeader +"\n");
        String[] myLines = this.displayMyOwnBoard().split("\n");
        String[] enLines = enemyView.displayEnemyBoard().split("\n");

        for (int j = 0; j < toDisplay.getHeight() + 2; j++) {

            res.append(myLines[j]);
            for (int k = myLines[j].length(); k < 2 * toDisplay.getWidth() + 19; k++) {
                res.append(" ");
            }
            res.append(enLines[j] + "\n");
        }
    return res.toString();
    }
}

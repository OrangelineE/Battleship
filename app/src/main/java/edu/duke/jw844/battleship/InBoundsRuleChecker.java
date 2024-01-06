package edu.duke.jw844.battleship;
/**
 * This class check if the placement is in the bound of the board
 */
public class InBoundsRuleChecker<T> extends PlacementRuleChecker<T>{
    /**
     * Checks if the coordinate of the ship is in the board
     * @param theShip the ship we want to check
     * @param theBoard the board we want to be checked
     * @return null if the placement is perfect else return the corresponding prompt
     */
    @Override
    protected String checkMyRule(Ship<T> theShip, Board<T> theBoard) {
        for (Coordinate c: theShip.getCoordinates()) {
            if (c.getRow() < 0) {
                return "That placement is invalid: the ship goes off the top of the board.\n";
            }
            if (c.getColumn() < 0 ) {
                return "That placement is invalid: the ship goes off the left of the board.\n";
            }
            if (c.getRow() >= theBoard.getHeight()){
               return  "That placement is invalid: the ship goes off the bottom of the board.\n";
            }
            if (c.getColumn() >= theBoard.getWidth()) {
                return "That placement is invalid: the ship goes off the right of the board.\n";
            }
        }
        return null;
    }
    /**
     * Constructs a rule checker
     * @param next represents the next rule we want to check
     */
    public InBoundsRuleChecker(PlacementRuleChecker<T> next) {
        super(next);
    }
}

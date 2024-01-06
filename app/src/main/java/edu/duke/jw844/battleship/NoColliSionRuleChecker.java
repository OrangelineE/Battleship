package edu.duke.jw844.battleship;
/**
 * This class check if the placement follows the no collision rule
 */
public class NoColliSionRuleChecker<T> extends PlacementRuleChecker<T>{
    /**
     * Checks if the ship has no collision with other ships
     * @param theShip the ship we want to check
     * @param theBoard the board we want to be checked
     * @return null if the placement is perfect else return the corresponding prompt
     */
    @Override
    protected String checkMyRule(Ship<T> theShip, Board<T> theBoard) {
        for (Coordinate c: theShip.getCoordinates()) {
            if (theBoard.whatIsAtForSelf(c) != null) {
//                System.out.println(theBoard.whatIsAtForSelf(c) + "( " + c.getRow()+ ", " + c.getColumn() +" )\n");
                return "That placement is invalid: the ship overlaps another ship.\n";
            }
        }
        return null;
    }
    /**
     * Constructs a rule checker
     * @param next represents the next rule we want to check
     */
    public NoColliSionRuleChecker(PlacementRuleChecker<T> next) {
        super(next);
    }
}

package edu.duke.jw844.battleship;

public abstract class PlacementRuleChecker<T> {
    private final PlacementRuleChecker<T> next;
    /**
     * Constructs a placementRuleChecker
     * @param next the next rule we want to check
     */
    public PlacementRuleChecker(PlacementRuleChecker<T> next) {
        this.next = next;
    }
    protected abstract String checkMyRule(Ship<T> theShip, Board<T> theBoard);
    /**
     * To check the placement with rules if it follows every rule, return null
     * @param theShip the ship we want to check
     * @param theBoard the board we want to be checked
     * return null if the placement follows every rule otherwise return the corresponding prompt
     */
    public String checkPlacement (Ship<T> theShip, Board<T> theBoard) {
        //if we fail our own rule: stop the placement is not legal
        String tmp = checkMyRule(theShip, theBoard);
        if (tmp!= null) {
            return tmp;
        }
        //otherwise, ask the rest of the chain.
        if (next != null) {
            return next.checkPlacement(theShip, theBoard);
        }
        //if there are no more rules, then the placement is legal
        return null;
    }
}
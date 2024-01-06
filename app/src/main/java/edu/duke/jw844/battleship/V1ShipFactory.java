//package edu.duke.jw844.battleship;
//
//public class V1ShipFactory implements AbstractShipFactory<Character>{
//    /**
//     * To create the ship at the given position, specific letter and regulated width and height
//     * @param where the position we want to create a ship
//     * @param w the width of the ship
//     * @param h the height of the ship
//     * @param letter represents the ship
//     * @param name is the ship name
//     */
//    protected Ship<Character> createShip(Placement where, int w, int h, char letter, String name) {
//        if (where.getOrientation() == 'H') return new RectangleShip<Character>(name, where.getCoordinate(),  h, w, letter,'*');
//        if (where.getOrientation() != 'H' && where.getOrientation() != 'V') {
//            throw new IllegalArgumentException("The orientations is invalid as " + where.getOrientation() + "!\n");
//        }
//        return new RectangleShip<Character>(name, where.getCoordinate(), w, h, letter,'*');
//    }
//
//    @Override
//    public Ship<Character> makeSubmarine(Placement where) { return createShip(where, 1, 2, 's', "Submarine"); }
//
//    @Override
//    public Ship<Character> makeBattleship(Placement where) {
//        return createShip(where, 1, 4, 'b', "Battleship");
//    }
//
//    @Override
//    public Ship<Character> makeCarrier(Placement where) { return createShip(where, 1, 6, 'c', "Carrier"); }
//
//    @Override
//    public Ship<Character> makeDestroyer(Placement where) {
//        return createShip(where, 1, 3, 'd', "Destroyer");
//    }
//}

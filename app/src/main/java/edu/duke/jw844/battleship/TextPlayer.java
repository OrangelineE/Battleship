package edu.duke.jw844.battleship;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.Function;
/**
 * This class handles the player information
 * It is able to read the placement of the user ,print corresponding prompts
 * and display the board for users
 */
public class TextPlayer<T> implements Player<T>{
    final Board<Character> theBoard;
    final BoardTextView view;
    final BufferedReader inputReader;
    final PrintStream out;
    final String playerName;
    final AbstractShipFactory<Character> shipFactory;
    final ArrayList<String> shipsToPlace;
    final HashMap<String, Function<Placement, Ship<Character>>> shipCreationFns;
    final Board<Character> enemyBoard;
    final BoardTextView enemyView;
    int moveTimes = 3;
    int sonarTimes = 3;

    /**
     * To link the ship name with its creation function
     */
    protected void setupShipCreationMap() {
        shipCreationFns.put("Submarine", (p) -> shipFactory.makeSubmarine(p));
        shipCreationFns.put("Destroyer", (p) -> shipFactory.makeDestroyer(p));
        shipCreationFns.put("Battleship", (p) -> shipFactory.makeBattleship(p));
        shipCreationFns.put("Carrier", (p) -> shipFactory.makeCarrier(p));
    }
    /**
     * To set up the ships for the player
     */
    protected void setupShipCreationList() {
        shipsToPlace.addAll(Collections.nCopies(2, "Submarine"));
        shipsToPlace.addAll(Collections.nCopies(3, "Destroyer"));
        shipsToPlace.addAll(Collections.nCopies(3, "Battleship"));
        shipsToPlace.addAll(Collections.nCopies(2, "Carrier"));
    }
    /**
     * Construct the text player and initialize the fields
     */
    public TextPlayer() {
        this.theBoard = new BattleShipBoard<Character>(10, 20,'X');
        this.enemyBoard = new BattleShipBoard<Character>(10, 20,'X');
        this.view = new BoardTextView(theBoard);
        this.enemyView = new BoardTextView(enemyBoard);
        this.inputReader = new BufferedReader(new InputStreamReader(System.in));
        this.out = System.out;
        this.shipFactory = new V2ShipFactory();
        this.playerName = "Default_A";
        this.shipsToPlace = new ArrayList<>();
        this.shipCreationFns = new HashMap<>();
        setupShipCreationList();
        setupShipCreationMap();
    }
    /**
     * Construct the text player and initialize the fields
     * @param theBoard the board to play
     * @param inputSource the user's input
     * @param out to display the board
     * @param v the ship factory
     * @param s_name is the player's name
     * @param enemyBoard is the enemy's board
     */
    public TextPlayer(Board<Character> theBoard, Reader inputSource, PrintStream out, V2ShipFactory v,
                      String s_name,Board<Character> enemyBoard) {
        this.theBoard = theBoard;
        this.view = new BoardTextView(theBoard);
        this.inputReader = new BufferedReader(inputSource);
        this.out = out;
        this.shipFactory = v;
        this.playerName = s_name;
        this.shipsToPlace = new ArrayList<>();
        this.shipCreationFns = new HashMap<>();
        this.enemyBoard = enemyBoard;
        this.enemyView = new BoardTextView(enemyBoard);
        setupShipCreationList();
        setupShipCreationMap();
    }
    /**
     * Display the prompt and read the placement of the user
     * parse it and generate a new placement
     * @param prompt is the prompt need to display
     * @return the placement the user want
     * @throws IllegalArgumentException if the user input nothing
     */
    public Placement readPlacement(String prompt) throws IOException {
        out.println(prompt);
        String s = inputReader.readLine();
        if (s == null) {
            throw new IOException("The readLine is empty!\n");
        }
        return new Placement(s);
    }
    /**
     * Display the prompt and read the placement of the user
     * parse it and generate a new placement
     * @param prompt is the prompt need to display
     * @return the placement the user want
     * @throws IllegalArgumentException if the user input nothing
     */
    public Coordinate readCoordinate(String prompt) throws IOException {
        out.println(prompt);
        String s = inputReader.readLine();
        if (s == null) {
            throw new IOException("The input is empty!\n");
        }
        return new Coordinate(s);
    }
    /**
     * Display the prompt and read the placement of the user
     * parse it and generate the
     * @param prompt is the prompt need to display
     * @return the placement the user want
     * @throws IllegalArgumentException if the user input nothing
     */
    public char readChoices(String prompt) throws IOException {
        System.out.println(prompt);
        String s = inputReader.readLine();
        if (s == null) {
            throw new IOException("The input is empty!\n");
        }
        if (s.length() != 1 ||( s.charAt(0) != 'F' && s.charAt(0) != 'S' && s.charAt(0) != 'M' && s.charAt(0) != 'f' && s.charAt(0) != 's' && s.charAt(0) != 'm')) {
            throw new IllegalArgumentException("The input is invalid as " + s.charAt(0) + " !\n");
        }
        return Character.toUpperCase(s.charAt(0));
    }
    /**
     * Display the prompt to the user at the beginning of the game
     */
    public void printMsg() {
        StringBuilder res = new StringBuilder("");
        res.append("Player " + playerName + ": ");
        res.append("you are going to place the following ships (which are all rectangular). " +
                "For each ship, type the coordinate of the upper left side of the ship, " +
                "followed by either H (for horizontal) or V (for vertical).  " +
                "For example M4H would place a ship horizontally starting at M4 and going to the right.  You have");
        res.append(System.lineSeparator());
        res.append("2 \"Submarines\" ships that are 1x2" + System.lineSeparator() +
                "3 \"Destroyers\" that are 1x3" + System.lineSeparator() +
                "3 \"Battleships\" that are 1x4" + System.lineSeparator() +
                "2 \"Carriers\" that are 1x6");
        System.out.println(res);
    }
    /**
     * Ask user to place a type of ships on the board, and then check the placement
     * If the placement break one of the rules, print the message
     * @param shipName the ship we ask the user to put
     * @param createFn the function takes a Placement as argument and return a Character
     * @throws IllegalArgumentException if a user input nothing and there is a wrong placement
     */
    public boolean doOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn) throws IOException {
        StringBuilder res = new StringBuilder("Player " + playerName + ": where do you want to place a " + shipName + "? Please input both coordinate and orientation, e.g. a0h");
        Placement p = readPlacement(res.toString());
        Ship<Character> s = createFn.apply(p);
        String problem = theBoard.tryAddShip(s);
        if (problem == null) {
            out.println(view.displayMyOwnBoard());
            return true;
        }
        else throw new IllegalArgumentException(problem);
    }
    /**
     * Ask user to place a type of ships on the board, and then check the placement
     * If the placement break one of the rules, print the message
     */
    public void doPlacementPhase() {
        out.println(view.displayMyOwnBoard());
        printMsg();
        for(String s : shipsToPlace) {
            while(true) {
                try {
                    while(!doOnePlacement(s,shipCreationFns.get(s))) {}
                    break;
                }catch (Exception e) {
                    out.println(e + "\nPlease input again.\n");
                }
            }
        }
    }

    /**
     * Display the board and allow users to fire, move and do sonar scan
     */
    public void playOneTurn() {
        while(true) {
            try{
                System.out.println(view.displayMyBoardWithEnemyNextToIt(enemyView,"Your Ocean", "Enemy's ocean"));
                StringBuilder prompt = new StringBuilder("Possible actions for Player " + playerName + "\n" +
                        "\n" +
                        " F Fire at a square\n" );
                if(moveTimes > 0) {
                    prompt.append(" M Move a ship to another square (" + moveTimes + " remaining) \n");
                }
                if (sonarTimes > 0) {
                    prompt.append(" S Sonar scan (" + sonarTimes + " remaining) \n");
                }
                prompt.append("Player " + playerName + ": what would you like to do?");
                if (moveTimes > 0 || sonarTimes > 0) {
                    char temp = readChoices(prompt.toString());
                    temp = Character.toUpperCase(temp);
                    if (temp == 'F') {
                        doFire();
                    } else if (moveTimes > 0 && temp == 'M' && doMovement()) {
                       moveTimes--;
                    }
                    else if (sonarTimes > 0 && temp == 'S'){
                        out.println(doSonar());
                        sonarTimes--;
                    }
                    else {throw new IllegalArgumentException("Your choice is invalid as " + temp + ".\n");}
                }
                else{
                    doFire();
                    }
                break;
        }catch (Exception e) {
            out.println(e);
        }
    }
    }
    /**
     * Move the ship the user selected to the destination
     * @throws IllegalArgumentException if there is no ship at the input coordinate
     * @return true if there is no exception
     */
    public boolean doMovement() throws IOException {
        System.out.println("Player " + playerName + ": which ship do you want to move (input one of its coordinates)");
        String s = inputReader.readLine();
        out.println(view.displayMyBoardWithEnemyNextToIt(enemyView, "Your Ocean", "Enemy's ocean"));
        if (theBoard.findAShip(s) == null) {
            throw new IllegalArgumentException("Can not find a ship at (" + (Character.toUpperCase(s.charAt(0)) - 'A') + ", " + s.charAt(1) + ") \n");
        }
        else{
            Ship<Character> theShip = theBoard.findAShip(s);
            makeMove(theShip, shipCreationFns.get(theShip.getName()));
            out.println(view.displayMyBoardWithEnemyNextToIt(enemyView, "Your Ocean", "Enemy's ocean"));
        }
        return true;
    }
    /**
     * Fire at the Coordinate the user input
     */
    public void doFire() {
        StringBuilder res = new StringBuilder("Player " + playerName + ": please input a valid coordinate to fire at your enemy, e.g. a4 \n");
        while(true) {
            try{
                Coordinate c = readCoordinate(res.toString());
                if (enemyBoard.fireAt(c) == null) {
                    out.println("You missed!");
                } else {
                    out.println("You hit a " + enemyBoard.fireAt(c).getName() + " !");
                }
                break;
            }catch (Exception e) {
                out.println(e);
            }
        }
        out.println(view.displayMyBoardWithEnemyNextToIt(enemyView, "Your Ocean", "Enemy's ocean"));
    }
    /**
     * Prompt thr user to select a ship and set the destination, and read the coordinate to move the ship to the target destination
     * @throws IOException when the input is null
     */
    public void makeMove(Ship<Character> toMove, Function<Placement, Ship<Character>> createFn) throws IOException {
        System.out.println("Player " + playerName + ": you choose the " + toMove.getName() + "! Now please input the position + orientation you want, e.g. a4h for a Submarine, k6l for a Carrier" +
                "\nNote that the Submarine and Destroyer has H (Horizontal) or V (Vertical) directions," + "the Battleship and Carrier has U(Up)/ R(Right)/ D(Down)/ L(Left) orientations\n");
        String input_des = inputReader.readLine();
        Placement p = new Placement(input_des);
        Ship<Character> s = createFn.apply(p);
        String problem = theBoard.CheckShipPlacement(s);
        if (problem != null)  throw new IllegalArgumentException(problem);
        toMove.makeMovement(s.getComponent());
    }
    /**
     *  Read the user's input so that the coverage of sonar scan will be ensured and then print the ship display information within the coverage to the user
     * @return String shows the ship occurrence situation
     */
    public String doSonar() {
        while(true) {
            try {
                System.out.println("Player " + playerName + ":  Now please input the center coordinate of the sonar scan on the board, e.g. a4");
                String center = inputReader.readLine();
                if (center == null) {
                    throw new IOException("The input is empty!\n");
                }
                return enemyBoard.sonarScan(center);
            }catch (Exception e) {
                out.println(e);
            }
        }
    }
    /**
     *  Get the board information of this player
     * @return theBoard of this player
     */
    public Board<Character> getBoard() {
        return theBoard;
    }
    /**
     *  Get the board text view information of this player
     * @return view of this player
     */
    public BoardTextView getBoardView() {
        return view;
    }
    /**
     *  Get the name of this player
     * @return playerName of this player
     */
    public String getPlayerName() {
        return playerName;
    }

}


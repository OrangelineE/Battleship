/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.jw844.battleship;

import java.io.*;
/**
 * This class handles the whole game process between two players
 * Firstly, two players place their battleships
 * Then, they try to hit others' ships until one of them wins
 */
public class App {
    final Player playerA;
    final Player playerB;
    /**
     * Constructs an App with two players
     */
    public App () {
        this.playerA = new TextPlayer();
        this.playerB = new TextPlayer();
    }
    /**
     * Constructs an App with two given players
     */
    public App (Player p1, Player p2) {
        this.playerA = p1;
        this.playerB = p2;
    }
    /**
     * To check if the game has the winner
     * if yes, return true. Otherwise, return false
     */
    public boolean isWin() {
        if (playerA.getBoard().isLose()){
            System.out.println("Player " + playerB.getPlayerName()+ " WINS!" );
            return true;
        } else if(playerB.getBoard().isLose()) {
            System.out.println("Player " + playerA.getPlayerName()+ " WINS!" );
            return true;
        }
        return false;
    }
    /**
     * Decide whether the player type based on user's input
     * @param theBoard the player's board
     * @param newReader read the user's input
     * @param out to display the board
     * @param v the ship factory
     * @param s_name is the player's name
     * @param enemyBoard is the enemy's board
     */
    public static Player<Character> doOneSetting(Board<Character> theBoard, BufferedReader newReader, PrintStream out, V2ShipFactory v, String s_name, Board<Character> enemyBoard) throws IOException {
        while (true){
            try {
            String input = newReader.readLine();
            char in = Character.toUpperCase(input.charAt(0));
            if (in == 'Y') {
                return new TextPlayer(theBoard, newReader, out, v, s_name, enemyBoard);
            } else if (in == 'N') {
                return new ComputerPlayer<Character>(theBoard, out, v, s_name, enemyBoard);
            } else throw new IllegalArgumentException("Invalid choice! Please input again!");
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
    /**
     * To do the placement phase
     */
    public void doPlacementPhase() throws IOException {
        playerA.doPlacementPhase();
        playerB.doPlacementPhase();
    }
    /**
     * To do the attacking phase one by one until there is a winner
     */
    public void doAttackingPhase() throws IOException {
        while(!isWin()) {
            playerA.playOneTurn();
            if (isWin()) break;
            playerB.playOneTurn();
        }
    }
    /**
     * Controls the whole game process:
     * create two boards and players
     * and then do the placement phase and attacking phase
     */
    public static void main(String[] args) throws IOException {

        Board<Character> newBoard1 = new BattleShipBoard<Character>(10, 20,'X');
        Board<Character> newBoard2 = new BattleShipBoard<Character>(10, 20,'X');
        BufferedReader newReader = new BufferedReader(new InputStreamReader(System.in));
        V2ShipFactory vShip = new V2ShipFactory();
        StringBuilder ans = new StringBuilder("Hello! Welcome to the BattleShip Game! There are two players. Each od them can be played by either human or computer.\n" +
                "Now please make choices for them : Do you want the player A to be a human player? (y/n)");
        System.out.println(ans);
        Player<Character> Aplayer = doOneSetting(newBoard1, newReader,System.out,vShip,"A",newBoard2);
        System.out.println("Do you want the player B to be a human player? (y/n)");
        Player<Character> Bplayer = doOneSetting(newBoard2, newReader,System.out,vShip,"B",newBoard1);
        App newApp = new App(Aplayer, Bplayer);
        newApp.doPlacementPhase();
        newApp.doAttackingPhase();
    }


}

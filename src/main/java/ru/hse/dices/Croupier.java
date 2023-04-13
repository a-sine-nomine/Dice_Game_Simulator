package ru.hse.dices;

import java.util.List;
import java.util.ArrayList;

/**
 * Stores a list of players participating in the current game and communicates with them during the game.
 *
 * @author ALisa Khabibrakhmanova
 */

class Croupier {
    /**
     * Constructs Croupier instance and makes players. In each team there is 3 players.
     *
     * @param commandsNumber number of teams
     */
    public Croupier(int commandsNumber) {
        this.commandsNumber = commandsNumber;
        this.endedGame = false;
        this.allSaidBye = 0;
        isAllTalked = false;
        numTalked = 0;
        sheet = new Sheet();
        players = makePlayers(sheet);
    }

    /**
     * Starts the 35 game performance. Shows leader table each 10 seconds. After the game announces the winners and prize.
     */
    public void playGame() {
        System.out.println("Let's start the game!");
        for (Thread player : players) {
            player.start();
        }

        for (int i = 2; i >= 0; i--) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                System.err.println("Thread Interrupted");
            }
            sheet.printLeader();
            System.out.println((10 * i + 5) + " seconds of the game left!");
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.err.println("Thread Interrupted");
        }

        for (Thread player : players) {
            player.interrupt();
        }
        System.out.println("""
                _________________________________
                End of the game!
                _________________________________
                """);

        endGame();

        synchronized (sheet) {
            while (!(this.getAllSaidBye() == players.size())) {
                try {
                    sheet.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    /**
     * Ends the game. Prints winners and prize and final board.
     */
    public void endGame() {
        synchronized (sheet) {
            sheet.printLeader();
            sheet.chooseMoney();
            System.out.printf("\n_________________________________\nThe win is %d¥\n_________________________________\n", sheet.getMoney());
            sheet.printAll();
            System.out.println("_________________________________");
            setEndedGame();
            sheet.notify();
        }
    }

    /**
     * Creates the list of the players.
     *
     * @param sheet Table where the score of the teams is stored.
     * @return list of the players.
     */
    public List<Thread> makePlayers(Sheet sheet) {
        List<Thread> threads = new ArrayList<>();
        int playerNum = 1;
        int temNum = 1;
        for (int i = 0; i < commandsNumber; i++) {
            Table table = new Table();
            for (int j = 0; j < 3; j++) {
                Player player = new Player("player" + playerNum, "team" + temNum, table, sheet, this);
                Thread thread = new Thread(player);
                threads.add(thread);
                playerNum++;
            }
            sheet.newTeam("team" + temNum);
            temNum++;
        }
        return threads;
    }

    /**
     * Checks if croupier ended the game.
     *
     * @return true if croupier ended the game. False otherwise.
     */
    public boolean isEndedGame() {
        return endedGame;
    }

    /**
     * Sets up that the croupier finished the game.
     */
    public void setEndedGame() {
        endedGame = true;
    }

    /**
     * Gets the number of players that said bye.
     *
     * @return the number of players that said bye.
     */
    public int getAllSaidBye() {
        return allSaidBye;
    }

    /**
     * Increases the number of people who have said bye.
     */
    public void incAllSaidBye() {
        allSaidBye++;
    }

    /**
     * Gets number of winners that talked.
     *
     * @return number of winners that talked.
     */
    public int getNumTalked() {
        return numTalked;
    }

    /**
     * Increases the number of winners that talked.
     */
    public void incNumTalked() {
        numTalked++;
    }

    /**
     * Gets whether all winners talked.
     *
     * @return true if all winners talked, false otherwise.
     */
    public boolean isAllTalked() {
        return isAllTalked;
    }

    /**
     * Sets isAllTalked to true.
     */
    public void setAllTalked() {
        this.isAllTalked = true;
    }

    /**
     * Number of teams in the game.
     */
    private final int commandsNumber;
    /**
     * List of players in the game.
     */
    private List<Thread> players;
    /**
     * Flag of ending the game.
     */
    private boolean endedGame;
    /**
     * Number of players that said bye.
     */
    private int allSaidBye;
    /**
     * Flag if all winners talked.
     */
    private boolean isAllTalked;
    /**
     * Number of the winners that talked.
     */
    private int numTalked;
    /**
     * Sheet with score of the game.
     */
    private final Sheet sheet;
}

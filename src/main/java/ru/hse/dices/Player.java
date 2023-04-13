package ru.hse.dices;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Player is the thread that roll the dices and write down the new score on the sheet.
 * Player shares the table with others players and can roll only when the table is free.
 */

class Player implements Runnable {
    /**
     * Constructs new player.
     *
     * @param name     Name of the player.
     * @param teamName Name of the team.
     * @param table    Team table for dice roll.
     * @param sheet    Sheet to write down new score.
     * @param croupier Croupier of the game.
     */

    public Player(String name, String teamName, Table table, Sheet sheet, Croupier croupier) {
        this.points = 0;
        this.name = name;
        this.teamName = teamName;
        this.table = table;
        this.sheet = sheet;
        this.croupier = croupier;
        this.random = new Random();
    }

    /**
     * Player plays the game till it interrupted. He waits till croupier announce the
     * results and then says if he won and says bye.
     */
    @Override
    public void run() {
        System.out.println("Hello, I'm " + name + " from " + teamName + ".");
        while (!Thread.interrupted()) {
            try {
                int newPoints = table.roll();
                points += newPoints;
                sheet.write(teamName, newPoints);
                Thread.sleep(random.nextInt(901) + 100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        synchronized (sheet) {
            while (!croupier.isEndedGame()) {
                try {
                    sheet.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            sayWin();
            sheet.notify();
            while (!croupier.isAllTalked()) {
                try {
                    sheet.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println(name + " says bye.");
            croupier.incAllSaidBye();
            sheet.notify();
        }
    }

    /**
     * Player says if he won at the end of the game and prize he gets then.
     *
     * @return true if player is the winner, false otherwise.
     */
    public boolean sayWin() {
        boolean isWinner = false;
        List<Map.Entry<String, Integer>> winners = sheet.getLeaders();
        for (Map.Entry<String, Integer> winner : winners) {
            if (winner.getKey().equals(teamName)) {
                isWinner = true;
                System.out.printf("%s gets %.2f¥\n", name, (double) points / winner.getValue() * (sheet.getMoney() / winners.size()));
                croupier.incNumTalked();
                if (croupier.getNumTalked() == winners.size() * 3) {
                    croupier.setAllTalked();
                    System.out.println("_________________________________");
                }
            }
        }
        return isWinner;
    }

    /**
     * Amount of points.
     */
    private int points;
    /**
     * Common table to roll the dice.
     */
    private final Table table;
    /**
     * Common sheet to write down the points.
     */
    private final Sheet sheet;
    /**
     * Croupier of the current game.
     */
    private final Croupier croupier;
    /**
     * Player's name.
     */
    private final String name;
    /**
     * The name og the team;
     */
    private final String teamName;
    /**
     * Random for sleep.
     */
    private final Random random;

}

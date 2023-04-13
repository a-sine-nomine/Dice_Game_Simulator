package ru.hse.dices;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * Sheet for storing score for all teams participating in game.
 */

class Sheet {
    /**
     * Constructs new empty sheet.
     */
    public Sheet() {
        isBusy = false;
    }

    /**
     * Writes down new score for team if it presents in list.
     *
     * @param team      Name of the team.
     * @param newPoints New score to add for team.
     * @throws InterruptedException if thread id interrupted.
     */
    public synchronized void write(String team, int newPoints) throws InterruptedException {
        while (isBusy) {
            wait();
        }
        isBusy = true;
        if (teams.get(team) == null)
            return;
        teams.put(team, newPoints + teams.get(team));
        isBusy = false;
        notifyAll();
    }

    /**
     * Prints all leaders in command line.
     *
     * @return true if there is teams and false otherwise.
     */
    public synchronized boolean printLeader() {
        if (teams.isEmpty())
            return false;
        while (isBusy) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Thread Interrupted");
            }
        }
        isBusy = true;

        List<Map.Entry<String, Integer>> leaders = getLeaders();
        System.out.println("Board of the leader(s):");
        for (Map.Entry<String, Integer> leader : leaders) {
            System.out.println(leader.getKey() + ": " + leader.getValue());
        }

        isBusy = false;
        notifyAll();
        return true;
    }

    /**
     * Return the list of current leaders of the game.
     *
     * @return list of current leaders of the game.
     */
    public List<Map.Entry<String, Integer>> getLeaders() {
        List<Map.Entry<String, Integer>> leaders = new ArrayList<>();
        List<Map.Entry<String, Integer>> points = new ArrayList<>(teams.entrySet());
        points.sort((a, b) -> b.getValue() - a.getValue());
        int leaderPoints = points.get(0).getValue();
        for (Map.Entry<String, Integer> point : points) {
            if (point.getValue() == leaderPoints) {
                leaders.add(point);
            } else break;
        }
        return leaders;
    }

    /**
     * Print score for all teams in descending order.
     *
     * @return True if there are any team and false otherwise.
     */
    public boolean printAll() {
        if (teams.isEmpty())
            return false;
        List<Map.Entry<String, Integer>> points = new ArrayList<>(teams.entrySet());
        points.sort((a, b) -> b.getValue() - a.getValue());
        System.out.println("Final board:");
        for (Map.Entry<String, Integer> point : points) {
            System.out.println(point.getKey() + ": " + point.getValue());
        }
        return true;
    }

    /**
     * Adds new team in the map with score 0.
     *
     * @param name Name of the new team.
     */
    public void newTeam(String name) {
        teams.put(name, 0);
    }

    /**
     * Gets the prize.
     *
     * @return prize.
     */
    public int getMoney() {
        return money;
    }

    /**
     * Chooses new prize.
     */
    public void chooseMoney() {
        money = new Random().nextInt(9_000_001) + 1_000_000;
    }

    /**
     * @return copy of last score.
     */
    public Map<String, Integer> getTeams() {
        return new HashMap<>(teams);
    }

    /**
     * Flag if sheet is busy.
     */
    private boolean isBusy;
    /**
     * Flag if All winners talked.
     */
    private int money;
    /**
     * Map of teams and points.
     */
    private final Map<String, Integer> teams = new HashMap<>();
}

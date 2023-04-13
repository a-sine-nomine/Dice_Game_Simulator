package ru.hse.dices;

import java.util.Random;

/**
 * Synchronized class for rolling the dice.
 */
class Table {
    /**
     * Rolls 6 standard dices.
     *
     * @return Integer from 6 to 36.
     * @throws InterruptedException when Croupier interrupt the game.
     */
    public synchronized int roll() throws InterruptedException {
        while (!isFree) {
            wait();
        }
        isFree = false;

        int rolls = 0;
        for (int i = 0; i < 6; i++) {
            rolls += random.nextInt(6) + 1;
        }

        isFree = true;

        notifyAll();
        return rolls;
    }

    /**
     * If table is free for rolling.
     */
    private boolean isFree = true;
    /**
     * Random for rolling a dice.
     */
    private final Random random = new Random();
}

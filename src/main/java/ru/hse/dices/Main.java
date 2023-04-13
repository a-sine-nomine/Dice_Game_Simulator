package ru.hse.dices;

import java.util.Scanner;

/**
 * Main class of the dice game. Organizes a dialog using an instance of the Java.util.Scanner and starts the game.
 *
 * @author ALisa Khabibrakhmanova
 */
public class Main {
    /**
     * Plays the game and asks user if they want to start again.
     *
     * @param args params of command line.
     */
    public static void main(String[] args) {
        int commandsNumber = checkCommandsNumber(args);
        if (commandsNumber == 0) return;
        while (true) {
            Croupier croupier = new Croupier(commandsNumber);
            croupier.playGame();
            System.out.println("If you want to play again type Yes");
            String message = in.nextLine();
            if (!"Yes".equals(message)) {
                System.out.println("Bye!");
                return;
            }
        }
    }

    /**
     * Checks whether args is one positive integer for teams number.
     *
     * @param args params of command line.
     * @return Integer from 1 to 10 for teams number if game can begin, 0 otherwise.
     */
    static public int checkCommandsNumber(String[] args) {
        int commandsNumber = 0;
        if (args.length == 0) {
            System.out.println("Argument not received.");
        } else if (args.length > 1) {
            System.out.println("Too much arguments.");
        } else {
            try {
                commandsNumber = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("Argument isn't integer.");
                return 0;
            }
            if (commandsNumber < 1 || commandsNumber > 10) {
                System.out.println("Number of commands should be number from 1 to 10.");
                return 0;
            }
        }
        return commandsNumber;
    }

    /**
     * scanner for dialog.
     */
    static final private Scanner in = new Scanner(System.in);
}

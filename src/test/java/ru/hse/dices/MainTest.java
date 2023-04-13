package ru.hse.dices;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    @Test
    void checkCommandsNumberTest() {
        String[] arg = new String[]{"1"};
        int commandsNumber = Main.checkCommandsNumber(arg);
        Assertions.assertEquals(1, commandsNumber);
    }
    @Test
    void checkCommandsNumberZeroTest() {
        String[] arg = new String[]{};
        int commandsNumber = Main.checkCommandsNumber(arg);
        Assertions.assertEquals(0, commandsNumber);
    }
    @Test
    void checkCommandsNumberManyTest() {
        String[] arg = new String[]{"1", "3", "4"};
        int commandsNumber = Main.checkCommandsNumber(arg);
        Assertions.assertEquals(0, commandsNumber);
    }
    @Test
    void checkCommandsNumberStrangeTest() {
        String[] arg = new String[]{"#"};
        int commandsNumber = Main.checkCommandsNumber(arg);
        Assertions.assertEquals(0, commandsNumber);
    }
    @Test
    void checkCommandsNumberOutTest() {
        String[] arg = new String[]{"20"};
        int commandsNumber = Main.checkCommandsNumber(arg);
        Assertions.assertEquals(0, commandsNumber);
    }
}
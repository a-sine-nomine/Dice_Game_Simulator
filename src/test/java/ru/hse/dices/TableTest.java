package ru.hse.dices;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {
    @Test
    void rollTest() throws InterruptedException {
        Table table = new Table();
        int points = table.roll();
        Assertions.assertTrue(points >= 6 && points <= 36);
    }
}
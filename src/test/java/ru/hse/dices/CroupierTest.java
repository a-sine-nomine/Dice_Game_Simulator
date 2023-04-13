package ru.hse.dices;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CroupierTest {
    @Test
    void getSetEndedGameTest() {
        Croupier croupier = new Croupier(2);
        croupier.setEndedGame();
        Assertions.assertTrue(croupier.isEndedGame());
    }
    @Test
    void getIncAllSaidByeTest() {
        Croupier croupier = new Croupier(2);
        for(int i = 0; i < 100; i++)
            croupier.incAllSaidBye();
        Assertions.assertEquals(100, croupier.getAllSaidBye());
    }
    @Test
    void getIncNumTalkedTest() {
        Croupier croupier = new Croupier(2);
        for(int i = 0; i < 100; i++)
            croupier.incNumTalked();
        Assertions.assertEquals(100, croupier.getNumTalked());
    }
    @Test
    void getAllTalkedTest() {
        Croupier croupier = new Croupier(2);
        assertFalse(croupier.isAllTalked());
    }
    @Test
    void getSetAllTalkedTest() {
        Croupier croupier = new Croupier(2);
        croupier.setAllTalked();
        assertTrue(croupier.isAllTalked());
    }

}
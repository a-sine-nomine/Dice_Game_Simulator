package ru.hse.dices;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    @Test
    void sayWinTest() throws InterruptedException {
        Sheet sheet = new Sheet();
        Player player = new Player("name", "test1", new Table(), sheet, new Croupier(1));
        sheet.newTeam("test1");
        sheet.write("test1", 20);
        sheet.newTeam("test2");
        sheet.write("test2", 10);
        Assertions.assertTrue(player.sayWin());
    }

    @Test
    void sayAllWin() throws InterruptedException {
        Croupier croupier = new Croupier(1);
        Sheet sheet = new Sheet();
        Player player1 = new Player("name1", "test1", new Table(), sheet, croupier);
        Player player2 = new Player("name2", "test1", new Table(), sheet, croupier);
        Player player3 = new Player("name3", "test1", new Table(), sheet, croupier);
        sheet.newTeam("test1");
        sheet.write("test1", 20);
        sheet.newTeam("test2");
        sheet.write("test2", 10);
        player1.sayWin();
        player3.sayWin();
        player3.sayWin();
        Assertions.assertTrue(croupier.isAllTalked());
    }
}
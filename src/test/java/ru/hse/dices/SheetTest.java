package ru.hse.dices;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Map;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SheetTest {
    @Test
    void newTeamTest() {
        Sheet sheet = new Sheet();
        sheet.newTeam("Team1");
        sheet.newTeam("Team2");
        Assertions.assertEquals(2, sheet.getTeams().size());
    }
    @Test
    void writeTest() throws InterruptedException {
        Sheet sheet = new Sheet();
        Croupier croupier = new Croupier(3);
        croupier.makePlayers(sheet);
        sheet.write("team1", 5);
        sheet.write("team1", 2);
        sheet.write("team1", 200);
        Assertions.assertEquals(207, sheet.getTeams().get("team1"));
    }
    @Test
    void getLeadersTest() throws InterruptedException {
        Sheet sheet = new Sheet();
        sheet.newTeam("Test1");
        sheet.write("Test1", 5);
        sheet.newTeam("Test2");
        sheet.write("Test2", 15);
        List<Map.Entry<String, Integer>> leaders = sheet.getLeaders();
        Assertions.assertEquals(1, leaders.size());
    }

    @Test
    void moneyTest() {
        Sheet sheet = new Sheet();
        sheet.chooseMoney();
        int money = sheet.getMoney();
        Assertions.assertTrue(money >= 1_000_000 && money <= 10_000_000);
    }

    @Test
    void printAllTrueTest() {
        Sheet sheet = new Sheet();
        sheet.newTeam("team1");
        sheet.newTeam("team2");
        sheet.newTeam("team13");
        Assertions.assertTrue(sheet.printAll());
    }
    @Test
    void printAllFalseTest() {
        Sheet sheet = new Sheet();
        Assertions.assertFalse(sheet.printAll());
    }
    @Test
    void printLeadersTrueTest() {
        Sheet sheet = new Sheet();
        sheet.newTeam("team1");
        sheet.newTeam("team2");
        sheet.newTeam("team13");
        Assertions.assertTrue(sheet.printLeader());
    }
    @Test
    void printLeadersFalseTest() {
        Sheet sheet = new Sheet();
        Assertions.assertFalse(sheet.printLeader());
    }
}
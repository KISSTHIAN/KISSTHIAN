package com.villagegame;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameLogicTest {

    @Test
    public void testInitialState() {
        GameLogic game = new GameLogic();
        assertEquals(100, game.getGold());
        assertEquals(50, game.getFood());
        assertEquals(0, game.getPopulation());
    }

    @Test
    public void testPlaceHouse() {
        GameLogic game = new GameLogic();
        boolean success = game.placeBuilding("house", 5, 5);
        assertTrue(success);
        assertEquals(50, game.getGold());
        assertEquals(2, game.getPopulation());
        assertEquals(1, game.getBuildings().size());
        assertEquals(2, game.getVillagers().size());
    }

    @Test
    public void testPlaceBuildingInsufficientGold() {
        GameLogic game = new GameLogic();
        game.placeBuilding("mine", 1, 1); // 100 gold
        assertEquals(0, game.getGold());

        boolean success = game.placeBuilding("farm", 2, 2); // 30 gold
        assertFalse(success);
    }
}

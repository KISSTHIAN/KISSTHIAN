package com.villagegame;

import java.util.ArrayList;
import java.util.List;

public class GameLogic {
    private int gold = 100;
    private int food = 50;
    private int population = 0;
    private final List<Building> buildings = new ArrayList<>();
    private final List<Villager> villagers = new ArrayList<>();

    private final int gridSize = 40;
    private final int mapWidth = 20;
    private final int mapHeight = 15;

    private long lastResourceUpdate;
    private final long resourceTickRate = 2000; // 2 seconds

    public GameLogic() {
        this.lastResourceUpdate = System.currentTimeMillis();
    }

    public boolean placeBuilding(String type, int gridX, int gridY) {
        // Check bounds
        if (gridX < 0 || gridX >= mapWidth || gridY < 0 || gridY >= mapHeight) return false;

        // Check if building already exists at this location
        for (Building b : buildings) {
            if (b.getX() == gridX && b.getY() == gridY) return false;
        }

        int cost;
        Building building;

        switch (type.toLowerCase()) {
            case "house":
                cost = 50;
                if (gold < cost) return false;
                building = new House(gridX, gridY);
                population += 2;
                for (int i = 0; i < 2; i++) {
                    villagers.add(new Villager(gridX * gridSize + 20, gridY * gridSize + 20));
                }
                break;
            case "farm":
                cost = 30;
                if (gold < cost) return false;
                building = new Farm(gridX, gridY);
                break;
            case "mine":
                cost = 100;
                if (gold < cost) return false;
                building = new Mine(gridX, gridY);
                break;
            default:
                return false;
        }

        gold -= cost;
        buildings.add(building);
        return true;
    }

    public void update(double deltaTime) {
        long now = System.currentTimeMillis();
        if (now - lastResourceUpdate >= resourceTickRate) {
            for (Building b : buildings) {
                if ("farm".equals(b.getType())) {
                    food += 5;
                } else if ("mine".equals(b.getType())) {
                    gold += 10;
                }
            }

            // Houses consume food
            long houseCount = buildings.stream().filter(b -> "house".equals(b.getType())).count();
            food -= houseCount * 2;
            if (food < 0) food = 0;

            lastResourceUpdate = now;
        }

        for (Villager v : villagers) {
            v.update(deltaTime);
        }
    }

    public int getGold() { return gold; }
    public int getFood() { return food; }
    public int getPopulation() { return population; }
    public List<Building> getBuildings() { return buildings; }
    public List<Villager> getVillagers() { return villagers; }
    public int getGridSize() { return gridSize; }
    public int getMapWidth() { return mapWidth; }
    public int getMapHeight() { return mapHeight; }
}

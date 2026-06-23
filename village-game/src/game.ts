import { House, Farm, Mine, Villager } from './entities';
import type { Building, BuildingType } from './entities';

export class Game {
    gold: number = 100;
    food: number = 50;
    population: number = 0;
    buildings: Building[] = [];
    villagers: Villager[] = [];
    gridSize: number = 40;
    mapWidth: number = 20;
    mapHeight: number = 15;

    lastResourceUpdate: number = 0;
    resourceTickRate: number = 2000; // 2 seconds

    constructor() {
        this.lastResourceUpdate = Date.now();
    }

    placeBuilding(type: BuildingType, gridX: number, gridY: number): boolean {
        // Check if building already exists at this location
        const existing = this.buildings.find(b => b.x === gridX && b.y === gridY);
        if (existing) return false;

        let building: Building;
        let cost = 0;

        switch (type) {
            case 'house':
                cost = 50;
                if (this.gold < cost) return false;
                building = new House(gridX, gridY);
                this.population += 2;
                // Add villagers for the house
                for(let i=0; i<2; i++) {
                    this.villagers.push(new Villager(gridX * this.gridSize + 20, gridY * this.gridSize + 20));
                }
                break;
            case 'farm':
                cost = 30;
                if (this.gold < cost) return false;
                building = new Farm(gridX, gridY);
                break;
            case 'mine':
                cost = 100;
                if (this.gold < cost) return false;
                building = new Mine(gridX, gridY);
                break;
            default:
                return false;
        }

        this.gold -= cost;
        this.buildings.push(building);
        return true;
    }

    update(deltaTime: number) {
        const now = Date.now();
        const elapsedSinceResourceTick = now - this.lastResourceUpdate;

        // Resource generation
        if (elapsedSinceResourceTick >= this.resourceTickRate) {
            this.buildings.forEach(b => {
                if (b.type === 'farm') {
                    this.food += 5;
                } else if (b.type === 'mine') {
                    this.gold += 10;
                }
            });

            // Houses consume food
            const houseCount = this.buildings.filter(b => b.type === 'house').length;
            this.food -= houseCount * 2;
            if (this.food < 0) this.food = 0;

            this.lastResourceUpdate = now;
        }

        // Update villagers with delta time
        this.villagers.forEach(v => v.update(deltaTime));
    }

    getStats() {
        return {
            gold: this.gold,
            food: this.food,
            population: this.population
        };
    }
}

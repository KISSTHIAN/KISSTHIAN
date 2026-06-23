export type BuildingType = 'house' | 'farm' | 'mine';

export interface Building {
    id: string;
    type: BuildingType;
    x: number; // grid x
    y: number; // grid y
    width: number;
    height: number;
    color: string;
}

export class House implements Building {
    id: string;
    type: BuildingType = 'house';
    width = 1;
    height = 1;
    color = '#e74c3c';
    x: number;
    y: number;
    constructor(x: number, y: number) {
        this.x = x;
        this.y = y;
        this.id = `house-${Date.now()}-${Math.random()}`;
    }
}

export class Farm implements Building {
    id: string;
    type: BuildingType = 'farm';
    width = 1;
    height = 1;
    color = '#2ecc71';
    x: number;
    y: number;
    constructor(x: number, y: number) {
        this.x = x;
        this.y = y;
        this.id = `farm-${Date.now()}-${Math.random()}`;
    }
}

export class Mine implements Building {
    id: string;
    type: BuildingType = 'mine';
    width = 1;
    height = 1;
    color = '#95a5a6';
    x: number;
    y: number;
    constructor(x: number, y: number) {
        this.x = x;
        this.y = y;
        this.id = `mine-${Date.now()}-${Math.random()}`;
    }
}

export class Villager {
    id: string;
    x: number; // pixel x
    y: number; // pixel y
    targetX: number;
    targetY: number;
    speed: number = 60; // pixels per second
    color: string = '#f1c40f';

    constructor(x: number, y: number) {
        this.id = `villager-${Date.now()}-${Math.random()}`;
        this.x = x;
        this.y = y;
        this.targetX = x;
        this.targetY = y;
    }

    update(deltaTime: number) {
        const dx = this.targetX - this.x;
        const dy = this.targetY - this.y;
        const distance = Math.sqrt(dx * dx + dy * dy);

        const moveDistance = this.speed * deltaTime;

        if (distance > moveDistance) {
            this.x += (dx / distance) * moveDistance;
            this.y += (dy / distance) * moveDistance;
        } else {
            this.x = this.targetX;
            this.y = this.targetY;
            // Set new random target nearby
            this.targetX += (Math.random() - 0.5) * 100;
            this.targetY += (Math.random() - 0.5) * 100;
        }
    }
}

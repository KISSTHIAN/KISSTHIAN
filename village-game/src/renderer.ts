import { Game } from './game';

export class Renderer {
    ctx: CanvasRenderingContext2D;
    canvas: HTMLCanvasElement;

    constructor(canvas: HTMLCanvasElement) {
        this.canvas = canvas;
        this.ctx = canvas.getContext('2d')!;
        this.resize();
        window.addEventListener('resize', () => this.resize());
    }

    resize() {
        this.canvas.width = window.innerWidth;
        this.canvas.height = window.innerHeight;
    }

    render(game: Game) {
        this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height);

        // Draw Grass/Background
        this.ctx.fillStyle = '#27ae60';
        this.ctx.fillRect(0, 0, this.canvas.width, this.canvas.height);

        // Draw Grid
        this.ctx.strokeStyle = 'rgba(0,0,0,0.1)';
        this.ctx.beginPath();
        for (let x = 0; x < game.mapWidth; x++) {
            this.ctx.moveTo(x * game.gridSize, 0);
            this.ctx.lineTo(x * game.gridSize, game.mapHeight * game.gridSize);
        }
        for (let y = 0; y < game.mapHeight; y++) {
            this.ctx.moveTo(0, y * game.gridSize);
            this.ctx.lineTo(game.mapWidth * game.gridSize, y * game.gridSize);
        }
        this.ctx.stroke();

        // Draw Buildings
        game.buildings.forEach(building => {
            this.ctx.fillStyle = building.color;
            this.ctx.fillRect(
                building.x * game.gridSize + 2,
                building.y * game.gridSize + 2,
                building.width * game.gridSize - 4,
                building.height * game.gridSize - 4
            );

            // Building labels
            this.ctx.fillStyle = 'white';
            this.ctx.font = '10px Arial';
            this.ctx.fillText(
                building.type.toUpperCase(),
                building.x * game.gridSize + 5,
                building.y * game.gridSize + 15
            );
        });

        // Draw Villagers
        game.villagers.forEach(villager => {
            this.ctx.fillStyle = villager.color;
            this.ctx.beginPath();
            this.ctx.arc(villager.x, villager.y, 5, 0, Math.PI * 2);
            this.ctx.fill();
        });
    }
}

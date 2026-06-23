import './style.css';
import { Game } from './game';
import { Renderer } from './renderer';
import type { BuildingType } from './entities';

const canvas = document.getElementById('gameCanvas') as HTMLCanvasElement;
const goldEl = document.getElementById('gold')!;
const foodEl = document.getElementById('food')!;
const populationEl = document.getElementById('population')!;
const buttons = document.querySelectorAll('.build-btn');

const game = new Game();
const renderer = new Renderer(canvas);

let selectedType: BuildingType | null = null;

buttons.forEach(btn => {
    btn.addEventListener('click', () => {
        buttons.forEach(b => b.classList.remove('selected'));
        const type = (btn as HTMLButtonElement).dataset.type as BuildingType;
        if (selectedType === type) {
            selectedType = null;
        } else {
            selectedType = type;
            btn.classList.add('selected');
        }
    });
});

canvas.addEventListener('click', (e) => {
    if (!selectedType) return;

    const rect = canvas.getBoundingClientRect();
    const x = e.clientX - rect.left;
    const y = e.clientY - rect.top;

    const gridX = Math.floor(x / game.gridSize);
    const gridY = Math.floor(y / game.gridSize);

    if (gridX >= 0 && gridX < game.mapWidth && gridY >= 0 && gridY < game.mapHeight) {
        if (game.placeBuilding(selectedType, gridX, gridY)) {
            // Success!
        } else {
            alert('No tienes suficiente oro o ya hay algo ahí.');
        }
    }
});

function updateUI() {
    const stats = game.getStats();
    goldEl.textContent = stats.gold.toString();
    foodEl.textContent = stats.food.toString();
    populationEl.textContent = stats.population.toString();
}

let lastTime = 0;
function gameLoop(timestamp: number) {
    const deltaTime = (timestamp - lastTime) / 1000;
    lastTime = timestamp;

    if (deltaTime < 0.1) { // Avoid large jumps
        game.update(deltaTime);
    }

    renderer.render(game);
    updateUI();
    requestAnimationFrame(gameLoop);
}

requestAnimationFrame((time) => {
    lastTime = time;
    requestAnimationFrame(gameLoop);
});

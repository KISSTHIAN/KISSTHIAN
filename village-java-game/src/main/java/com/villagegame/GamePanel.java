package com.villagegame;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private final GameLogic game;

    public GamePanel(GameLogic game) {
        this.game = game;
        setBackground(new Color(39, 174, 96)); // Grass green
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int gridSize = game.getGridSize();

        // Draw Grid
        g2.setColor(new Color(0, 0, 0, 30));
        for (int x = 0; x <= game.getMapWidth(); x++) {
            g2.drawLine(x * gridSize, 0, x * gridSize, game.getMapHeight() * gridSize);
        }
        for (int y = 0; y <= game.getMapHeight(); y++) {
            g2.drawLine(0, y * gridSize, game.getMapWidth() * gridSize, y * gridSize);
        }

        // Draw Buildings
        for (Building b : game.getBuildings()) {
            g2.setColor(b.getColor());
            g2.fillRect(b.getX() * gridSize + 2, b.getY() * gridSize + 2, b.getWidth() * gridSize - 4, b.getHeight() * gridSize - 4);

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 10));
            g2.drawString(b.getType().toUpperCase(), b.getX() * gridSize + 5, b.getY() * gridSize + 15);
        }

        // Draw Villagers
        for (Villager v : game.getVillagers()) {
            g2.setColor(v.getColor());
            int vx = (int) v.getX();
            int vy = (int) v.getY();
            g2.fillOval(vx - 5, vy - 5, 10, 10);
        }
    }
}

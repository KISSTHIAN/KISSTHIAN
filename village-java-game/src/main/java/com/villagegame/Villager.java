package com.villagegame;

import java.awt.Color;
import java.util.UUID;

public class Villager {
    private final String id;
    private double x;
    private double y;
    private double targetX;
    private double targetY;
    private final double speed = 60.0; // pixels per second
    private final Color color = new Color(241, 196, 15);

    public Villager(double x, double y) {
        this.id = UUID.randomUUID().toString();
        this.x = x;
        this.y = y;
        this.targetX = x;
        this.targetY = y;
    }

    public void update(double deltaTime) {
        double dx = targetX - x;
        double dy = targetY - y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        double moveDistance = speed * deltaTime;

        if (distance > moveDistance) {
            x += (dx / distance) * moveDistance;
            y += (dy / distance) * moveDistance;
        } else {
            x = targetX;
            y = targetY;
            // Set new random target nearby
            targetX += (Math.random() - 0.5) * 100;
            targetY += (Math.random() - 0.5) * 100;
        }
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public Color getColor() { return color; }
}

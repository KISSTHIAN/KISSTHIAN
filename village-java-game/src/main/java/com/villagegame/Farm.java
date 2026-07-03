package com.villagegame;

import java.awt.Color;
import java.util.UUID;

public class Farm implements Building {
    private final String id;
    private final int x;
    private final int y;

    public Farm(int x, int y) {
        this.id = UUID.randomUUID().toString();
        this.x = x;
        this.y = y;
    }

    @Override
    public String getId() { return id; }

    @Override
    public String getType() { return "farm"; }

    @Override
    public int getX() { return x; }

    @Override
    public int getY() { return y; }

    @Override
    public int getWidth() { return 1; }

    @Override
    public int getHeight() { return 1; }

    @Override
    public Color getColor() { return new Color(46, 204, 113); }
}

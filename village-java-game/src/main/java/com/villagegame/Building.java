package com.villagegame;

import java.awt.Color;

public interface Building {
    String getId();
    String getType();
    int getX();
    int getY();
    int getWidth();
    int getHeight();
    Color getColor();
}

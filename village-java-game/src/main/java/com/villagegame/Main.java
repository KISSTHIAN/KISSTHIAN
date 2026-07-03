package com.villagegame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameLogic game = new GameLogic();
            GameWindow window = new GameWindow(game);

            Timer timer = new Timer(16, e -> {
                game.update(0.016); // Approx 60 FPS
                window.updateUI();
            });
            timer.start();
        });
    }
}

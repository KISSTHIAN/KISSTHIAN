package com.villagegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameWindow extends JFrame {
    private final GameLogic game;
    private final JLabel goldLabel;
    private final JLabel foodLabel;
    private final JLabel popLabel;
    private String selectedType = null;

    public GameWindow(GameLogic game) {
        this.game = game;
        setTitle("Aldea Game (Java)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // UI Panel
        JPanel uiPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        uiPanel.setBackground(new Color(44, 62, 80));

        goldLabel = createLabel("Oro: 100");
        foodLabel = createLabel("Comida: 50");
        popLabel = createLabel("Población: 0");

        uiPanel.add(goldLabel);
        uiPanel.add(foodLabel);
        uiPanel.add(popLabel);

        // Control Panel
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controlPanel.setBackground(new Color(44, 62, 80));

        controlPanel.add(createBuildButton("Casa (50 Oro)", "house"));
        controlPanel.add(createBuildButton("Granja (30 Oro)", "farm"));
        controlPanel.add(createBuildButton("Mina (100 Oro)", "mine"));

        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        topPanel.add(uiPanel);
        topPanel.add(controlPanel);
        add(topPanel, BorderLayout.NORTH);

        // Game Panel
        GamePanel gamePanel = new GamePanel(game);
        gamePanel.setPreferredSize(new Dimension(game.getMapWidth() * game.getGridSize(), game.getMapHeight() * game.getGridSize()));
        add(gamePanel, BorderLayout.CENTER);

        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (selectedType != null) {
                    int gridX = e.getX() / game.getGridSize();
                    int gridY = e.getY() / game.getGridSize();
                    if (game.placeBuilding(selectedType, gridX, gridY)) {
                        gamePanel.repaint();
                    } else {
                        JOptionPane.showMessageDialog(GameWindow.this, "No puedes construir aquí o no tienes suficiente oro.");
                    }
                }
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        return label;
    }

    private JButton createBuildButton(String text, String type) {
        JButton btn = new JButton(text);
        btn.addActionListener(e -> selectedType = type);
        return btn;
    }

    public void updateUI() {
        goldLabel.setText("Oro: " + game.getGold());
        foodLabel.setText("Comida: " + game.getFood());
        popLabel.setText("Población: " + game.getPopulation());
        repaint();
    }
}

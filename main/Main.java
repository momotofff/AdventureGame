package main;

import javax.swing.*;

public class Main
{
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(3);
        window.setResizable(false);
        window.setTitle("AdventureGame");

        GamePanel gamePanel = new GamePanel();
        UI ui = new UI(gamePanel);

        window.add(ui);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        ui.startGame();
//        gamePanel.setupGame();
//        gamePanel.startGameThread();
    }
}

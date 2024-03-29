package main;

import javax.swing.*;

public class Main
{
    public static void main(String[] args)
    {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(3);
        window.setResizable(false);
        window.setTitle("AdventureGame");

        UI ui = new UI(window);
        window.add(ui);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        ui.startGame();
    }
}

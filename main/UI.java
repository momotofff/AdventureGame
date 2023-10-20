package main;

import objects.BaseObject;
import objects.Key;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI
{
    GamePanel gamePanel;
    Font font;
    BufferedImage bufferedImage;

    public  UI(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
        font = new Font("Arial", Font.PLAIN, 30);
        bufferedImage = new Key().image;
    }

    public void draw(Graphics2D graphics2D)
    {
        graphics2D.setFont(font);
        graphics2D.setColor(Color.white);
        graphics2D.drawImage(bufferedImage, gamePanel.tileSize / 2, gamePanel.tileSize / 2, gamePanel.tileSize / 2, gamePanel.tileSize / 2, null);
        graphics2D.drawString("x " + gamePanel.player.hasKey, 60, 45);
    }
}

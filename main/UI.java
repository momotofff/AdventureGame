package main;

import objects.Key;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI
{
    GamePanel gamePanel;
    Font font;
    BufferedImage bufferedImage;

    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;

    public UI(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
        font = new Font("Arial", Font.PLAIN, 30);
        bufferedImage = new Key().image;
    }

    public void showMessage(String text)
    {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D graphics2D)
    {
        graphics2D.setFont(font);
        graphics2D.setColor(new Color(230,200,170));
        graphics2D.drawImage(bufferedImage, gamePanel.tileSize / 2, gamePanel.tileSize / 2, gamePanel.tileSize / 2, gamePanel.tileSize / 2, null);
        graphics2D.drawString("x " + gamePanel.player.hasKey, 60, 45);

        if (messageOn)
        {
            graphics2D.setFont(graphics2D.getFont().deriveFont(30F));
            graphics2D.drawString(message, gamePanel.tileSize / 2, gamePanel.tileSize * 2);

            if (++messageCounter > 120)
            {
                messageCounter = 0;
                messageOn = false;
            }
        }
    }
}

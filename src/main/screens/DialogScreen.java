package main.screens;

import main.*;
import main.screens.interfaces.IScreenShotter;
import main.screens.interfaces.IScreenSwitcher;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class DialogScreen extends AbstractScreen
{
    private AbstractDialogue dialogue;
    private final IScreenShotter screenShotter;
    private BufferedImage screenShot;

    public DialogScreen(IScreenSwitcher switcher, KeyHandler keyHandler, IScreenShotter screenShotter)
    {
        super(switcher, keyHandler, 4);
        this.screenShotter = screenShotter;
    }

    public void setDialogue(AbstractDialogue dialogue)
    {
        this.dialogue = dialogue;
    }

    @Override
    public void draw(Graphics2D graphics2D, Font font)
    {
        if (screenShot != null)
            graphics2D.drawImage(screenShot, null, 0, 0);

        startAnimation(graphics2D);

        if (isFinishAnimation)
        {
            Point window = new Point(startSizeWindow.x, startSizeWindow.y);
            graphics2D.setFont(font.deriveFont(Font.PLAIN, 40));

            if (dialogue == null)
                return;

            for (String line: dialogue.getText().split("%"))
            {
                graphics2D.drawString(line, window.x + Parameters.tileSize, window.y + Parameters.tileSize);
                window.y += Parameters.tileSize;
            }
        }

    }

    @Override
    public void activate()
    {
        keyHandler.addListener(KeyEvent.VK_SPACE, this::onKeySpace);
        screenShot = screenShotter.getScreenShot();
    }

    private void onKeySpace()
    {
        if (dialogue == null)
            return;

        dialogue.onKeyPressed();

        if (dialogue.isFinished())
            switcher.switchScreen(GameState.Running);
    }

    @Override
    public void deactivate()
    {
        keyHandler.removeListeners();
        dialogue = null;
        screenShot = null;
        scaleCountWindow = (Point) startSizeWindow.clone();
        isFinishAnimation = false;
    }
}

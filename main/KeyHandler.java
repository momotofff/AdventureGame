package main;

import entity.Direction;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Optional;

public class KeyHandler implements KeyListener
{
    GamePanel gamePanel;
    public boolean upPressed;
    public boolean downPressed;
    public boolean leftPressed;
    public boolean rightPressed;

    public KeyHandler(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e)
    {
        if (gamePanel.state == GameState.Dialog)
            if ((gamePanel.NPC.get(0)).utteranceCounter > 0)
                if (KeyEvent.VK_SPACE == e.getKeyCode())
                {
                    gamePanel.NPC.get(0).speak(gamePanel.NPC.get(0).utteranceCounter);
                    (gamePanel.NPC.get(0)).utteranceCounter--;
                }


        switch (e.getKeyCode())
        {
            case KeyEvent.VK_W : upPressed = true; break;
            case KeyEvent.VK_A : leftPressed = true; break;
            case KeyEvent.VK_S : downPressed = true; break;
            case KeyEvent.VK_D : rightPressed = true; break;
            case KeyEvent.VK_SPACE :
                switch (gamePanel.state)
                {
                    case Inventory : gamePanel.state = GameState.Running; break;
                    case Dialog : gamePanel.state = GameState.Running; break;
                    case Paused : gamePanel.state = GameState.Running; break;
                    case Running : gamePanel.state = GameState.Paused; break;
                }

                break;

            case KeyEvent.VK_E :
                switch (gamePanel.state)
                {
                    case Running : gamePanel.state = GameState.Inventory; break;
                    case Inventory : gamePanel.state = GameState.Running; break;
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
            case KeyEvent.VK_W : upPressed = false; break;
            case KeyEvent.VK_A : leftPressed = false; break;
            case KeyEvent.VK_S : downPressed = false; break;
            case KeyEvent.VK_D : rightPressed = false; break;
        }
    }

    public Optional<Direction> getPressedDirection()
    {
        if (upPressed)
            return Optional.of(Direction.Up);

        if (leftPressed)
            return Optional.of(Direction.Left);

        if (rightPressed)
            return Optional.of(Direction.Right);

        if (downPressed)
            return Optional.of(Direction.Down);

        return Optional.empty();
    }
}

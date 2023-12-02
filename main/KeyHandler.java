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
    private boolean paused = false;
    private boolean inventory = false;

    public KeyHandler(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e)
    {

        switch (e.getKeyCode())
        {
            case KeyEvent.VK_W : upPressed = true; break;
            case KeyEvent.VK_A : leftPressed = true; break;
            case KeyEvent.VK_S : downPressed = true; break;
            case KeyEvent.VK_D : rightPressed = true; break;
            case KeyEvent.VK_SPACE : if(paused) {
                paused = false;
                gamePanel.state = GameState.Running;
            }
            else
            {
                gamePanel.state = GameState.Paused;
                paused = true;
            }
            break;
            case KeyEvent.VK_E : if(inventory) {
                inventory = false;
                gamePanel.state = GameState.Running;
            }
            else
            {
                gamePanel.state = GameState.Inventory;
                inventory = true;
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

package main;

import entity.Direction;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Optional;

public class KeyHandler implements KeyListener
{
    private final HashMap<Integer, Runnable> listeners = new HashMap<>();

    private boolean upPressed;
    private boolean downPressed;
    private boolean leftPressed;
    private boolean rightPressed;

    public void addListener(Integer key, Runnable listener)
    {
        listeners.put(key, listener);
    }

    public void removeListener(Integer key)
    {
        listeners.remove(key);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e)
    {
        Runnable listener = listeners.get(e.getKeyCode());
        if (listener != null)
            listener.run();
/*
        else if (GamePanel.getState() == GameState.Paused)
        {
            switch (e.getKeyCode())
            {
                case KeyEvent.VK_W:
                    if (UI.commandPause == UI.CommandsPause.Continue)
                        UI.commandPause = UI.CommandsPause.Exit;
                    else if (UI.commandPause == UI.CommandsPause.Exit)
                        UI.commandPause = UI.CommandsPause.Save;
                    else if (UI.commandPause == UI.CommandsPause.Save)
                        UI.commandPause = UI.CommandsPause.Load;
                    else if (UI.commandPause == UI.CommandsPause.Load)
                        UI.commandPause = UI.CommandsPause.Continue;
                    break;

                case KeyEvent.VK_S:
                    if (UI.commandPause == UI.CommandsPause.Continue)
                        UI.commandPause = UI.CommandsPause.Load;
                    else if (UI.commandPause == UI.CommandsPause.Load)
                        UI.commandPause = UI.CommandsPause.Save;
                    else if (UI.commandPause == UI.CommandsPause.Save)
                        UI.commandPause = UI.CommandsPause.Exit;
                    else if (UI.commandPause == UI.CommandsPause.Exit)
                        UI.commandPause = UI.CommandsPause.Continue;
                    break;

                case KeyEvent.VK_SPACE:
                    if (UI.commandPause == UI.CommandsPause.Continue)
                        GamePanel.state = GameState.Running;
                    else if (UI.commandPause == UI.CommandsPause.Load)
                        System.exit(1);
                    else if (UI.commandPause == UI.CommandsPause.Save)
                        System.exit(1);
                    else if (UI.commandPause == UI.CommandsPause.Exit)
                    {
                        GamePanel.state = GameState.StartScreen;
                        GamePanel.sound.stop();
                    }

                    break;
            }
        }

        else if (GamePanel.getState() == GameState.Inventory)
        {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_E:
                case KeyEvent.VK_SPACE:
                    GamePanel.state = GameState.Running;
                    break;
            }
        }

        else if (GamePanel.getState() == GameState.Dialog)
        {
            if (e.getKeyCode() == KeyEvent.VK_SPACE)
            {
                GamePanel.currentDialogue.onKeyPressed();

                if (GamePanel.currentDialogue.isFinished())
                    GamePanel.state = GameState.Running;
            }
        }
*/
        switch (e.getKeyCode())
        {
            case KeyEvent.VK_W : upPressed = true; break;
            case KeyEvent.VK_A : leftPressed = true; break;
            case KeyEvent.VK_S : downPressed = true; break;
            case KeyEvent.VK_D : rightPressed = true; break;
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

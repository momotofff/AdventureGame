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

    public void removeListeners()
    {
        listeners.clear();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e)
    {
        Runnable listener = listeners.get(e.getKeyCode());
        if (listener != null)
            listener.run();

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

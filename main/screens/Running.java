package main.screens;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Running extends AbstractScreen
{
    private enum RunningState
    {
        Running,
        Inventory,
        Dialogue,
        Pause
    }

    private RunningState runningState = RunningState.Running;
    private final KeyHandler keyHandler;

    public Running(IScreenSwitcher switcher, KeyHandler keyHandler)
    {
        super(switcher);
        this.keyHandler = keyHandler;
    }

    @Override
    public void draw(Graphics2D graphics2D, Font font)
    {

    }

    @Override
    public void activate()
    {
        runningState = RunningState.Running;

        keyHandler.addListener(KeyEvent.VK_SPACE, () -> {
            switch (runningState)
            {
                case Running : runningState = RunningState.Pause; break;
                case Inventory : runningState = RunningState.Running; break;
                case Pause : runningState = RunningState.Running; break;
                case Dialogue : GamePanel.currentDialogue.onKeyPressed();
                    if (GamePanel.currentDialogue.isFinished())
                        runningState = RunningState.Running;
            }
        });

        keyHandler.addListener(KeyEvent.VK_E, () -> {
            switch (runningState)
            {
                case Running -> runningState = RunningState.Inventory;
                case Inventory -> runningState = RunningState.Running;
            }
        });
    }

    @Override
    public void deactivate() {

    }
}

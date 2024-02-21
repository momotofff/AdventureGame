package main.utils;

import main.Parameters;

import java.awt.*;

public class AbstractMessage
{
    public int startTime = 0;
    public int timeOut = 200;
    public String message = null;
    public Point startPosition = new Point(Parameters.tileSize / 2, Parameters.tileSize * 2);
    public final int maxStep = 50;
    public int startStep = 0;

    public AbstractMessage(String message)
    {
        this.message = message;
    }
}

package main;

import java.awt.*;

public abstract class Parameters
{
    static final int originalTileSize = 32;
    static final int scale = 2;

    public static final int tileSize = originalTileSize * scale;
    static final Point maxBlocksScreen = new Point(25, 14);

    public static final Point screenSize = new Point(maxBlocksScreen.x * tileSize, maxBlocksScreen.y * tileSize);
}


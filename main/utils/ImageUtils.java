package main.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.nio.Buffer;

public class ImageUtils
{
    public static BufferedImage blur(BufferedImage source)
    {
        Kernel kernel = new Kernel(3, 3, new float[] {
            1f / (3 * 3), 1f / (3 * 3), 1f / (3 * 3),
            1f / (3 * 3), 1f / (3 * 3), 1f / (3 * 3),
            1f / (3 * 3), 1f / (3 * 3), 1f / (3 * 3)
        });

        ConvolveOp convolveOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);

        return convolveOp.filter(source, null);
    }

    public static BufferedImage darker(BufferedImage source)
    {
        Kernel kernel = new Kernel(1, 1, new float[] {0.75f});

        ConvolveOp convolveOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);

        return convolveOp.filter(source, null);
    }
}

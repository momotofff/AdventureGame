package entity;

import main.GamePanel;
import main.KeyHandler;
import main.Sounds;
import objects.BaseObject;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Magician extends Entity
{
    GamePanel gamePanel;
    KeyHandler keyHandler;
    int speedAnimation = 10;
    int timeOutAnimations = 0;
    ArrayList<Integer> directionsNPC = new ArrayList<>();

    public Magician(GamePanel gamePanel, Point defaultWorldPosition)
    {
        super(gamePanel, defaultWorldPosition);
        keyHandler = new KeyHandler(gamePanel);

        this.gamePanel = gamePanel;

        collisionArea = new Rectangle(defaultWorldPosition.x + 24, defaultWorldPosition.y + 36, 12, 12);
        worldPosition = defaultWorldPosition;
        speed = 1;
        getImage();
        direction = Direction.Right;

        directionsNPC.add(KeyEvent.VK_W);
        directionsNPC.add(KeyEvent.VK_A);
        directionsNPC.add(KeyEvent.VK_S);
        directionsNPC.add(KeyEvent.VK_D);
    }

    public void update()
    {
        if (--timeOutAnimations < 0)
        {
            switch (directionsNPC.get((int) (Math.random() * directionsNPC.size())))
            {
                case KeyEvent.VK_W:
                    keyHandler.upPressed = true;
                    keyHandler.leftPressed = false;
                    keyHandler.downPressed = false;
                    keyHandler.rightPressed = false;
                    break;
                case KeyEvent.VK_A:
                    keyHandler.upPressed = false;
                    keyHandler.leftPressed = true;
                    keyHandler.downPressed = false;
                    keyHandler.rightPressed = false;
                    break;
                case KeyEvent.VK_S:
                    keyHandler.upPressed = false;
                    keyHandler.leftPressed = false;
                    keyHandler.downPressed = true;
                    keyHandler.rightPressed = false;
                    break;
                case KeyEvent.VK_D:
                    keyHandler.upPressed = false;
                    keyHandler.leftPressed = false;
                    keyHandler.downPressed = false;
                    keyHandler.rightPressed = true;
                    break;
            }
            timeOutAnimations = 300;
        }


        screenCoordinates = new Point(
                worldPosition.x - gamePanel.player.worldPosition.x + gamePanel.player.screenCoordinates.x,
                worldPosition.y - gamePanel.player.worldPosition.y + gamePanel.player.screenCoordinates.y
        );

        keyHandler.getPressedDirection().ifPresent(value ->
        {
            this.direction = value;


            if (!gamePanel.collisionChecker.checkTile(this))
            {
                switch (direction)
                {
                    case Up:
                        worldPosition.y -= speed;
                        collisionArea.y -= speed;
                        break;

                    case Left:
                        worldPosition.x -= speed;
                        collisionArea.x -= speed;
                        break;

                    case Right:
                        worldPosition.x += speed;
                        collisionArea.x += speed;
                        break;

                    case Down:
                        worldPosition.y += speed;
                        collisionArea.y += speed;
                        break;

                    default:

                        break;
                }

                if (++spriteCounter > speedAnimation)
                {
                    ++spriteNumber;
                    spriteCounter = 0;
                    //gamePanel.sound.play(Sounds.Step);
                }
            }
        });

        if (keyHandler.getPressedDirection().isEmpty()) {
            spriteNumber = 0;
        }


    }

    @Override
    public void getImage()
    {
        LoadAnimation(Direction.Up, "/assets/Magician/up1.png");
        LoadAnimation(Direction.Up, "/assets/Magician/up2.png");
        LoadAnimation(Direction.Up, "/assets/Magician/up3.png");
        LoadAnimation(Direction.Up, "/assets/Magician/up4.png");

        LoadAnimation(Direction.Left, "/assets/Magician/left1.png");
        LoadAnimation(Direction.Left, "/assets/Magician/left2.png");
        LoadAnimation(Direction.Left, "/assets/Magician/left3.png");
        LoadAnimation(Direction.Left, "/assets/Magician/left4.png");

        LoadAnimation(Direction.Down, "/assets/Magician/down1.png");
        LoadAnimation(Direction.Down, "/assets/Magician/down2.png");
        LoadAnimation(Direction.Down, "/assets/Magician/down3.png");
        LoadAnimation(Direction.Down, "/assets/Magician/down4.png");

        LoadAnimation(Direction.Right, "/assets/Magician/right1.png");
        LoadAnimation(Direction.Right, "/assets/Magician/right2.png");
        LoadAnimation(Direction.Right, "/assets/Magician/right3.png");
        LoadAnimation(Direction.Right, "/assets/Magician/right4.png");
    }

    public void drawing(Graphics2D graphics2D)
    {
        BufferedImage image = GetAnimationFrame(direction, spriteNumber);
        graphics2D.drawImage(image, screenCoordinates.x, screenCoordinates.y, gamePanel.tileSize, gamePanel.tileSize, null);
    }


}

package interceptor;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * This class is responsible of drawing an explosion animation at the Turret tip
 * or at the position of collision between a projectile and a Bomb.
 */
public class Explosion {

    private static int i = 0;
    private static int j = 0;
    private static int m = 0;
    private static int k = 0;
    private static double turretX = 0;
    private static double turretY = 0;

    private static Image[] explosion = new Image[12];

    /**
     * This function loads Image resources from the disk drive for later use.
     *
     * @throws IOException
     */
    public static void initExplosion() throws IOException {
        explosion[0] = ImageIO.read(new File("Data\\Graphics\\Images Explosion\\0.png"));
        explosion[1] = ImageIO.read(new File("Data\\Graphics\\Images Explosion\\1.png"));
        explosion[2] = ImageIO.read(new File("Data\\Graphics\\Images Explosion\\2.png"));
        explosion[3] = ImageIO.read(new File("Data\\Graphics\\Images Explosion\\3.png"));
        explosion[4] = ImageIO.read(new File("Data\\Graphics\\Images Explosion\\4.png"));
        explosion[5] = ImageIO.read(new File("Data\\Graphics\\Images Explosion\\5.png"));
        explosion[6] = ImageIO.read(new File("Data\\Graphics\\Images Explosion\\6.png"));
        explosion[7] = ImageIO.read(new File("Data\\Graphics\\Images Explosion\\7.png"));
        explosion[8] = ImageIO.read(new File("Data\\Graphics\\Images Explosion\\8.png"));
        explosion[9] = ImageIO.read(new File("Data\\Graphics\\Images Explosion\\9.png"));
        explosion[10] = ImageIO.read(new File("Data\\Graphics\\Images Explosion\\10.png"));
        explosion[11] = ImageIO.read(new File("Data\\Graphics\\Images Explosion\\11.png"));
    }

    /**
     * This function is responsible of the drawing operation at the turret tip
     * using the pre-loaded images.
     *
     * @param g Graphics component used for drawing.
     */
    public static void drawAtTurret(Graphics g) {
        if (GamePnl.isExplosion) {
            g.drawImage(explosion[j], (int) turretX, (int) turretY, null);
            i++;
            if (i == 10) {
                i = 0;
                j++;
                if (j == 12) {
                    j = 0;
                    GamePnl.isExplosion = false;
                }
            }
        }
    }

    /**
     * This function is responsible of the drawing operation using the
     * pre-loaded images.
     *
     * @param g Graphics component used for drawing.
     * @param x the X coordinate where the animation is drawn.
     * @param y the Y coordinate where the animation is drawn.
     */
    public static void drawAtPoint(Graphics g, int x, int y) {

        if (Bomb.isCollided()) {
            g.drawImage(explosion[k], x, y, null);
            m++;
            if (m == 10) {
                m = 0;
                k++;
                if (k == 12) {
                    k = 0;
                    Bomb.setIsCollided(false);
                }
            }
        }
    }

    /**
     * @param aTurretX the turretX to set
     */
    public static void setTurretX(double aTurretX) {
        turretX = aTurretX;
    }

    /**
     * @param aTurretY the turretY to set
     */
    public static void setTurretY(double aTurretY) {
        turretY = aTurretY;
    }
}

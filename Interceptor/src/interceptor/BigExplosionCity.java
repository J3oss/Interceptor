package interceptor;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * This class is responsible of drawing a nuclear explosion animation.
 */
public class BigExplosionCity {

    private static int bigExplosionI = 0;
    private static int bigExplosionJ = 0;

    private static Image[] bigExplosionImg = new Image[18];

    /**
     * This function loads Image resources from the disk drive for later use.
     *
     * @throws IOException
     */
    public static void initFire() throws IOException {
        bigExplosionImg[0] = ImageIO.read(new File("Data\\Graphics\\Image BigExplosion\\0.png"));
        bigExplosionImg[1] = ImageIO.read(new File("Data\\Graphics\\Image BigExplosion\\1.png"));
        bigExplosionImg[2] = ImageIO.read(new File("Data\\Graphics\\Image BigExplosion\\2.png"));
        bigExplosionImg[3] = ImageIO.read(new File("Data\\Graphics\\Image BigExplosion\\3.png"));
        bigExplosionImg[4] = ImageIO.read(new File("Data\\Graphics\\Image BigExplosion\\4.png"));
        bigExplosionImg[5] = ImageIO.read(new File("Data\\Graphics\\Image BigExplosion\\5.png"));
        bigExplosionImg[6] = ImageIO.read(new File("Data\\Graphics\\Image BigExplosion\\6.png"));
        bigExplosionImg[7] = ImageIO.read(new File("Data\\Graphics\\Image BigExplosion\\7.png"));
        bigExplosionImg[8] = ImageIO.read(new File("Data\\Graphics\\Image BigExplosion\\8.png"));
        bigExplosionImg[9] = ImageIO.read(new File("Data\\Graphics\\Image BigExplosion\\9.png"));
        bigExplosionImg[10] = ImageIO.read(new File("Data\\Graphics\\Image BigExplosion\\10.png"));
        bigExplosionImg[11] = ImageIO.read(new File("Data\\Graphics\\Image BigExplosion\\11.png"));
        bigExplosionImg[12] = ImageIO.read(new File("Data\\Graphics\\Image BigExplosion\\12.png"));
        bigExplosionImg[13] = ImageIO.read(new File("Data\\Graphics\\Image BigExplosion\\13.png"));
        bigExplosionImg[14] = ImageIO.read(new File("Data\\Graphics\\Image BigExplosion\\14.png"));
        bigExplosionImg[15] = ImageIO.read(new File("Data\\Graphics\\Image BigExplosion\\15.png"));
        bigExplosionImg[16] = ImageIO.read(new File("Data\\Graphics\\Image BigExplosion\\16.png"));
        bigExplosionImg[17] = ImageIO.read(new File("Data\\Graphics\\Image BigExplosion\\17.png"));
    }

    /**
     * This function is responsible of the drawing operation using the
     * pre-loaded images.
     *
     * @param g Graphics component used for drawing.
     * @param x the X coordinate where the animation is drawn, the Y coordinate
     * is 800 by default.
     */
    public static void redraw(Graphics g, int x) {
        if (Bomb.isBigExplosion()) {
            g.drawImage(bigExplosionImg[bigExplosionJ], x - 100, 800, null);
            bigExplosionI++;
            if (bigExplosionI == 10) {
                bigExplosionI = 0;
                bigExplosionJ++;
                if (bigExplosionJ == 18) {
                    bigExplosionJ = 0;
                    Bomb.setBigExplosion(false);
                }
            }
        }
    }
}

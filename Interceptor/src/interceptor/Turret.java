package interceptor;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Turret class responsible for drawing of the turret.
 */
public class Turret {

    private static ImageIcon turretIcon;
    private static JLabel turretLbl;
    private static double GunPointTranslationAngle;
    private static Image gunPointImg;
    static int turretDelay;
    static int turretDelayLevel = 320;

    /**
     * Loads turret Image from the disc.
     * @return a JLabel containing the turret image.
     * @throws IOException turret image may not be found.
     */
    public static JLabel turretImage() throws IOException {
        gunPointImg = ImageIO.read(new File("Data\\Graphics\\gun point.png"));
        turretIcon = new ImageIcon("hull.png");
        turretLbl = new JLabel(turretIcon);
        turretLbl.setBounds(0, 900, 166, 100);
        return turretLbl;
    }

    /**
     * Draws the turret as the right angle.
     *
     * @param g is Graphics content.
     */
    public static void drawGunPoint(Graphics g) {
        BufferedImage newImg = (BufferedImage) gunPointImg;
        AffineTransform picAttributes = new AffineTransform();
        picAttributes.translate(0, 940);
        picAttributes.rotate(GunPointTranslationAngle);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(newImg, picAttributes, null);
    }

    /**
     *
     * @param GunPointTranslationAngle
     */
    public static void setGunPointTranslationAngle(double GunPointTranslationAngle) {
        Turret.GunPointTranslationAngle = GunPointTranslationAngle;
    }

    /**
     *
     * @return
     */
    public static double getGunPointTranslationAngle() {
        return GunPointTranslationAngle;
    }
}

package interceptor;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * This Class is responsible for the drawing and movement of projectiles.
 */
public class Projectile {

    static ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
    static ArrayList<Projectile> projectilesRemove = new ArrayList<Projectile>();

    private double angle;
    private final int speed = 150;
    private final double accelration = -9.8;
    private double currentTime = 0;
    private static Image projectileImg;

    private int xInitial;
    private int yInitial;

    private double initialVx;
    private double initialVy;
    private double currentVx;
    private double currentVy;
    private double velocityAngle;

    private int xDraw;
    private int yDraw;
    private double topRx;
    private double topRy;
    private double BottomRx;
    private double BottomRy;

    /**
     * Projectile constructor.
     */
    public Projectile(double xFinal, double yFinal, int xPoint, int yPoint, Graphics g) {
        // calculations 
        xInitial = xPoint;
        yInitial = yPoint;
        double yResultant = yInitial - yFinal;
        double xResultant = xFinal - xInitial;
        angle = Math.atan(yResultant / xResultant);
        initialVx = Math.cos(angle) * speed;
        initialVy = -Math.sin(angle) * speed;
        draw(g);
        projectiles.add(this);
    }

    /**
     * Calculates movement of projectiles using laws of motion.
     */
    public static void move() {
        // projectile equation 
        for (Projectile projectileToRemove : projectilesRemove) {
            projectiles.remove(projectileToRemove);
        }
        for (Projectile projectile : projectiles) {
            projectile.currentTime += 0.1;
            projectile.currentVy = projectile.initialVy + (9.8 * projectile.currentTime);
            projectile.xDraw = (int) (projectile.xInitial + projectile.currentTime * projectile.initialVx);

            projectile.yDraw = (int) (projectile.yInitial + 0.5 * projectile.currentTime * (projectile.currentVy + projectile.initialVy));

            if (projectile.yDraw >= 800 && projectile.currentTime > 10) {
                projectilesRemove.add(projectile);
            }

            projectile.velocityAngle = Math.atan(projectile.currentVy / projectile.initialVx);//+(Math.PI/2);
            projectile.topRx = projectile.xDraw + 90 * Math.cos(projectile.velocityAngle);
            projectile.topRy = projectile.yDraw + 90 * Math.sin(projectile.velocityAngle);
            projectile.BottomRx = projectile.xDraw + 90 * Math.cos(projectile.velocityAngle) - 40 * Math.sin(projectile.velocityAngle);
            projectile.BottomRy = projectile.yDraw + 90 * Math.sin(projectile.velocityAngle) + 40 * Math.cos(projectile.velocityAngle);

        }
        for (Projectile projectileToRemove : projectilesRemove) {
            projectiles.remove(projectileToRemove);
        }
    }

    /**
     * Draws projectile at its location and transforms the image by rotating it
     * to the projectile's current angle.
     *
     * @param g Graphics object
     */
    public void draw(Graphics g) {
        BufferedImage newImg = (BufferedImage) projectileImg;
        AffineTransform picAttributes = new AffineTransform();
        picAttributes.translate(xDraw, yDraw);
        picAttributes.rotate(velocityAngle);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(projectileImg, picAttributes, null);
    }

    /**
     * Redraws all projectiles in projectile ArrayList.
     *
     * @param g Graphics object
     */
    public static void redraw(Graphics g) {
        for (Projectile projectile : projectiles) {
            projectile.draw(g);
        }
    }

    /**
     * @param aProjectileImg the projectileImg to set
     */
    public static void setProjectileImg(Image aProjectileImg) {
        projectileImg = aProjectileImg;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(double currentTime) {
        this.currentTime = currentTime;
    }

    public double getInitialVx() {
        return initialVx;
    }

    public void setInitialVx(double initialVx) {
        this.initialVx = initialVx;
    }

    public double getInitialVy() {
        return initialVy;
    }

    public void setInitialVy(double initialVy) {
        this.initialVy = initialVy;
    }

    public double getCurrentVx() {
        return currentVx;
    }

    public void setCurrentVx(double currentVx) {
        this.currentVx = currentVx;
    }

    public double getCurrentVy() {
        return currentVy;
    }

    public void setCurrentVy(double currentVy) {
        this.currentVy = currentVy;
    }

    public int getxDraw() {
        return xDraw;
    }

    public void setxDraw(int xDraw) {
        this.xDraw = xDraw;
    }

    public int getyDraw() {
        return yDraw;
    }

    public void setyDraw(int yDraw) {
        this.yDraw = yDraw;
    }

    public double getTopRx() {
        return topRx;
    }

    public double getTopRy() {
        return topRy;
    }

    public double getBottomRx() {
        return BottomRx;
    }

    public double getBottomRy() {
        return BottomRy;
    }

    public static void reset() {
        projectiles.clear();
        projectilesRemove.clear();
    }
}

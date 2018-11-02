package interceptor;

import java.awt.*;
import java.util.*;

/**
 * This Class is responsible for the drawing, movement and collision detection
 * between all the coins and the city or a projectile.
 *
 */
public class Coins {

    private int currentX;
    private int currentY;
    private static final int SIDELENGTHX = 50;
    private static final int SIDELENGTHY = 70;
    private static Image coinsImg;
    private static int coinsDelay = 4500;
    private static ArrayList<Coins> coinsArr = new ArrayList<Coins>();
    static ArrayList<Coins> coinsRemoveArr = new ArrayList<Coins>();

    /**
     * Redraws all coins in the ArrayList which holds coins that should be
     * drawn.
     *
     * @param g GUI Graphics component to draw on.
     */
    public static void reDraw(Graphics g) {
        for (Coins coins : coinsArr) {
            if (coins.currentY != 0) {
                g.setColor(Color.yellow);
                g.drawImage(coinsImg, coins.currentX, coins.currentY, null);
            }
        }
    }

    /**
     * This function is responsible for updating the drawing coordinates,
     * collision detection and removal of collided coins from the ArrayList.
     *
     * @param pixelsToMove moves the coins down by "pixelsToMove" pixels.
     */
    public static void move(int pixelsToMove) {
        for (Projectile p : Projectile.projectiles) {

            for (Coins coins : coinsArr) {
                double topRightX = p.getTopRx();
                double topRighty = p.getTopRy();
                double bottomRightX = p.getBottomRx();
                double bottomRighty = p.getBottomRy();

                if (topRightX >= coins.currentX && topRightX <= coins.currentX + SIDELENGTHX
                        && topRighty >= coins.currentY && topRighty <= coins.currentY + SIDELENGTHY) {

                    coinsRemoveArr.add(coins);
                    CoinsDestroyed();

                } else if (bottomRightX >= coins.currentX && bottomRightX <= coins.currentX + SIDELENGTHX
                        && bottomRighty >= coins.currentY && bottomRighty <= coins.currentY + SIDELENGTHY) {

                    coinsRemoveArr.add(coins);
                    CoinsDestroyed();
                }

            }

        }

        for (Coins coins : coinsArr) {
            coins.currentY += pixelsToMove;
            if (coins.currentY >= 800) {
                coinsRemoveArr.add(coins);
            }
        }
        for (Coins coinsToRemove : coinsRemoveArr) {
            coinsArr.remove(coinsToRemove);
        }

    }

    /**
     * Called when a collision is detected by move method; Updates the coins in
     * StatusPanel.
     */
    public static void CoinsDestroyed() {
        Audio.collectCoinsound();
        StatusPanel.coins += 10;
        StatusPanel.CoinsLbl.setText("Coins " + StatusPanel.coins + "   ");
    }

    /**
     * Constructs a new Coin at a random X position within the specified limits
     * and at Y=0;Then draws it using the passed Graphics Component;Finally, it
     * adds the created Coin to the ArrayList.
     *
     * @param g GUI component to Draw on.
     * @param XMin Minimum X pixel to draw on.
     * @param XMax Maximum X pixel to draw on.
     */
    public Coins(Graphics g, int XMin, int XMax) {
        Random rand = new Random();
        currentX = Math.abs(rand.nextInt(XMax - XMin) + XMin - SIDELENGTHX);
        currentY = 0;
        g.drawImage(coinsImg, currentX, 0, null);
        coinsArr.add(this);
    }

    /**
     * Resets all static parameters and clears all ArrayLists.
     */
    public static void reset() {
        coinsArr.clear();
        coinsRemoveArr.clear();
        setCoinsDelay(4500);
        GamePnl.coinsTimer.setDelay(getCoinsDelay());
    }

    /**
     * @param aCoinsImg the coinsImg to set
     */
    public static void setCoinsImg(Image aCoinsImg) {
        coinsImg = aCoinsImg;
    }

    /**
     * @param aCoinsDelay the coinsDelay to set
     */
    public static void setCoinsDelay(int aCoinsDelay) {
        coinsDelay = aCoinsDelay;
    }

    /**
     * @return the coinsDelay
     */
    public static int getCoinsDelay() {
        return coinsDelay;
    }

}

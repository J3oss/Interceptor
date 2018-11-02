package interceptor;

import java.awt.*;
import java.util.*;

/**
 * This Class is responsible for the drawing, movement and collision detection
 * between all the bombs and the city or a projectile.
 *
 */
public class Bomb {

    private static int countBombs = 0;

    private int currentX;
    private int currentY;
    private static final int SIDELENGTHX = 50;
    private static final int SIDELENGTHY = 70;

    private static Image bmbImg;
    private static int bombDelay;
    private static Graphics bmbGraphics;

    private static boolean collided = false;
    private static int sendx;
    private static int sendy;

    private static boolean bigExplosion = false;
    private static int sendxx;

    static ArrayList<Bomb> bombsRemoveArr = new ArrayList<Bomb>();
    private static ArrayList<Bomb> bombsArr = new ArrayList<Bomb>();

    /**
     * Redraws all bombs in the ArrayList which holds bombs that should be
     * drawn.
     *
     * @param g GUI Graphics component to draw on. Redraws ALL bombs.
     */
    public static void reDraw(Graphics g) {
        for (Bomb bomb : bombsArr) {
            if (bomb.currentY != 0) {
                g.drawImage(bmbImg, bomb.currentX, bomb.currentY, SIDELENGTHX, SIDELENGTHY, null);
            }
        }
    }

    /**
     * This function is responsible for updating the drawing coordinates,
     * collision detection and removal of collided bombs from the ArrayList.
     *
     * @param pixelsToMove moves the Bombs down by "pixelsToMove" pixels.
     */
    public static void move(int pixelsToMove) {
        for (Projectile p : Projectile.projectiles) {
            for (Bomb bomb : bombsArr) {
                double topRightX = p.getTopRx();
                double topRighty = p.getTopRy();
                double bottomRightX = p.getBottomRx();
                double bottomRighty = p.getBottomRy();

                if (topRightX >= bomb.currentX && topRightX <= bomb.currentX + SIDELENGTHX
                        && topRighty >= bomb.currentY && topRighty <= bomb.currentY + SIDELENGTHY) {

                    setIsCollided(true);
                    sendx = bomb.currentX;
                    sendy = bomb.currentY;

                    bombsRemoveArr.add(bomb);
                    Projectile.projectilesRemove.add(p);

                    bombDestroyed();

                } else if (bottomRightX >= bomb.currentX && bottomRightX <= bomb.currentX + SIDELENGTHX
                        && bottomRighty >= bomb.currentY && bottomRighty <= bomb.currentY + SIDELENGTHY) {

                    setIsCollided(true);
                    sendx = bomb.currentX;
                    sendy = bomb.currentY;

                    bombsRemoveArr.add(bomb);
                    Projectile.projectilesRemove.add(p);

                    bombDestroyed();
                }
            }
        }

        for (Bomb bomb : bombsArr) {
            bomb.currentY += pixelsToMove;
            if (bomb.currentY >= 800) {
                sendxx = bomb.currentX;
                bombsRemoveArr.add(bomb);
                StatusPanel.HEALTH -= 20;
                setBigExplosion(true);
                Audio.bigexplosionsound();
            }
        }
        for (Bomb bombToRemove : bombsRemoveArr) {
            bombsArr.remove(bombToRemove);
        }
    }

    /**
     * Called when a collision is detected by move method; Updates the score in
     * StatusPanel, Decreases the delay of Bomb Generation by 200ms, Decreases
     * the delay time to fire the turret By 5 ms and increases level by
     * monitoring the variable count.
     */
    public static void bombDestroyed() {
        Audio.explosionsound();
        StatusPanel.score += 10;
        StatusPanel.scoreLbl.setText("Score: " + StatusPanel.score + "   ");
        if (!MenuPanel.isMulti) {
            countBombs++;
            if (countBombs == 10) {
                if (StatusPanel.getLevel() < StatusPanel.maxLevel) {
                    Bomb.setBombDelay(Bomb.getBombDelay() - 200);
                    GamePnl.bombsTimer.setDelay(getBombDelay());
                    Coins.setCoinsDelay(Coins.getCoinsDelay() - 200);
                    GamePnl.coinsTimer.setDelay(Coins.getCoinsDelay() - 200);
                    StatusPanel.setLevel(StatusPanel.getLevel() + 1);
                    StatusPanel.HEALTH = StatusPanel.maxHealth;
                    countBombs = 0;
                }
            }

            StatusPanel.levelLbl.setText("Level: " + StatusPanel.getLevel() + "   ");
        }

    }

    /**
     * Constructs a new bomb at a random X position within the specified limits
     * and at Y=0;Then draws it using the passed Graphics Component;Finally, it
     * adds the created bomb to the ArrayList.
     *
     * @param g GUI component to Draw on.
     * @param XMin Minimum X pixel to draw on.
     * @param XMax Maximum X pixel to draw on.
     */
    public Bomb(Graphics g, int XMin, int XMax) {
        Random rand = new Random();
        currentX = Math.abs(rand.nextInt(XMax - XMin) + XMin - SIDELENGTHX);
        currentY = 0;
        g.drawImage(bmbImg, currentX, 0, SIDELENGTHX, SIDELENGTHY, null);
        bombsArr.add(this);
    }

    /**
     * Resets all static parameters and clears all ArrayLists.
     */
    public static void reset() {
        setBombDelay(3000);
        GamePnl.bombsTimer.setDelay(getBombDelay());
        bombsArr.clear();
        bombsRemoveArr.clear();
        setBigExplosion(false);
        setIsCollided(false);
    }

    /**
     *
     * @return current X position of the bomb
     */
    public int getCurrentX() {
        return currentX;
    }

    /**
     * @return current Y position of the bomb
     */
    public int getCurrentY() {
        return currentY;
    }

    /**
     *
     * @param currentY position of the bomb to set to.
     */
    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }

    /**
     * @return the bombDelay
     */
    public static int getBombDelay() {
        return bombDelay;
    }

    /**
     * @param aBombDelay the bombDelay to set
     */
    public static void setBombDelay(int aBombDelay) {
        bombDelay = aBombDelay;
    }

    /**
     * @param aBmbGraphics the bmbGraphics to set
     */
    public static void setBmbGraphics(Graphics aBmbGraphics) {
        bmbGraphics = aBmbGraphics;
    }

    /**
     * @return the collided
     */
    public static boolean isCollided() {
        return collided;
    }

    /**
     * @return the sendx
     */
    public static int getSendx() {
        return sendx;
    }

    /**
     * @return the sendy
     */
    public static int getSendy() {
        return sendy;
    }

    /**
     * @return the bigExplosion
     */
    public static boolean isBigExplosion() {
        return bigExplosion;
    }

    /**
     * @return the sendxx
     */
    public static int getSendxx() {
        return sendxx;
    }

    /**
     * @param aIsCollided the collided to set
     */
    public static void setIsCollided(boolean aIsCollided) {
        collided = aIsCollided;
    }

    /**
     * @param aIsBigexplosion the bigExplosion to set
     */
    public static void setBigExplosion(boolean aIsBigexplosion) {
        bigExplosion = aIsBigexplosion;
    }

    /**
     * @param aBmbImg the bmbImg to set
     */
    public static void setBmbImg(Image aBmbImg) {
        bmbImg = aBmbImg;
    }

}

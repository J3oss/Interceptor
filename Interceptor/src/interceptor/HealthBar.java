package interceptor;

import java.awt.*;

/**
 * This class draws the health bar given a Graphics component.
 */
public class HealthBar {

    /**
     * Draws HelathBar according to current health status.
     *
     * @param g
     */
    public static void drawBar(Graphics g) {

        g.setColor(Color.gray);
        g.fillRect(5, 5, StatusPanel.maxHealth, 50);
        if (StatusPanel.HEALTH <= StatusPanel.maxHealth / 4) {
            g.setColor(Color.red);
            g.fillRect(5, 5, StatusPanel.HEALTH, 50);
        } else if (StatusPanel.HEALTH < StatusPanel.maxHealth / 2) {
            g.setColor(Color.yellow);
            g.fillRect(5, 5, StatusPanel.HEALTH, 50);
        } else if (StatusPanel.HEALTH >= StatusPanel.maxHealth / 2) {
            g.setColor(Color.green);
            g.fillRect(5, 5, StatusPanel.HEALTH, 50);
        }

        g.setColor(Color.white);
        g.drawRect(5, 5, StatusPanel.maxHealth, 50);
    }
}

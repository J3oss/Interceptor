package interceptor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class represents a custom JPanel to display current game status.
 */
public class StatusPanel extends JPanel {

    static int maxLevel = 10;
    static int score = 0;
    private static int level = 1;
    static int coins = 0;
    static int maxHealth = 200; //Max Health increase after upgrade
    static int HEALTH = 200;

    static JButton upgradeCity = new JButton("upgrade city");
    static JButton upgradeTurret = new JButton("upgrade turret");
    static JLabel scoreLbl = new JLabel("Score: " + score + "   ");
    static JLabel levelLbl = new JLabel("Level: " + level + "   ");
    static JLabel CoinsLbl = new JLabel("Coins: " + coins + "   ");

    private static int maxCityLevel = 4, currentCityLevel = 0, maxTurretLevel = 4, currentTurretLevel = 0;
    static JLabel turretLvlLbl = new JLabel("Turret Level (Max 5): " + (currentTurretLevel + 1) + "   ");
    static JLabel cityLvlLbl = new JLabel("City Level (Max 5): " + (currentCityLevel + 1) + "   ");

    /**
     * Status Panel constructor sets all attributes to custom values/states.
     *
     */
    public StatusPanel() {
        scoreLbl.setFont(new Font("times new roman", 50, 20));
        scoreLbl.setForeground(Color.yellow);
        levelLbl.setFont(new Font("times new roman", 50, 20));
        levelLbl.setForeground(Color.yellow);
        CoinsLbl.setFont(new Font("times new roman", 50, 20));
        CoinsLbl.setForeground(Color.yellow);
        cityLvlLbl.setFont(new Font("times new roman", 50, 20));
        cityLvlLbl.setForeground(Color.yellow);
        turretLvlLbl.setFont(new Font("times new roman", 50, 20));
        turretLvlLbl.setForeground(Color.yellow);

        upgradeCity.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (StatusPanel.coins >= 100 && currentCityLevel < maxCityLevel) {
                    StatusPanel.coins -= 100;
                    StatusPanel.maxHealth += 20;
                    StatusPanel.HEALTH = StatusPanel.maxHealth;
                    StatusPanel.getCoinsLbl().setText("Coins " + StatusPanel.coins + "   ");
                    currentCityLevel++;
                    cityLvlLbl.setText("City Level (Max 5): " + (currentCityLevel + 1) + "   ");
                }
            }
        });
        upgradeTurret.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (StatusPanel.coins >= 100 && currentTurretLevel < maxTurretLevel) {
                    StatusPanel.coins -= 100;
                    StatusPanel.getCoinsLbl().setText("Coins " + StatusPanel.coins + "   ");
                    Turret.turretDelayLevel -= 20;
                    currentTurretLevel++;
                    turretLvlLbl.setText("Turret Level (Max 5): " + (currentTurretLevel + 1) + "   ");

                }
            }
        });

        setBackground(Color.decode("#3b243e"));
        setPreferredSize(new Dimension(GameFrame.screen.width, 50));

        add(upgradeCity);
        add(upgradeTurret);
        add(scoreLbl);
        add(levelLbl);
        add(CoinsLbl);
        add(cityLvlLbl);
        add(turretLvlLbl);

    }

    /**
     * Resets all status values.
     */
    public static void resetStatus() {
        score = 0;
        level = 1;
        maxLevel = 10;
        coins = 0;
        HEALTH = 200;
        maxHealth = 200;
        currentCityLevel = currentTurretLevel = 0;
        cityLvlLbl.setText("City Level (Max 5): " + (currentCityLevel + 1) + "   ");
        turretLvlLbl.setText("Turret Level (Max 5): " + (currentTurretLevel + 1) + "   ");
        CoinsLbl.setText("Coins " + coins + "   ");
        scoreLbl.setText("Score: " + score + "   ");
        levelLbl.setText("Level: " + level + "   ");
    }

    /**
     * @return the level
     */
    public static int getLevel() {
        return level;
    }

    /**
     * @param aLevel the level to set
     */
    public static void setLevel(int aLevel) {
        level = aLevel;
    }

    /**
     * @param aLevelLbl the levelLbl to set
     */
    public static void setLevelLbl(JLabel aLevelLbl) {
        levelLbl = aLevelLbl;
    }

    /**
     * @param aCoinsLbl the CoinsLbl to set
     */
    public static void setCoinsLbl(JLabel aCoinsLbl) {
        CoinsLbl = aCoinsLbl;
    }

    /**
     * @return the levelLbl
     */
    public static JLabel getLevelLbl() {
        return levelLbl;
    }

    /**
     * @return the CoinsLbl
     */
    public static JLabel getCoinsLbl() {
        return CoinsLbl;
    }
}

package interceptor;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.*;

/**
 * This class represents a custom JPanel adapted to be the main Game Panel where
 * all projectiles, bombs, coins, animations and other miscellaneous swing
 * components will be drawn.
 *
 */
public class GamePnl extends JPanel {

    static boolean isExplosion = false;

    static javax.swing.Timer bombsTimer;
    static javax.swing.Timer coinsTimer;
    static javax.swing.Timer multiTimer;

    private JLabel currentCity = City.cityImage();
    private JLabel currentTurret = Turret.turretImage();
    private JButton pauseBtn = new PauseButton();

    public static String endMultiGame = new String();

    /**
     * Constructor of the class.
     *
     * Calls initializing function.
     *
     * @throws IOException Image may not be found
     */
    public GamePnl() throws IOException {
        init();
    }

    /**
     * Initializing function for timers and contains motion and action listners
     * of mouse.
     *
     * @throws IOException Image may not be found
     */
    public void init() throws IOException {
        Projectile.setProjectileImg(ImageIO.read(new FileImageInputStream(new File("Data\\Graphics\\Projectiles.png"))));
        Bomb.setBmbImg(ImageIO.read(new FileImageInputStream(new File("Data\\Graphics\\Bombs.png"))));
        Coins.setCoinsImg(ImageIO.read(new FileImageInputStream(new File("Data\\Graphics\\Coins.png"))));
        City.setHullImage(ImageIO.read(new File("Data\\Graphics\\hull.png")));

        setLayout(null);
        setSize(1920, 1030);
        setBackground(Color.red);
        Explosion.initExplosion();
        BigExplosionCity.initFire();
        add(pauseBtn);
        add(City.cityImage());

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (Turret.turretDelay <= 200) {
                    isExplosion = true;
                    Audio.turrentshotsound();
                    Graphics g = getGraphics();
                    //xplus and y plus are the nozzle's coordinates.
                    double xPlus = 132 * Math.abs(Math.cos(-Turret.getGunPointTranslationAngle()));
                    double yPlus = 940 - 132 * Math.abs(Math.sin(-Turret.getGunPointTranslationAngle()));
                    Projectile p = new Projectile(e.getX(), e.getY(), (int) xPlus, (int) (yPlus), g);
                    Explosion.setTurretX((int) xPlus - 57);
                    Explosion.setTurretY((int) yPlus - 67);
                    Explosion.drawAtTurret(g);
                    Turret.turretDelay = Turret.turretDelayLevel;
                }
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                double x = (double) e.getX();
                double y = (double) e.getY();
                if (y < 975) {
                    double angle = -Math.atan(((940 + 35 - y) / (x)));
                    Turret.setGunPointTranslationAngle(angle);
                } else {
                    Turret.setGunPointTranslationAngle(0);
                }
            }

        });

        bombsTimer = new javax.swing.Timer(Bomb.getBombDelay(), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Graphics g = getGraphics();
                Bomb bm = new Bomb(g, (getWidth() / 2) - 400, (getWidth()) - 120);
            }
        });

        multiTimer = new javax.swing.Timer(60000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MenuPanel.pw1.println(StatusPanel.score);
                    MenuPanel.pw1.flush();
                    endMultiGame = MenuPanel.br.readLine();
                    StatusPanel.HEALTH = 0;
                    Audio.stopAudio();
                } catch (IOException ex) {
                    Logger.getLogger(GamePnl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        coinsTimer = new javax.swing.Timer(Coins.getCoinsDelay(), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!MenuPanel.isMulti) {
                    Graphics g = getGraphics();
                    Coins cs = new Coins(g, (getWidth() / 2) - 400, (getWidth()) - 120);
                }
            }
        });
    }

    /**
     * Calls all moving and drawing functions in game
     *
     * @param g Graphics object
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        GamePnl.bombsTimer.setDelay(Bomb.getBombDelay());
        GamePnl.coinsTimer.setDelay(Coins.getCoinsDelay());
        Turret.drawGunPoint(g);
        City.drawHullImage(g);
        Bomb.move(1);
        Coins.move(1);
        Projectile.move();
        Projectile.redraw(g);
        Bomb.reDraw(g);
        Coins.reDraw(g);
        Turret.turretDelay -= 1;
        if (MenuPanel.isMulti) {
            this.pauseBtn.setVisible(false);
            StatusPanel.levelLbl.setVisible(false);
            StatusPanel.CoinsLbl.setVisible(false);
            StatusPanel.upgradeCity.setVisible(false);
            StatusPanel.upgradeTurret.setVisible(false);
            StatusPanel.cityLvlLbl.setVisible(false);
            StatusPanel.turretLvlLbl.setVisible(false);
        } else {
            this.pauseBtn.setVisible(true);
            StatusPanel.levelLbl.setVisible(true);
            StatusPanel.CoinsLbl.setVisible(true);
            StatusPanel.upgradeCity.setVisible(true);
            StatusPanel.upgradeTurret.setVisible(true);
            StatusPanel.cityLvlLbl.setVisible(true);
            StatusPanel.turretLvlLbl.setVisible(true);
            HealthBar.drawBar(g);
        }
        Explosion.drawAtTurret(g);
        Explosion.drawAtPoint(g, Bomb.getSendx(), Bomb.getSendy());
        BigExplosionCity.redraw(g, Bomb.getSendxx());
        Projectile.projectilesRemove.clear();
        Coins.coinsRemoveArr.clear();
        Bomb.bombsRemoveArr.clear();
    }
}

/**
 * Loads and adjusts the city Image to be drawn on the GamePanel.
 */
class City {

    private static ImageIcon cityIcon;
    private static JLabel cityHolder;
    private static Image hullImage;

    /**
     * returns a label containing city image.
     */
    public static JLabel cityImage() {
        cityIcon = new ImageIcon("Data\\Graphics\\City.jpg");
        cityHolder = new JLabel(cityIcon);
        cityHolder.setBounds(0, 0, 1920, 1030);
        return cityHolder;
    }

    /**
     * Draws turret hull.
     */
    public static void drawHullImage(Graphics g) {
        g.drawImage(hullImage, 0, 920, null, null);
    }

    public static void setHullImage(Image hullImage) {
        City.hullImage = hullImage;
    }
}

/**
 * This class represents a custom JButton designed to be used as a pause button.
 */
class PauseButton extends JButton {

    private static ImageIcon icon;

    public PauseButton() {

        icon = new ImageIcon("Data\\Graphics\\PauseBtn.png");
        this.setIcon(icon);
        this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.setFocusPainted(false);
        this.setBounds(1840, 20, 50, 50);
        this.setBorder(null);
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Audio.stopAudio();
                Interceptor.gameFrame.gameMenu();

            }
        });
    }
}

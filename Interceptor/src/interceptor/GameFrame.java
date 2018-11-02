package interceptor;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class represents the Main game frame which contains the main game loop
 * and on which other dialog-like frames may appear, when needed; This class
 * implements the interface runnable so that it can run in a thread of its own;
 * This class will contain and show One or more panels depending on the state of
 * the game;The three Panels are a MenuPanel, a StatusPanel and a GamePanel.
 *
 */
public class GameFrame extends JFrame implements Runnable {

    static Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

    static String playerName;
    static ArrayList<Score> scoresList = new <Score> ArrayList();

    private MenuPanel startMnuPnl = new MenuPanel();
    private StatusPanel statusPnl = new StatusPanel();
    private GamePnl gamePnl;//initialized in constructor because it may throw IO exception

    private static boolean isStart = false;
    private static boolean started = false;
    private static boolean isStop = false;
    private static boolean stopped = false;

    private Container c = this.getContentPane();

    /**
     * Construction Calls the init method.
     *
     * @throws IOException
     */
    public GameFrame() throws IOException {
        init();
    }

    /**
     * This method Loads the scores from leaderboard.txt, enters the data into
     * the ArrayList then sorts for later use in the leaderBoard; It also
     * initializes the needed panels and sets the Game Frame attributes.
     *
     * @throws IOException
     */
    public void init() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("Data\\leaderBoard.txt"));
        String name;
        String hisScore;
        while ((name = reader.readLine()) != null && (hisScore = reader.readLine()) != null) {
            scoresList.add(new Score(name, Integer.parseInt(hisScore)));
        }
        reader.close();
        Collections.sort(scoresList);

        startMnuPnl = new MenuPanel();
        statusPnl = new StatusPanel();
        gamePnl = new GamePnl();

        this.setTitle("Interceptor");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);
        c.add(startMnuPnl);
        this.pack();
    }

    /**
     * This overridden method represents the main game loop which allows the
     * game to run in a thread.
     */
    @Override
    public void run() {
        while (true) {
            if (isStart & !started) {
                Audio.stopAudio();
                startTheGame();
            }
            if (isStart && started) {
                gamePnl.repaint();
                if (StatusPanel.HEALTH <= 0) {
                    try {
                        clearGame();
                    } catch (Exception e) {
                    }

                }
            }
            try {
                Thread.sleep(7);
            } catch (InterruptedException e) {
            }

        }
    }

    /**
     * This method starts the actual game by adjusting the visibility of
     * respective panels and updating the game state booleans.
     */
    public void startTheGame() {
        startMnuPnl.setVisible(false);
        startMnuPnl.multiplayer.setVisible(false);
        c.add(statusPnl, BorderLayout.SOUTH);
        c.add(gamePnl);
        gamePnl.setVisible(true);
        statusPnl.setVisible(true);

        gamePnl.bombsTimer.start();
        gamePnl.coinsTimer.start();

        started = true;
        stopped = false;
        isStop = false;
    }

    /**
     * This method pauses the game by adjusting the visibility of respective
     * panels and updating the game state booleans.
     */
    public void gameMenu() {
        Audio.stopAudio();
        Audio.hellmarchsound();
        gamePnl.setVisible(false);
        statusPnl.setVisible(false);
        startMnuPnl.setVisible(true);
        gamePnl.bombsTimer.stop();
        gamePnl.coinsTimer.stop();
        gamePnl.multiTimer.stop();
        stopped = true;
        started = false;
        isStart = false;
    }

    /**
     * This method Terminates the game after it saves the score and name of the
     * player to the array list and writes it to "leaderboard.txt".
     */
    public void exitGame() throws IOException {

        scoresList.add(new Score(playerName, StatusPanel.score));
        Collections.sort(scoresList);
        BufferedWriter writer = new BufferedWriter(new FileWriter("Data\\leaderBoard.txt", true));
        writer.write(playerName);
        writer.newLine();
        writer.write(Integer.toString(StatusPanel.score));
        writer.newLine();
        writer.close();
        System.exit(0);
    }

    /**
     * This method ends the game and returns to main Menu by adjusting the
     * visibility of respective panels and updating the game state
     * booleans;Moreover, it saves the score and name of the player to the array
     * list and writes it to "leaderboard.txt".
     */
    public void clearGame() throws IOException {

        scoresList.add(new Score(playerName, StatusPanel.score));
        Collections.sort(scoresList);
        BufferedWriter writer = new BufferedWriter(new FileWriter("Data\\leaderBoard.txt", true));
        writer.write(playerName);
        writer.newLine();
        writer.write(Integer.toString(StatusPanel.score));
        writer.newLine();
        writer.close();

        startMnuPnl.newGame.setLocation(startMnuPnl.newGameLocation.x, startMnuPnl.newGameLocation.y);
        startMnuPnl.multiplayer.setVisible(true);
        startMnuPnl.setNewGameText("NEW GAME");

        gameMenu();
        //GamePnl.endMultiGame.equals("")||
        if (statusPnl.HEALTH == 0 & !MenuPanel.isMulti) {
            JOptionPane.showMessageDialog(null, "Game Over");
        } else if (!GamePnl.endMultiGame.equals("") && MenuPanel.isMulti) {
            JOptionPane.showMessageDialog(null, GamePnl.endMultiGame);
        }

        Turret.turretDelayLevel = 320;
        playerName = null;
        Bomb.reset();
        Projectile.reset();
        Coins.reset();
        statusPnl.resetStatus();

        isStart = false;
        started = false;
        isStop = false;
        stopped = false;

    }

    /**
     * @param aIsStart the isStart to set
     */
    public static void setIsStart(boolean aIsStart) {
        isStart = aIsStart;
    }

    /**
     * @param aIsStop the isStop to set
     */
    public static void setIsStop(boolean aIsStop) {
        isStop = aIsStop;
    }

}

/**
 * This class represents a player's score, it implements comparable to allow
 * easier sorting of an ArrayList of Scores.
 *
 * @author Mohammed Ehab
 */
class Score implements Comparable {

    private String playerNameString;
    private int playerScore;

    /**
     * Constructs a score objects and takes 2 parameters.
     *
     * @param playerNameString
     * @param playerScore
     */
    public Score(String playerNameString, int playerScore) {
        this.playerNameString = playerNameString;
        this.playerScore = playerScore;
    }

    @Override
    public int compareTo(Object o) {
        int score = ((Score) o).getPlayerScore();
        return score - this.getPlayerScore();
    }

    @Override
    public String toString() {
        return getPlayerNameString() + "\t" + getPlayerScore();
    }

    public String nmstoString() {
        return getPlayerNameString();
    }

    public String scrstoString() {
        return "\t" + Integer.toString(getPlayerScore());
    }

    /**
     * @return the playerNameString
     */
    public String getPlayerNameString() {
        return playerNameString;
    }

    /**
     * @param playerNameString the playerNameString to set
     */
    public void setPlayerNameString(String playerNameString) {
        this.playerNameString = playerNameString;
    }

    /**
     * @return the playerScore
     */
    public int getPlayerScore() {
        return playerScore;
    }

    /**
     * @param playerScore the playerScore to set
     */
    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }
}

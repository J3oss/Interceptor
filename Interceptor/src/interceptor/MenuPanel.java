package interceptor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import static interceptor.GamePnl.multiTimer;
import java.awt.Point;

/**
 * This class represents a custom JPanel designed to act as a start and pause
 * Menu.
 */
public class MenuPanel extends JPanel {

    private static ImageIcon backGround = new ImageIcon("Data\\Graphics\\StartScreen.jpg");
    private static JLabel BGLbl = new JLabel(backGround);

    private Font titleFont = new Font("snap itc", 50, 85);
    private Font buttonsFont = new Font("snap itc", 50, 55);

    private Color black = new Color(0, 0, 0);

    private JLabel title = new JLabel("INTERCEPTOR");

    JButton newGame = new JButton("NEW GAME");
    JButton multiplayer = new JButton("MULTIPLAYER");
    private JButton leaderBoard = new JButton("LEADERBOARD");
    private JButton quit = new JButton("QUIT");

    public Socket s;
    public InputStreamReader isr;
    public OutputStreamWriter osw;
    public static BufferedReader br;
    public static PrintWriter pw1;
    static boolean isMulti;
    Point newGameLocation = new Point(GameFrame.screen.width / 2 - 210, 250);

    /**
     * Constructor of MenuPanel
     */
    public MenuPanel() {
        init();
    }

    /**
     * Menu Panel initializer.
     */
    public void init() {

        this.setLayout(null);

        BGLbl.setBounds(0, 0, 1920, 1080);

        title.setFont(titleFont);
        title.setForeground(black);
        title.setBounds(200, 100, 1000, 150);

        newGame.setFont(buttonsFont);
        newGame.setForeground(black);
        newGame.setOpaque(false);
        newGame.setContentAreaFilled(false);
        newGame.setBorderPainted(false);
        newGame.setBounds(GameFrame.screen.width / 2 - 210, 250, 420, 70);
        newGame.setFocusPainted(false);
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                JFrame nameFrame = new JFrame();
                JPanel leadersPnl = new JPanel();
                JTextField nameTxt = new JTextField("YOUR NAME", 10);
                JButton okButton = new JButton("Confirm Name");

                nameTxt.setOpaque(false);
                nameTxt.setFont(buttonsFont);
                nameTxt.setBorder(null);

                okButton.setFocusPainted(false);
                okButton.setBorderPainted(false);
                okButton.setContentAreaFilled(false);
                okButton.setFont(buttonsFont);
                okButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Bomb.setBombDelay(3000);
                        GameFrame.playerName = nameTxt.getText();
                        newGame.setText("CONTINUE");
                        newGame.setLocation(newGameLocation.x, newGameLocation.y + 150);
                        GameFrame.setIsStart(true);
                        GameFrame.setIsStop(false);
                        nameFrame.dispose();
                        Audio.stopAudio();
                        Audio.startplayingsound();
                        Audio.didpause = 0;
                        isMulti = false;
                    }
                });

                leadersPnl.setBackground(new Color(193, 34, 34));
                leadersPnl.add(nameTxt);
                leadersPnl.add(okButton);

                nameFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                nameFrame.setBounds(640, 400, 640, 300);
                nameFrame.setUndecorated(true);
                nameFrame.getContentPane().add(leadersPnl);
                nameFrame.setFocusable(true);
                nameFrame.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent k) {
                        if (k.getKeyCode() == KeyEvent.VK_ESCAPE) {
                            nameFrame.dispose();
                        }
                    }
                });

                if (GameFrame.playerName == null) {
                    nameFrame.setVisible(true);
                } else {
                    GameFrame.setIsStart(true);
                    GameFrame.setIsStop(false);
                }

            }

        });

        multiplayer.setFont(buttonsFont);
        multiplayer.setForeground(black);
        multiplayer.setOpaque(false);
        multiplayer.setContentAreaFilled(false);
        multiplayer.setBorderPainted(false);
        multiplayer.setBounds(GameFrame.screen.width / 2 - 255, 400, 550, 70);
        multiplayer.setFocusPainted(false);
        multiplayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Font buttonsFont2 = new Font("snap itc", 50, 40);
                JFrame ipFrame = new JFrame();
                JPanel nameIPPanel = new JPanel();
                JTextField nameTxt = new JTextField("YOUR NAME", 15);
                JTextField IPTxt = new JTextField("127.0.0.1", 15);
                JButton confirmBtn = new JButton("Confirm Name and IP");

                nameTxt.setOpaque(false);
                nameTxt.setFont(buttonsFont2);
                nameTxt.setBorder(null);

                IPTxt.setOpaque(false);
                IPTxt.setFont(buttonsFont2);
                IPTxt.setBorder(null);

                confirmBtn.setFocusPainted(false);
                confirmBtn.setBorderPainted(false);
                confirmBtn.setContentAreaFilled(false);
                confirmBtn.setFont(buttonsFont2);
                confirmBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            Bomb.setBombDelay(1000);
                            s = new Socket(IPTxt.getText(), 5000);
                            isr = new InputStreamReader(s.getInputStream());
                            osw = new OutputStreamWriter(s.getOutputStream());
                            br = new BufferedReader(isr);
                            pw1 = new PrintWriter(osw);
                            br.readLine();

                            StatusPanel.HEALTH = 1000;

                            isMulti = true;

                            GameFrame.playerName = nameTxt.getText();
                            newGame.setText("CONTINUE");
                            GameFrame.setIsStart(true);
                            GameFrame.setIsStop(false);
                            multiTimer.start();
                            ipFrame.dispose();
                            Audio.stopAudio();
                            Audio.startplayingsound();
                            Audio.didpause = 0;

                        } catch (IOException ex) {
                            Logger.getLogger(MenuPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });

                nameIPPanel.setBackground(new Color(193, 34, 34));
                nameIPPanel.add(nameTxt);
                nameIPPanel.add(IPTxt);
                nameIPPanel.add(confirmBtn);

                ipFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                ipFrame.setBounds(640, 400, 640, 300);
                ipFrame.setUndecorated(true);
                ipFrame.getContentPane().add(nameIPPanel);
                ipFrame.setFocusable(true);
                ipFrame.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent k) {
                        if (k.getKeyCode() == KeyEvent.VK_ESCAPE) {
                            ipFrame.dispose();
                        }
                    }
                });

                if (GameFrame.playerName == null) {
                    ipFrame.setVisible(true);
                } else {
                    GameFrame.setIsStart(true);
                    GameFrame.setIsStop(false);
                }

            }

        });

        leaderBoard.setFont(buttonsFont);
        leaderBoard.setForeground(black);
        leaderBoard.setOpaque(false);
        leaderBoard.setContentAreaFilled(false);
        leaderBoard.setBorderPainted(false);
        leaderBoard.setBounds(GameFrame.screen.width / 2 - 265, 550, 530, 70);
        leaderBoard.setFocusPainted(false);
        leaderBoard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JFrame leaderBoardFrame = new JFrame();
                JPanel leadersPnl = new JPanel();
                JButton closeBtn = new JButton("Close");
                JTextArea leadersTxt = new JTextArea(12, 10);
                JTextArea leadersscoresTxt = new JTextArea(12, 10);
                String nms = "\n";
                String scrs = "\n";
                for (int i = 0; i < 10 && i < GameFrame.scoresList.size(); i++) {
                    nms += GameFrame.scoresList.get(i).nmstoString() + "\n";
                }
                for (int i = 0; i < 10 && i < GameFrame.scoresList.size(); i++) {
                    scrs += GameFrame.scoresList.get(i).scrstoString() + "\n";
                }
                Font fnt = new Font("snap itc", 50, 30);
                leadersTxt.setText(nms);
                leadersTxt.setOpaque(false);
                leadersTxt.setFont(fnt);
                leadersTxt.setBorder(null);
                leadersTxt.setEditable(false);
                leadersTxt.setMargin(new Insets(100, 20, 20, 20));

                leadersscoresTxt.setText(scrs);
                leadersscoresTxt.setOpaque(false);
                leadersscoresTxt.setFont(fnt);
                leadersscoresTxt.setBorder(null);
                leadersscoresTxt.setEditable(false);
                leadersscoresTxt.setMargin(new Insets(100, 20, 20, 20));

                closeBtn.setBorderPainted(false);
                closeBtn.setContentAreaFilled(false);
                closeBtn.setFont(buttonsFont);
                closeBtn.setFocusPainted(false);
                closeBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        leaderBoardFrame.dispose();
                    }
                });
                leadersPnl.add(leadersTxt);
                leadersPnl.add(leadersscoresTxt);
                leadersPnl.add(closeBtn);
                leadersPnl.setBackground(new Color(193, 34, 34));
                leaderBoardFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                leaderBoardFrame.setBounds(640, 220, 640, 720);
                leaderBoardFrame.setUndecorated(true);
                leaderBoardFrame.getContentPane().add(leadersPnl);
                leaderBoardFrame.setVisible(true);
            }
        });

        quit.setFont(buttonsFont);
        quit.setForeground(black);
        quit.setOpaque(false);
        quit.setContentAreaFilled(false);
        quit.setBorderPainted(false);
        quit.setBounds(GameFrame.screen.width / 2 - 100, 675, 200, 70);
        quit.setFocusPainted(false);
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (GameFrame.playerName != null) {
                    try {
                        Interceptor.gameFrame.clearGame();
                    } catch (IOException ex) {
                        Logger.getLogger(MenuPanel.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    System.exit(0);
                }

            }
        });

        this.add(title);
        this.add(newGame);
        this.add(multiplayer);
        this.add(leaderBoard);
        this.add(quit);
        this.add(BGLbl);
    }

    public void setNewGameText(String s) {
        this.newGame.setText(s);
    }

}

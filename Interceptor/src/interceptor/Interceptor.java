package interceptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Project Main class
 */
public class Interceptor {

    static GameFrame gameFrame;
    static GameServer host;

    public static void main(String[] args) {
        try {
            Audio.loadAudio();
            Audio.hellmarchsound();

            host = new GameServer();
            Thread hostThread = new Thread(host);
            hostThread.start();

            gameFrame = new GameFrame();
            gameFrame.setVisible(true);

            Thread gameThread = new Thread(gameFrame);
            gameThread.start();

        } catch (IOException e) {
            System.err.println("Resources Not found");
        }
    }
}

/**
 *
 * Code of game server
 */
class GameServer implements Runnable {

    private static int player1;
    private static int player2;

    static void host() {
        try {
            ServerSocket ss = new ServerSocket(5000);

            Socket s1 = ss.accept();
            System.out.println("Player 1 Connected");
            Socket s2 = ss.accept();
            System.out.println("Player 2 Connected");

            InputStreamReader isr1 = new InputStreamReader(s1.getInputStream());
            InputStreamReader isr2 = new InputStreamReader(s2.getInputStream());

            OutputStreamWriter osw1 = new OutputStreamWriter(s1.getOutputStream());
            OutputStreamWriter osw2 = new OutputStreamWriter(s2.getOutputStream());

            BufferedReader br1 = new BufferedReader(isr1);
            BufferedReader br2 = new BufferedReader(isr2);

            PrintWriter pw1 = new PrintWriter(osw1);
            PrintWriter pw2 = new PrintWriter(osw2);

            pw1.println("startgame");
            pw2.println("startgame");

            pw1.flush();
            pw2.flush();

            player1 = Integer.parseInt(br1.readLine());
            player2 = Integer.parseInt(br2.readLine());

            System.out.println(player1);
            System.out.println(player2);

            if (player1 > player2) {
                pw1.println("win");
                pw2.println("loss");
            } else if (player1 < player2) {
                pw2.println("win");
                pw1.println("loss");
            } else if (player1 == player2) {
                pw1.println("draw");
                pw2.println("draw");
            }
            pw1.flush();
            pw2.flush();

            pw1.close();
            pw2.close();
            br1.close();
            br2.close();
            isr1.close();
            isr2.close();
            osw1.close();
            osw2.close();
            s1.close();
            s2.close();
            ss.close();
        } catch (IOException ex) {
            Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        while (true) {
            host();
        }
    }
}

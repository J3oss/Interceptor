package interceptor;

import java.io.File;
import javax.sound.sampled.*;

/**
 * This class has a role of playing sound/Audio Files specific for some game
 * events.
 */
public class Audio {

    public static int didpause = 0;
    static Clip hellmarchsound;
    static Clip collectCoinsound;
    static Clip explosionsound;
    static Clip startplayingsound;
    static Clip bigexplosionsound;
    static Clip turrentshotsound;

    static AudioInputStream audioInA;
    static AudioInputStream audioInB;
    static AudioInputStream audioInC;
    static AudioInputStream audioInD;
    static AudioInputStream audioInE;
    static AudioInputStream audioInF;

    /**
     * This function initializes (Loads) all the required audio files to be
     * later used for audio playback.
     */
    public static void loadAudio() {
        try {

            File fileA = new File("Data\\Audio\\hellmarchsound.wav");
            audioInA = AudioSystem.getAudioInputStream(fileA);

            File fileB = new File("Data\\Audio\\collectCoin.wav");
            audioInB = AudioSystem.getAudioInputStream(fileB);

            File fileC = new File("Data\\Audio\\fireProjectile.wav");
            audioInC = AudioSystem.getAudioInputStream(fileC);

            File fileD = new File("Data\\Audio\\startplayingsound.wav");
            audioInD = AudioSystem.getAudioInputStream(fileD);

            File fileE = new File("Data\\Audio\\bigexplosionsound.wav");
            audioInE = AudioSystem.getAudioInputStream(fileE);

            File fileF = new File("Data\\Audio\\BombHit.wav");
            audioInF = AudioSystem.getAudioInputStream(fileF);

            hellmarchsound = AudioSystem.getClip();

            collectCoinsound = AudioSystem.getClip();

            turrentshotsound = AudioSystem.getClip();

            bigexplosionsound = AudioSystem.getClip();

            startplayingsound = AudioSystem.getClip();

            explosionsound = AudioSystem.getClip();

            hellmarchsound.open(audioInA);
            collectCoinsound.open(audioInB);
            explosionsound.open(audioInC);
            startplayingsound.open(audioInD);
            bigexplosionsound.open(audioInE);
            turrentshotsound.open(audioInF);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error in initializer");
        }

    }

    /**
     * Stops the Hell March sound and The Siren sound.
     */
    public static void stopAudio() {

        hellmarchsound.stop();
        startplayingsound.stop();

    }

    /**
     * Plays the sound of a Projectile hitting a Bomb.
     */
    public static void explosionsound() {
        explosionsound.setFramePosition(0);
        explosionsound.start();
    }

    /**
     * Plays the Siren sound only if a new game is started.
     */
    public static void startplayingsound() {

        if (didpause == 0) {
            startplayingsound.setFramePosition(0);
            startplayingsound.start();
            didpause++;
        }

    }

    /**
     * Plays the sound of a Bomb hitting the city.
     */
    public static void bigexplosionsound() {

        bigexplosionsound.setFramePosition(0);
        bigexplosionsound.start();
    }

    /**
     * Plays the sound of when a projectile is fired.
     */
    public static void turrentshotsound() {

        turrentshotsound.start();
        turrentshotsound.setFramePosition(0);

    }

    /**
     * Plays the sound of when a coin is collected.
     */
    public static void collectCoinsound() {

        collectCoinsound.setFramePosition(0);
        collectCoinsound.start();

    }

    /**
     * Plays the Hell March sound track.
     */
    public static void hellmarchsound() {

        hellmarchsound.setFramePosition(0);
        hellmarchsound.start();
    }
}

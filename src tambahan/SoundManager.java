import javax.sound.sampled.*;
import java.io.*;

public class SoundManager {

    public static void playGameResult(String result) {
        switch (result) {
            case "WIN":  playWinSound();  break;
            case "LOSE": playLoseSound(); break;
            case "DRAW": playDrawSound(); break;
        }
    }

    public static void playWinSound() {
        new Thread(() -> {
            try {
                int[] freqs     = {523, 659, 784, 1047};
                int[] durations = {150, 150, 150, 400};
                for (int i = 0; i < freqs.length; i++) {
                    playTone(freqs[i], durations[i], 0.6f);
                    Thread.sleep(20);
                }
            } catch (Exception e) {
                System.err.println("[Sound] Win sound error: " + e.getMessage());
            }
        }).start();
    }

    public static void playLoseSound() {
        new Thread(() -> {
            try {
                int[] freqs     = {400, 300, 200};
                int[] durations = {200, 200, 500};
                for (int i = 0; i < freqs.length; i++) {
                    playTone(freqs[i], durations[i], 0.5f);
                    Thread.sleep(20);
                }
            } catch (Exception e) {
                System.err.println("[Sound] Lose sound error: " + e.getMessage());
            }
        }).start();
    }

    public static void playDrawSound() {
        new Thread(() -> {
            try {
                int[] freqs     = {440, 440};
                int[] durations = {200, 200};
                for (int i = 0; i < freqs.length; i++) {
                    playTone(freqs[i], durations[i], 0.4f);
                    Thread.sleep(100);
                }
            } catch (Exception e) {
                System.err.println("[Sound] Draw sound error: " + e.getMessage());
            }
        }).start();
    }

    public static void playClickSound() {
        new Thread(() -> {
            try {
                playTone(800, 80, 0.3f);
            } catch (Exception e) {
                System.err.println("[Sound] Click sound error: " + e.getMessage());
            }
        }).start();
    }

    public static void playComputerSound() {
        new Thread(() -> {
            try {
                playTone(500, 80, 0.3f);
            } catch (Exception e) {
                System.err.println("[Sound] Computer sound error: " + e.getMessage());
            }
        }).start();
    }

    private static void playTone(int frequency, int durationMs, float volume) {
        try {
            AudioFormat format = new AudioFormat(44100, 16, 1, true, false);
            DataLine.Info info  = new DataLine.Info(SourceDataLine.class, format);

            if (!AudioSystem.isLineSupported(info)) return;

            SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();

            int numSamples = (int) (44100 * durationMs / 1000.0);
            byte[] buffer  = new byte[numSamples * 2]; 

            for (int i = 0; i < numSamples; i++) {
                double angle = 2.0 * Math.PI * i * frequency / 44100;
                double envelope = Math.min(1.0, (numSamples - i) / (44100 * 0.05));
                short  sample   = (short) (Math.sin(angle) * envelope * volume * Short.MAX_VALUE);

                buffer[i * 2]     = (byte) (sample & 0xFF);
                buffer[i * 2 + 1] = (byte) ((sample >> 8) & 0xFF);
            }

            line.write(buffer, 0, buffer.length);
            line.drain();
            line.close();

        } catch (Exception e) {
            System.err.println("[Sound] playTone error: " + e.getMessage());
        }
    }
}

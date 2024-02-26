import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import java.awt.Graphics2D;
import java.io.File;
import javax.imageio.ImageIO;

public class ArtWindow {
    JFrame window;
    boolean toggle = true;

    // Constructor: Create a window for the Fractal Art
    ArtWindow() {
        window = new JFrame("Dynamic Fractal by Gian Alingog");
        window.setSize(1280 + 14, 720 + 37); // +14 width and +37 height to account for black bars on the sides of the window
        window.setLocation(20, 20);
        window.add(new FractalArt());
        window.setVisible(toggle);
        window.setResizable(false);

        saveImage(window);
    }

    // Save what is painted on the screen to image to be used for image manipulation
    public void saveImage(JFrame frame) {
        BufferedImage image = new BufferedImage(frame.getContentPane().getWidth(), frame.getContentPane().getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        frame.getContentPane().paint(g2);
        try {
            ImageIO.write(image, "png", new File("art.png")); // Do not change the filename
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import java.awt.Graphics2D;

public class PixImage {
    private BufferedImage originalImage;
    private JFrame frame;
    private String title;

    // width and height of the image
    public final int width;
    public final int height;

    // arrays containing the red, green and blue values for each pixel
    public int[][] red;
    public int[][] green;
    public int[][] blue;

    // Creates a new blank (black) PixImage with the specified width and height
    public PixImage(int height, int width) {
        // create a frame to display the image (for later)
        this.title = height + " X " + width;
        frame = new JFrame(this.title);
        frame.setVisible(false);

        // setup properties for a blank image (i.e. all black)
        this.width = width;
        this.height = height;
        originalImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        red = new int[height][width];
        green = new int[height][width];
        blue = new int[height][width];
    }

    // makes a copy of the input image
    public PixImage(PixImage original) {
        // create a frame to display the image (for later)
        this.title = "Tranquility in Geometry"; // Teehee! Hard-coding a name! Please don't get mad at me!
        frame = new JFrame(this.title);
        frame.setVisible(false);

        // setup properties for a blank image (i.e. all black)
        this.width = original.width;
        this.height = original.height;
        originalImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        red = new int[height][width];
        green = new int[height][width];
        blue = new int[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                red[i][j] = original.red[i][j];
                green[i][j] = original.green[i][j];
                blue[i][j] = original.blue[i][j];
            }
        }
    }

    // Creates a new PixImage from the given file
    public PixImage(String imageFileName) {
        this.title = imageFileName;
        // create a frame to display the image (for later)
        frame = new JFrame(this.title);
        frame.setVisible(false);

        File imgFile = new File(imageFileName);
        if (!imgFile.exists()) {
            System.out.println("Image file NOT FOUND at: " + imgFile.getAbsolutePath());
        } else if (!imgFile.canRead()) {
            System.out.println("No READ perms for file at: " + imgFile.getAbsolutePath());
        } else {
            System.out.println("Loading valid image file from: " + imgFile.getAbsolutePath());
        }

        // load the image
        try {
            originalImage = ImageIO.read(imgFile);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.out.println("HERE");
        }

        // convert image to correct color space
        if (!originalImage.getColorModel().equals(BufferedImage.TYPE_INT_RGB)) {
            BufferedImage rgbImg = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(),
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = rgbImg.createGraphics();
            g2d.drawImage(originalImage, 0, 0, null);
            g2d.dispose();
            originalImage = rgbImg;
        }

        // get the width and height
        width = originalImage.getWidth();
        height = originalImage.getHeight();

        // get colors and put them into separate arrays
        int[] colorArray = originalImage.getRGB(0, 0, width, height, null, 0, width);
        red = new int[height][width];
        green = new int[height][width];
        blue = new int[height][width];

        // separate out the colors
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                red[i][j] = (colorArray[i * width + j] >>> 16) & 0xFF;
                green[i][j] = (colorArray[i * width + j] >>> 8) & 0xFF;
                blue[i][j] = (colorArray[i * width + j] >>> 0) & 0xFF;
            }
        }
    }

    // Displays the image on the screen
    public void showImage() {
        updateColors();
        JLabel labelImage = new JLabel(new ImageIcon(originalImage));
        frame.getContentPane().add(labelImage);

        JScrollPane scroller = new JScrollPane(labelImage, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        frame.getContentPane().add(scroller);

        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }

    // Saves the image to the given file name (should end in .png)
    public void saveImage(String imageFileName) {
        if (!imageFileName.substring(imageFileName.length() - 4).equals(".png")) {
            System.out.println(imageFileName + " should end in .png");
        }
        updateColors();
        try {
            File outputfile = new File(imageFileName);
            ImageIO.write(originalImage, "png", outputfile);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    // recalculates the colors for the image from the rgb arrays
    private void updateColors() {
        int[] colorArray = new int[width * height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                // clamp the colors
                if (red[i][j] > 255) {
                    red[i][j] = 255;
                }
                if (green[i][j] > 255) {
                    green[i][j] = 255;
                }
                if (blue[i][j] > 255) {
                    blue[i][j] = 255;
                }
                if (red[i][j] < 0) {
                    red[i][j] = 0;
                }
                if (green[i][j] < 0) {
                    green[i][j] = 0;
                }
                if (blue[i][j] < 0) {
                    blue[i][j] = 0;
                }
                // combine colors into single rgb int and put them into the color array
                colorArray[i * width + j] = ((red[i][j]) << 16) + ((green[i][j]) << 8) + ((blue[i][j]) << 0);
            }
        }
        originalImage.setRGB(0, 0, width, height, colorArray, 0, width);
    }
}

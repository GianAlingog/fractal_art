// Fractal Art by Gian Alingog
// ***************
// Project 3 - Algorithmic Art Project
// 02/17/2024

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;
import java.awt.Color;

class FractalArt extends JPanel {
    // *****Variables*****

    // DO Change

    private static final int BIGGEST_POWER = 12;
    private static final int SMALLEST_POWER = 3;

    // DO NOT Change
    private static final int BIGGEST_LENGTH = (int) Math.pow(2, BIGGEST_POWER);
    private static final int SMALLEST_LENGTH = (int) Math.pow(2, SMALLEST_POWER);

    // You may switch to this format if you prefer working with bases of two in base ten.
    // private static final int BIGGEST_LENGTH = 512;
    // private static final int SMALLEST_LENGTH = 2;

    // private static final int BIGGEST_POWER = (int) (Math.log(BIGGEST_LENGTH) / Math.log(2));
    // private static final int SMALLEST_POWER = (int) (Math.log(SMALLEST_LENGTH) / Math.log(2));

    private static final int POWER_DIFFERENCE = BIGGEST_POWER - SMALLEST_POWER;

    // Call the recursive method with our base case
    public void paintComponent(Graphics g) {
        
        // Base Square in the middle of the screen (1280x720 resolution)
        createSquare(1280 / 2, 720 / 2, BIGGEST_LENGTH, g);
    }

    // This is our recursive function, it creates a square then four more squares
    // which are half the size and are centered on its corners.
    private void createSquare(int x, int y, int len, Graphics g) {
        // If the current square is still greater than SMALLEST_LENGTH, continue
        // Else, do nothing (stop recursion)
        if (len >= SMALLEST_LENGTH) {
            // Creates a square with x, y defining its center.
            Rectangle base = new Rectangle(x, y, len, len);
            drawSquare(base, g);

            // Four more recursively generated squares
            createSquare(base.x + len / 2, base.y + len / 2, len / 2, g); // top right
            createSquare(base.x + len / 2, base.y - len / 2, len / 2, g); // bottom right
            createSquare(base.x - len / 2, base.y + len / 2, len / 2, g); // top left
            createSquare(base.x - len / 2, base.y - len / 2, len / 2, g); // bottom left
        }
    }

    // Draws the passed in square to the screen
    private void drawSquare(Rectangle square, Graphics g) {
        // Colors are randomized based on the length of the square and an offset

        Random rnd = new Random(System.currentTimeMillis()); // Randomizer with (relatively) random seed generator
        double colorOffset = rnd.nextDouble();

        // ((Math.log(square.width) / Math.log(2)) - SMALLEST_POWER) / POWER_DIFFERENCE Analysis:
        // - 2^POWER_DIFFERENCE, is the difference in bases of two of the largest and smallest square.
        // - Every square recursively generated has a length of base 2.
        // - Math.log(square.width) / Math.log(2) calculates which power of 2 the length of the current square is.
        // - Subtracting by SMALLEST_POWER adjusts for when the smallest power is not 0.
        // - Then, we divide by POWER_DIFFERENCE to find what the percentage of this power of 2 in relation to the max value.
        double percentage = ((Math.log(square.width) / Math.log(2)) - SMALLEST_POWER) / POWER_DIFFERENCE;

        // The method for converting a percentage value to rgb can be found here:
        // https://stackoverflow.com/a/30309719
        double red = Math.min(Math.max(0, 1.5 - Math.abs(1 - 4 * (percentage - 0.5))),1) + colorOffset;
        double green = Math.min(Math.max(0, 1.5 - Math.abs(1 - 4 * (percentage - 0.25))),1) + colorOffset;
        double blue = Math.min(Math.max(0, 1.5 - Math.abs(1 - 4 * (percentage))), 1) + colorOffset;

        // Update the square's color with the color we generated
        g.setColor(new Color(
                (float) ((red > 1) ? red - 1 : red),
                (float) ((green > 1) ? green - 1 : green),
                (float) ((blue > 1) ? blue - 1 : blue)));

        // Use this instead for single color
        // g.setColor(Color.black);

        // Draw rectangle onto screen
        // Offsets the x and y coordinates to draw it based off its center
        g.drawRect(square.x - square.width / 2, square.y - square.height / 2, square.width, square.height);
    }
}
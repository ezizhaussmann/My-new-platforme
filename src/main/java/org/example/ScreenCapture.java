package org.example;

import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class ScreenCapture {
    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame();
        captureAndSaveScreenshot(frame);
    }

    private static BufferedImage captureAndSaveScreenshot(JFrame activeWindow) throws IOException {
        int width = Math.max(1024, activeWindow.getWidth()); // Example: set a minimum size if needed
        int height = Math.max(768, activeWindow.getHeight());

        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
        Rectangle bounds = new Rectangle(0, 0, width, height);

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics graphics = image.createGraphics();
        activeWindow.paint(graphics); // Paint the JFrame to the image
        graphics.dispose();

        File file = new File("screenshot.png");
        ImageIO.write(image, "png", file);

        return image;
    }
}


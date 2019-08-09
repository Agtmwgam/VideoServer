package com.tw.common;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

/**
 * @see https://stackoverflow.com/a/16880714/230513
 */
public class HeatTest {

    private static final int N = 256;
    private TexturePanel p = new TexturePanel();

    private void display() {
        JFrame f = new JFrame("HeatTest");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(p);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        new HeatWorker().execute();
    }

    private class TexturePanel extends JPanel {

        private TexturePaint paint;

        public void setTexture(TexturePaint tp) {
            this.paint = tp;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setPaint(paint);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(N, N);
        }
    }

    private class HeatWorker extends SwingWorker<TexturePaint, TexturePaint> {

        private final Random random = new Random();

        @Override
        protected TexturePaint doInBackground() throws Exception {
            BufferedImage image = new BufferedImage(N, N, BufferedImage.TYPE_INT_ARGB);
            TexturePaint paint = new TexturePaint(image, new Rectangle(N, N));
            int[] iArray = {0, 0, 0, 255};
            while (true) {
                WritableRaster raster = image.getRaster();
                for (int row = 0; row < N; row++) {
                    for (int col = 0; col < N; col++) {
                        iArray[0] = 255;
                        iArray[1] = (int) (128 + 32 * random.nextGaussian());
                        iArray[2] = 0;
                        raster.setPixel(col, row, iArray);
                    }
                }
                publish(paint);
                Thread.sleep(40); // ~25 Hz
            }
        }

        @Override
        protected void process(List<TexturePaint> list) {
            for (TexturePaint paint : list) {
                p.setTexture(paint);
                p.repaint();
            }
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HeatTest().display();
            }
        });
    }
}
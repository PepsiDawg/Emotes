package io.github.pepsidog.emotes;

import io.github.mrsperry.mcutils.ParticleColor;
import org.apache.commons.io.FilenameUtils;
import org.bukkit.entity.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class ImageUtils {
    private static List<String> validExtensions = Arrays.asList("png", "jpg", "jpeg");

    public static BufferedImage downloadImage(String url) {
        try {
            BufferedImage img = null;
            Image test = null;

            URL imgURL = new URL(url);
            img = ImageIO.read(imgURL);
            if(img.getHeight()*img.getWidth() > 128*128) { throw new Exception("Image is too large!"); }

            return img;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* https://stackoverflow.com/questions/2534116/how-to-convert-get-rgbx-y-integer-pixel-to-colorr-g-b-a-in-java */
    public static ParticleColor[][] getPixelParticleColors(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        int pixelLength = (image.getAlphaRaster() != null) ? 4 : 3;
        ParticleColor[][] colors = new ParticleColor[width][height];

        for(int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgba = image.getRGB(x, y);
                int r = (rgba>>16)&0xFF;
                int g = (rgba>>8)&0xFF;
                int b = (rgba>>0)&0xFF;
                int a = (rgba>>24)&0xFF;

                if(a > 0.5) {
                    colors[x][height - y - 1] = ParticleColor.fromDecimal(r, g, b);
                } else {
                    colors[x][height - y - 1] = null;
                }
            }
        }

        return colors;
    }

    public static boolean isImage(File file) {
        return validExtensions.contains(FilenameUtils.getExtension(file.getName()));
    }
}
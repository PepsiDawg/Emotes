package io.github.pepsidog.emotes;

import io.github.mrsperry.mcutils.ParticleColor;
import org.apache.commons.io.FilenameUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class EmoteManager {
    private Map<String, Emote> emoteList;
    private int maxWidth = 32;
    private int maxHeight = 32;
    private double density = 0.1;

    public EmoteManager() {
        emoteList = new HashMap<>();
        getSizeRestrictions();
    }

    private void getSizeRestrictions() {
        FileConfiguration config = EmotesPlugin.getInstance().getConfig();

        if(config.isInt("size.maxWidth")) {
            this.maxWidth = config.getInt("size.maxWidth");
        } else {
            Bukkit.getServer().getLogger().warning("No max width found in the config. Defaulting to 32");
        }

        if(config.isInt("size.maxHeight")) {
            this.maxHeight = config.getInt("size.maxWidth");
        } else {
            Bukkit.getLogger().warning("No max height found in the config. Defaulting to 32");
        }

        if(config.isDouble("size.density")) {
            this.density = config.getDouble("size.density");
        } else {
            Bukkit.getLogger().warning("No density found in the config. Defaulting to 0.1");
        }

        Bukkit.getLogger().info("Using max dimensions of (" + maxWidth + "x" + maxHeight + ")");
    }

    public void registerEmote(File file) throws Exception {
        if(ImageUtils.isImage(file)) {
            BufferedImage image = ImageIO.read(file);

            if(image.getWidth() <= this.maxWidth && image.getHeight() <= this.maxHeight) {
                ParticleColor[][] pixels = ImageUtils.getPixelParticleColors(image);
                String name = FilenameUtils.removeExtension(file.getName());

                emoteList.put(name, new Emote(name, image.getWidth(), image.getHeight(), pixels));
            } else {
                throw new Exception("Image " + file.getName() + " is too large! Max size allowed is (" + this.maxWidth + "x" + this.maxHeight + ")");
            }
        } else {
            throw new Exception(file.getName() + " is not recognized as a valid image file!");
        }
    }

    public boolean isEmote(String name) {
        return this.emoteList.containsKey(name);
    }

    public Emote getEmote(String name) {
        return this.emoteList.get(name);
    }

    public double getDensity() {
        return this.density;
    }

    public Set<String> getAvaliableEmotes() {
        return this.emoteList.keySet();
    }
}

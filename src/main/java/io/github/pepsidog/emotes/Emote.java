package io.github.pepsidog.emotes;

import io.github.mrsperry.mcutils.ParticleColor;

public class Emote {
    private String name;
    private int width;
    private int height;
    private ParticleColor[][] pixels;

    public Emote(String name, int width, int height, ParticleColor[][] pixels) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.pixels = pixels;
    }

    public String getName() {
        return this.name;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public ParticleColor[][] getPixels() {
        return this.pixels;
    }
}

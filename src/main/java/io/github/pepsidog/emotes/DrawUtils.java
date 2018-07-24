package io.github.pepsidog.emotes;

import io.github.mrsperry.mcutils.ParticleColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class DrawUtils {
    private static final double cs = Math.cos(Math.PI/2);
    private static final double sn = Math.sin(Math.PI/2);

    public static void drawEmote(Emote emote, Player player) {
        Vector dir = player.getLocation().getDirection().normalize();
        Location center = player.getLocation().clone().add(0, 2.5, 0);
        double density = EmotesPlugin.getInstance().getEmoteManager().getDensity();
        double px = dir.getX() * cs - dir.getZ() * sn;
        double pz = dir.getX() * sn + dir.getZ() * cs;
        double factor = emote.getWidth() * density / 2;

        dir.setX(px);
        dir.setZ(pz);
        dir = dir.multiply(density);

        for(int y = 0; y < emote.getHeight(); y++) {
            Location start = center.clone().add(px*factor, y*density, pz*factor);
            for(int x = 0; x < emote.getWidth(); x++) {
                ParticleColor color = emote.getPixels()[x][y];

                if(color != null) {
                    player.getWorld().spawnParticle(Particle.REDSTONE, start, 0, color.red, color.green, color.blue, 1);
                }
                start.subtract(dir.getX(), 0, dir.getZ());
            }
        }
    }

    private static double map(double num, double min, double max, double min2, double max2) {
        return ((num-min) / (max-min)) * (max2-min2) + min2;
    }
}

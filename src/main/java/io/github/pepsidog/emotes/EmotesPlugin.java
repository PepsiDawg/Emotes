package io.github.pepsidog.emotes;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class EmotesPlugin extends JavaPlugin {
    private static EmotesPlugin self;
    private EmoteManager manager;

    @Override
    public void onEnable() {
        self = this;

        File emotes = new File(getDataFolder(), "emotes");
        if(!emotes.exists()){
            emotes.mkdirs();
        }

        saveDefaultConfig();
        manager = new EmoteManager();
        registerEmotes(emotes);
        EmoteCommands emoteCommands = new EmoteCommands();
        getCommand("emotelist").setExecutor(emoteCommands);
        getCommand("emote").setExecutor(emoteCommands);
    }

    private void registerEmotes(File emoteFolder) {
        try {
            int count = 0;
            for(File emote : emoteFolder.listFiles()) {
                manager.registerEmote(emote);
                count++;
            }
            getLogger().info("Registered " + count + " emotes!");
        } catch (Exception e) {
            getLogger().warning(e.getMessage());
        }
    }

    public static EmotesPlugin getInstance() {
        return self;
    }

    public EmoteManager getEmoteManager() { return this.manager; }
}

package io.github.pepsidog.emotes;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class EmoteCommands implements CommandExecutor {
    private final double cs = Math.cos(Math.PI/2);
    private final double sn = Math.sin(Math.PI/2);
    private EmoteManager manager;

    public EmoteCommands() {
        manager = EmotesPlugin.getInstance().getEmoteManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player)) { sender.sendMessage(ChatColor.RED + "Only players can use that command"); return true; }
        Player player = (Player) sender;

        switch(command.getName().toLowerCase()) {
            case "emote":
                emote(player, args);
                break;
            case "emotelist":
                emotelist(player);
                break;
        }

        return true;
    }

    private void emotelist(Player player) {
        String emotes = StringUtils.join(EmotesPlugin.getInstance().getEmoteManager().getAvaliableEmotes(), ", ");
        player.sendMessage(ChatColor.GREEN + emotes);
    }

    private void emote(Player player, String[] args) {
        if(args.length != 1) { player.sendMessage(ChatColor.GRAY + "/emote [emote]"); return; }

        if(manager.isEmote(args[0])) {
            new BukkitRunnable() {
                int age = 0;

                @Override
                public void run() {
                    if(age > 20) {
                        this.cancel();
                        return;
                    }
                    DrawUtils.drawEmote(manager.getEmote(args[0]), player);
                    age+=4;
                }
            }.runTaskTimer(EmotesPlugin.getInstance(), 0, 4);
        } else {
            player.sendMessage(ChatColor.RED + "Unknown emote " + args[0]);
        }
    }
}

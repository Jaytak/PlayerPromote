package com.jaytak.playerPromote;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlayerPromote extends JavaPlugin {
    private LuckPerms luckPerms;
    private String playerTitle = "§2§l[§r§2Player Promote§2§l]§r ";

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getCommand("agree").setExecutor(new AgreeCommand());
        luckPerms = getServer().getServicesManager().load(LuckPerms.class);
        if (luckPerms == null) {
            getLogger().severe(playerTitle + "LuckPerms not found! This plugin requires LuckPerms.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        int pluginId = 23577;
        Metrics metrics = new Metrics(this, pluginId);
        getLogger().info("PlayerPromote by JayTAK Enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("PlayerPromote by JayTAK Disabled!");

    }

    public class AgreeCommand implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(playerTitle + "Only players can use this command.");
                return true;
            }

            Player player = (Player) sender;

            if (!player.hasPermission("agreeplugin.use")) {
                player.sendMessage(playerTitle + "You do not have permission to use this command.");
                return true;
            }

            User user = luckPerms.getUserManager().getUser(player.getUniqueId());

            if (user == null) {
                player.sendMessage(playerTitle + "Error fetching your data. Try again later.");
                return true;
            }

            if (user.getPrimaryGroup().equals("default")) {

                user.data().add(Node.builder("group.player").build());

                luckPerms.getUserManager().saveUser(user);

                player.sendMessage(playerTitle + "You have been promoted to the Player group! GLHF!");
            } else {
                player.sendMessage(playerTitle + "You are already in the group Player, or higher.");
            }

            return true;
        }
    }
}

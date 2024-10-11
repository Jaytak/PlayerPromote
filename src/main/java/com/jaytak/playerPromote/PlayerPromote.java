package com.jaytak.playerPromote;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class PlayerPromote extends JavaPlugin implements Listener {
    private LuckPerms luckPerms;
    private String playerTitle = "§2§l[§r§2Player Promote§2§l]§r ";
    private String newPlayerMessage = "";
    private String promoteMessage = "";
    private String alreadyPromotedMessage = "";
    private String initalGroup = "default";
    private String promoteToGroup = "group.player";

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        loadConfig();
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

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!event.getPlayer().hasPlayedBefore() && !Objects.equals(newPlayerMessage, "DISABLED")) {
            event.getPlayer().sendMessage(newPlayerMessage);
        }
    }

    private void loadConfig(){
        playerTitle = getConfig().getString("PlayerTitle", "§2§l[§r§2Player Promote§2§l]§r ");
        newPlayerMessage = getConfig().getString("NewPlayerMessage", "This is the first time you have joined. Please read the rules at spawn and you can promote yourself to player once you have agreed to the rules.");
        promoteMessage = getConfig().getString("PromoteMessage", "You have been promoted to the Player group! GLHF!");
        alreadyPromotedMessage = getConfig().getString("AlreadyPromotedMessage","You are already in the group Player, or higher.");
        initalGroup = getConfig().getString("DefaultGroup", "default");
        promoteToGroup = getConfig().getString("PromoteToGroup", "group.player");
    }


    public class AgreeCommand implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(playerTitle + "Only players can use this command.");
                return true;
            }

            Player player = (Player) sender;

            if (command.getName().equalsIgnoreCase("agree")) {
                if (!player.hasPermission("agreeplugin.use")) {
                    player.sendMessage(playerTitle + "You do not have permission to use this command.");
                    return true;
                }

                User user = luckPerms.getUserManager().getUser(player.getUniqueId());

                if (user == null) {
                    player.sendMessage(playerTitle + "Error fetching your data. Try again later.");
                    return true;
                }

                if (user.getPrimaryGroup().equals(initalGroup)) {
                    user.data().add(Node.builder(promoteToGroup).build());
                    luckPerms.getUserManager().saveUser(user);
                    player.sendMessage(playerTitle + promoteMessage);
                }
                else {
                    player.sendMessage(playerTitle + alreadyPromotedMessage);
                }
            }
            return true;
        }
    }
}

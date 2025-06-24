package com.denied403.hardcoursecheckpoints.Points;

import com.denied403.hardcoursecheckpoints.HardcourseCheckpoints;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PointsManager {

    private final HardcourseCheckpoints plugin;
    private final FileConfiguration config;

    public PointsManager(HardcourseCheckpoints plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    public int getPoints(UUID playerUUID) {
        return config.getInt("points." + playerUUID.toString(), 0);
    }

    public void setPoints(UUID playerUUID, int points) {
        config.set("points." + playerUUID.toString(), points);
        plugin.saveConfig();
    }

    public void addPoints(UUID playerUUID, int amount) {
        int current = getPoints(playerUUID);
        setPoints(playerUUID, current + amount);
    }

    public void removePoints(UUID playerUUID, int amount) {
        int current = getPoints(playerUUID);
        int newAmount = Math.max(0, current - amount);
        setPoints(playerUUID, newAmount);
    }

    // Send the points in the action bar above the hotbar
    public void sendPointsActionBar(Player player) {
        int points = getPoints(player.getUniqueId());
        String message = ChatColor.RED + "Points: " + ChatColor.WHITE + points;
        player.sendActionBar(message);
    }
}

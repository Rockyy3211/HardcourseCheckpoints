package com.denied403.hardcoursecheckpoints.Points;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class JumpBoost implements Listener {

    private boolean isJumpBoostBoot(ItemStack item) {
        if (item == null || item.getType() != Material.LEATHER_BOOTS || !item.hasItemMeta()) return false;
        String name = ChatColor.stripColor(item.getItemMeta().getDisplayName());
        return name != null && name.equalsIgnoreCase("Jump Boost");
    }

    @EventHandler
    public void onRightClickJumpBoot(PlayerInteractEvent event) {
        if (!event.hasItem()) return;

        ItemStack item = event.getItem();
        if (!isJumpBoostBoot(item)) return;

        Player player = event.getPlayer();
        event.setCancelled(true);

        // Remove the item
        item.setAmount(item.getAmount() - 1);
        player.getInventory().setItemInMainHand(item.getAmount() > 0 ? item : null);

        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, 100, 0));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lHARDCOURSE &fYou have used jump boost!"));
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1.2f);
    }
}

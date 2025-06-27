package com.denied403.hardcoursecheckpoints.Points;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class JumpBoostListener implements Listener {

    private final ItemStack referenceBoots;

    public JumpBoostListener() {
        // Prepare a reference boots item to compare with
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta meta = (LeatherArmorMeta) boots.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&lJump Boost"));
            meta.setColor(Color.LIME);
            meta.setUnbreakable(true);
            boots.setItemMeta(meta);
        }
        this.referenceBoots = boots;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        ItemStack boots = player.getInventory().getBoots();

        if (boots != null && boots.isSimilar(referenceBoots)) {
            // Add Jump Boost if not already applied
            if (!player.hasPotionEffect(PotionEffectType.JUMP_BOOST)) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, 60, 0, true, false, false));
            }
        } else {
            // Remove Jump Boost if they're not wearing the special boots
            if (player.hasPotionEffect(PotionEffectType.JUMP_BOOST)) {
                player.removePotionEffect(PotionEffectType.JUMP_BOOST);
            }
        }
    }
}

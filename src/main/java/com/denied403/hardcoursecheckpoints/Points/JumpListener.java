package com.denied403.hardcoursecheckpoints.Points;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import com.denied403.hardcoursecheckpoints.HardcourseCheckpoints;

public class JumpListener implements Listener {

    private final PointsShop shop;
    private final HardcourseCheckpoints plugin;

    public JumpListener(PointsShop shop, HardcourseCheckpoints plugin) {
        this.shop = shop;
        this.plugin = plugin;
    }

    private boolean isWearingJumpBoots(Player player) {
        ItemStack boots = player.getInventory().getBoots();
        if (boots == null || boots.getType() != Material.LEATHER_BOOTS || !boots.hasItemMeta()) return false;

        if (!(boots.getItemMeta() instanceof LeatherArmorMeta)) return false;
        LeatherArmorMeta meta = (LeatherArmorMeta) boots.getItemMeta();

        return ChatColor.stripColor(meta.getDisplayName()).equalsIgnoreCase("Jump Boost")
                && meta.getColor().asRGB() == org.bukkit.Color.LIME.asRGB();
    }

    @EventHandler
    public void onPlayerJump(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        // Detect jump (going upward from a previous ground position)
        if (event.getFrom().getY() + 0.01 >= event.getTo().getY()) return; // not moving up
        if (!player.isOnGround()) return; // must be just leaving the ground
        if (!isWearingJumpBoots(player)) return;

        // Prevent triggering again
        ItemStack boots = player.getInventory().getBoots();
        if (boots == null) return;

        new BukkitRunnable() {
            @Override
            public void run() {
                player.getInventory().setBoots(null);

                PotionEffectType jumpBoost = PotionEffectType.getByName("JUMP");
                if (jumpBoost != null) {
                    player.removePotionEffect(jumpBoost);
                }

                player.getWorld().spawnParticle(Particle.CLOUD, player.getLocation().add(0, 1, 0), 10);
                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1f, 1f);
                player.sendMessage(ChatColor.YELLOW + "Jump Boost consumed.");
            }
        }.runTaskLater(plugin, 30L); // 30 ticks = 1.5 seconds
    }
}

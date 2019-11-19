package com.YanniSperon.MC;

import com.YanniSperon.MC.Lootboxes.Lootbox;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class LootboxEventListener implements Listener {
    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
            if (itemInMainHand != null && itemInMainHand.getType() != null && itemInMainHand.getType() != Material.AIR) {
                if (itemInMainHand.getItemMeta().getDisplayName().contains(Global.LootboxIndicator)) {
                    player.getInventory().setItemInMainHand(null);
                    event.setCancelled(true);
                    if (itemInMainHand.getAmount() > 1) {
                        itemInMainHand.setAmount(itemInMainHand.getAmount() - 1);
                        player.getInventory().setItemInMainHand(itemInMainHand);
                    }

                    Lootbox box = null;

                    for (Lootbox lootboxValue : Global.Lootboxes.values()) {
                        if (itemInMainHand.getItemMeta().getDisplayName().toLowerCase().equalsIgnoreCase(lootboxValue.GetName().toLowerCase())) {
                            box = lootboxValue;
                        }
                    }

                    if (box == null) {
                        Global.SendInvalidLootbox(player, itemInMainHand.getItemMeta().getDisplayName());
                    } else {
                        box.Open(player);
                    }
                }
            }
        }
    }
}

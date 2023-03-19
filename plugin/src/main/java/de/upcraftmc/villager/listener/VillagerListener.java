/*
 * Copyright (c) 2023 - present | LuciferMorningstarDev <contact@lucifer-morningstar.dev>
 * Copyright (c) 2023 - present | upcraftmc.com <contact@upcraftmc.com>
 * Copyright (c) 2023 - present | upcraftmc.com team and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package de.upcraftmc.villager.listener;

import de.upcraftmc.villager.UpCraftAntiVillagerLagPlugin;
import de.upcraftmc.villager.utils.ComponentSerializer;
import de.upcraftmc.villager.utils.VillagerLevelUtils;
import de.upcraftmc.villager.utils.VillagerUtiils;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.TradeSelectEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

/**
 * AntiVillagerLag; de.upcraftmc.villager.listener:VillagerListener
 *
 * @author <a href="https://github.com/LuciferMorningstarDev">LuciferMorningstarDev</a>
 * @since 19.03.2023
 */
public class VillagerListener implements Listener {

    private UpCraftAntiVillagerLagPlugin plugin;

    public VillagerListener(UpCraftAntiVillagerLagPlugin plugin) {
        this.plugin = plugin;
    }

    public void sanityChecks(Villager vil, long currentTime) {
        long vilLevelCooldown = VillagerUtiils.getLevelCooldown(vil, this.plugin);
        if (vilLevelCooldown > currentTime + VillagerLevelUtils.cooldown * 2L) {
            VillagerUtiils.setLevelCooldown(vil, this.plugin, Long.valueOf(VillagerLevelUtils.cooldown));
        }
        long vilTime = VillagerUtiils.getRestockTime(vil, this.plugin);
        if (vilTime > vil.getWorld().getFullTime()) {
            VillagerUtiils.setNewRestockTime(vil, this.plugin);
        }
    }

    @EventHandler
    public void inventoryMove(InventoryClickEvent event) {
        if (!(event.getInventory().getHolder() instanceof Villager)) {
            return;
        }

        Villager vil = (Villager) event.getInventory().getHolder();
        if (!vil.hasAI()) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        event.setCancelled(true);
        player.closeInventory();

        player.sendMessage(ComponentSerializer.etAndHEX.deserialize("&cZum Traden muss der Villager deaktiviert sein."));
    }

    @EventHandler
    public void villagerTradeClick(TradeSelectEvent event) {
        if (!(event.getInventory().getHolder() instanceof Villager)) {
            return;
        }

        Villager vil = (Villager) event.getInventory().getHolder();
        if (!vil.hasAI()) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        event.setCancelled(true);
        player.closeInventory();

        player.sendMessage(ComponentSerializer.etAndHEX.deserialize("&cZum Traden muss der Villager deaktiviert sein."));
    }

    @EventHandler
    public void afterTrade(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        if (event.getInventory().getHolder() instanceof org.bukkit.entity.WanderingTrader) {
            return;
        }
        if (event.getInventory().getType() != InventoryType.MERCHANT) {
            return;
        }
        if (event.getInventory().getHolder() == null) {
            return;
        }
        Villager vil = (Villager) event.getInventory().getHolder();
        if (!vil.hasAI()) {
            return;
        }

        if (!VillagerUtiils.hasLevelCooldown(vil, this.plugin)) {
            VillagerUtiils.setLevelCooldown(vil, this.plugin, Long.valueOf(0L));
        }

        VillagerLevelUtils.callOn(vil, player, this.plugin);

    }

    @EventHandler
    public void rightClick(PlayerInteractEntityEvent e) {
        Player player = e.getPlayer();
        if (player.hasPermission("avl.disable")) {
            return;
        }
        if (!e.getRightClicked().getType().equals(EntityType.VILLAGER)) {
            return;
        }
        Villager vil = (Villager) e.getRightClicked();
        if (!VillagerUtiils.hasLevelCooldown(vil, this.plugin)) {
            VillagerUtiils.setLevelCooldown(vil, this.plugin, Long.valueOf(0L));
        }
        if (!VillagerUtiils.hasRestockTime(vil, this.plugin)) {
            VillagerUtiils.setNewRestockTime(vil, this.plugin);
        }
        long currentTime = System.currentTimeMillis() / 1000L;
        sanityChecks(vil, currentTime);

        long vilLevelCooldown = VillagerUtiils.getLevelCooldown(vil, this.plugin);
        long totalSeconds = vilLevelCooldown - currentTime;
        long sec = totalSeconds % 60L;

        if (!vil.hasAI() && vilLevelCooldown > currentTime) {
            player.sendMessage(ComponentSerializer.etAndHEX.deserialize(
                    "&cDer Villager levelt gerade. Du kannst den Villager in &e" +
                            sec + " Sekunde(n) &cwieder benutzen."
            ));
            vil.shakeHead();
            e.setCancelled(true);
            return;
        }

    }

}

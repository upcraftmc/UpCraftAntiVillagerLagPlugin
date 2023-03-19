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
import de.upcraftmc.villager.utils.VillagerUtiils;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Zombie;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * AntiVillagerLag; de.upcraftmc.villager.listener:ZombieDamageVillagerEvent
 *
 * @author <a href="https://github.com/LuciferMorningstarDev">LuciferMorningstarDev</a>
 * @since 19.03.2023
 */
public class ZombieDamageVillagerEvent implements Listener {

    private UpCraftAntiVillagerLagPlugin plugin;

    public ZombieDamageVillagerEvent(UpCraftAntiVillagerLagPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCancelVillagerDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Villager)
                || (!(event.getDamager() instanceof Zombie) && !(event.getDamager() instanceof ZombieVillager))) {
            return;
        }

        Villager vil = (Villager) event.getEntity();
        if (VillagerUtiils.isProtected(vil, this.plugin) && VillagerUtiils.shouldBeDisabled(vil, plugin)) {
            event.setCancelled(true);
        }
    }

}

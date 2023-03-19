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

package de.upcraftmc.villager.commands;

import de.upcraftmc.villager.UpCraftAntiVillagerLagPlugin;
import de.upcraftmc.villager.utils.ComponentSerializer;
import de.upcraftmc.villager.utils.VillagerUtiils;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import java.util.List;

/**
 * AntiVillagerLag; de.upcraftmc.villager.commands:InfoCommand
 *
 * @author <a href="https://github.com/LuciferMorningstarDev">LuciferMorningstarDev</a>
 * @since 19.03.2023
 */
public class InfoCommand implements AVLSubCommand {

    private UpCraftAntiVillagerLagPlugin plugin;

    public InfoCommand(UpCraftAntiVillagerLagPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean run(final Player sender, final List subArgs) {
        Entity target = sender.getTargetEntity(5);
        if (target == null || !(target instanceof Villager)) {
            sender.sendMessage(ComponentSerializer.etAndHEX.deserialize("&cDu schaust keinen Villager an oder bist mehr als &e5 Block &c entfernt."));
            return false;
        }

        Villager v = (Villager) target;

        String stat = v.hasAI() ? "&caktiv" : "&ainaktiv";
        String zombie = VillagerUtiils.isProtected(v, plugin) ? "&aaktiv" : "&cinaktiv";
        String ignore = VillagerUtiils.ignore(v, plugin) ? "&cinaktiv" : "&aaktiv";

        sender.sendMessage(ComponentSerializer.etAndHEX.deserialize(String.join("\n", List.of(
                "&r&7============ &aAnti&bVillager&cLag &7============",
                "&r  &7» Villager &7[&6" + v.getName() + "&7] &7(&9" + v.getUniqueId() + "&7)",
                "&r  &7» UmgebungsChecks: " + ignore,
                "&r  &7» Location: " + loc(v.getLocation()),
                "&r  &7» AI: " + stat,
                "&r  &7» Zombieschutz: " + stat,
                "&r&7============ &aAnti&bVillager&cLag &7============"
        ))));
        return false;
    }

    private String loc(Location v) {
        return "&9" + v.getWorld() + " &7- &a" + v.getX() + "&7, &a" + v.getY() + "&7, &a" + v.getZ();
    }

}

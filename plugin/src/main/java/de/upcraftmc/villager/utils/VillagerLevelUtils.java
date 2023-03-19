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

package de.upcraftmc.villager.utils;

import de.upcraftmc.villager.UpCraftAntiVillagerLagPlugin;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * AntiVillagerLag; de.upcraftmc.villager.utils:VLM
 *
 * @author <a href="https://github.com/LuciferMorningstarDev">LuciferMorningstarDev</a>
 * @since 19.03.2023
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VillagerLevelUtils {

    public static long cooldown = 5L;

    public static void callOn(Villager vil, Player player, UpCraftAntiVillagerLagPlugin plugin) {
        int vilLevel = vil.getVillagerLevel();
        long newLevel = VillagerUtiils.villagerEXP(vil);
        long currentTime = System.currentTimeMillis() / 1000L;
        long vilLevelCooldown = VillagerUtiils.getLevelCooldown(vil, plugin);
        long totalSeconds = vilLevelCooldown - currentTime;
        long sec = totalSeconds % 60L;
        if (vilLevelCooldown > currentTime) {
            player.sendMessage(ComponentSerializer.etAndHEX.deserialize(
                    "&cDer Villager levelt gerade. Du kannst den Villager in &e" +
                            sec + " Sekunde(n) &cwieder benutzen."
            ));
            return;
        }
        if (vilLevel < newLevel) {
            VillagerUtiils.setLevelCooldown(vil, plugin, Long.valueOf(cooldown));
            vil.addPotionEffect(
                    new PotionEffect(
                            PotionEffectType.SLOW,
                            (int) (cooldown * 20L) + 20,
                            120,
                            false, false
                    )
            );
        } else {
            return;
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> vil.setAware(false), 100L);
    }

}

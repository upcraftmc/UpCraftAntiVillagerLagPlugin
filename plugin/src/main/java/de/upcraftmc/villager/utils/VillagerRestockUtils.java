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
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import java.util.Iterator;
import java.util.List;

/**
 * AntiVillagerLag; de.upcraftmc.villager.utils:VillagerRestockUtils
 *
 * @author <a href="https://github.com/LuciferMorningstarDev">LuciferMorningstarDev</a>
 * @since 19.03.2023
 */
public class VillagerRestockUtils {

    private static List<Long> restockTimes;

    public static void setRestockTimes(UpCraftAntiVillagerLagPlugin pl) {
        restockTimes = pl.getConfig().getLongList("RestockTimes");
    }

    public static void restockMessage(long timeTillNextRestock, Player player) {
        long totalsec = timeTillNextRestock / 20L;
        long sec = totalsec % 60L;
        long min = (totalsec - sec) / 60L;
        player.sendMessage(ComponentSerializer.etAndHEX.deserialize("Der Villager ist in &e" + min + "Minute(n) &cwieder aufgefüllt."));
    }

    public static boolean handleRestock(Villager vil, long currDayTimeTick, UpCraftAntiVillagerLagPlugin plugin) {
        long curTick = vil.getWorld().getFullTime();
        long currentDay = curTick - currDayTimeTick;
        long vilTick = VillagerUtiils.getRestockTime(vil, plugin);
        for (Iterator<Long> iterator = restockTimes.iterator(); iterator.hasNext(); ) {
            long restockTime = ((Long) iterator.next()).longValue();
            long todayRestock = currentDay + restockTime;
            if (curTick >= todayRestock && vilTick < todayRestock) {
                VillagerUtiils.restock(vil);
                VillagerUtiils.setNewRestockTime(vil, plugin);
                return true;
            }
        }
        return false;
    }

}

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

package de.upcraftmc.villager;

import de.upcraftmc.villager.commands.AVLCommand;
import de.upcraftmc.villager.listener.VillagerListener;
import de.upcraftmc.villager.listener.ZombieDamageVillagerEvent;
import de.upcraftmc.villager.utils.VillagerRestockUtils;
import de.upcraftmc.villager.utils.VillagerUtiils;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * AntiVillagerLag; de.upcraftmc.villager:UpCraftAntiVillagerLagPlugin
 *
 * @author <a href="https://github.com/LuciferMorningstarDev">LuciferMorningstarDev</a>
 * @since 16.03.2023
 */
public class UpCraftAntiVillagerLagPlugin extends JavaPlugin {
    @Getter
    private static UpCraftAntiVillagerLagPlugin instance;

    @Override
    public void onLoad() {
        if(instance != null) {
            throw new RuntimeException("UpCraftAntiVillagerLagPlugin can only been initialized once.");
        }
        instance = this;
    }

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        VillagerRestockUtils.setRestockTimes(this);
        Bukkit.getPluginManager().registerEvents(new ZombieDamageVillagerEvent(this), this);
        Bukkit.getPluginManager().registerEvents(new VillagerListener(this), this);
        Bukkit.getCommandMap().register("avl", new AVLCommand(this));
        VillagerUtiils.startDisableLoop(this);
    }

    @Override
    public void onDisable() {

    }

}

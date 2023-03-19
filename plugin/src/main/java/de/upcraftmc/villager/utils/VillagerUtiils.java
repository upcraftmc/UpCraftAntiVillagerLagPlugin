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
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

/**
 * AntiVillagerLag; de.upcraftmc.villager.utils:VillagerUtiils
 *
 * @author <a href="https://github.com/LuciferMorningstarDev">LuciferMorningstarDev</a>
 * @since 19.03.2023
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VillagerUtiils {

    private static final String SHOULD_BE_DISABLED_KEY = "shouldBeDisabled";
    private static final String ZOMBIE_PROTECT_KEY = "ZombieProtect";
    private static final String LEVEL_COOLDOWN_KEY = "LevelCooldown";
    private static final String RESTOCK_TIME_KEY = "RestockTime";

    private static final String IGNORE_KEY = "ignore";

    public static long villagerEXP(Villager vil) {
        int vilEXP = vil.getVillagerExperience();
        if (vilEXP >= 250) {
            return 5L;
        }
        if (vilEXP >= 150) {
            return 4L;
        }
        if (vilEXP >= 70) {
            return 3L;
        }
        if (vilEXP >= 10) {
            return 2L;
        }
        return 1L;
    }

    public static void setShouldBeDisabled(Villager v, UpCraftAntiVillagerLagPlugin plugin, Boolean disable) {
        v.setAI(!disable);
        PersistentDataContainer container = v.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, SHOULD_BE_DISABLED_KEY);
        container.set(key, PersistentDataType.STRING, disable.toString());
    }

    public static boolean hasShouldBeDisabled(Villager v, UpCraftAntiVillagerLagPlugin plugin) {
        PersistentDataContainer container = v.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, SHOULD_BE_DISABLED_KEY);
        return container.has(key, PersistentDataType.STRING);
    }

    public static boolean shouldBeDisabled(Villager v, UpCraftAntiVillagerLagPlugin plugin) {
        if (!hasShouldBeDisabled(v, plugin)) {
            return false;
        }
        PersistentDataContainer container = v.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, SHOULD_BE_DISABLED_KEY);
        return Boolean.valueOf(container.get(key, PersistentDataType.STRING));
    }

    public static void setIgnore(Villager v, UpCraftAntiVillagerLagPlugin plugin, Boolean disabledByBlock) {
        PersistentDataContainer container = v.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, IGNORE_KEY);
        container.set(key, PersistentDataType.STRING, disabledByBlock.toString());
    }

    public static boolean hasIgnore(Villager v, UpCraftAntiVillagerLagPlugin plugin) {
        PersistentDataContainer container = v.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, IGNORE_KEY);
        return container.has(key, PersistentDataType.STRING);
    }

    public static boolean ignore(Villager v, UpCraftAntiVillagerLagPlugin plugin) {
        if (!hasIgnore(v, plugin)) {
            return false;
        }
        PersistentDataContainer container = v.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, IGNORE_KEY);
        return Boolean.valueOf(container.get(key, PersistentDataType.STRING));
    }

    public static void addProtected(Villager v, UpCraftAntiVillagerLagPlugin plugin) {
        PersistentDataContainer container = v.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, ZOMBIE_PROTECT_KEY);
        container.set(key, PersistentDataType.STRING, "protect");
    }

    public static boolean isProtected(Villager v, UpCraftAntiVillagerLagPlugin plugin) {
        PersistentDataContainer container = v.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, ZOMBIE_PROTECT_KEY);
        return container.has(key, PersistentDataType.STRING);
    }

    public static void removeProtected(Villager v, UpCraftAntiVillagerLagPlugin plugin) {
        PersistentDataContainer container = v.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, ZOMBIE_PROTECT_KEY);
        container.remove(key);
    }

    public static void setLevelCooldown(Villager v, UpCraftAntiVillagerLagPlugin plugin, Long cooldown) {
        PersistentDataContainer container = v.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, LEVEL_COOLDOWN_KEY);
        container.set(key, PersistentDataType.LONG, Long.valueOf(System.currentTimeMillis() / 1000L + cooldown.longValue()));
    }

    public static boolean hasLevelCooldown(Villager v, UpCraftAntiVillagerLagPlugin plugin) {
        PersistentDataContainer container = v.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, LEVEL_COOLDOWN_KEY);
        return container.has(key, PersistentDataType.LONG);
    }

    public static long getLevelCooldown(Villager v, UpCraftAntiVillagerLagPlugin plugin) {
        PersistentDataContainer container = v.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, LEVEL_COOLDOWN_KEY);
        return ((Long) container.get(key, PersistentDataType.LONG)).longValue();
    }

    public static void restock(Villager v) {
        List<MerchantRecipe> recipes = v.getRecipes();
        for (MerchantRecipe r : recipes) {
            r.setUses(0);
        }
    }

    public static void setNewRestockTime(Villager v, UpCraftAntiVillagerLagPlugin plugin) {
        PersistentDataContainer container = v.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, RESTOCK_TIME_KEY);
        container.set(key, PersistentDataType.LONG, Long.valueOf(v.getWorld().getFullTime()));
    }

    public static boolean hasRestockTime(Villager v, UpCraftAntiVillagerLagPlugin plugin) {
        PersistentDataContainer container = v.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, RESTOCK_TIME_KEY);
        return container.has(key, PersistentDataType.LONG);
    }

    public static long getRestockTime(Villager v, UpCraftAntiVillagerLagPlugin plugin) {
        PersistentDataContainer container = v.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, RESTOCK_TIME_KEY);
        return container.get(key, PersistentDataType.LONG).longValue();
    }

    public static void performVillagerCheck(Villager v, UpCraftAntiVillagerLagPlugin plugin) {
        if (ignore(v, plugin)) {
            return;
        }

        Location villagerLocation = v.getLocation();
        Block standsOn = villagerLocation.getBlock();
        Material standsOnMaterial = standsOn.getType();

        // blocks checker metal
        if (plugin.getConfig().getBoolean("ToggleableOptions.UseMetalBlocks", true)) {
            switch (standsOnMaterial) {
                case NETHERITE_BLOCK:
                case DIAMOND_BLOCK:
                case GOLD_BLOCK:
                case EMERALD_BLOCK:
                case IRON_BLOCK:
                case COPPER_BLOCK:
                case WAXED_COPPER_BLOCK:
                case OXIDIZED_COPPER: {
                    setShouldBeDisabled(v, plugin, true);
                }
                default:
                    setShouldBeDisabled(v, plugin, false);
                    break;
            }
        }

        // blocks checker other
        if (!shouldBeDisabled(v, plugin) && plugin.getConfig().getBoolean("ToggleableOptions.UseOtherBlocks", true)) {
            if (plugin.getConfig().contains("BlocksThatDisable")) {
                List<String> types = plugin.getConfig().getStringList("BlocksThatDisable").stream().map(s -> s.toLowerCase()).collect(Collectors.toList());
                if (types.contains(standsOnMaterial.toString().toLowerCase())) {
                    setShouldBeDisabled(v, plugin, true);
                }
            }
        }

        // name tag check
        if (!shouldBeDisabled(v, plugin) && plugin.getConfig().getBoolean("ToggleableOptions.UseNameTags", true)) {
            String vName = v.getName().replaceAll("(?i)[&§][0-9A-FK-ORX]]", "");
            if (plugin.getConfig().contains("NamesThatDisable") && plugin.getConfig().getStringList("NamesThatDisable").contains(vName)) {
                setShouldBeDisabled(v, plugin, true);
            }
        }

        // work station checker
        if (!shouldBeDisabled(v, plugin) && plugin.getConfig().getBoolean("ToggleableOptions.UseWorkStations", true)) {
            List<Material> blocksToCheck = new ArrayList<>();
            int radius = plugin.getConfig().getInt("WorkStationCheckRadius", 2);
            boolean willBeDisabled = false;
            for (String blockName : plugin.getConfig().getStringList("WorkstationsThatDisable")) {
                Material block = Material.getMaterial(blockName.toUpperCase());
                if (block != null) {
                    blocksToCheck.add(block);
                }
            }

            for (int x = -radius; x <= radius; x++) {
                for (int y = -radius; y <= radius; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        Location blockLocation = new Location(v.getWorld(), v.getLocation().getX() + x, v.getLocation().getY() + y, v.getLocation().getZ() + z);
                        if (blocksToCheck.contains(blockLocation.getBlock().getType())) {
                            setShouldBeDisabled(v, plugin, true);
                        }
                    }
                }
            }

        }

        if (shouldBeDisabled(v, plugin)) {
            v.setAI(false);
        } else {
            v.setAI(true);
        }
    }

    public static void startDisableLoop(UpCraftAntiVillagerLagPlugin plugin) {
        Timer disableVillsTimer = new Timer();
        disableVillsTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Bukkit.getWorlds().forEach(current -> {
                    new Thread(() -> {
                        Bukkit.getScheduler().runTask(plugin, () -> {

                            current.getLivingEntities().stream().filter(e -> (e instanceof Villager)).forEach(v -> {
                                try {
                                    if (!(v instanceof Villager)) {
                                        return;
                                    }
                                    performVillagerCheck((Villager) v, plugin);
                                } catch (Exception e) {
                                }
                            });

                        });
                    }, "disable-villagers-" + current.getName()).start();
                });
            }
        }, 1000 * 15, plugin.getConfig().getInt("lll", 10));
    }

}

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
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AntiVillagerLag; de.upcraftmc.villager.commands:AVLCommand
 *
 * @author <a href="https://github.com/LuciferMorningstarDev">LuciferMorningstarDev</a>
 * @since 19.03.2023
 */
public class AVLCommand extends Command {

    private UpCraftAntiVillagerLagPlugin plugin;

    private Map<String, AVLSubCommand> subCommands;

    public AVLCommand(UpCraftAntiVillagerLagPlugin plugin) {
        super("antivillagerlag");
        this.setAliases(List.of("avl"));
        this.subCommands = new HashMap<>();
        this.subCommands.put("reload", new ReloadCommand(plugin));
        this.subCommands.put("info", new InfoCommand(plugin));
        this.subCommands.put("enable", new EnableCommand(plugin));
        this.subCommands.put("disable", new DisableCommand(plugin));
        this.subCommands.put("zombie", new ZombieCommand(plugin));
    }

    static void help(CommandSender sender) {
        sender.sendMessage(ComponentSerializer.etAndHEX.deserialize(String.join("\n", List.of(
                "&r&7============ &aAnti&bVillager&cLag &7============",
                "&r  &7» &6/avl &binfo &7- Zeigt dir informationen über den Villager an den du ansiehst.",
                "&r  &7» &6/avl &aenable &7- Aktiviere den Villager den du ansiehst.",
                "&r  &7» &6/avl &cdisable &7- Deaktiviere den Villager den du ansiehst.",
                "&r  &7» &6/avl &2zombie &7- Aktiviert/Deaktiviert den Zombieschutz vom Villager den du ansiehst.",
                "&r  &7» &6/avl &2ignore &7- Aktiviert/Deaktiviert UmgebungsChecks vom Villager den du ansiehst.",
                "&r&7============ &aAnti&bVillager&cLag &7============"
        ))));
    }

    @Override
    public boolean execute(@NotNull final CommandSender sender, @NotNull final String commandLabel, final @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ComponentSerializer.etAndHEX.deserialize("&cDer Command funktioniert nur als Spieler."));
            return false;
        }
        if (args.length > 0) {
            String subCommand = args[0];
            AVLSubCommand sub = subCommands.get(subCommand);
            if (sub == null) {
                help(sender);
                return false;
            }
            return sub.run((Player) sender, List.of(args));
        }
        help(sender);
        return false;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull final CommandSender sender, @NotNull final String alias, final @NotNull String[] args) throws IllegalArgumentException {
        return Collections.emptyList();
    }

}

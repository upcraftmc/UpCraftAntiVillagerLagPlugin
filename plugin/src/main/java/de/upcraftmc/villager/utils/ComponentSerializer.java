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

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

/**
 * AntiVillagerLag; de.upcraftmc.villager.utils:ComponentSerializer
 *
 * @author <a href="https://github.com/LuciferMorningstarDev">LuciferMorningstarDev</a>
 * @since 19.03.2023
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ComponentSerializer {

    public static final LegacyComponentSerializer sectionAndHEX = LegacyComponentSerializer.builder().character('§').hexCharacter('#').hexColors().build();
    public static final LegacyComponentSerializer unusualSectionAndHEX = LegacyComponentSerializer
            .builder()
            .character('§')
            .hexCharacter('#')
            .hexColors()
            .useUnusualXRepeatedCharacterHexFormat()
            .build();
    public static final LegacyComponentSerializer etAndHEX = LegacyComponentSerializer.builder().character('&').hexCharacter('#').hexColors().build();
    public static final LegacyComponentSerializer etOnly = LegacyComponentSerializer.builder().character('&').build();
    public static final LegacyComponentSerializer sectionOnly = LegacyComponentSerializer.builder().character('§').build();

}

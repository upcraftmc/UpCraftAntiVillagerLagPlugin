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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 * AntiVillagerLag; de.upcraftmc.villager.utils:DateUtils
 *
 * @author <a href="https://github.com/LuciferMorningstarDev">LuciferMorningstarDev</a>
 * @since 19.03.2023
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtils {

    public static String formatDate(Date now, String format) {
        DateFormat df = new SimpleDateFormat(format);
        return df.format(now);
    }

    public static String formatDate(LocalDate now, String format) {
        DateFormat df = new SimpleDateFormat(format);
        return df.format(now);
    }

    public static String convertDateToHHMMSS(Date now) {
        return formatDate(now, "HH:mm:ss");
    }

    public static String convertDateToHHMMSS(LocalDate now) {
        return formatDate(now, "HH:mm:ss");
    }

    public static String convertDateToString(Date now) {
        return formatDate(now, "dd/MM/yyyy HH:mm:ss");
    }

    public static String convertDateToString(LocalDate now) {
        return formatDate(now, "dd/MM/yyyy HH:mm:ss");
    }

    public static String formatDateNow() {
        LocalDate localDate = LocalDate.now();
        int dd = localDate.getDayOfMonth();
        int mm = localDate.getMonthValue();
        int yyyy = localDate.getYear();
        String date = dd + ":" + mm + ":" + yyyy;
        if (dd < 10) {
            date = "0" + dd + ":" + mm + ":" + yyyy;
        }
        if (mm < 10) {
            date = dd + ":0" + mm + ":" + yyyy;
        }
        if (dd < 10 && mm < 10) {
            date = "0" + dd + ":0" + mm + ":" + yyyy;
        }
        return date;
    }

    public static String getDateAsString(LocalDate localDate) {
        int dd = localDate.getDayOfMonth();
        int mm = localDate.getMonthValue();
        int yyyy = localDate.getYear();
        String date = dd + ":" + mm + ":" + yyyy;
        if (dd < 10) {
            date = "0" + dd + ":" + mm + ":" + yyyy;
        }
        if (mm < 10) {
            date = dd + ":0" + mm + ":" + yyyy;
        }
        if (dd < 10 && mm < 10) {
            date = "0" + dd + ":0" + mm + ":" + yyyy;
        }
        return date;
    }

}

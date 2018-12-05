package net.lordofthecraft.omniscience.util;

import net.lordofthecraft.omniscience.OmniConfig;

import static org.bukkit.ChatColor.*;

public class Formatter {

    public static String getPageHeader(int page, int maxPages) {
        return prefix() + GRAY + " ((Page " + page + "/" + maxPages + "))";
    }

    public static String subHeader(String text) {
        return DARK_GRAY.toString() + ITALIC + text + RESET;
    }

    public static String success(String text) {
        return prefix() + GREEN + text;
    }

    public static String error(String text) {
        return prefix() + RED + " (Error) " + GRAY + text + RESET;
    }

    public static String prefix() {
        return s() + "«" + p() + "Omniscience" + s() + "»" + RESET;
    }

    public static String bonus(String text) {
        return GRAY + text;
    }

    public static String formatPrimaryMessage(String message) {
        return p() + message;
    }

    public static String formatSecondaryMessage(String message) {
        return s() + message;
    }

    private static String p() {
        return OmniConfig.INSTANCE.getPrimary().toString();
    }

    private static String s() {
        return OmniConfig.INSTANCE.getSecondary().toString();
    }
}

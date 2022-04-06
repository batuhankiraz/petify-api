package com.estu.petify.petifyfacades.utils;

public class AdvertiseUtils {

    public static String formatPetPreferences(final String value) {

        return value.replace(" ", ",")
                    .replace("-", ",")
                    .replace("/", ",")
                    .replace("|", ",")
                    .replace(" - ", ",")
                    .replace(" / ", ",")
                    .replace(" | ", ",")
                    .replace(" || ", ",");
    }
}

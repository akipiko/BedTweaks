package com.github.akipiko3150.bedtweaks.modmenu;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "bedtweaks")
public class ModConfig implements ConfigData {

    @ConfigEntry.Gui.Excluded
    public static ModConfig instance;

    public static void init() {
        AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);
        instance = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    }

    @ConfigEntry.Gui.Tooltip()
    @Comment("If true, allows to sleep when the current time of day is not valid for sleeping.")
    public boolean allowWithoutRange = true;

    @ConfigEntry.Gui.Tooltip()
    @Comment("If true, allows to sleep when the current time of day is not valid for sleeping.")
    public boolean allowAnytime = true;

    @ConfigEntry.Gui.Tooltip()
    @Comment("If true, allows to sleep when monsters are nearby.")
    public boolean allowNearbyMonsters = true;
}

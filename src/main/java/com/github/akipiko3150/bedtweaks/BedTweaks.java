package com.github.akipiko3150.bedtweaks;

import com.github.akipiko3150.bedtweaks.modmenu.ModConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.minecraft.util.ActionResult;

public class BedTweaks implements ModInitializer {

    @Override
    public void onInitialize() {

        ModConfig.init();

        EntitySleepEvents.ALLOW_NEARBY_MONSTERS.register((player, sleepingPos, vanillaResult) -> {
            if (ModConfig.instance.allowNearbyMonsters){
                return ActionResult.SUCCESS;
            } else {
                return ActionResult.PASS;
            }
        });
        EntitySleepEvents.ALLOW_SLEEP_TIME.register((player, sleepingPos, vanillaResult) -> {
            if (ModConfig.instance.allowAnytime){
                return ActionResult.SUCCESS;
            } else {
                return ActionResult.PASS;
            }
        });

    }
}

package com.github.akipiko3150.bedtweaks;

import com.github.akipiko3150.bedtweaks.modmenu.ModConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;

public class BedTweaks implements ModInitializer {

    private final int TIME_AT_NIGHT = 13000;
    private final int TIME_AT_DAY = 1000;
    private final int TIME_OF_ONE_DAY = 24000;

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
            if (ModConfig.instance.allowSleepTime){
                return ActionResult.SUCCESS;
            } else {
                return ActionResult.PASS;
            }
        });
        EntitySleepEvents.START_SLEEPING.register((entity, sleepingPos) -> {
            if (!(entity instanceof PlayerEntity)) return;
            if (entity.getWorld() instanceof ServerWorld serverWorld){
                long daytime = serverWorld.getTimeOfDay() % TIME_OF_ONE_DAY;
                long addTime = (TIME_AT_NIGHT <= daytime) ? 0 : TIME_AT_NIGHT;
                serverWorld.setTimeOfDay(serverWorld.getTimeOfDay() - daytime + addTime);
                serverWorld.resetWeather();
                entity.wakeUp();
            }
        });
    }
}

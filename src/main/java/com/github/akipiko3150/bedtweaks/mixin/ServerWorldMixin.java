package com.github.akipiko3150.bedtweaks.mixin;

import com.github.akipiko3150.bedtweaks.modmenu.ModConfig;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Supplier;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin extends World {

    @Unique
    private static final long TIME_AT_NIGHT = 13000L;

    protected ServerWorldMixin(MutableWorldProperties properties, RegistryKey<World> registryRef, DynamicRegistryManager registryManager, RegistryEntry<DimensionType> dimensionEntry, Supplier<Profiler> profiler, boolean isClient, boolean debugWorld, long biomeAccess, int maxChainedNeighborUpdates) {
        super(properties, registryRef, registryManager, dimensionEntry, profiler, isClient, debugWorld, biomeAccess, maxChainedNeighborUpdates);
    }


    @Redirect(
            method = "tick(Ljava/util/function/BooleanSupplier;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/world/ServerWorld;setTimeOfDay(J)V"
            )
    )
    private void redirectSetTimeOfDay(ServerWorld serverWorld, long timeOfDay){
        long time;
        if (ModConfig.instance.skipToNight && serverWorld.isDay()){
            long l = serverWorld.getTimeOfDay();
            time = l + TIME_AT_NIGHT - l % 24000L;
        } else {
            time = timeOfDay;
        }
        serverWorld.setTimeOfDay(time);
    }

}

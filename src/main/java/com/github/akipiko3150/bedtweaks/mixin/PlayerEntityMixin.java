package com.github.akipiko3150.bedtweaks.mixin;

import com.github.akipiko3150.bedtweaks.modmenu.ModConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow
    private int sleepTimer;

    @Inject(
            method = "canResetTimeBySleeping()Z",
            at = @At("RETURN"),
            cancellable = true
    )
    public void InjectCanResetTimeBySleeping(CallbackInfoReturnable<Boolean> info){
        int skipTime = ModConfig.instance.removeSleepingTime ? 0 : 100;
        info.setReturnValue(this.isSleeping() && this.sleepTimer >= skipTime);
    }

}

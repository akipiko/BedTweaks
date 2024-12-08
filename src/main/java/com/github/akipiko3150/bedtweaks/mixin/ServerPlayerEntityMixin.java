package com.github.akipiko3150.bedtweaks.mixin;

import com.github.akipiko3150.bedtweaks.modmenu.ModConfig;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin  extends PlayerEntity {

    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Inject(
            method = "isBedWithinRange(Lnet/minecraft/util/math/BlockPos;)Z",
            at = @At("RETURN"),
            cancellable = true
    )
    public void InjectIsBedWithinRange(BlockPos pos, CallbackInfoReturnable<Boolean> info){
        info.setReturnValue(
                ModConfig.instance.allowWithoutRange || info.getReturnValue()
        );
    }

}


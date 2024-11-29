package com.github.akipiko3150.bedtweaks.mixin;

import com.github.akipiko3150.bedtweaks.modmenu.ModConfig;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
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

    @Inject(method = "isBedWithinRange(Lnet/minecraft/util/math/BlockPos;)Z", at = @At("RETURN"), cancellable = true)
    public void test(BlockPos pos, CallbackInfoReturnable<Boolean> info){
        Vec3d vec3d = Vec3d.ofBottomCenter(pos);

        info.setReturnValue(
                ModConfig.instance.allowWithoutRange ||
                        (Math.abs(this.getX() - vec3d.getX()) <= 3.0 && Math.abs(this.getY() - vec3d.getY()) <= 2.0 && Math.abs(this.getZ() - vec3d.getZ()) <= 3.0)
        );
    }

}


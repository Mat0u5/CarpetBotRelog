package net.mat0u5.carpetbotrelog.mixin;

import carpet.patches.EntityPlayerMPFake;
import com.mojang.authlib.GameProfile;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {

    @Redirect(method = "respawnPlayer", at = @At(value = "NEW", target = "(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/server/world/ServerWorld;Lcom/mojang/authlib/GameProfile;Lnet/minecraft/server/network/ServerPlayerInteractionManager;)Lnet/minecraft/server/network/ServerPlayerEntity;"))
    public ServerPlayerEntity respawnBot(MinecraftServer minecraftServer, ServerWorld serverWorld, GameProfile gameProfile, ServerPlayerInteractionManager serverPlayerInteractionManager) {
        if (serverPlayerInteractionManager.player instanceof EntityPlayerMPFake) {
            return FakePlayerMixin.createInstance(minecraftServer, serverWorld, gameProfile, serverPlayerInteractionManager);
        }
        return new ServerPlayerEntity(minecraftServer, serverWorld, gameProfile, serverPlayerInteractionManager);
    }
}

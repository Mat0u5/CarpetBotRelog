package net.mat0u5.carpetbotrelog.mixin;

import carpet.patches.EntityPlayerMPFake;
import net.minecraft.network.packet.c2s.play.ClientStatusC2SPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @Inject(method = "tick", at = @At(value = "HEAD"))
    public void respawn(CallbackInfo ci) {
        MinecraftServer server = (MinecraftServer) (Object) this;
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            if (player instanceof EntityPlayerMPFake) {
                EntityPlayerMPFake bot = (EntityPlayerMPFake) player;
                if ((bot.getHealth() <= 0.0F || bot.removed) && bot.networkHandler != null) {
                    bot.networkHandler.onClientStatus(new ClientStatusC2SPacket(ClientStatusC2SPacket.Mode.PERFORM_RESPAWN));
                }
            }
        }
    }
}

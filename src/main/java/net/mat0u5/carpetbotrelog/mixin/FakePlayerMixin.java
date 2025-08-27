package net.mat0u5.carpetbotrelog.mixin;

import carpet.patches.EntityPlayerMPFake;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.network.packet.c2s.play.ClientStatusC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayerMPFake.class)
public class FakePlayerMixin {

	@Inject(method = "onDeath", at = @At(value = "INVOKE", target = "Lcarpet/patches/EntityPlayerMPFake;setHealth(F)V"), cancellable = true)
	private void vanillaDeath(DamageSource cause, CallbackInfo ci) {
		EntityPlayerMPFake bot = (EntityPlayerMPFake) (Object) this;
		bot.networkHandler.onClientStatus(new ClientStatusC2SPacket(ClientStatusC2SPacket.Mode.PERFORM_RESPAWN));
		ci.cancel();
	}
}
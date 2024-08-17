package org.aquamancer.warlordsbufficons.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.OverlayMessageS2CPacket;
import net.minecraft.text.*;
import org.aquamancer.warlordsbufficons.client.ActionBarParser;
import org.aquamancer.warlordsbufficons.client.ActionBarWriter;
import org.aquamancer.warlordsbufficons.client.Controller;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

import static org.aquamancer.warlordsbufficons.client.WarlordsBuffIconsClient.chat;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin {
    @Shadow public abstract void onOverlayMessage(OverlayMessageS2CPacket packet);



    @Inject(method = "onOverlayMessage", at = @At("HEAD"), cancellable = true)
    private void onOverlayMessage(OverlayMessageS2CPacket packet, CallbackInfo ci) {
        Controller.handleNewOverlayMessagePacket(packet);
        if (Controller.cancelOverlayMessageS2CPacket) {
            ci.cancel();
        }
    }
    // can modify packet with modifyVar
}

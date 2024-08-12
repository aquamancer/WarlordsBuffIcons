package org.aquamancer.warlordsbufficons.mixin;

import net.fabricmc.fabric.api.client.networking.v1.ClientConfigurationNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.s2c.play.OverlayMessageS2CPacket;
import net.minecraft.text.*;
import org.aquamancer.warlordsbufficons.client.ActionBarInfo;
import org.aquamancer.warlordsbufficons.client.ActionBarOverrider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

import static org.aquamancer.warlordsbufficons.client.WarlordsBuffIconsClient.chat;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin {
    @Shadow public abstract void onOverlayMessage(OverlayMessageS2CPacket packet);

    MinecraftClient client = MinecraftClient.getInstance();
    ActionBarInfo actionBarInfo = new ActionBarInfo();
    ActionBarOverrider actionBarOverrider = new ActionBarOverrider();

    @Inject(method = "onOverlayMessage", at = @At("HEAD"), cancellable = true)
    private void onOverlayMessage(OverlayMessageS2CPacket packet, CallbackInfo ci) {
        Text message = packet.getMessage();

        client.inGameHud.getChatHud().clear(true);
        chat(packet.getMessage().toString());
        this.actionBarInfo.updateActionBarInfo(message);

        List<Text> siblings = message.getSiblings();
        for (Text sibling : siblings) {
            chat("sibling:{" + sibling.toString() + "}");
        }
        // intercept the packet and prevent it from being handled
        ci.cancel();
        // i can't find a way to modify the packet to get rid of the debuffs and leave the hp/team and then process the modified packet
        // because this mixin listens to when the actionbar message packets are received
        // it would recurse and lead to stackoverflow error
        // so i have to directly recreate the action bar message through the packet data.
    }
}

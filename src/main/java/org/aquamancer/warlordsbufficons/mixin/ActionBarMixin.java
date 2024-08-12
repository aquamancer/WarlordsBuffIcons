package org.aquamancer.warlordsbufficons.mixin;

import net.minecraft.client.MinecraftClient;
import org.aquamancer.warlordsbufficons.client.ActionBarInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.text.Text;

import static org.aquamancer.warlordsbufficons.client.WarlordsBuffIconsClient.chat;

@Mixin(InGameHud.class)
public class ActionBarMixin {
    MinecraftClient client = MinecraftClient.getInstance();
    ActionBarInfo actionBarInfo = new ActionBarInfo();

    @Inject(at = @At("HEAD"), method = "Lnet/minecraft/client/gui/hud/InGameHud;setOverlayMessage(Lnet/minecraft/text/Text;Z)V")
    private void sendMessage(Text message, boolean tinted, CallbackInfo info) {
        this.actionBarInfo.updateActionBarInfo(message);
        client.inGameHud.getChatHud().clear(true);
        for (Text sibling : message.getSiblings()) {
            if (sibling.getSiblings().size() > 0) {
   //             chat("sibling:{" + sibling.getString() + "}, nameColor:{" + sibling.getSiblings().get(0).getStyle().getColor() + "}");
            } else {
    //            chat("sibling:{" + sibling.getString() + "}");
            }
        }
        //chat(message.toString());
    }
}
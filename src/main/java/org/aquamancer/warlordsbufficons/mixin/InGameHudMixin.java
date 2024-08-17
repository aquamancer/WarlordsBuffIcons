package org.aquamancer.warlordsbufficons.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.util.Identifier;
import org.aquamancer.warlordsbufficons.client.Controller;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.aquamancer.warlordsbufficons.client.WarlordsBuffIconsClient.chat;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    MinecraftClient client = MinecraftClient.getInstance();
    @Shadow
    public abstract void render(DrawContext context, float tickDelta);


    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderAutosaveIndicator(Lnet/minecraft/client/gui/DrawContext;)V"))
    private void render(DrawContext context, float tickDelta, CallbackInfo ci) {
        Controller.renderIcons(context, tickDelta, ci);
    }

}

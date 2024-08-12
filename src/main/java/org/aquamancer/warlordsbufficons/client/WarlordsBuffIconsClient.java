package org.aquamancer.warlordsbufficons.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.Overlay;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.text.Text;
import net.minecraft.network.packet.s2c.play.OverlayMessageS2CPacket;


public class WarlordsBuffIconsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        MinecraftClient client = MinecraftClient.getInstance();


        ClientPlayNetworkHandler handler = client.getNetworkHandler();

    }
    public static void chat(String message) {
        MinecraftClient c = MinecraftClient.getInstance();
        if (c == null) {
            return;
        }

        c.inGameHud.getChatHud().addMessage(Text.of(message));
    }
}

package org.aquamancer.warlordsbufficons.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.text.Text;


public class WarlordsBuffIconsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        MinecraftClient client = MinecraftClient.getInstance();

       // ClientPlayConnectionEvents.JOIN.register(this::onPlayerJoin);

    }
    public static void chat(String message) {
        MinecraftClient c = MinecraftClient.getInstance();
        if (c == null) {
            return;
        }

        c.inGameHud.getChatHud().addMessage(Text.of(message));
    }
}

package org.aquamancer.warlordsbufficons.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.OverlayMessageS2CPacket;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

import static org.aquamancer.warlordsbufficons.client.WarlordsBuffIconsClient.chat;

public class Controller {
    private static MinecraftClient client = MinecraftClient.getInstance();
    private static ActionBarParser actionBarParser = new ActionBarParser();
    private static ActionBarWriter actionBarWriter = new ActionBarWriter();
    private static IconRenderer iconRenderer = new IconRenderer();

    public static ClientTickTracker clientTickTracker = new ClientTickTracker();

    public static boolean cancelOverlayMessageS2CPacket = true;
    public static boolean shouldRenderIcons = false;

    public static void handleNewOverlayMessagePacket(OverlayMessageS2CPacket packet) {
        try {
            cancelOverlayMessageS2CPacket = true;
            Text message = packet.getMessage();

            client.inGameHud.getChatHud().clear(true);
            chat(packet.getMessage().toString());
            ActionBarData actionBarData = actionBarParser.parseActionBarInfo(message);
            if (actionBarParser.isCompassDisplayed) {
                cancelOverlayMessageS2CPacket = false;
                return;
            }
            if (actionBarData.getBuffList().size() == 0) {
                clientTickTracker.setTick(0); //setTick(Long.MIN_VALUE);
            }
            actionBarWriter.sendActionBar(actionBarData, client);
            iconRenderer.updateActionBarData(actionBarData);

        } catch (IndexOutOfBoundsException e) {
            chat("index out out of bounds exception");
            e.printStackTrace();
        }


//        List<Text> siblings = message.getSiblings();
//        for (Text sibling : siblings) {
//            if (sibling.getSiblings().size() > 0 && sibling.getSiblings().get(0).getStyle().getColor() != null) {
//                chat("sibling:{" + sibling.getString() + "}" + "color:{" + sibling.getSiblings().get(0).getStyle().getColor() + "}" + "style:{" + sibling.getSiblings().get(0).getStyle().toString() + "}");
//            } else {
//                chat("sibling:{" + sibling.getString() + "}" + "color:{" + sibling.getStyle().getColor() + "}style:{" + sibling.getStyle().toString() + "}");
//            }
//        }
        // can modify the local packet variable using injection annotation, but modifying the overlay directly might be better
    }
    // called every render tick if hud is shown
    public static void renderIcons(DrawContext context, float tickDelta, CallbackInfo ci) {

    }
}

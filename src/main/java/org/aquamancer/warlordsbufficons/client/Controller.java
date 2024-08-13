package org.aquamancer.warlordsbufficons.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.OverlayMessageS2CPacket;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.text.Text;

import java.util.List;

import static org.aquamancer.warlordsbufficons.client.WarlordsBuffIconsClient.chat;

public class Controller {
    private static MinecraftClient client = MinecraftClient.getInstance();
    private static ActionBarParser actionBarParser = new ActionBarParser();
    private static ActionBarWriter actionBarWriter = new ActionBarWriter();

    public static boolean cancelOverlayMessageS2CPacket = true;

    public static void handleNewOverlayMessagePacket(OverlayMessageS2CPacket packet) {
        cancelOverlayMessageS2CPacket = true;
        Text message = packet.getMessage();

        client.inGameHud.getChatHud().clear(true);
        chat(packet.getMessage().toString());
        ActionBarData actionBarData = actionBarParser.parseActionBarInfo(message);
        if (actionBarParser.isCompassDisplayed) {
            cancelOverlayMessageS2CPacket = false;
            return;
        }
        if (actionBarData != null) {
            actionBarWriter.sendActionBar(actionBarData, client);
        }

        List<Text> siblings = message.getSiblings();
//        for (Text sibling : siblings) {
//            if (sibling.getSiblings().size() > 0 && sibling.getSiblings().get(0).getStyle().getColor() != null) {
//                chat("sibling:{" + sibling.getString() + "}" + "color:{" + sibling.getSiblings().get(0).getStyle().getColor() + "}" + "style:{" + sibling.getSiblings().get(0).getStyle().toString() + "}");
//            } else {
//                chat("sibling:{" + sibling.getString() + "}" + "color:{" + sibling.getStyle().getColor() + "}style:{" + sibling.getStyle().toString() + "}");
//            }
//        }
//         i can't find a way to modify the packet to get rid of the debuffs and leave the hp/team and then process the modified packet
//         because this mixin listens to when the actionbar message packets are received
//         it would recurse and lead to stackoverflow error
//         so i have to directly recreate the action bar message through the packet data.
    }
}

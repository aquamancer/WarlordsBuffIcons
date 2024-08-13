package org.aquamancer.warlordsbufficons.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

import static org.aquamancer.warlordsbufficons.client.WarlordsBuffIconsClient.chat;

public class ActionBarWriter {

    public void sendActionBar(ActionBarData data, MinecraftClient client) {
        client.inGameHud.setOverlayMessage(buildActionBar(data), false);
    }
    private Text buildActionBar(ActionBarData data) {
        MutableText bar = Text.empty();
        MutableText temp = Text.empty();
        if (data.isShowHpPrefix()) {
            temp.append("HP: ");
            temp.setStyle(data.getHpPrefixStyle());
            bar.append(temp);
            temp = Text.empty();
        }
        // have to manually set the "/" and maxHealth to bold because for some reason is not being picked up in packet
        // also doing every combination separately in order to not reuse the style from currentHealth/maxHealth format
        if (data.isShowCurrentHealth() && data.isShowMaxHealth()) {
            temp.append(String.valueOf(data.getCurrentHealth()));
            temp.setStyle(data.getCurrentHealthStyle());
            bar.append(temp);
            temp = Text.empty();
            temp.append("/");
            temp.setStyle(data.getCurrentAndMaxHealthStyle().get(1).withBold(true));
            bar.append(temp);
            temp = Text.empty();

            temp.append(String.valueOf(data.getMaxHealth()));
            temp.setStyle(data.getCurrentAndMaxHealthStyle().get(2).withBold(true));
            bar.append(temp);
            temp = Text.empty();

            bar.append("   ");
        } else if (data.isShowCurrentHealth()) {
            temp.append(String.valueOf(data.getCurrentHealth()));
            temp.setStyle(data.getCurrentHealthStyle());
            bar.append(temp);
            temp = Text.empty();

            bar.append("   ");
        } else if (data.isShowMaxHealth()) {
            temp.append(String.valueOf(data.getMaxHealth()));
            temp.setStyle(data.getMaxHealthStyle());
            bar.append(temp);
            temp = Text.empty();

            bar.append("   ");
        }

        if (data.isShowTeam()) {
            temp.append(data.getTeam());
            temp.setStyle(data.getTeamStyle());
            bar.append(temp);
            temp = Text.empty();

            bar.append("   ");
        }
        return bar;
    }
//    public void overrideActionBar(Text actionBarText, int indexOfFirstBuff) {
//        ClientPlayerEntity player = client.player;
//        player.sendMessage(Text.literal("asdf"), true);
//    }


}

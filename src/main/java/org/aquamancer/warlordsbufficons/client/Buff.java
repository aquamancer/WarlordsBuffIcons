package org.aquamancer.warlordsbufficons.client;

import net.minecraft.text.TextColor;

public class Buff {
    public String text;
    public float duration;
    public boolean isDebuff;

    public Buff(String text, float duration, TextColor color) {
        this.text = text;
        this.duration = duration;

        if (color.getName().equals("red")) {
            this.isDebuff = true;
        } else {
            this.isDebuff = false;
        }
    }
    public Buff(String text, float duration, boolean isDebuff) {
        this.text = text;
        this.duration = duration;
        this.isDebuff = isDebuff;
    }
}

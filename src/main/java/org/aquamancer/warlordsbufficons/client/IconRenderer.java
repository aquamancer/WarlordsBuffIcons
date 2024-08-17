package org.aquamancer.warlordsbufficons.client;

import net.minecraft.client.gui.DrawContext;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class IconRenderer {
    /*
    PROBLEM:
    we receive packets each time a buff value changes, or another buff is added
    when a new packet is received, how do we identify new vs existing buffs that are simply ticking down?
    we must know this for gradual graphical countdown of buffs
    we can't just loop painting each buff that exists because then we can't have a hourglass tick down relative to its initial duration

    potential solutions:
    1.  when the packet shows a buff being added, we can expect that buff to be the cause of another packet sent in exactly 1 second (limitation: if buffs are received that are not x.0 remaining duration)
        then, if a new packet is received outside of the expected times, we know that the existing data will match up exactly with the new packet, besides the new buff because we know that nothing should have changed
        limitation: it's possible for a new buff to be added exactly when an existing buff ticks down
    2.  keep a database of how long each buff lasts. if the packet includes a buff at its max duration, it's a new buff. limitation: if a buff gets added with a remaining duration lower than expected

if a buff is removed, we can read the line from left to right.
ex.
fhex1 arcane2 wounding3 avemark4 fhex5 blind6 mhex7
and then the next (unexpected) packet is
fhex1 arcane2 fhex5 mhex7

numbers = seconds left

we can read from left to right:
fhex:1 expected fhex(1)
arcane(2) expected arcane(2)
fhex(5) expected wounding(3) -> look for fhex5 in original. found at index 4 -> wound and avemark was removed

maybe just go off of if all the buffs are the same and in the same order, doesn't matter what the remaining duration is

maybe linked list?

if the packet is unexpected then
    - a buff was added
    - a buff was removed
    - buff duration was changed abruptly (vind)
    - lagspike or game paused - if the packets are the exact same, pause until a different packet and resume
    - hp/team changed

how about don't go off of time, and instead just go off of the next expected packet
nah this doesn't work because then you can't sync stuff when there's unexpected


what if the game is paused or huge lagspike

     */
    private ActionBarData previousPacket;
    private ActionBarData currentPacket;
    private ActionBarData expectedNextPacket;
    private int expectedNextPacketTick;
    private int expectedTickTolerance;
    private List<Icon> icons;

    public void updateActionBarData(ActionBarData data) {
        previousPacket = currentPacket;
        currentPacket = data;

        // if
    }

    private boolean buffsChanged(ActionBarData previousPacket, ActionBarData currentPacket) {
        return previousPacket.getBuffList().equals(currentPacket.getBuffList());
    }
    private boolean isPacketExpected(ActionBarData currentPacket, ActionBarData expectedNextPacket) {
        return currentPacket.getBuffList().equals(expectedNextPacket.getBuffList());
    }

    public void renderIcons(DrawContext context, float tickDelta, CallbackInfo ci) {
        for (int i = 0 ; i < this.icons.size(); i++) {
            this.icons.get(i).render(context, tickDelta);
        }
    }
}

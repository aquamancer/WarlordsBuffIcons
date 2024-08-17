package org.aquamancer.warlordsbufficons.client;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

import static org.aquamancer.warlordsbufficons.client.WarlordsBuffIconsClient.chat;

public class ClientTickTracker {
    private long tick;
    public ClientTickTracker() {
        tick = 0;
        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            tick++;
        });
    }
    public void setTick(long tick) {
        this.tick = tick;
    }
    public long getTick() {
        return tick;
    }
}

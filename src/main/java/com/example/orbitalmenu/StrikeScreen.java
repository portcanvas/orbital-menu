package com.example.orbitalmenu;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class StrikeScreen extends Screen {
    public StrikeScreen() {
        super(Component.literal("Orbital Menu"));
    }

    @Override
    protected void init() {
        int w = 160, h = 20, gap = 4;
        int x = this.width / 2 - w / 2;
        int y = this.height / 2 - 90;

        this.addRenderableWidget(new StringWidget(x, y - 16, w, 12,
                Component.literal("Orbital Menu - aim at a block"), this.font));

        y = add("Lightning", Strikes::lightning, x, y, w, h, gap);
        y = add("Single Strike", Strikes::single, x, y, w, h, gap);
        y = add("Big Strike", Strikes::big, x, y, w, h, gap);
        y = add("Carpet Bombing", Strikes::carpet, x, y, w, h, gap);
        y = add("Cluster Bomb", Strikes::cluster, x, y, w, h, gap);
        y = add("Nuke", Strikes::nuke, x, y, w, h, gap);

        this.addRenderableWidget(Button.builder(Component.literal("Close"), b -> this.onClose())
                .bounds(x, y + 8, w, h).build());
    }

    private int add(String label, Runnable action, int x, int y, int w, int h, int gap) {
        this.addRenderableWidget(Button.builder(Component.literal(label), b -> {
            this.onClose();           // close first so the raycast uses your aim
            action.run();
        }).bounds(x, y, w, h).build());
        return y + h + gap;
    }
}

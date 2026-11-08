
package com.example.orbitalmenu;

import net.minecraft.client.Minecraft;
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
        int y = this.height / 2 - 110;

        this.addRenderableWidget(new StringWidget(x, y - 16, w, 12,
                Component.literal("Orbital Menu - aim at a block"), this.font));

        y = add("Nuke Shot", Strikes::nukeShot, x, y, w, h, gap);
        y = add("Stab Shot", Strikes::stabShot, x, y, w, h, gap);
        y = add("Wither Skull Cannon", Strikes::witherSkullCannon, x, y, w, h, gap);
        y = add("Wolf Cannon", Strikes::wolfCannon, x, y, w, h, gap);
        y = add("Railgun", Strikes::railgun, x, y, w, h, gap);
        y = add("Ring", Strikes::ring, x, y, w, h, gap);

        this.addRenderableWidget(Button.builder(Component.literal("Stasis Chamber"), b -> {
            this.onClose();
            Minecraft.getInstance().gui.setScreen(new StasisChamberScreen());
        }).bounds(x, y, w, h).build());
        y += h + gap;

        this.addRenderableWidget(Button.builder(Component.literal("Close"), b -> this.onClose())
                .bounds(x, y + 8, w, h).build());
    }

    private int add(String label, Runnable action, int x, int y, int w, int h, int gap) {
        this.addRenderableWidget(Button.builder(Component.literal(label), b -> {
            this.onClose();
            action.run();
        }).bounds(x, y, w, h).build());
        return y + h + gap;
    }
}

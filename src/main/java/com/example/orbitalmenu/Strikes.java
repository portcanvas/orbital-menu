package com.example.orbitalmenu;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class StasisChamberScreen extends Screen {
    private EditBox xBox, yBox, zBox;

    public StasisChamberScreen() {
        super(Component.literal("Stasis Chamber"));
    }

    @Override
    protected void init() {
        int w = 160, h = 20, gap = 6;
        int x = this.width / 2 - w / 2;
        int y = this.height / 2 - 70;

        this.addRenderableWidget(new StringWidget(x, y - 16, w, 12,
                Component.literal("Teleport to coordinates"), this.font));

        xBox = new EditBox(this.font, x, y, w, h, Component.literal("X"));
        xBox.setHint(Component.literal("X"));
        this.addRenderableWidget(xBox);
        y += h + gap;

        yBox = new EditBox(this.font, x, y, w, h, Component.literal("Y"));
        yBox.setHint(Component.literal("Y"));
        this.addRenderableWidget(yBox);
        y += h + gap;

        zBox = new EditBox(this.font, x, y, w, h, Component.literal("Z"));
        zBox.setHint(Component.literal("Z"));
        this.addRenderableWidget(zBox);
        y += h + gap + 4;

        this.addRenderableWidget(Button.builder(Component.literal("Teleport"), b -> {
            String xs = xBox.getValue().trim();
            String ys = yBox.getValue().trim();
            String zs = zBox.getValue().trim();
            if (xs.isEmpty() || ys.isEmpty() || zs.isEmpty()) {
                if (this.minecraft != null && this.minecraft.player != null) {
                    this.minecraft.player.sendSystemMessage(Component.literal("Enter X, Y and Z first."));
                }
                return;
            }
            Strikes.stasisChamber(xs, ys, zs);
            this.onClose();
        }).bounds(x, y, w, h).build());
        y += h + gap;

        this.addRenderableWidget(Button.builder(Component.literal("Back"), b -> {
            this.onClose();
            Minecraft.getInstance().gui.setScreen(new StrikeScreen());
        }).bounds(x, y, w, h).build());
    }
}

package com.example.orbitalmenu;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayDeque;
import java.util.Queue;

public class OrbitalMenuClient implements ClientModInitializer {
    public static KeyMapping OPEN_MENU;
    /** Commands are sent one per tick so servers don't flag command spam. */
    private static final Queue<String> QUEUE = new ArrayDeque<>();

    @Override
    public void onInitializeClient() {
        KeyMapping.Category category = KeyMapping.Category.register(
                Identifier.fromNamespaceAndPath("orbitalmenu", "main"));

        OPEN_MENU = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.orbitalmenu.open", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_O, category));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (OPEN_MENU.consumeClick()) {
                if (client.player != null && client.gui.screen() == null) {
                    client.gui.setScreen(new StrikeScreen());
                }
            }
            if (client.player != null && !QUEUE.isEmpty()) {
                client.player.connection.sendCommand(QUEUE.poll());
            } else if (client.player == null) {
                QUEUE.clear();
            }
        });
    }

    public static void queue(String command) {
        QUEUE.add(command);
    }

    public static Minecraft mc() {
        return Minecraft.getInstance();
    }
}

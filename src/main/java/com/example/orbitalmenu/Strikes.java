package com.example.orbitalmenu;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.Locale;

/** Each strike is just a list of normal commands, so it only works if you have permission. */
public final class Strikes {
    private Strikes() {}

    private static BlockPos target() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return null;
        HitResult hit = mc.player.pick(256.0, 1.0f, false);
        if (hit instanceof BlockHitResult b && hit.getType() == HitResult.Type.BLOCK) {
            return b.getBlockPos();
        }
        mc.player.sendSystemMessage(Component.literal("Orbital Menu: look at a block (max 256 blocks away)."));
        return null;
    }

    private static String f(double v) {
        return String.format(Locale.ROOT, "%.1f", v);
    }

    private static void boom(double x, double y, double z, int power, int fuse) {
        OrbitalMenuClient.queue("summon tnt " + f(x) + " " + f(y) + " " + f(z)
                + " {fuse:" + fuse + "s,explosion_power:" + power + "f}");
    }

    private static void beam(double x, double y, double z) {
        OrbitalMenuClient.queue("particle end_rod " + f(x) + " " + f(y + 40) + " " + f(z)
                + " 0.1 40 0.1 0 200 force");
        OrbitalMenuClient.queue("summon lightning_bolt " + f(x) + " " + f(y) + " " + f(z));
    }

    public static void lightning() {
        BlockPos p = target();
        if (p == null) return;
        OrbitalMenuClient.queue("summon lightning_bolt " + p.getX() + " " + p.getY() + " " + p.getZ());
    }

    public static void single() {
        BlockPos p = target();
        if (p == null) return;
        double x = p.getX() + 0.5, y = p.getY() + 1, z = p.getZ() + 0.5;
        beam(x, y, z);
        boom(x, y, z, 10, 20);
    }

    public static void big() {
        BlockPos p = target();
        if (p == null) return;
        double x = p.getX() + 0.5, y = p.getY() + 1, z = p.getZ() + 0.5;
        beam(x, y, z);
        boom(x, y, z, 25, 30);
    }

    public static void nuke() {
        BlockPos p = target();
        if (p == null) return;
        double x = p.getX() + 0.5, y = p.getY() + 1, z = p.getZ() + 0.5;
        beam(x, y, z);
        boom(x, y, z, 50, 40);
        for (int i = 0; i < 8; i++) {
            double a = i * Math.PI / 4;
            boom(x + Math.cos(a) * 8, y, z + Math.sin(a) * 8, 20, 45);
        }
    }

    public static void carpet() {
        BlockPos p = target();
        if (p == null) return;
        Minecraft mc = Minecraft.getInstance();
        double yaw = Math.toRadians(mc.player.getYRot());
        double dx = -Math.sin(yaw), dz = Math.cos(yaw);
        for (int i = 0; i < 12; i++) {
            double x = p.getX() + 0.5 + dx * i * 4;
            double z = p.getZ() + 0.5 + dz * i * 4;
            boom(x, p.getY() + 1, z, 8, 20 + i * 3);
        }
    }

    public static void cluster() {
        BlockPos p = target();
        if (p == null) return;
        java.util.Random r = new java.util.Random();
        for (int i = 0; i < 15; i++) {
            double x = p.getX() + 0.5 + (r.nextDouble() - 0.5) * 24;
            double z = p.getZ() + 0.5 + (r.nextDouble() - 0.5) * 24;
            boom(x, p.getY() + 1, z, 6, 20 + r.nextInt(30));
        }
    }
}

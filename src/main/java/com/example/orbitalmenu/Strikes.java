
package com.example.orbitalmenu;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.Locale;

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
    }

    public static void nukeShot() {
        BlockPos p = target();
        if (p == null) return;
        double x = p.getX() + 0.5, y = p.getY() + 1, z = p.getZ() + 0.5;
        beam(x, y, z);
        OrbitalMenuClient.queue("summon lightning_bolt " + f(x) + " " + f(y) + " " + f(z));
        boom(x, y, z, 30, 25);
    }

    public static void stabShot() {
        BlockPos p = target();
        if (p == null) return;
        double x = p.getX() + 0.5, y = p.getY() + 1, z = p.getZ() + 0.5;
        OrbitalMenuClient.queue("summon trident " + f(x) + " " + f(y + 30) + " " + f(z)
                + " {Motion:[0.0,-2.0,0.0]}");
        boom(x, y, z, 12, 20);
    }

    public static void witherSkullCannon() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;
        BlockPos p = target();
        if (p == null) return;
        double x = p.getX() + 0.5, y = p.getY() + 2, z = p.getZ() + 0.5;
        double yaw = Math.toRadians(mc.player.getYRot());
        double dx = -Math.sin(yaw), dz = Math.cos(yaw);
        for (int i = 0; i < 5; i++) {
            double sx = x - dx * (6 + i * 3);
            double sz = z - dz * (6 + i * 3);
            OrbitalMenuClient.queue("summon wither_skull " + f(sx) + " " + f(y) + " " + f(sz)
                    + " {Motion:[" + f(dx * 1.5) + "," + f(0.0) + "," + f(dz * 1.5) + "],power:3}");
        }
    }

    public static void wolfCannon() {
        BlockPos p = target();
        if (p == null) return;
        java.util.Random r = new java.util.Random();
        for (int i = 0; i < 10; i++) {
            double x = p.getX() + 0.5 + (r.nextDouble() - 0.5) * 6;
            double z = p.getZ() + 0.5 + (r.nextDouble() - 0.5) * 6;
            OrbitalMenuClient.queue("summon wolf " + f(x) + " " + f(p.getY() + 1) + " " + f(z)
                    + " {Angry:1}");
        }
    }

    public static void railgun() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;
        double yaw = Math.toRadians(mc.player.getYRot());
        double pitch = Math.toRadians(mc.player.getXRot());
        double dx = -Math.sin(yaw) * Math.cos(pitch);
        double dy = -Math.sin(pitch);
        double dz = Math.cos(yaw) * Math.cos(pitch);
        double x = mc.player.getX(), y = mc.player.getEyeY(), z = mc.player.getZ();
        for (int i = 1; i <= 60; i++) {
            double px = x + dx * i, py = y + dy * i, pz = z + dz * i;
            OrbitalMenuClient.queue("particle crit " + f(px) + " " + f(py) + " " + f(pz) + " 0 0 0 0 1 force");
            if (i % 5 == 0) {
                OrbitalMenuClient.queue("summon area_effect_cloud " + f(px) + " " + f(py) + " " + f(pz)
                        + " {Radius:1.2f,Duration:1,Particle:{type:\"minecraft:flame\"},"
                        + "Effects:[{id:\"minecraft:instant_damage\",amplifier:2,duration:1}]}");
            }
        }
    }

    public static void ring() {
        BlockPos p = target();
        if (p == null) return;
        double cx = p.getX() + 0.5, cz = p.getZ() + 0.5, cy = p.getY() + 1;
        int points = 16;
        double radius = 10;
        for (int i = 0; i < points; i++) {
            double a = (2 * Math.PI / points) * i;
            double x = cx + Math.cos(a) * radius;
            double z = cz + Math.sin(a) * radius;
            boom(x, cy, z, 4, 20 + i);
        }
    }

    public static void stasisChamber(String x, String y, String z) {
        OrbitalMenuClient.queue("tp @s " + x + " " + y + " " + z);
    }
}

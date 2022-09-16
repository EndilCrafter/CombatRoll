package net.rolling.network;

import com.google.common.collect.Iterables;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.world.ServerWorld;

public class ServerNetwork {
    public static void initializeHandlers() {
//        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
//            sender.sendPacket(Packets.WeaponRegistrySync.ID, WeaponRegistry.getEncodedRegistry());
//            sender.sendPacket(Packets.ConfigSync.ID, configSerialized);
//        });
        ServerPlayNetworking.registerGlobalReceiver(Packets.RollAnimation.ID, (server, player, handler, buf, responseSender) -> {
            ServerWorld world = Iterables.tryFind(server.getWorlds(), (element) -> element == player.world)
                    .orNull();
            if (world == null || world.isClient) {
                return;
            }
            final var packet = Packets.RollAnimation.read(buf);
            final var forwardBuffer = new Packets.RollAnimation(player.getId(), packet.animationName()).write();
            PlayerLookup.tracking(player).forEach(serverPlayer -> {
                try {
                    if (serverPlayer.getId() != player.getId() && ServerPlayNetworking.canSend(serverPlayer, Packets.RollAnimation.ID)) {
                        ServerPlayNetworking.send(serverPlayer, Packets.RollAnimation.ID, forwardBuffer);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });
    }
}
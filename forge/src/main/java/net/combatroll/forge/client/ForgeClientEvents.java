package net.combatroll.forge.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.combatroll.CombatRoll;
import net.combatroll.client.gui.HudRenderHelper;

@Mod.EventBusSubscriber(modid = CombatRoll.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ForgeClientEvents {
    @SubscribeEvent
    public static void onRenderHud(RenderGuiEvent.Post event){
        HudRenderHelper.render(event.getGuiGraphics(), event.getPartialTick());
    }
}

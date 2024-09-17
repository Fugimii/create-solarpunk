package net.fugimii.solarpunk.item;

import net.fugimii.solarpunk.SolarpunkMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;

public class CreativeModeTabs {
	public static final ResourceKey<CreativeModeTab> CREATIVE_TAB_KEY = ResourceKey.create(Registries.CREATIVE_MODE_TAB,
			new ResourceLocation(SolarpunkMod.MOD_ID, "tab"));

	public static void register() {
		SolarpunkMod.LOGGER.info("Registering Creative mode tabs for " + SolarpunkMod.MOD_ID);
	}
}

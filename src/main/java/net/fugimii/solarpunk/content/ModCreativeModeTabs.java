package net.fugimii.solarpunk.content;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fugimii.solarpunk.SolarpunkMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTabs {
	public static final CreativeModeTab CREATIVE_MODE_TAB = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB,
			new ResourceLocation(SolarpunkMod.MOD_ID, "solarpunk"),
			FabricItemGroup.builder()
					.title(Component.translatable("itemGroup.solarpunk"))
					.icon(() -> new ItemStack(ModBlocks.CALIBRATED_SOLAR_PANEL))
					.displayItems((pParameters, entries) -> {

						entries.accept(ModBlocks.CALIBRATED_SOLAR_PANEL);
						entries.accept(ModBlocks.SOLAR_PANEL);

					}).build());

	public static void register() {

	}
}

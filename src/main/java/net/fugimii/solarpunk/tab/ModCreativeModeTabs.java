package net.fugimii.solarpunk.tab;

import com.simibubi.create.AllCreativeModeTabs;

import com.simibubi.create.foundation.utility.Components;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fugimii.solarpunk.SolarpunkMod;
import net.fugimii.solarpunk.block.ModBlocks;
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
					.icon(() -> new ItemStack(ModBlocks.LARGE_SOLAR_PANEL))
					.displayItems((pParameters, entries) -> {

						// Create Blocks
						entries.accept(ModBlocks.LARGE_SOLAR_PANEL);

					}).build());

	public static void register() {

	}
}

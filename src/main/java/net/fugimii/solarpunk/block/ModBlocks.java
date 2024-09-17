package net.fugimii.solarpunk.block;

import com.tterrag.registrate.util.entry.BlockEntry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fugimii.solarpunk.SolarpunkMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class ModBlocks {
	public static final BlockEntry<LargeSolarPanel> LARGE_SOLAR_PANEL = SolarpunkMod.REGISTRATE.block("large_solar_panel", LargeSolarPanel::new)
			.initialProperties(() -> Blocks.DRIED_KELP_BLOCK)
			.register();
}

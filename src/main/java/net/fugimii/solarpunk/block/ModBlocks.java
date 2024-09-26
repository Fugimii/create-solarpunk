package net.fugimii.solarpunk.block;

import com.simibubi.create.AllBlocks;
import com.tterrag.registrate.util.entry.BlockEntry;

import net.fugimii.solarpunk.SolarpunkMod;
import net.fugimii.solarpunk.block.solarPanel.LargeSolarPanel;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.MapColor;

public class ModBlocks {
	public static final BlockEntry<LargeSolarPanel> LARGE_SOLAR_PANEL = SolarpunkMod.REGISTRATE.block("large_solar_panel", LargeSolarPanel::new)
			.initialProperties(AllBlocks.ANDESITE_CASING)
			.properties(p -> p.mapColor(MapColor.COLOR_BLUE))
			.item()
			.build()
			.register();

	public static void register() {
		SolarpunkMod.LOGGER.info("Registering Blocks for " + SolarpunkMod.MOD_ID);
	}
}

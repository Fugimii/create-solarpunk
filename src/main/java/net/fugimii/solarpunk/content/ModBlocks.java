package net.fugimii.solarpunk.content;

import com.tterrag.registrate.util.entry.BlockEntry;

import net.fugimii.solarpunk.SolarpunkMod;
import net.fugimii.solarpunk.content.block.solarPanel.calibratedSolarPanel.CalibratedSolarPanel;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.MapColor;

public class ModBlocks {
	public static final BlockEntry<CalibratedSolarPanel> CALIBRATED_SOLAR_PANEL = SolarpunkMod.REGISTRATE.block("calibrated_solar_panel", CalibratedSolarPanel::new)
			.initialProperties(() -> Blocks.SPRUCE_PLANKS)
			.properties(p -> p.mapColor(MapColor.COLOR_BLUE))
			.item()
			.build()
			.register();

	public static void register() {
		SolarpunkMod.LOGGER.info("Registering Blocks for " + SolarpunkMod.MOD_ID);
	}
}

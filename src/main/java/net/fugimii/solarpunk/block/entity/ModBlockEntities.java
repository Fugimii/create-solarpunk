package net.fugimii.solarpunk.block.entity;

import com.tterrag.registrate.util.entry.BlockEntityEntry;

import net.fugimii.solarpunk.SolarpunkMod;
import net.fugimii.solarpunk.block.ModBlocks;

import static net.fugimii.solarpunk.SolarpunkMod.REGISTRATE;

public class ModBlockEntities {
	public static final BlockEntityEntry<LargeSolarPanelBlockEntity> LARGE_SOLAR_PANEL_BLOCK_ENTITY = REGISTRATE
			.blockEntity("large_solar_panel_be", LargeSolarPanelBlockEntity::new)
			.validBlocks(ModBlocks.LARGE_SOLAR_PANEL)
			.register();

	public static void register() {
		SolarpunkMod.LOGGER.info("Registering Block Entities for " + SolarpunkMod.MOD_ID);
	}
}

package net.fugimii.solarpunk.block;

import com.tterrag.registrate.util.entry.BlockEntityEntry;

import net.fugimii.solarpunk.SolarpunkMod;
import net.fugimii.solarpunk.block.solarPanel.LargeSolarPanelBlockEntity;
import net.fugimii.solarpunk.block.solarPanel.LargeSolarPanelInstance;
import net.fugimii.solarpunk.block.solarPanel.LargeSolarPanelRenderer;

import static net.fugimii.solarpunk.SolarpunkMod.REGISTRATE;

public class ModBlockEntities {
	public static final BlockEntityEntry<LargeSolarPanelBlockEntity> LARGE_SOLAR_PANEL = REGISTRATE
			.blockEntity("large_solar_panel", LargeSolarPanelBlockEntity::new)
			.instance(() -> LargeSolarPanelInstance::new, false)
			.validBlocks(ModBlocks.LARGE_SOLAR_PANEL)
			.renderer(() -> LargeSolarPanelRenderer::new)
			.register();

	public static void register() {
		SolarpunkMod.LOGGER.info("Registering Block Entities for " + SolarpunkMod.MOD_ID);
	}
}

package net.fugimii.solarpunk.content;

import com.tterrag.registrate.util.entry.BlockEntityEntry;

import net.fugimii.solarpunk.SolarpunkMod;
import net.fugimii.solarpunk.content.block.solarPanel.calibratedSolarPanel.CalibratedSolarPanelBlockEntity;
import net.fugimii.solarpunk.content.block.solarPanel.calibratedSolarPanel.CalibratedSolarPanelInstance;
import net.fugimii.solarpunk.content.block.solarPanel.calibratedSolarPanel.CalibratedSolarPanelRenderer;

import static net.fugimii.solarpunk.SolarpunkMod.REGISTRATE;

public class ModBlockEntities {
	public static final BlockEntityEntry<CalibratedSolarPanelBlockEntity> CALIBRATED_SOLAR_PANEL = REGISTRATE
			.blockEntity("calibrated_solar_panel", CalibratedSolarPanelBlockEntity::new)
			.instance(() -> CalibratedSolarPanelInstance::new, false)
			.validBlocks(ModBlocks.CALIBRATED_SOLAR_PANEL)
			.renderer(() -> CalibratedSolarPanelRenderer::new)
			.register();

	public static void register() {
		SolarpunkMod.LOGGER.info("Registering Block Entities for " + SolarpunkMod.MOD_ID);
	}
}

package net.fugimii.solarpunk.content;

import com.jozufozu.flywheel.core.PartialModel;

import net.fugimii.solarpunk.SolarpunkMod;

public class ModPartialModels {
	public static final PartialModel
			CALIBRATED_SOLAR_PANEL_PANEL = block("calibrated_solar_panel/calibrated_solar_panel_panel")
	;

	private static PartialModel block(String path) {
		return new PartialModel(SolarpunkMod.asResource("block/" + path));
	}

	public static void register() {
		SolarpunkMod.LOGGER.info("Registering Partial Models for " + SolarpunkMod.MOD_ID);
	}
}

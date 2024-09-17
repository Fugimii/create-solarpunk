package net.fugimii.solarpunk.block;

import com.tterrag.registrate.util.entry.BlockEntry;

import net.fugimii.solarpunk.SolarpunkMod;
import net.fugimii.solarpunk.item.CreativeModeTabs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class ModBlocks {
	static {
		SolarpunkMod.REGISTRATE.setCreativeTab(CreativeModeTabs.CREATIVE_TAB_KEY);
	}

	public static final BlockEntry<LargeSolarPanel> LARGE_SOLAR_PANEL = SolarpunkMod.REGISTRATE.block("large_solar_panel", LargeSolarPanel::new)
			.initialProperties(() -> Blocks.DRIED_KELP_BLOCK)
			.item()
			.build()
			.register();

	public static void register() {
		SolarpunkMod.LOGGER.info("Registering Blocks for " + SolarpunkMod.MOD_ID + String.valueOf(LARGE_SOLAR_PANEL));
	}
}

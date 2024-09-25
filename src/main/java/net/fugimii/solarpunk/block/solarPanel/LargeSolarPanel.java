package net.fugimii.solarpunk.block.solarPanel;

import com.simibubi.create.foundation.block.IBE;

import net.fugimii.solarpunk.block.ModBlockEntities;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class LargeSolarPanel extends Block implements IBE<LargeSolarPanelBlockEntity> {
	public LargeSolarPanel(Properties properties) {
		super(properties);
	}

	@Override
	public Class<LargeSolarPanelBlockEntity> getBlockEntityClass() {
		return LargeSolarPanelBlockEntity.class;
	}

	@Override
	public BlockEntityType<? extends LargeSolarPanelBlockEntity> getBlockEntityType() {
		return ModBlockEntities.LARGE_SOLAR_PANEL.get();
	}
}

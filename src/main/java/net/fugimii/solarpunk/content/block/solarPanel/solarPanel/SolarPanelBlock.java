package net.fugimii.solarpunk.content.block.solarPanel.solarPanel;

import com.simibubi.create.foundation.block.IBE;

import net.fugimii.solarpunk.content.ModBlockEntities;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class SolarPanelBlock extends Block implements IBE<SolarPanelBlockEntity> {
	public SolarPanelBlock(Properties properties) {
		super(properties);
	}

	@Override
	public Class<SolarPanelBlockEntity> getBlockEntityClass() {
		return SolarPanelBlockEntity.class;
	}

	@Override
	public BlockEntityType<? extends SolarPanelBlockEntity> getBlockEntityType() {
		return ModBlockEntities.SOLAR_PANEL.get();
	}
}

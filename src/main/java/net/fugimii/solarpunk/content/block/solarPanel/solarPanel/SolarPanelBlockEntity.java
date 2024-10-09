package net.fugimii.solarpunk.content.block.solarPanel.solarPanel;

import net.fugimii.solarpunk.content.block.solarPanel.AbstractSolarPanelBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class SolarPanelBlockEntity extends AbstractSolarPanelBlockEntity {
	protected int EnergyProductionRate = 15`;

	public SolarPanelBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}
}

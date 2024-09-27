package net.fugimii.solarpunk.content.block.solarPanel.calibratedSolarPanel;

import com.simibubi.create.foundation.block.IBE;

import net.fugimii.solarpunk.content.ModBlockEntities;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class CalibratedSolarPanel extends Block implements IBE<CalibratedSolarPanelBlockEntity> {
	public CalibratedSolarPanel(Properties properties) {
		super(properties);
	}

	@Override
	public Class<CalibratedSolarPanelBlockEntity> getBlockEntityClass() {
		return CalibratedSolarPanelBlockEntity.class;
	}

	@Override
	public BlockEntityType<? extends CalibratedSolarPanelBlockEntity> getBlockEntityType() {
		return ModBlockEntities.CALIBRATED_SOLAR_PANEL.get();
	}
}

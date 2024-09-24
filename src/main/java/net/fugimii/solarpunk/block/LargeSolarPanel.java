package net.fugimii.solarpunk.block;

import com.simibubi.create.foundation.block.IBE;

import net.fugimii.solarpunk.block.entity.LargeSolarPanelBlockEntity;
import net.fugimii.solarpunk.block.entity.ModBlockEntities;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class LargeSolarPanel extends Block implements IBE<LargeSolarPanelBlockEntity> {
	protected LargeSolarPanel(Properties properties) {
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

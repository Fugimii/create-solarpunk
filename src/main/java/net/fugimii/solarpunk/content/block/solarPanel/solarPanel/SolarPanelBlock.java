package net.fugimii.solarpunk.content.block.solarPanel.solarPanel;

import com.simibubi.create.foundation.block.IBE;

import net.fugimii.solarpunk.content.ModBlockEntities;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;

public class SolarPanelBlock extends HorizontalDirectionalBlock implements IBE<SolarPanelBlockEntity> {
	public SolarPanelBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(FACING);
		super.createBlockStateDefinition(builder);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
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

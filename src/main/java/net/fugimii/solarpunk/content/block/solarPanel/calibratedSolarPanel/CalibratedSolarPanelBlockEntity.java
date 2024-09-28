package net.fugimii.solarpunk.content.block.solarPanel.calibratedSolarPanel;

import com.mrh0.createaddition.util.Util;

import com.simibubi.create.foundation.item.TooltipHelper;

import com.simibubi.create.foundation.utility.Lang;

import net.fugimii.solarpunk.SolarpunkMod;
import net.fugimii.solarpunk.content.block.solarPanel.AbstractSolarPanelBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraft.world.level.Level;

import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class CalibratedSolarPanelBlockEntity extends AbstractSolarPanelBlockEntity {
	private boolean isViewObstructed = false;

	public CalibratedSolarPanelBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@Override
	public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
		tooltip.add(Component.literal(spacing).append(Component.translatable(SolarpunkMod.MOD_ID + ".tooltip.energy.production").withStyle(ChatFormatting.GRAY)));
		tooltip.add(Component.literal(spacing).append(Component.literal(" " + Util.format(getEnergyProductionRate(level, getBlockPos())) + "fe/t ")).withStyle(ChatFormatting.AQUA)
				.append(Component.translatable(SolarpunkMod.MOD_ID + ".tooltip.solar_panel.at_current_time").withStyle(ChatFormatting.DARK_GRAY)));
		if (isViewObstructed) {
			tooltip.add(Component.literal(spacing));
			tooltip.add(Component.literal(spacing).append(Component.translatable(SolarpunkMod.MOD_ID + ".tooltip.solar_panel.obstructed").withStyle(ChatFormatting.GOLD)));

			Component hint = Component.literal("").append(Component.translatable(SolarpunkMod.MOD_ID + ".tooltip.solar_panel.obstructed_description").withStyle(ChatFormatting.GRAY));
			List<Component> cutString = TooltipHelper.cutTextComponent(hint, TooltipHelper.Palette.GRAY_AND_WHITE);
			for (int i = 0; i < cutString.size(); i++)
				Lang.builder()
						.add(cutString.get(i)
								.copy())
						.forGoggles(tooltip);
		}
		return true;
	}

	@Override
	public int getEnergyProductionRate(Level level, BlockPos pos) {
		// Get the direction towards the sun based on the time of day (simplified for noon)
		float sunAngle = level.getSunAngle(1.0F);
		double sunAngleDegrees = Math.toDegrees(sunAngle);

		if (!(sunAngleDegrees < 90 || sunAngleDegrees > 270)) {
			return 0;
		}

		// Convert that direction to a vector
		Vec3 sunDirection = new Vec3(0, 1, 0).zRot(sunAngle)
				.yRot((float) Math.toRadians(180.0F)); // Flip the vector to point towards the sun

		Vec3 blockPos = new Vec3(pos.getX() + 0.5, pos.above().getY(), pos.getZ() + 0.5); // Convert the BlockPos to a Vec3

		// Perform the raycast
		ClipContext context = new ClipContext(
				blockPos,
				blockPos.add(sunDirection.scale(1000.0F)),  // Extend the ray far enough
				ClipContext.Block.VISUAL,
				ClipContext.Fluid.ANY,
				null // uhhh what am I supposed to put here?
		);

		BlockHitResult hitResult = level.clip(context);
		if (hitResult.getType() == HitResult.Type.BLOCK) {
			isViewObstructed = true;
			return 0;
		} else {
			isViewObstructed = false;
			return (int) (Math.abs(EnergyProductionRate - level.getSkyDarken()) * getEnergyMultipler(level, pos));
		}
	}
}

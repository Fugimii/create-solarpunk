package net.fugimii.solarpunk.block.solarPanel;

import com.mrh0.createaddition.energy.InternalEnergyStorage;
import com.mrh0.createaddition.transfer.EnergyTransferable;
import com.mrh0.createaddition.util.Util;
import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.kinetics.waterwheel.WaterWheelBlock;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;

import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;

import com.simibubi.create.foundation.item.TooltipHelper;

import com.simibubi.create.foundation.utility.Lang;

import io.github.fabricators_of_create.porting_lib.util.LazyOptional;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.fugimii.solarpunk.SolarpunkMod;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraft.world.level.Level;

import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import team.reborn.energy.api.EnergyStorage;
import team.reborn.energy.api.EnergyStorageUtil;

import java.util.List;

public class LargeSolarPanelBlockEntity extends AbstractSolarPanelBlockEntity {
	private boolean isViewObstructed = false;

	public LargeSolarPanelBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
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
		float celestialAngle = level.getSunAngle(1.0F);
		// Convert that direction to a vector
		Vec3 sunDirection = new Vec3(0, 1, 0).zRot(celestialAngle)
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
			return Math.abs(EnergyProductionRate - level.getSkyDarken());
		}
	}
}

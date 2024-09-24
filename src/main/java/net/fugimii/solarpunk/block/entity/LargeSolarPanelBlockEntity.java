package net.fugimii.solarpunk.block.entity;

import com.mrh0.createaddition.energy.InternalEnergyStorage;
import com.mrh0.createaddition.transfer.EnergyTransferable;
import com.mrh0.createaddition.util.Util;
import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;

import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;

import io.github.fabricators_of_create.porting_lib.util.LazyOptional;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.fugimii.solarpunk.SolarpunkMod;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.LightLayer;
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

public class LargeSolarPanelBlockEntity extends SmartBlockEntity implements EnergyTransferable, IHaveGoggleInformation {
	protected final InternalEnergyStorage energy;
	public int EnergyProductionRate = 30; // Depends on the light level from the sky (normalized light level * EnergyProductionRate)

	public LargeSolarPanelBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
		energy = new InternalEnergyStorage(1024, 0, 128);
	}

	@Override
	public void addBehaviours(List<BlockEntityBehaviour> behaviours) {}

	@Override
	public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
		tooltip.add(Component.literal(spacing).append(Component.translatable(SolarpunkMod.MOD_ID + ".tooltip.energy.production").withStyle(ChatFormatting.GRAY)));
		tooltip.add(Component.literal(spacing).append(Component.literal(" " + Util.format(getEnergyProductionRate(level, getBlockPos())) + "fe/t ")).withStyle(ChatFormatting.AQUA));
		tooltip.add(Component.literal(String.valueOf(Math.toDegrees(level.getSunAngle(1.0F)))));
		return true;
	}

	@Override
	public EnergyStorage getEnergyStorage(Direction side) {
		return energy;
	}

	public boolean isEnergyInput(Direction side) {
		return false;
	}

	public boolean isEnergyOutput(Direction side) {
		return true;
	}

	@Override
	public void read(CompoundTag compound, boolean clientPacket) {
		super.read(compound, clientPacket);
		energy.read(compound);
	}

	@Override
	public void write(CompoundTag compound, boolean clientPacket) {
		super.write(compound, clientPacket);
		energy.write(compound);
	}

	private boolean firstTickState = true;

	@Override
	public void tick() {
		super.tick();
		if(level.isClientSide()) return;
		if(firstTickState) firstTick();
		firstTickState = false;

		if(true)
			energy.internalProduceEnergy(getEnergyProductionRate(level, getBlockPos()));

		for(Direction d : Direction.values()) {
			if(!isEnergyOutput(d))
				continue;
			EnergyStorage ies = getCachedEnergy(d);
			if(ies == null)
				continue;
			try(Transaction t = Transaction.openOuter()) {
				EnergyStorageUtil.move(energy, ies, EnergyProductionRate, t);
				t.commit();
			}
		}
	}

	public int getEnergyProductionRate(Level level, BlockPos pos) {
		// Get the direction towards the sun based on the time of day (simplified for noon)
		float celestialAngle = level.getSunAngle(1.0F);
		Vec3 sunDirection = new Vec3(0, 1, 0).zRot(level.getSunAngle(1.0F));

		Vec3 blockPos = new Vec3(pos.getX() + 0.5, pos.above().getY(), pos.getZ() + 0.5); // Convert the BlockPos to a Vec3

		SolarpunkMod.LOGGER.info(String.valueOf(blockPos.add(sunDirection.scale(50.0F).yRot((float) Math.toRadians(180.0F)))));

		// Perform the raycast
		ClipContext context = new ClipContext(
				blockPos,
				blockPos.add(sunDirection.scale(50.0F).yRot((float) Math.toRadians(180.0F))),  // Extend the ray far enough (1000 blocks or more)
				ClipContext.Block.COLLIDER,
				ClipContext.Fluid.ANY,
				null
		);

		BlockHitResult hitResult = level.clip(context);
		if (hitResult.getType() == BlockHitResult.Type.MISS) {
			return 0;
		} else {
			return EnergyProductionRate;
		}
	}

	public void firstTick() {
		updateCache();
	}
	public void updateCache() {
		if(level.isClientSide()) return;
		for(Direction side : Direction.values()) {
			BlockEntity te = level.getBlockEntity(worldPosition.relative(side));
			if(te == null) {
				setCache(side, LazyOptional.empty());
				continue;
			}
			LazyOptional<EnergyStorage> le = LazyOptional.ofObject(EnergyStorage.SIDED.find(level, worldPosition.relative(side), side.getOpposite()));
			setCache(side, le);
		}
	}

	private LazyOptional<EnergyStorage> escacheUp = LazyOptional.empty();
	private LazyOptional<EnergyStorage> escacheDown = LazyOptional.empty();
	private LazyOptional<EnergyStorage> escacheNorth = LazyOptional.empty();
	private LazyOptional<EnergyStorage> escacheEast = LazyOptional.empty();
	private LazyOptional<EnergyStorage> escacheSouth = LazyOptional.empty();
	private LazyOptional<EnergyStorage> escacheWest = LazyOptional.empty();

	public void setCache(Direction side, LazyOptional<EnergyStorage> storage) {
		switch (side) {
			case DOWN -> escacheDown = storage;
			case EAST -> escacheEast = storage;
			case NORTH -> escacheNorth = storage;
			case SOUTH -> escacheSouth = storage;
			case UP -> escacheUp = storage;
			case WEST -> escacheWest = storage;
		}
	}

	public EnergyStorage getCachedEnergy(Direction side) {
		return switch (side) {
			case DOWN -> escacheDown.orElse(null);
			case EAST -> escacheEast.orElse(null);
			case NORTH -> escacheNorth.orElse(null);
			case SOUTH -> escacheSouth.orElse(null);
			case UP -> escacheUp.orElse(null);
			case WEST -> escacheWest.orElse(null);
		};
	}
}

package net.fugimii.solarpunk.content.block.solarPanel;

import com.mrh0.createaddition.energy.InternalEnergyStorage;
import com.mrh0.createaddition.transfer.EnergyTransferable;
import com.mrh0.createaddition.util.Util;
import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;

import io.github.fabricators_of_create.porting_lib.util.LazyOptional;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.fugimii.solarpunk.SolarpunkMod;
import net.fugimii.solarpunk.content.block.solarPanel.solarPanel.SolarPanelBlock;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import team.reborn.energy.api.EnergyStorage;
import team.reborn.energy.api.EnergyStorageUtil;

import java.util.List;

public class AbstractSolarPanelBlockEntity extends SmartBlockEntity implements EnergyTransferable, IHaveGoggleInformation {
	protected final InternalEnergyStorage energy;
	protected int EnergyProductionRate = 30;
	protected float EnergyHeightMultiplier = 0.2F;
	protected float EnergyTemperatureMultiplier = 0.2F;

	public AbstractSolarPanelBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
		energy = new InternalEnergyStorage(1024, 0, 128);
	}

	@Override
	public void addBehaviours(List<BlockEntityBehaviour> behaviours) {}

	@Override
	public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
		tooltip.add(Component.literal(spacing).append(Component.translatable(SolarpunkMod.MOD_ID + ".tooltip.energy.production").withStyle(ChatFormatting.GRAY)));
		tooltip.add(Component.literal(spacing).append(Component.literal(" " + Util.format(getEnergyProductionRate(level, getBlockPos())) + "fe/t ")).withStyle(ChatFormatting.AQUA)
				.append(Component.translatable(SolarpunkMod.MOD_ID + ".tooltip.solar_panel.at_current_time").withStyle(ChatFormatting.DARK_GRAY)));
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
		float panelAngle;
		Direction facing = getBlockState().getValue(SolarPanelBlock.FACING);

		if (facing == Direction.EAST) {
			panelAngle = 45;
		} else if (facing == Direction.WEST) {
			panelAngle = 135;
		} else {
			// If the panel isn't facing east or west, it's not going to face the sun
			return 0;
		}

		// TODO: Fix this shit
		float sunAngle = level.getSunAngle(1.0F);

		Vec3 sunDirection = new Vec3(0, 1, 0).zRot(sunAngle)
				.yRot((float) Math.toRadians(180.0F)); // Flip the vector to point towards the sun

		double zRotationRadians = Math.atan2(sunDirection.y, sunDirection.x);
		double zRotationDegrees = Math.toDegrees(zRotationRadians);

		double panelToSunAngle = zRotationDegrees - panelAngle;
		double panelToSunNormalized = 1.0 - Math.abs(panelToSunAngle / 90);
		SolarpunkMod.LOGGER.info(String.valueOf(zRotationDegrees));

		if (zRotationDegrees > 0) {
			return (int) (panelToSunNormalized * EnergyProductionRate * getEnergyMultipler(level, pos));
		}

		return 0;
	}

	public float getEnergyMultipler(Level level, BlockPos pos) {
		float energyMultiplier = 1;

		if (!level.dimensionType().bedWorks()) { // If it's the nether or end
			return 0;
		}

		if (level.isRaining()) {
			return 0;
		}

		// Multiply the energy production by the height above sea level
		int height = pos.getY();
		if (height > level.getSeaLevel()) {
			float heightAboveSeaLevel = height - level.getSeaLevel();
			float normalisedHeightAboveSeaLevel = heightAboveSeaLevel / (level.getMaxBuildHeight() - level.getSeaLevel());
			energyMultiplier += (normalisedHeightAboveSeaLevel * EnergyHeightMultiplier);
		}

		// Multiply the energy production by the temperature in the current biome
		float normalizedTemperature = level.getBiome(pos).value().getBaseTemperature() / 2.0F;
		if (normalizedTemperature > 0.7F) {
			energyMultiplier += (normalizedTemperature * EnergyTemperatureMultiplier);
			SolarpunkMod.LOGGER.info(String.valueOf(normalizedTemperature * EnergyTemperatureMultiplier));
			SolarpunkMod.LOGGER.info("ENERGY " + energyMultiplier);
		}

		return energyMultiplier;
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

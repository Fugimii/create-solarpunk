package net.fugimii.solarpunk.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fugimii.solarpunk.block.ModBlocks;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;

public class ModModelProvider extends FabricModelProvider {

	public ModModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
		blockStateModelGenerator.createNonTemplateModelBlock(ModBlocks.LARGE_SOLAR_PANEL.get());
	}

	@Override
	public void generateItemModels(ItemModelGenerators itemModelGenerator) {

	}
}

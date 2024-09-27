package net.fugimii.solarpunk.block.solarPanel;

import com.jozufozu.flywheel.api.Material;
import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.api.instance.DynamicInstance;
import com.jozufozu.flywheel.backend.instancing.blockentity.BlockEntityInstance;
import com.jozufozu.flywheel.core.materials.model.ModelData;
import com.jozufozu.flywheel.util.transform.Rotate;
import com.jozufozu.flywheel.util.transform.Translate;

import net.fugimii.solarpunk.SolarpunkMod;
import net.fugimii.solarpunk.block.ModPartialModels;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LightLayer;

public class LargeSolarPanelInstance extends BlockEntityInstance<LargeSolarPanelBlockEntity> implements DynamicInstance {
	protected final ModelData panel;

	public LargeSolarPanelInstance(MaterialManager materialManager, LargeSolarPanelBlockEntity blockEntity) {
		super(materialManager, blockEntity);

		Material<ModelData> mat = getTransformMaterial();

		panel = mat.getModel(ModPartialModels.LARGE_SOLAR_PANEL_PANEL, blockState)
				.createInstance();
	}

	@Override
	public void beginFrame() {
		transform(panel.loadIdentity());

		double sunAngle = Math.toDegrees(world.getSunAngle(1.0F));

		SolarpunkMod.LOGGER.info(String.valueOf(sunAngle));

		int skyLight = world.getBrightness(LightLayer.SKY, blockEntity.getBlockPos().above());
		panel.setSkyLight(skyLight);

		int blockLight = world.getBrightness(LightLayer.BLOCK, blockEntity.getBlockPos().above());
		panel.setBlockLight(blockLight);
	}

	@Override
	protected void remove() {
		panel.delete();
	}

	private <T extends Translate<T> & Rotate<T>> T transform(T msr) {
		return msr.translate(getInstancePosition())
				.centre()
				.unCentre();
	}
}

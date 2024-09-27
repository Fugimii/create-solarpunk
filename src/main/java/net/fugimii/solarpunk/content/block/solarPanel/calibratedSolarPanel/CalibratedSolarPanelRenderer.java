package net.fugimii.solarpunk.content.block.solarPanel.calibratedSolarPanel;

import com.jozufozu.flywheel.backend.Backend;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.foundation.blockEntity.renderer.SafeBlockEntityRenderer;

import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;

import net.fugimii.solarpunk.content.ModPartialModels;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;

public class CalibratedSolarPanelRenderer extends SafeBlockEntityRenderer<CalibratedSolarPanelBlockEntity> {
	public CalibratedSolarPanelRenderer(BlockEntityRendererProvider.Context context) {
	}

	@Override
	protected void renderSafe(CalibratedSolarPanelBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource bufferSource, int light, int overlay) {
		if (Backend.canUseInstancing(be.getLevel())) return;

		BlockState panelState = be.getBlockState();
		VertexConsumer vb = bufferSource.getBuffer(RenderType.translucent());
		SuperByteBuffer panel = CachedBufferer.partial(ModPartialModels.CALIBRATED_SOLAR_PANEL_PANEL, panelState);

		panel.light(light)
				.renderInto(ms, vb);
	}
}

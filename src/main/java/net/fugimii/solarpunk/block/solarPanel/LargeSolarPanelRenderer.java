package net.fugimii.solarpunk.block.solarPanel;

import com.jozufozu.flywheel.backend.Backend;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.foundation.blockEntity.renderer.SafeBlockEntityRenderer;

import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;

import net.fugimii.solarpunk.block.ModPartialModels;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;

public class LargeSolarPanelRenderer extends SafeBlockEntityRenderer<LargeSolarPanelBlockEntity> {
	public LargeSolarPanelRenderer(BlockEntityRendererProvider.Context context) {
	}

	@Override
	protected void renderSafe(LargeSolarPanelBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource bufferSource, int light, int overlay) {
		if (Backend.canUseInstancing(be.getLevel())) return;

		BlockState panelState = be.getBlockState();
		VertexConsumer vb = bufferSource.getBuffer(RenderType.translucent());
		SuperByteBuffer panel = CachedBufferer.partial(ModPartialModels.LARGE_SOLAR_PANEL_PANEL, panelState);

		panel.light(light)
				.renderInto(ms, vb);
	}
}

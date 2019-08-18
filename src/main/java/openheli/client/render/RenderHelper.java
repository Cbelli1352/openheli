package openheli.client.render;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.List;

@SideOnly(Side.CLIENT)
public class RenderHelper {
    @SideOnly(Side.CLIENT)
    public static void renderModel(final IBakedModel model, ResourceLocation texture) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();

        bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
        renderQuadsColor(bufferbuilder, model.getQuads((IBlockState) null, (EnumFacing) null, 0L));
        tessellator.draw();
    }


    @SideOnly(Side.CLIENT)
    private static void renderQuadsColor(final BufferBuilder bufferbuilder, final List<BakedQuad> quads) {
        for(BakedQuad quad : quads)
        {
            net.minecraftforge.client.model.pipeline.LightUtil.renderQuadColor(bufferbuilder, quad, -1);
        }
    }
}
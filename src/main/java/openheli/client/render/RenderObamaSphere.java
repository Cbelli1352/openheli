package openheli.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import openheli.OpenHeli;
import openheli.client.model.ModelCache;
import openheli.entity.EntityObamaSphere;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class RenderObamaSphere extends Render<EntityObamaSphere> {

    private static final ResourceLocation textures = new ResourceLocation(OpenHeli.MODID + ":textures/entities/obamasphere.png");
    private static final IBakedModel model = cacheModel();

    public RenderObamaSphere(RenderManager rm) {
        super(rm);
    }

    @Override
    public void doRender(EntityObamaSphere entity, double x, double y, double z, float entityYaw, float partialTicks) {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
        try {
            GlStateManager.pushMatrix();
            GlStateManager.translate((float) x, (float) y+1, (float) z);
            GlStateManager.rotate(180, 1, 0, 0);
            GlStateManager.rotate(entityYaw + 90, 0, 1, 0);
            RenderHelper.renderModel(model, textures);
            GlStateManager.popMatrix();

        } catch (Exception e) {
            System.out.println("Could not render");
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityObamaSphere entity) {
        return textures;
    }

    public static IBakedModel cacheModel() {
        try {
            return ModelCache.INSTANCE.getBakedModel(new ResourceLocation(OpenHeli.MODID + ":models/entity/obamasphere.obj"));
        } catch(Exception e) {
            OpenHeli.logger.error("Could not load");
        }
        return null;
    }
}
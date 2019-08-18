package openheli.entity;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import openheli.OpenHeli;

public class ModEntities {
    public static void init() {
        int id = 1;
        EntityRegistry.registerModEntity(new ResourceLocation(OpenHeli.MODID, "obama_sphere"), EntityObamaSphere.class, "obama_sphere",  id++, OpenHeli.instance, 256, 1, true, 0,0);
    }
}

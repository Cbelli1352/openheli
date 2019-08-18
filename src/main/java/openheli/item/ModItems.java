package openheli.item;


import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import openheli.OpenHeli;

public class ModItems {
    @GameRegistry.ObjectHolder("openheli:poop")
    public static ItemPoop poop;

    @GameRegistry.ObjectHolder("openheli:bath_water")
    public static ItemBathWater bath_water;

    @GameRegistry.ObjectHolder("openheli:obama_sphere")
    public static ItemObamaSphere obama_sphere;

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        OpenHeli.logger.info("Loading models");
        poop.initModel();
        bath_water.initModel();
        obama_sphere.initModel();
    }
}

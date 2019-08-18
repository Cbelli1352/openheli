package openheli.proxy;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import openheli.OpenHeli;
import openheli.PlayerHandler;
import openheli.entity.EntityObamaSphere;
import openheli.entity.ModEntities;
import openheli.item.ItemBathWater;
import openheli.item.ItemObamaSphere;
import openheli.item.ItemPoop;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import openheli.network.NetworkHandler;

@Mod.EventBusSubscriber
public class CommonProxy {
    public static final PlayerHandler playerHandler = new PlayerHandler();

    public void preInit(FMLPreInitializationEvent event) {
        NetworkHandler.init();
    }

    public void init(FMLInitializationEvent event) {
        ModEntities.init();
    }

    public void postInit(FMLPostInitializationEvent event) {
    }

    @SubscribeEvent
    public static void registerItems(Register<Item> event) {
        Item[] items = {
                new ItemPoop(),
                new ItemBathWater(),
                new ItemObamaSphere(),
        };

        OpenHeli.logger.info("Registering items");
        event.getRegistry().registerAll(items);
    }

    @SubscribeEvent
    public  static  void  registerSounds(Register<SoundEvent> event) {
        event.getRegistry().register(EntityObamaSphere.initSound());
    }
}

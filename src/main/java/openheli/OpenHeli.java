package openheli;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import openheli.proxy.CommonProxy;
import org.apache.logging.log4j.Logger;

@Mod(modid = OpenHeli.MODID, name = OpenHeli.NAME, version = OpenHeli.VERSION)
public class OpenHeli
{
    public static final String MODID = "openheli";
    public static final String NAME = "Open Helicopters";
    public static final String VERSION = "1.0";

    public static Logger logger;

    @SidedProxy(clientSide = "openheli.proxy.ClientProxy", serverSide = "openheli.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static OpenHeli instance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}

package openheli;

import net.minecraft.network.NetHandlerPlayServer;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;

public class PlayerHandler {
    public static Field floatingTickCount = null;
    public static Field vehicleFloatingTickCount = null;

    public PlayerHandler() {
        try {
            floatingTickCount = ReflectionHelper.findField(NetHandlerPlayServer.class, "floatingTickCount", "field_147365_f");
            vehicleFloatingTickCount =  ReflectionHelper.findField(NetHandlerPlayServer.class, "vehicleFloatingTickCount", "field_184346_E");
        } catch (Exception e) {
            OpenHeli.logger.error("Couldn't find floatingTickCount field.");
        }
    }
}
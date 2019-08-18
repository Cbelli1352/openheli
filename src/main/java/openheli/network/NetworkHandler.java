package openheli.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import openheli.OpenHeli;
import openheli.network.message.MessageFacing;

public class NetworkHandler {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(OpenHeli.MODID);
    private static int messageId = 1;

    public static void init() {
        registerMessage(MessageFacing.class, Side.SERVER);
    }

    public static void registerMessage(Class packet, Side side) {
        INSTANCE.registerMessage(packet, packet, messageId++, side);
    }
}

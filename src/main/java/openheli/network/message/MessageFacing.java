package openheli.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import openheli.OpenHeli;
import openheli.entity.EntityObamaSphere;


public class MessageFacing implements IMessage, IMessageHandler<MessageFacing, IMessage> {
    public Vec3d facing;
    public double yaw;
    public MessageFacing() {
        facing = new Vec3d(0, 0, 0);
        yaw = 0;
    }
    public MessageFacing(Vec3d v, double y) {
        facing = v;
        yaw = 0;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeDouble(facing.x);
        buf.writeDouble(facing.y);
        buf.writeDouble(facing.z);
        buf.writeDouble(yaw);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();
        yaw = buf.readDouble();
        facing = new Vec3d(x, y, z);
    }

    @Override
    public IMessage onMessage(MessageFacing message, MessageContext ctx) {
        FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> {
            Entity riding = ctx.getServerHandler().player.getRidingEntity();
            if(riding instanceof EntityObamaSphere) {
                ((EntityObamaSphere) riding).setFacing(message.facing);
                ((EntityObamaSphere) riding).rotationYaw = (float)yaw;
            }
        });

        return null;
    }
}

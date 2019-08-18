package openheli.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import openheli.OpenHeli;
import net.minecraft.entity.item.EntityBoat;
import openheli.item.ModItems;
import openheli.network.NetworkHandler;
import openheli.network.message.MessageFacing;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static openheli.PlayerHandler.vehicleFloatingTickCount;

public class EntityObamaSphere extends Entity {
    private double lerpX, lerpY, lerpZ;
    private double lerpYaw;
    private double lerpPitch;
    private int lerpSteps;
    private long tick;

    private static SoundEvent sound = null;

    protected Vec3d facing;
    protected double throttle;
    protected double strafe;

    public EntityObamaSphere(World worldIn) {
        super(worldIn);
        this.setSize(2, 2);
        facing = new Vec3d(0, 0, 0);
        throttle = 0;
        tick = 0;
        setNoGravity(true);
    }

    public EntityObamaSphere(World worldIn, double x, double y, double z) {
        super(worldIn);
        this.setPosition(x, y, z);
        this.setSize(2, 2);
        facing = new Vec3d(0, 0, 0);
        throttle = 0;
        tick = 0;
        setNoGravity(true);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if(!world.isRemote && this.getControllingPassenger() == null) {
            this.dropItemWithOffset(ModItems.obama_sphere, 1, 0);
            this.setDead();
        }
        return true;
    }

    public void onClientUpdate() {
        EntityLivingBase riding = (EntityLivingBase) getControllingPassenger();

        if (riding instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) riding;
            setFacing(player.getLookVec());
            rotationYaw = player.rotationYaw;
            throttle = player.moveForward;
            strafe = player.moveStrafing;
        }
    }

    @Override
    public void entityInit() {
    }

    @Override
    public void readEntityFromNBT(@Nonnull NBTTagCompound compound) {
    }

    @Override
    public void writeEntityToNBT(@Nonnull NBTTagCompound compound) {
    }

    @Override
    public void fall(float distance, float damageMultiplier) {
    }

    @Override
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
        if (!world.isRemote && !player.isSneaking()) {
            player.startRiding(this);
            return true;
        } else {
            return false;
        }
    }

    public void setFacing(Vec3d f) {
        facing = f;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    public void sound(double momentum) {
        long rate = (long)MathHelper.clamp(3/momentum, 1, 10);
        if(momentum > 0.01 && tick >= rate) {
            playSound(sound, 1, (float)(momentum*1.5));
            tick = 0;
        }
        tick++;
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        super.onUpdate();
        this.tickLerp();
        this.fallDistance = 0;
        move(MoverType.SELF, motionX, motionY, motionZ);
        onClientUpdate();

        if (isBeingRidden()) {
            Vec3d forward = facing.scale(throttle);
            Vec3d side = Vec3d.fromPitchYaw(0, rotationYaw - 90).scale(strafe);
            Vec3d combined = forward.add(side).normalize();
            Vec3d motion = combined.scale(0.1);
            sound(new Vec3d(motionX, motionY, motionZ).lengthVector());

            motionX += motion.x;
            motionY += motion.y;
            motionZ += motion.z;
            motionX *= 0.9;
            motionY *= 0.9;
            motionZ *= 0.9;
        } else {
            motionX = 0;
            motionY = 0;
            motionZ = 0;
        }
        if (!world.isRemote) {
            Entity passenger = getControllingPassenger();
            if (passenger instanceof EntityPlayerMP) {
                try {
                    vehicleFloatingTickCount.setInt(((EntityPlayerMP) passenger).connection, 0);
                } catch (IllegalAccessException e) {
                    OpenHeli.logger.error("Failed to reset player's floating state.", e);
                }
            }
        }
    }

    private void tickLerp() {
        if (this.lerpSteps > 0 && !this.canPassengerSteer()) {
            double d0 = this.posX + (this.lerpX - this.posX) / (double) this.lerpSteps;
            double d1 = this.posY + (this.lerpY - this.posY) / (double) this.lerpSteps;
            double d2 = this.posZ + (this.lerpZ - this.posZ) / (double) this.lerpSteps;
            double d3 = MathHelper.wrapDegrees(this.lerpYaw - (double) this.rotationYaw);
            this.rotationYaw = (float) ((double) this.rotationYaw + d3 / (double) this.lerpSteps);
            this.rotationPitch = (float) ((double) this.rotationPitch + (this.lerpPitch - (double) this.rotationPitch) / (double) this.lerpSteps);
            --this.lerpSteps;
            this.setPosition(d0, d1, d2);
            this.setRotation(this.rotationYaw, this.rotationPitch);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
        this.lerpX = x;
        this.lerpY = y;
        this.lerpZ = z;
        this.lerpYaw = (double) yaw;
        this.lerpPitch = (double) pitch;
        this.lerpSteps = 10;
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    public void updatePassenger(@Nonnull Entity passenger) {
        super.updatePassenger(passenger);

        if (passenger instanceof EntityLivingBase) {
            EntityLivingBase entity = (EntityLivingBase) passenger;
            entity.renderYawOffset = rotationYaw;
        }
    }

    public void applyEntityCollision(@Nonnull Entity entityIn) {
        if (entityIn instanceof EntityObamaSphere) {
            if (entityIn.getEntityBoundingBox().minY < this.getEntityBoundingBox().maxY) {
                super.applyEntityCollision(entityIn);
            }
        } else {
            super.applyEntityCollision(entityIn);
        }
    }

    @Override
    protected boolean canBeRidden(Entity entityIn) {
        return true;
    }

    @Nullable
    public Entity getControllingPassenger() {
        List<Entity> list = this.getPassengers();
        return list.isEmpty() ? null : list.get(0);
    }

    public static SoundEvent initSound() {
        sound = new SoundEvent(new ResourceLocation("openheli","obama")).setRegistryName("obama");
        return sound;
    }
}

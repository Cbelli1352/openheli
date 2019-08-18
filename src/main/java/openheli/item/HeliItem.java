package openheli.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import openheli.OpenHeli;

public class HeliItem extends Item {
    public HeliItem(String registryName) {
        setRegistryName(registryName);
        setUnlocalizedName(OpenHeli.MODID + "." + registryName);
    }

    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}

package openheli.client.model;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;

public class FakeAtlasSprite extends TextureAtlasSprite {
    public FakeAtlasSprite(ResourceLocation location) {
        super(location.toString());
        this.width = 1;
        this.height = 1;
        initSprite(1, 1, 0, 0, false);
    }
}

package net.starlegacy.nethersky;

import net.minecraft.world.WorldProviderHell;

public class CandyWorldProvider extends WorldProviderHell {
    @Override
    public boolean shouldClientCheckLighting() {
        return false;
    }
}

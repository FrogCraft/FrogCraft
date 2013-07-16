package Spray;

import net.minecraft.world.World;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy{
    @Override
    public void registerRenderInformation(){
    	RenderingRegistry.registerEntityRenderingHandler(EntitySpray.class, new RenderSpray());
    }
    
    @Override
    public void registerTileEntitySpecialRenderer(/*PLACEHOLDER*/){}

    @Override
    public World getClientWorld(){return FMLClientHandler.instance().getClient().theWorld;}
}
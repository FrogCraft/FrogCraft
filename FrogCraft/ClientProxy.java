package FrogCraft;

import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;

import FrogCraft.Items.*;
import FrogCraft.Items.Railgun.EntityCoin;
import FrogCraft.Items.Railgun.RenderCoin;
import FrogCraft.Items.Railgun.RenderRailgun;
import FrogCraft.Machines2.ACWindMill.RenderACWindMillTop;
import FrogCraft.Machines2.ACWindMill.TileEntityACWindMillTop;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy{
	
    @Override
    public void registerRenderInformation(){
    	RenderingRegistry.registerEntityRenderingHandler(EntityCoin.class, new RenderCoin());
    	MinecraftForgeClient.registerItemRenderer(mod_FrogCraft.Railgun.itemID, new RenderRailgun());
    	
    	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityACWindMillTop.class, new RenderACWindMillTop());
    }

    @Override
    public void registerTileEntitySpecialRenderer(/*PLACEHOLDER*/){}

    @Override
    public World getClientWorld(){return FMLClientHandler.instance().getClient().theWorld;}
}

package Spray;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod( modid = "mod_FrogCraft_Spray", name="FrogCraft_Spray", version="1.0")
@NetworkMod(channels = { "mod_FrogCraft_Spray" },clientSideRequired = true,serverSideRequired = false)
public class mod_FrogCraft_Spray {

	@Instance("mod_FrogCraft_Spray")
	static mod_FrogCraft_Spray instance;
	
	@SidedProxy(clientSide = "Spray.ClientProxy", serverSide = "Spray.CommonProxy")
	public static CommonProxy proxy;
	
	static int id_ItemSpray;
	static Item_Spray Spray;
	
	@PreInit
    public void preInit(FMLPreInitializationEvent event) {	
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		id_ItemSpray=config.get("General", "Spray_Item_ID", 29745).getInt();
		Item_Spray.maxSpray=config.get("Generals", "Number_of_Spray", 2).getInt();
		
		Item_Spray.names=new String[Item_Spray.maxSpray];
		
		EntitySpray.GRIDS_WIDTHS=new float[Item_Spray.maxSpray];
		EntitySpray.GRIDS_HEIGHTS=new float[Item_Spray.maxSpray];	
		
		for (int i=0;i<Item_Spray.maxSpray;i++){
			Item_Spray.names[i]=config.get("Spray_Names", String.valueOf(i), "喷漆").getString();
			EntitySpray.GRIDS_WIDTHS[i]=(float) config.get("Spray_Width", String.valueOf(i), 2.0D).getDouble(2.0D);
			EntitySpray.GRIDS_HEIGHTS[i]=(float) config.get("Spray_Height", String.valueOf(i), 2.0D).getDouble(2.0D);			
		}	
		
		config.save();
		
		Spray=new Item_Spray(id_ItemSpray);
		GameRegistry.registerItem(Spray, "FrogCraft_Spray");
	}
	
	@Init
	public void load(FMLInitializationEvent event) {
		EntityRegistry.registerModEntity(EntitySpray.class, "Frogcraft_Spray", 0, this, 40, 5, true);
		EntityRegistry.registerGlobalEntityID(EntitySpray.class, "Frogcraft_Spray", 0);
		proxy.registerRenderInformation();
	}
}

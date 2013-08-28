package FrogCraft;

import FrogCraft.Blocks.BlockHNO3;
import FrogCraft.Blocks.WorldGenerator;
import FrogCraft.Common.*;
import FrogCraft.Intergration.GregTech;
import FrogCraft.Intergration.ic2;
import FrogCraft.Items.*;
import FrogCraft.Items.MobilePS.*;
import FrogCraft.Items.Railgun.*;
import FrogCraft.Machines.*;
import FrogCraft.Machines.IndustrialDevices.*;
import FrogCraft.Machines2.*;
import FrogCraft.Machines2.ACWindMill.*;
import FrogCraft.api.*;

import net.minecraft.block.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


@Mod( modid = "mod_FrogCraft", name="FrogCraft", version="162.1.5",dependencies = "required-after:IC2; after:gregtech_addon")
@NetworkMod(channels = { "mod_FrogCraft" },clientSideRequired = true,serverSideRequired = false,packetHandler = PacketHandler.class)
public class mod_FrogCraft {
	@SidedProxy(clientSide = "FrogCraft.ClientProxy", serverSide = "FrogCraft.CommonProxy")
	public static CommonProxy proxy;
	
	/**Instance of FrogCraft*/
	@Instance("mod_FrogCraft")
	public static mod_FrogCraft instance;
	
	/** Instance of all frogcraft achievements*/
	public static Achievements fcAchievements;					  
	
	//Configurations
	public static int rate_PneumaticCompressor;
	public static boolean boom_PneumaticCompressor;
	public static boolean rndboom_PneumaticCompressor;	

	//Dynamics
    public static int[][] sideAndFacingToSpriteOffset;
    
    /** For loading config only!*/
    private Configuration config;
	@EventHandler
    public void preInit(FMLPreInitializationEvent event) {					
		config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();

        //---------------------------------------------------------------------------------------
        RecipeRegister.usingPlate=config.get("generals", "usingPlate", true).getBoolean(true);
        RecipeRegister.TungstenSteel_PneumaticCompressor=config.get("generals", "TungstenSteel_PneumaticCompressor", true).getBoolean(true);
        RecipeRegister.SteelACWindMill=config.get("generals", "Steel_ACWindMill", true).getBoolean(true);
        
        RecipeRegister.ePC=config.get("Enable", "PneumaticCompressor", true).getBoolean(true);
        RecipeRegister.eAP=config.get("Enable", "AirPump", true).getBoolean(true);
        RecipeRegister.eIC=config.get("Enable", "IndustrialCompressor", true).getBoolean(true);
        RecipeRegister.eIM=config.get("Enable", "IndustrialMacerator", true).getBoolean(true);       
        RecipeRegister.eIE=config.get("Enable", "IndustrialExtractor", true).getBoolean(true);
        RecipeRegister.eIF=config.get("Enable", "IndustrialFurnance", true).getBoolean(true);
        RecipeRegister.eHSU=config.get("Enable", "HSU", true).getBoolean(true);
        RecipeRegister.eUHSU=config.get("Enable", "UHSU", true).getBoolean(true);        
        RecipeRegister.eEVT=config.get("Enable", "EVT", true).getBoolean(true);
        RecipeRegister.eL=config.get("Enable", "Liquifier", true).getBoolean(true);  
        RecipeRegister.eCT=config.get("Enable", "CondenseTower", true).getBoolean(true);        
        RecipeRegister.eTC=config.get("Enable", "ThermalCracker", true).getBoolean(true);
        RecipeRegister.eACR=config.get("Enable", "AdvanceChemicalReactor", true).getBoolean(true);         

        RecipeRegister.eACWM=config.get("Enable", "AcademicCityWindMill", true).getBoolean(true);
        RecipeRegister.eAWB=config.get("Enable", "AutoWorkBench", true).getBoolean(true);  
        RecipeRegister.eCF=config.get("Enable", "CombustionFurnace", true).getBoolean(true);  
        
        RecipeRegister.eR=config.get("Enable", "Railgun", true).getBoolean(true);
        RecipeRegister.eMPS=config.get("Enable", "MobilePowerSupply", true).getBoolean(true);  
        RecipeRegister.eNH3C60K=config.get("Enable", "NH3CoolantCell60K", true).getBoolean(true);
        RecipeRegister.eNH3C180K=config.get("Enable", "NH3CoolantCell180K", true).getBoolean(true);      
        RecipeRegister.eNH3C360K=config.get("Enable", "NH3CoolantCell360K", true).getBoolean(true);
        
        WorldGenerator.genCarnallite=config.get("Enable", "genCarnalliteOreUnderWater", true).getBoolean(true);
        WorldGenerator.genFluorapatite=config.get("Enable", "genFluorapatiteOreUnderGround", true).getBoolean(true);
        WorldGenerator.genDewalquite=config.get("Enable", "genDewalquiteOreUnderGround", true).getBoolean(true);        
        WorldGenerator.genClay=config.get("Enable", "genClayUnderGround", true).getBoolean(true);
        WorldGenerator.genRuby=config.get("Enable", "genRubyOreUnderWater", true).getBoolean(true);
        WorldGenerator.genSapphire=config.get("Enable", "genSapphireOreUnderGround", true).getBoolean(true);
        WorldGenerator.genGreenSapphire=config.get("Enable", "genGreenSapphireOreUnderGround", true).getBoolean(true);        
        WorldGenerator.genMarble=config.get("Enable", "genMarbleUnderGround", true).getBoolean(true);    
        WorldGenerator.genBasalt=config.get("Enable", "genBasaltUnderGround", true).getBoolean(true);        
        //Loading Configurations-----------------------------------------------------------------
        
        //PneumaticCompressor
        rate_PneumaticCompressor=config.get("Generals", "rate_PneumaticCompressor", 100).getInt();    
        boom_PneumaticCompressor=config.get("Generals", "boom_PneumaticCompressor", true).getBoolean(true); 
        rndboom_PneumaticCompressor=config.get("Generals", "rndboom_PneumaticCompressor", false).getBoolean(false); 
        
        //Railgun
        EntityCoin.dMax=(short) config.get("Generals", "Railgun_Max_Range", 40).getInt();
        EntityCoin.damageHit=(short)  config.get("Generals", "Railgun_Damage", 50).getInt();
        EntityCoin.explosion=(float)config.get("Generals", "Railgun_Explosive", 0d).getDouble(0d); 
        Item_Railgun.euPerShot=config.get("Generals", "Railgun_Eu_Per_Shot", 100000).getInt();
  
        //---------------------------------------------------------------------------------------
		fcItems.tabFrogCraft = new CreativeTabs("tabFrogCraft") {
	        public ItemStack getIconItemStack() {return new ItemStack(fcItems.Machines, 1, 8);}
	    };   
        
        ItemsRegister.loadItemsData();        
        regItems();
		fcAchievements=new Achievements();  //This is important to be placed here!!!!	
		
		config.save();
	}
	
	public void regItems(){	
		//Initialize Blocks
		fcItems.Ore=new FrogCraft.Blocks.BlockOre(getBlockID("Ore", 658));
		fcItems.ACWindMillCylinder = new BlockACWindMillCylinder(getBlockID("ACWindMillCylinder", 659));
		fcItems.MobilePS=new BlockMobilePS(getBlockID("MobilePS", 660));
		fcItems.Machines=new BlockMachines(getBlockID("Machines", 661));
		fcItems.Machines2=new BlockMachines2(getBlockID("Machines2", 662));
		
		//Initialize Items
		fcItems.Cells=new Item_Cells(getItemID("Cells", 19739));
		ItemsRegister.registerFluids();
		FluidManager.initFluids();
		fcItems.Ingots=new Item_Ingots(getItemID("Ingots", 19738));
		fcItems.Miscs=new Item_Miscs(getItemID("Miscs", 19740));
		fcItems.Dusts=new Item_Dusts(getItemID("Dusts", 19741));

		//Initialize Fluid Blocks
		fcItems.BlockHNO3= new BlockHNO3(getBlockID("HNO3", 657));

		fcItems.NitricAcidBucket=new Item_NitricAcidBucket(getItemID("NitricAcidBucket", 19734));
		fcItems.IC2Coolant_NH3_60K=new Item_IC2Coolant.Item_IC2CoolantNH3_60K(getItemID("IC2Coolant_NH3_60K", 19737));
		fcItems.IC2Coolant_NH3_180K=new Item_IC2Coolant.Item_IC2CoolantNH3_180K(getItemID("IC2Coolant_NH3_180K", 19736));	
		fcItems.IC2Coolant_NH3_360K=new Item_IC2Coolant.Item_IC2CoolantNH3_360K(getItemID("IC2Coolant_NH3_360K", 19735));		
		fcItems.Railgun=new Item_Railgun(getItemID("Railgun", 19744));
		fcItems.Fan=new Item_Fan(getItemID("Fan", 19745));
		fcItems.UBattery=new Item_RadioactiveDecayCell(getItemID("UBattery", 19746),"UBattery",1);
		fcItems.ThBattery=new Item_RadioactiveDecayCell(getItemID("id_ThBattery", 19747),"ThBattery",1);
		fcItems.PuBattery=new Item_RadioactiveDecayCell(getItemID("id_PuBattery", 19748),"PuBattery",1);

		//Register MetaBlocks
		GameRegistry.registerBlock(fcItems.Machines,ItemBlockMachines.class);	
		GameRegistry.registerBlock(fcItems.Machines2,ItemBlockMachines2.class);	
		GameRegistry.registerBlock(fcItems.Ore,FrogCraft.Blocks.ItemBlockOre.class);	
		
		//Register Normal Blocks	
		GameRegistry.registerBlock(fcItems.BlockHNO3);
		GameRegistry.registerBlock(fcItems.ACWindMillCylinder);		
		GameRegistry.registerBlock(fcItems.MobilePS,ItemBlock_MobilePS.class);
		
		//Register Items
		GameRegistry.registerItem(fcItems.NitricAcidBucket, "NitricAcidBucket");		
		GameRegistry.registerItem(fcItems.IC2Coolant_NH3_60K, "FrogCraft_IC2Coolant_NH3_60K");
		GameRegistry.registerItem(fcItems.IC2Coolant_NH3_180K, "FrogCraft_IC2Coolant_NH3_180K");
		GameRegistry.registerItem(fcItems.IC2Coolant_NH3_360K, "FrogCraft_IC2Coolant_NH3_360K");		
		GameRegistry.registerItem(fcItems.Railgun, "FrogCraft_Railgun");
		GameRegistry.registerItem(fcItems.Fan, "FrogCraft_id_ItemFan");
		GameRegistry.registerItem(fcItems.Ingots,"FrogCraft_Ingots");			
		GameRegistry.registerItem(fcItems.Cells,"FrogCraft_Cells");			
		GameRegistry.registerItem(fcItems.Miscs,"FrogCraft_Miscs");	
		GameRegistry.registerItem(fcItems.Dusts,"FrogCraft_Dusts");	
		GameRegistry.registerItem(fcItems.UBattery,"UBattery");
		GameRegistry.registerItem(fcItems.ThBattery,"ThBattery");
		GameRegistry.registerItem(fcItems.PuBattery,"PuBattery");
}
	
	@EventHandler
	public void load(FMLInitializationEvent event) {
		//Register TileEntities
		GameRegistry.registerTileEntity(SidedIC2Machine.class, "containerSidedIC2Machine");

		GameRegistry.registerTileEntity(TileEntityIndustrialDevice.class,"containerIndustrialDevice");
		GameRegistry.registerTileEntity(TileEntityIndustrialFurnance.class,"containerIndustrialFurnance");
		GameRegistry.registerTileEntity(TileEntityIndustrialMacerator.class,"containerIndustrialMacerator");
		GameRegistry.registerTileEntity(TileEntityIndustrialCompressor.class,"containerIndustrialCompressor");		
		GameRegistry.registerTileEntity(TileEntityIndustrialExtractor.class,"containerIndustrialExtractor");
		
		GameRegistry.registerTileEntity(TileEntityPneumaticCompressor.class, "containerPneumaticCompressor");
		GameRegistry.registerTileEntity(TileEntityAirPump.class, "containerAirPump");		
		GameRegistry.registerTileEntity(TileEntityHSU.class, "containerHSU");	
		GameRegistry.registerTileEntity(TileEntityUHSU.class, "containerUHSU");		
		GameRegistry.registerTileEntity(TileEntityEVTransformer.class, "containerEVTransformer");	
		GameRegistry.registerTileEntity(TileEntityLiquifier.class, "containerLiquifier");	
		GameRegistry.registerTileEntity(TileEntityLiquidInjector.class, "containerLiquidInjector");
		GameRegistry.registerTileEntity(TileEntityThermalCracker.class, "containerThermalCracker");
		GameRegistry.registerTileEntity(TileEntityAdvanceChemicalReactor.class, "containerAdvanceChemicalReactor");		
		
		GameRegistry.registerTileEntity(TileEntityACWindMillTop.class, "containerACWindMillTop");
		GameRegistry.registerTileEntity(TileEntityACWindMillBase.class, "containerACWindMillBase");		
		GameRegistry.registerTileEntity(TileEntityLiquidOutput.class, "containerLiquidOutput");
		GameRegistry.registerTileEntity(TileEntityAutoWorkBench.class, "containerAutoWorkBench");
		GameRegistry.registerTileEntity(TileEntityCombustionFurnace.class, "containerCombustionFurnace");
		
		GameRegistry.registerTileEntity(TileEntityMobilePS.class, "containerMobilePS");
		
		EntityRegistry.registerModEntity(EntityCoin.class, "Frogcraft_Railgun_Coin", 0, this, 40, 3, true);
		//EntityRegistry.registerGlobalEntityID(EntityCoin.class, "Frogcraft_Railgun_Coin", 0);
		
		GameRegistry.registerWorldGenerator(new WorldGenerator());
		proxy.registerRenderInformation();
		proxy.init();
	
		ic2.init();
		GregTech.init();
		ItemsRegister.registerOreDict();
		
		//Register Forge Event
		MinecraftForge.EVENT_BUS.register(fcItems.BlockHNO3);
	}
		
	@EventHandler
    public void postInit(FMLPostInitializationEvent event) {
		NetworkRegistry.instance().registerGuiHandler(this, new CommonProxy());
		
		Item_Railgun.AmmoID=ic2.getItem("coin").itemID;
		
		//TODO
		RecipeRegister.loadRecipes();

        GameRegistry.registerFuelHandler(new FuelHandler());
        
		try
        {
            sideAndFacingToSpriteOffset = (int[][])Class.forName("ic2.core.block.BlockMultiID").getField("sideAndFacingToSpriteOffset").get(null);
        }
        catch (Exception e)
        {
            sideAndFacingToSpriteOffset = new int[][]{
                    {
                        3, 2, 0, 0, 0, 0
                    }, {
                        2, 3, 1, 1, 1, 1
                    }, {
                        1, 1, 3, 2, 5, 4
                    }, {
                        0, 0, 2, 3, 4, 5
                    }, {
                        4, 5, 4, 5, 3, 2
                    }, {
                        5, 4, 5, 4, 2, 3
                    }
            };
        }
	}
	
	
	int getItemID(String name,int defaultValue){
		return config.get("Items", name, defaultValue).getInt();
	}
	
	int getBlockID(String name,int defaultValue){
		return config.get("Blocks", name, defaultValue).getInt();
	}	
}

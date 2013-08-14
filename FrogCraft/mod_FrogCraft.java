package FrogCraft;

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
import FrogCraft.Ore.WorldGenerator;
import FrogCraft.api.*;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraftforge.common.Configuration;
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
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


@Mod( modid = "mod_FrogCraft", name="FrogCraft", version="162.1.3",dependencies = "required-after:IC2; after:gregtech_addon")
@NetworkMod(channels = { "mod_FrogCraft" },clientSideRequired = true,serverSideRequired = false,packetHandler = PacketHandler.class)
public class mod_FrogCraft {
	@SidedProxy(clientSide = "FrogCraft.ClientProxy", serverSide = "FrogCraft.CommonProxy")
	public static CommonProxy proxy;
	
	@Instance("mod_FrogCraft")
	public static mod_FrogCraft instance;
	
	public static Achievements fcAchievements;
	
	//Configuration
	static int id_BlockOre,
			   id_ACWindMillCylinder,
			   id_BlockMobliePS,
			   id_BlockMachines,
			   id_BlockMachines2;
					  
	
	static int id_IC2Coolant_NH3_60K,
			   id_IC2Coolant_NH3_180K,
			   id_IC2Coolant_NH3_360K,
			   id_ItemIngots,
			   id_ItemCells,
			   id_ItemMiscs,
			   id_ItemDusts,
			   id_Railgun,
			   id_ItemFan,
			   id_UBattery,
			   id_ThBattery,
			   id_PuBattery;
	
	public static int rate_PneumaticCompressor;
	public static boolean boom_PneumaticCompressor;
	public static boolean rndboom_PneumaticCompressor;	
	
	//Blocks
	public static FrogCraft.Ore.BlockOre Ore;
	public static BlockFence ACWindMillCylinder;
	public static BlockMobilePS MobilePS;
	public static BlockMachines Machines;	
	public static BlockMachines2 Machines2;	
	public static Item_IC2Coolant IC2Coolant_NH3_60K;
	public static Item_IC2Coolant IC2Coolant_NH3_180K;
	public static Item_IC2Coolant IC2Coolant_NH3_360K;
	public static Item_Ingots Ingots;
	public static Item_Cells Cells;
	public static Item_Miscs Miscs;
	public static Item_Dusts Dusts;	
	public static Item_Railgun Railgun;
	public static Item_Fan Fan;
	public static Item_RadioactiveDecayCell UBattery,ThBattery,PuBattery;
	
	//Dynamics
    public static int[][] sideAndFacingToSpriteOffset;
	
    //Custom Creative Page
    public static CreativeTabs tabFrogCraft = new CreativeTabs("tabFrogCraft") {
        public ItemStack getIconItemStack() {return new ItemStack(Machines, 1, 8);}
    };   
    
    
	@EventHandler
    public void preInit(FMLPreInitializationEvent event) {					
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
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
        
        RecipeRegister.eR=config.get("Enable", "Railgun", true).getBoolean(true);
        RecipeRegister.eMPS=config.get("Enable", "MobilePowerSupply", true).getBoolean(true);  
        RecipeRegister.eNH3C60K=config.get("Enable", "NH3CoolantCell60K", true).getBoolean(true);
        RecipeRegister.eNH3C180K=config.get("Enable", "NH3CoolantCell180K", true).getBoolean(true);      
        RecipeRegister.eNH3C360K=config.get("Enable", "NH3CoolantCell360K", true).getBoolean(true);
        
        WorldGenerator.genCarnallite=config.get("Enable", "genCarnalliteOreUnderWater", true).getBoolean(true);
        WorldGenerator.genFluorapatite=config.get("Enable", "genFluorapatiteOreUnderGround", true).getBoolean(true);
        WorldGenerator.genClay=config.get("Enable", "genClayUnderGround", true).getBoolean(true);
        //---------------------------------------------------------------------------------------
        id_BlockOre=config.get("Blocks", "Ore", 658).getInt();
        id_ACWindMillCylinder=config.get("Blocks", "ACWindMillCylinder", 659).getInt();  
        id_BlockMobliePS=config.get("Blocks", "MobilePS", 660).getInt();  
        id_BlockMachines=config.get("Blocks", "Machines", 661).getInt();  
        id_BlockMachines2=config.get("Blocks", "Machines2", 662).getInt();  
        
        id_IC2Coolant_NH3_360K=config.get("Items", "IC2Coolant_NH3_360K", 19735).getInt();
        id_IC2Coolant_NH3_180K=config.get("Items", "IC2Coolant_NH3_180K", 19736).getInt();
        id_IC2Coolant_NH3_60K=config.get("Items", "IC2Coolant_NH3_60K", 19737).getInt();
        id_ItemIngots=config.get("Items", "Ingots", 19738).getInt();
        id_ItemCells=config.get("Items", "Cells", 19739).getInt();
        id_ItemMiscs=config.get("Items", "Miscs", 19740).getInt();
        id_ItemDusts=config.get("Items", "Dusts", 19741).getInt();
        id_Railgun=config.get("Items", "Railgun", 19744).getInt();
        id_ItemFan=config.get("Items", "Fan", 19745).getInt();     
        id_UBattery=config.get("Items", "UBattery", 19746).getInt();        
        id_ThBattery=config.get("Items", "id_ThBattery", 19747).getInt();   
        id_PuBattery=config.get("Items", "id_PuBattery", 19748).getInt(); 
        
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
        config.save();

        ItemsRegister.loadItemsData();        
  
        //---------------------------------------------------------------------------------------
        regItems();
		fcAchievements=new Achievements();  //This is important to be placed here!!!!	
	}
	
	public void regItems(){
		//Initialize Blocks
		Ore=new FrogCraft.Ore.BlockOre(id_BlockOre);
		fcItems.oreID=Ore.blockID;
		fcItems.acwindmillcylinder=ACWindMillCylinder = new BlockACWindMillCylinder(id_ACWindMillCylinder);
		fcItems.mobileps=MobilePS=new BlockMobilePS(id_BlockMobliePS);
		Machines=new BlockMachines(id_BlockMachines);
		fcItems.machineID=Machines.blockID;
		Machines2=new BlockMachines2(id_BlockMachines2);
		fcItems.machine2ID=Machines2.blockID;
		
		//Initialize Items
		Cells=new Item_Cells(id_ItemCells);
		fcItems.cellsID=Cells.itemID;		
		ItemsRegister.registerFluids();
		FluidManager.initFluids();
		Ingots=new Item_Ingots(id_ItemIngots);
		fcItems.ingotsID=Ingots.itemID;
		Miscs=new Item_Miscs(id_ItemMiscs);
		fcItems.miscsID=Miscs.itemID;
		Dusts=new Item_Dusts(id_ItemDusts);
		fcItems.dustsID=Dusts.itemID;
		
		fcItems.IC2Coolant_NH3_60K=IC2Coolant_NH3_60K=new Item_IC2Coolant.Item_IC2CoolantNH3_60K(id_IC2Coolant_NH3_60K);
		fcItems.IC2Coolant_NH3_180K=IC2Coolant_NH3_180K=new Item_IC2Coolant.Item_IC2CoolantNH3_180K(id_IC2Coolant_NH3_180K);	
		fcItems.IC2Coolant_NH3_360K=IC2Coolant_NH3_360K=new Item_IC2Coolant.Item_IC2CoolantNH3_360K(id_IC2Coolant_NH3_360K);		
		fcItems.railgun=Railgun=new Item_Railgun(id_Railgun);
		fcItems.fan=Fan=new Item_Fan(id_ItemFan);
		fcItems.UBattery=UBattery=new Item_RadioactiveDecayCell(id_UBattery,"UBattery",1);
		fcItems.ThBattery=ThBattery=new Item_RadioactiveDecayCell(id_ThBattery,"ThBattery",1);
		fcItems.PuBattery=PuBattery=new Item_RadioactiveDecayCell(id_PuBattery,"PuBattery",1);
		
		
		//Register MetaBlocks
		GameRegistry.registerBlock(Machines,ItemBlockMachines.class);	
		GameRegistry.registerBlock(Machines2,ItemBlockMachines2.class);	
		GameRegistry.registerBlock(Ore,FrogCraft.Ore.ItemBlockOre.class);	
		
		//Register Normal Blocks	
		GameRegistry.registerBlock(ACWindMillCylinder);		
		GameRegistry.registerBlock(MobilePS,ItemBlock_MobilePS.class);
		
		//Register Items
		GameRegistry.registerItem(IC2Coolant_NH3_60K, "FrogCraft_IC2Coolant_NH3_60K");
		GameRegistry.registerItem(IC2Coolant_NH3_180K, "FrogCraft_IC2Coolant_NH3_180K");
		GameRegistry.registerItem(IC2Coolant_NH3_360K, "FrogCraft_IC2Coolant_NH3_360K");		
		GameRegistry.registerItem(Railgun, "FrogCraft_Railgun");
		GameRegistry.registerItem(Fan, "FrogCraft_id_ItemFan");
		GameRegistry.registerItem(Ingots,"FrogCraft_Ingots");			
		GameRegistry.registerItem(Cells,"FrogCraft_Cells");			
		GameRegistry.registerItem(Miscs,"FrogCraft_Miscs");	
		GameRegistry.registerItem(Dusts,"FrogCraft_Dusts");	
		

		GameRegistry.registerItem(UBattery,"UBattery");
		GameRegistry.registerItem(ThBattery,"ThBattery");
		GameRegistry.registerItem(PuBattery,"PuBattery");
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
		
		GameRegistry.registerTileEntity(TileEntityMobilePS.class, "containerMobilePS");
		
		EntityRegistry.registerModEntity(EntityCoin.class, "Frogcraft_Railgun_Coin", 0, this, 40, 3, true);
		//EntityRegistry.registerGlobalEntityID(EntityCoin.class, "Frogcraft_Railgun_Coin", 0);
		
		GameRegistry.registerWorldGenerator(new WorldGenerator());
		proxy.registerRenderInformation();
		proxy.init();
	
		ic2.init();
		GregTech.init();
		OreDictRegister.registerOreDict();
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
	
}

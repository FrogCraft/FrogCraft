package FrogCraft;

import cpw.mods.fml.common.registry.GameRegistry;
import ic2.api.item.Items;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.src.ModLoader;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import FrogCraft.Common.RecipeManager;
import FrogCraft.Items.Item_Cells;
import FrogCraft.api.*;
import FrogCraft.api.fcItems.cls;
import gregtechmod.api.GregTech_API;

public class RecipeRegister {	
	public static boolean isGTLoaded;
	public static void loadRecipes(){
		isGTLoaded=ModLoader.isModLoaded("GregTech_Addon");
		loadMobilePSSolarUpdates();
		loadMobilePSEnergyUpdates();
		loadNormalRecipes();
		loadICMachineRecipes();
		loadGTMachineRecipes();
		
		//Condense Tower
		RecipeManager.addLiquidInjectorRecipe(new LiquidStack(fcItems.liquidsID, 100,0), 750, 10,  			    //Liquefied Air
										new LiquidStack(fcItems.gasesID, 1,1),           						//CO2
										new LiquidStack(mod_FrogCraft.GTGas,70,15),								//N2
										new LiquidStack(fcItems.gasesID, 28,0),           						//O2
										new LiquidStack(fcItems.gasesID, 1,2),           						//Ar
										null);
		
		RecipeManager.addLiquidInjectorRecipe(new LiquidStack(fcItems.liquidsID, 100,1), 640, 10,  			    //CoalTar
				new LiquidStack(fcItems.liquidsID, 1,3),           						//C6H6
				new LiquidStack(fcItems.gasesID, 100,5),								//CO
				new LiquidStack(mod_FrogCraft.GTGas,100,9),           					//CH4
				new LiquidStack(mod_FrogCraft.GTGas,100,0),								//H2
				null);							
		
		
		//AdvanceChemicalReactor
		//CaO+CO2=Ca(OH)2
		RecipeManager.addAdvanceChemicalReactorRecipe(Items.getItem("waterCell"), new ItemStack(fcItems.dustsID,1,0),null, null, null,new ItemStack(fcItems.dustsID,1,4), null, null, null, null,null,null,Items.getItem("cell"), 128, 10,-1);
		//Ca(OH)2+CO2=CaCO3+H2O
		RecipeManager.addAdvanceChemicalReactorRecipe(new ItemStack(fcItems.dustsID,1,4), new ItemStack(fcItems.cellsID,1,4),null, null, null,GregTech_API.getGregTechItem(1, 1, 4), Items.getItem("waterCell"), null, null, null, 128, 10);		
		//N2+3H2=2NH3
		RecipeManager.addAdvanceChemicalReactorRecipe(new ItemStack(mod_FrogCraft.GTCell,1,15),new ItemStack(mod_FrogCraft.GTCell,3,0),null,null,null,new ItemStack(fcItems.cellsID,2,0),null,null,null,null,fcItems.getItem(cls.misc, "AmmoniaModule"),null,Items.getItem("cell").copy().splitStack(2),5000,-1,100);
		//N2+2O2=2NO2
		RecipeManager.addAdvanceChemicalReactorRecipe(new ItemStack(fcItems.cellsID,2,2),new ItemStack(mod_FrogCraft.GTCell,1,15),null,null,null,new ItemStack(mod_FrogCraft.GTCell,2,38),null,null,null,null,fcItems.getItem(cls.misc, "HeatingModule"),null,Items.getItem("cell"),7000,-1,100);		
		//CO2+2NH3=CO(NH2)2+H2O
		RecipeManager.addAdvanceChemicalReactorRecipe(new ItemStack(fcItems.cellsID,1,4), new ItemStack(fcItems.cellsID,2,0),null,null,null,new ItemStack(fcItems.dustsID,1,1),Items.getItem("waterCell"),null,null,null,null,null,Items.getItem("cell").copy().splitStack(2),2000,50,1);
		//3NO2+H2O=2HNO3+NO
		RecipeManager.addAdvanceChemicalReactorRecipe(new ItemStack(mod_FrogCraft.GTCell,3,38), Items.getItem("waterCell"), null, null, null, new ItemStack(fcItems.cellsID,2,6), new ItemStack(fcItems.cellsID,1,7), null, null, null, null, null, Items.getItem("cell"), 800, 50,-1);
		//2NO+O2=2NO2
		RecipeManager.addAdvanceChemicalReactorRecipe(new ItemStack(fcItems.cellsID,2,7), new ItemStack(fcItems.cellsID,1,2), null, null, null, new ItemStack(mod_FrogCraft.GTCell,2,38), null, null, null, null, null, null, Items.getItem("cell"), 100, 10, -1);
		//HNO3+NH3=NH4NO3
		RecipeManager.addAdvanceChemicalReactorRecipe(new ItemStack(fcItems.cellsID,1,6), new ItemStack(fcItems.cellsID,1,0), null, null, null, new ItemStack(fcItems.dustsID,1,2), null, null, null, null, null, null, Items.getItem("cell").copy().splitStack(2), 100, 10, -1);
		//C+O2=CO2
		RecipeManager.addAdvanceChemicalReactorRecipe(ic2.api.item.Items.getItem("coalDust"), new ItemStack(fcItems.cellsID,1,2), null, null, null, new ItemStack(fcItems.cellsID,1,4), null, null, null, null, 5000, 100);
		RecipeManager.addAdvanceChemicalReactorRecipe(GregTech_API.getGregTechItem(1, 1, 240), new ItemStack(fcItems.cellsID,1,2), null, null, null, new ItemStack(fcItems.cellsID,1,4), null, null, null, null, 5000, 100);
		RecipeManager.addAdvanceChemicalReactorRecipe(new ItemStack(Item.coal,1,0), new ItemStack(fcItems.cellsID,1,2), null, null, null, new ItemStack(fcItems.cellsID,1,4), null, null, null, null, 7500, 100);
		RecipeManager.addAdvanceChemicalReactorRecipe(new ItemStack(Item.coal,1,1), new ItemStack(fcItems.cellsID,1,2), null, null, null, new ItemStack(fcItems.cellsID,1,4), null, null, null, null, 7500, 100);
		RecipeManager.addAdvanceChemicalReactorRecipe(GregTech_API.getGregTechItem(2, 1, 8), new ItemStack(fcItems.cellsID,1,2), null, null, null, new ItemStack(fcItems.cellsID,1,4), null, null, null, null,null,null,Items.getItem("cell"), 7500, 100, -1);
		//Ca3(PO4)2 + 3SiO2 + 5 C == 3 CaSiO3 + 5CO + 2P
		RecipeManager.addAdvanceChemicalReactorRecipe(GregTech_API.getGregTechItem(1, 1, 45), GregTech_API.getGregTechItem(1, 3, 7), GregTech_API.getGregTechItem(2, 1, 8), null, null, new ItemStack(fcItems.dustsID,3,3), new ItemStack(fcItems.cellsID,5,8), new ItemStack(fcItems.ingotsID,2,1), null, null,fcItems.getItem(cls.misc, "HeatingModule"),Items.getItem("cell").copy().splitStack(4),null, 32000,-1, 350);
		RecipeManager.addAdvanceChemicalReactorRecipe(GregTech_API.getGregTechItem(1, 2, 45), GregTech_API.getGregTechItem(1, 6, 7), GregTech_API.getGregTechItem(1, 1, 240), null, null, new ItemStack(fcItems.dustsID,6,3), new ItemStack(fcItems.cellsID,10,8), new ItemStack(fcItems.ingotsID,4,1), null, null,fcItems.getItem(cls.misc, "HeatingModule"),Items.getItem("cell").copy().splitStack(10),null, 32000,-1, 350);
		//2CO+O2=2CO2
		RecipeManager.addAdvanceChemicalReactorRecipe(new ItemStack(fcItems.cellsID,2,8), new ItemStack(fcItems.cellsID,1,2), null, null, null, new ItemStack(fcItems.cellsID,2,4), null,null,null,null,null,null,Items.getItem("cell"), 100, 10,-1);
		//CaSiO3=CaO+SiO2
		RecipeManager.addAdvanceChemicalReactorRecipe(new ItemStack(fcItems.dustsID,1,3),null,null,null,null,new ItemStack(fcItems.dustsID,1,0),GregTech_API.getGregTechItem(1, 1, 7),null,null,null,fcItems.getItem(cls.misc, "HeatingModule"),null,null,40000,400,-1);
		//C->raw carbon fiber
		RecipeManager.addAdvanceChemicalReactorRecipe(GregTech_API.getGregTechItem(2, 9, 8), null, null, null, null, Items.getItem("carbonFiber"), null, null, null, null,null,null,Items.getItem("cell").copy().splitStack(9), 1000, 50, -1);
		//Mg+Br2=MgBr2
		RecipeManager.addAdvanceChemicalReactorRecipe(GregTech_API.getGregTechItem(1, 1, 13),fcItems.getItem(cls.cell,"cell_Bromine",1),null,null,null,fcItems.getItem(cls.dust, "MgBr2", 1),null,null,null,null,null,null,Items.getItem("cell"),300,10,-1);
		//K+Cl2=KCl
		RecipeManager.addAdvanceChemicalReactorRecipe(GregTech_API.getGregTechItem(2, 2, 14),GregTech_API.getGregTechItem(2, 1, 13),null,null,null,fcItems.getItem(cls.dust, "KCl", 2),null,null,null,null,null,null,Items.getItem("cell").copy().splitStack(3),300,10,-1);		
		RecipeManager.addAdvanceChemicalReactorRecipe(GregTech_API.getGregTechItem(2, 1, 13),fcItems.getItem(cls.ingot, "K",2),null,null,null,fcItems.getItem(cls.dust, "KCl", 2),null,null,null,null,null,null,Items.getItem("cell"),300,10,-1);	
		//CaF2=Ca+F2
		RecipeManager.addAdvanceChemicalReactorRecipe(fcItems.getItem(cls.dust,"CaF2",1),null,null,null,null,GregTech_API.getGregTechItem(2, 1, 11),fcItems.getItem(cls.cell,"cell_Fluorine",1),null,null,null,fcItems.getItem(cls.misc, "ElectrolizeModule"),Items.getItem("cell").copy().splitStack(2),null,40000,-1,350);			
		//Ca+F2=CaF2
		RecipeManager.addAdvanceChemicalReactorRecipe(GregTech_API.getGregTechItem(2, 1, 11),fcItems.getItem(cls.cell,"cell_Fluorine",1),null,null,null,fcItems.getItem(cls.dust,"CaF2",1),null,null,null,null,null,null,Items.getItem("cell").copy().splitStack(2),100,10,-1);		
		
		
		//Thermal Cracker
		//Coal
		RecipeManager.addThermalCrackerRecipe(ic2.api.item.Items.getItem("coalDust"), 48, 80, new ItemStack(fcItems.miscsID,1,0), new LiquidStack(mod_FrogCraft.Liquids.itemID,200,1));
		RecipeManager.addThermalCrackerRecipe(GregTech_API.getGregTechItem(1, 1, 240), 48, 80, new ItemStack(fcItems.miscsID,1,0), new LiquidStack(mod_FrogCraft.Liquids.itemID,200,1));		
		
		//Stone->Cao+CO2
		RecipeManager.addThermalCrackerRecipe(new ItemStack(Block.stone,1), 64, 150, new ItemStack(fcItems.dustsID,1,0), new LiquidStack(fcItems.gasesID,500,1));
		RecipeManager.addThermalCrackerRecipe(new ItemStack(Block.cobblestone,1), 64, 100, new ItemStack(fcItems.dustsID,1,0), new LiquidStack(fcItems.gasesID,500,1));	
	
		//CaCO3->CaO+CO2
		RecipeManager.addThermalCrackerRecipe(GregTech_API.getGregTechItem(1, 1, 4), 30, 100, new ItemStack(fcItems.dustsID,1,0), new LiquidStack(fcItems.gasesID,1000,1));	
	}
		
	static void loadNormalRecipes(){//Mg 13 Al18 MgAl13
		CraftingManager m=CraftingManager.getInstance();
		//Dusts
		regUnShapedRecipe(fcItems.getItem(cls.dust, "Magnalium", 1),new Object[]{"dustMagnesium","dustAluminium","dustAluminium"});
		
		//Modules
		//Electrolize Module
		regRecipe(new ItemStack(fcItems.miscsID,1,4),new Object[]{" m "," o "," e ",'m',Items.getItem("magnetizer"),'e',Items.getItem("electrolyzer"),'o',Items.getItem("advancedCircuit")});
		//Heating Module
		if (isGTLoaded)
			regUnShapedRecipe(new ItemStack(fcItems.miscsID,1,5),new Object[]{GregTech_API.getGregTechItem(3, 1, 21),Items.getItem("advancedCircuit")});		
		//Ammonia Catalyst
		regUnShapedRecipe(new ItemStack(fcItems.miscsID,1,6),new Object[]{new ItemStack(fcItems.miscsID,1,5),Items.getItem("refinedIronIngot")});	
		
		//Mobile Power Supply
		regRecipe(new ItemStack(fcItems.mobileps),new Object[]{"iei","ibi","ifi",'b',Items.getItem("batBox"),'f',Items.getItem("electroFurnace"),'e',Items.getItem("advancedCircuit"),'i',"ingotRefinedIron"});
		regRecipe(new ItemStack(fcItems.mobileps),new Object[]{"iei","ibi","ifi",'b',Items.getItem("batBox"),'f',Items.getItem("electroFurnace"),'e',Items.getItem("advancedCircuit"),'i',"ingotAluminium"});	
		
		//Cooling Cell
		regRecipe(new ItemStack(fcItems.IC2Coolant_NH3_60K),new Object[]{" t ","tnt"," t ",'t',"ingotTin",'n',new ItemStack(fcItems.cellsID,1,0)});
		regRecipe(new ItemStack(fcItems.IC2Coolant_NH3_180K),new Object[]{"ttt","nnn","ttt",'t',"ingotTin",'n',new ItemStack(fcItems.IC2Coolant_NH3_60K)});
		regRecipe(new ItemStack(fcItems.IC2Coolant_NH3_360K),new Object[]{"tnt","tct","tnt",'t',"ingotTin",'n',new ItemStack(fcItems.IC2Coolant_NH3_180K),'c',Items.getItem("denseCopperPlate")});		
		
		if (isGTLoaded){
			//Pneumatic Compressor
			m.addRecipe(new ItemStack(fcItems.machineID,1,0),new Object[]{"mam","ece","mam",'a',GregTech_API.getGregTechBlock(0, 1, 10),'e',Items.getItem("advancedCircuit"),'c',Items.getItem("compressor"),'m',Items.getItem("advancedAlloy")});
			//Gas pump
			regRecipe(new ItemStack(fcItems.machineID,1,1),new Object[]{"xdx","xpx","xax",'p',Items.getItem("pump"),'a',"craftingRawMachineTier02",'d',Items.getItem("advancedCircuit"),'x',Items.getItem("cell")});		
			//Industrial Version
			regRecipe(new ItemStack(fcItems.machineID,1,2),new Object[]{"rbr","dcd","rar",'c',Items.getItem("compressor"),'b',Block.chest,'a',"craftingRawMachineTier02",'d',Items.getItem("advancedCircuit"),'r',Items.getItem("reinforcedStone")});
			regRecipe(new ItemStack(fcItems.machineID,1,3),new Object[]{"rbr","dcd","rar",'c',Items.getItem("macerator"),'b',Block.chest,'a',"craftingRawMachineTier02",'d',Items.getItem("advancedCircuit"),'r',Block.obsidian});
			regRecipe(new ItemStack(fcItems.machineID,1,4),new Object[]{"rbr","dcd","rar",'c',Items.getItem("extractor"),'b',Block.chest,'a',"craftingRawMachineTier02",'d',Items.getItem("advancedCircuit"),'r',Items.getItem("electrolyzedWaterCell")});		
			//Industrial Furnace
			m.addRecipe(new ItemStack(fcItems.machineID,1,5),new Object[]{" b ","dcd"," r ",'c',Items.getItem("inductionFurnace"),'b',Block.chest,'d',Items.getItem("advancedCircuit"),'r',GregTech_API.getGregTechItem(3, 1, 21)});
			//HSU
			regRecipe(new ItemStack(fcItems.machineID,1,6),new Object[]{"ete","lml","eae",'a',"craftingRawMachineTier02",'e',GregTech_API.getGregTechItem(3, 1, 0),'m',Items.getItem("mfsUnit"),'t',Items.getItem("hvTransformer"),'l',Block.blockLapis});	
			//UHSU
			regRecipe(new ItemStack(fcItems.machineID,1,7),new Object[]{"ltl","lul","lal",'a',GregTech_API.getGregTechBlock(0, 1, 10),'u',new ItemStack(fcItems.machineID,1,6),'t',new ItemStack(fcItems.machineID,1,8),'l',"chunkLazurite"});
			//EV Transformer
			m.addRecipe(new ItemStack(fcItems.machineID,1,8),new Object[]{"www","ctc","www",'t',Items.getItem("hvTransformer"),'c',Items.getItem("advancedCircuit"),'w',Items.getItem("trippleInsulatedIronCableItem")});
			//Liquefier
			regRecipe(new ItemStack(fcItems.machineID,1,9),new Object[]{"lpl","lcl","eae",'a',"craftingRawMachineTier02",'c',Items.getItem("compressor"),'p',Items.getItem("pump"),'e',Items.getItem("electronicCircuit"),'l',Items.getItem("cell")});
			//CondenseTower
			regRecipe(new ItemStack(fcItems.machineID,1,10),new Object[]{"idi","cpc","cac",'a',"craftingRawMachineTier02",'i',Items.getItem("miningPipe"),'d',Items.getItem("advancedCircuit"),'p',Items.getItem("pump"),'c',Items.getItem("cell")});
			//ThermalCracker
			regRecipe(new ItemStack(fcItems.machineID,1,11),new Object[]{"cdc","cec","rar",'a',"craftingRawMachineTier02",'d',Items.getItem("advancedCircuit"),'c',Items.getItem("cell"),'r',GregTech_API.getGregTechItem(3, 1, 21),'e',Items.getItem("extractor")});
			//AdvanceChemicalReactor
			regRecipe(new ItemStack(fcItems.machineID,1,12),new Object[]{"sms","cec","sas",'a',"craftingRawMachineTier02",'m',Items.getItem("magnetizer"),'e',Items.getItem("extractor"),'s',"plateSteel",'c',Items.getItem("advancedCircuit")});
			
			
			//LiquidOutput
			m.addRecipe(new ItemStack(fcItems.machine2ID,1,0),new Object[]{"rir","rgr","rir",'r',Items.getItem("refinedIronIngot"),'i',Items.getItem("miningPipe"),'g',Items.getItem("reinforcedGlass")});
			m.addRecipe(new ItemStack(fcItems.machine2ID,1,0),new Object[]{"rir","rgr","rir",'r',GregTech_API.getGregTechItem(0, 1, 18),'i',Items.getItem("miningPipe"),'g',Items.getItem("reinforcedGlass")});
			//CondenseTowerCylinder
			m.addRecipe(new ItemStack(fcItems.machine2ID,1,1),new Object[]{"rir","rir","rir",'r',Items.getItem("refinedIronIngot"),'i',Items.getItem("miningPipe")});	
			m.addRecipe(new ItemStack(fcItems.machine2ID,1,1),new Object[]{"rir","rir","rir",'r',GregTech_API.getGregTechItem(0, 1, 18),'i',Items.getItem("miningPipe")});
		}
		
		//WindMill
		m.addRecipe(new ItemStack(fcItems.fan),new Object[]{" f "," f ","f f",'f',Items.getItem("ironFence")});
		m.addRecipe(new ItemStack(fcItems.acwindmillcylinder,12),new Object[]{"f f","f f","f f",'f',Items.getItem("ironFence")});
		//Top
		regRecipe(new ItemStack(fcItems.machine2ID,1,2),new Object[]{"crc","rwr","crc", 'c', "ingotCopper",'w',Items.getItem("windMill"),'r',"dyeRed"});
        //Base   
		regRecipe(new ItemStack(fcItems.machine2ID,1,3),new Object[]{" i ","cac"," e ", 'c', Items.getItem("insulatedCopperCableItem"),'a',"craftingRawMachineTier02",'e',Items.getItem("electronicCircuit"),'i',new ItemStack(fcItems.acwindmillcylinder)});        	
	
        //RailGun
        regRecipe(new ItemStack(fcItems.miscsID,1,2),new Object[]{"scs","sts","scs",'s',"craftingSuperconductor",'c',"crafting360kCoolantStore",'t',new ItemStack(fcItems.machineID,1,8)});
        regRecipe(new ItemStack(fcItems.miscsID,1,2),new Object[]{"sss","ctc","sss",'s',"craftingSuperconductor",'c',"crafting360kCoolantStore",'t',new ItemStack(fcItems.machineID,1,8)});       
        regRecipe(new ItemStack(fcItems.railgun),new Object[]{"igi","oco","ili",'c',new ItemStack(fcItems.miscsID,1,2),'l',"crafting1kkEUStore",'g',new ItemStack(fcItems.miscsID,1,3),'o',Items.getItem("overclockerUpgrade"),'i',"plateAlloyIridium"});
	
        
        //GoldClod
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fcItems.miscsID,1,3),new Object[]{"ingotPotassium","ingotPotassium","ingotPotassium","gemPhosphor","gemPhosphor","gemPhosphor","molecule_1n","molecule_1n","molecule_1n"}));
	
        //GunPowder
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Item.gunpowder,2),new Object[]{"dustAmmoniumNitrate","dustAmmoniumNitrate","dustCharcoal","craftingSulfurToGunpowder"}));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Item.gunpowder,4),new Object[]{"dustAmmoniumNitrate","dustAmmoniumNitrate","dustCoal","craftingSulfurToGunpowder"}));	
	}
		
	static void loadGTMachineRecipes(){
		//Generator------------------------------------------------------------
		//Simifluid - CoalTar
		GregTech_API.addFuel(new ItemStack(fcItems.cellsID,1,1), Items.getItem("cell"), 10, 0);
		//Simifluid - Benzyl
		GregTech_API.addFuel(new ItemStack(fcItems.cellsID,1,9), Items.getItem("cell"), 10, 0);
		//Simifluid - Bromine
		GregTech_API.addFuel(new ItemStack(fcItems.cellsID,1,10), Items.getItem("cell"), 10, 0);
		//Gas - CO
		GregTech_API.addFuel(new ItemStack(fcItems.cellsID,1,8), Items.getItem("cell"), 30, 1);
		//Plasma - GoldClod
		GregTech_API.addFuel(new ItemStack(fcItems.miscsID,1,3), null, 100, 5);
		
		//Freezer--------------------------------------------------------------
		GregTech_API.addVacuumFreezerRecipe(new ItemStack(fcItems.IC2Coolant_NH3_60K), new ItemStack(fcItems.IC2Coolant_NH3_60K), 100);
		GregTech_API.addVacuumFreezerRecipe(new ItemStack(fcItems.IC2Coolant_NH3_180K), new ItemStack(fcItems.IC2Coolant_NH3_180K), 100);
		GregTech_API.addVacuumFreezerRecipe(new ItemStack(fcItems.IC2Coolant_NH3_360K), new ItemStack(fcItems.IC2Coolant_NH3_360K), 100);
		
		//Implosion Compressor-------------------------------------------------
		//Lapis dust 2 lapis
		GregTech_API.addImplosionRecipe(GregTech_API.getGregTechItem(1, 8, 2), 1, new ItemStack(Item.dyePowder.itemID,8,4), GregTech_API.getGregTechItem(1, 1, 63));
		
		//Centrifuge------------------------------------------------------------
		//Potassium K
		GregTech_API.addCentrifugeRecipe(GregTech_API.getGregTechItem(2, 1, 14), 0, Items.getItem("cell"), new ItemStack(fcItems.ingotsID,1,0), null, null, 20);
		//Magnalium
		GregTech_API.addCentrifugeRecipe(fcItems.getItem(cls.dust, "Magnalium", 1), 0, GregTech_API.getGregTechItem(1, 1, 13), GregTech_API.getGregTechItem(1, 2, 18), null, null, 300);
		//Carnallite
		GregTech_API.addCentrifugeRecipe(fcItems.getItem(cls.dust, "Carnallite", 5), 8, fcItems.getItem(cls.dust, "KCl", 4), fcItems.getItem(cls.dust, "MgBr2", 1), Items.getItem("waterCell").copy().splitStack(8), null, 40);
		
		//Electrolizer-----------------------------------------------------------
		//KCl
		GregTech_API.addElectrolyzerRecipe(fcItems.getItem(cls.dust, "KCl", 2), 2, GregTech_API.getGregTechItem(2, 2, 14), GregTech_API.getGregTechItem(2, 1, 13), null, null, 120, 64);
		//MgBr2
		GregTech_API.addElectrolyzerRecipe(fcItems.getItem(cls.dust, "MgBr2", 1), 1, GregTech_API.getGregTechItem(1, 1, 13), fcItems.getItem(cls.cell,"cell_Bromine",1), null, null, 100, 64);		
		//Fluorapatite
		GregTech_API.addElectrolyzerRecipe(fcItems.getItem(cls.dust, "Fluorapatite", 2), 0, fcItems.getItem(cls.dust, "CaF2", 1), GregTech_API.getGregTechItem(1, 2, 45), null, null, 100,64);		
		
		//Canner-----------------------------------------------------------------
		//K
		GregTech_API.addCannerRecipe(new ItemStack(fcItems.ingotsID,1,0), Items.getItem("cell"), GregTech_API.getGregTechItem(2, 1, 14), null, 5, 1);	

		//Instrial Grinder-------------------------------------------------------
		GregTech_API.addGrinderRecipe(new ItemStack(fcItems.oreID,1,0), -1, fcItems.getItem(cls.dust, "Carnallite", 8), new ItemStack(Block.dirt,1), null, null);
		GregTech_API.addGrinderRecipe(new ItemStack(fcItems.oreID,1,1), -1, fcItems.getItem(cls.dust, "Fluorapatite", 8), GregTech_API.getGregTechItem(4, 1, 36), null, null);
				
		
		//Blast-------------------------------------------------------------------
		//Mg+2Al=MgAl2
		GregTech_API.addBlastRecipe(fcItems.getItem(cls.dust, "Magnalium", 1), null, GregTech_API.getGregTechItem(0, 1, 13), null, 150, 64, 1900);

		//Assembler---------------------------------------------------------------
		GregTech_API.addAssemblerRecipe(GregTech_API.getGregTechItem(0, 8, 13), null, Items.getItem("advancedMachine").splitStack(2), 500, 1);
	}
	
	static void loadICMachineRecipes(){		
		//General
		if (isGTLoaded){ //GT Items
			//Super Conductor
			ic2.api.recipe.Recipes.advRecipes.addRecipe(GregTech_API.getGregTechItem(3, 4, 2), new Object[]{"lll","wiw","fff",'l',new ItemStack(fcItems.IC2Coolant_NH3_60K),'w',GregTech_API.getGregTechItem(0, 1, 80),'i',Items.getItem("iridiumPlate"),'f',GregTech_API.getGregTechItem(3, 1, 0)});
			//AdvanceMachine
			ic2.api.recipe.Recipes.advRecipes.addRecipe(Items.getItem("advancedMachine").splitStack(2), new Object[]{"xxx","x x","xxx",'x',GregTech_API.getGregTechItem(0, 1, 13)});
		}
		
		//IC Items
		//fertilizer
		regUnShapedRecipe(Items.getItem("fertilizer").splitStack(16),new Object[]{fcItems.getItem(cls.dust, "Urea"),Items.getItem("fertilizer")});
		regUnShapedRecipe(Items.getItem("fertilizer").splitStack(64),new Object[]{fcItems.getItem(cls.misc, "GoldClod"),Items.getItem("fertilizer")});
		//overclockerUpgrade
		ic2.api.recipe.Recipes.advRecipes.addRecipe(Items.getItem("overclockerUpgrade").splitStack(2),new Object[]{" o ","lal","   ",'a',Items.getItem("electronicCircuit"),'l',Items.getItem("insulatedCopperCableItem"),'o',fcItems.IC2Coolant_NH3_60K});
		
		//IC2 Compressor Recipes
		ic2.api.recipe.Recipes.compressor.addRecipe(new ItemStack(fcItems.miscsID,8,0), new ItemStack(fcItems.miscsID,1,1));
		
		//IC2 Extractor Recipes
		for (int i=0;i<Item_Cells.itemsData.size();i++){
			ic2.api.recipe.Recipes.extractor.addRecipe(new ItemStack(fcItems.cellsID,1,i), Items.getItem("cell"));
		}
		ic2.api.recipe.Recipes.extractor.addRecipe(Items.getItem("hydratingCell"),Items.getItem("cell"));
		
		//IC2 Macerator Recipes
		//Magnalium
		ic2.api.recipe.Recipes.macerator.addRecipe(GregTech_API.getGregTechItem(0, 1, 13), fcItems.getItem(cls.dust, "Magnalium", 1));
		//Carnallite
		ic2.api.recipe.Recipes.macerator.addRecipe(new ItemStack(fcItems.oreID,1,0), fcItems.getItem(cls.dust, "Carnallite", 2));	
		//Fluorapatite
		ic2.api.recipe.Recipes.macerator.addRecipe(new ItemStack(fcItems.oreID,1,1), fcItems.getItem(cls.dust, "Fluorapatite", 2));	
	}
	
	
	//Common stuff------------------------------------------------------------------------------------------------------------------
	static void loadMobilePSEnergyUpdates() {
		RecipeManager.addMobilePSEnergyUpdate(ic2.api.item.Items.getItem("energyStorageUpgrade"), 10000);
		RecipeManager.addMobilePSEnergyUpdate(GregTech_API.getGregTechItem(3, 1, 26), 100000);		//Lithium Update
		RecipeManager.addMobilePSEnergyUpdate(GregTech_API.getGregTechItem(3, 1, 12), 100000);       //Energy Crystal Update
		RecipeManager.addMobilePSEnergyUpdate(GregTech_API.getGregTechItem(3, 1, 13), 1000000);		//Lapotron Update
		RecipeManager.addMobilePSEnergyUpdate(GregTech_API.getGregTechItem(3, 1, 14), 10000000);		//Orb Update		
	}

	static void loadMobilePSSolarUpdates(){
		RecipeManager.addMobilePSSolarUpdate(ic2.api.item.Items.getItem("solarPanel"), 1, 0);        //IC Solar Panel
		RecipeManager.addMobilePSSolarUpdateI(GregTech_API.getGregTechItem(3, 1, 7), 1, 0);           //GT Solar Component
        
		try {
			Block advSolarPanel = (Block) Class.forName("advsolar.AdvancedSolarPanel").getField("blockAdvSolarPanel").get(null);  //Advance Solar Panel
			RecipeManager.addMobilePSSolarUpdate(new ItemStack(advSolarPanel,1,0), 8, 1);
			RecipeManager.addMobilePSSolarUpdate(new ItemStack(advSolarPanel,1,1), 64, 8); 
			RecipeManager.addMobilePSSolarUpdate(new ItemStack(advSolarPanel,1,2), 512, 64);  				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			Block compactSolarBlock = (Block) Class.forName("cpw.mods.compactsolars.CompactSolars").getField("compactSolarBlock").get(null);//Compact Solar Panel
			RecipeManager.addMobilePSSolarUpdate(new ItemStack(compactSolarBlock,1,0), 8, 0);
			RecipeManager.addMobilePSSolarUpdate(new ItemStack(compactSolarBlock,1,1), 64, 0); 
			RecipeManager.addMobilePSSolarUpdate(new ItemStack(compactSolarBlock,1,2), 512, 0);  				
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	static void regUnShapedRecipe(ItemStack result,Object[] par){
		GameRegistry.addRecipe(new ShapelessOreRecipe(result,par));
	}	
	
	static void regRecipe(ItemStack result,Object[] par){
		GameRegistry.addRecipe(new ShapedOreRecipe(result,true,par));
	}
}

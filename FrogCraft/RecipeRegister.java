package FrogCraft;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.src.ModLoader;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import FrogCraft.Common.*;
import FrogCraft.Intergration.*;
import FrogCraft.Items.Item_Cells;
import FrogCraft.api.*;
import FrogCraft.api.fcItems.cls;


public class RecipeRegister {	
	public static String refinedIron="ingotRefinedIron",aluminium="ingotAluminium";
	
	public static boolean usingPlate=true,
			TungstenSteel_PneumaticCompressor=true,
			SteelACWindMill=true;
	
	public static boolean ePC,eAP,eIC,eIM,eIE,eIF,eHSU,eUHSU,eEVT,eL,eCT,eTC,eACR, //Machine1
						  eACWM,eAWB,                                               //Machine2
						  eR,eMPS,eNH3C60K,eNH3C180K,eNH3C360K;                    //Other stuff
	
	public static void loadRecipes(){
		if(GregTech.isGTLoaded&&usingPlate){
			refinedIron="plateRefinedIron";
			aluminium="plateAluminium";
		}
		
		loadMobilePSSolarUpdates();
		loadMobilePSEnergyUpdates();
		loadNormalRecipes();
		loadMachineRecipes();
		loadICMachineRecipes();
		loadGTMachineRecipes();
	}
		
	static void loadNormalRecipes(){
		CraftingManager m=CraftingManager.getInstance();
		//Dusts
		ic2.regUnshaped(fcItems.getItem(cls.dust, "Magnalium", 3),new Object[]{"dustMagnesium","dustAluminium","dustAluminium"});
	
		
		//Modules
		//Electrolize Module
		ic2.regShaped(new ItemStack(fcItems.miscsID,1,4),new Object[]{" m "," o "," e ",'m',ic2.getItem("magnetizer"),'e',ic2.getItem("electrolyzer"),'o',"craftingCircuitTier04"});
		//Heating Module
		if (GregTech.isGTLoaded)
			ic2.regUnshaped(new ItemStack(fcItems.miscsID,1,5),new Object[]{GregTech.getGTComponent(21, 1),"craftingCircuitTier04"});		
		else
			ic2.regUnshaped(new ItemStack(fcItems.miscsID,1,5), new Object[]{"craftingElectricFurnace","craftingCircuitTier04"});
		//Ammonia Catalyst
		ic2.regShaped(new ItemStack(fcItems.miscsID,1,6),new Object[]{"iii"," c ","iii",'c',new ItemStack(fcItems.miscsID,1,5),'i',refinedIron});	
		
		//Mobile Power Supply
		if(eMPS)ic2.regShaped(new ItemStack(fcItems.mobileps),new Object[]{"iei","ibi","ifi",'b',ic2.getItem("batBox"),'f',"craftingElectricFurnace",'e',"craftingCircuitTier04",'i',refinedIron});
		if(eMPS)ic2.regShaped(new ItemStack(fcItems.mobileps),new Object[]{"iei","ibi","ifi",'b',ic2.getItem("batBox"),'f',"craftingElectricFurnace",'e',"craftingCircuitTier04",'i',aluminium});	
		
		//Cooling Cell
		if(eNH3C60K)ic2.regShaped(new ItemStack(fcItems.IC2Coolant_NH3_60K),new Object[]{" t ","tnt"," t ",'t',"ingotTin",'n',new ItemStack(fcItems.cellsID,1,0)});
		if(eNH3C180K)ic2.regShaped(new ItemStack(fcItems.IC2Coolant_NH3_180K),new Object[]{"ttt","nnn","ttt",'t',"ingotTin",'n',new ItemStack(fcItems.IC2Coolant_NH3_60K)});
		if(eNH3C360K)ic2.regShaped(new ItemStack(fcItems.IC2Coolant_NH3_360K),new Object[]{"tnt","tct","tnt",'t',"ingotTin",'n',new ItemStack(fcItems.IC2Coolant_NH3_180K),'c',ic2.getItem("denseCopperPlate")});		
		
		//Pneumatic Compressor
		if(ePC){
			if(TungstenSteel_PneumaticCompressor)
				ic2.regShaped(new ItemStack(fcItems.machineID,1,0),new Object[]{"mam","ece","mam",'a',"craftingRawMachineTier04",'e',"craftingCircuitTier04",'c',"craftingCompressor",'m',"plateTungstenSteel"});		
			else
				ic2.regShaped(new ItemStack(fcItems.machineID,1,0),new Object[]{"mam","ece","mam",'a',"craftingRawMachineTier04",'e',"craftingCircuitTier04",'c',"craftingCompressor",'m',"plateAlloyAdvanced"});
		}
		//Gas pump
		if(eAP)ic2.regShaped(new ItemStack(fcItems.machineID,1,1),new Object[]{"mdm","xpx","mam",'p',"craftingPump",'a',"craftingRawMachineTier02",'d',"craftingCircuitTier04",'x',ic2.getItem("cell"),'m',"plateAlloyAdvanced"});		
		
		//Industrial Version
		if(GregTech.isGTLoaded){
			if(eIC)ic2.regShaped(new ItemStack(fcItems.machineID,1,2),new Object[]{"rbr","dcd","rar",'c',"craftingCompressor",'b',"craftingConveyor",'a',"craftingRawMachineTier02",'d',"craftingCircuitTier04",'r',ic2.getItem("reinforcedStone")});
			if(eIM)ic2.regShaped(new ItemStack(fcItems.machineID,1,3),new Object[]{"dbd","ocr","dad",'c',"craftingMacerator",'b',"craftingConveyor",'a',"craftingRawMachineTier02",'d',"craftingCircuitTier04",'r',Block.obsidian,'o',"craftingGrinder"});
			if(eIE)ic2.regShaped(new ItemStack(fcItems.machineID,1,4),new Object[]{"rbr","dcd","rar",'c',"craftingExtractor",'b',"craftingConveyor",'a',"craftingRawMachineTier02",'d',"craftingCircuitTier04",'r',ic2.getItem("electrolyzedWaterCell")});	
			if(eIF)ic2.regShaped(new ItemStack(fcItems.machineID,1,5),new Object[]{"mbm","dcd","mrm",'c',"craftingInductionFurnace",'b',"craftingConveyor",'d',"craftingCircuitTier04",'r',"craftingHeatingCoilTier00",'m',"plateAlloyAdvanced"});
		}
		else{
			if(eIC)ic2.regShaped(new ItemStack(fcItems.machineID,1,2),new Object[]{"rbr","dcd","rar",'c',"craftingCompressor",'b',Block.chest,'a',"craftingRawMachineTier02",'d',"craftingCircuitTier04",'r',ic2.getItem("reinforcedStone")});
			if(eIM)ic2.regShaped(new ItemStack(fcItems.machineID,1,3),new Object[]{"rbr","dcd","rar",'c',"craftingMacerator",'b',Block.chest,'a',"craftingRawMachineTier02",'d',"craftingCircuitTier04",'r',Block.obsidian});
			if(eIE)ic2.regShaped(new ItemStack(fcItems.machineID,1,4),new Object[]{"rbr","dcd","rar",'c',"craftingExtractor",'b',Block.chest,'a',"craftingRawMachineTier02",'d',"craftingCircuitTier04",'r',ic2.getItem("electrolyzedWaterCell")});	
			if(eIF)ic2.regShaped(new ItemStack(fcItems.machineID,1,5),new Object[]{"mbm","dcd","mrm",'c',"craftingInductionFurnace",'b',Block.chest,'d',"craftingCircuitTier04",'r',"craftingHeatingCoilTier00",'m',"plateAlloyAdvanced"});
		}

		if(GregTech.isGTLoaded){
			//HSU
			if(eHSU)ic2.regShaped(new ItemStack(fcItems.machineID,1,6),new Object[]{"ete","lml","eae",'a',"craftingRawMachineTier02",'e',"craftingCircuitTier07",'m',ic2.getItem("mfsUnit"),'t',ic2.getItem("hvTransformer"),'l',Block.blockLapis});	
			//UHSU
			if(eUHSU)ic2.regShaped(new ItemStack(fcItems.machineID,1,7),new Object[]{"sts","lul","lal",'a',"craftingRawMachineTier04",'u',new ItemStack(fcItems.machineID,1,6),'t',new ItemStack(fcItems.machineID,1,8),'l',"chunkLazurite",'s',"craftingSuperconductor"});
		}
		else{
			//HSU
			if(eHSU)ic2.regShaped(new ItemStack(fcItems.machineID,1,6),new Object[]{"ete","lml","eae",'a',"craftingRawMachineTier02",'e',"craftingCircuitTier04",'m',ic2.getItem("mfsUnit"),'t',ic2.getItem("hvTransformer"),'l',Block.blockLapis});	
			//UHSU
			if(eUHSU)ic2.regShaped(new ItemStack(fcItems.machineID,1,7),new Object[]{"ltl","lul","lal",'a',"craftingRawMachineTier02",'u',new ItemStack(fcItems.machineID,1,6),'t',new ItemStack(fcItems.machineID,1,8),'l',"chunkLazurite"});		
		}
		//EV Transformer
		if(eEVT)ic2.regShaped(new ItemStack(fcItems.machineID,1,8),new Object[]{"www","ctl","www",'t',ic2.getItem("hvTransformer"),'c',"craftingCircuitTier04",'w',"craftingWireIron",'l',"crafting1kkEUStore"});
		
		//Liquefier
		if(eL)ic2.regShaped(new ItemStack(fcItems.machineID,1,9),new Object[]{"lpl","lcl","eae",'a',"craftingRawMachineTier02",'c',"craftingCompressor",'p',"craftingPump",'e',"craftingCircuitTier02",'l',ic2.getItem("cell")});
		//CondenseTower
		if(eCT)ic2.regShaped(new ItemStack(fcItems.machineID,1,10),new Object[]{"idi","cpc","cac",'a',new ItemStack(fcItems.machine2ID,1,1),'i',ic2.getItem("miningPipe"),'d',"craftingCircuitTier04",'p',"craftingPump",'c',ic2.getItem("cell")});
		//ThermalCracker
		if(eTC)ic2.regShaped(new ItemStack(fcItems.machineID,1,11),new Object[]{"cdc","cec","rar",'a',"craftingRawMachineTier02",'d',"craftingCircuitTier04",'c',ic2.getItem("cell"),'r',"craftingHeatingCoilTier00",'e',"craftingExtractor"});
		//AdvanceChemicalReactor
		if(GregTech.isGTLoaded){
			if(eACR)ic2.regShaped(new ItemStack(fcItems.machineID,1,12),new Object[]{"sms","cec","sas",'a',"craftingRawMachineTier02",'m',ic2.getItem("magnetizer"),'e',"craftingExtractor",'s',"plateSteel",'c',"craftingCircuitTier04"});
		}
		else{
			if(eACR)ic2.regShaped(new ItemStack(fcItems.machineID,1,12),new Object[]{"sms","cec","sas",'a',"craftingRawMachineTier02",'m',ic2.getItem("magnetizer"),'e',"craftingExtractor",'s',"plateAlloyAdvanced",'c',"craftingCircuitTier04"});			
		}
			
		//LiquidOutput
		if(eCT)ic2.regShaped(new ItemStack(fcItems.machine2ID,1,0),new Object[]{"rgr","rir","rar",'r',refinedIron,'i',ic2.getItem("miningPipe"),'g',ic2.getItem("reinforcedGlass"),'a',"craftingRawMachineTier02"});
		if(eCT)ic2.regShaped(new ItemStack(fcItems.machine2ID,1,0),new Object[]{"rgr","rir","rar",'r',aluminium,'i',ic2.getItem("miningPipe"),'g',ic2.getItem("reinforcedGlass"),'a',"craftingRawMachineTier02"});
		//CondenseTowerCylinder
		if(eCT)ic2.regShaped(new ItemStack(fcItems.machine2ID,1,1),new Object[]{"rir","rir","rar",'r',refinedIron,'i',ic2.getItem("miningPipe"),'a',"craftingRawMachineTier02"});	
		if(eCT)ic2.regShaped(new ItemStack(fcItems.machine2ID,1,1),new Object[]{"rir","rir","rar",'r',aluminium,'i',ic2.getItem("miningPipe"),'a',"craftingRawMachineTier02"});

		
		//WindMill
		if(GregTech.isGTLoaded&&SteelACWindMill){
			if(eACWM)ic2.regShaped(new ItemStack(fcItems.fan),new Object[]{" i "," f ","i i",'f',"plateSteel",'i',"stickSteel"});
			if(eACWM)ic2.regShaped(new ItemStack(fcItems.acwindmillcylinder,12),new Object[]{"f f","f f","f f",'f',"stickSteel"});
		}else{
			if(eACWM)ic2.regShaped(new ItemStack(fcItems.fan),new Object[]{" f "," f ","f f",'f',ic2.getItem("ironFence")});
			if(eACWM)ic2.regShaped(new ItemStack(fcItems.acwindmillcylinder,12),new Object[]{"f f","f f","f f",'f',ic2.getItem("ironFence")});			
		}
		//Top
		if(eACWM)ic2.regShaped(new ItemStack(fcItems.machine2ID,1,2),new Object[]{"crc","rwr","crc", 'c', "plateAlloyAdvanced",'w',ic2.getItem("windMill"),'r',"dyeRed"});
        //Base   
		if(eACWM)ic2.regShaped(new ItemStack(fcItems.machine2ID,1,3),new Object[]{" i ","cac"," e ", 'c', "calclavia:WIRE",'a',"craftingRawMachineTier02",'e',"craftingCircuitTier02",'i',new ItemStack(fcItems.acwindmillcylinder)});        	
		//AutoWorkBench
		if(eAWB)ic2.regShaped(new ItemStack(fcItems.machine2ID,1,4),new Object[]{"ttt","tct","ttt",'t',"ingotTin",'c',Block.workbench});
		
        //RailGun
        if(eR)ic2.regShaped(new ItemStack(fcItems.miscsID,1,2),new Object[]{"scs","sts","scs",'s',"craftingSuperconductor",'c',"crafting360kCoolantStore",'t',new ItemStack(fcItems.machineID,1,8)});
        if(eR)ic2.regShaped(new ItemStack(fcItems.miscsID,1,2),new Object[]{"sss","ctc","sss",'s',"craftingSuperconductor",'c',"crafting360kCoolantStore",'t',new ItemStack(fcItems.machineID,1,8)});       
        if(eR)ic2.regShaped(new ItemStack(fcItems.railgun),new Object[]{"igi","oco","ili",'c',new ItemStack(fcItems.miscsID,1,2),'l',"crafting1kkEUStore",'g',new ItemStack(fcItems.miscsID,1,3),'o',ic2.getItem("overclockerUpgrade"),'i',"plateAlloyIridium"});
	
        
        //GoldClod
        ic2.regUnshaped(new ItemStack(fcItems.miscsID,1,3),new Object[]{"ingotPotassium","ingotPotassium","ingotPotassium","gemPhosphor","gemPhosphor","gemPhosphor","molecule_1n","molecule_1n","molecule_1n"});
	
        //GunPowder
        ic2.regUnshaped(new ItemStack(Item.gunpowder,2),new Object[]{"dustAmmoniumNitrate","dustAmmoniumNitrate","dustCharcoal","craftingSulfurToGunpowder"});
        ic2.regUnshaped(new ItemStack(Item.gunpowder,4),new Object[]{"dustAmmoniumNitrate","dustAmmoniumNitrate","dustCoal","craftingSulfurToGunpowder"});	
	
        //Decay Batteries
        if(!GregTech.isGTLoaded)
        	ic2.regShaped(new ItemStack(fcItems.UBattery), new Object[]{"epe","pop","ere",'o',"ingotUranium",'r',ic2.getItem("reactorReflectorThick"),'e',"craftingCircuitTier04",'p',"plateAlloyAdvanced"});
        ic2.regShaped(new ItemStack(fcItems.UBattery), new Object[]{"epe","pop","ere",'o',"ingotUranium",'r',ic2.getItem("reactorReflectorThick"),'e',"craftingCircuitTier04",'p',"plateLead"});
        ic2.regShaped(new ItemStack(fcItems.PuBattery), new Object[]{"epe","pop","ere",'o',"ingotPlutonium",'r',ic2.getItem("reactorReflectorThick"),'e',"craftingCircuitTier04",'p',"plateLead"});
        ic2.regShaped(new ItemStack(fcItems.ThBattery), new Object[]{"epe","pop","ere",'o',"ingotThorium",'r',ic2.getItem("reactorReflectorThick"),'e',"craftingCircuitTier04",'p',"plateLead"});
	}
		
	static void loadMachineRecipes(){
		//Condense Tower
		if(GregTech.isGTLoaded){
		RecipeManager.addCondenseTowerRecipe(FluidManager.getFluid("liquifiedair", 12), 60, 20,  			    //Liquefied Air
				                             FluidManager.getFluid("co2", 1),           						//CO2
									         FluidManager.getFluid("Nitrogen",7),								//N2
									         FluidManager.getFluid("oxygen", 3),           						//O2
									         FluidManager.getFluid("argon", 1));           						//Ar
										
		
		RecipeManager.addCondenseTowerRecipe(FluidManager.getFluid("coaltar", 22), 40, 20,  			    //CoalTar
				FluidManager.getFluid("benzene", 2),           						//C6H6
				FluidManager.getFluid("co", 5),								//CO
				FluidManager.getFluid("Methane",10),           					//CH4
				FluidManager.getFluid("Hydrogen",5));								//H2
		}
		
		//AdvanceChemicalReactor
		//CaO+CO2=Ca(OH)2 237104015
		RecipeManager.addAdvanceChemicalReactorRecipe(ic2.getItem("waterCell"), new ItemStack(fcItems.dustsID,1,0),null, null, null,new ItemStack(fcItems.dustsID,1,4), null, null, null, null,null,null,ic2.getItem("cell"), 10, 10,-1);
		//Ca(OH)2+CO2=CaCO3+H2O
		RecipeManager.addAdvanceChemicalReactorRecipe(new ItemStack(fcItems.dustsID,1,4), new ItemStack(fcItems.cellsID,1,4),null, null, null,GregTech.getGTDust(4,1), ic2.getItem("waterCell"), null, null, null, 10, 10);		
		//N2+3H2=2NH3
		RecipeManager.addAdvanceChemicalReactorRecipe(GregTech.getGTCell(15,1),GregTech.getGTCell(0,3),null,null,null,new ItemStack(fcItems.cellsID,2,0),null,null,null,null,fcItems.getItem(cls.misc, "AmmoniaModule"),null,ic2.getItem("cell",2),128,-1,150);
		//N2+2O2=2NO2
		RecipeManager.addAdvanceChemicalReactorRecipe(new ItemStack(fcItems.cellsID,2,2),GregTech.getGTCell(15, 1),null,null,null,GregTech.getGTCell(38, 2),null,null,null,null,fcItems.getItem(cls.misc, "HeatingModule"),null,ic2.getItem("cell"),128,-1,200);		
		//CO2+2NH3=CO(NH2)2+H2O
		RecipeManager.addAdvanceChemicalReactorRecipe(new ItemStack(fcItems.cellsID,1,4), new ItemStack(fcItems.cellsID,2,0),null,null,null,new ItemStack(fcItems.dustsID,1,1),ic2.getItem("waterCell"),null,null,null,null,null,ic2.getItem("cell",2),40,50,-1);
		//3NO2+H2O=2HNO3+NO
		RecipeManager.addAdvanceChemicalReactorRecipe(GregTech.getGTCell(38, 3), ic2.getItem("waterCell"), null, null, null, new ItemStack(fcItems.cellsID,2,6), new ItemStack(fcItems.cellsID,1,7), null, null, null, null, null, ic2.getItem("cell"), 16, 50,-1);
		//2NO+O2=2NO2
		RecipeManager.addAdvanceChemicalReactorRecipe(new ItemStack(fcItems.cellsID,2,7), new ItemStack(fcItems.cellsID,1,2), null, null, null, GregTech.getGTCell(38, 2), null, null, null, null, null, null, ic2.getItem("cell"), 10, 10, -1);
		//HNO3+NH3=NH4NO3
		RecipeManager.addAdvanceChemicalReactorRecipe(new ItemStack(fcItems.cellsID,1,6), new ItemStack(fcItems.cellsID,1,0), null, null, null, new ItemStack(fcItems.dustsID,1,2), null, null, null, null, null, null, ic2.getItem("cell",2), 10, 10, -1);
		//C+O2=CO2
		RecipeManager.addAdvanceChemicalReactorRecipe(ic2.getItem("coalDust"), new ItemStack(fcItems.cellsID,1,2), null, null, null, new ItemStack(fcItems.cellsID,2,4), null, null, null, null, 50, 100);
		RecipeManager.addAdvanceChemicalReactorRecipe(GregTech.getGTDust(240, 1), new ItemStack(fcItems.cellsID,1,2), null, null, null, new ItemStack(fcItems.cellsID,2,4), null, null, null, null, 50, 100);
		RecipeManager.addAdvanceChemicalReactorRecipe(new ItemStack(Item.coal,1,0), new ItemStack(fcItems.cellsID,1,2), null, null, null, new ItemStack(fcItems.cellsID,1,4), null, null, null, null, 75, 100);
		RecipeManager.addAdvanceChemicalReactorRecipe(new ItemStack(Item.coal,1,1), new ItemStack(fcItems.cellsID,1,2), null, null, null, new ItemStack(fcItems.cellsID,1,4), null, null, null, null, 75, 100);
		RecipeManager.addAdvanceChemicalReactorRecipe(GregTech.getGTCell(8, 1), new ItemStack(fcItems.cellsID,1,2), null, null, null, new ItemStack(fcItems.cellsID,1,4), null, null, null, null,null,null,ic2.getItem("cell"), 75, 100, -1);
		//Ca3(PO4)2 + 3SiO2 + 5 C == 3 CaSiO3 + 5CO + 2P
		RecipeManager.addAdvanceChemicalReactorRecipe(GregTech.getGTDust(45, 1), GregTech.getGTDust(7, 3), GregTech.getGTCell(8, 5), null, null, new ItemStack(fcItems.dustsID,3,3), new ItemStack(fcItems.cellsID,5,8), new ItemStack(fcItems.ingotsID,2,1), null, null,fcItems.getItem(cls.misc, "HeatingModule"),null,null, 128,-1, 350);
		RecipeManager.addAdvanceChemicalReactorRecipe(GregTech.getGTDust(45, 2), GregTech.getGTDust(7, 6), GregTech.getGTDust(240, 5), null, null, new ItemStack(fcItems.dustsID,6,3), new ItemStack(fcItems.cellsID,10,8), new ItemStack(fcItems.ingotsID,4,1), null, null,fcItems.getItem(cls.misc, "HeatingModule"),ic2.getItem("cell",10),null, 128,-1, 350);
		//2CO+O2=2CO2
		RecipeManager.addAdvanceChemicalReactorRecipe(new ItemStack(fcItems.cellsID,2,8), new ItemStack(fcItems.cellsID,1,2), null, null, null, new ItemStack(fcItems.cellsID,2,4), null,null,null,null,null,null,ic2.getItem("cell"), 10, 10,-1);
		//CaSiO3=CaO+SiO2
		RecipeManager.addAdvanceChemicalReactorRecipe(new ItemStack(fcItems.dustsID,1,3),null,null,null,null,new ItemStack(fcItems.dustsID,1,0),GregTech.getGTDust(7, 1),null,null,null,fcItems.getItem(cls.misc, "HeatingModule"),null,null,128,-1,400);
		//C->raw carbon fiber
		RecipeManager.addAdvanceChemicalReactorRecipe(GregTech.getGTCell(8, 9), null, null, null, null, ic2.getItem("carbonFiber"), null, null, null, null,null,null,ic2.getItem("cell",9), 20, 50, -1);
		//Mg+Br2=MgBr2
		RecipeManager.addAdvanceChemicalReactorRecipe(GregTech.getGTDust(13, 1),fcItems.getItem(cls.cell,"cell_Bromine",1),null,null,null,fcItems.getItem(cls.dust, "MgBr2", 1),null,null,null,null,null,null,ic2.getItem("cell"),30,10,-1);
		//Cl2+2K=2KCl
		RecipeManager.addAdvanceChemicalReactorRecipe(GregTech.getGTCell(13,1),GregTech.getGTCell(14,2),null,null,null,fcItems.getItem(cls.dust, "KCl", 2),null,null,null,null,null,null,ic2.getItem("cell",3),30,10,-1);		
		RecipeManager.addAdvanceChemicalReactorRecipe(GregTech.getGTCell(13,1),fcItems.getItem(cls.ingot, "K",2),null,null,null,fcItems.getItem(cls.dust, "KCl", 2),null,null,null,null,null,null,ic2.getItem("cell"),30,10,-1);	
		//CaF2=Ca+F2
		RecipeManager.addAdvanceChemicalReactorRecipe(fcItems.getItem(cls.dust,"CaF2",1),null,null,null,null,GregTech.getGTCell(11,1),fcItems.getItem(cls.cell,"cell_Fluorine",1),null,null,null,fcItems.getItem(cls.misc, "ElectrolizeModule"),ic2.getItem("cell",2),null,128,-1,350);			
		//Ca+F2=CaF2
		RecipeManager.addAdvanceChemicalReactorRecipe(GregTech.getGTCell(11, 1),fcItems.getItem(cls.cell,"cell_Fluorine",1),null,null,null,fcItems.getItem(cls.dust,"CaF2",1),null,null,null,null,null,null,ic2.getItem("cell",2),10,10,-1);		
		
		
		//Thermal Cracker
		//Coal
		RecipeManager.addThermalCrackerRecipe(ic2.getItem("coalDust"), 48, 80, new ItemStack(fcItems.miscsID,1,0), FluidManager.getFluid("CoalTar", 200));
		RecipeManager.addThermalCrackerRecipe(GregTech.getGTDust(240, 1), 48, 80, new ItemStack(fcItems.miscsID,1,0), FluidManager.getFluid("CoalTar", 200));		
		
		//Stone->Cao+CO2
		RecipeManager.addThermalCrackerRecipe(new ItemStack(Block.stone,1), 64, 150, new ItemStack(fcItems.dustsID,1,0), FluidManager.getFluid("CO2", 500));
		RecipeManager.addThermalCrackerRecipe(new ItemStack(Block.cobblestone,1), 64, 100, new ItemStack(fcItems.dustsID,1,0), FluidManager.getFluid("CO2", 500));	
	
		//CaCO3->CaO+CO2
		RecipeManager.addThermalCrackerRecipe(GregTech.getGTDust(4, 1), 30, 100, new ItemStack(fcItems.dustsID,1,0), FluidManager.getFluid("CO2", 1000));
	}
	
	static void loadGTMachineRecipes(){
		//Ingot
		if(!GregTech.isGTLoaded)
			return;

		//Mangalium dust
		FurnaceRecipes.smelting().addSmelting(fcItems.dustsID, 5, GregTech.getGTItem(40,1), 0.8F);
		//GT Automatic machines
		ic2.regUnshaped(new ItemStack(GregTech.sBlockList[1], 1, 50), new Object[] {ic2.getItem("macerator"),GregTech.getGTComponent(5,1)});
		ic2.regUnshaped(new ItemStack(GregTech.sBlockList[1], 1, 51), new Object[] {ic2.getItem("extractor"),GregTech.getGTComponent(5,1)});
		ic2.regUnshaped(new ItemStack(GregTech.sBlockList[1], 1, 52), new Object[] {ic2.getItem("compressor"),GregTech.getGTComponent(5,1)});
		ic2.regUnshaped(new ItemStack(GregTech.sBlockList[1], 1, 53), new Object[] {ic2.getItem("recycler"),GregTech.getGTComponent(5,1)});
		ic2.regUnshaped(new ItemStack(GregTech.sBlockList[1], 1, 54), new Object[] {ic2.getItem("electroFurnace"),GregTech.getGTComponent(5,1)});
			
		//Super Conductor
		ic2.regShaped(GregTech.getGTComponent(2, 4), new Object[]{"lll","wiw","fff",'l',new ItemStack(fcItems.IC2Coolant_NH3_60K),'w',"plateTungsten",'i',"plateAlloyIridium",'f',"craftingCircuitTier07"});
		
		//Generator------------------------------------------------------------
		//Simifluid - CoalTar
		GregTech.sRecipeAdder.addFuel(new ItemStack(fcItems.cellsID,1,1), ic2.getItem("cell"), 10, 0);
		//Simifluid - Benzene
		GregTech.sRecipeAdder.addFuel(new ItemStack(fcItems.cellsID,1,9), ic2.getItem("cell"), 10, 0);
		//Simifluid - Bromine
		GregTech.sRecipeAdder.addFuel(new ItemStack(fcItems.cellsID,1,10), ic2.getItem("cell"), 10, 0);
		//Gas - CO
		GregTech.sRecipeAdder.addFuel(new ItemStack(fcItems.cellsID,1,8), ic2.getItem("cell"), 30, 1);
		//Plasma - GoldClod
		GregTech.sRecipeAdder.addFuel(new ItemStack(fcItems.miscsID,1,3), null, 100, 5);
		
		//Freezer--------------------------------------------------------------
		GregTech.sRecipeAdder.addVacuumFreezerRecipe(new ItemStack(fcItems.IC2Coolant_NH3_60K), new ItemStack(fcItems.IC2Coolant_NH3_60K), 100);
		GregTech.sRecipeAdder.addVacuumFreezerRecipe(new ItemStack(fcItems.IC2Coolant_NH3_180K), new ItemStack(fcItems.IC2Coolant_NH3_180K), 100);
		GregTech.sRecipeAdder.addVacuumFreezerRecipe(new ItemStack(fcItems.IC2Coolant_NH3_360K), new ItemStack(fcItems.IC2Coolant_NH3_360K), 100);
		
		//Implosion Compressor-------------------------------------------------
		//Lapis dust 2 lapis
		GregTech.sRecipeAdder.addImplosionRecipe(GregTech.getGTDust(2, 8), 1, new ItemStack(Item.dyePowder.itemID,8,4), GregTech.getGTDust(63, 1));
		
		//Centrifuge------------------------------------------------------------
		//Potassium K
		GregTech.sRecipeAdder.addCentrifugeRecipe(GregTech.getGTCell(14, 1), 0, ic2.getItem("cell"), new ItemStack(fcItems.ingotsID,1,0), null, null, 20);
		//Magnalium
		GregTech.sRecipeAdder.addCentrifugeRecipe(fcItems.getItem(cls.dust, "Magnalium", 3), 0, GregTech.getGTDust(13, 1), GregTech.getGTDust(18, 2), null, null, 300);
		//Carnallite
		GregTech.sRecipeAdder.addCentrifugeRecipe(fcItems.getItem(cls.dust, "Carnallite", 5), 8, fcItems.getItem(cls.dust, "KCl", 4), fcItems.getItem(cls.dust, "MgBr2", 1), ic2.getItem("waterCell",8), null, 40);
		
		//Electrolizer-----------------------------------------------------------
		//KCl
		GregTech.sRecipeAdder.addElectrolyzerRecipe(fcItems.getItem(cls.dust, "KCl", 2), 2, GregTech.getGTCell(14,2),GregTech.getGTCell(13, 1), null, null, 120, 64);
		//MgBr2
		GregTech.sRecipeAdder.addElectrolyzerRecipe(fcItems.getItem(cls.dust, "MgBr2", 1), 1, GregTech.getGTDust(13,1), fcItems.getItem(cls.cell,"cell_Bromine",1), null, null, 100, 64);		
		//Fluorapatite
		GregTech.sRecipeAdder.addElectrolyzerRecipe(fcItems.getItem(cls.dust, "Fluorapatite", 2), 0, fcItems.getItem(cls.dust, "CaF2", 1), GregTech.getGTDust(45,2), null, null, 100,64);		
		
		//Canner-----------------------------------------------------------------
		//K
		GregTech.sRecipeAdder.addCannerRecipe(new ItemStack(fcItems.ingotsID,1,0), ic2.getItem("cell"), GregTech.getGTCell(14,1), null, 5, 1);	

		//Instrial Grinder-------------------------------------------------------
		GregTech.sRecipeAdder.addGrinderRecipe(new ItemStack(fcItems.oreID,1,0), -1, fcItems.getItem(cls.dust, "Carnallite", 8), new ItemStack(Block.dirt,1), null, null);
		GregTech.sRecipeAdder.addGrinderRecipe(new ItemStack(fcItems.oreID,1,1), -1, fcItems.getItem(cls.dust, "Fluorapatite", 8), GregTech.getGTSmallDust(36, 1), null, null);

		//Assembler---------------------------------------------------------------
		GregTech.sRecipeAdder.addAssemblerRecipe(GregTech.getGTItem(13,8), null, ic2.getItem("advancedMachine",2), 500, 1);
	}
	
	static void loadICMachineRecipes(){		
		//IC Items
		//AdvanceMachine
		ic2.regShaped(ic2.getItem("advancedMachine"), new Object[]{"xxx","x x","xxx",'x',"plateMagnalium"});
		//Hv transformer
		ic2.regShaped(ic2.getItem("hvTransformer"), new Object[]{" w ","cmo"," w ",'w',"craftingWireIron",'c',"craftingCircuitTier02",'m',ic2.getItem("mvTransformer"),'o',"crafting100kEUStore"});
		//fertilizer
		ic2.regUnshaped(ic2.getItem("fertilizer",16),new Object[]{fcItems.getItem(cls.dust, "Urea"),"craftingFertilizer"});
		ic2.regUnshaped(ic2.getItem("fertilizer",64),new Object[]{fcItems.getItem(cls.misc, "GoldClod"),"craftingFertilizer"});
		//overclockerUpgrade
		ic2.regShaped(ic2.getItem("overclockerUpgrade",2),new Object[]{" o ","lal","   ",'a',"craftingCircuitTier02",'l',"calclavia:WIRE",'o',fcItems.IC2Coolant_NH3_60K});
		
		//IC2 Compressor Recipes
		ic2.addCompressor(new ItemStack(fcItems.miscsID,8,0), new ItemStack(fcItems.miscsID,1,1));
		
		//IC2 Extractor Recipes
		for (int i:Item_Cells.subNames.keySet().toArray(new Integer[]{}))
			ic2.addExtractor(new ItemStack(fcItems.cellsID,1,i), ic2.getItem("cell"));

		ic2.addExtractor(ic2.getItem("hydratingCell"),ic2.getItem("cell"));
		
		//IC2 Macerator Recipes
		//Magnalium
		ic2.addMacerator(GregTech.getGTItem(40, 1), fcItems.getItem(cls.dust, "Magnalium", 1));
		ic2.addMacerator(GregTech.getGTItem(13,1), fcItems.getItem(cls.dust, "Magnalium", 1));
		//Carnallite
		ic2.addMacerator(new ItemStack(fcItems.oreID,1,0), fcItems.getItem(cls.dust, "Carnallite", 2));	
		//Fluorapatite
		ic2.addMacerator(new ItemStack(fcItems.oreID,1,1), fcItems.getItem(cls.dust, "Fluorapatite", 2));	
		
		//IC2 matterAmplifier
		ic2.addAmplifier(ItemManager.getItem(3, "Fluorapatite"), 6000);
		ic2.addAmplifier(ItemManager.getItem(3, "Carnallite"), 6000);
		
	}
	
	
	//Common stuff------------------------------------------------------------------------------------------------------------------
	static void loadMobilePSEnergyUpdates() {
		RecipeManager.addMobilePSEnergyUpdate(ic2.getItem("energyStorageUpgrade"), 10000);
		if(GregTech.isGTLoaded){
			RecipeManager.addMobilePSEnergyUpdate(GregTech.getGTComponent(26,1), 100000);		//Lithium Update
			RecipeManager.addMobilePSEnergyUpdate(GregTech.getGTComponent(12,1), 100000);       //Energy Crystal Update
			RecipeManager.addMobilePSEnergyUpdate(GregTech.getGTComponent(13,1), 1000000);		//Lapotron Update
			RecipeManager.addMobilePSEnergyUpdate(GregTech.getGTComponent(14,1), 10000000);		//Orb Update	
		}
	}

	static void loadMobilePSSolarUpdates(){
		RecipeManager.addMobilePSSolarUpdate(ic2.getItem("solarPanel"), 1, 0);        //IC Solar Panel
		if(GregTech.isGTLoaded)
			RecipeManager.addMobilePSSolarUpdateI(GregTech.getGTComponent(7,1), 1, 0);           //GT Solar Component
        
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

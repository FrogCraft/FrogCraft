package FrogCraft;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.oredict.OreDictionary;

import FrogCraft.Blocks.ItemBlockOre;
import FrogCraft.Common.FluidManager;
import FrogCraft.Items.*;
import FrogCraft.api.fcItems;

public class ItemsRegister {

	public static void loadItemsData(){
		//Cells
		Item_Cells.add(0,"cell_Ammonia");
		Item_Cells.add(1,"cell_CoalTar");
/*2*/	Item_Cells.add(2,"cell_Oxygen");
		Item_Cells.add(3,"cell_LiquifiedAir");
		Item_Cells.add(4,"cell_CO2");
/*5*/	Item_Cells.add(5,"cell_Argon");		
  		Item_Cells.add(6,"cell_HNO3");
		Item_Cells.add(7,"cell_NO");
/*8*/	Item_Cells.add(8,"cell_CO");
		Item_Cells.add(9,"cell_Benzene");
		Item_Cells.add(10,"cell_Bromine");
/*11*/	Item_Cells.add(11,"cell_Fluorine");
		Item_Cells.add(12,"cell_SO2");
		Item_Cells.add(13, "cell_SO3");
		
		//Miscs
		Item_Miscs.add(0,"pCokingCoal");
		Item_Miscs.add(1,"Briquette");
/*2*/	Item_Miscs.add(2,"Railgun_Core");
		Item_Miscs.add(3,"GoldClod");
		Item_Miscs.add(4,"ElectrolizeModule");
/*5*/	Item_Miscs.add(5,"HeatingModule");
		Item_Miscs.add(6,"AmmoniaModule");
		Item_Miscs.add(7,"V2O5Module");		
		
		//Dusts
		Item_Dusts.add(0,"CaO");
		Item_Dusts.add(1,"Urea");
/*2*/	Item_Dusts.add(2,"NH4NO3");
		Item_Dusts.add(3,"CaSiO3");
		Item_Dusts.add(4,"CaOH2");
/*5*/	Item_Dusts.add(5,"Magnalium");	
		Item_Dusts.add(6,"Carnallite");	
		Item_Dusts.add(7,"KCl");	
/*8*/	Item_Dusts.add(8,"MgBr2");
		Item_Dusts.add(9,"Fluorapatite");
		Item_Dusts.add(10,"CaF2");
/*11*/  Item_Dusts.add(11, "V2O5");
		Item_Dusts.add(12, "Dewalquite");
		Item_Dusts.add(13, "TiO2");
/*14*/	Item_Dusts.add(14, "Al2O3");
		
		//Ingots
		Item_Ingots.add(0,"K");
		Item_Ingots.add(1,"P");
}
	

	public static void registerFluids(){
		FluidManager.RegisterFluid("LiquifiedAir", new ItemStack(fcItems.Cells,1,3));
		FluidManager.RegisterFluid("CoalTar", new ItemStack(fcItems.Cells,1,1));
		FluidManager.RegisterFluid("HNO3", new ItemStack(fcItems.Cells,1,6));
		FluidManager.RegisterFluid("Benzene", new ItemStack(fcItems.Cells,1,9));
		FluidManager.RegisterFluid("Bromine", new ItemStack(fcItems.Cells,1,10));
		
		FluidManager.RegisterFluid("Oxygen", new ItemStack(fcItems.Cells,1,2));
		FluidManager.RegisterFluid("CO2", new ItemStack(fcItems.Cells,1,4));
		FluidManager.RegisterFluid("Argon", new ItemStack(fcItems.Cells,1,5));
		FluidManager.RegisterFluid("Ammonia", new ItemStack(fcItems.Cells,1,0));
		FluidManager.RegisterFluid("NO", new ItemStack(fcItems.Cells,1,7));
		FluidManager.RegisterFluid("CO", new ItemStack(fcItems.Cells,1,8));
		FluidManager.RegisterFluid("Fluorine", new ItemStack(fcItems.Cells,1,11));
		FluidManager.RegisterFluid("SO2", new ItemStack(fcItems.Cells,1,12));
		FluidManager.RegisterFluid("SO3", new ItemStack(fcItems.Cells,1,13));		
	}
	
	
	public static void registerOreDict(){		
		//FC
		OreDictionary.registerOre("crafting60kCoolantStore",fcItems.IC2Coolant_NH3_60K);
		OreDictionary.registerOre("crafting180kCoolantStore",fcItems.IC2Coolant_NH3_180K);	
		OreDictionary.registerOre("crafting360kCoolantStore",fcItems.IC2Coolant_NH3_360K);
		
		OreDictionary.registerOre("craftingHeatingCoilTier00",new ItemStack(fcItems.Miscs,1,5));
		
		OreDictionary.registerOre("molecule_1h_1n_3o",new ItemStack(fcItems.Cells,1,6));
		OreDictionary.registerOre("molecule_1n_1o",new ItemStack(fcItems.Cells,1,7));
		
		OreDictionary.registerOre("dustNH4NO3",new ItemStack(fcItems.Dusts,1,2));
		OreDictionary.registerOre("dustMagnalium",new ItemStack(fcItems.Dusts,1,5));	
		OreDictionary.registerOre("dustCarnallite",new ItemStack(fcItems.Dusts,1,6));		
		OreDictionary.registerOre("dustFluorapatite",new ItemStack(fcItems.Dusts,1,9));
		OreDictionary.registerOre("dustV2O5",new ItemStack(fcItems.Dusts,1,11));
		OreDictionary.registerOre("dustDewalquite",new ItemStack(fcItems.Dusts,1,12));
		OreDictionary.registerOre("dustTiO2",new ItemStack(fcItems.Dusts,1,13));		
		
		OreDictionary.registerOre("ingotPotassium",new ItemStack(fcItems.Ingots,1,0));
		OreDictionary.registerOre("gemPhosphor",new ItemStack(fcItems.Ingots,1,1));	
		
		//Register Ores
		for (int i=0;i<ItemBlockOre.subNames.length;i++){
			OreDictionary.registerOre(ItemBlockOre.subNames[i],new ItemStack(fcItems.Ore.blockID,1,i));	
			gregtechmod.api.util.GT_OreDictUnificator.add(ItemBlockOre.subNames[i],new ItemStack(fcItems.Ore.blockID,1,i));
		}
	}
}

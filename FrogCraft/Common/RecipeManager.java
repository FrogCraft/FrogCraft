package FrogCraft.Common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.*;
import net.minecraftforge.oredict.OreDictionary;


public class RecipeManager {
	/**Condence Tower Recipes Integer for fluidID*/
	public static HashMap<Integer,FluidStack[]> condenseTowerRecipes= new HashMap();
	/**{amount of input consumed per tick, tickTotal, energy}*/
	public static HashMap<Integer,Integer[]> condenseTowerRecipeInfo= new HashMap();
	
	/** {inputItemStack,outputItemStack,outputLiquidStack,energyPerTick,tickTotal} */
	public static List<Object[]> thermalCrackerRecipes =  new ArrayList<Object[]>();
	
	
	/** {id,damage,amountDay,amountNight,isItem} 
	 * 	isItem: Do not change the top texture, especially for items such as GT solar component,
	 *  		should set to true for items and false for blocks,otherwise crash!*/
	public static List<int[]> mobilePSSolarUpdates =  new ArrayList<int[]>();	
	/** {id,damage,amount} */
	public static List<int[]> mobilePSEnergyUpdates =  new ArrayList<int[]>();		

	/**	0-4:Reactants 5-9:Products 10:Catalyst 11:cellNeed 12:cellOut*/
	public static List<ItemStack[]> advanceChemicalReactorRecipes = new ArrayList<ItemStack[]>();
	/** {euPerTick,tick,tickWithCatalyst} */
	public static List<int[]> advanceChemicalReactorRecipeInfo =  new ArrayList<int[]>();	
	
	/** Reactant, OutputSolid, OutputLiquid*/
	public static List<Object[]> combustionFurnaceRecipes = new ArrayList<Object[]>();
	//AdvanceChemicalReactor
	/** Add a recipe to Advance Chemical Reactor*/
	public static void addAdvanceChemicalReactorRecipe(ItemStack r1,ItemStack r2,ItemStack r3,ItemStack r4,ItemStack r5,
													   ItemStack o1,ItemStack o2,ItemStack o3,ItemStack o4,ItemStack o5,
													   int eu,int tick){
		advanceChemicalReactorRecipes.add(new ItemStack[]{r1,r2,r3,r4,r5,o1,o2,o3,o4,o5,null,null,null});
		advanceChemicalReactorRecipeInfo.add(new int[]{eu,tick,1});
	}
	
	/** Add a recipe to Advance Chemical Reactor
	 * 	tick=-1 means catalyst is need to start the reaction*/
	public static void addAdvanceChemicalReactorRecipe(ItemStack r1,ItemStack r2,ItemStack r3,ItemStack r4,ItemStack r5,
													   ItemStack o1,ItemStack o2,ItemStack o3,ItemStack o4,ItemStack o5,
													   ItemStack catalyst,ItemStack cellNeeded,ItemStack cellout,int eu,int tick,int tickWithCatalyst){
		advanceChemicalReactorRecipes.add(new ItemStack[]{r1,r2,r3,r4,r5,o1,o2,o3,o4,o5,catalyst,cellNeeded,cellout});
		advanceChemicalReactorRecipeInfo.add(new int[]{eu,tick,tickWithCatalyst});
	}
	
	/** Add a recipe to Advance Chemical Reactor
	 * 	tick=-1 means catalyst is need to start the reaction*/
	public static void addAdvanceChemicalReactorRecipe(ItemStack[] reactants,ItemStack[] products,ItemStack catalyst,ItemStack cellNeed,ItemStack cellOut,int eu,int tick,int tickWithCatalyst){
		ItemStack[] rec=new ItemStack[]{null,null,null,null,null,null,null,null,null,null,null,null,null};
		int index=0;
		for(int i=0;i<5;i++){
			if(reactants[i]!=null){
				rec[index]=reactants[i].copy();
				index++;
			}
		}
		
		index=5;
		for(int i=5;i<10;i++){
			if(products[i-5]!=null){
				rec[index]=products[i-5].copy();
				index++;
			}
		}
		
		rec[10]=catalyst;
		rec[11]=cellNeed;
		rec[12]=cellOut;
		advanceChemicalReactorRecipes.add(rec);
		advanceChemicalReactorRecipeInfo.add(new int[]{eu,tick,tickWithCatalyst});
	}
	
	public static int findAdvanceChemicalReactorRecipeIndex(ItemStack[] in){
		ItemStack[] items=new ItemStack[5];
		int num=0;
		for (int i=0;i<5;i++){
			if(in[i]!=null){
				int id=-1;
				for(int j=0;j<num;j++){ //contains?
					if(items[j].isItemEqual(in[i]))
						id=j;
				}
				
				if(id==-1) //does't contain
				{
					items[num]=in[i].copy();
					num+=1;
				}
				else{
					items[id].stackSize+=in[i].stackSize;
				}
					
			}
		}
		
		ItemStack[] r=new ItemStack[num];
		for (int i=0;i<num;i++)
			r[i]=items[i];
		
		for (int i=0;i<advanceChemicalReactorRecipes.size();i++){ //Search each recipe
			boolean fit=true;
			for (int j=0;j<5;j++){ //Look at each requirement in the recipe
				if (advanceChemicalReactorRecipes.get(i)[j]!=null)
					if(!findRequirement(advanceChemicalReactorRecipes.get(i)[j],items))
						fit=false;
			}
			
			if(fit){
				for (int j=0;j<num;j++){ //Find any unwelcome element not in the recipe
					if(!findRequirementIgnoreAmount(items[j],advanceChemicalReactorRecipes.get(i)))
						fit=false;
				}
				
				//ItemStack cat=advanceChemicalReactorRecipes.get(i)[10];
				//if(cat!=null){
				//	if(in[12]==null) //Missing catalyst
				//		fit=false;
				//	else if(!cat.isItemEqual(in[12])) //Condition mismatch
				//		fit=false;
				//}
				//else if(cat==null&&in[12]!=null) //Condition mismatch
					//fit=false;
			}
			
			if (fit)
				return i;
		}
		return -1;
	}

	/** Internal use only!*/
	static boolean findRequirementIgnoreAmount(ItemStack theRecipe,ItemStack items[]){ //Determine if theRecipe is in items
		for (int k=0;k<5;k++){ 	//Look at each item we have to try to match the recipe
			if (items[k]!=null&&theRecipe.isItemEqual(items[k])){
					return true;
			}
		}
		return false;
	}
	
	/** Internal use only!*/
	static boolean findRequirement(ItemStack theRecipe,ItemStack items[]){ //Determine if theRecipe is in items
		for (int k=0;k<5;k++){ 	//Look at each item we have to try to match the recipe
			if (items[k]!=null&&theRecipe.isItemEqual(items[k])){
				int a=items[k].stackSize,b=theRecipe.stackSize;
				if(a>=b)
					return true;
			}
		}
		return false;
	}
	
	//Condense Tower------------------------------------------------------------------------------------
	public static void addCondenseTowerRecipe(FluidStack in,int energy,int tick,FluidStack... outs){		
		condenseTowerRecipes.put(in.getFluid().getID(), outs);
		condenseTowerRecipeInfo.put(in.getFluid().getID(), new Integer[]{in.amount,energy,tick});
	}

	/** Find a recipe for inFluid, amount is checked*/
	public static FluidStack[] getCondenceTowerRecipes(FluidStack inFluid){
		FluidStack[] r=condenseTowerRecipes.get(inFluid.getFluid().getID());
		
		if(r==null)
			return null;
		
		if(getCondenceTowerRecipeInfo(inFluid)[0]>inFluid.amount)
			return null; //Not enough liquid
		
		return r;
	}
	
	public static Integer[] getCondenceTowerRecipeInfo(FluidStack in){
		return condenseTowerRecipeInfo.get(in.getFluid().getID());
	}
	
	/** Get the tier(minimum height required for the Condense Tower) */
	public static int getCondensetowerRecipesTier(FluidStack[] rec){
		if(rec==null)
			return -1;
		else
			return rec.length;
	}
	
	//Thermal Cracker---------------------------------------------------------------------------------------------------------------------

	/** Add a new recipe for Thermal Cracker  energy=per tick tick=total tick*/
	public static void addThermalCrackerRecipe(ItemStack in,int energy,int tick,ItemStack out,FluidStack outl){
		thermalCrackerRecipes.add(new Object[]{in,out,outl,energy,tick});
	}
	
	/** Find a specific recipe for Thermal Cracker(amount sensitive)*/
	public static Object[] getThermalCrackerRecipe(ItemStack in){
		if(in==null)
			return null;
		
		for (Object[] rec:thermalCrackerRecipes){
			ItemStack recIn=(ItemStack) rec[0];
			if(recIn.isItemEqual(in)){
				if(in.stackSize>=recIn.stackSize) 
					return rec;  //Find the match recipe
				else
					return null; //Don't have enough item
			}
		}
		return null;
	}
	
	//Combustion Furnace---------------------------------------------------------------------------------------------------------------------
	/** Add a new recipe for Combustion Furnace, the input must be a fuel*/
	public static void addCombustionFurnaceRecipe(ItemStack in,ItemStack out,FluidStack outl){
		combustionFurnaceRecipes.add(new Object[]{in,out,outl});
	}
	/** Add a new recipe for Combustion Furnace, the input must be a fuel, this is a ore dictionary version*/
	public static void addCombustionFurnaceRecipe(String in,ItemStack out,FluidStack outl){
		for (ItemStack itemStack: OreDictionary.getOres(in))
			addCombustionFurnaceRecipe(itemStack,out,outl);
	}
	
	/** Find a specific recipe for Combustion Furnace*/
	public static Object[] getCombustionFurnaceRecipe(ItemStack in){
		if(in==null)
			return null;
		
		for (Object[] rec:combustionFurnaceRecipes){
			ItemStack recIn=(ItemStack) rec[0];
			if(recIn.isItemEqual(in))
				return rec;  //Find the match recipe
		}
		return null;
	}
	
	
	//Mobile Power Supply
	/** Add a custom solar panel supply for Mobile Power Supply
	 *  !!!ONLY USE THIS FOR BLOCKS!!! */
	public static void addMobilePSSolarUpdate(ItemStack item,int amountDay,int amountNight){
		if (item==null)
			return;
		mobilePSSolarUpdates.add(new int[]{item.itemID,item.getItemDamage(),amountDay,amountNight,0});
	}
	
	/** Add a custom solar panel supply for Mobile Power Supply
	 *  !!!Only USE THIS FOR ITEMS!!! */
	public static void addMobilePSSolarUpdateI(ItemStack item,int amountDay,int amountNight){
		if (item==null)
			return;
		mobilePSSolarUpdates.add(new int[]{item.itemID,item.getItemDamage(),amountDay,amountNight,1});
	}
	
	/** Get the value of the generating rate of the solar panel at day and night time
	 * 	Returns:
	 * 	par0: value of currently generating
	 * 	par1: 0: Invalid solar update
	 * 		  1: Block solar update
	 * 		  2: Item solar update*/
	public static int[] getMobilePSSolarValue(ItemStack item,boolean isNight){
		int []r = new int[2];
		if (item==null)
			return null;
		
		r[1]=0;
		
		int[]j;
		for (int i=0;i<mobilePSSolarUpdates.size();i++){
			j=mobilePSSolarUpdates.get(i);
			if (j[0]==item.itemID&
				j[1]==item.getItemDamage()){
				if (j[4]==1)
					r[1]=2;
				else
					r[1]=1;
				
				if (!isNight)
					r[0]=j[2];
				else 
					r[0]=j[3];
			}
		}
		
		return r;
	}
	
	/** Add a custom energy upgrade item for Mobile Power Supply */
	public static void addMobilePSEnergyUpdate(ItemStack item,int amount){
		if (item==null)
			return;
		mobilePSEnergyUpdates.add(new int[]{item.itemID,item.getItemDamage(),amount});
	}	
	
	/** Get the value of the storage updates provided by the item
	 * 	Returns 0 if the item is not a valid registered storage energy updater*/
	public static int getMobilePSEnergyUpdatesValue(ItemStack item){
		if (item==null)
			return 0;
		
		int[]j;
		for (int i=0;i<mobilePSEnergyUpdates.size();i++){
			j=mobilePSEnergyUpdates.get(i);
			if (j[0]==item.itemID&
				j[1]==item.getItemDamage())
					return j[2];
		}
		
		return 0;
	}	
}

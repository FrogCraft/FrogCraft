package FrogCraft.Common;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.liquids.LiquidStack;


public class RecipeManager {
	/** {idIn,damageIn,amountIn,idOut,damageOut,amountOut,energy,tick} */
	public static List<int[]> liquifierRecipes = new ArrayList<int[]>();
	/** {idIn,damageIn,amountIn,energy,tick,idOut, damageOut, amountOut....}*/
	public static List<int[]> liquidInjectorRecipes = new ArrayList<int[]>();
	/** {idIn,damageIn,amountIn,energy,tick,idOut,damageOut,amountOut,idOut,damageOut,amountOut} */
	public static List<int[]> thermalCrackerRecipes =  new ArrayList<int[]>();
	
	/** {id,damage,amountDay,amountNight,isItem} 
	 * 	isItem: Do not change the top texture, especially for items such as GT solar component,
	 *  		should set to true for items and false for blocks,otherwise crash!*/
	public static List<int[]> mobilePSSolarUpdates =  new ArrayList<int[]>();	
	/** {id,damage,amount} */
	public static List<int[]> mobilePSEnergyUpdates =  new ArrayList<int[]>();		

	/**	0-4:Reactants 5-9:Products 10:Catalyst 11:cellNeed 12:cellOut*/
	public static List<ItemStack[]> advanceChemicalReactorRecipes = new ArrayList<ItemStack[]>();
	/** {eu,tick,tickWithCatalyst} */
	public static List<int[]> advanceChemicalReactorRecipeInfo =  new ArrayList<int[]>();	
	
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
	
	//Liquefier--------------------------------------------------------------------------------------------------------------------------------------
	/** add a new recipe for liquifier */
	public static void addLiquifierRecipe(int idIn,int damageIn,int amountIn,int idOut,int damageOut,int amountOut,int energy,int tick){
		liquifierRecipes.add(new int[]{idIn,damageIn,amountIn,idOut,damageOut,amountOut,energy,tick});
	}
	
	public static void addLiquifierRecipe(LiquidStack in,LiquidStack out,int energy,int tick){
		liquifierRecipes.add(new int[]{in.itemID,in.itemMeta,in.amount,out.itemID,out.itemMeta,out.amount,energy,tick});
	}
	
	/** find a recipe for liquifier*/
	public static int[] getLiquifierRecipe(int idIn,int damageIn,int amountIn){
		int[] j;
		for (int i=0;i<liquifierRecipes.size();i++){
			j=liquifierRecipes.get(i);
			if (j[0]==idIn&j[1]==damageIn&j[2]<=amountIn){
				return j;
			}
		}
		return null;
	}
	
	/** find a recipe for liquifier*/
	public static int[] getLiquifierRecipe(LiquidStack in){
		return getLiquifierRecipe(in.itemID,in.itemMeta,in.amount);
	}
	
	//Liquid Injector-------------------------------------------------------------------------------------------------------------------------------

	/** add a new liquid recipe for condense tower  energy-per in*/
	public static void addLiquidInjectorRecipe(LiquidStack in,int energy,int tick,LiquidStack out1,LiquidStack out2,LiquidStack out3,LiquidStack out4,LiquidStack out5){
		addLiquidInjectorRecipe(in.itemID,in.itemMeta,in.amount,energy,tick,out1,out2,out3,out4,out5);
	}
	
	/** add a new liquid recipe for condense tower */
	public static void addLiquidInjectorRecipe(int idIn,int damageIn,int amountIn,int energy,int tick,LiquidStack out1,LiquidStack out2,LiquidStack out3,LiquidStack out4,LiquidStack out5){
		if (out1==null) out1=new LiquidStack(0,0);
		if (out2==null) out2=new LiquidStack(0,0);
		if (out3==null) out3=new LiquidStack(0,0);
		if (out4==null) out4=new LiquidStack(0,0);	
		if (out5==null) out5=new LiquidStack(0,0);		
		liquidInjectorRecipes.add(new int[]{idIn,damageIn,amountIn,energy,tick,
				out1.itemID,out1.itemMeta,out1.amount,
				out2.itemID,out2.itemMeta,out2.amount,
				out3.itemID,out3.itemMeta,out3.amount,
				out4.itemID,out4.itemMeta,out4.amount,
				out5.itemID,out5.itemMeta,out5.amount});		
	}
	
	/** find a liquid recipe for condense tower */
	public static int[] getLiquidInjectorRecipes(int idIn,int damageIn,int amountIn){
		int[] j;
		for (int i=0;i<liquidInjectorRecipes.size();i++){
			j=liquidInjectorRecipes.get(i);
			if (j[0]==idIn&j[1]==damageIn&j[2]<=amountIn){
				return j;
			}
		}
		return null;
	}
	
	/** find a specific liquid recipe for condense tower 
	 * 	index=0,1,2,3,4
	 * 	return:id,damage,amount*/
	public static int[] getLiquidInjectorRecipesX(int[] r,int index){
		if (r==null)
			return null;
		
		if (r[(3*index)+5]==0)
			return null;
		
		int[] result=new int[3];
		result[0]=r[(3*index)+5];
		result[1]=r[(3*index)+6];
		result[2]=r[(3*index)+7];
		return result;
	}
	
	/** Check if specific output in the recipe is needed or not*/
	public static boolean hasLiquidInjectorRecipesX(int[] r,int index){
		if (r==null)
			return false;
		
		if (r[index+5]==0)
			return false;
		
		return true;
	}
	
	/** Get the tier(minimum height of the Condense Tower) */
	public static int getLiquidInjectorRecipesTier(int[] r){
		int t=1;
		if (r[8]>0) t=2;
		if (r[11]>0) t=3;
		if (r[14]>0) t=4;
		if (r[17]>0) t=5;
		return t;
	}
	
	//Thermal Cracker---------------------------------------------------------------------------------------------------------------------

	/** Add a new recipe for Thermal Cracker  energy-per tick*/
	public static void addThermalCrackerRecipe(ItemStack in,int energy,int tick,ItemStack out,LiquidStack outl){
		if (in==null)
			return;
		if (outl==null) outl=new LiquidStack(0,0,0);
		if (out==null) out=new ItemStack(0,0,0);
		thermalCrackerRecipes.add(new int[]{in.itemID,in.getItemDamage(),in.stackSize,energy,tick,out.itemID,out.getItemDamage(),out.stackSize,outl.itemID,outl.itemMeta,outl.amount});
	}
	
	/** Find a specific recipe for Thermal Cracker*/
	public static int[] getThermalCrackerRecipe(ItemStack in){
		int[] j;
		for (int i=0;i<thermalCrackerRecipes.size();i++){
			j=thermalCrackerRecipes.get(i);
			if (j[0]==in.itemID&j[1]==in.getItemDamage()&j[2]<=in.stackSize){
				return j;
			}
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

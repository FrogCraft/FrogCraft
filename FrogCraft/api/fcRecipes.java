package FrogCraft.api;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

/** This is a part of FrogCraft API allow you to access FrogCraft Recipes, you can include this as apart of your mod,
 *  do not modify any thing, otherwise it will cause unknown compatibility error or any unexpected result!
 *  Call this anytime you want (after you register your items) */
public class fcRecipes {
	//API functions - FrogCraft does not uses these functions internally, you can copy them in to your mod
	/** Add a recipe to Advance Chemical Reactor
	 * 	tick=-1 means catalyst is need to start the reaction
	 * 	eu means total cosumed eu!*/
	public static void addAdvanceChemicalReactorRecipe(ItemStack r1,ItemStack r2,ItemStack r3,ItemStack r4,ItemStack r5,
													   ItemStack o1,ItemStack o2,ItemStack o3,ItemStack o4,ItemStack o5,
			                                           ItemStack catalyst,ItemStack cellNeeded,ItemStack cellout,int eu,int tick,int tickWithCatalyst){
		try {
			Class.forName("FrogCraft.Common.RecipeManager").getMethod("addAdvanceChemicalReactorRecipe", ItemStack.class,ItemStack.class,ItemStack.class,ItemStack.class,ItemStack.class,
					ItemStack.class,ItemStack.class,ItemStack.class,ItemStack.class,ItemStack.class,
					ItemStack.class,ItemStack.class,ItemStack.class,int.class,int.class,int.class)
					.invoke(null, r1,r2,r3,r4,r5,o1,o2,o3,o4,o5,catalyst,cellNeeded,cellout,eu,tick,tickWithCatalyst);
		}catch (Exception e) {}
	}
	
	/** Add a recipe to Advance Chemical Reactor*/
	public static void addAdvanceChemicalReactorRecipe(ItemStack r1,ItemStack r2,ItemStack r3,ItemStack r4,ItemStack r5,
													   ItemStack o1,ItemStack o2,ItemStack o3,ItemStack o4,ItemStack o5,
													   int eu,int tick){
		addAdvanceChemicalReactorRecipe(r1,r2,r3,r4,r5,o1,o2,o3,o4,o5,null,null,null,eu,tick,-1);
	}
	
	/** add a new recipe for condense tower energy means eu cosumed per action*/
	public static void addCondenseTowerRecipe(FluidStack in,int energy,int tick,FluidStack... out){
		try {
		Class.forName("FrogCraft.Common.RecipeManager").getMethod("addCondenseTowerRecipe", FluidStack.class,int.class,int.class,FluidStack[].class)
		.invoke(null, in,energy,tick,out);
		}catch (Exception e) {}	
	}
	
	/** Add a new recipe for Thermal Cracker  energy-per tick*/
	public static void addThermalCrackerRecipe(ItemStack in,int energy,int tick,ItemStack out,FluidStack outl){
		try {
		Class.forName("FrogCraft.Common.RecipeManager").getMethod("addThermalCrackerRecipe", ItemStack.class,int.class,int.class,ItemStack.class,FluidStack.class)
		.invoke(null, in,energy,tick,out,outl);
		}catch (Exception e) {}			
	}
	
	/** Add a new recipe for Combustion Furnace, the input itemstack must be a fuel!!!*/
	public static void addCombustionFurnaceRecioe(ItemStack in,ItemStack out,FluidStack outl){
		try {
		Class.forName("FrogCraft.Common.RecipeManager").getMethod("addCombustionFurnaceRecipe", ItemStack.class,ItemStack.class,FluidStack.class)
		.invoke(null, in,out,outl);
		}catch (Exception e) {}	
	}
	
	/** Add a custom solar panel supply for Mobile Power Supply
	 *  !!!ONLY USE THIS FOR BLOCKS!!! */
	public static void addMobilePSSolarUpdateBlock(ItemStack item,int amountDay,int amountNight){
		try {
		Class.forName("FrogCraft.Common.RecipeManager").getMethod("addMobilePSSolarUpdate", ItemStack.class,int.class,int.class)
		.invoke(null, item,amountDay,amountNight);
		}catch (Exception e) {}	
	}
	
	/** Add a custom solar panel supply for Mobile Power Supply
	 *  !!!Only USE THIS FOR ITEMS!!! */
	public static void addMobilePSSolarUpdateItem(ItemStack item,int amountDay,int amountNight){
		try {
		Class.forName("FrogCraft.Common.RecipeManager").getMethod("addMobilePSSolarUpdateI", ItemStack.class,int.class,int.class)
		.invoke(null, item,amountDay,amountNight);
		}catch (Exception e) {}			
	}
}

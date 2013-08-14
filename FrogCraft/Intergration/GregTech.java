package FrogCraft.Intergration;

import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;

import gregtechmod.api.*;
import gregtechmod.api.util.GT_Recipe;

public class GregTech extends GregTech_API{
	public static boolean isGTLoaded=false;
	public static ItemStack sampleITNT=null;
	
	public static int GTComponent=-1;
	
	public static void init(){
		sampleITNT=new ItemStack(ic2.getItem("industrialTnt").itemID,64,0);
		isGTLoaded=isGregTechLoaded();
		
		if (GregTech_API.getGregTechItem(3, 1, 27)!=null)
			GTComponent=GregTech_API.getGregTechItem(3, 1, 27).itemID;
		else
			GTComponent=-1;
	}
	
	public static ItemStack getGTItem(int damage,int amount){
		return GregTech_API.getGregTechItem(0, amount, damage);
	}
	
	public static ItemStack getGTDust(int damage,int amount){
		return GregTech_API.getGregTechItem(1, amount, damage);
	}

	public static ItemStack getGTCell(int damage,int amount){
		return GregTech_API.getGregTechItem(2, amount, damage);
	}
	
	public static ItemStack getGTComponent(int damage,int amount){
		return GregTech_API.getGregTechItem(3, amount, damage);
	}
	
	public static ItemStack getGTSmallDust(int damage,int amount){
		return GregTech_API.getGregTechItem(4, amount, damage);
	}
	
	/**Return:{output,itntStackSize,cosumedInput}*/
	public static Object[] findImplosionRecipe(ItemStack in){
    	int recid = GT_Recipe.findEqualImplosionRecipeIndex(in,sampleITNT);
    	if(recid<0)
    		return null;
    	
    	GT_Recipe g=GT_Recipe.sImplosionRecipes.get(recid);
    	return new Object[]{g.mOutput1,g.mOutput2.stackSize,g.mInput1.stackSize};
	}
	
	
}

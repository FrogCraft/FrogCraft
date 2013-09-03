package FrogCraft.Intergration;

import gregtechmod.api.util.GT_Recipe;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;


public class GregTech{
	public static boolean isGTLoaded=false;
	public static ItemStack sampleITNT=null;
		
	public static int GTComponent=-1;
	
	public static void init(){
		sampleITNT=new ItemStack(ic2.getItem("industrialTnt").itemID,64,0);
		isGTLoaded=ModLoader.isModLoaded("gregtech_addon");
		
		if (getGregTechItem(3, 1, 27)!=null)
			GTComponent=getGregTechItem(3, 1, 27).itemID;
	}
	
	public static ItemStack getGTItem(int damage,int amount){
		return getGregTechItem(0, amount, damage);
	}
	
	public static ItemStack getGTDust(int damage,int amount){
		return getGregTechItem(1, amount, damage);
	}

	public static ItemStack getGTCell(int damage,int amount){
		return getGregTechItem(2, amount, damage);
	}
	
	public static ItemStack getGTComponent(int damage,int amount){
		return getGregTechItem(3, amount, damage);
	}
	
	public static ItemStack getGTSmallDust(int damage,int amount){
		return getGregTechItem(4, amount, damage);
	}
	
	/**Return:{output,itntStackSize,cosumedInput}*/
	public static Object[] findImplosionRecipe(ItemStack in){
		if(!isGTLoaded)
			return null;
		
    	int recid = GT_Recipe.findEqualImplosionRecipeIndex(in,sampleITNT);
    	if(recid<0)
    		return null;
    	
    	GT_Recipe g=GT_Recipe.sImplosionRecipes.get(recid);
    	return new Object[]{g.mOutput1,g.mOutput2.stackSize,g.mInput1.stackSize};
	}
	
	private static ItemStack getGregTechItem(int type,int amount,int damage){
		try{
			return (ItemStack) Class.forName("gregtechmod.api.GregTech_API").getMethod("getGregTechItem", int.class,int.class,int.class).invoke(null, type,amount,damage);
		}catch(Exception e){}
		return null;
	}
}

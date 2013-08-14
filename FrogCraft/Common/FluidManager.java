package FrogCraft.Common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData;

public class FluidManager {
	//Fluid Registry
	//Icon is registered in FrogCraft.Machines.BlockMachines!!!!!
	/**{filledContainer,emptyContainer}*/
	public static HashMap<String,Object[]> fluids=new HashMap();
	
	public static void RegisterFluid(String fluidName,ItemStack filledContainer){
		RegisterFluid(fluidName,filledContainer,ic2.api.item.Items.getItem("cell"));
	}
	
	public static void RegisterFluid(String fluidName,ItemStack filledContainer,ItemStack emptyContainer){
		fluids.put(fluidName,new Object[]{filledContainer,emptyContainer});
	}
	
	public static void initFluids(){
		for (String fluidName:fluids.keySet()){
			Fluid fluid=new Fluid(fluidName);
			FluidContainerRegistry.registerFluidContainer(fluid, (ItemStack)(fluids.get(fluidName)[0]), (ItemStack)(fluids.get(fluidName)[1]));			
			FluidRegistry.registerFluid(fluid);
		}
	}
	
	public static FluidStack getFluid(String fluidName){return getFluid(fluidName,1000);}
	
	public static FluidStack getFluid(String fluidName,int volume){
		return new FluidStack(FluidRegistry.getFluidID(fluidName.toLowerCase(Locale.ENGLISH)),volume);
	}
	//Common used methods----------------------------------------------------------------------
	
	/**Fill a empty container with fluid*/
	public static void fillContainer(FluidStack fluid,ItemStack[] slots,int empty,int target){
		ItemStack s=FluidContainerRegistry.fillFluidContainer(fluid, slots[empty]);
			
    	if (s==null) //Not a empty container or mismatch container
    		return;
    	
    	if (slots[target]!=null)
    		//The target slot is occupied by some other itemstack
    		if (slots[target].itemID!=s.itemID|slots[target].getItemDamage()!=s.getItemDamage()|
    		//Their are no enough space
    		slots[target].stackSize>slots[target].getMaxStackSize()-s.stackSize)
    			return;
    
    	
    	if (slots[target]==null)
    		slots[target]=s; //Create a new stack when the target slot is empty
    	else
    		slots[target].stackSize+=s.stackSize; //Otherwise increase the stackSize
    	
    	slots[empty].stackSize-=1;    	//Use a empty container
    	if(slots[empty].stackSize==0)
    		slots[empty]=null;          //If all empty containers are used, then will not display it anymore
    	
    	//Consume fluids from the fluid
    	fluid.amount-=FluidContainerRegistry.getFluidForFilledItem(slots[target]).amount; 
	}
	
	/**Return the fluid drain from a filled container if it's allowed*/
	public static FluidStack drainContainer(int maxVolume,FluidStack currentLiquid,ItemStack[] slots,int filled,int target){
		FluidStack fluidInItem=FluidContainerRegistry.getFluidForFilledItem(slots[filled]);
		
		if(fluidInItem==null)
			return null;//Not a valid fluid containing item
		
    	
    	ItemStack s=getEmptyContainer(slots[filled]);
    	if (s==null)
    		return null;  //TODO : No empty Container, should we eat it? May be here's a bug
    	
    	if (slots[target]!=null)
    		//Different stack at the output 
    		if (slots[target].itemID!=s.itemID|slots[target].getItemDamage()!=s.getItemDamage()|
    		//Too many stack at the output
    		slots[target].stackSize>slots[target].getMaxStackSize()-s.stackSize)
    			return null;
    	
    	
    	if (currentLiquid!=null){
    		if (!fluidInItem.isFluidEqual(currentLiquid))
    			return null; //Fluid type mismatch
    		if (fluidInItem.amount>maxVolume-currentLiquid.amount)
    			return null; //No enough space
    	}
    	
   
    	
    	//Make a output
    	if (slots[target]==null){
    		s.stackSize=1;
    		slots[target]=s;
    	}
    	else
    		slots[target].stackSize+=1;
    	
    	
    	slots[filled].stackSize-=1; //Consume the filled stack
    	if (slots[filled].stackSize==0)
    		slots[filled]=null;     //Remove empty stacks
    		

    	return fluidInItem;
    }
	
	/**Get the empty container for the filled container*/
	public static ItemStack getEmptyContainer(ItemStack filled){
		for (FluidContainerRegistry.FluidContainerData dat:FluidContainerRegistry.getRegisteredFluidContainerData())
			if (dat.filledContainer.isItemEqual(filled))
				return dat.emptyContainer;
    	return null;
    }
	
	public static ItemStack[] getFilledContainers(FluidStack stack){
		return getFilledContainers(stack.fluidID);
	}
	
	public static ItemStack[] getFilledContainers(int id){
		FluidStack fluid=new FluidStack(id,1000);
		List<ItemStack> result = new ArrayList<ItemStack>();
		for(FluidContainerData data:FluidContainerRegistry.getRegisteredFluidContainerData()){
			if (data.fluid.isFluidEqual(fluid))
				result.add(data.filledContainer);
		}
		return result.toArray(new ItemStack[]{});
	}
	
	public static String getFluidDisplayName(int fluidID){
		//TODO We have to find out a way to display fluid name!!
		return StatCollector.translateToLocal((new FluidStack(fluidID,1000)).getFluid().getLocalizedName());
	}
}

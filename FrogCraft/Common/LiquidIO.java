package FrogCraft.Common;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.liquids.LiquidContainerData;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidStack;

public class LiquidIO {
	public static void fillContainer(LiquidStack liquid,ItemStack[] slots,int empty,int target){
    	if (!LiquidContainerRegistry.isEmptyContainer(slots[empty]))
    		return;
    	
    	ItemStack s=LiquidContainerRegistry.fillLiquidContainer(liquid,slots[empty]);
    	if (s==null)
    		return;
    	if (slots[target]!=null)
    		if (slots[target].itemID!=s.itemID|slots[target].getItemDamage()!=s.getItemDamage()|slots[target].stackSize>slots[target].getMaxStackSize()-s.stackSize)
    			return;
    
    	
    	if (slots[target]==null)
    		slots[target]=s;
    	else
    		slots[target].stackSize+=s.stackSize;
    	
    	if (slots[empty].stackSize==1)
    		slots[empty]=null;
    	else
    		slots[empty].stackSize-=1;    	
    	
    	liquid.amount-=LiquidContainerRegistry.getLiquidForFilledItem(slots[target]).amount;    	
    }
    
	public static ItemStack getEmptyContainer(ItemStack filled){
    	LiquidContainerData[] dat=LiquidContainerRegistry.getRegisteredLiquidContainerData();
    	for (int i=0;i<dat.length;i++){
    		if (dat[i].filled.itemID==filled.itemID&dat[i].filled.getItemDamage()==filled.getItemDamage())
    			return dat[i].container;
    	}
    	
    	return null;
    }
    
	public static LiquidStack drainContainer(int maxVolume,LiquidStack currentLiquid,ItemStack[] slots,int filled,int target){
    	if (!LiquidContainerRegistry.isFilledContainer(slots[filled]))
    		return null;
    	
    	ItemStack s=getEmptyContainer(slots[filled]);
    	if (s==null)
    		return null;
    	if (slots[target]!=null)
    		if (slots[target].itemID!=s.itemID|slots[target].getItemDamage()!=s.getItemDamage()|slots[target].stackSize>slots[target].getMaxStackSize()-s.stackSize)
    			return null;
    	
    	LiquidStack l=LiquidContainerRegistry.getLiquidForFilledItem(slots[filled]);
    	
    	if (currentLiquid!=null){
    		if (l.itemID!=currentLiquid.itemID|l.itemMeta!=currentLiquid.itemMeta)
    			return null;
    		if (l.amount>maxVolume-currentLiquid.amount)
    			return null;
    	}
    	
   
    	
    	//actions
    	
    	if (slots[target]==null){
    		s.stackSize=1;
    		slots[target]=s;
    	}
    	else
    		slots[target].stackSize+=1;
    	
    	if (slots[filled].stackSize==1)
    		slots[filled]=null;
    	else
    		slots[filled].stackSize-=1;

    	return l;
    }
	
	public static ItemStack[] getFilledContainers(int id,int damage){
		LiquidStack liquid=new LiquidStack(id,1,damage);
		List<ItemStack> result = new ArrayList<ItemStack>();
		for(LiquidContainerData data:LiquidContainerRegistry.getRegisteredLiquidContainerData()){
			if (data.stillLiquid.isLiquidEqual(liquid))
				result.add(data.filled);
		}
		return result.toArray(new ItemStack[]{});
	}
}

package FrogCraft.Items;


import java.util.List;

import FrogCraft.api.fcItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;

public class Item_RadioactiveDecayCell extends Item implements ic2.api.item.IElectricItem{
	public int rate=0;
	
	public Item_RadioactiveDecayCell(int id,String name,int rate) {
		super(id);
		setNoRepair();
		setMaxStackSize(1);
		setMaxDamage(0);
		setUnlocalizedName(name);
		setCreativeTab(fcItems.tabFrogCraft);	
		this.rate=rate;
	}
	
	
    private void setCharge(ItemStack aStack){
    	if (aStack.stackTagCompound == null) aStack.stackTagCompound = new NBTTagCompound();
    	aStack.stackTagCompound.setInteger("charge", rate);
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister r){
    	itemIcon=r.registerIcon("FrogCraft:"+getUnlocalizedName());
    }
    
	@Override
	public boolean canProvideEnergy(ItemStack itemStack){setCharge(itemStack);return true;}

	@Override
	public int getChargedItemId(ItemStack itemStack) {setCharge(itemStack);return itemID;}

	@Override
	public int getEmptyItemId(ItemStack itemStack) {setCharge(itemStack);return itemID;}

	@Override
	public int getMaxCharge(ItemStack itemStack) {setCharge(itemStack);return rate;}

	@Override
	public int getTier(ItemStack itemStack) {setCharge(itemStack);return 1;}

	@Override
	public int getTransferLimit(ItemStack itemStack) {setCharge(itemStack);return rate;}

}

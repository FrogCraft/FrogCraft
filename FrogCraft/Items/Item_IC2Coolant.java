package FrogCraft.Items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import FrogCraft.mod_FrogCraft;
import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class Item_IC2Coolant extends Item implements IReactorComponent{

	public static class Item_IC2CoolantNH3_60K extends Item_IC2Coolant{
		public Item_IC2CoolantNH3_60K(int id) {
			super(id,60000,"NH3_60K");
		}
	}
	
	public static class Item_IC2CoolantNH3_180K extends Item_IC2Coolant{
		public Item_IC2CoolantNH3_180K(int id) {
			super(id,180000,"NH3_180K");
		}
	}
	
	public static class Item_IC2CoolantNH3_360K extends Item_IC2Coolant{
		public Item_IC2CoolantNH3_360K(int id) {
			super(id,360000,"NH3_360K");
		}
	}	
	
	
	public int heatStorage;
	public String name;
	public Item_IC2Coolant(int id,int heatCapacity,String name) {
		super(id);
		heatStorage=heatCapacity;
		this.name=name;
		
		setNoRepair();
		setMaxStackSize(1);
		setMaxDamage(10000);
		setUnlocalizedName("Item_Coolant_"+name);
		setCreativeTab(mod_FrogCraft.tabFrogCraft);	
	}

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister par1IconRegister)
    {
    	itemIcon=par1IconRegister.registerIcon("FrogCraft:Coolant_"+name);
    }

	@Override
	public void processChamber(IReactor reactor, ItemStack yourStack, int x,int y) {}

	@Override
	public boolean acceptUraniumPulse(IReactor reactor, ItemStack yourStack,
			ItemStack pulsingStack, int youX, int youY, int pulseX, int pulseY) {
		return false;
	}

	@Override
	public boolean canStoreHeat(IReactor reactor, ItemStack yourStack, int x,
			int y) {	 
		return true;
	}

	@Override
	public int getMaxHeat(IReactor reactor, ItemStack yourStack, int x, int y) {
		return heatStorage;
	}

	@Override
	public int getCurrentHeat(IReactor reactor, ItemStack yourStack, int x,
			int y) {
		return getHeatOfStack(yourStack);
	}

	@Override
	public int alterHeat(IReactor reactor, ItemStack yourStack, int x, int y,int heat) {
		int myHeat = getHeatOfStack(yourStack);
		myHeat += heat;
		int dmg=getMaxDamage()*myHeat/heatStorage;

		if (myHeat > this.heatStorage)
		{
			reactor.setItemAt(x, y, null);
			heat = this.heatStorage - myHeat + 1;
		}
		else
		{
			if (myHeat < 0)
			{
				heat = myHeat;
				myHeat = 0;
			}
			else {
				heat = 0;
			}
			setHeatForStack(yourStack, myHeat);
		}
		yourStack.setItemDamage(dmg);
		return heat;
	}

	@Override
	public float influenceExplosion(IReactor reactor, ItemStack yourStack) {
		return 0;
	}

	private void setHeatForStack(ItemStack stack, int heat)
	{
		NBTTagCompound nbtData = getOrCreateNbtData(stack);
		nbtData.setInteger("heat", heat);
		if (this.heatStorage > 0)
		{
			double p = heat / this.heatStorage;
			int newDmg = (int)(stack.getMaxDamage() * p);
			if (newDmg >= stack.getMaxDamage()) newDmg = stack.getMaxDamage() - 1;
			stack.setItemDamage(newDmg);
		}
	}

	private int getHeatOfStack(ItemStack stack) {
		NBTTagCompound nbtData = getOrCreateNbtData(stack);
		return nbtData.getInteger("heat");
	}
	public static NBTTagCompound getOrCreateNbtData(ItemStack itemStack) {
		NBTTagCompound ret = itemStack.getTagCompound();
		if (ret == null) {
			ret = new NBTTagCompound("tag");
			itemStack.setTagCompound(ret);
		}
		return ret;
	}
}

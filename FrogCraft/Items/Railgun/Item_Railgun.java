package FrogCraft.Items.Railgun;

import java.util.List;

import FrogCraft.mod_FrogCraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;



public class Item_Railgun extends Item implements ic2.api.item.IElectricItem{
	public static int AmmoID=1;
	public static int euPerShot=100000;
	public static Icon icon,coin,using;
	
	public Item_Railgun(int par1){
		super(par1);
		maxStackSize = 1;
		setHasSubtypes(true);
		setUnlocalizedName("Item_Railgun");
		setCreativeTab(mod_FrogCraft.tabFrogCraft);
		setMaxDamage(256);
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
        
        ItemStack s= new ItemStack(par1, 1, 1);
        ic2.api.item.ElectricItem.charge(s, Integer.MAX_VALUE, 4, true, false);
        par3List.add(s);
    }
	
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
    	icon=itemIcon=par1IconRegister.registerIcon("FrogCraft:RailGun");
    	coin=par1IconRegister.registerIcon("FrogCraft:Coin_Onhand");
    	using=par1IconRegister.registerIcon("FrogCraft:RailGun_Coin");
    }
    

    
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
    	par3List.add(StatCollector.translateToLocal("item.Item_Railgun.info"));
    }

	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player)
	{
		if (!player.capabilities.isCreativeMode){			
			player.setItemInUse(item, this.getMaxItemUseDuration(item));
		}
		if (player.capabilities.isCreativeMode){
			player.setItemInUse(item, this.getMaxItemUseDuration(item));			
		}
		return item;
	}
	
	
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 72000;
    }
    
    @Override
    public void onPlayerStoppedUsing(ItemStack item, World world, EntityPlayer player, int tick)
    {    	
    	if (getMaxItemUseDuration(item)-tick<10)
    		return;
    	//player.spawnExplosionParticle();
    	
    	if (!player.capabilities.isCreativeMode&!player.inventory.hasItem(AmmoID))
    		return;
    
		if (player.capabilities.isCreativeMode){  //Creative mode
			if (!world.isRemote)
				world.spawnEntityInWorld(new EntityCoin(world, player));
			return;
		}
		

		if(ic2.api.item.ElectricItem.use(item, euPerShot, player)){  //Normal player
			if (!world.isRemote)
				world.spawnEntityInWorld(new EntityCoin(world, player));
				
			player.inventory.consumeInventoryItem(AmmoID);
		}
		else{ //Not enough electricity
			if (!world.isRemote)
				player.sendChatToPlayer(ChatMessageComponent.func_111066_d(StatCollector.translateToLocal("item.Item_Railgun.tooTired")));
		}
    	

    }
    
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.bow;
    }

    //IC2-------------------
    
	@Override
	public boolean canProvideEnergy(ItemStack itemStack) {return false;}

	@Override
	public int getChargedItemId(ItemStack itemStack) {return itemID;}

	@Override
	public int getEmptyItemId(ItemStack itemStack) {return itemID;}

	@Override
	public int getMaxCharge(ItemStack itemStack) {return 1000000;}

	@Override
	public int getTier(ItemStack itemStack) {return 3;}

	@Override
	public int getTransferLimit(ItemStack itemStack) {return 2048;}
}
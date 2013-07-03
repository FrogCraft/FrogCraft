package FrogCraft.Machines2.ACWindMill;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import FrogCraft.mod_FrogCraft;


import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.StringTranslate;
import net.minecraft.world.World;

public class Item_Fan extends Item {
	public static String name="ACWindMill Rotor";

	public Item_Fan(int par1) {
		super(par1);
		setUnlocalizedName("Frogcraft_Fan");
		setCreativeTab(mod_FrogCraft.tabFrogCraft);
	}

    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        itemIcon = par1IconRegister.registerIcon("FrogCraft:ACWindMill_Fan");
    }
    
    public String getItemDisplayName(ItemStack par1ItemStack)
    {
    	return name;
    }
	
	@Override
	public boolean onItemUse(ItemStack item_stack, EntityPlayer player,
			World world, int x, int y, int z, int side, float x_off,
			float y_off, float z_off) {
		
    	TileEntity te=world.getBlockTileEntity(x, y, z);
        if (te instanceof TileEntityACWindMillTop){
        	if (!((TileEntityACWindMillTop)te).settled){
        		((TileEntityACWindMillTop)te).settled=(true);
        		if (!player.capabilities.isCreativeMode)
        			player.inventory.consumeInventoryItem(itemID);
        	}
        	return true;
        }
        
        return false;
	}
}
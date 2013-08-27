package FrogCraft.Items.MobilePS;

import java.util.Random;

import FrogCraft.mod_FrogCraft;
import FrogCraft.api.fcItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMobilePS extends BlockContainer{
	private Icon[] iconBuffer;
	
	public BlockMobilePS(int id) {
		super(id, Material.rock);
		setCreativeTab(fcItems.tabFrogCraft);
		setUnlocalizedName("BlockMobilePS");
	}

    //Register Icons
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister r)    {
    	iconBuffer = new Icon[]{
    			r.registerIcon("FrogCraft:MobilePS_Bottom"),
    			r.registerIcon("FrogCraft:MobilePS_Top"),
    			r.registerIcon("FrogCraft:MobilePS_Side"),
    			r.registerIcon("FrogCraft:MobilePS_Side"),
    			r.registerIcon("FrogCraft:MobilePS_Side"),
    			r.registerIcon("FrogCraft:MobilePS_Side")
    			};
    }
	
	@SideOnly(Side.CLIENT)
    @Override
    public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int blockSide)
    {        
    	if (blockSide==1){
    		TileEntityMobilePS te=(TileEntityMobilePS) world.getBlockTileEntity(x, y, z);
    		if (te.topID!=0){
    			Block b=Block.blocksList[te.topID];
    			return b.getIcon(blockSide,te.topDamage);
    		}
    	}
    		
        return iconBuffer[mod_FrogCraft.sideAndFacingToSpriteOffset[blockSide][3]];
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public Icon getIcon(int blockSide, int blockMeta)
    {
    	return iconBuffer[mod_FrogCraft.sideAndFacingToSpriteOffset[blockSide][3]];
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(World world, int x, int y, int z, Random var5){
    	world.markBlockForRenderUpdate(x,  y,  z);
    }
    
	@Override
	public TileEntity createNewTileEntity(World world) {return new TileEntityMobilePS();}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack item) {
		if (!(world.getBlockTileEntity(x, y, z) instanceof TileEntityMobilePS))
			return;
		
		if (item.stackTagCompound!=null){
			TileEntityMobilePS te = (TileEntityMobilePS)world.getBlockTileEntity(x, y, z);
            NBTTagList tagList = item.stackTagCompound.getTagList("Inventory");
            for (int i = 0; i < tagList.tagCount(); i++) {
                    NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
                    byte slot = tag.getByte("Slot");
                    if (slot >= 0 && slot < te.inv.length) {
                            te.inv[slot] = ItemStack.loadItemStackFromNBT(tag);
                    }
            }
			te.maxEnergy=item.stackTagCompound.getInteger("maxEnergy");
			te.energy=item.stackTagCompound.getInteger("charge");
			te.onInventoryChanged();
			//Minecraft.getMinecraft().thePlayer.sendChatMessage(String.valueOf(te.maxEnergy));
			//Minecraft.getMinecraft().thePlayer.sendChatMessage(String.valueOf(te.energy));			
		}
		else
			((TileEntityMobilePS)world.getBlockTileEntity(x, y, z)).energy=0;
	}
	
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
    {
    	if (player.isSneaking())
    		return false;

        
        if (player.getCurrentEquippedItem() != null
        && (player.getCurrentEquippedItem().itemID == ic2.api.item.Items.getItem("wrench").itemID || player.getCurrentEquippedItem().itemID == ic2.api.item.Items.getItem("electricWrench").itemID))
        {            

        }
        else if(((player.getCurrentEquippedItem() != null)&&(player.getCurrentEquippedItem().itemID != ic2.api.item.Items.getItem("ecMeter").itemID))|(player.getCurrentEquippedItem() == null))
        {
        	player.openGui(FrogCraft.mod_FrogCraft.instance, 0, world, x, y, z);
        	return true;
        }
        return false;
    }
        
    @Override
    public int quantityDropped(Random par1Random)
    {
        return 0;
    }
    
    @Override
    public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
    	if (!(world.getBlockTileEntity(x, y, z) instanceof TileEntityMobilePS))
    		return;
    	
    	TileEntityMobilePS te=(TileEntityMobilePS) world.getBlockTileEntity(x, y, z);
    	ItemStack item = new ItemStack(this,1);
    	if (item.stackTagCompound==null)
    		item.stackTagCompound=new NBTTagCompound();
    	
    	item.stackTagCompound.setInteger("maxEnergy", te.maxEnergy);
    	ic2.api.item.ElectricItem.manager.charge(item, te.energy, 10, true, false);
    	
        NBTTagList itemList = new NBTTagList();
        for (int i = 0; i < te.inv.length; i++) {
                ItemStack stack = te.inv[i];
                if (stack != null) {
                        NBTTagCompound tag = new NBTTagCompound();
                        tag.setByte("Slot", (byte) i);
                        stack.writeToNBT(tag);
                        itemList.appendTag(tag);
                }
        }
    	item.stackTagCompound.setTag("Inventory", itemList);
    	
    	dropBlockAsItem_do(world,x,y,z,item);
    	super.breakBlock(world, x, y, z, par5, par6);
    }

}

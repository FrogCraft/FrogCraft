package FrogCraft.Machines2;

import ic2.api.energy.event.EnergyTileUnloadEvent;
import FrogCraft.*;
import FrogCraft.Common.BaseIC2Machine;
import FrogCraft.Common.SidedIC2Machine;
import FrogCraft.Machines.ItemBlockMachines;
import FrogCraft.Machines2.ACWindMill.*;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class BlockMachines2 extends BlockContainer {

	private Icon[][] iconBuffer;

	
	//Get TileEntities
    @Override
    public TileEntity createTileEntity(World world, int meta)
    {
    	switch (meta){
    		case 0:
    			return new TileEntityLiquidOutput();
    		case 1:
    			return new SidedIC2Machine();
    		case 2:
    			return new TileEntityACWindMillTop();
    		case 3:
    			return new TileEntityACWindMillBase();
    		case 4:
    			return new TileEntityAutoWorkBench();
    		default:
    				return null;
    	}
    }

    
    //Regist Icons
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister r)    {
    	iconBuffer = new Icon[ItemBlockMachines2.subNames.length][6];
    
    	//LiquidOutput
    	iconBuffer[0][0]=r.registerIcon("FrogCraft:CondenseTowerCylinder_Top");
    	iconBuffer[0][1]=r.registerIcon("FrogCraft:CondenseTowerCylinder_Top");
    	iconBuffer[0][2]=r.registerIcon("FrogCraft:CondenseTowerCylinder_Side");
    	iconBuffer[0][3]=r.registerIcon("FrogCraft:LiquidOutput");    	
    	iconBuffer[0][4]=r.registerIcon("FrogCraft:CondenseTowerCylinder_Side");
    	iconBuffer[0][5]=r.registerIcon("FrogCraft:CondenseTowerCylinder_Side");   
    	
    	//Condense tower cylinder
    	iconBuffer[1][0]=r.registerIcon("FrogCraft:CondenseTowerCylinder_Top");
    	iconBuffer[1][1]=r.registerIcon("FrogCraft:CondenseTowerCylinder_Top");
    	iconBuffer[1][2]=r.registerIcon("FrogCraft:CondenseTowerCylinder_Side");
    	iconBuffer[1][3]=r.registerIcon("FrogCraft:CondenseTowerCylinder_Side");    	
    	iconBuffer[1][4]=r.registerIcon("FrogCraft:CondenseTowerCylinder_Side");
    	iconBuffer[1][5]=r.registerIcon("FrogCraft:CondenseTowerCylinder_Side");  
    	
    	//ACWindMill_Top
    	iconBuffer[2][0]=r.registerIcon("FrogCraft:ACWindMill_Side");
    	iconBuffer[2][1]=r.registerIcon("FrogCraft:ACWindMill_Side");
    	iconBuffer[2][2]=r.registerIcon("FrogCraft:ACWindMill_Back");
    	iconBuffer[2][3]=r.registerIcon("FrogCraft:ACWindMill_Front");    	
    	iconBuffer[2][4]=r.registerIcon("FrogCraft:ACWindMill_Side");
    	iconBuffer[2][5]=r.registerIcon("FrogCraft:ACWindMill_Side");
    	
    	//ACWindMill_Base
    	iconBuffer[3][0]=r.registerIcon("FrogCraft:ACWindMill_Base_Bottom");
    	iconBuffer[3][1]=r.registerIcon("FrogCraft:ACWindMill_Base_Top");
    	iconBuffer[3][2]=r.registerIcon("FrogCraft:ACWindMill_Base_Side");
    	iconBuffer[3][3]=r.registerIcon("FrogCraft:ACWindMill_Base_Side");    	
    	iconBuffer[3][4]=r.registerIcon("FrogCraft:ACWindMill_Base_Side");
    	iconBuffer[3][5]=r.registerIcon("FrogCraft:ACWindMill_Base_Side");  
    	
    	//AutoWorkBench
    	iconBuffer[4][0]=r.registerIcon("FrogCraft:AutoWorkBench_Back");
    	iconBuffer[4][1]=r.registerIcon("FrogCraft:AutoWorkBench_Top");
    	iconBuffer[4][2]=r.registerIcon("FrogCraft:AutoWorkBench_Back");
    	iconBuffer[4][3]=r.registerIcon("FrogCraft:AutoWorkBench_Front");    	
    	iconBuffer[4][4]=r.registerIcon("FrogCraft:AutoWorkBench_Side");
    	iconBuffer[4][5]=r.registerIcon("FrogCraft:AutoWorkBench_Side");    	
    }
    
    //Gui Interface
    int GetGui(TileEntity te){
    	if (te instanceof TileEntityLiquidOutput)
    		return 7;

    	return -1;
    }
    
    
    //Visualize effects
    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(World world, int x, int y, int z, Random var5){
    	world.markBlockForRenderUpdate(x,  y,  z);
    }
    
    
    
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////    
    /////////////////////////Other Procedures///////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    //Main Procedure
    public BlockMachines2(int id) {
		super(id, Material.rock);
        setHardness(2.0F);
        setResistance(5.0F);
        setUnlocalizedName("Machines2");
        setCreativeTab(mod_FrogCraft.tabFrogCraft);
        


	}
	
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int par6, float par7, float par8, float par9)
    {
    
        if (entityPlayer.isSneaking())//|world.getBlockTileEntity(x, y, z)==null)
        {
            return false;   
        }
        
        TileEntity te = world.getBlockTileEntity(x, y, z);     
        
        if (te instanceof TileEntityACWindMillTop){
        	if (((TileEntityACWindMillTop)te).settled){
        		((TileEntityACWindMillTop)te).settled=false;
        		dropBlockAsItem_do(world,x,y,z,new ItemStack(mod_FrogCraft.Fan,1));
        		return true;
        	}
        }
        
        if(entityPlayer.getCurrentEquippedItem() != null&&entityPlayer.getCurrentEquippedItem().itemID==mod_FrogCraft.Fan.itemID){
        	return false;
        }
        
        
        if (entityPlayer.getCurrentEquippedItem() != null
        && (entityPlayer.getCurrentEquippedItem().getItemName().toLowerCase().contains("wrench")==true))
        {            

            if (te != null)
            {
                //EnergyNet.getForWorld(world).removeTileEntity(team);
            	if (te instanceof BaseIC2Machine)
            		MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent((BaseIC2Machine)te));
                te.invalidate();
                return true;
            }
        }
        else if(((entityPlayer.getCurrentEquippedItem() != null)&&(entityPlayer.getCurrentEquippedItem().itemID != ic2.api.item.Items.getItem("ecMeter").itemID))|(entityPlayer.getCurrentEquippedItem() == null))
        {
        	if ((new GuiHandler()).getServerGuiElement(0, entityPlayer, world, x, y, z) != null){
        		entityPlayer.openGui(FrogCraft.mod_FrogCraft.instance, 0, world, x, y, z);
        	    return true;
        	}
        }
        return false;
    }
    
    public int damageDropped(int par1){return par1; } 

    @Override
    public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
    	SidedIC2Machine te=(SidedIC2Machine)world.getBlockTileEntity(x, y, z);
    	
        if (te instanceof TileEntityACWindMillTop){
        	if (((TileEntityACWindMillTop)te).settled)
        		dropBlockAsItem_do(world,x,y,z,new ItemStack(mod_FrogCraft.Fan,1));
        }
    	
    	dropItems(world, x, y, z);
    	super.breakBlock(world, x, y, z, par5, par6);
    }

    private void dropItems(World world, int x, int y, int z){
            Random rand = new Random();

            TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
            if (!(tileEntity instanceof IInventory)) {
                    return;
            }
            IInventory inventory = (IInventory) tileEntity;

            for (int i = 0; i < inventory.getSizeInventory(); i++) {
                    ItemStack item = inventory.getStackInSlot(i);

                    if (item != null && item.stackSize > 0) {
                            float rx = rand.nextFloat() * 0.8F + 0.1F;
                            float ry = rand.nextFloat() * 0.8F + 0.1F;
                            float rz = rand.nextFloat() * 0.8F + 0.1F;

                            EntityItem entityItem = new EntityItem(world,
                                            x + rx, y + ry, z + rz,
                                            new ItemStack(item.itemID, item.stackSize, item.getItemDamage()));

                            if (item.hasTagCompound()) {
                                    entityItem.getEntityItem().setTagCompound((NBTTagCompound) item.getTagCompound().copy());
                            }

                            float factor = 0.05F;
                            entityItem.motionX = rand.nextGaussian() * factor;
                            entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
                            entityItem.motionZ = rand.nextGaussian() * factor;
                            world.spawnEntityInWorld(entityItem);
                            item.stackSize = 0;
                    }
            }
    }
    

	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int par1, CreativeTabs tab, List subItems) {
		for (int ix = 0; ix < ItemBlockMachines2.subNames.length; ix++) {
			subItems.add(new ItemStack(this, 1, ix));
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world) {return null;}
	
	
    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving player, ItemStack stack)
    {
        super.onBlockPlacedBy(world, x, y, z, player, stack);
        int heading = MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
       
        if (!(world.getBlockTileEntity(x, y, z) instanceof SidedIC2Machine))
        	return;
        
        SidedIC2Machine te=(SidedIC2Machine)world.getBlockTileEntity(x, y, z);

        switch (heading)
        {
        case 0:
            te.setFacing((short)2);
            break;
        case 1:
            te.setFacing((short)5);
            break;
        case 2:
            te.setFacing((short)3);
            break;
        case 3:
            te.setFacing((short)4);
            break;
        }
    }
	
	
    @SideOnly(Side.CLIENT)
    @Override
    public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int blockSide)
    {
        int blockMeta = world.getBlockMetadata(x, y, z);
        TileEntity te=world.getBlockTileEntity(x, y, z);
        
        if (!(te instanceof SidedIC2Machine))
        	return iconBuffer[blockMeta][mod_FrogCraft.sideAndFacingToSpriteOffset[blockSide][3]];
        
        return iconBuffer[blockMeta][mod_FrogCraft.sideAndFacingToSpriteOffset[blockSide][((SidedIC2Machine)te).facing]];
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public Icon getIcon(int blockSide, int blockMeta)
    {
        return iconBuffer[blockMeta][mod_FrogCraft.sideAndFacingToSpriteOffset[blockSide][3]];
    }    
    
  //This will tell minecraft not to render any side of our cube.
    public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
       return true;
    }

    //And this tell it that you can see through this block, and neighbor blocks should be rendered.
    public boolean isOpaqueCube()
    {
       return false;
    }
}

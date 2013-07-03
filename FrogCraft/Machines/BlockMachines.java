package FrogCraft.Machines;

import ic2.api.energy.event.EnergyTileUnloadEvent;

import java.util.List;
import java.util.Random;

import FrogCraft.*;
import FrogCraft.Common.BaseIC2Machine;
import FrogCraft.Common.SidedIC2Machine;
import FrogCraft.Machines.IndustrialDevices.*;


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
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class BlockMachines extends BlockContainer {

	private Icon[][] iconBuffer;

	
	//Get TileEntities
    @Override
    public TileEntity createTileEntity(World world, int meta)
    {
    	switch (meta){
    		case 0:
    			return new TileEntityPneumaticCompressor();
    		case 1:
    			return new TileEntityAirPump();
    		case 2:
    			return new TileEntityIndustrialCompressor();
    		case 3:
    			return new TileEntityIndustrialMacerator();
    		case 4:
    			return new TileEntityIndustrialExtractor();
    		case 5:
    			return new TileEntityIndustrialFurnance();
    		case 6:
    			return new TileEntityHSU();
    		case 7:
    			return new TileEntityUHSU();
    		case 8:
    			return new TileEntityEVTransformer();
    		case 9:
    			return new TileEntityLiquifier();
    		case 10:
    			return new TileEntityLiquidInjector();
    		case 11:
    			return new TileEntityThermalCracker();
    		case 12:
    			return new TileEntityAdvanceChemicalReactor();
    		default:
    				return null;
    	}
    }

    
    //Register Icons
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister r)    {
    	iconBuffer = new Icon[ItemBlockMachines.subNames.length][12];

    	//PneumaticCompressor
    	iconBuffer[0][0]=r.registerIcon("FrogCraft:IndustrialDevice_Side");
    	iconBuffer[0][1]=r.registerIcon("FrogCraft:PneumaticCompressor_Top");
    	iconBuffer[0][2]=r.registerIcon("FrogCraft:PneumaticCompressor_Side2");
    	iconBuffer[0][3]=r.registerIcon("FrogCraft:PneumaticCompressor_Side2");    	
    	iconBuffer[0][4]=r.registerIcon("FrogCraft:PneumaticCompressor_Side");
    	iconBuffer[0][5]=r.registerIcon("FrogCraft:PneumaticCompressor_Side");    
    	
    	//AirPump
    	iconBuffer[1][0]=r.registerIcon("FrogCraft:IndustrialDevice_Side");
    	iconBuffer[1][1]=r.registerIcon("FrogCraft:AirPump_Top");
    	iconBuffer[1][2]=r.registerIcon("FrogCraft:AirPump_Back");
    	iconBuffer[1][3]=r.registerIcon("FrogCraft:AirPump_Front");    	
    	iconBuffer[1][4]=r.registerIcon("FrogCraft:AirPump_Side");
    	iconBuffer[1][5]=r.registerIcon("FrogCraft:AirPump_Side");    
    	
    	//IndustrialCompressor
    	iconBuffer[2][0]=r.registerIcon("FrogCraft:IndustrialDevice_Side");
    	iconBuffer[2][1]=r.registerIcon("FrogCraft:IndustrialDevice_Top");
    	iconBuffer[2][2]=r.registerIcon("FrogCraft:IndustrialDevice_Side");
    	iconBuffer[2][3]=r.registerIcon("FrogCraft:IndustrialCompressor_Front");    	
    	iconBuffer[2][4]=r.registerIcon("FrogCraft:IndustrialDevice_Side");
    	iconBuffer[2][5]=r.registerIcon("FrogCraft:IndustrialDevice_Side");    
     	iconBuffer[2][6]=r.registerIcon("FrogCraft:IndustrialDevice_Side");
    	iconBuffer[2][7]=r.registerIcon("FrogCraft:IndustrialDevice_Top");
    	iconBuffer[2][8]=r.registerIcon("FrogCraft:IndustrialDevice_Side");
    	iconBuffer[2][9]=r.registerIcon("FrogCraft:IndustrialCompressor_Front2");    	
    	iconBuffer[2][10]=r.registerIcon("FrogCraft:IndustrialDevice_Side");
    	iconBuffer[2][11]=r.registerIcon("FrogCraft:IndustrialDevice_Side");   	
    	
    	//IndustrialMacerator
    	iconBuffer[3][0]=r.registerIcon("FrogCraft:IndustrialDevice_Side");
    	iconBuffer[3][1]=r.registerIcon("FrogCraft:IndustrialDevice_Top");
    	iconBuffer[3][2]=r.registerIcon("FrogCraft:IndustrialDevice_Side");
    	iconBuffer[3][3]=r.registerIcon("FrogCraft:IndustrialMacerator_Front");    	
    	iconBuffer[3][4]=r.registerIcon("FrogCraft:IndustrialDevice_Side");
    	iconBuffer[3][5]=r.registerIcon("FrogCraft:IndustrialDevice_Side");    
    	iconBuffer[3][6]=r.registerIcon("FrogCraft:IndustrialDevice_Side");
    	iconBuffer[3][7]=r.registerIcon("FrogCraft:IndustrialMacerator_Top");
    	iconBuffer[3][8]=r.registerIcon("FrogCraft:IndustrialDevice_Side");
    	iconBuffer[3][9]=r.registerIcon("FrogCraft:IndustrialMacerator_Front");    	
    	iconBuffer[3][10]=r.registerIcon("FrogCraft:IndustrialDevice_Side");
    	iconBuffer[3][11]=r.registerIcon("FrogCraft:IndustrialDevice_Side");     	
    	
    	//IndustrialExtractor
    	iconBuffer[4][0]=r.registerIcon("FrogCraft:IndustrialDevice_Side");
    	iconBuffer[4][1]=r.registerIcon("FrogCraft:IndustrialDevice_Top");
    	iconBuffer[4][2]=r.registerIcon("FrogCraft:IndustrialDevice_Side");
    	iconBuffer[4][3]=r.registerIcon("FrogCraft:IndustrialExtractor_Front");    	
    	iconBuffer[4][4]=r.registerIcon("FrogCraft:IndustrialDevice_Side");
    	iconBuffer[4][5]=r.registerIcon("FrogCraft:IndustrialDevice_Side"); 
    	iconBuffer[4][6]=r.registerIcon("FrogCraft:IndustrialDevice_Side");
    	iconBuffer[4][7]=r.registerIcon("FrogCraft:IndustrialDevice_Top");
    	iconBuffer[4][8]=r.registerIcon("FrogCraft:IndustrialDevice_Side");
    	iconBuffer[4][9]=r.registerIcon("FrogCraft:IndustrialExtractor_Front2");    	
    	iconBuffer[4][10]=r.registerIcon("FrogCraft:IndustrialDevice_Side");
    	iconBuffer[4][11]=r.registerIcon("FrogCraft:IndustrialDevice_Side");    	
    	
    	//IndustrialFurnance
    	iconBuffer[5][0]=r.registerIcon("FrogCraft:IndustrialDevice_Side");
    	iconBuffer[5][1]=r.registerIcon("FrogCraft:IndustrialDevice_Top");
    	iconBuffer[5][2]=r.registerIcon("FrogCraft:IndustrialDevice_Side");
    	iconBuffer[5][3]=r.registerIcon("FrogCraft:IndustrialFurnance_Front");    	
    	iconBuffer[5][4]=r.registerIcon("FrogCraft:IndustrialDevice_Side");
    	iconBuffer[5][5]=r.registerIcon("FrogCraft:IndustrialDevice_Side");   
    	iconBuffer[5][6]=r.registerIcon("FrogCraft:IndustrialDevice_Side");
    	iconBuffer[5][7]=r.registerIcon("FrogCraft:IndustrialDevice_Top");
    	iconBuffer[5][8]=r.registerIcon("FrogCraft:IndustrialDevice_Side");
    	iconBuffer[5][9]=r.registerIcon("FrogCraft:IndustrialFurnance_Front2");    	
    	iconBuffer[5][10]=r.registerIcon("FrogCraft:IndustrialDevice_Side");
    	iconBuffer[5][11]=r.registerIcon("FrogCraft:IndustrialDevice_Side");    	
  	
    	//HSU
    	iconBuffer[6][0]=r.registerIcon("FrogCraft:HSU_Side");
    	iconBuffer[6][1]=r.registerIcon("FrogCraft:HSU_Side");
    	iconBuffer[6][2]=r.registerIcon("FrogCraft:HSU_Side");
    	iconBuffer[6][3]=r.registerIcon("FrogCraft:HSU_Front");    	
    	iconBuffer[6][4]=r.registerIcon("FrogCraft:HSU_Side");
    	iconBuffer[6][5]=r.registerIcon("FrogCraft:HSU_Side");  
    	
    	//UHSU
    	iconBuffer[7][0]=r.registerIcon("FrogCraft:UHSU_Side");
    	iconBuffer[7][1]=r.registerIcon("FrogCraft:UHSU_Side");
    	iconBuffer[7][2]=r.registerIcon("FrogCraft:UHSU_Side");
    	iconBuffer[7][3]=r.registerIcon("FrogCraft:UHSU_Front");    	
    	iconBuffer[7][4]=r.registerIcon("FrogCraft:UHSU_Side");
    	iconBuffer[7][5]=r.registerIcon("FrogCraft:UHSU_Side");      	
    	
    	//EVT
    	iconBuffer[8][0]=r.registerIcon("FrogCraft:EV_Transformer_Side");
    	iconBuffer[8][1]=r.registerIcon("FrogCraft:EV_Transformer_Side");
    	iconBuffer[8][2]=r.registerIcon("FrogCraft:EV_Transformer_Side");
    	iconBuffer[8][3]=r.registerIcon("FrogCraft:EV_Transformer_Front");    	
    	iconBuffer[8][4]=r.registerIcon("FrogCraft:EV_Transformer_Side");
    	iconBuffer[8][5]=r.registerIcon("FrogCraft:EV_Transformer_Side");    
    	
    	//Liquifier
    	iconBuffer[9][0]=r.registerIcon("FrogCraft:IndustrialDevice_Side");
    	iconBuffer[9][1]=r.registerIcon("FrogCraft:IndustrialDevice_Side");
    	iconBuffer[9][2]=r.registerIcon("FrogCraft:Liquefier");
    	iconBuffer[9][3]=r.registerIcon("FrogCraft:Liquefier");    	
    	iconBuffer[9][4]=r.registerIcon("FrogCraft:Liquefier");
    	iconBuffer[9][5]=r.registerIcon("FrogCraft:Liquefier");   
    	iconBuffer[9][6]=r.registerIcon("FrogCraft:IndustrialDevice_Side");
    	iconBuffer[9][7]=r.registerIcon("FrogCraft:IndustrialDevice_Side");
    	iconBuffer[9][8]=r.registerIcon("FrogCraft:Liquefier_Active");
    	iconBuffer[9][9]=r.registerIcon("FrogCraft:Liquefier_Active");    	
    	iconBuffer[9][10]=r.registerIcon("FrogCraft:Liquefier_Active");
    	iconBuffer[9][11]=r.registerIcon("FrogCraft:Liquefier_Active");  
    	
    	//LiquidInjector
    	iconBuffer[10][0]=r.registerIcon("FrogCraft:IndustrialDevice_Side");
    	iconBuffer[10][1]=r.registerIcon("FrogCraft:CondenseTowerCylinder_Top");
    	iconBuffer[10][2]=r.registerIcon("FrogCraft:CondenseTower_Back");
    	iconBuffer[10][3]=r.registerIcon("FrogCraft:CondenseTower_Front");    	
    	iconBuffer[10][4]=r.registerIcon("FrogCraft:CondenseTower_Side");
    	iconBuffer[10][5]=r.registerIcon("FrogCraft:CondenseTower_Side");  
    	
    	//ThermalCracker
    	iconBuffer[11][0]=r.registerIcon("FrogCraft:IndustrialDevice_Side");
    	iconBuffer[11][1]=r.registerIcon("FrogCraft:IndustrialDevice_Side");
    	iconBuffer[11][2]=r.registerIcon("FrogCraft:ThermalCracker_Back");
    	iconBuffer[11][3]=r.registerIcon("FrogCraft:ThermalCracker_Front");    	
    	iconBuffer[11][4]=r.registerIcon("FrogCraft:ThermalCracker_Side");
    	iconBuffer[11][5]=r.registerIcon("FrogCraft:ThermalCracker_Side");   
    	iconBuffer[11][6]=r.registerIcon("FrogCraft:IndustrialDevice_Side");
    	iconBuffer[11][7]=r.registerIcon("FrogCraft:IndustrialDevice_Side");
    	iconBuffer[11][8]=r.registerIcon("FrogCraft:ThermalCracker_Back");
    	iconBuffer[11][9]=r.registerIcon("FrogCraft:ThermalCracker_Front_Active");    	
    	iconBuffer[11][10]=r.registerIcon("FrogCraft:ThermalCracker_Side");
    	iconBuffer[11][11]=r.registerIcon("FrogCraft:ThermalCracker_Side"); 
    	
    	//Advance Chemical Reactor
    	iconBuffer[12][0]=r.registerIcon("FrogCraft:IndustrialDevice_Side");
    	iconBuffer[12][1]=r.registerIcon("FrogCraft:IndustrialDevice_Side");
    	iconBuffer[12][2]=r.registerIcon("FrogCraft:IndustrialDevice_Side");
    	iconBuffer[12][3]=r.registerIcon("FrogCraft:AdvanceChemicalReactor_Front");    	
    	iconBuffer[12][4]=r.registerIcon("FrogCraft:IndustrialDevice_Side");
    	iconBuffer[12][5]=r.registerIcon("FrogCraft:IndustrialDevice_Side");     	
    	iconBuffer[12][6]=r.registerIcon("FrogCraft:IndustrialDevice_Side");
    	iconBuffer[12][7]=r.registerIcon("FrogCraft:IndustrialDevice_Side");
    	iconBuffer[12][8]=r.registerIcon("FrogCraft:IndustrialDevice_Side");
    	iconBuffer[12][9]=r.registerIcon("FrogCraft:AdvanceChemicalReactor_Front_Active");    	
    	iconBuffer[12][10]=r.registerIcon("FrogCraft:IndustrialDevice_Side");
    	iconBuffer[12][11]=r.registerIcon("FrogCraft:IndustrialDevice_Side");     	
    }
    
    
    
    //Visualize effects
    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(World world, int x, int y, int z, Random var5){

    	world.markBlockForRenderUpdate(x,  y,  z);
        
    	TileEntity te=world.getBlockTileEntity(x, y, z);
    	
        if  (te instanceof TileEntityIndustrialDevice|te instanceof TileEntityThermalCracker)
        	if (((SidedIC2Machine)te).isWorking()){
        		float var7 = (float)x + 1.0F;
        		float var8 = (float)y + 1.0F;
        		float var9 = (float)z + 1.0F;

        		for (int var10 = 0; var10 < 4; ++var10){
        			float var11 = -0.2F - var5.nextFloat() * 0.6F;
        			float var12 = -0.1F + var5.nextFloat() * 0.2F;
        			float var13 = -0.2F - var5.nextFloat() * 0.6F;
        			world.spawnParticle("smoke", (double)(var7 + var11), (double)(var8 + var12), (double)(var9 + var13), 0.0D, 0.0D, 0.0D);
        		}
        	}	
    }
    
    
    
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////    
    /////////////////////////Other Procedures///////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    //Main Procedure
    public BlockMachines(int id) {
		super(id, Material.rock);
        setHardness(2.0F);
        setResistance(5.0F);
        setUnlocalizedName("Machines");
        setCreativeTab(mod_FrogCraft.tabFrogCraft);
	}
	
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int par6, float par7, float par8, float par9)
    {
    	
    	
        if (entityPlayer.isSneaking())
        {
            return false;   
        }
        
        if (entityPlayer.getCurrentEquippedItem() != null
        && (entityPlayer.getCurrentEquippedItem().getItemName().toLowerCase().contains("wrench")==true))
        {            
        	TileEntity team = world.getBlockTileEntity(x, y, z);
            if (team != null)
            {
                //ic2.api.energy.EnergyNet.getForWorld(world).removeTileEntity(team);
                //MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent((BaseIC2Machine)team));
                team.invalidate();
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
		for (int ix = 0; ix < ItemBlockMachines.subNames.length; ix++) {
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
        SidedIC2Machine te=(SidedIC2Machine)world.getBlockTileEntity(x, y, z);
        
        int ico=mod_FrogCraft.sideAndFacingToSpriteOffset[blockSide][te.facing];
        
               
        if (te instanceof TileEntityIndustrialDevice|
        	te instanceof TileEntityThermalCracker|
        	te instanceof TileEntityAdvanceChemicalReactor|
        	te instanceof TileEntityLiquifier)
        	
        	if (te.isWorking())ico+=6;
        
        
        return iconBuffer[blockMeta][ico];
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public Icon getIcon(int blockSide, int blockMeta)
    {
        return iconBuffer[blockMeta][mod_FrogCraft.sideAndFacingToSpriteOffset[blockSide][3]];
    }    
    
}

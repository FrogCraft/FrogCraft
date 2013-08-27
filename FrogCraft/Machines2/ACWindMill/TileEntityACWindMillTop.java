package FrogCraft.Machines2.ACWindMill;

import java.util.Random;

import FrogCraft.Common.SidedIC2Machine;
import FrogCraft.api.fcItems;

import ic2.api.network.NetworkHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.Direction;
import net.minecraftforge.common.ForgeDirection;


public class TileEntityACWindMillTop extends SidedIC2Machine{		
	public boolean settled;
	public int randAngle=(new Random()).nextInt(180);
	
    @Override
    public void updateEntity(){  	
        super.updateEntity();
        
        if (worldObj.isRemote)
            return;
        
        setWorking(settled);
        
        if(settled){
        	TileEntityACWindMillBase te=findBase();
        	int gen=13;        	
        	for (int x=-3;x<4;x++){
            	for (int y=-3;y<4;y++){
                	for (int z=-3;z<4;z++){
                		int id=worldObj.getBlockId(x+xCoord, y+yCoord, z+zCoord);
                		if(id!=0&id!=fcItems.ACWindMillCylinder.blockID)
                			gen-=5;
                	}
            	}
        	}
        	
        	
        	if (te != null && yCoord-te.yCoord>9){
        		gen+=0.1*(yCoord-te.yCoord-10);
        	
        		if(gen<0)
        			gen=0;
        		
        		te.energy+=gen;
        		if (te.energy>te.maxEnergy)
        			te.energy=te.maxEnergy;
        	}
        }
    }
    
    
    TileEntityACWindMillBase findBase(){
    	for (int y=yCoord-1;y>0;y--){
    		if (worldObj.getBlockId(xCoord, y, zCoord)!=fcItems.ACWindMillCylinder.blockID){
    			if (worldObj.getBlockTileEntity(xCoord, y, zCoord) instanceof TileEntityACWindMillBase){
    				return (TileEntityACWindMillBase) worldObj.getBlockTileEntity(xCoord, y, zCoord) ;
    			}
    			else{
    				return null;
    			}
    		}
    	}
    	return null;
    }
    
    @Override
    public void setFacing(short var1)
    {
    	super.setFacing(var1);
    }
    
    
    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
    	super.readFromNBT(tagCompound);
    	settled=(tagCompound.getBoolean("settled"));
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
    	super.writeToNBT(tagCompound);  
    	tagCompound.setBoolean("settled", settled);
    }
}

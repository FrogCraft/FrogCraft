package FrogCraft.Common;

import net.minecraft.item.ItemStack;

import ic2.api.tile.IWrenchable;
import ic2.api.network.INetworkDataProvider;
import ic2.api.network.INetworkTileEntityEventListener;
import ic2.api.network.NetworkHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import java.util.ArrayList;
import java.util.List;

import FrogCraft.Common.BaseIC2Machine;

public class SidedIC2Machine extends TileEntity implements IWrenchable, INetworkDataProvider,INetworkTileEntityEventListener {

	public short facing = 5;
	private boolean isWorking;
	public static List networkedFields;
	protected boolean created = false;

	//Class Declaration
	public SidedIC2Machine(){
		super();
		
    	if (networkedFields == null)
    	{
            networkedFields = new ArrayList();
            networkedFields.add("facing");
            networkedFields.add("isWorking");
    	}
	}
	
	public void setWorking(boolean value){
		isWorking=value;
		NetworkHelper.updateTileEntityField(this, "isWorking");
	}
	
	public boolean isWorking(){
		return isWorking;
	}
	
    @Override
    public void updateEntity()
    {
    	
    	if (!this.created)
        {
            this.created = true;
            //NetworkHelper.requestInitialData(this);
            NetworkHelper.announceBlockUpdate(worldObj, xCoord, yCoord, zCoord);
            
        }
    	 super.updateEntity();    	 
     	//if (worldObj!=null)
    		//worldObj.markBlockForRenderUpdate(xCoord,  yCoord,  zCoord);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
    	super.readFromNBT(tagCompound);
    	facing = tagCompound.getShort("facing");    
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
    	super.writeToNBT(tagCompound);
    	tagCompound.setShort("facing", facing);
    }


    @Override
    public short getFacing(){return facing;}
    
    
    @Override
    public boolean wrenchCanSetFacing(EntityPlayer var1, int facingToSet)
    {
    	if (facingToSet < 2     	|| facingToSet == facing) // dismantle upon clicking the face
    		return false;
        return true;
    }

   
    public void beforeSetFacing(short newFacing,short oldFacing){}
    public void afterSetFacing(short facing){}
    
    @Override
    public void setFacing(short var1)
    {
    	beforeSetFacing(var1,facing);
        facing = var1;
        NetworkHelper.updateTileEntityField(this, "facing");
        NetworkHelper.announceBlockUpdate(worldObj, xCoord, yCoord, zCoord);
        afterSetFacing(var1);
    }

    @Override
    public boolean wrenchCanRemove(EntityPlayer var1){return true;}

    @Override
    public float getWrenchDropRate(){return 1.0F;}

    @Override
    public ItemStack getWrenchDrop(EntityPlayer entityPlayer)
    {
        return new ItemStack(this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord), 1, this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord));
    }
    
	@Override
	public List<String> getNetworkedFields(){return networkedFields;}

	@Override
	public void onNetworkEvent(int event){}   
	
	
	
	
	
}


//    	ItemStack a=new ItemStack(ic2.api.item.Items.getItem("industrialTnt").itemID,64,0);
//if (!world.isRemote)entityPlayer.sendChatToPlayer(String.valueOf(gregtechmod.api.util.GT_Recipe.findEqualImplosionRecipeIndex(gregtechmod.api.GregTech_API.getGregTechItem(1,4,36),a)));

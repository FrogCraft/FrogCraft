package FrogCraft.Machines2.ACWindMill;

import FrogCraft.Common.BaseIC2Generator;
import ic2.api.Direction;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;

public class TileEntityACWindMillBase extends BaseIC2Generator implements ic2.api.energy.tile.IEnergySource{
	public TileEntityACWindMillBase(){
		super(256);
	}
	
    @Override
    public void updateEntity(){  	
        super.updateEntity();
        
        if (worldObj.isRemote)
            return;   
        
        out(32);
    }
 }

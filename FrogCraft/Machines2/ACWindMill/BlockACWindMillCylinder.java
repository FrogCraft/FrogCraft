package FrogCraft.Machines2.ACWindMill;

import java.util.List;

import FrogCraft.mod_FrogCraft;
import net.minecraft.block.BlockFence;
import net.minecraft.block.StepSound;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockACWindMillCylinder extends BlockFence{	
	public BlockACWindMillCylinder(int id){
		super(id, "FrogCraft:ACWindMill_Cylinder", Material.iron);
		setHardness(2.0F);
		setResistance(5.0F);
		setStepSound(new StepSound("stone", 1.0F, 1.5F));
		setUnlocalizedName("ACWindMill_Cylinder");
		setCreativeTab(mod_FrogCraft.tabFrogCraft);
	}
	
	@Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
		if (world.getBlockId(x, y-1, z)==blockID|world.getBlockId(x, y+1, z)==blockID)
			return true;
		if (world.getBlockTileEntity(x, y-1, z) instanceof TileEntityACWindMillBase)
			return true;
		return false;
    }
	
	@Override
    public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
    {
        AxisAlignedBB axisalignedbb1 = this.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);

        if (axisalignedbb1 != null && par5AxisAlignedBB.intersectsWith(axisalignedbb1))
        {
            par6List.add(axisalignedbb1);
        }
        this.setBlockBounds(0.375F, 0.0F, 0.375F, 0.625F, 1.0F, 0.625F);
    }
	
    public boolean canConnectFenceTo(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
    	return false;
    }

    
    public int getRenderType()
    {
        return 0;
    }
}

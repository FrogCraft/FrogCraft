package FrogCraft.Ore;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import FrogCraft.mod_FrogCraft;

public class BlockOre extends Block{
	
	private Icon[] iconBuffer;
	
    public BlockOre(int id) {
		super(id, Material.rock);
        setHardness(2.0F);
        setResistance(5.0F);
        //setStepSound(Block.dirt.stepSound);
        setUnlocalizedName("Ore");
        setCreativeTab(mod_FrogCraft.tabFrogCraft);
	}
    
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int par1, CreativeTabs tab, List subItems) {
		for (int ix = 0; ix < ItemBlockOre.subNames.length; ix++) {
			subItems.add(new ItemStack(this, 1, ix));
		}
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int blockSide)
    {
    	return iconBuffer[world.getBlockMetadata(x, y, z)];
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public Icon getIcon(int blockSide, int blockMeta)
    {
    	return iconBuffer[blockMeta];
    }
    
    //Regist Icons
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister r)    {
    	iconBuffer=new Icon[ItemBlockOre.subNames.length];
    	for (int i=0;i<ItemBlockOre.subNames.length;i++)
    		iconBuffer[i]=r.registerIcon("FrogCraft:Ores/"+ItemBlockOre.subNames[i]);
    }
    
    public int damageDropped(int par1){return par1; } 
}

package FrogCraft.Machines2;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;

import FrogCraft.mod_FrogCraft;
import FrogCraft.Common.GuiLiquids;

public class GuiLiquidOutput extends GuiLiquids {
	protected TileEntityLiquidOutput te;

	
    public GuiLiquidOutput (InventoryPlayer inventoryPlayer,TileEntityLiquidOutput tileEntity) {
    	super(new ContainerLiquidOutput(inventoryPlayer,tileEntity));
    	te=tileEntity;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
    	fontRenderer.drawString(StatCollector.translateToLocal("tile.Machines2."+ItemBlockMachines2.subNames[te.getBlockMetadata()]+".name"), 8, 6, 4210752);
    	
    	this.drawLiquidBar(143, 23, 16, 47, te.idOut, te.damageOut, te.amountOutP/10);

    	mc.renderEngine.bindTexture("/mods/FrogCraft/textures/gui/Gui_LiquidInjector.png");     
    	this.drawTexturedModalRect(143, 23, 176, 0, 16, 47);
      	

    	int x=par1-((width-xSize)/2),y=par2-((height-ySize)/2);
    	if (x>=142&y>=22&x<=160&y<=70){
    		List<String> l = new ArrayList<String>();
    		if (te.idOut>0){
    			l.add(new ItemStack(te.idOut,1,te.damageOut).getDisplayName());
    			l.add(String.valueOf(te.amountOutP/10)+"."+String.valueOf(te.amountOutP-(te.amountOutP/10)*10)+"%");
    			l.add("About "+String.valueOf(te.amountOutP*te.maxCapacity/1000)+"mB");
    			func_102021_a(l, x, y);   
            }             
    	}    	

    }
        
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2,int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture("/mods/FrogCraft/textures/gui/Gui_LiquidOutput.png");
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}
}
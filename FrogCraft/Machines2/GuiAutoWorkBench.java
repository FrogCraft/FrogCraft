package FrogCraft.Machines2;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;

public class GuiAutoWorkBench extends GuiContainer {
	protected TileEntityAutoWorkBench te;

	
    public GuiAutoWorkBench (InventoryPlayer inventoryPlayer,TileEntityAutoWorkBench tileEntity) {
    	super(new ContainerAutoWorkBench(inventoryPlayer,tileEntity));
    	te=tileEntity;	
    	this.xSize=252;
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
    	fontRenderer.drawString(StatCollector.translateToLocal("tile.Machines2."+ItemBlockMachines2.subNames[te.getBlockMetadata()]+".name"), 8, 6, 4210752);
    }
       
    
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2,int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		mc.renderEngine.func_110577_a(new ResourceLocation("frogcraft","/textures/gui/Gui_AutoWorkBench.png"));
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}
}
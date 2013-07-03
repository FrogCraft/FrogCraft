package FrogCraft.Machines;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import FrogCraft.mod_FrogCraft;

public class GuiAdvanceChemicalReactor extends GuiContainer {
	
	protected TileEntityAdvanceChemicalReactor tileentity;

    public GuiAdvanceChemicalReactor (InventoryPlayer inventoryPlayer,TileEntityAdvanceChemicalReactor tileEntity) {
            super(new ContainerAdvanceChemicalReactor(inventoryPlayer,tileEntity));
            tileentity=tileEntity;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int param1, int param2) {
            //draw text and stuff here
            //the parameters for drawString are: string, x, y, color
            fontRenderer.drawString(ItemBlockMachines.Machines_Names[tileentity.getBlockMetadata()], 8, 6, 4210752);
            //fontRenderer.drawString(String.valueOf(tileentity.energy), 8, 6, 4210752);
            //draws "Inventory" or your regional equivalent
            //fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2,
                    int par3) {
            //draw your Gui here, only thing you need to change is the path
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.mc.renderEngine.bindTexture("/mods/FrogCraft/textures/gui/Gui_AdvanceChemicalReactor.png");
            int x = (width - xSize) / 2;
            int y = (height - ySize) / 2;
            this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
            
            
            if (tileentity.elevel>0)
            	this.drawTexturedModalRect(x + 148, y + 23 +14 - tileentity.elevel, 176, 17+14- tileentity.elevel, 10, tileentity.elevel);
            
            
            drawTexturedModalRect(x + 73, y + 40, 176, 0, 29, tileentity.progress*9/100);
    }

    
}

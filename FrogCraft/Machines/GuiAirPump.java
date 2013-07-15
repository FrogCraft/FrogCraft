package FrogCraft.Machines;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import FrogCraft.mod_FrogCraft;

public class GuiAirPump extends GuiContainer {
		
		protected TileEntityAirPump tileentity;
	
        public GuiAirPump (InventoryPlayer inventoryPlayer,TileEntityAirPump tileEntity) {
                super(new ContainerAirPump(inventoryPlayer,tileEntity));
                tileentity=tileEntity;
        }

        @Override
        protected void drawGuiContainerForegroundLayer(int param1, int param2) {
                //draw text and stuff here
                //the parameters for drawString are: string, x, y, color
                fontRenderer.drawString(StatCollector.translateToLocal("tile.Machines."+ItemBlockMachines.subNames[tileentity.getBlockMetadata()]+".name"), 8, 6, 4210752);
                fontRenderer.drawString("Gas amount: "+String.valueOf(tileentity.max_gasamount*tileentity.GasPercentage/10000), 8, 22, 4210752); 
                fontRenderer.drawString("Max amount: "+String.valueOf(tileentity.max_gasamount), 8, 32, 4210752); 
                fontRenderer.drawString("Percentage: "+String.valueOf(tileentity.GasPercentage/100)+"%", 8, 42, 4210752); 
                fontRenderer.drawString("Eu Stored: "+String.valueOf(tileentity.energy), 8, 52, 4210752); 
                //draws "Inventory" or your regional equivalent
                fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96, 4210752);
        }

        @Override
        protected void drawGuiContainerBackgroundLayer(float par1, int par2,
                        int par3) {
                //draw your Gui here, only thing you need to change is the path
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                this.mc.renderEngine.bindTexture("/mods/FrogCraft/textures/gui/GUI_AirPump.png");
                int x = (width - xSize) / 2;
                int y = (height - ySize) / 2;
                this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
                
                
                if (tileentity.energy>0)
                	this.drawTexturedModalRect(x + 118, y + 33 +14 - gete(), 188, 14- gete(), 9, gete());
                
                
                //if (tileentity.GasPercentage>0)
                	this.drawTexturedModalRect(x + 145, y + 18, 176, 41-getp(), 12,5+getp());
                
        }

        int gete(){
        	return 14*tileentity.energy/tileentity.maxEnergy;
        }
        
        int getp(){
        	return 40*tileentity.GasPercentage/10000;
        }
}
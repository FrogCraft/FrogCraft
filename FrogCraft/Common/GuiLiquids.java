package FrogCraft.Common;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.util.Icon;

@SideOnly(Side.CLIENT)
public abstract class GuiLiquids extends GuiContainer{
	public GuiLiquids(Container par1Container) {super(par1Container);}
	
	public static void drawLiquidBar(int x	 	  ,int y     ,int width    ,int height,
			     int liquidID,int Damage,int percentage){
		if (liquidID==0)
			return;
		RenderEngine renderEngine = FMLClientHandler.instance().getClient().renderEngine;
		Item item = Item.itemsList[liquidID];
		Icon icon =	item.getIconFromDamage(Damage);

		if (icon==null)
			return;
		
		if (item.getSpriteNumber() == 0)     
			renderEngine.bindTexture("/terrain.png");
		else    
			renderEngine.bindTexture("/gui/items.png");

		
		double u = icon.getInterpolatedU(3.0D);
		double u2 = icon.getInterpolatedU(13.0D);
		double v = icon.getInterpolatedV(1.0D);
		double v2 = icon.getInterpolatedV(15.0D);

		int z=height*percentage/100;

		GL11.glEnable(3553);
		GL11.glColor4d(1.0D, 1.0D, 1.0D, 1.0D);

		GL11.glBegin(7);
		GL11.glTexCoord2d(u, v);
		GL11.glVertex2i(x, y+height-z);

		GL11.glTexCoord2d(u, v2);
		GL11.glVertex2i(x, y + height);

		GL11.glTexCoord2d(u2, v2);
		GL11.glVertex2i(x + width, y + height);

		GL11.glTexCoord2d(u2, v);
		GL11.glVertex2i(x + width, y+height-z);
		GL11.glEnd();
	}
}

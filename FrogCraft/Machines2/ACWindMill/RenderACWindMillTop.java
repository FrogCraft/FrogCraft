package FrogCraft.Machines2.ACWindMill;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import FrogCraft.api.fcItems;


public class RenderACWindMillTop extends TileEntitySpecialRenderer{
	public TileEntityACWindMillTop te;
	
	public void renderTileEntityAt(TileEntity tileEntity, double d, double d1, double d2, float f) {
		te=(TileEntityACWindMillTop) tileEntity;
		GL11.glPushMatrix();
		//This will move our renderer so that it will be on proper place in the world
		GL11.glTranslatef((float)d, (float)d1, (float)d2);
		/*Note that true tile entity coordinates (tileEntity.xCoord, etc) do not match to render coordinates (d, etc) that are calculated as [true coordinates] - [player coordinates (camera coordinates)]*/
		renderBlockYour( tileEntity.worldObj, tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, fcItems.Machines2);
		GL11.glPopMatrix();
	}
	
	int getDirection(){
        switch (te.facing){
        case 2:
        	return 0;
        case 5:
        	return 1;
        case 3:
        	return 2;
        default:
        	return 3;
        }
	}
	
	public void renderBlockYour(World world, int i, int j, int k, Block block) {
		if (!te.isWorking())
			return;
		
		//Tessellator tessellator = Tessellator.instance;
		//make the block brightness dependent from surroundings lighting.
		//float f = block.getBlockBrightness(world, i, j, k);
		//int l = world.getLightBrightnessForSkyBlocks(i, j, k, 0);
		//int l1 = l % 65536;
		//int l2 = l / 65536;
		//tessellator.setColorOpaque_F(f, f, f);
		//OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)l1, (float)l2); 
  
		
		func_110628_a(new ResourceLocation("frogcraft","textures/blocks/ACWindMill_Fan.png"));
	    
		GL11.glPushMatrix();

		
		GL11.glTranslatef(0.5F, 0, 0.5F);
		GL11.glRotatef(getDirection() * (270F), 0F, 1F, 0F);
		GL11.glTranslatef(0, 0.5F, -0.6F);

		
		GL11.glRotatef(te.randAngle+(Minecraft.getMinecraft().getSystemTime()%1800000)/20, 0.0F, 0.0F, 1.0F);		
		draw();
		GL11.glRotatef(180, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(180, 0.0F, 0.0F, 1.0F);
		draw();
		
		
		GL11.glPopMatrix();
	}

	private void render_light(int direction, float center_x, float center_y) {
		int x = MathHelper.floor_double(te.xCoord);
		int y = MathHelper.floor_double(te.yCoord + center_y);
		int z = MathHelper.floor_double(te.zCoord);

		if (direction == 2) {
			x = MathHelper.floor_double(te.xCoord - center_x);
		}

		if (direction == 1) {
			z = MathHelper.floor_double(te.zCoord + center_x);
		}

		if (direction == 0) {
			x = MathHelper.floor_double(te.xCoord + center_x);
		}

		if (direction == 3) {
			z = MathHelper.floor_double(te.zCoord - center_x);
		}

		int light = te.worldObj.getLightBrightnessForSkyBlocks(
				x, y, z, 0);
		int var8 = light % 65536;
		int var9 = light / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit,
				var8, var9);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
	}
	    
	public static float half_thickness = 0.03125F; 
	private void draw() {
		float radius=10;
		float start_x = -radius / 2.0F; 
		float start_y = radius / 2.0F; 


		
		for (float i = 0; i < radius; i++) {
			for (float j = 0; j < radius; j++) {

				float left = start_x + i;  
				float right = left + 1;
				float top = start_y - j;
				float bottom = top - 1;

				//this.render_light(getDirection(), (right + left) / 2,(bottom + top) / 2);

				float texture_left = i / radius;
				float texture_right = (i + 1) / radius;
				float texture_top = j / radius;
				float texture_bottom = (j + 1) / radius;

				Tessellator tessellator = Tessellator.instance;
				tessellator.startDrawingQuads();

				tessellator.setNormal(0.0F, 0.0F, -1.0F);
				tessellator.addVertexWithUV(right, top, half_thickness,	texture_right, texture_top);
				tessellator.addVertexWithUV(left, top, half_thickness, texture_left, texture_top);
				tessellator.addVertexWithUV(left, bottom, half_thickness, texture_left, texture_bottom);
				tessellator.addVertexWithUV(right, bottom, half_thickness, texture_right, texture_bottom);


				tessellator.setNormal(0.0F, 0.0F, 1.0F);
				tessellator.addVertexWithUV(left, top, -half_thickness, 0, 0);
				tessellator.addVertexWithUV(right, top, -half_thickness, 0, 0);
				tessellator.addVertexWithUV(right, bottom, -half_thickness, 0,0);
				tessellator.addVertexWithUV(left, bottom, -half_thickness, 0, 0);


				tessellator.setNormal(0.0F, 1.0F, 0.0F);
				tessellator.addVertexWithUV(right, top, -half_thickness, 0, 0);
				tessellator.addVertexWithUV(left, top, -half_thickness, 0, 0);
				tessellator.addVertexWithUV(left, top, half_thickness, 0, 0);
				tessellator.addVertexWithUV(right, top, half_thickness, 0, 0);


				tessellator.setNormal(0.0F, -1.0F, 0.0F);
				tessellator.addVertexWithUV(right, bottom, half_thickness, 0, 0);
				tessellator.addVertexWithUV(left, bottom, half_thickness, 0, 0);
				tessellator.addVertexWithUV(left, bottom, -half_thickness, 0, 0);
				tessellator.addVertexWithUV(right, bottom, -half_thickness, 0, 0);


				tessellator.setNormal(-1.0F, 0.0F, 0.0F);
				tessellator.addVertexWithUV(left, top, half_thickness, 0, 0);
				tessellator.addVertexWithUV(left, top, -half_thickness, 0, 0);
				tessellator
						.addVertexWithUV(left, bottom, -half_thickness, 0, 0);
				tessellator.addVertexWithUV(left, bottom, half_thickness, 0, 0);


				tessellator.setNormal(1.0F, 0.0F, 0.0F);
				tessellator.addVertexWithUV(right, top, -half_thickness, 0, 0);
				tessellator.addVertexWithUV(right, top, half_thickness, 0, 0);
				tessellator.addVertexWithUV(right, bottom, half_thickness, 0, 0);
				tessellator.addVertexWithUV(right, bottom, -half_thickness, 0,0);

				tessellator.draw();
			}
		}
	}
}
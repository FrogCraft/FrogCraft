package FrogCraft.NEI;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

import FrogCraft.Common.GuiLiquids;
import FrogCraft.Common.FluidManager;
import FrogCraft.Machines.ItemBlockMachines;

import codechicken.nei.PositionedStack;
import codechicken.nei.forge.GuiContainerManager;
import codechicken.nei.recipe.TemplateRecipeHandler;
import static codechicken.core.gui.GuiDraw.*;

public class ThermalCrackerRecipeHandler extends TemplateRecipeHandler{
	public ThermalCrackerRecipeHandler(){
		codechicken.nei.api.API.registerRecipeHandler(this);
		codechicken.nei.api.API.registerUsageHandler(this);
	}
	
	public void loadTransferRects(){
		this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(40,18, 24,17), getRecipeId(), new Object[0]));
		
		//Add Machine Gui support
		ArrayList guis = new ArrayList();
		ArrayList transferRects2 = new ArrayList();
		guis.add(FrogCraft.Machines.GuiThermalCracker.class);
		transferRects2.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(40,18, 24,17), getRecipeId(), new Object[0]));
		TemplateRecipeHandler.RecipeTransferRectHandler.registerRectsToGuis(guis, transferRects2); 
	}
	
	//The string displayed on the top of the recipe window
	@Override
	public String getRecipeName() {
		return StatCollector.translateToLocal("tile.Machines."+ItemBlockMachines.subNames[11]+".name");
	}

	//Recipe window texture
	@Override
	public String getGuiTexture() {
		return "frogcraft:textures/gui/Gui_ThermalCracker.png";
	}
	
	@Override
	public int recipiesPerPage(){
		return 1;
	}
	
	//Return a custom ID for the Recipe GUI
	public String getRecipeId(){
		return "FrogCraft.TC";
	}
	
	@Override
	public void drawExtras(int recipe){
		drawProgressBar(40,18, 176, 80, 24,17,20,0);
		Cached_Recipe rec=(Cached_Recipe)arecipes.get(recipe);
		drawString(StatCollector.translateToLocal("nei.euTotal")+":"+String.valueOf(rec.eu*rec.tick),28,80, 4210752,false);
		drawString(StatCollector.translateToLocal("nei.euTick")+":"+String.valueOf(rec.eu),28,90, 4210752,false);	
		drawString(StatCollector.translateToLocal("nei.tick")+":"+String.valueOf(rec.tick),28,100, 4210752,false);
		if (rec.outID>0)
			drawString(StatCollector.translateToLocal("nei.outputLiquid")+":"+FluidManager.getFluidDisplayName(rec.outID),28,110, 4210752,false);
		
		
		GuiLiquids.drawLiquidBar(138, 12, 16, 47, rec.outID, 100);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		changeTexture(getGuiTexture());
		drawTexturedModalRect(138, 12, 176, 0, 16, 47);
		
		drawTexturedModalRect(76,46, 176, 52, 10, 14);	
		drawTexturedModalRect(20,39, 176, 66, 14, 13);
	}
	
	@Override
    public void loadCraftingRecipes(String outputId, Object... results)
    {
		if (outputId.equals(getRecipeId())) {
			for(int i=0;i<FrogCraft.Common.RecipeManager.thermalCrackerRecipes.size();i++)
				arecipes.add(new Cached_Recipe(i));
		}
		else 
			super.loadCraftingRecipes(outputId, results);
    }
	
	@Override
	public void loadCraftingRecipes(ItemStack result){
		for(int i=0;i<FrogCraft.Common.RecipeManager.thermalCrackerRecipes.size();i++){
			Cached_Recipe rec=new Cached_Recipe(i);
			if (rec.contains(rec.products, result))
				this.arecipes.add(rec);
		}
	}
	
	public void loadUsageRecipes(ItemStack ingredient){
		for(int i=0;i<FrogCraft.Common.RecipeManager.thermalCrackerRecipes.size();i++){
			Cached_Recipe rec=new Cached_Recipe(i);
			if (rec.contains(rec.resources, ingredient))
				this.arecipes.add(rec);
		}
	}
	
	
	//Custom class represents a cached recipe
	public class Cached_Recipe extends TemplateRecipeHandler.CachedRecipe{
		public ArrayList products= new ArrayList();
		public ArrayList resources= new ArrayList();

		public int eu,tick,outID;
		
		public Cached_Recipe(int i){			
			Object[] rec=FrogCraft.Common.RecipeManager.thermalCrackerRecipes.get(i);
			this.resources.add(new PositionedStack(((ItemStack)rec[0]).copy(), 19, 17));
			eu=(Integer) rec[3];
			tick=(Integer) rec[4];
			if(rec[1]!=null)
				this.products.add(new PositionedStack(((ItemStack)rec[1]).copy(), 70, 17));
			outID=((FluidStack)rec[2]).fluidID;
			
			if (outID>-1)
				this.products.add(new PositionedStack(FluidManager.getFilledContainers(outID), 108, 45));
			
		}
		
		public ArrayList getIngredients()
		{
			return resources;
		}

		public PositionedStack getResult()
		{
			return null;
		}
		
		public ArrayList getOtherStacks() {
			return this.products;
		}
	}
}

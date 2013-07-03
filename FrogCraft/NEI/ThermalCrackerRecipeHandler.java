package FrogCraft.NEI;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import FrogCraft.Common.GuiLiquids;
import FrogCraft.Common.LiquidIO;

import codechicken.nei.PositionedStack;
import codechicken.nei.forge.GuiContainerManager;
import codechicken.nei.recipe.TemplateRecipeHandler;

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
		return FrogCraft.Machines.ItemBlockMachines.Machines_Names[11];
	}

	//Recipe window texture
	@Override
	public String getGuiTexture() {
		return "/mods/FrogCraft/textures/gui/Gui_ThermalCracker.png";
	}
	
	@Override
	public int recipiesPerPage(){
		return 1;
	}
	
	//Return a custom ID for the Recipe GUI
	public String getRecipeId(){
		return "FrogCraft.TC";
	}
	
	public void drawExtras(GuiContainerManager gui, int recipe){
		Cached_Recipe rec=(Cached_Recipe)arecipes.get(recipe);
		gui.drawText(28,80,NEI_Config.euTotal+String.valueOf(rec.eu*rec.tick), 4210752,false);
		gui.drawText(28,90,NEI_Config.euTick+String.valueOf(rec.eu), 4210752,false);	
		gui.drawText(28,100,NEI_Config.tick+String.valueOf(rec.tick), 4210752,false);
		if (rec.outID>0)
			gui.drawText(28,110,NEI_Config.liquid+new ItemStack(rec.outID,1,rec.outDamage).getDisplayName(), 4210752,false);
		
		
		GuiLiquids.drawLiquidBar(138, 12, 16, 47, rec.outID, rec.outDamage, 100);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		gui.bindTexture(getGuiTexture());
		gui.drawTexturedModalRect(138, 12, 176, 0, 16, 47);
		drawProgressBar(gui,40,18, 176, 80, 24,17,20,0);
		gui.drawTexturedModalRect(76,46, 176, 52, 10, 14);	
		gui.drawTexturedModalRect(20,39, 176, 66, 14, 13);
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

		public int eu,tick,outID,outDamage,outAmount;
		
		public Cached_Recipe(int i){			
			int[] rec=FrogCraft.Common.RecipeManager.thermalCrackerRecipes.get(i);
			this.resources.add(new PositionedStack(new ItemStack(rec[0],rec[2],rec[1]), 19, 17));
			eu=rec[3];
			tick=rec[4];
			if(rec[5]>0)
				this.products.add(new PositionedStack(new ItemStack(rec[5],rec[7],rec[6]), 70, 17));
			outID=rec[8];
			outDamage=rec[9];
			outAmount=rec[10];
			
			if (outID>0){
				this.products.add(new PositionedStack(LiquidIO.getFilledContainers(outID, outDamage), 108, 45));
			}
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

package FrogCraft.NEI;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

import FrogCraft.Common.FluidManager;
import FrogCraft.Common.GuiLiquids;
import FrogCraft.Common.ItemManager;
import FrogCraft.Intergration.ic2;
import FrogCraft.Machines.ItemBlockMachines;
import FrogCraft.api.fcItems.cls;

import codechicken.nei.PositionedStack;
import codechicken.nei.forge.GuiContainerManager;
import codechicken.nei.recipe.TemplateRecipeHandler;
import static codechicken.core.gui.GuiDraw.*;


public class LiquifierRecipeHandler extends TemplateRecipeHandler{
	public LiquifierRecipeHandler(){
		codechicken.nei.api.API.registerRecipeHandler(this);
		codechicken.nei.api.API.registerUsageHandler(this);
	}
	
	@Override
	public void loadTransferRects(){
		this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(35, 23,24, 16), getRecipeId(), new Object[0]));
		
		//Add Machine Gui support
		ArrayList guis = new ArrayList();
		ArrayList transferRects2 = new ArrayList();
		guis.add(FrogCraft.Machines.GuiLiquifier.class);
		transferRects2.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(109, 30,127-114, 52-40), getRecipeId(), new Object[0]));
		TemplateRecipeHandler.RecipeTransferRectHandler.registerRectsToGuis(guis, transferRects2); 
	}
	
	//The string displayed on the top of the recipe window
	@Override
	public String getRecipeName() {
		return StatCollector.translateToLocal("tile.Machines."+ItemBlockMachines.subNames[9]+".name");
	}

	//Recipe window texture
	@Override
	public String getGuiTexture() {
		return "frogcraft:textures/gui/Gui_Liquifier.png";
	}
	
	@Override
	public int recipiesPerPage(){
		return 1;
	}
	
	//Return a custom ID for the Recipe GUI
	public String getRecipeId(){
		return "FrogCraft.L";
	}
	
	@Override
    public void drawBackground(int recipe)
    {
    	super.drawBackground(recipe);
    	Cached_Recipe rec=(Cached_Recipe)arecipes.get(recipe);
    	
    	GuiLiquids.drawLiquidBar(108, 45, 16, 16, FluidRegistry.getFluidID("liquifiedair"), 100);
    }
	
	@Override
	public void drawExtras(int recipe){
		Cached_Recipe rec=(Cached_Recipe)arecipes.get(recipe);
		drawString(StatCollector.translateToLocal("nei.euTotal")+": 3200",28,80, 4210752,false);
		drawString("Per 1000mB",28,90, 4210752,false);	
		drawString(StatCollector.translateToLocal("nei.euTick")+": 32",28,100, 4210752,false);

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		changeTexture(getGuiTexture());
		drawProgressBar(73, 45, 176, 70, 24, 16,30,0);
	}
	
	@Override
    public void loadCraftingRecipes(String outputId, Object... results)
    {
		if (outputId.equals(getRecipeId())) {
				arecipes.add(new Cached_Recipe());
		}
		else 
			super.loadCraftingRecipes(outputId, results);
    }
	
	@Override
	public void loadCraftingRecipes(ItemStack result){
		Cached_Recipe rec=new Cached_Recipe();
		if (rec.contains(rec.products, result))
			this.arecipes.add(rec);
	}
	
	@Override
	public void loadUsageRecipes(ItemStack ingredient){
		Cached_Recipe rec=new Cached_Recipe();
		if (rec.contains(rec.resources, ingredient))
			this.arecipes.add(rec);
	}
	
	
	//Custom class represents a cached recipe
	public class Cached_Recipe extends TemplateRecipeHandler.CachedRecipe{
		public ArrayList products= new ArrayList();
		public ArrayList resources= new ArrayList();
		
		public Cached_Recipe(){			
			this.resources.add(new PositionedStack(ic2.getItem("airCell"), 42, 10));
			this.products.add(new PositionedStack(ItemManager.getItem(cls.cell, "cell_LiquifiedAir"), 108, 45));
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

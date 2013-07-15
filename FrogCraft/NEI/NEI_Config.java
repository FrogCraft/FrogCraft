package FrogCraft.NEI;

import net.minecraftforge.common.Configuration;
import codechicken.nei.api.IConfigureNEI;

public class NEI_Config implements IConfigureNEI{
	@Override
	public void loadConfig() {
		new AdvanceChemicalReactorRecipeHandler();
		new ThermalCrackerRecipeHandler();
		new CondenseTowerRecipeHandler();
	}

	@Override
	public String getName() {
		return "FrogCraft NEI Plugin";
	}

	@Override
	public String getVersion() {
		return "0.99";
	}

}

package FrogCraft.Machines;

public class TileEntityUHSU extends TileEntityHSU{
	public TileEntityUHSU(){
		super(8192,1000000000);
		tier=5;
	}
	
	@Override
    public void outputEu(){
    	out(8192);
    }
}

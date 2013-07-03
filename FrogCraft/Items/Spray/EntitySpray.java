package FrogCraft.Items.Spray;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntitySpray extends Entity { 
	public static float[] GRIDS_HEIGHTS ;
	public static float[] GRIDS_WIDTHS ;

	public int hanging_direction;
	public int block_pos_x;
	public int block_pos_y;
	public int block_pos_z;
	public int title_id;

	public EntitySpray(World world) {
		super(world);
		this.setSize(1.0F, 1.0F);
	}

	public EntitySpray(World world, int x, int y, int z, int direction,
			int title_id) {
		this(world);

		this.block_pos_x = x;
		this.block_pos_y = y;
		this.block_pos_z = z;
		this.hanging_direction = direction;
		this.title_id = title_id;

		this.save_params();
		this.init_params();
	}

	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(18, 0);
		this.dataWatcher.addObject(19, 0);
		this.dataWatcher.addObject(20, 0);
		this.dataWatcher.addObject(21, 0);
		this.dataWatcher.addObject(22, 0);
	}

	private void save_params() {
		this.dataWatcher.updateObject(18, this.hanging_direction);
		this.dataWatcher.updateObject(19, this.block_pos_x);
		this.dataWatcher.updateObject(20, this.block_pos_y);
		this.dataWatcher.updateObject(21, this.block_pos_z);
		this.dataWatcher.updateObject(22, this.title_id);
	}

	public void load_params() {
		this.hanging_direction = this.dataWatcher.getWatchableObjectInt(18);
		this.block_pos_x = this.dataWatcher.getWatchableObjectInt(19);
		this.block_pos_y = this.dataWatcher.getWatchableObjectInt(20);
		this.block_pos_z = this.dataWatcher.getWatchableObjectInt(21);
		this.title_id = this.dataWatcher.getWatchableObjectInt(22);

		this.init_params();
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound var1) {
		this.hanging_direction = var1.getInteger("hanging_direction");
		this.block_pos_x = var1.getInteger("block_pos_x");
		this.block_pos_y = var1.getInteger("block_pos_y");
		this.block_pos_z = var1.getInteger("block_pos_z");
		this.title_id = var1.getInteger("title_id");

		init_params();
		save_params();
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound var1) {
		var1.setInteger("hanging_direction", this.hanging_direction);
		var1.setInteger("block_pos_x", this.block_pos_x);
		var1.setInteger("block_pos_y", this.block_pos_y);
		var1.setInteger("block_pos_z", this.block_pos_z);
		var1.setInteger("title_id", this.title_id);
	}

	public void init_params() {
		int direction = this.hanging_direction;

		float half_width = GRIDS_WIDTHS[this.title_id] / 2.0F;
		float half_height = GRIDS_HEIGHTS[this.title_id] / 2.0F;

		float entity_pos_x;
		float entity_pos_y = this.block_pos_y + half_height;
		float entity_pos_z;

		float horizontal_off = 0.0625F; 

		if (direction == 2) {
			entity_pos_x = this.block_pos_x + 1.0F - half_width;
			entity_pos_z = this.block_pos_z - horizontal_off;

			this.rotationYaw = this.prevRotationYaw = 180;
			this.setPosition(entity_pos_x, entity_pos_y, entity_pos_z);
			this.boundingBox.setBounds(entity_pos_x - half_width, entity_pos_y
					- half_height, entity_pos_z - horizontal_off, entity_pos_x
					+ half_width, entity_pos_y + half_height, entity_pos_z
					+ horizontal_off);
			return;
		}

		if (direction == 1) {
			entity_pos_x = this.block_pos_x - horizontal_off;
			entity_pos_z = this.block_pos_z + half_width;

			this.rotationYaw = this.prevRotationYaw = 270;
			this.setPosition(entity_pos_x, entity_pos_y, entity_pos_z);
			this.boundingBox.setBounds(entity_pos_x - horizontal_off,
					entity_pos_y - half_height, entity_pos_z - half_width,
					entity_pos_x + horizontal_off, entity_pos_y + half_height,
					entity_pos_z + half_width);
			return;
		}

		if (direction == 0) {
			entity_pos_x = this.block_pos_x + half_width;
			entity_pos_z = this.block_pos_z + 1.0F + horizontal_off;

			this.rotationYaw = this.prevRotationYaw = 0;
			this.setPosition(entity_pos_x, entity_pos_y, entity_pos_z);
			this.boundingBox.setBounds(entity_pos_x - half_width, entity_pos_y
					- half_height, entity_pos_z - horizontal_off, entity_pos_x
					+ half_width, entity_pos_y + half_height, entity_pos_z
					+ horizontal_off);
			return;
		}

		if (direction == 3) {
			entity_pos_x = this.block_pos_x + 1.0F + horizontal_off;
			entity_pos_z = this.block_pos_z + 1.0F - half_width;

			this.rotationYaw = this.prevRotationYaw = 90;
			this.setPosition(entity_pos_x, entity_pos_y, entity_pos_z);
			this.boundingBox.setBounds(entity_pos_x - horizontal_off,
					entity_pos_y - half_height, entity_pos_z - half_width,
					entity_pos_x + horizontal_off, entity_pos_y + half_height,
					entity_pos_z + half_width);
			return;
		}
	}

	public boolean onValidSurface(EntityPlayer player) {
		if (!this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox)
				.isEmpty()) {
			return false;
		}

		int x = this.block_pos_x;
		int y = this.block_pos_y;
		int z = this.block_pos_z;
		int direction = this.hanging_direction;

		float half_width = GRIDS_WIDTHS[this.title_id] / 2.0F; 
		float half_height = GRIDS_HEIGHTS[this.title_id] / 2.0F; 

		if (direction == 2) {
			x = MathHelper.floor_double(this.posX - half_width);
		}

		if (direction == 1) {
			z = MathHelper.floor_double(this.posZ - half_width);
		}

		if (direction == 0) {
			x = MathHelper.floor_double(this.posX - half_width);
		}

		if (direction == 3) {
			z = MathHelper.floor_double(this.posZ - half_width);
		}

		y = MathHelper.floor_double(this.posY - half_height);

		for (int i = 0; i < GRIDS_WIDTHS[this.title_id]; ++i) {
			for (int j = 0; j < GRIDS_HEIGHTS[this.title_id]; ++j) {
				Material material, material2;

				if (direction == 1 || direction == 3) {
					material = this.worldObj.getBlockMaterial(this.block_pos_x,
							y + j, z + i);
					material2 = this.worldObj.getBlockMaterial(
							this.block_pos_x + 1, y + j, z + i);
				} else {
					material = this.worldObj.getBlockMaterial(x + i, y + j,
							this.block_pos_z);
					material2 = this.worldObj.getBlockMaterial(x + i,
							y + j, this.block_pos_z + 1);
				}
				if (!material.isSolid()) {
					clientPrintLn(player,"Can not paint on this material!");
					return false;
				}
				if (material2.isLiquid()) {
					clientPrintLn(player,"Can not paint in liquid!");
					return false;
				}
			}
		
			
		}

		List<?> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this,
				this.boundingBox);
		for (Object entity : list) {
			if (!(entity instanceof EntitySpray))
				continue;
			clientPrintLn(player,"Error occurs at" + entity + " >_<");
			return false;
		}

		return true;
	}


	@Override
	public boolean func_85031_j(Entity par1Entity) {
		return par1Entity instanceof EntityPlayer ? this.attackEntityFrom(
				DamageSource.causePlayerDamage((EntityPlayer) par1Entity), 0)
				: false;
	}

	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, int par2) {
		if (this.isEntityInvulnerable())
			return false;

		if (!this.isDead && !this.worldObj.isRemote) {
			this.setDead();
			this.setBeenAttacked();
		}
		return true;
	}
	
	private void clientPrintLn(EntityPlayer player, String str) {
		if(!this.worldObj.isRemote) {
			player.sendChatToPlayer(str);
		}
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}
}
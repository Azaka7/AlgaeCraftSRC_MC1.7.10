package azaka7.algaecraft.common.entity;

import azaka7.algaecraft.common.blocks.ACBlocks;
import azaka7.algaecraft.common.blocks.BlockGreekFire;
import azaka7.algaecraft.common.items.ACItems;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class EntityGreekFire extends EntityThrowable{
	
	
	public EntityGreekFire(World p_i1773_1_)
    {
        super(p_i1773_1_);
    }

    public EntityGreekFire(World p_i1774_1_, EntityLivingBase p_i1774_2_)
    {
        super(p_i1774_1_, p_i1774_2_);
    }

    public EntityGreekFire(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_)
    {
        super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
    }
    
    public void setThrowableHeading(double p_70186_1_, double p_70186_3_, double p_70186_5_, float p_70186_7_, float p_70186_8_)
    {
    	super.setThrowableHeading(p_70186_1_, p_70186_3_, p_70186_5_, p_70186_7_, p_70186_8_);
    }
    
    public void scaleMotionSpeed(double scaleFactor){
        this.motionX *= scaleFactor;
        this.motionY *= scaleFactor;
        this.motionZ *= scaleFactor;
    }
    
    @Override
    public void onUpdate()
    {
    	int x = ((Double) this.posX).intValue();
    	int y = ((Double) this.posY).intValue();
    	int z = ((Double) this.posZ).intValue();
    	if(((BlockGreekFire) ACBlocks.greekFire).canNeighborBurn(worldObj, x, y, z)){
    		worldObj.setBlock(x, y, z, ACBlocks.greekFire, 14, 3);
    	}
    }

    /**
     * Called when this EntityThrowable hits a block or entity.
     */
    protected void onImpact(MovingObjectPosition mop)
    {
    	boolean flag = mop.entityHit != null;
        if (flag)
        {
            mop.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()).setFireDamage(), 0.5F);
            mop.entityHit.setFire(10);
        }
        
        if (!this.worldObj.isRemote)
        {
            Double x, y, z;
            if(flag){
    			x = mop.entityHit.posX;
				y = mop.entityHit.posY + (mop.entityHit.height/2);
				z = mop.entityHit.posZ;
    		} else {
    			x = (double) (mop.blockX);
    			y = (double) (mop.blockY);
    			z = (double) (mop.blockZ);
    		}
            
            worldObj.setBlock(x.intValue(), y.intValue(), z.intValue(), ACBlocks.greekFire, 14, 3);
            
            this.setDead();
        }
    }
}

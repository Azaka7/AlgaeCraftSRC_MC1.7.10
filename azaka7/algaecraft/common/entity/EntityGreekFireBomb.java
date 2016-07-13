package azaka7.algaecraft.common.entity;

import azaka7.algaecraft.common.blocks.ACBlocks;
import azaka7.algaecraft.common.blocks.BlockGreekFire;
import azaka7.algaecraft.common.items.ACItems;
import net.minecraft.block.material.Material;
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

public class EntityGreekFireBomb extends EntityThrowable{
	
	private static final float sf = 0.55F;
	
	public EntityGreekFireBomb(World p_i1773_1_)
    {
        super(p_i1773_1_);
        this.motionX *= sf;
        this.motionY *= sf;
        this.motionZ *= sf;
    }

    public EntityGreekFireBomb(World p_i1774_1_, EntityLivingBase p_i1774_2_)
    {
        super(p_i1774_1_, p_i1774_2_);
        this.motionX *= sf;
        this.motionY *= sf;
        this.motionZ *= sf;
    }

    public EntityGreekFireBomb(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_)
    {
        super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
        this.motionX *= sf;
        this.motionY *= sf;
        this.motionZ *= sf;
    }
    
    public void setThrowableHeading(double p_70186_1_, double p_70186_3_, double p_70186_5_, float p_70186_7_, float p_70186_8_)
    {
    	super.setThrowableHeading(p_70186_1_, p_70186_3_, p_70186_5_, p_70186_7_, p_70186_8_);
        this.motionX *= sf * 1.5;
        this.motionY *= sf * 1.5;
        this.motionZ *= sf * 1.5;
    }
    
    public void onUpdate()
    {
    	super.onUpdate();
        int x0 = ((Double) this.posX).intValue() ,y0 = ((Double) this.posY).intValue(), z0 = ((Double) this.posZ).intValue();
        if(worldObj.getBlock(x0,y0,z0).getMaterial() == Material.water && worldObj.getBlock(x0, y0 + 1, z0).getMaterial() == Material.air){
        	String particle = "iconcrack_" + Item.getIdFromItem(ACItems.greekFireBomb);
            for (int i = 0; i < 16; ++i)
            {
                this.worldObj.spawnParticle(particle, this.posX + this.rand.nextFloat()*0.2F - this.rand.nextFloat()*0.2F, this.posY + this.rand.nextFloat()*0.2F, this.posZ + this.rand.nextFloat()*0.2F - this.rand.nextFloat()*0.2F, 0.0D, 0.0D, 0.0D);
            }
        	this.worldObj.playAuxSFX(2002, (int)Math.round(this.posX), (int)Math.round(this.posY), (int)Math.round(this.posZ), 16388);

            if (!this.worldObj.isRemote)
            {
                Double x, y, z;
                for(int dx = -2; dx <= 2; dx++){
                	for(int dz = -2; dz <= 2; dz++){
                		
                		x = this.posX + dx;
            			y = this.posY + rand.nextInt(3);
            			z = this.posZ + dz;
                		
                		if(this.rand.nextInt(10) < 7 /*&& (this.getThrower() instanceof EntityPlayer ? ((EntityPlayer)this.getThrower()).canPlayerEdit(x, y, z, mop.sideHit, new ItemStack(ACItems.greekFireBomb)) : true)*/ && worldObj.getBlock(x.intValue(),y.intValue(),z.intValue()).isReplaceable(worldObj, x.intValue(), y.intValue(), z.intValue())){
                			if(this.worldObj.rayTraceBlocks(Vec3.createVectorHelper(this.posX, this.posY, this.posZ), Vec3.createVectorHelper(x,y,z)) == null){
                				//if(ACBlocks.greekFire.canBlockStay(worldObj, x.intValue(), y.intValue(), z.intValue())){
                        		//	this.worldObj.setBlock(x.intValue(), y.intValue(), z.intValue(), ACBlocks.greekFire, 3, 3);
                				//} else {
                					this.worldObj.spawnEntityInWorld(BlockGreekFire.createFallingBlock(worldObj, x.intValue(), y.intValue(), z.intValue(), 10));
                				//}
                			}
                		}
                	}
                }
                this.setDead();
            }
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
            mop.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 1.0F);
            mop.entityHit.setFire(10);
        }
        
        String particle = "iconcrack_" + Item.getIdFromItem(ACItems.greekFireBomb);
        for (int i = 0; i < 16; ++i)
        {
            this.worldObj.spawnParticle(particle, this.posX + this.rand.nextFloat()*0.2F - this.rand.nextFloat()*0.2F, this.posY + this.rand.nextFloat()*0.2F, this.posZ + this.rand.nextFloat()*0.2F - this.rand.nextFloat()*0.2F, 0.0D, 0.0D, 0.0D);
        }
    	this.worldObj.playAuxSFX(2002, (int)Math.round(this.posX), (int)Math.round(this.posY), (int)Math.round(this.posZ), 16388);

        if (!this.worldObj.isRemote)
        {
            Double x, y, z;
            for(int dx = -2; dx <= 2; dx++){
            	for(int dz = -2; dz <= 2; dz++){
            		if(flag){
            			x = mop.entityHit.posX + dx;
        				y = mop.entityHit.posY + 1;
        				z = mop.entityHit.posZ + dz;
            		} else {
            			x = (double) (mop.blockX + dx);
            			y = (double) (mop.blockY + this.rand.nextInt(3) + 1);
            			z = (double) (mop.blockZ + dz);
            		}
            		if(this.rand.nextInt(10) < 7 /*&& (this.getThrower() instanceof EntityPlayer ? ((EntityPlayer)this.getThrower()).canPlayerEdit(x, y, z, mop.sideHit, new ItemStack(ACItems.greekFireBomb)) : true)*/ && worldObj.getBlock(x.intValue(),y.intValue(),z.intValue()).isReplaceable(worldObj, x.intValue(), y.intValue(), z.intValue())){
            			if(this.worldObj.rayTraceBlocks(Vec3.createVectorHelper(this.posX, this.posY, this.posZ), Vec3.createVectorHelper(x,y,z)) == null){
            				//if(ACBlocks.greekFire.canBlockStay(worldObj, x.intValue(), y.intValue(), z.intValue())){
                    		//	this.worldObj.setBlock(x.intValue(), y.intValue(), z.intValue(), ACBlocks.greekFire, 3, 3);
            				//} else {
            					this.worldObj.spawnEntityInWorld(BlockGreekFire.createFallingBlock(worldObj, x.intValue(), y.intValue(), z.intValue(), 1));
            				//}
            			}
            		}
            	}
            }
            this.setDead();
        }
    }
}

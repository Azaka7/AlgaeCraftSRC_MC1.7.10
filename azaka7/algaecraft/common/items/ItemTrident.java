package azaka7.algaecraft.common.items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import azaka7.algaecraft.common.handlers.ACPathingHandler.Pos;

import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemTrident extends ItemSword {
	
	private static final Set blocks = Sets.newHashSet(new Block[] {Blocks.chest, Blocks.pumpkin, Blocks.melon_block, Blocks.hay_block, Blocks.web, Blocks.wooden_pressure_plate, Blocks.stone_pressure_plate, Blocks.rail, Blocks.sponge, Blocks.vine});
	private float attackDamage;
	private ToolMaterial toolMaterial;

	protected ItemTrident(float damage, ToolMaterial material) {
		//super(damage, material, blocks);
		super(material);
		toolMaterial = material;
        this.attackDamage = 3.0F + material.getDamageVsEntity();
	}
	
	@Override
	public float func_150931_i()
    {
        return this.toolMaterial.getDamageVsEntity();
    }
	
	public float func_150893_a(ItemStack p_150893_1_, Block p_150893_2_)
    {
        return this.blocks.contains(p_150893_2_) ? this.toolMaterial.getEfficiencyOnProperMaterial() : super.func_150893_a(p_150893_1_, p_150893_2_);
    }
	
	/**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
    public boolean hitEntity(ItemStack p_77644_1_, EntityLivingBase p_77644_2_, EntityLivingBase p_77644_3_)
    {
        p_77644_1_.damageItem(1, p_77644_3_);
        return true;
    }
	
	/**
     * Returns True is the item is renderer in full 3D when hold.
     */
    @SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        return true;
    }
    
    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
    {
        p_77659_3_.setItemInUse(p_77659_1_, this.getMaxItemUseDuration(p_77659_1_));
        return p_77659_1_;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack p_77661_1_)
    {
        return EnumAction.bow;
    }
    
    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack p_77626_1_)
    {
        return 72000;
    }
    
    /**
     * Returns true if the item can be used on the given entity, e.g. shears on sheep.
     */
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase p_111207_3_)
    {
    	return false;
    }
    
    /**
     * called when the player releases the use item button. Args: itemstack, world, entityplayer, itemInUseCount
     */
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int duration)
    {
        int j = this.getMaxItemUseDuration(stack) - duration;
        float charge = (float)j / 20.0F;
        charge = (charge * charge + charge * 2.0F) / 3.0F;

        if ((double)charge < 0.1D)
        {
            return;
        }

        if (charge > 1.0F)
        {
            charge = 1.0F;
        }
        
        player.swingItem();
        
        float pitch = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * world.getWorldTime();
        float yaw = player.rotationYawHead;
        
        double xold = player.posX;//player.boundingBox.minX+(player.width/2);
        double yold = player.posY - 0.30000001192092896D + (double)player.getEyeHeight();
        double zold = player.posZ;//player.boundingBox.minZ+(player.width/2);
        
        pitch = (float) Math.toRadians(pitch);
        yaw = (float) Math.toRadians(yaw);
        double xnew = xold+(6*Math.sin(0-yaw)*Math.cos(pitch));
        double ynew = yold+(6*Math.sin(0-pitch));
        double znew = zold+(6*Math.cos(yaw)*Math.cos(pitch));
        //System.out.println("Vec2: "+xnew+","+ynew+","+znew);
        
        Vec3 oldPos = Vec3.createVectorHelper(xold,yold,zold);
        Vec3 newPos = Vec3.createVectorHelper(xnew,ynew,znew);
        
        MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, true);
        AxisAlignedBB aimArea = AxisAlignedBB.getBoundingBox(Math.min(xold, xnew), Math.min(yold, ynew), Math.min(zold, znew), Math.max(xold, xnew), Math.max(yold, ynew), Math.max(zold, znew));
        
        List<EntityLivingBase> potentialVictims = new ArrayList<EntityLivingBase>();
        
        List<Entity> targets = world.getEntitiesWithinAABBExcludingEntity(player, aimArea);
		//System.out.println("Entity? : "+targets.isEmpty());
        if(!targets.isEmpty()){
			for(Entity target : targets){
				if(target instanceof EntityLivingBase && target.boundingBox.calculateIntercept(oldPos, newPos) != null){
					potentialVictims.add((EntityLivingBase) target);
					//System.out.println("!!!!!ENTITY!!!!!!");
				}
			}
		}
        if(potentialVictims.isEmpty()){return;}
        
        List<Integer> distances = new ArrayList<Integer>();
        for(int i = 0; i < potentialVictims.size(); i++){
        	EntityLivingBase target = potentialVictims.get(i);
        	distances.add((Math.round(target.getDistanceToEntity(player)*1000)*1000)+i);
        }
        Collections.sort(distances);
        EntityLivingBase victim = null;
        for(Integer pos : distances){
        	victim = potentialVictims.get(pos.intValue()%1000);
        }
        
        if(!(movingobjectposition == null || movingobjectposition.typeOfHit == null)){
            
        	if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
        	{
        		Pos pos = new Pos(movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ);
        		if(pos.getBlock(world).isOpaqueCube() && player.getDistance(movingobjectposition.hitVec.xCoord,movingobjectposition.hitVec.yCoord + 0.30000001192092896D - (double)player.getEyeHeight(), movingobjectposition.hitVec.zCoord) < player.getDistanceToEntity(victim)){
        			return;
        		}
        	}
        }
        
        boolean flag = false;
        
        Vec3 realPos = Vec3.createVectorHelper(oldPos.xCoord, oldPos.yCoord, oldPos.zCoord);
        Pos basePos = new Pos(Math.round((float) realPos.xCoord), Math.round((float) realPos.yCoord), Math.round((float) realPos.zCoord));
        Vec3 unitVec = Vec3.createVectorHelper(Math.sin(0-yaw)*Math.cos(pitch), Math.sin(0-pitch), Math.cos(yaw)*Math.cos(pitch));
        for(int i = 0; i < 14 && !flag; i++){
        	if(basePos.getBlock(world).isOpaqueCube()){
        		flag = true;
        	}
        	else{
        		realPos = realPos.addVector(unitVec.xCoord/2, unitVec.yCoord/2, unitVec.zCoord/2);
                basePos = new Pos(Math.round((float) realPos.xCoord), Math.round((float) realPos.yCoord), Math.round((float) realPos.zCoord));
        	}
        	
        }
        if(flag){
            if(player.getDistance(realPos.xCoord, realPos.yCoord + 0.30000001192092896D - (double)player.getEyeHeight(), realPos.zCoord) < player.getDistanceToEntity(victim)){
                return;
            }
        }
        
        
        if(victim.worldObj.isRemote){return;}
    	float distance = victim.getDistanceToEntity(player);
    	if(distance > 6){return;}
    	if(distance == 0){distance = 0.00001F;}
    	
    	int flame = EnchantmentHelper.getFireAspectModifier(player);
        if (victim instanceof EntityLivingBase && flame > 0 && !victim.isBurning())
        {
            victim.setFire(flame*2);
        }
        victim.attackEntityFrom(DamageSource.causePlayerDamage(player), (float) ((this.toolMaterial.getDamageVsEntity()+1)*Math.pow(2.0D, (1.90001*(charge/distance)))));
    	
        stack.damageItem(2, player);
        
    }
	
	/**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
     */
    public Multimap getItemAttributeModifiers()
    {
        Multimap multimap = super.getItemAttributeModifiers();
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", (double)this.attackDamage, 0));
        return multimap;
    }
	
	public static class Materials{
		public static final ToolMaterial bone = EnumHelper.addToolMaterial("BONE_AC", 0, 100, 3.0F, 0.5F, 18).setRepairItem(new ItemStack(Items.bone));
		public static final ToolMaterial emerald = EnumHelper.addToolMaterial("EMERALD_GEM_AC", 2, 812, 8.5F, 4.0F, 12).setRepairItem(new ItemStack(Items.emerald));
		public static final ToolMaterial netherbrick = EnumHelper.addToolMaterial("NETHERBRICK_AC", 1, 128, 4.8F, 1.2F, 20).setRepairItem(new ItemStack(Items.netherbrick));
	}

}

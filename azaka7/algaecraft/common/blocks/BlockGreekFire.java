package azaka7.algaecraft.common.blocks;

import static net.minecraftforge.common.util.ForgeDirection.DOWN;
import static net.minecraftforge.common.util.ForgeDirection.EAST;
import static net.minecraftforge.common.util.ForgeDirection.NORTH;
import static net.minecraftforge.common.util.ForgeDirection.SOUTH;
import static net.minecraftforge.common.util.ForgeDirection.UP;
import static net.minecraftforge.common.util.ForgeDirection.WEST;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import azaka7.algaecraft.common.ACGameData;

public class BlockGreekFire extends BlockFire {
	
	public BlockGreekFire(){
		super();
	}

    public int getRenderType()
    {
        return ACGameData.greekFireModelID;
    }
	
	public static void registerFlamables()
    {
        ((BlockFire) ACBlocks.greekFire).func_149842_a(getIdFromBlock(Blocks.planks), 5, 20);
        ((BlockFire) ACBlocks.greekFire).func_149842_a(getIdFromBlock(Blocks.double_wooden_slab), 5, 20);
        ((BlockFire) ACBlocks.greekFire).func_149842_a(getIdFromBlock(Blocks.wooden_slab), 5, 20);
        ((BlockFire) ACBlocks.greekFire).func_149842_a(getIdFromBlock(Blocks.fence), 5, 20);
        ((BlockFire) ACBlocks.greekFire).func_149842_a(getIdFromBlock(Blocks.oak_stairs), 5, 20);
        ((BlockFire) ACBlocks.greekFire).func_149842_a(getIdFromBlock(Blocks.birch_stairs), 5, 20);
        ((BlockFire) ACBlocks.greekFire).func_149842_a(getIdFromBlock(Blocks.spruce_stairs), 5, 20);
        ((BlockFire) ACBlocks.greekFire).func_149842_a(getIdFromBlock(Blocks.jungle_stairs), 5, 20);
        ((BlockFire) ACBlocks.greekFire).func_149842_a(getIdFromBlock(Blocks.log), 5, 5);
        ((BlockFire) ACBlocks.greekFire).func_149842_a(getIdFromBlock(Blocks.log2), 5, 5);
        ((BlockFire) ACBlocks.greekFire).func_149842_a(getIdFromBlock(Blocks.leaves), 30, 60);
        ((BlockFire) ACBlocks.greekFire).func_149842_a(getIdFromBlock(Blocks.leaves2), 30, 60);
        ((BlockFire) ACBlocks.greekFire).func_149842_a(getIdFromBlock(Blocks.bookshelf), 30, 20);
        ((BlockFire) ACBlocks.greekFire).func_149842_a(getIdFromBlock(Blocks.tnt), 15, 100);
        ((BlockFire) ACBlocks.greekFire).func_149842_a(getIdFromBlock(Blocks.tallgrass), 60, 100);
        ((BlockFire) ACBlocks.greekFire).func_149842_a(getIdFromBlock(Blocks.double_plant), 60, 100);
        ((BlockFire) ACBlocks.greekFire).func_149842_a(getIdFromBlock(Blocks.yellow_flower), 60, 100);
        ((BlockFire) ACBlocks.greekFire).func_149842_a(getIdFromBlock(Blocks.red_flower), 60, 100);
        ((BlockFire) ACBlocks.greekFire).func_149842_a(getIdFromBlock(Blocks.wool), 30, 60);
        ((BlockFire) ACBlocks.greekFire).func_149842_a(getIdFromBlock(Blocks.vine), 15, 100);
        ((BlockFire) ACBlocks.greekFire).func_149842_a(getIdFromBlock(Blocks.coal_block), 5, 5);
        ((BlockFire) ACBlocks.greekFire).func_149842_a(getIdFromBlock(Blocks.hay_block), 60, 20);
        ((BlockFire) ACBlocks.greekFire).func_149842_a(getIdFromBlock(Blocks.carpet), 60, 20);
        ((BlockFire) ACBlocks.greekFire).func_149842_a(getIdFromBlock(Blocks.water), 100, 1);
        ((BlockFire) ACBlocks.greekFire).func_149842_a(getIdFromBlock(Blocks.flowing_water), 100, 1);
    }
	
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
    {
		if(!entity.isInWater()){
			entity.setFire(10);
		}
		entity.attackEntityFrom(DamageSource.inFire, 1);
    }
	
	public void breakBlock(World world, int x, int y, int z, Block block, int meta)
    {
		if(world.getBlock(x, y, z).getMaterial() == Material.water && world.getBlock(x, y+1, z).isReplaceable(world, x, y, z)){ 
			world.setBlock(x, y+1, z, this, meta, 3);
		}
		else if(meta != 15 && world.isAirBlock(x, y, z) && world.isAirBlock(x, y-1, z) && !this.canNeighborBurn(world, x, y, z)){
			//world.setBlock(x, y-1, z, this, meta, 3);
			EntityFallingBlock falling = createFallingBlock(world, x, y, z, meta);
			world.spawnEntityInWorld(falling);
		}
    }

    public boolean canCatchFire(IBlockAccess world, int x, int y, int z, ForgeDirection face)
    {
        return this.getFlammability(world.getBlock(x, y, z)) > 0 || world.getBlock(x, y, z).getMaterial() == Material.water;
    }

    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
        return World.doesBlockHaveSolidTopSurface(world, x, y - 1, z) || world.getBlock(x, y - 1, z).getMaterial() == Material.water || this.canNeighborBurn(world, x, y, z);
    }
    
    public void onBlockAdded(World world, int p_149726_2_, int p_149726_3_, int p_149726_4_)
    {
        if (world.provider.dimensionId > 0 || !Blocks.portal.func_150000_e(world, p_149726_2_, p_149726_3_, p_149726_4_))
        {
        	world.scheduleBlockUpdate(p_149726_2_, p_149726_3_, p_149726_4_, this, this.tickRate(world) + world.rand.nextInt(10));
        }
    }
	
	private boolean canNeighborBurn(World world, int x, int y, int z)
    {
        return this.canCatchFire(world, x + 1, y, z, WEST ) ||
               this.canCatchFire(world, x - 1, y, z, EAST ) ||
               this.canCatchFire(world, x, y - 1, z, UP   ) ||
               this.canCatchFire(world, x, y + 1, z, DOWN ) ||
               this.canCatchFire(world, x, y, z - 1, SOUTH) ||
               this.canCatchFire(world, x, y, z + 1, NORTH);
    }
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand)
    {
        if (world.getGameRules().getGameRuleBooleanValue("doFireTick"))
        {
            int l = world.getBlockMetadata(x, y, z);
            boolean flag = world.getBlock(x, y - 1, z).isFireSource(world, x, y - 1, z, UP);
            
            if(!flag && l > 5 && rand.nextInt(100) < (l*l*0.4)){
        		world.setBlockToAir(x, y, z);
        		return;
        	}
        	
            if (!this.canPlaceBlockAt(world, x, y, z))
            {
                world.setBlockToAir(x, y, z);
            }

            if (!flag && !world.isRaining() && (world.canLightningStrikeAt(x, y, z) || world.canLightningStrikeAt(x - 1, y, z) || world.canLightningStrikeAt(x + 1, y, z) || world.canLightningStrikeAt(x, y, z - 1) || world.canLightningStrikeAt(x, y, z + 1)))
            {
                world.setBlockToAir(x, y, z);
            }
            else
            {

                if (l < 15)
                {
                    world.setBlockMetadataWithNotify(x, y, z, l + rand.nextInt(3) / 2, 4);
                }

                world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world) + rand.nextInt(10));

                if (!flag && !this.canNeighborBurn(world, x, y, z))
                {
                    if (!World.doesBlockHaveSolidTopSurface(world, x, y - 1, z) || l > 3)
                    {
                        world.setBlockToAir(x, y, z);
                    }
                }
                else if (!flag && /*!this.canCatchFire(world, x, y - 1, z, UP) &&*/ l == 15 && rand.nextInt(4) == 0)
                {
                    world.setBlockToAir(x, y, z);
                }
                else
                {
                    boolean flag1 = world.isBlockHighHumidity(x, y, z);
                    byte b0 = 0;

                    if (flag1)
                    {
                        b0 = -50;
                    }

                    this.tryCatchFire(world, x + 1, y, z, 300 + b0, rand, l, WEST );
                    this.tryCatchFire(world, x - 1, y, z, 300 + b0, rand, l, EAST );
                    this.tryCatchFire(world, x, y - 1, z, 250 + b0, rand, l, UP   );
                    this.tryCatchFire(world, x, y + 1, z, 250 + b0, rand, l, DOWN );
                    this.tryCatchFire(world, x, y, z - 1, 300 + b0, rand, l, SOUTH);
                    this.tryCatchFire(world, x, y, z + 1, 300 + b0, rand, l, NORTH);
                    
                    for (int i1 = x - 1; i1 <= x + 1; ++i1)
                    {
                        for (int j1 = z - 1; j1 <= z + 1; ++j1)
                        {
                            for (int k1 = y - 1; k1 <= y + 4; ++k1)
                            {
                                if (i1 != x || k1 != y || j1 != z)
                                {
                                    int l1 = 100;

                                    if (k1 > y + 1)
                                    {
                                        l1 += (k1 - (y + 1)) * 100;
                                    }

                                    int i2 = this.getChanceOfNeighborsEncouragingFire(world, i1, k1, j1);

                                    if (i2 > 0)
                                    {
                                        int j2 = (i2 + 40 + world.difficultySetting.getDifficultyId() * 7) / (l + 30);

                                        if (flag1)
                                        {
                                            j2 /= 2;
                                        }

                                        if (j2 > 0 && rand.nextInt(l1) <= j2) //&& (!world.canLightningStrikeAt(i1, k1, j1)) && !world.canLightningStrikeAt(i1 - 1, k1, z) && !world.canLightningStrikeAt(i1 + 1, k1, j1) && !world.canLightningStrikeAt(i1, k1, j1 - 1) && !world.canLightningStrikeAt(i1, k1, j1 + 1))
                                        {
                                        	int k2 = l + rand.nextInt(11) / 4; // 5/4

                                            if (k2 > 15)
                                            {
                                                k2 = 15;
                                            }

                                            world.setBlock(i1, k1, j1, this, k2, 3);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void tryCatchFire(World world, int x, int y, int z, int p_149841_5_, Random rand, int p_149841_7_, ForgeDirection face)
    {
        int j1 = this.getFlammability(world.getBlock(x, y, z));

        if (rand.nextInt(p_149841_5_) < j1)
        {
            boolean flag = world.getBlock(x, y, z) == Blocks.tnt;

            if (rand.nextInt(p_149841_7_ + 10) < 5)
            {
                int k1 = p_149841_7_ + rand.nextInt(5) / 4;

                if (k1 > 15)
                {
                    k1 = 15;
                }

                world.setBlock(x, y, z, this, k1, 3);
            }
            else
            {
                world.setBlockToAir(x, y, z);
            }

            if (flag)
            {
                Blocks.tnt.onBlockDestroyedByPlayer(world, x, y, z, 1);
            }
        }
    }
    
    private int getChanceOfNeighborsEncouragingFire(World world, int x, int y, int z)
    {
        byte b0 = 0;

        if (!world.isAirBlock(x, y, z))
        {
            return 0;
        }
        else
        {
            int l = b0;
            l = this.getChanceToEncourageFire(world, x + 1, y, z, l, WEST );
            l = this.getChanceToEncourageFire(world, x - 1, y, z, l, EAST );
            l = this.getChanceToEncourageFire(world, x, y - 1, z, l, UP   );
            l = this.getChanceToEncourageFire(world, x, y + 1, z, l, DOWN );
            l = this.getChanceToEncourageFire(world, x, y, z - 1, l, SOUTH);
            l = this.getChanceToEncourageFire(world, x, y, z + 1, l, NORTH);
            return l;
        }
    }
    
    @Override
    public int getChanceToEncourageFire(IBlockAccess world, int x, int y, int z, int oldChance, ForgeDirection face)
    {
        int newChance = this.getEncouragement(world.getBlock(x, y, z));
        return (newChance > oldChance ? newChance : oldChance);
    }
    
    public static EntityFallingBlock createFallingBlock(World world, int x, int y, int z, int meta){
    	EntityFallingBlock falling = new EntityFallingBlock(world, x+0.5, y+0.5, z+0.5, ACBlocks.greekFire, meta){
    		public void onUpdate()
    	    {
    			super.onUpdate();
    			int x = ((Double) this.posX).intValue();
    			int y = ((Double) this.posY).intValue();
    			int z = ((Double) this.posZ).intValue();
    			if(worldObj.getBlock(x,y-1,z).getMaterial() == Material.water){
    				worldObj.setBlock(x, y, z, this.func_145805_f());
    				this.setDead();
    			}
    	    }
		};
		falling.field_145812_b = 2;
		
		return falling;
    }
}

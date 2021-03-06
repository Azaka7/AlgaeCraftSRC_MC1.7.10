package azaka7.algaecraft.common.blocks;

import java.util.Random;

import azaka7.algaecraft.AlgaeCraft;
import azaka7.algaecraft.common.ACGameData;
import azaka7.algaecraft.common.CommonProxy;
import azaka7.algaecraft.common.tileentity.TileEntityWaterFilter;
import azaka7.algaecraft.common.world.ACBiomes;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.EnumPlantType;

public class BlockAlgae extends BlockBush {
	
	public BlockAlgae() {
		super(Material.grass);
		float var3 = 0.5F;
        float var4 = 0.015625F;
		this.setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, var4, 0.5F + var3);
		this.setStepSound(soundTypeSnow);
	}
	
	@Override
    public boolean isReplaceable(IBlockAccess world, int x, int y, int z)
    {
        return true;
    }
	
	@Override
    public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z)
    {
		return EnumPlantType.Water;
    }
	
	@Override
	public boolean canBlockStay(World world, int x, int y, int z)
    {
		Block below = world.getBlock(x, y-1, z);
		
		if(below.getMaterial() != Material.water || below == Blocks.flowing_water){return false;}
		if(world.getBlock(x, y+1, z).getMaterial().isLiquid()){return false;}
		
		boolean biome = false;
		BiomeGenBase thisBiome = world.getBiomeGenForCoords(x, z);
		if(ACGameData.biomeIDSwampList.length > 0){
			for(int n = 0; n<ACGameData.biomeIDSwampList.length; n++){
				if(ACGameData.biomeIDSwampList[n]==thisBiome.biomeID|| BiomeGenBase.swampland.biomeID==thisBiome.biomeID){
					biome=true;
					break;
				}
			}
		}
		/*if(!biome){
			for(int n=0;n<ACGameData.biomeIDSwampList.length;n++){
				if(ACBiomes.isBiomeIDInList(thisBiome,ACGameData.biomeIDSwampList) || BiomeGenBase.swampland.biomeID==thisBiome.biomeID){
					biome=true;
					break;
				}
			}
		}*/
		
        for(int x1 = -6; x1 <= 6; x1++){
        	for(int y1 = -6; y1 <= 6; y1++){
        		for(int z1 = -6; z1 <= 6; z1++){
        			if(world.getTileEntity(x+x1, y+y1, z+z1) instanceof TileEntityWaterFilter){
        				if(!biome && BlockWaterFilter.isLocationValid(world, x, y-1, z, BlockWaterFilter.EnumWaterType.SWAMP, x+x1, y+y1, z+z1)){
                    		return true;
                    	}
                    	else if(biome && BlockWaterFilter.isLocationInvalid(world, x, y-1, z, BlockWaterFilter.EnumWaterType.SWAMP, x+x1, y+y1, z+z1)){
                    		return false;
                    	}
        			}
                }
            }
        }
		return biome;
    }
	
	public void updateTick(World par1World, int par2, int par3, int par4, Random rand)
    {
        super.updateTick(par1World, par2, par3, par4, rand);
        double growChance = ACGameData.algaeGrowthChance;
        if (rand.nextDouble() < growChance){
        	if(this.canBlockStay(par1World, par2+1, par3, par4)&&par1World.getBlock(par2+1, par3, par4)==Blocks.air){
        		par1World.setBlock(par2+1, par3, par4, this, 0, 2);
        	}
        }
        if (rand.nextDouble() < growChance){
        	if(this.canBlockStay(par1World,par2-1, par3, par4)&&par1World.getBlock(par2-1, par3, par4)==Blocks.air){
        		par1World.setBlock(par2-1, par3, par4, this, 0, 2);
        	}
        }
        if (rand.nextDouble() < growChance){
        	if(this.canBlockStay(par1World,par2, par3, par4+1)&&par1World.getBlock(par2, par3, par4+1)==Blocks.air){
        		par1World.setBlock(par2, par3, par4+1, this, 0, 2);
        	}
        }
        if (rand.nextDouble() < growChance){
        	if(this.canBlockStay(par1World,par2, par3, par4-1)&&par1World.getBlock(par2, par3, par4-1)==Blocks.air){
        		par1World.setBlock(par2, par3, par4-1, this, 0, 2);
        	}
        }
    }
	
	public int getRenderType()
    {
        return ACGameData.algaeModelID;
    }
	
	public int getRenderBlockPass()
    {
		return ACBlocks.renderPass();
    }
	
	public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        return (par1IBlockAccess.getBlock(par2, par3, par4)==Blocks.air || !par1IBlockAccess.getBlock(par2, par3, par4).isOpaqueCube()) && !(par1IBlockAccess.getBlock(par2, par3, par4)==this) || par5==1;
    }
	
	@SideOnly(Side.CLIENT)
	public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
		if((par5Entity.posY - par3 - par5Entity.height) <= 0.015625){
			par5Entity.motionX /= 1.15;
			par5Entity.motionY /= 1.1;
			par5Entity.motionZ /= 1.15;
		}
	}

}
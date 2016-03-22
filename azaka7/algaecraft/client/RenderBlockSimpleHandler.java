package azaka7.algaecraft.client;

import static net.minecraftforge.common.util.ForgeDirection.DOWN;
import static net.minecraftforge.common.util.ForgeDirection.EAST;
import static net.minecraftforge.common.util.ForgeDirection.NORTH;
import static net.minecraftforge.common.util.ForgeDirection.SOUTH;
import static net.minecraftforge.common.util.ForgeDirection.UP;
import static net.minecraftforge.common.util.ForgeDirection.WEST;

import java.lang.reflect.Array;
import java.util.ArrayList;

import azaka7.algaecraft.AlgaeCraft;
import azaka7.algaecraft.client.model.ModelBrazier;
import azaka7.algaecraft.common.ACGameData;
import azaka7.algaecraft.common.blocks.BlockBrazier;
import azaka7.algaecraft.common.blocks.BlockCoral;
import azaka7.algaecraft.common.blocks.BlockGreekFire;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderBlockSimpleHandler implements ISimpleBlockRenderingHandler {
	
	//public static final BlockModelRenderer brazierRenderer = new BlockModelRenderer(null, new ModelBrazier(), brazierTexture, 1.0F);
	
	public final int modelID;
	public final boolean render3Dinventory;
	public final String modelName;
	
	public RenderBlockSimpleHandler(int modelid, boolean b, String string) {
		
		modelID = modelid;
		render3Dinventory = b;
		modelName = string;
		
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId,
			RenderBlocks renderer) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		if(modelID == ACGameData.algaeModelID){
			return renderAlgae(world, x, y, z, block, renderer);
		}
		else if(modelID == ACGameData.coralModelID){
			return renderCoral(renderer, world,  x, y, z, block);
		}
		else if(modelID == ACGameData.spongeModelID){
			return renderSponge(renderer, world, x, y, z, block);
		}
		else if(modelID == ACGameData.seaweedModelID){
			return renderSeaweed(renderer, world, x, y, z, block);
		}
		else if(modelID == ACGameData.greekFireModelID){
			return renderGreekFire(world, (BlockGreekFire) block, x, y, z);
		}
		else if(modelID == ACGameData.brazierModelID){
			return renderBrazier(renderer, world, x, y, z, block);//renderBrazier(renderer, world, x, y, z, block);
		}
		/*if(modelID == ACGameData.waterBlockModelID){
			return  renderWaterOnBasicBlock(renderer,world,x,y,z,block);
		}*/
		return false;
	}
	
	private boolean renderBrazier(RenderBlocks renderer, IBlockAccess world, int x, int y,
			int z, Block block){
		//Do position checks
		//brazierRenderer.renderAtPos(x, y, z);
		if(((BlockBrazier) block).inWater){
			renderBlockCrops(block,x,y,z,world,renderer);
			renderer.renderBlockLiquid(Blocks.flowing_water, x, y, z);
		}
		else{
			renderBlockCrops(block,x,y,z,world,renderer);
		}
		return true;
	}
	
	private boolean renderWaterOnBasicBlock(RenderBlocks renderer,
			IBlockAccess world, int x, int y, int z, Block block) {
		if(getAdjacentCount(world,x,y,z,Blocks.water)>=2){
			renderer.renderBlockLiquid(Blocks.water, x, y, z);
			return true;
		}
		else if(getAdjacentCount(world,x,y,z,Blocks.flowing_water,Blocks.water)>=1){
			renderer.renderBlockLiquid(Blocks.flowing_water, x, y, z);
		}
		return true;
	}
	
	private int getAdjacentCount(IBlockAccess world, int x, int y, int z, Block... target){
		int count = 0;
		ArrayList list = new ArrayList();
		for(int i = 0; i < target.length; i++){
			list.add(target[i]);
		}
		if(list.contains(world.getBlock(x-1, y, z))){count++;}
		if(list.contains(world.getBlock(x+1, y, z))){count++;}
		if(list.contains(world.getBlock(x, y, z-1))){count++;}
		if(list.contains(world.getBlock(x, y, z+1))){count++;}
		return count;
	}

	public boolean renderSeaweed(RenderBlocks renderer, IBlockAccess world, int x, int y,
			int z, Block block){
		renderBlockCrops(block,x,y,z,world,renderer);
		//this.renderBlockFluids(renderer, blockAccess, Block.waterStill, par2, par3, par4);
		//renderer.renderBlockCropsImpl(block, world.getBlockMetadata(x, y, z), (double)x, (double)((float)y - 0.0625F), (double)z);
		renderer.renderBlockLiquid(Blocks.flowing_water, x, y, z);
		return true;
	}
	public boolean renderBlockCrops(Block p_147796_1_, int p_147796_2_, int p_147796_3_, int p_147796_4_, IBlockAccess blockAccess, RenderBlocks renderer)
    {
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(p_147796_1_.getMixedBrightnessForBlock(blockAccess, p_147796_2_, p_147796_3_, p_147796_4_));
        tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
        renderer.renderBlockCropsImpl(p_147796_1_, blockAccess.getBlockMetadata(p_147796_2_, p_147796_3_, p_147796_4_), (double)p_147796_2_, (double)((float)p_147796_3_ - 0.0625F), (double)p_147796_4_);
        return true;
    }
	
	
	
	public boolean renderAlgae(IBlockAccess world, int x, int y, int z,
			Block block, RenderBlocks renderer){
		
		block.setBlockBounds(0.0F, 0.0F-0.125F, 0.0F, 1.0F, 0.0F-0.0625F, 1.0F);
		renderer.renderStandardBlock(block, x, y, z);
		float var7 = 0.5F;
        float var8 = 0.015625F;
		block.setBlockBounds(0.5F - var7, 0.0F-0.125F, 0.5F - var7, 0.5F + var7, var8, 0.5F + var7);
        return true;
	}
	
	private boolean renderCoral(RenderBlocks var1, IBlockAccess var2, int var3, int var4, int var5, Block var6)
    {
		if(var2.getBlock(var3, var4, var5) instanceof BlockCoral){
			if(var2.getBlockMetadata(var3, var4, var5)%8 == 6){
				var6.setBlockBounds(0.375F, 0.0F, 0.375F, 0.625F, 0.25F, 0.625F);
				var1.renderStandardBlock(var6, var3, var4, var5);
			}
			else if(var2.getBlockMetadata(var3, var4, var5)%8 == 2){
				var6.setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
				var1.renderStandardBlock(var6, var3, var4, var5);
			}
			else{
				var6.setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.75F, 0.75F);
				var1.renderCrossedSquares(var6, var3, var4, var5);
			}
		}
		else{
			var6.setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.75F, 0.75F);
			var1.renderCrossedSquares(var6, var3, var4, var5);
		}
		var1.renderBlockLiquid(Blocks.water, var3, var4, var5);
		return true;
    }
	
	private boolean renderSponge(RenderBlocks var1, IBlockAccess var2, int var3, int var4, int var5, Block var6)
    {
		int var7 = var2.getBlockMetadata(var3, var4, var5);
		if(var7 == 0){
			var6.setBlockBounds(0.375F, 0.0F, 0.375F, 0.625F, 0.25F, 0.625F);
			var1.renderStandardBlock(var6, var3, var4, var5);
		}
		else if(var7 == 1){
			var6.setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
			var1.renderStandardBlock(var6, var3, var4, var5);
		}
		else if(var7 == 2){
			var6.setBlockBounds(0.125F, 0.0F, 0.125F, 0.875F, 0.75F, 0.875F);
			var1.renderStandardBlock(var6, var3, var4, var5);
		}
		
		var1.renderBlockLiquid(Blocks.water, var3, var4, var5);
		
		return true;
    }
	
	public boolean renderGreekFire(IBlockAccess blockAccess, BlockGreekFire block, int p_147801_2_, int p_147801_3_, int p_147801_4_)
    {
        Tessellator tessellator = Tessellator.instance;
        IIcon iicon = block.getFireIcon(0);
        IIcon iicon1 = block.getFireIcon(1);
        IIcon iicon2 = iicon;

        tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
        tessellator.setBrightness(block.getMixedBrightnessForBlock(blockAccess, p_147801_2_, p_147801_3_, p_147801_4_));
        double d0 = (double)iicon2.getMinU();
        double d1 = (double)iicon2.getMinV();
        double d2 = (double)iicon2.getMaxU();
        double d3 = (double)iicon2.getMaxV();
        float f = 1.4F;
        double d5;
        double d6;
        double d7;
        double d8;
        double d9;
        double d10;
        double d11;

        if (!World.doesBlockHaveSolidTopSurface(blockAccess, p_147801_2_, p_147801_3_ - 1, p_147801_4_) && !block.canCatchFire(blockAccess, p_147801_2_, p_147801_3_ - 1, p_147801_4_, UP))
        {
        	float f2 = 0.2F;
            float f1 = 0.0625F;
            boolean sides = false;

            if ((p_147801_2_ + p_147801_3_ + p_147801_4_ & 1) == 1)
            {
                d0 = (double)iicon1.getMinU();
                d1 = (double)iicon1.getMinV();
                d2 = (double)iicon1.getMaxU();
                d3 = (double)iicon1.getMaxV();
            }

            if ((p_147801_2_ / 2 + p_147801_3_ / 2 + p_147801_4_ / 2 & 1) == 1)
            {
                d5 = d2;
                d2 = d0;
                d0 = d5;
            }

            if (block.canCatchFire(blockAccess, p_147801_2_ - 1, p_147801_3_, p_147801_4_, EAST))
            {
            	sides = true;
                tessellator.addVertexWithUV((double)((float)p_147801_2_ + f2), (double)((float)p_147801_3_ + f + f1), (double)(p_147801_4_ + 1), d2, d1);
                tessellator.addVertexWithUV((double)(p_147801_2_ + 0), (double)((float)(p_147801_3_ + 0) + f1), (double)(p_147801_4_ + 1), d2, d3);
                tessellator.addVertexWithUV((double)(p_147801_2_ + 0), (double)((float)(p_147801_3_ + 0) + f1), (double)(p_147801_4_ + 0), d0, d3);
                tessellator.addVertexWithUV((double)((float)p_147801_2_ + f2), (double)((float)p_147801_3_ + f + f1), (double)(p_147801_4_ + 0), d0, d1);
                tessellator.addVertexWithUV((double)((float)p_147801_2_ + f2), (double)((float)p_147801_3_ + f + f1), (double)(p_147801_4_ + 0), d0, d1);
                tessellator.addVertexWithUV((double)(p_147801_2_ + 0), (double)((float)(p_147801_3_ + 0) + f1), (double)(p_147801_4_ + 0), d0, d3);
                tessellator.addVertexWithUV((double)(p_147801_2_ + 0), (double)((float)(p_147801_3_ + 0) + f1), (double)(p_147801_4_ + 1), d2, d3);
                tessellator.addVertexWithUV((double)((float)p_147801_2_ + f2), (double)((float)p_147801_3_ + f + f1), (double)(p_147801_4_ + 1), d2, d1);
            }

            if (block.canCatchFire(blockAccess, p_147801_2_ + 1, p_147801_3_, p_147801_4_, WEST))
            {
            	sides = true;
                tessellator.addVertexWithUV((double)((float)(p_147801_2_ + 1) - f2), (double)((float)p_147801_3_ + f + f1), (double)(p_147801_4_ + 0), d0, d1);
                tessellator.addVertexWithUV((double)(p_147801_2_ + 1 - 0), (double)((float)(p_147801_3_ + 0) + f1), (double)(p_147801_4_ + 0), d0, d3);
                tessellator.addVertexWithUV((double)(p_147801_2_ + 1 - 0), (double)((float)(p_147801_3_ + 0) + f1), (double)(p_147801_4_ + 1), d2, d3);
                tessellator.addVertexWithUV((double)((float)(p_147801_2_ + 1) - f2), (double)((float)p_147801_3_ + f + f1), (double)(p_147801_4_ + 1), d2, d1);
                tessellator.addVertexWithUV((double)((float)(p_147801_2_ + 1) - f2), (double)((float)p_147801_3_ + f + f1), (double)(p_147801_4_ + 1), d2, d1);
                tessellator.addVertexWithUV((double)(p_147801_2_ + 1 - 0), (double)((float)(p_147801_3_ + 0) + f1), (double)(p_147801_4_ + 1), d2, d3);
                tessellator.addVertexWithUV((double)(p_147801_2_ + 1 - 0), (double)((float)(p_147801_3_ + 0) + f1), (double)(p_147801_4_ + 0), d0, d3);
                tessellator.addVertexWithUV((double)((float)(p_147801_2_ + 1) - f2), (double)((float)p_147801_3_ + f + f1), (double)(p_147801_4_ + 0), d0, d1);
            }

            if (block.canCatchFire(blockAccess, p_147801_2_, p_147801_3_, p_147801_4_ - 1, SOUTH))
            {
            	sides = true;
                tessellator.addVertexWithUV((double)(p_147801_2_ + 0), (double)((float)p_147801_3_ + f + f1), (double)((float)p_147801_4_ + f2), d2, d1);
                tessellator.addVertexWithUV((double)(p_147801_2_ + 0), (double)((float)(p_147801_3_ + 0) + f1), (double)(p_147801_4_ + 0), d2, d3);
                tessellator.addVertexWithUV((double)(p_147801_2_ + 1), (double)((float)(p_147801_3_ + 0) + f1), (double)(p_147801_4_ + 0), d0, d3);
                tessellator.addVertexWithUV((double)(p_147801_2_ + 1), (double)((float)p_147801_3_ + f + f1), (double)((float)p_147801_4_ + f2), d0, d1);
                tessellator.addVertexWithUV((double)(p_147801_2_ + 1), (double)((float)p_147801_3_ + f + f1), (double)((float)p_147801_4_ + f2), d0, d1);
                tessellator.addVertexWithUV((double)(p_147801_2_ + 1), (double)((float)(p_147801_3_ + 0) + f1), (double)(p_147801_4_ + 0), d0, d3);
                tessellator.addVertexWithUV((double)(p_147801_2_ + 0), (double)((float)(p_147801_3_ + 0) + f1), (double)(p_147801_4_ + 0), d2, d3);
                tessellator.addVertexWithUV((double)(p_147801_2_ + 0), (double)((float)p_147801_3_ + f + f1), (double)((float)p_147801_4_ + f2), d2, d1);
            }

            if (block.canCatchFire(blockAccess, p_147801_2_, p_147801_3_, p_147801_4_ + 1, NORTH))
            {
            	sides = true;
                tessellator.addVertexWithUV((double)(p_147801_2_ + 1), (double)((float)p_147801_3_ + f + f1), (double)((float)(p_147801_4_ + 1) - f2), d0, d1);
                tessellator.addVertexWithUV((double)(p_147801_2_ + 1), (double)((float)(p_147801_3_ + 0) + f1), (double)(p_147801_4_ + 1 - 0), d0, d3);
                tessellator.addVertexWithUV((double)(p_147801_2_ + 0), (double)((float)(p_147801_3_ + 0) + f1), (double)(p_147801_4_ + 1 - 0), d2, d3);
                tessellator.addVertexWithUV((double)(p_147801_2_ + 0), (double)((float)p_147801_3_ + f + f1), (double)((float)(p_147801_4_ + 1) - f2), d2, d1);
                tessellator.addVertexWithUV((double)(p_147801_2_ + 0), (double)((float)p_147801_3_ + f + f1), (double)((float)(p_147801_4_ + 1) - f2), d2, d1);
                tessellator.addVertexWithUV((double)(p_147801_2_ + 0), (double)((float)(p_147801_3_ + 0) + f1), (double)(p_147801_4_ + 1 - 0), d2, d3);
                tessellator.addVertexWithUV((double)(p_147801_2_ + 1), (double)((float)(p_147801_3_ + 0) + f1), (double)(p_147801_4_ + 1 - 0), d0, d3);
                tessellator.addVertexWithUV((double)(p_147801_2_ + 1), (double)((float)p_147801_3_ + f + f1), (double)((float)(p_147801_4_ + 1) - f2), d0, d1);
            }

            if (block.canCatchFire(blockAccess, p_147801_2_, p_147801_3_ + 1, p_147801_4_, DOWN))
            {
            	sides = true;
                d5 = (double)p_147801_2_ + 0.5D + 0.5D;
                d6 = (double)p_147801_2_ + 0.5D - 0.5D;
                d7 = (double)p_147801_4_ + 0.5D + 0.5D;
                d8 = (double)p_147801_4_ + 0.5D - 0.5D;
                d9 = (double)p_147801_2_ + 0.5D - 0.5D;
                d10 = (double)p_147801_2_ + 0.5D + 0.5D;
                d11 = (double)p_147801_4_ + 0.5D - 0.5D;
                double d12 = (double)p_147801_4_ + 0.5D + 0.5D;
                d0 = (double)iicon.getMinU();
                d1 = (double)iicon.getMinV();
                d2 = (double)iicon.getMaxU();
                d3 = (double)iicon.getMaxV();
                ++p_147801_3_;
                f = -0.2F;

                if ((p_147801_2_ + p_147801_3_ + p_147801_4_ & 1) == 0)
                {
                    tessellator.addVertexWithUV(d9, (double)((float)p_147801_3_ + f), (double)(p_147801_4_ + 0), d2, d1);
                    tessellator.addVertexWithUV(d5, (double)(p_147801_3_ + 0), (double)(p_147801_4_ + 0), d2, d3);
                    tessellator.addVertexWithUV(d5, (double)(p_147801_3_ + 0), (double)(p_147801_4_ + 1), d0, d3);
                    tessellator.addVertexWithUV(d9, (double)((float)p_147801_3_ + f), (double)(p_147801_4_ + 1), d0, d1);
                    d0 = (double)iicon1.getMinU();
                    d1 = (double)iicon1.getMinV();
                    d2 = (double)iicon1.getMaxU();
                    d3 = (double)iicon1.getMaxV();
                    tessellator.addVertexWithUV(d10, (double)((float)p_147801_3_ + f), (double)(p_147801_4_ + 1), d2, d1);
                    tessellator.addVertexWithUV(d6, (double)(p_147801_3_ + 0), (double)(p_147801_4_ + 1), d2, d3);
                    tessellator.addVertexWithUV(d6, (double)(p_147801_3_ + 0), (double)(p_147801_4_ + 0), d0, d3);
                    tessellator.addVertexWithUV(d10, (double)((float)p_147801_3_ + f), (double)(p_147801_4_ + 0), d0, d1);
                }
                else
                {
                    tessellator.addVertexWithUV((double)(p_147801_2_ + 0), (double)((float)p_147801_3_ + f), d12, d2, d1);
                    tessellator.addVertexWithUV((double)(p_147801_2_ + 0), (double)(p_147801_3_ + 0), d8, d2, d3);
                    tessellator.addVertexWithUV((double)(p_147801_2_ + 1), (double)(p_147801_3_ + 0), d8, d0, d3);
                    tessellator.addVertexWithUV((double)(p_147801_2_ + 1), (double)((float)p_147801_3_ + f), d12, d0, d1);
                    d0 = (double)iicon1.getMinU();
                    d1 = (double)iicon1.getMinV();
                    d2 = (double)iicon1.getMaxU();
                    d3 = (double)iicon1.getMaxV();
                    tessellator.addVertexWithUV((double)(p_147801_2_ + 1), (double)((float)p_147801_3_ + f), d11, d2, d1);
                    tessellator.addVertexWithUV((double)(p_147801_2_ + 1), (double)(p_147801_3_ + 0), d7, d2, d3);
                    tessellator.addVertexWithUV((double)(p_147801_2_ + 0), (double)(p_147801_3_ + 0), d7, d0, d3);
                    tessellator.addVertexWithUV((double)(p_147801_2_ + 0), (double)((float)p_147801_3_ + f), d11, d0, d1);
                }
            }
            if(!sides){
            	double d4 = (double)p_147801_2_ + 0.5D + 0.2D;
                d5 = (double)p_147801_2_ + 0.5D - 0.2D;
                d6 = (double)p_147801_4_ + 0.5D + 0.2D;
                d7 = (double)p_147801_4_ + 0.5D - 0.2D;
                d8 = (double)p_147801_2_ + 0.5D - 0.3D;
                d9 = (double)p_147801_2_ + 0.5D + 0.3D;
                d10 = (double)p_147801_4_ + 0.5D - 0.3D;
                d11 = (double)p_147801_4_ + 0.5D + 0.3D;
                tessellator.addVertexWithUV(d8, (double)((float)p_147801_3_ + f), (double)(p_147801_4_ + 1), d2, d1);
                tessellator.addVertexWithUV(d4, (double)(p_147801_3_ + 0), (double)(p_147801_4_ + 1), d2, d3);
                tessellator.addVertexWithUV(d4, (double)(p_147801_3_ + 0), (double)(p_147801_4_ + 0), d0, d3);
                tessellator.addVertexWithUV(d8, (double)((float)p_147801_3_ + f), (double)(p_147801_4_ + 0), d0, d1);
                tessellator.addVertexWithUV(d9, (double)((float)p_147801_3_ + f), (double)(p_147801_4_ + 0), d2, d1);
                tessellator.addVertexWithUV(d5, (double)(p_147801_3_ + 0), (double)(p_147801_4_ + 0), d2, d3);
                tessellator.addVertexWithUV(d5, (double)(p_147801_3_ + 0), (double)(p_147801_4_ + 1), d0, d3);
                tessellator.addVertexWithUV(d9, (double)((float)p_147801_3_ + f), (double)(p_147801_4_ + 1), d0, d1);
                d0 = (double)iicon1.getMinU();
                d1 = (double)iicon1.getMinV();
                d2 = (double)iicon1.getMaxU();
                d3 = (double)iicon1.getMaxV();
                tessellator.addVertexWithUV((double)(p_147801_2_ + 1), (double)((float)p_147801_3_ + f), d11, d2, d1);
                tessellator.addVertexWithUV((double)(p_147801_2_ + 1), (double)(p_147801_3_ + 0), d7, d2, d3);
                tessellator.addVertexWithUV((double)(p_147801_2_ + 0), (double)(p_147801_3_ + 0), d7, d0, d3);
                tessellator.addVertexWithUV((double)(p_147801_2_ + 0), (double)((float)p_147801_3_ + f), d11, d0, d1);
                tessellator.addVertexWithUV((double)(p_147801_2_ + 0), (double)((float)p_147801_3_ + f), d10, d2, d1);
                tessellator.addVertexWithUV((double)(p_147801_2_ + 0), (double)(p_147801_3_ + 0), d6, d2, d3);
                tessellator.addVertexWithUV((double)(p_147801_2_ + 1), (double)(p_147801_3_ + 0), d6, d0, d3);
                tessellator.addVertexWithUV((double)(p_147801_2_ + 1), (double)((float)p_147801_3_ + f), d10, d0, d1);
                d4 = (double)p_147801_2_ + 0.5D - 0.5D;
                d5 = (double)p_147801_2_ + 0.5D + 0.5D;
                d6 = (double)p_147801_4_ + 0.5D - 0.5D;
                d7 = (double)p_147801_4_ + 0.5D + 0.5D;
                d8 = (double)p_147801_2_ + 0.5D - 0.4D;
                d9 = (double)p_147801_2_ + 0.5D + 0.4D;
                d10 = (double)p_147801_4_ + 0.5D - 0.4D;
                d11 = (double)p_147801_4_ + 0.5D + 0.4D;
                tessellator.addVertexWithUV(d8, (double)((float)p_147801_3_ + f), (double)(p_147801_4_ + 0), d0, d1);
                tessellator.addVertexWithUV(d4, (double)(p_147801_3_ + 0), (double)(p_147801_4_ + 0), d0, d3);
                tessellator.addVertexWithUV(d4, (double)(p_147801_3_ + 0), (double)(p_147801_4_ + 1), d2, d3);
                tessellator.addVertexWithUV(d8, (double)((float)p_147801_3_ + f), (double)(p_147801_4_ + 1), d2, d1);
                tessellator.addVertexWithUV(d9, (double)((float)p_147801_3_ + f), (double)(p_147801_4_ + 1), d0, d1);
                tessellator.addVertexWithUV(d5, (double)(p_147801_3_ + 0), (double)(p_147801_4_ + 1), d0, d3);
                tessellator.addVertexWithUV(d5, (double)(p_147801_3_ + 0), (double)(p_147801_4_ + 0), d2, d3);
                tessellator.addVertexWithUV(d9, (double)((float)p_147801_3_ + f), (double)(p_147801_4_ + 0), d2, d1);
                d0 = (double)iicon.getMinU();
                d1 = (double)iicon.getMinV();
                d2 = (double)iicon.getMaxU();
                d3 = (double)iicon.getMaxV();
                tessellator.addVertexWithUV((double)(p_147801_2_ + 0), (double)((float)p_147801_3_ + f), d11, d0, d1);
                tessellator.addVertexWithUV((double)(p_147801_2_ + 0), (double)(p_147801_3_ + 0), d7, d0, d3);
                tessellator.addVertexWithUV((double)(p_147801_2_ + 1), (double)(p_147801_3_ + 0), d7, d2, d3);
                tessellator.addVertexWithUV((double)(p_147801_2_ + 1), (double)((float)p_147801_3_ + f), d11, d2, d1);
                tessellator.addVertexWithUV((double)(p_147801_2_ + 1), (double)((float)p_147801_3_ + f), d10, d0, d1);
                tessellator.addVertexWithUV((double)(p_147801_2_ + 1), (double)(p_147801_3_ + 0), d6, d0, d3);
                tessellator.addVertexWithUV((double)(p_147801_2_ + 0), (double)(p_147801_3_ + 0), d6, d2, d3);
                tessellator.addVertexWithUV((double)(p_147801_2_ + 0), (double)((float)p_147801_3_ + f), d10, d2, d1);
            }
        }
        else
        {
        	double d4 = (double)p_147801_2_ + 0.5D + 0.2D;
            d5 = (double)p_147801_2_ + 0.5D - 0.2D;
            d6 = (double)p_147801_4_ + 0.5D + 0.2D;
            d7 = (double)p_147801_4_ + 0.5D - 0.2D;
            d8 = (double)p_147801_2_ + 0.5D - 0.3D;
            d9 = (double)p_147801_2_ + 0.5D + 0.3D;
            d10 = (double)p_147801_4_ + 0.5D - 0.3D;
            d11 = (double)p_147801_4_ + 0.5D + 0.3D;
            tessellator.addVertexWithUV(d8, (double)((float)p_147801_3_ + f), (double)(p_147801_4_ + 1), d2, d1);
            tessellator.addVertexWithUV(d4, (double)(p_147801_3_ + 0), (double)(p_147801_4_ + 1), d2, d3);
            tessellator.addVertexWithUV(d4, (double)(p_147801_3_ + 0), (double)(p_147801_4_ + 0), d0, d3);
            tessellator.addVertexWithUV(d8, (double)((float)p_147801_3_ + f), (double)(p_147801_4_ + 0), d0, d1);
            tessellator.addVertexWithUV(d9, (double)((float)p_147801_3_ + f), (double)(p_147801_4_ + 0), d2, d1);
            tessellator.addVertexWithUV(d5, (double)(p_147801_3_ + 0), (double)(p_147801_4_ + 0), d2, d3);
            tessellator.addVertexWithUV(d5, (double)(p_147801_3_ + 0), (double)(p_147801_4_ + 1), d0, d3);
            tessellator.addVertexWithUV(d9, (double)((float)p_147801_3_ + f), (double)(p_147801_4_ + 1), d0, d1);
            d0 = (double)iicon1.getMinU();
            d1 = (double)iicon1.getMinV();
            d2 = (double)iicon1.getMaxU();
            d3 = (double)iicon1.getMaxV();
            tessellator.addVertexWithUV((double)(p_147801_2_ + 1), (double)((float)p_147801_3_ + f), d11, d2, d1);
            tessellator.addVertexWithUV((double)(p_147801_2_ + 1), (double)(p_147801_3_ + 0), d7, d2, d3);
            tessellator.addVertexWithUV((double)(p_147801_2_ + 0), (double)(p_147801_3_ + 0), d7, d0, d3);
            tessellator.addVertexWithUV((double)(p_147801_2_ + 0), (double)((float)p_147801_3_ + f), d11, d0, d1);
            tessellator.addVertexWithUV((double)(p_147801_2_ + 0), (double)((float)p_147801_3_ + f), d10, d2, d1);
            tessellator.addVertexWithUV((double)(p_147801_2_ + 0), (double)(p_147801_3_ + 0), d6, d2, d3);
            tessellator.addVertexWithUV((double)(p_147801_2_ + 1), (double)(p_147801_3_ + 0), d6, d0, d3);
            tessellator.addVertexWithUV((double)(p_147801_2_ + 1), (double)((float)p_147801_3_ + f), d10, d0, d1);
            d4 = (double)p_147801_2_ + 0.5D - 0.5D;
            d5 = (double)p_147801_2_ + 0.5D + 0.5D;
            d6 = (double)p_147801_4_ + 0.5D - 0.5D;
            d7 = (double)p_147801_4_ + 0.5D + 0.5D;
            d8 = (double)p_147801_2_ + 0.5D - 0.4D;
            d9 = (double)p_147801_2_ + 0.5D + 0.4D;
            d10 = (double)p_147801_4_ + 0.5D - 0.4D;
            d11 = (double)p_147801_4_ + 0.5D + 0.4D;
            tessellator.addVertexWithUV(d8, (double)((float)p_147801_3_ + f), (double)(p_147801_4_ + 0), d0, d1);
            tessellator.addVertexWithUV(d4, (double)(p_147801_3_ + 0), (double)(p_147801_4_ + 0), d0, d3);
            tessellator.addVertexWithUV(d4, (double)(p_147801_3_ + 0), (double)(p_147801_4_ + 1), d2, d3);
            tessellator.addVertexWithUV(d8, (double)((float)p_147801_3_ + f), (double)(p_147801_4_ + 1), d2, d1);
            tessellator.addVertexWithUV(d9, (double)((float)p_147801_3_ + f), (double)(p_147801_4_ + 1), d0, d1);
            tessellator.addVertexWithUV(d5, (double)(p_147801_3_ + 0), (double)(p_147801_4_ + 1), d0, d3);
            tessellator.addVertexWithUV(d5, (double)(p_147801_3_ + 0), (double)(p_147801_4_ + 0), d2, d3);
            tessellator.addVertexWithUV(d9, (double)((float)p_147801_3_ + f), (double)(p_147801_4_ + 0), d2, d1);
            d0 = (double)iicon.getMinU();
            d1 = (double)iicon.getMinV();
            d2 = (double)iicon.getMaxU();
            d3 = (double)iicon.getMaxV();
            tessellator.addVertexWithUV((double)(p_147801_2_ + 0), (double)((float)p_147801_3_ + f), d11, d0, d1);
            tessellator.addVertexWithUV((double)(p_147801_2_ + 0), (double)(p_147801_3_ + 0), d7, d0, d3);
            tessellator.addVertexWithUV((double)(p_147801_2_ + 1), (double)(p_147801_3_ + 0), d7, d2, d3);
            tessellator.addVertexWithUV((double)(p_147801_2_ + 1), (double)((float)p_147801_3_ + f), d11, d2, d1);
            tessellator.addVertexWithUV((double)(p_147801_2_ + 1), (double)((float)p_147801_3_ + f), d10, d0, d1);
            tessellator.addVertexWithUV((double)(p_147801_2_ + 1), (double)(p_147801_3_ + 0), d6, d0, d3);
            tessellator.addVertexWithUV((double)(p_147801_2_ + 0), (double)(p_147801_3_ + 0), d6, d2, d3);
            tessellator.addVertexWithUV((double)(p_147801_2_ + 0), (double)((float)p_147801_3_ + f), d10, d2, d1);
        }

        return true;
    }

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		// TODO Auto-generated method stub
		return render3Dinventory;
	}

	@Override
	public int getRenderId() {
		// TODO Auto-generated method stub
		return modelID;
	}

}

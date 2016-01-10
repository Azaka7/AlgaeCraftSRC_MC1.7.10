package azaka7.algaecraft.client;

import java.lang.reflect.Array;
import java.util.ArrayList;

import azaka7.algaecraft.common.ACGameData;
import azaka7.algaecraft.common.blocks.BlockCoral;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderBlockSimpleHandler implements ISimpleBlockRenderingHandler {

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
		if(modelID == ACGameData.coralModelID){
			return renderCoral(renderer, world,  x, y, z, block);
		}
		if(modelID == ACGameData.spongeModelID){
			return renderSponge(renderer, world, x, y, z, block);
		}
		if(modelID == ACGameData.seaweedModelID){
			return renderSeaweed(renderer, world, x, y, z, block);
		}
		/*if(modelID == ACGameData.waterBlockModelID){
			return  renderWaterOnBasicBlock(renderer,world,x,y,z,block);
		}*/
		return false;
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

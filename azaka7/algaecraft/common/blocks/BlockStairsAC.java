package azaka7.algaecraft.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;

public class BlockStairsAC extends BlockStairs{

	protected BlockStairsAC(Block p_i45428_1_, int p_i45428_2_) {
		super(p_i45428_1_, p_i45428_2_);
		this.useNeighborBrightness = true;
	}

}

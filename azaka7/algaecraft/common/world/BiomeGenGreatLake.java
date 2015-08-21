package azaka7.algaecraft.common.world;

import java.util.Random;

import net.minecraft.block.BlockFlower;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class BiomeGenGreatLake extends BiomeGenBase {
	
	public BiomeGenGreatLake(int par1, boolean b1, boolean b2) {
		super(par1, b1);
		this.theBiomeDecorator.treesPerChunk = 8;
        this.theBiomeDecorator.grassPerChunk = 1;
        this.theBiomeDecorator.flowersPerChunk=1;
		if(b2){
			this.flowers.clear();
            for (int x = 0; x < BlockFlower.field_149859_a.length; x++)
            {
                this.addFlower(Blocks.red_flower, x == 1 ? 0 : x, 10);
            }
            this.theBiomeDecorator.flowersPerChunk=75;
		}
	}
	
	public WorldGenAbstractTree func_150567_a(Random p_150567_1_)
    {
		return BiomeGenBase.taiga.func_150567_a(p_150567_1_);
    }

}

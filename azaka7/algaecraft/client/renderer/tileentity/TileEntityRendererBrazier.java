package azaka7.algaecraft.client.renderer.tileentity;

import org.lwjgl.opengl.GL11;

import azaka7.algaecraft.AlgaeCraft;
import azaka7.algaecraft.client.model.ModelBrazier;
import azaka7.algaecraft.client.model.ModelBrazier0;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TileEntityRendererBrazier extends TileEntitySpecialRenderer{
	
	private static final ResourceLocation mainTexture = new ResourceLocation(AlgaeCraft.MODID+":textures/entities/brazier.png");
	private static final ModelBase model = new ModelBrazier0();
	
	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float s) {
		
		GL11.glPushMatrix();
		this.bindTexture(mainTexture);
		GL11.glTranslatef((float)x+0.5F, (float)y+1.5F, (float)z+0.5F);
		GL11.glRotatef(180, 0.0F, 0.0F, 1.0F);
		model.render(null, tile.getBlockMetadata() % 2, 0, 0, 0, 0, 0.0625F);
		GL11.glPopMatrix();
		
	}

}

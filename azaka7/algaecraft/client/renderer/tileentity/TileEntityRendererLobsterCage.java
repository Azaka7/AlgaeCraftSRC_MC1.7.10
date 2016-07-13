package azaka7.algaecraft.client.renderer.tileentity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import azaka7.algaecraft.AlgaeCraft;
import azaka7.algaecraft.client.model.ModelLobster;
import azaka7.algaecraft.client.model.ModelLobsterCage;
import azaka7.algaecraft.common.entity.EntityLobster;
import azaka7.algaecraft.common.tileentity.TileEntityCage;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntityRendererLobsterCage extends TileEntitySpecialRenderer {
	
	private ModelBase model;
	private final ModelLobster lobsterModel = new ModelLobster();
	private final ModelLobsterCage modelCage = new ModelLobsterCage();
	private static final ResourceLocation mainTexture = new ResourceLocation(AlgaeCraft.MODID+":textures/blocks/LobsterCageBlock.png");

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d0, double d1,
			double d2, float f) {
		
		if(tileentity instanceof TileEntityCage){
			this.renderContained((TileEntityCage) tileentity, d0, d1, d2, f);
		}
		
	}
	
	public void renderContained(TileEntityCage tileentity, double d0, double d1, double d2, float f){
		GL11.glPushMatrix();
		this.bindTexture(mainTexture);
		GL11.glTranslatef((float)d0+0.5F, (float)d1-0.5F, (float)d2+0.5F);
		modelCage.render(null, 0, 0, 0, 0, 0, 0.0625F);
		GL11.glPopMatrix();
		
		tileentity.blockMetadata = -1;
		if(tileentity.getBlockMetadata() == 0){return;}
		
		if(tileentity.getBlockMetadata() == 1 || tileentity.getBlockMetadata() == 2){
			
			if(this.model != lobsterModel){
			this.model = lobsterModel;
			}
			GL11.glPushMatrix();
			this.bindTexture(tileentity.getBlockMetadata() == 1 ? EntityLobster.mainTexture : EntityLobster.blueTexture);
			GL11.glDisable(GL11.GL_CULL_FACE);
			GL11.glTranslatef((float)d0+0.5F, (float)d1+1.66666F, (float)d2+0.5F);
			GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(this.getAngleFromPosition(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord), 0.0F, 1.0F, 0.0F);
			model.render(null, 0, 0, 0, 0, 0, 0.0625F);
			GL11.glPopMatrix();
			
		}
	}
	
	public float getAngleFromPosition(double x, double y, double z){
		
		return (float) (180*Math.abs(Math.sin((3*x+z)/4))+180*Math.abs(Math.cos((3*z+x)/4))+360*Math.sin((3*y+x+z)/5));
	}

}

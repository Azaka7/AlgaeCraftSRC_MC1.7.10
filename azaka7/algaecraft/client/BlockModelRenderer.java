package azaka7.algaecraft.client;

import org.lwjgl.opengl.GL11;

import azaka7.algaecraft.common.entity.EntityLobster;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class BlockModelRenderer {
	
	private final Entity entity;
	private final ModelBase model;
	private final ResourceLocation mainTexture;
	private float f0, f1, f2, f3, f4, f5;
	
	public BlockModelRenderer(ModelBase m, ResourceLocation texture){
		this(null, m, texture, 1.0F);
	}
	
	public BlockModelRenderer(Entity e,ModelBase m, ResourceLocation texture, float scale){
		entity = e;
		model = m;
		mainTexture = texture;
		f0=f1=f2=f3=f4=0F;
		f5 = scale;
	}
	
	/**
	 * Sets the float arguments used in ModelBase.render()
	 * No more that 6 arguments are processed.
	 * @param floats
	 * @return returns this instance with redefined floats.
	 */
	public BlockModelRenderer setArguments(float... floats){
		for(int i = 0; i < 6 && i < floats.length; i++){
			switch(i){
			case 0: f0 = floats[i]; break;
			case 1: f1 = floats[i]; break;
			case 2: f2 = floats[i]; break;
			case 3: f3 = floats[i]; break;
			case 4: f4 = floats[i]; break;
			case 5: f5 = floats[i]; break;
			}
		}
		return this;
	}
	
	public void renderAtPos(double x, double y, double z){
		GL11.glPushMatrix();
		Minecraft.getMinecraft().getTextureManager().bindTexture(mainTexture);
		GL11.glTranslatef((float)x+0.5F, (float)y-0.5F, (float)z+0.5F);
		model.render(entity, f0, f1, f2, f3, f4, f5);
		GL11.glPopMatrix();
	}
	
}

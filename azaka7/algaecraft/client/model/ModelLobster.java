package azaka7.algaecraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

//Date: 4/10/2013 9:54:56 PM
//Template version 1.1
//Java generated by Techne
//Keep in mind that you still need to fill in some blanks
//- ZeuX

public class ModelLobster extends ModelBase
{
//fields
 ModelRenderer Shape1;
 ModelRenderer Shape2;
 ModelRenderer Shape3;
 ModelRenderer Shape4;
 ModelRenderer Shape6;
 ModelRenderer Shape7;
 ModelRenderer Shape8;
 ModelRenderer Shape9;
 ModelRenderer Shape10;
 ModelRenderer Shape11;
 ModelRenderer Shape5;
 ModelRenderer Shape51;
 ModelRenderer Shape52;
 ModelRenderer Shape53;
 ModelRenderer Shape50;
 ModelRenderer Shape501;
 ModelRenderer Shape502;
 ModelRenderer Shape503;

public ModelLobster()
{
 textureWidth = 256;
 textureHeight = 256;
 
   Shape1 = new ModelRenderer(this, 0, 8);
   Shape1.addBox(0F, 0F, 0F, 2, 2, 2);
   Shape1.setRotationPoint(-1F, 21F, -6F);
   Shape1.setTextureSize(256, 256);
   Shape1.mirror = true;
   setRotation(Shape1, 0F, 0F, 0F);
   Shape2 = new ModelRenderer(this, 0, 0);
   Shape2.addBox(0F, 0F, 0F, 4, 2, 6);
   Shape2.setRotationPoint(-2F, 21F, -4F);
   Shape2.setTextureSize(256, 256);
   Shape2.mirror = true;
   setRotation(Shape2, 0F, 0F, 0F);
   Shape3 = new ModelRenderer(this, 20, 0);
   Shape3.addBox(0F, 0F, 0F, 2, 1, 4);
   Shape3.setRotationPoint(-1F, 22F, 2F);
   Shape3.setTextureSize(256, 256);
   Shape3.mirror = true;
   setRotation(Shape3, -0.2602503F, 0F, 0F);
   Shape4 = new ModelRenderer(this, 32, 0);
   Shape4.addBox(0F, 0F, 0F, 4, 1, 2);
   Shape4.setRotationPoint(-2F, 23F, 5F);
   Shape4.setTextureSize(256, 256);
   Shape4.mirror = true;
   setRotation(Shape4, 0F, 0F, 0F);
   Shape6 = new ModelRenderer(this, 8, 8);
   Shape6.addBox(-2F, 0F, 0F, 2, 1, 6);
   Shape6.setRotationPoint(1F, 22F, -1F);
   Shape6.setTextureSize(256, 256);
   Shape6.mirror = true;
   setRotation(Shape6, 0.1570796F, 2.862759F, 0F);
   Shape7 = new ModelRenderer(this, 8, 8);
   Shape7.addBox(0F, 0F, 0F, 2, 1, 6);
   Shape7.setRotationPoint(-1F, 22F, -1F);
   Shape7.setTextureSize(256, 256);
   Shape7.mirror = true;
   setRotation(Shape7, 0.1570796F, -2.862759F, 0F);
   Shape8 = new ModelRenderer(this, 44, 0);
   Shape8.addBox(-1F, 0F, 0F, 1, 0, 13);
   Shape8.setRotationPoint(-1F, 21F, -4F);
   Shape8.setTextureSize(256, 256);
   Shape8.mirror = true;
   setRotation(Shape8, 0.0371786F, 0F, 0F);
   Shape9 = new ModelRenderer(this, 43, 0);
   Shape9.addBox(0F, 0F, 0F, 1, 0, 13);
   Shape9.setRotationPoint(1F, 21F, -4F);
   Shape9.setTextureSize(256, 256);
   Shape9.mirror = true;
   setRotation(Shape9, 0.0371755F, 0F, 0F);
   Shape10 = new ModelRenderer(this, 24, 6);
   Shape10.addBox(-3F, 0F, -5F, 3, 1, 5);
   Shape10.setRotationPoint(-3F, 21F, -5F);
   Shape10.setTextureSize(256, 256);
   Shape10.mirror = true;
   setRotation(Shape10, 0F, 0F, 0F);
   Shape11 = new ModelRenderer(this, 24, 6);
   Shape11.addBox(0F, 0F, -5F, 3, 1, 5);
   Shape11.setRotationPoint(3F, 21F, -5F);
   Shape11.setTextureSize(256, 256);
   Shape11.mirror = true;
   setRotation(Shape11, 0F, 0F, 0F);
   Shape5 = new ModelRenderer(this, 6, 0);
   Shape5.addBox(-3F, 0F, 0F, 3, 1, 1);
   Shape5.setRotationPoint(-2F, 22F, 1F);
   Shape5.setTextureSize(256, 256);
   Shape5.mirror = true;
   setRotation(Shape5, 0F, 0.5576792F, -0.5205006F);
   Shape51 = new ModelRenderer(this, 6, 0);
   Shape51.addBox(-3F, 0F, 0F, 3, 1, 1);
   Shape51.setRotationPoint(-2F, 22F, -1F);
   Shape51.setTextureSize(256, 256);
   Shape51.mirror = true;
   setRotation(Shape51, 0F, 0.1858931F, -0.5204921F);
   Shape52 = new ModelRenderer(this, 6, 0);
   Shape52.addBox(-3F, 0F, 0F, 3, 1, 1);
   Shape52.setRotationPoint(-2F, 22F, -2F);
   Shape52.setTextureSize(256, 256);
   Shape52.mirror = true;
   setRotation(Shape52, 0F, -0.185895F, -0.5204921F);
   Shape53 = new ModelRenderer(this, 6, 0);
   Shape53.addBox(-3F, 0F, 0F, 3, 1, 1);
   Shape53.setRotationPoint(-1F, 22F, -4F);
   Shape53.setTextureSize(256, 256);
   Shape53.mirror = true;
   setRotation(Shape53, 0F, -0.5576851F, -0.5205006F);
   Shape50 = new ModelRenderer(this, 6, 0);
   Shape50.addBox(0F, 0F, 0F, 3, 1, 1);
   Shape50.setRotationPoint(1F, 22F, -4F);
   Shape50.setTextureSize(256, 256);
   Shape50.mirror = true;
   setRotation(Shape50, 0F, 0.5576792F, 0.5204921F);
   Shape501 = new ModelRenderer(this, 6, 0);
   Shape501.addBox(0F, 0F, 0F, 3, 1, 1);
   Shape501.setRotationPoint(2F, 22F, -2F);
   Shape501.setTextureSize(256, 256);
   Shape501.mirror = true;
   setRotation(Shape501, 0F, 0.185895F, 0.5204921F);
   Shape502 = new ModelRenderer(this, 6, 0);
   Shape502.addBox(0F, 0F, 0F, 3, 1, 1);
   Shape502.setRotationPoint(2F, 22F, -1F);
   Shape502.setTextureSize(256, 256);
   Shape502.mirror = true;
   setRotation(Shape502, 0F, -0.185895F, 0.5204921F);
   Shape503 = new ModelRenderer(this, 6, 0);
   Shape503.addBox(0F, 0F, 0F, 3, 1, 1);
   Shape503.setRotationPoint(2F, 22F, 1F);
   Shape503.setTextureSize(256, 256);
   Shape503.mirror = true;
   setRotation(Shape503, 0F, -0.5576851F, 0.5204921F);
}

public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
{
 this.renderWithoutEntity(f, f1, f2, f3, f4, f5);
}

public void renderWithoutEntity(float f, float f1, float f2, float f3, float f4, float f5){
	 this.setRotationAngles(f, f1, f2, f3, f4, f5, null);
	 Shape1.render(f5);
	 Shape2.render(f5);
	 Shape3.render(f5);
	 Shape4.render(f5);
	 Shape6.render(f5);
	 Shape7.render(f5);
	 Shape8.render(f5);
	 Shape9.render(f5);
	 Shape10.render(f5);
	 Shape11.render(f5);
	 Shape5.render(f5);
	 Shape51.render(f5);
	 Shape52.render(f5);
	 Shape53.render(f5);
	 Shape50.render(f5);
	 Shape501.render(f5);
	 Shape502.render(f5);
	 Shape503.render(f5);
}

private void setRotation(ModelRenderer model, float x, float y, float z)
{
 model.rotateAngleX = x;
 model.rotateAngleY = y;
 model.rotateAngleZ = z;
}

public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e)
{
 super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
}

}
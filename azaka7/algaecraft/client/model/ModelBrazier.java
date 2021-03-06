// Date: 2/27/2016 8:02:29 PM
// Template version 1.1
// Java generated by Techne
package azaka7.algaecraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBrazier extends ModelBase
{
  //fields
    ModelRenderer Shape1;
    ModelRenderer Shape2;
    ModelRenderer Shape3;
    ModelRenderer Shape4;
    ModelRenderer Shape5;
    ModelRenderer Shape6;
    ModelRenderer Shape7;
    ModelRenderer Shape8;
    ModelRenderer Shape9;
    ModelRenderer Shape10;
    ModelRenderer Shape11;
    ModelRenderer Shape12;
    ModelRenderer Shape13;
  
  public ModelBrazier()
  {
    textureWidth = 64;
    textureHeight = 64;
    
      Shape1 = new ModelRenderer(this, 0, 0);
      Shape1.addBox(0F, 0F, 0F, 6, 2, 6);
      Shape1.setRotationPoint(-3F, 22F, -3F);
      Shape1.setTextureSize(64, 64);
      Shape1.mirror = true;
      setRotation(Shape1, 0F, 0F, 0F);
      Shape2 = new ModelRenderer(this, 0, 8);
      Shape2.addBox(0F, 0F, 0F, 4, 1, 4);
      Shape2.setRotationPoint(-2F, 21F, -2F);
      Shape2.setTextureSize(64, 64);
      Shape2.mirror = true;
      setRotation(Shape2, 0F, 0F, 0F);
      Shape3 = new ModelRenderer(this, 56, 0);
      Shape3.addBox(0F, 0F, 0F, 2, 23, 2);
      Shape3.setRotationPoint(-1F, -2F, -1F);
      Shape3.setTextureSize(64, 64);
      Shape3.mirror = true;
      setRotation(Shape3, 0F, 0F, 0F);
      Shape4 = new ModelRenderer(this, 0, 13);
      Shape4.addBox(0F, 0F, 0F, 4, 1, 4);
      Shape4.setRotationPoint(-2F, -3F, -2F);
      Shape4.setTextureSize(64, 64);
      Shape4.mirror = true;
      setRotation(Shape4, 0F, 0F, 0F);
      Shape5 = new ModelRenderer(this, 48, 0);
      Shape5.addBox(0F, 0F, 0F, 1, 6, 1);
      Shape5.setRotationPoint(2F, -6F, -0.5F);
      Shape5.setTextureSize(64, 64);
      Shape5.mirror = true;
      setRotation(Shape5, 0F, 0F, 0F);
      Shape6 = new ModelRenderer(this, 52, 0);
      Shape6.addBox(0F, 0F, 0F, 1, 8, 1);
      Shape6.setRotationPoint(1.5F, -8.5F, 1.5F);
      Shape6.setTextureSize(64, 64);
      Shape6.mirror = true;
      setRotation(Shape6, 0F, 0F, 0F);
      Shape7 = new ModelRenderer(this, 48, 0);
      Shape7.addBox(0F, 0F, 0F, 1, 6, 1);
      Shape7.setRotationPoint(-0.5F, -6F, 2F);
      Shape7.setTextureSize(64, 64);
      Shape7.mirror = true;
      setRotation(Shape7, 0F, 0F, 0F);
      Shape8 = new ModelRenderer(this, 52, 0);
      Shape8.addBox(0F, 0F, 0F, 1, 8, 1);
      Shape8.setRotationPoint(-2.5F, -8.5F, 1.5F);
      Shape8.setTextureSize(64, 64);
      Shape8.mirror = true;
      setRotation(Shape8, 0F, 0F, 0F);
      Shape9 = new ModelRenderer(this, 48, 0);
      Shape9.addBox(0F, 0F, 0F, 1, 6, 1);
      Shape9.setRotationPoint(-3F, -6F, -0.5F);
      Shape9.setTextureSize(64, 64);
      Shape9.mirror = true;
      setRotation(Shape9, 0F, 0F, 0F);
      Shape10 = new ModelRenderer(this, 52, 0);
      Shape10.addBox(0F, 0F, 0F, 1, 8, 1);
      Shape10.setRotationPoint(-2.5F, -8.5F, -2.5F);
      Shape10.setTextureSize(64, 64);
      Shape10.mirror = true;
      setRotation(Shape10, 0F, 0F, 0F);
      Shape11 = new ModelRenderer(this, 48, 0);
      Shape11.addBox(0F, 0F, 0F, 1, 6, 1);
      Shape11.setRotationPoint(-0.5F, -6F, -3F);
      Shape11.setTextureSize(64, 64);
      Shape11.mirror = true;
      setRotation(Shape11, 0F, 0F, 0F);
      Shape12 = new ModelRenderer(this, 52, 0);
      Shape12.addBox(0F, 0F, 0F, 1, 8, 1);
      Shape12.setRotationPoint(1.5F, -8.5F, -2.5F);
      Shape12.setTextureSize(64, 64);
      Shape12.mirror = true;
      setRotation(Shape12, 0F, 0F, 0F);
      Shape13 = new ModelRenderer(this, 0, 60);
      Shape13.addBox(0F, 0F, 0F, 2, 2, 2);
      Shape13.setRotationPoint(-1F, -5.5F, -1F);
      Shape13.setTextureSize(64, 64);
      Shape13.mirror = true;
      setRotation(Shape13, 0F, 0F, 0F);
  }
  public void render(float f5)
  {
	  this.render(null, 0, 0, 0, 0, 0, f5);
  }
  
  @Override
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    Shape1.render(f5);
    Shape2.render(f5);
    Shape3.render(f5);
    Shape4.render(f5);
    Shape5.render(f5);
    Shape6.render(f5);
    Shape7.render(f5);
    Shape8.render(f5);
    Shape9.render(f5);
    Shape10.render(f5);
    Shape11.render(f5);
    Shape12.render(f5);
    Shape13.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }

}

package azaka7.algaecraft.client.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelFlippers extends ModelBiped
{
    ModelRenderer flipperLeft;
    ModelRenderer flipperRight;
  
  public ModelFlippers()
  {
	  super(0F);
	  ini(0F);
  }
  
  public ModelFlippers(float f) {
	super(f);
	ini(f);
}
  
  public void ini(float f){
	  
	    textureWidth = 64;
	    textureHeight = 32;
	    
		bipedHeadwear.cubeList.clear();
	    
	      flipperLeft = new ModelRenderer(this, 44, 0);
	      flipperLeft.addBox(0F, 0F, 0F, 4, 1, 6, f);
	      flipperLeft.setRotationPoint(-2F, 11F, -6F);
	      flipperLeft.setTextureSize(64, 32);
	      flipperLeft.mirror = true;
	      setRotation(flipperLeft, 0F, 0F, 0F);
	      
	      flipperRight = new ModelRenderer(this, 44, 0);
	      flipperRight.addBox(0F, 0F, 0F, 4, 1, 6,f);
	      flipperRight.setRotationPoint(-2F, 11F, -6F);
	      flipperRight.setTextureSize(64, 32);
	      flipperRight.mirror = true;
	      setRotation(flipperRight, 0F, 0F, 0F);

	      super.bipedLeftLeg.addChild(flipperLeft);
	      super.bipedRightLeg.addChild(flipperRight);
	      
  }

public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }

}
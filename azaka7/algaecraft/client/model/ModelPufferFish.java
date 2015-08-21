package azaka7.algaecraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelPufferFish extends ModelBase
{
  //fields
    ModelRenderer body;//body
    ModelRenderer afin;//fin
    ModelRenderer bfin;//other fin
    ModelRenderer tail;//tail fin
    ModelRenderer spikes;//spikes
  
  public ModelPufferFish()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      body = new ModelRenderer(this, 0, 0);
      body.addBox(0F, 0F, 0F, 8, 8, 8);
      body.setRotationPoint(-4F, 16F, -4F);
      body.setTextureSize(64, 32);
      body.mirror = true;
      setRotation(body, 0F, 0F, 0F);
      afin = new ModelRenderer(this, 32, 0);
      afin.addBox(0F, 0F, 0F, 2, 3, 0);
      afin.setRotationPoint(-4F, 19F, -1F);
      afin.setTextureSize(64, 32);
      afin.mirror = true;
      setRotation(afin, 0F, -1.832596F, 0F);
      bfin = new ModelRenderer(this, 32, 0);
      bfin.addBox(0F, 0F, 0F, 2, 3, 0);
      bfin.setRotationPoint(4F, 19F, 0F);
      bfin.setTextureSize(64, 32);
      bfin.mirror = true;
      setRotation(bfin, 0F, -1.308997F, 0F);
      tail = new ModelRenderer(this, 0, 16);
      tail.addBox(0F, 0F, 0F, 0, 6, 4);
      tail.setRotationPoint(0F, 17F, 3F);
      tail.setTextureSize(64, 32);
      tail.mirror = true;
      setRotation(tail, 0F, 0F, 0F);
      spikes = new ModelRenderer(this, 28, 14);
      spikes.addBox(0F, 0F, 0F, 9, 9, 9);
      spikes.setRotationPoint(-4.5F, 15.5F, -4.5F);
      spikes.setTextureSize(64, 32);
      spikes.mirror = true;
      setRotation(spikes, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5);
    body.render(f5);
    afin.render(f5);
    bfin.render(f5);
    tail.render(f5);
    spikes.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, null);
  }

}

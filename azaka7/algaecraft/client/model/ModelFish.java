package azaka7.algaecraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelFish extends ModelBase
{
  //fields
    ModelRenderer Body_Tail_End1;
    ModelRenderer Mouth_Bottom;
    ModelRenderer Mouth_Top;
    ModelRenderer Top_Dorsal_Fin;
    ModelRenderer Top_Tail_fin;
    ModelRenderer Left_Side_Fin;
    ModelRenderer Right_Side_Fin;
    
    //Some constant k, giving ratio of rotation units to degrees
    float k = (-1.570796F)/(-90F);
  
  public ModelFish()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      Body_Tail_End1 = new ModelRenderer(this, 0, 0);
      Body_Tail_End1.addBox(0F, 0F, 0F, 4, 2, 1);
      Body_Tail_End1.setRotationPoint(0F, 18+0F, 0F);
      Body_Tail_End1.setTextureSize(64, 32);
      Body_Tail_End1.mirror = true;
      setRotation(Body_Tail_End1, 0F, -1.570796F, 0F);
      Mouth_Bottom = new ModelRenderer(this, 11, 0);
      Mouth_Bottom.addBox(0F, 0F, 0F, 1, 1, 1);
      Mouth_Bottom.setRotationPoint(-1F, 18+0.70F, -0.1F);
      Mouth_Bottom.setTextureSize(64, 32);
      Mouth_Bottom.mirror = true;
      setRotation(Mouth_Bottom, 0F-40*k, /*-1.570796F*/0F, /*0.5061455F*/0F);
      Mouth_Top = new ModelRenderer(this, 16, 0);
      Mouth_Top.addBox(0F, 0F, 0F, 1, 1, 1);
      Mouth_Top.setRotationPoint(-1F, 18+0.15F, -0.1F);
      Mouth_Top.setTextureSize(64, 32);
      Mouth_Top.mirror = true;
      setRotation(Mouth_Top, /*-0.3665191F*/-50*k, 0F/*-1.570796F*/, 0F);
      Top_Dorsal_Fin = new ModelRenderer(this, 0, 4);
      Top_Dorsal_Fin.addBox(0F, 0F, 0F, 2, 1, 0);
      Top_Dorsal_Fin.setRotationPoint(-0.5F, 18+0F, 0.9F);
      Top_Dorsal_Fin.setTextureSize(64, 32);
      Top_Dorsal_Fin.mirror = false;
      setRotation(Top_Dorsal_Fin, 0F, 0F, 0F);//setRotation(Top_Dorsal_Fin, 0.1111F, 0.1111F, 0.1111F);//-0.2617994
      this.setRotation(Top_Dorsal_Fin, k*0F, k*-90F, k*0F);
      Top_Tail_fin = new ModelRenderer(this, 0, 6);
      Top_Tail_fin.addBox(0F, 0F, 0F, 2, 3, 0);
      Top_Tail_fin.setRotationPoint(-0.5F, 18-0.5F, 3.5F);
      Top_Tail_fin.setTextureSize(64, 32);
      Top_Tail_fin.mirror = true;
      setRotation(Top_Tail_fin, 0F, -1.570796F, 0F);
      Left_Side_Fin = new ModelRenderer(this, 0, 10);
      Left_Side_Fin.addBox(0F, 0F, 0F, 1, 1, 0);
      Left_Side_Fin.setRotationPoint(0F, 18+1F, 1F);
      Left_Side_Fin.setTextureSize(64, 32);
      Left_Side_Fin.mirror = true;
      setRotation(Left_Side_Fin, 0F, -1.413717F, 0F);
      Right_Side_Fin = new ModelRenderer(this, 0, 12);
      Right_Side_Fin.addBox(0F, 0F, 0F, 1, 1, 0);
      Right_Side_Fin.setRotationPoint(-1F, 18+1F, 1F);
      Right_Side_Fin.setTextureSize(64, 32);
      Right_Side_Fin.mirror = true;
      setRotation(Right_Side_Fin, 0F, -1.727876F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5,entity);
    Top_Dorsal_Fin.offsetY=-0.03F;
    Body_Tail_End1.render(f5);
    Mouth_Bottom.render(f5);
    Mouth_Top.render(f5);
    Top_Dorsal_Fin.render(f5);
    Top_Tail_fin.render(f5);
    Left_Side_Fin.render(f5);
    Right_Side_Fin.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    //
  }

}

/*package azaka7.algaecraft.common.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelFish extends ModelBase
{
  //fields
    ModelRenderer Body_Tail_End1;
    ModelRenderer Mouth_Bottom;
    ModelRenderer Mouth_Top;
    ModelRenderer Top_Dorsal_Fin;
    ModelRenderer Top_Tail_fin;
    ModelRenderer Left_Side_Fin;
    ModelRenderer Right_Side_Fin;
  
  public ModelFish()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      Body_Tail_End1 = new ModelRenderer(this, 0, 0);
      Body_Tail_End1.addBox(0F, 0F, 0F, 4, 2, 1);
      Body_Tail_End1.setRotationPoint(0F, 18+0F, 0F);
      Body_Tail_End1.setTextureSize(64, 32);
      Body_Tail_End1.mirror = true;
      setRotation(Body_Tail_End1, 0F, 0F, 0F);
      Mouth_Bottom = new ModelRenderer(this, 11, 0);
      Mouth_Bottom.addBox(0F, 0F, 0F, 1, 1, 1);
      Mouth_Bottom.setRotationPoint(-0.4F, 18+0.7F, 0F);
      Mouth_Bottom.setTextureSize(64, 32);
      Mouth_Bottom.mirror = true;
      setRotation(Mouth_Bottom, 0F, 0F, 0.5061455F);
      Mouth_Top = new ModelRenderer(this, 16, 0);
      Mouth_Top.addBox(0F, 0F, 0F, 1, 1, 1);
      Mouth_Top.setRotationPoint(-0.8F, 18+0.3F, 0F);
      Mouth_Top.setTextureSize(64, 32);
      Mouth_Top.mirror = true;
      setRotation(Mouth_Top, 0F, 0F, -0.3665191F);
      Top_Dorsal_Fin = new ModelRenderer(this, 0, 4);
      Top_Dorsal_Fin.addBox(0F, 0F, 0F, 2, 1, 0);
      Top_Dorsal_Fin.setRotationPoint(0.9F, 18+0F, 0.5F);
      Top_Dorsal_Fin.setTextureSize(64, 32);
      Top_Dorsal_Fin.mirror = true;
      setRotation(Top_Dorsal_Fin, 0F, 0F, -0.2617994F);
      Top_Tail_fin = new ModelRenderer(this, 0, 6);
      Top_Tail_fin.addBox(0F, 0F, 0F, 2, 3, 0);
      Top_Tail_fin.setRotationPoint(3.5F, 18-0.5F, 0.5F);
      Top_Tail_fin.setTextureSize(64, 32);
      Top_Tail_fin.mirror = true;
      setRotation(Top_Tail_fin, 0F, 0F, 0F);
      Left_Side_Fin = new ModelRenderer(this, 0, 10);
      Left_Side_Fin.addBox(0F, 0F, 0F, 1, 1, 0);
      Left_Side_Fin.setRotationPoint(1F, 18+1F, 0F);
      Left_Side_Fin.setTextureSize(64, 32);
      Left_Side_Fin.mirror = true;
      setRotation(Left_Side_Fin, 0F, 0.1570796F, 0F);
      Right_Side_Fin = new ModelRenderer(this, 0, 12);
      Right_Side_Fin.addBox(0F, 0F, 0F, 1, 1, 0);
      Right_Side_Fin.setRotationPoint(1F, 18+1F, 1F);
      Right_Side_Fin.setTextureSize(64, 32);
      Right_Side_Fin.mirror = true;
      setRotation(Right_Side_Fin, 0F, -0.1570796F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    entity.rotationYaw += (90);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    Body_Tail_End1.render(f5);
    Mouth_Bottom.render(f5);
    Mouth_Top.render(f5);
    Top_Dorsal_Fin.render(f5);
    Top_Tail_fin.render(f5);
    Left_Side_Fin.render(f5);
    Right_Side_Fin.render(f5);
    entity.rotationYaw -= (90);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
  }

}
*/
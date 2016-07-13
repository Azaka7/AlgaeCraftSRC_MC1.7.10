package azaka7.algaecraft.client.renderer.entity;

import azaka7.algaecraft.client.model.ModelLobster;
import azaka7.algaecraft.common.entity.EntityLobster;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderLobster extends RenderLiving
{
    public RenderLobster(ModelLobster model, float f)
    {
        super(model, f);
    }

    /**
     * Return the Lobster's maximum death rotation.
     */
    protected float getLobsterDeathRotation(EntityLobster par1EntityLobster)
    {
        return 180.0F;
    }

    /**
     * Renders the Lobster.
     */
    public void renderLobster(EntityLobster par1EntityLobster, double par2, double par4, double par6, float par8, float par9)
    {
        super.doRender(par1EntityLobster, par2, par4, par6, par8, par9);
    }

    /**
     * Disallows the Lobster to render the renderPassModel.
     */
    protected int shouldLobsterRenderPass(EntityLobster par1EntityLobster, int par2, float par3)
    {
        return -1;
    }

    protected float getDeathMaxRotation(EntityLiving par1EntityLiving)
    {
        return this.getLobsterDeathRotation((EntityLobster)par1EntityLiving);
    }

    /**
     * Queries whether should render the specified pass or not.
     */
    protected int shouldRenderPass(EntityLiving par1EntityLiving, int par2, float par3)
    {
        return this.shouldLobsterRenderPass((EntityLobster)par1EntityLiving, par2, par3);
    }

    public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
    {
        this.renderLobster((EntityLobster)par1EntityLiving, par2, par4, par6, par8, par9);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        this.renderLobster((EntityLobster)par1Entity, par2, par4, par6, par8, par9);
    }

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return ((EntityLobster) entity).isBlue() ? EntityLobster.blueTexture : EntityLobster.mainTexture;
	}
}
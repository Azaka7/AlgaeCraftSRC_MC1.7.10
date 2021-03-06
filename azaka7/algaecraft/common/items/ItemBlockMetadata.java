package azaka7.algaecraft.common.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemBlockMetadata extends ItemBlock
{
    protected final Block field_150941_b;
    protected final String[] field_150942_c;
    private static final String __OBFID = "CL_00000051";

    public ItemBlockMetadata(Block p_i45346_1_, String[] p_i45346_3_)
    {
        super(p_i45346_1_);
        this.field_150941_b = p_i45346_1_;
        this.field_150942_c = p_i45346_3_;
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    /**
     * Gets an icon index based on an item's damage value
     */
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int par1)
    {
        return this.field_150941_b.getIcon(2, par1);
    }

    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
    public int getMetadata(int par1)
    {
        return par1;
    }

    /**
     * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
     * different names based on their damage or NBT.
     */
    public String getUnlocalizedName(ItemStack par1ItemStack)
    {
        int i = par1ItemStack.getItemDamage();

        if (i < 0 || i >= this.field_150942_c.length)
        {
            i = 0;
        }

        return super.getUnlocalizedName() + "." + this.field_150942_c[i];
    }
}
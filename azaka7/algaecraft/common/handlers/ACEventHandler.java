package azaka7.algaecraft.common.handlers;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.IIcon;
import net.minecraft.world.Explosion;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.brewing.PotionBrewEvent;
import net.minecraftforge.event.entity.item.ItemEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.terraingen.WorldTypeEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;
import azaka7.algaecraft.common.ACGameData;
import azaka7.algaecraft.common.items.*;
import azaka7.algaecraft.common.world.ACBiomes;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ACEventHandler {
	
	public static ACEventHandler INSTANCE = new ACEventHandler();
	
	public void initialize(){
		MinecraftForge.EVENT_BUS.register(this);
		Item.getItemFromBlock(Blocks.log).setPotionEffect(PotionHelper.glowstoneEffect);
	}
	
	@SubscribeEvent
	public void customMobDrop(LivingDropsEvent event){
		if(event.entityLiving.getClass() == EntitySquid.class){
			ItemStack stack = new ItemStack(ACItems.itemSquidRaw, event.entity.worldObj.rand.nextInt(3));
			event.drops.add(new EntityItem(event.entityLiving.worldObj, event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ, stack));
		}
	}
	
	@SubscribeEvent
	public void handleAnvil(AnvilUpdateEvent event){
		if(event.left !=null && event.right != null){
			if(event.left.getItem() instanceof ItemDiveMask){
				int lvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.respiration.effectId, event.left);
				boolean flag = (lvl == 0 && event.right.getItem() == Items.redstone)
								|| (lvl == 1 && event.right.getItem() == Items.glowstone_dust)
								|| (lvl == 2 && event.right.getItem() == Items.ghast_tear);
				if(flag){
					event.output = ((ItemDiveMask) event.left.getItem()).upgrade(event.left.copy());
					event.cost = 4*(lvl+1);
				}
			}
		}
	}
}

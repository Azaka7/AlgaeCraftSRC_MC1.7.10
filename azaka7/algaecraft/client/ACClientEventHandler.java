package azaka7.algaecraft.client;

import java.util.ArrayList;

import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.common.MinecraftForge;

import org.lwjgl.opengl.GL11;

import scala.actors.threadpool.Arrays;
import azaka7.algaecraft.common.ACGameData;
import azaka7.algaecraft.common.handlers.ACEventHandler;
import azaka7.algaecraft.common.items.ItemDiveMask;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ACClientEventHandler {
	public static ACClientEventHandler INSTANCE = new ACClientEventHandler();
	
	public void initialize(){
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void handleFogDensity(EntityViewRenderEvent.FogDensity event){
		Entity entity = event.entity;
		if(entity instanceof EntityPlayer){
			ItemStack helm = ((EntityPlayer) entity).inventory.armorItemInSlot(3);
			if(helm != null){
				if(helm.getItem() instanceof ItemDiveMask){
					if(/*entity.isInsideOfMaterial(Material.water)*/event.block.getMaterial() == Material.water){
						float f = EnchantmentHelper.getRespiration((EntityLivingBase) entity)*0.01F;
						f =  0.04F - f;
						event.density = f;
						GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_EXP);
						event.setCanceled(true);
					}
				}
			}
		}
	
	}
	
	@SubscribeEvent
	public void handleFogColor(EntityViewRenderEvent.FogColors event){
		boolean masked = false;
		Entity entity = event.entity;
		if(entity instanceof EntityPlayer){
			ItemStack helm = ((EntityPlayer) entity).inventory.armorItemInSlot(3);
			if(helm != null){
				if(helm.getItem() instanceof ItemDiveMask){
					masked = true;
					if(entity.isInsideOfMaterial(Material.water)){
						float g = 0;
						float f = EnchantmentHelper.getRespiration((EntityLivingBase) entity)*0.05F;
						for(int s = 0; s < ACGameData.biomeIDSwampList.length; s++){
							if(ACGameData.biomeIDSwampList[s] == entity.worldObj.getBiomeGenForCoords((int) Math.round(entity.posX),(int) Math.round(entity.posZ)).biomeID){
								g = 0.3F;
								break;
							}
						}
						event.red = 0.4F;
						event.green = 0.4F + g;
						event.blue = 0.75F + f;
					}
				}
			}
		}
		if(!masked && entity.isInsideOfMaterial(Material.water)){
			ArrayList<Integer> ints = new ArrayList<Integer>();
			for(int id : ACGameData.biomeIDSwampList){
				ints.add(id);
			}
			if(ints.contains(entity.worldObj.getBiomeGenForCoords(((Double)entity.posX).intValue(), ((Double)entity.posZ).intValue()).biomeID)){
				if(event.green * 10F > 1.0F){
					event.red *= 0.5F/(event.green*10F);
					event.blue *= 0.4F/(event.green*10F);
					event.green = 1.0F;
				} else {
					event.red *= 0.5F;
					event.green *= 10F;
					event.blue *= 0.4F;
				}
			}
		}
	}
	
}

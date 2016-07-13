package azaka7.algaecraft.common.handlers;

import java.util.ArrayList;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import azaka7.algaecraft.common.handlers.ACSwimmingHandler.ISwimGear;
import azaka7.algaecraft.common.handlers.ACSwimmingHandler.MoveKey;
import azaka7.algaecraft.common.handlers.ACSwimmingHandler.SwimFactor;
import azaka7.algaecraft.common.items.ItemBCD;
import azaka7.algaecraft.network.ACPacketDispatcher;
import azaka7.algaecraft.network.KeyPressMessage;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ACTickHandler {
	
	public static ACTickHandler INSTANCE = new ACTickHandler();
	
	public void initialize(){
		FMLCommonHandler.instance().bus().register(this);
	}
	
	@SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		EntityPlayer player = event.player;
		if(event.phase == Phase.END){
			if(player.capabilities.isFlying && 
					!(player.inventory.armorItemInSlot(2) !=null && player.inventory.armorItemInSlot(2).getItem() instanceof ItemBCD) && 
					player.getEntityData().getBoolean(ItemBCD.bcdFlight)){
				player.capabilities.allowFlying = player.capabilities.isCreativeMode;
				player.capabilities.isFlying = false;
				player.capabilities.setFlySpeed(0.05F);
				player.getEntityData().setBoolean(ItemBCD.bcdFlight, false);
			}
			if(event.player.isInWater()){
				//System.out.println("PostTick for: "+event.side.toString());
				//System.out.println("Player name: "+player.getDisplayName());
				ArrayList<SwimFactor> factors = ACSwimmingHandler.getFactorsFromPlayerNBT(event.player);
				if(factors.size() == 0){
					return;
				}
				float yaw = player.getRotationYawHead(); // 0 = south = +z, 0x
				while(yaw >= 180){
					yaw -= 360;
				}
				while(yaw < -180){
					yaw += 360;
				}
				double hspeed = Math.sqrt(Math.pow(player.motionX, 2) + Math.pow(player.motionZ, 2));
				double moveDirection = 360;
				if(hspeed != 0){
					moveDirection = Math.toDegrees(Math.acos(player.motionZ/hspeed));
					if(player.motionX > 0){
						moveDirection *= -1;
					}
				}
				double moveSpeed = Math.sqrt(Math.pow(hspeed, 2)+Math.pow(player.motionY, 2));
				double dir = yaw-moveDirection;
				//System.out.println("iterating post-tick factors");
				//System.out.println("Relative Direction: "+yaw+":"+moveDirection);
				//System.out.println(event.side.toString()+" player motion: x="+player.motionX+"|y="+player.motionY+"|z="+player.motionZ);
				//System.out.println("Applying factors: ");
				for(SwimFactor factor : factors){
					if(factor == null){break;}
					//System.out.println("\t-"+factor.toString());
					double f = factor.getFactor();
					if(moveSpeed > factor.getUpperLimit()+0.00000001 || moveSpeed < factor.getLowerLimit()-0.00000001){
						break;
					}
					//for(MoveKey key : factor.getMoveKeys()){
					MoveKey key = factor.getMoveKey();
						switch(key){
						case FOREWARD: {
							if(Math.abs(dir)<=45){
								player.motionX *= f;
								player.motionZ *= f;
								//System.out.println("multiply foreward");
							} else if(Math.abs(dir)>=135){
								player.motionX /= f;
								player.motionZ /= f;
								//System.out.println("divide foreward");
							}
							break;}
						case BACKWARD: {
							if(Math.abs(dir)>=135 && Math.abs(dir) <=180){
								player.motionX *= f;
								player.motionZ *= f;
								//System.out.println("multiply backward");
							} else if(Math.abs(dir)<=45){
								player.motionX /= f;
								player.motionZ /= f;
								//System.out.println("divide backward");
							}
							break;}
						case LEFT: {
							if((dir>=45 && dir<=135) || dir<-180){
								player.motionX *= f;
								player.motionZ *= f;
								//System.out.println("multiply left");
							} else if((dir<=-45 && dir>=-135) || dir>180){
								player.motionX /= f;
								player.motionZ /= f;
								//System.out.println("divide left");
							}
							break;}
						case RIGHT: {
							if((dir<=-45 && dir>=-135) || dir>180){
								player.motionX *= f;
								player.motionZ *= f;
								//System.out.println("multiply right");
							} else if((dir>=45 && dir<=135) || dir<-180){
								player.motionX /= f;
								player.motionZ /= f;
								//System.out.println("divide right");
							}
							break;}
						case JUMP: {
							if(player.motionY > 0){
								player.motionY *= f;
								//System.out.println("multiply up");
							} else if(player.motionY < 0){
								player.motionY /= f;
								//System.out.println("divide up");
							}
							break;}
						case SNEAK: {
							if(player.motionY < 0){
								player.motionY *= f;
								//System.out.println("multiply down");
							} else if(player.motionY > 0){
								player.motionY /= f;
								//System.out.println("divide down");
							}
							break;}
						}
					for(int i = 0; i < player.inventory.getSizeInventory(); i++){
						ItemStack theStack = player.inventory.getStackInSlot(i);
						if(theStack != null && theStack.getItem() != null && theStack.getItem() instanceof ISwimGear){
							ISwimGear theItem = (ISwimGear) theStack.getItem();
							ArrayList<SwimFactor> itemFactors = theItem.getValidFactorsForItem(player, theStack, i);
							if(itemFactors.contains(factor)){
								theItem.onPlayerSwimTick(player, theStack, i);
							}
						}
					}
				}
			}
			NBTTagCompound tags = player.getEntityData();
			tags.removeTag(ACSwimmingHandler.SWIM_TAG);
		} 
		else if (event.phase == Phase.START){
			//System.out.println(event.side.toString() + " PreTick Player name: "+player.getDisplayName());
			//System.out.println(event.side.toString() + " PreTick Player motion: x="+player.motionX+"|y="+player.motionY+"|z="+player.motionZ);
			if(event.side == Side.CLIENT && event.player.isInWater()){
				//TODO Only clear NBT immediately after the tags are used, so the 
				//server doesn't get confused when no key packet is sent.
				doClientPreTick(event);
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	private void doClientPreTick(TickEvent.PlayerTickEvent event){
		EntityPlayer player = event.player;
		ArrayList<MoveKey> keysPressed = new ArrayList<MoveKey>();
		for(MoveKey key : MoveKey.values()){
			if(key.isPressed()){
				keysPressed.add(key);
			}
		}
		if(keysPressed.size() == 0){return;}
		MoveKey[] keys = new MoveKey[keysPressed.size()];
		for(int i = 0; i < keys.length; i++){
			keys[i] = keysPressed.get(i);
		}
		ArrayList<SwimFactor> factors = ACSwimmingHandler.getValidFactorsForKeys(player, keys);
		if(keys.length > 0){
			KeyPressMessage message = new KeyPressMessage(keys);
			ACPacketDispatcher.sendToServer(message);
		}
		ACSwimmingHandler.setFactorsToPlayerNBT(factors, player);
	}
}
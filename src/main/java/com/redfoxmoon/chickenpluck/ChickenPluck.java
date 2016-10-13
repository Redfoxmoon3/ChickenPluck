package com.redfoxmoon.chickenpluck;

import java.util.Random;

import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.init.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = ChickenPluck.MODID, version = ChickenPluck.VERSION, acceptedMinecraftVersions = "[1.10.2]")
public class ChickenPluck {

    public static final String MODID = "chickenpluck";
    public static final String VERSION = "1.0";
    
	private Random random = new Random();
    
    @Mod.Instance
    public static ChickenPluck instance;
    
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
    	
		MinecraftForge.EVENT_BUS.register(this);
    }
	
	@SubscribeEvent
	public void pluck(EntityInteract event) {
		
		if(event.getHand() == EnumHand.OFF_HAND)
			return; //double drops \o/
		
		EntityChicken chicken;
		
		if(event.getWorld().isRemote || !(event.getTarget() instanceof EntityChicken))
			return;
		
		chicken = (EntityChicken)event.getTarget();
		
		if(chicken.isChild() || chicken.isChickenJockey())
			return; //don't even think about it
		
		if(event.getEntityPlayer().getHeldItemMainhand() == null) {
			chicken.attackEntityFrom(DamageSource.causePlayerDamage(event.getEntityPlayer()), 1);
			chicken.dropItem(Items.FEATHER, random.nextInt(2) + 1);
		}
	}
}
package com.golems.entity;

import java.util.List;

import com.golems.main.Config;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class EntityStrawGolem extends GolemBase 
{			
	public EntityStrawGolem(World world) 
	{
		super(world, Config.STRAW.getBaseAttack(), Blocks.hay_block);
		this.setCanSwim(true);
	}
	
	protected ResourceLocation applyTexture()
	{
		return this.makeGolemTexture("straw");
	}
		
	@Override
	protected void applyAttributes() 
	{
	 	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Config.STRAW.getMaxHealth());
	  	this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35D);
	}
	
	@Override
	public void addGolemDrops(List<WeightedRandomChestContent> dropList, boolean recentlyHit, int lootingLevel)
	{
		int size = 6 + this.rand.nextInt(8 + lootingLevel * 4);
		this.addGuaranteedDropEntry(dropList, new ItemStack(Items.wheat, size));
		this.addDropEntry(dropList, Items.wheat_seeds, 0, 1, 3 + lootingLevel * 2, 10 + lootingLevel * 10);
	}
	
	@Override
	public SoundEvent getGolemSound() 
	{
		return SoundEvents.block_grass_step;
	}
}

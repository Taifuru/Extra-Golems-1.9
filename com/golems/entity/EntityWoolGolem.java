package com.golems.entity;

import java.util.List;

import com.golems.main.Config;
import com.golems.main.ExtraGolems;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class EntityWoolGolem extends GolemMultiTextured 
{	
	public static final String woolPrefix = "wool";
	private static final String[] coloredWoolTypes = {"black","orange","magenta","light_blue","yellow","lime","pink","gray","silver","cyan","purple","blue","brown","green","red","white"};

	public EntityWoolGolem(World world) 
	{
		super(world, Config.WOOL.getBaseAttack(), new ItemStack(Blocks.wool), woolPrefix, coloredWoolTypes);
		this.setCanSwim(true);
	}
	
	@Override
	public ItemStack getCreativeReturn()
	{
		ItemStack woolStack = super.getCreativeReturn();
		woolStack.setItemDamage(this.getTextureNum() % (coloredWoolTypes.length + 1));
		return woolStack;
	}
	
	@Override
	public String getModId() 
	{
		return ExtraGolems.MODID;
	}

	@Override
	protected void applyAttributes() 
	{
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Config.WOOL.getMaxHealth());
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30D);
	}

	@Override
	public void addGolemDrops(List<WeightedRandomChestContent> dropList, boolean recentlyHit, int lootingLevel)
	{
		int size = 1 + this.rand.nextInt(3) + lootingLevel;
		int meta = this.getTextureNum() % coloredWoolTypes.length;
		this.addGuaranteedDropEntry(dropList, new ItemStack(Blocks.wool, 1 + rand.nextInt(2), 0));
		this.addDropEntry(dropList, Blocks.wool, meta, 1, 2, 60 + lootingLevel * 10);
		this.addDropEntry(dropList, Items.string, 0, 1, 2, 5 + lootingLevel * 10);
	}

	@Override
	public SoundEvent getGolemSound() 
	{
		return SoundEvents.block_cloth_step;
	}
	
	@Override
	public void setTextureNum(byte toSet)
	{
		toSet %= (byte)(this.coloredWoolTypes.length - 1);
		super.setTextureNum(toSet);
	}
}

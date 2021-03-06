package com.golems.entity;

import java.util.List;

import com.golems.main.Config;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class EntityLeafGolem extends GolemColorized 
{
	public static final String ALLOW_SPECIAL = "Allow Special: Regeneration";
	
	private static final ResourceLocation TEXTURE_BASE = GolemBase.makeGolemTexture("leaves");
	private static final ResourceLocation TEXTURE_OVERLAY = GolemBase.makeGolemTexture("leaves_grayscale");

	public EntityLeafGolem(World world)
	{
		super(world, Config.LEAF.getBaseAttack(), new ItemStack(Blocks.leaves), 0x5F904A, TEXTURE_BASE, TEXTURE_OVERLAY);
		this.setCanSwim(true);
	}
	
	@Override
	protected void applyAttributes() 
	{
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Config.LEAF.getMaxHealth());
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.31D);
	}
	
	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	 * use this to react to sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		if(Config.LEAF.getBoolean(ALLOW_SPECIAL) && this.getActivePotionEffect(MobEffects.regeneration) == null && rand.nextInt(40) == 0)
		{
			this.addPotionEffect(new PotionEffect(MobEffects.regeneration, 200 + 20 * (1 + rand.nextInt(8)), 1));
		}
		
		if(this.ticksExisted % 10 == 2 && this.worldObj.isRemote)
		{
			BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(this.getPosition());
			long color = biome.getFoliageColorAtPos(this.getPosition());
			// debug:
			// System.out.print("color is " + color + "\n");
			this.setColor(color);
		}
		
		// slow falling for this entity
		if(this.motionY < -0.1D)
		{
			this.motionY *= 4.0D / 5.0D;
		}
	}
	
	@Override
	public void addGolemDrops(List<WeightedRandomChestContent> dropList, boolean recentlyHit, int lootingLevel)
	{
		this.addGuaranteedDropEntry(dropList, new ItemStack(Blocks.leaves, lootingLevel + 1));
		this.addDropEntry(dropList, Blocks.sapling, 0, 1, 1, 20 + lootingLevel * 10);
		this.addDropEntry(dropList, Items.apple, 0, 1, 1, 15 + lootingLevel * 10);
		this.addDropEntry(dropList, Items.stick, 0, 1, 2, 5 + lootingLevel * 10);
	}

	@Override
	public SoundEvent getGolemSound() 
	{
		return SoundEvents.block_grass_step;
	}
}

package co.uk.niadel.mpi.events.entity;

import net.minecraft.entity.EntityBodyHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityJumpHelper;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.ai.EntitySenses;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.world.World;

/**
 * This can be considered incredibly powerful, as you can manipulate AI and other things with this.
 * @author Niadel
 *
 */
public class EventEntityLivingInit
{
	/**
	 * The entity being initialised.
	 */
	public EntityLiving entity;
	
	/**
	 * This entity's world object.
	 */
	public World world;
	
	/**
	 * This entity's AI tasks, used for manipulating AI.
	 */
	public EntityAITasks tasks, targetTasks;
	
	/**
	 * This entity's look helper.
	 */
	public EntityLookHelper lookHelper;
	
	/**
	 * This entity's move helper.
	 */
	public EntityMoveHelper moveHelper;
	
	/**
	 * This entity's jump helper.
	 */
	public EntityJumpHelper jumpHelper;
	
	/**
	 * This entity's body helper.
	 */
	public EntityBodyHelper bodyHelper;
	
	/**
	 * This entity's navigator.
	 */
	public PathNavigate navigator;
	
	/**
	 * This entity's senses.
	 */
	public EntitySenses senses;
	
	public EventEntityLivingInit(EntityLiving entity, World world, EntityAITasks tasks, EntityAITasks targetTasks, EntityLookHelper lookHelper, EntityMoveHelper moveHelper, EntityJumpHelper jumpHelper, EntityBodyHelper bodyHelper, PathNavigate navigator, EntitySenses senses)
	{
		this.entity = entity;
		this.world = world;
		this.tasks = tasks;
		this.targetTasks = targetTasks;
		this.lookHelper = lookHelper;
		this.jumpHelper = jumpHelper;
		this.moveHelper = moveHelper;
		this.bodyHelper = bodyHelper;
		this.navigator = navigator;
		this.senses = senses;
	}
}

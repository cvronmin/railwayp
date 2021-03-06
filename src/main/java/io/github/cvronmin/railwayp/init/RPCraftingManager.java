package io.github.cvronmin.railwayp.init;

import java.util.Arrays;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWrittenBook;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.RecipeSorter.Category;

public class RPCraftingManager {
	public static void register() {
		GameRegistry.addRecipe(new ItemStack(RPItems.platform_banner, 2), new Object[]{
				"AAA",
				"BBB",
				'A', new ItemStack(Blocks.STAINED_HARDENED_CLAY,1,EnumDyeColor.WHITE.getMetadata()),
				'B',Blocks.PLANKS});
		GameRegistry.addRecipe(new ItemStack(RPItems.platform_banner, 4), new Object[]{
				"CCC",
				"AAA",
				"BBB",
				'A', new ItemStack(Blocks.STAINED_HARDENED_CLAY,1,EnumDyeColor.WHITE.getMetadata()),
				'B',Blocks.PLANKS,
				'C',Items.SIGN});
		GameRegistry.addRecipe(new ItemStack(RPItems.whpf, 2), new Object[]{
				"AAA",
				"BBB",
				'A', new ItemStack(Blocks.STAINED_HARDENED_CLAY,1,EnumDyeColor.BLACK.getMetadata()),
				'B',Blocks.PLANKS});
		GameRegistry.addRecipe(new ItemStack(RPItems.name_banner, 4), new Object[]{
				" A ",
				"BBB",
				'A', Items.SIGN,
				'B',Blocks.STAINED_HARDENED_CLAY});
		GameRegistry.addRecipe(new ItemStack(RPItems.EDITOR, 1), new Object[]{
				"  A",
				" A ",
				"B  ",
				'A', Items.STICK,
				'B',Items.DYE});
		registerMosaticTile();
		registerPlate();
		net.minecraftforge.oredict.RecipeSorter.register("railwayp:cloning", CloningRecipe.class, Category.SHAPELESS, "after:minecraft:shapeless");
		registerCloningRecipe();
	}
	private static void registerMosaticTile(){
		for(int i = 0;i < 16;i++){
			ItemStack itemstack = new ItemStack(RPBlocks.mosaic_tile, 8);
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setString("Color", Integer.toHexString(EnumDyeColor.byMetadata(i).getMapColor().colorValue));
            itemstack.setTagInfo("BlockEntityTag", nbttagcompound);
			GameRegistry.addRecipe(itemstack, new Object[]{
					"ABA",
					"BBB",
					"ABA",
					'A', new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, i),
					'B',Items.QUARTZ});
		}
	}
	private static void registerPlate(){
		for(int i = 0;i < 16;i++){
			ItemStack itemstack = new ItemStack(RPBlocks.plate, 8);
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setString("Color", Integer.toHexString(EnumDyeColor.byMetadata(i).getMapColor().colorValue));
            itemstack.setTagInfo("BlockEntityTag", nbttagcompound);
			GameRegistry.addRecipe(itemstack, new Object[]{
					"AAA",
					"ABA",
					"AAA",
					'B', new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, i),
					'A',Items.IRON_INGOT});
		}
	}
	private static void registerCloningRecipe(){
		GameRegistry.addRecipe(new CloningRecipe(RPItems.name_banner));
		GameRegistry.addRecipe(new CloningRecipe(RPItems.platform_banner));
		GameRegistry.addRecipe(new CloningRecipe(RPItems.whpf));
		GameRegistry.addRecipe(new CloningRecipe(RPItems.route_sign));
		GameRegistry.addRecipe(new CloningRecipe(Item.getItemFromBlock(RPBlocks.mosaic_tile)));
		GameRegistry.addRecipe(new CloningRecipe(Item.getItemFromBlock(RPBlocks.plate)));
	}
	private static class CloningRecipe implements IRecipe{
		Item item;
		public CloningRecipe(Item item) {
			this.item = item;
		}
		@Override
		public boolean matches(InventoryCrafting inv, World worldIn) {
	        int i = 0;
	        ItemStack itemstack = null;

	        for (int j = 0; j < inv.getSizeInventory(); ++j)
	        {
	            ItemStack itemstack1 = inv.getStackInSlot(j);

	            if (itemstack1 != null)
	            {
	            	if(itemstack1.getItem() == item){
	                if (itemstack1.hasTagCompound())
	                {
	                    if (itemstack != null)
	                    {
	                        return false;
	                    }

	                    itemstack = itemstack1;
	                }
	                else ++i;
	            	}else return false;
	            }
	        }

	        return itemstack != null && i > 0;
		}
		
		@Override
		public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
	        NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack>withSize(inv.getSizeInventory(), ItemStack.EMPTY);

	        for (int i = 0; i < nonnulllist.size(); ++i)
	        {
	            ItemStack itemstack = inv.getStackInSlot(i);
	                nonnulllist.set(i, net.minecraftforge.common.ForgeHooks.getContainerItem(itemstack));
	        }

	        return nonnulllist;
		}
		
		@Override
		public int getRecipeSize() {
			return 9;
		}
		
		@Override
		public ItemStack getRecipeOutput() {
			return ItemStack.EMPTY;
		}
		
		@Override
		public ItemStack getCraftingResult(InventoryCrafting inv) {
	        int i = 0;
	        ItemStack itemstack = ItemStack.EMPTY;

	        for (int j = 0; j < inv.getSizeInventory(); ++j)
	        {
	            ItemStack itemstack1 = inv.getStackInSlot(j);

	            if (!itemstack1.isEmpty())
	            {
	            	if(itemstack1.getItem() == item){
		                if (itemstack1.hasTagCompound())
		                {
		                    if (!itemstack.isEmpty())
		                    {
		                        return ItemStack.EMPTY;
		                    }

		                    itemstack = itemstack1;
		                }
		                else ++i;
		            	}else return ItemStack.EMPTY;
	            }
	        }

	        if (!itemstack.isEmpty() && i >= 1)
	        {
	            ItemStack itemstack2 = new ItemStack(item, i + 1, itemstack.getMetadata());

	            if (itemstack.hasDisplayName())
	            {
	                itemstack2.setStackDisplayName(itemstack.getDisplayName());
	            }
	            itemstack2.setTagCompound(itemstack.getTagCompound());

	            return itemstack2;
	        }
	        else
	        {
	            return ItemStack.EMPTY;
	        }
		}
	}
}

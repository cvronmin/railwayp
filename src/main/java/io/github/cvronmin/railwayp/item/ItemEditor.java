package io.github.cvronmin.railwayp.item;

import io.github.cvronmin.railwayp.client.gui.GuiNameBannerEditor;
import io.github.cvronmin.railwayp.client.gui.GuiPlatformBannerEditor;
import io.github.cvronmin.railwayp.client.gui.GuiRouteSignageEditor;
import io.github.cvronmin.railwayp.client.gui.GuiWHPFEditor;
import io.github.cvronmin.railwayp.tileentity.TileEntityNameBanner;
import io.github.cvronmin.railwayp.tileentity.TileEntityPlatformBanner;
import io.github.cvronmin.railwayp.tileentity.TileEntityRouteSignage;
import io.github.cvronmin.railwayp.tileentity.TileEntityWHPF;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemEditor extends Item {
	public ItemEditor(){
		
	}
    /**
     * Called when a Block is right-clicked with this Item
     */
	@SideOnly(Side.CLIENT)
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		if(!worldIn.isRemote){
			return EnumActionResult.PASS;
		}
    	TileEntity te = worldIn.getTileEntity(pos);
    	if (te instanceof TileEntityPlatformBanner) {
			FMLClientHandler.instance().getClient().displayGuiScreen(new GuiPlatformBannerEditor((TileEntityPlatformBanner) te));
			return EnumActionResult.SUCCESS;
		}
    	if (te instanceof TileEntityNameBanner) {
			FMLClientHandler.instance().getClient().displayGuiScreen(new GuiNameBannerEditor((TileEntityNameBanner) te));
			return EnumActionResult.SUCCESS;
		}
    	if (te instanceof TileEntityWHPF) {
			FMLClientHandler.instance().getClient().displayGuiScreen(new GuiWHPFEditor((TileEntityWHPF) te));
			return EnumActionResult.SUCCESS;
		}
    	if (te instanceof TileEntityRouteSignage) {
			FMLClientHandler.instance().getClient().displayGuiScreen(new GuiRouteSignageEditor((TileEntityRouteSignage) te));
			return EnumActionResult.SUCCESS;
		}
        return EnumActionResult.PASS;
    }
}

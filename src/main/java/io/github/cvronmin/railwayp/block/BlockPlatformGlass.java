package io.github.cvronmin.railwayp.block;

import java.util.List;
import java.util.Random;

import io.github.cvronmin.railwayp.init.RPBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.EnumFacing.AxisDirection;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockPlatformGlass extends Block{
    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");
    public static final PropertyBool X_MINUS = PropertyBool.create("lefty");
    public static final PropertyBool Z_MINUS = PropertyBool.create("forwardly");
    protected static final AxisAlignedBB[] field_185730_f = new AxisAlignedBB[] {
    		//CENTER 0b0000
    		new AxisAlignedBB(0.4D + .4, 0.0D, 0.4D + .4, 0.6D + .4, 1.0D, 0.6D + .4),
    		//SOUTH 0b0001
    		new AxisAlignedBB(0.4D + .4, 0.0D, 0.4D + .4, 0.6D + .4, 1.0D, 1.0D    ),
    		//WEST 0b0010
    		new AxisAlignedBB(0.0D    , 0.0D, 0.4D + .4, 0.6D + .4, 1.0D, 0.6D + .4),
    		//SOUTH+WEST 0b0011
    		new AxisAlignedBB(0.0D    , 0.0D, 0.4D + .4, 0.6D + .4, 1.0D, 1.0D    ),
    		//NORTH 0b0100
    		new AxisAlignedBB(0.4D + .4, 0.0D, 0.0D    , 0.6D + .4, 1.0D, 0.6D + .4),
    		//NORTH+SOUTH 0b0101
    		new AxisAlignedBB(0.4D + .4, 0.0D, 0.0D    , 0.6D + .4, 1.0D, 1.0D    ),
    		//NORTH+WEST 0b0110
    		new AxisAlignedBB(0.0D    , 0.0D, 0.0D    , 0.6D + .4, 1.0D, 0.6D + .4),
    		//NORTH+SOUTH+WEST 0b0111
    		new AxisAlignedBB(0.0D    , 0.0D, 0.0D    , 0.6D + .4, 1.0D, 1.0D    ),
    		//EAST 0b1000
    		new AxisAlignedBB(0.4D + .4, 0.0D, 0.4D + .4, 1.0D    , 1.0D, 0.6D + .4),
    		//EAST + SOUTH 0b1001
    		new AxisAlignedBB(0.4D + .4, 0.0D, 0.4D + .4, 1.0D    , 1.0D, 1.0D    ),
    		//EAST + WEST 0b1010
    		new AxisAlignedBB(0.0D    , 0.0D, 0.4D + .4, 1.0D    , 1.0D, 0.6D + .4),
    		//EAST + SOUTH + WEST 0b1011
    		new AxisAlignedBB(0.0D    , 0.0D, 0.4D + .4, 1.0D    , 1.0D, 1.0D    ),
    		//EAST + NORTH 0b1100
    		new AxisAlignedBB(0.4D + .4, 0.0D, 0.0D    , 1.0D    , 1.0D, 0.6D + .4),
    		//EAST + NORTH + SOUTH 0b1101
    		new AxisAlignedBB(0.4D + .4, 0.0D, 0.0D    , 1.0D    , 1.0D, 1.0D    ),
    		//east + NORTH + WEST 0b1110
    		new AxisAlignedBB(0.0D    , 0.0D, 0.0D    , 1.0D    , 1.0D, 0.6D + .4),
    		//FULL 0b1111
    		new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D),
    		//TODO X-MINUS (lefty)
    		//CENTER 0b0000
    		new AxisAlignedBB(0.4D - .4, 0.0D, 0.4D + .4, 0.6D - .4, 1.0D, 0.6D + .4),
    		//SOUTH 0b0001
    		new AxisAlignedBB(0.4D - .4, 0.0D, 0.4D + .4, 0.6D - .4, 1.0D, 1.0D),
    		//WEST 0b0010
    		new AxisAlignedBB(0.0D, 0.0D, 0.4D + .4, 0.6D - .4, 1.0D, 0.6D + .4),
    		//SOUTH+WEST 0b0011
    		new AxisAlignedBB(0.0D, 0.0D, 0.4D + .4, 0.6D - .4, 1.0D, 1.0D),
    		//NORTH 0b0100
    		new AxisAlignedBB(0.4D - .4, 0.0D, 0.0D, 0.6D - .4, 1.0D, 0.6D + .4),
    		//NORTH+SOUTH 0b0101
    		new AxisAlignedBB(0.4D - .4, 0.0D, 0.0D, 0.6D - .4, 1.0D, 1.0D),
    		//NORTH+WEST 0b0110
    		new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.6D - .4, 1.0D, 0.6D + .4),
    		//NORTH+SOUTH+WEST 0b0111
    		new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.6D - .4, 1.0D, 1.0D),
    		//EAST 0b1000
    		new AxisAlignedBB(0.4D - .4, 0.0D, 0.4D + .4, 1.0D, 1.0D, 0.6D + .4),
    		//EAST + SOUTH 0b1001
    		new AxisAlignedBB(0.4D - .4, 0.0D, 0.4D + .4, 1.0D, 1.0D, 1.0D),
    		//EAST + WEST 0b1010
    		new AxisAlignedBB(0.0D, 0.0D, 0.4D + .4, 1.0D, 1.0D, 0.6D + .4),
    		//EAST + SOUTH + WEST 0b1011
    		new AxisAlignedBB(0.0D, 0.0D, 0.4D + .4, 1.0D, 1.0D, 1.0D),
    		//EAST + NORTH 0b1100
    		new AxisAlignedBB(0.4D - .4, 0.0D, 0.0D, 1.0D, 1.0D, 0.6D + .4),
    		//EAST + NORTH + SOUTH 0b1101
    		new AxisAlignedBB(0.4D - .4, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D),
    		//east + NORTH + WEST 0b1110
    		new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.6D + .4),
    		//FULL 0b1111
    		new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D),
    		//TODO Z-MINUS (forwardly)
    		//CENTER 0b0000
    		new AxisAlignedBB(0.4D + .4, 0.0D, 0.4D - .4, 0.6D + .4, 1.0D, 0.6D - .4),
    		//SOUTH 0b0001
    		new AxisAlignedBB(0.4D + .4, 0.0D, 0.4D - .4, 0.6D + .4, 1.0D, 1.0D),
    		//WEST 0b0010
    		new AxisAlignedBB(0.0D, 0.0D, 0.4D - .4, 0.6D + .4, 1.0D, 0.6D - .4),
    		//SOUTH+WEST 0b0011
    		new AxisAlignedBB(0.0D, 0.0D, 0.4D - .4, 0.6D + .4, 1.0D, 1.0D),
    		//NORTH 0b0100
    		new AxisAlignedBB(0.4D + .4, 0.0D, 0.0D, 0.6D + .4, 1.0D, 0.6D - .4),
    		//NORTH+SOUTH 0b0101
    		new AxisAlignedBB(0.4D + .4, 0.0D, 0.0D, 0.6D + .4, 1.0D, 1.0D),
    		//NORTH+WEST 0b0110
    		new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.6D + .4, 1.0D, 0.6D - .4),
    		//NORTH+SOUTH+WEST 0b0111
    		new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.6D + .4, 1.0D, 1.0D),
    		//EAST 0b1000
    		new AxisAlignedBB(0.4D + .4, 0.0D, 0.4D - .4, 1.0D, 1.0D, 0.6D - .4),
    		//EAST + SOUTH 0b1001
    		new AxisAlignedBB(0.4D + .4, 0.0D, 0.4D - .4, 1.0D, 1.0D, 1.0D),
    		//EAST + WEST 0b1010
    		new AxisAlignedBB(0.0D, 0.0D, 0.4D - .4, 1.0D, 1.0D, 0.6D - .4),
    		//EAST + SOUTH + WEST 0b1011
    		new AxisAlignedBB(0.0D, 0.0D, 0.4D - .4, 1.0D, 1.0D, 1.0D),
    		//EAST + NORTH 0b1100
    		new AxisAlignedBB(0.4D + .4, 0.0D, 0.0D, 1.0D, 1.0D, 0.6D - .4),
    		//EAST + NORTH + SOUTH 0b1101
    		new AxisAlignedBB(0.4D + .4, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D),
    		//east + NORTH + WEST 0b1110
    		new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.6D - .4),
    		//FULL 0b1111
    		new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D),
    		//TODO X-MINUS (lefty) & Z-MINUS (forwardly)
    		//CENTER 0b0000
    		new AxisAlignedBB(0.4D - .4, 0.0D, 0.4D - .4, 0.6D - .4, 1.0D, 0.6D - .4),
    		//SOUTH 0b0001
    		new AxisAlignedBB(0.4D - .4, 0.0D, 0.4D - .4, 0.6D - .4, 1.0D, 1.0D),
    		//WEST 0b0010
    		new AxisAlignedBB(0.0D, 0.0D, 0.4D - .4, 0.6D - .4, 1.0D, 0.6D - .4),
    		//SOUTH+WEST 0b0011
    		new AxisAlignedBB(0.0D, 0.0D, 0.4D - .4, 0.6D - .4, 1.0D, 1.0D),
    		//NORTH 0b0100
    		new AxisAlignedBB(0.4D - .4, 0.0D, 0.0D, 0.6D - .4, 1.0D, 0.6D - .4),
    		//NORTH+SOUTH 0b0101
    		new AxisAlignedBB(0.4D - .4, 0.0D, 0.0D, 0.6D - .4, 1.0D, 1.0D),
    		//NORTH+WEST 0b0110
    		new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.6D - .4, 1.0D, 0.6D - .4),
    		//NORTH+SOUTH+WEST 0b0111
    		new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.6D - .4, 1.0D, 1.0D),
    		//EAST 0b1000
    		new AxisAlignedBB(0.4D - .4, 0.0D, 0.4D - .4, 1.0D, 1.0D, 0.6D - .4),
    		//EAST + SOUTH 0b1001
    		new AxisAlignedBB(0.4D - .4, 0.0D, 0.4D - .4, 1.0D, 1.0D, 1.0D),
    		//EAST + WEST 0b1010
    		new AxisAlignedBB(0.0D, 0.0D, 0.4D - .4, 1.0D, 1.0D, 0.6D - .4),
    		//EAST + SOUTH + WEST 0b1011
    		new AxisAlignedBB(0.0D, 0.0D, 0.4D - .4, 1.0D, 1.0D, 1.0D),
    		//EAST + NORTH 0b1100
    		new AxisAlignedBB(0.4D - .4, 0.0D, 0.0D, 1.0D, 1.0D, 0.6D - .4),
    		//EAST + NORTH + SOUTH 0b1101
    		new AxisAlignedBB(0.4D - .4, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D),
    		//east + NORTH + WEST 0b1110
    		new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.6D - .4),
    		//FULL 0b1111
    		new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)
    		};
    public BlockPlatformGlass()
    {
		super(Material.GLASS);
        this.setDefaultState(this.blockState.getBaseState().withProperty(X_MINUS, false).withProperty(Z_MINUS, false).withProperty(NORTH, Boolean.valueOf(false)).withProperty(EAST, Boolean.valueOf(false)).withProperty(SOUTH, Boolean.valueOf(false)).withProperty(WEST, Boolean.valueOf(false)));
        this.setCreativeTab(CreativeTabs.DECORATIONS);
    }

    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB p_185477_4_, List<AxisAlignedBB> p_185477_5_, Entity p_185477_6_)
    {
        state = this.getActualState(state, worldIn, pos);
        int mask = (state.getValue(X_MINUS) ? 16 : 0) | (state.getValue(Z_MINUS) ? 32 : 0);
        addCollisionBoxToList(pos, p_185477_4_, p_185477_5_, field_185730_f[0 | mask]);

        if (((Boolean)state.getValue(NORTH)).booleanValue())
        {
            addCollisionBoxToList(pos, p_185477_4_, p_185477_5_, field_185730_f[getBoundingBoxIndex(EnumFacing.NORTH) | mask]);
        }

        if (((Boolean)state.getValue(SOUTH)).booleanValue())
        {
            addCollisionBoxToList(pos, p_185477_4_, p_185477_5_, field_185730_f[getBoundingBoxIndex(EnumFacing.SOUTH) | mask]);
        }

        if (((Boolean)state.getValue(EAST)).booleanValue())
        {
            addCollisionBoxToList(pos, p_185477_4_, p_185477_5_, field_185730_f[getBoundingBoxIndex(EnumFacing.EAST) | mask]);
        }

        if (((Boolean)state.getValue(WEST)).booleanValue())
        {
            addCollisionBoxToList(pos, p_185477_4_, p_185477_5_, field_185730_f[getBoundingBoxIndex(EnumFacing.WEST) | mask]);
        }
    }

    private static int getBoundingBoxIndex(EnumFacing p_185729_0_)
    {
        return 1 << p_185729_0_.getHorizontalIndex();
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        state = this.getActualState(state, source, pos);
        return field_185730_f[getBoundingBoxIndex(state)];
    }

    private static int getBoundingBoxIndex(IBlockState state)
    {
        int i = 0;
        i |= (state.getValue(X_MINUS) ? 16 : 0) | (state.getValue(Z_MINUS) ? 32 : 0);
        if (((Boolean)state.getValue(NORTH)).booleanValue())
        {
            i |= getBoundingBoxIndex(EnumFacing.NORTH);
        }

        if (((Boolean)state.getValue(EAST)).booleanValue())
        {
            i |= getBoundingBoxIndex(EnumFacing.EAST);
        }

        if (((Boolean)state.getValue(SOUTH)).booleanValue())
        {
            i |= getBoundingBoxIndex(EnumFacing.SOUTH);
        }

        if (((Boolean)state.getValue(WEST)).booleanValue())
        {
            i |= getBoundingBoxIndex(EnumFacing.WEST);
        }

        return i;
    }

    /**
     * Get the actual Block state of this Block at the given position. This applies properties not visible in the
     * metadata, such as fence connections.
     */
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        return state.withProperty(NORTH, canPaneConnectTo(worldIn, pos, EnumFacing.NORTH))
                .withProperty(SOUTH, canPaneConnectTo(worldIn, pos, EnumFacing.SOUTH))
                .withProperty(WEST, canPaneConnectTo(worldIn, pos, EnumFacing.WEST))
                .withProperty(EAST, canPaneConnectTo(worldIn, pos, EnumFacing.EAST));
    }

    /**
     * Get the Item that this Block should drop when harvested.
     */
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return super.getItemDropped(state, rand, fortune);
    }

    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    public final boolean canPaneConnectToBlock(Block blockIn)
    {
        return blockIn.getDefaultState().isFullCube() || blockIn == this || blockIn == Blocks.GLASS || blockIn == Blocks.STAINED_GLASS || blockIn == Blocks.STAINED_GLASS_PANE || blockIn instanceof BlockPane || blockIn == RPBlocks.platform_door_base || blockIn == RPBlocks.platform_door_extension || blockIn == RPBlocks.platform_door_head;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        return blockAccess.getBlockState(pos.offset(side)).getBlock() == this ? false : super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }

    protected boolean canSilkHarvest()
    {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        return (state.getValue(X_MINUS) ? 1 : 0) | (state.getValue(Z_MINUS) ? 2 : 0);
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta) {
    	return getDefaultState().withProperty(X_MINUS, (meta & 1) == 1).withProperty(Z_MINUS, (meta & 2) == 1);
    }

    /**
     * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        switch (rot)
        {
            case CLOCKWISE_180:
                return state.withProperty(NORTH, state.getValue(SOUTH)).withProperty(EAST, state.getValue(WEST)).withProperty(SOUTH, state.getValue(NORTH)).withProperty(WEST, state.getValue(EAST));
            case COUNTERCLOCKWISE_90:
                return state.withProperty(NORTH, state.getValue(EAST)).withProperty(EAST, state.getValue(SOUTH)).withProperty(SOUTH, state.getValue(WEST)).withProperty(WEST, state.getValue(NORTH));
            case CLOCKWISE_90:
                return state.withProperty(NORTH, state.getValue(WEST)).withProperty(EAST, state.getValue(NORTH)).withProperty(SOUTH, state.getValue(EAST)).withProperty(WEST, state.getValue(SOUTH));
            default:
                return state;
        }
    }

    /**
     * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        switch (mirrorIn)
        {
            case LEFT_RIGHT:
                return state.withProperty(NORTH, state.getValue(SOUTH)).withProperty(SOUTH, state.getValue(NORTH));
            case FRONT_BACK:
                return state.withProperty(EAST, state.getValue(WEST)).withProperty(WEST, state.getValue(EAST));
            default:
                return super.withMirror(state, mirrorIn);
        }
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {NORTH, EAST, WEST, SOUTH, X_MINUS, Z_MINUS});
    }

    public boolean canPaneConnectTo(IBlockAccess world, BlockPos pos, EnumFacing dir)
    {
        BlockPos off = pos.offset(dir);
        IBlockState state = world.getBlockState(off);
        return canPaneConnectToBlock(state.getBlock()) || state.isSideSolid(world, off, dir.getOpposite());
    }
    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
    		float hitZ, int meta, EntityLivingBase placer, EnumHand stack) {
        return this.getDefaultState().withProperty(X_MINUS, hitX < 0.5 ^ (hitY > 0 & hitY < 1 & hitZ > 0 & hitZ < 1 &( hitX == 1 | hitX == 0))).withProperty(Z_MINUS, hitZ < 0.5 ^ (hitY > 0 & hitY < 1 & hitX > 0 & hitX < 1 &( hitZ == 1 | hitZ == 0)));}
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(X_MINUS, hitX < 0.5 ^ (hitY > 0 & hitY < 1 & hitZ > 0 & hitZ < 1 &( hitX == 1 | hitX == 0))).withProperty(Z_MINUS, hitZ < 0.5 ^ (hitY > 0 & hitY < 1 & hitX > 0 & hitX < 1 &( hitZ == 1 | hitZ == 0)));
    }

}

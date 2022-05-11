package xyz.rosati.ipipes.blocks.pipes;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("deprecation")
public class PipeBlock extends Block implements Waterloggable {
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final BooleanProperty NORTH = BooleanProperty.of("north");
    public static final BooleanProperty SOUTH = BooleanProperty.of("south");
    public static final BooleanProperty EAST = BooleanProperty.of("east");
    public static final BooleanProperty WEST = BooleanProperty.of("west");
    public static final BooleanProperty UP = BooleanProperty.of("up");
    public static final BooleanProperty DOWN = BooleanProperty.of("down");
    public static final Map<Direction, BooleanProperty> PROP_MAP = Util.make(new HashMap<Direction, BooleanProperty>(), map->{
        map.put(Direction.NORTH, NORTH);
        map.put(Direction.SOUTH, SOUTH);
        map.put(Direction.EAST, EAST);
        map.put(Direction.WEST, WEST);
        map.put(Direction.UP, UP);
        map.put(Direction.DOWN, DOWN);
    });

    public PipeBlock() {
        super(FabricBlockSettings.of(Material.GLASS)
                                 .hardness(4.0f)
                                 .nonOpaque());

        setDefaultState(this.stateManager.getDefaultState()
                                         .with(WATERLOGGED, false)
                                         .with(NORTH, false)
                                         .with(SOUTH, false)
                                         .with(EAST, false)
                                         .with(SOUTH, false)
                                         .with(UP, false)
                                         .with(DOWN, false));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        // TODO: outline shape with connections
//        VoxelShapes.union
        return VoxelShapes.cuboid(4/16f, 4/16f, 4/16f, 1f - 4/16f, 1f - 4/16f, 1f - 4/16f);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return makeConnections(ctx.getWorld(), ctx.getBlockPos())
                .with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));

        Boolean value = isConnectable(world, neighborPos, direction.getOpposite());

        return state.with(getProperty(direction), value);
    }

    private boolean isConnectable(WorldAccess world, BlockPos pos, Direction dir) {
        Block block = world.getBlockState(pos).getBlock();

        return block instanceof PipeBlock || block instanceof AbstractChestBlock || world.getBlockEntity(pos) instanceof Inventory;
    }

    public Property<Boolean> getProperty(Direction facing) {
        return PROP_MAP.get(facing);
    }

    private BlockState makeConnections(World world, BlockPos pos) {
        Boolean north = isConnectable(world, pos.north(), Direction.SOUTH);
        Boolean east = isConnectable(world, pos.east(), Direction.WEST);
        Boolean south = isConnectable(world, pos.south(), Direction.NORTH);
        Boolean west = isConnectable(world, pos.west(), Direction.EAST);
        Boolean up = isConnectable(world, pos.up(), Direction.DOWN);
        Boolean down = isConnectable(world, pos.down(), Direction.UP);

        return this.getDefaultState()
                   .with(NORTH, north)
                   .with(EAST, east)
                   .with(SOUTH, south)
                   .with(WEST, west)
                   .with(UP, up)
                   .with(DOWN, down);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}

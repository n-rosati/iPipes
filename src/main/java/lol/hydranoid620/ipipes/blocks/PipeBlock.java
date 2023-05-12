package lol.hydranoid620.ipipes.blocks;

import lol.hydranoid620.ipipes.blocks.entities.PipeBlockEntity;
import lol.hydranoid620.ipipes.iPipes;
import lol.hydranoid620.ipipes.routing.Node;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;

@SuppressWarnings("deprecation")
public class PipeBlock extends BlockWithEntity implements Waterloggable, IPipeConnectable {
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public PipeBlock() {
        super(FabricBlockSettings.of(Material.GLASS)
                                 .strength(0.1f, 4.0f)
                                 .nonOpaque()
                                 .sounds(BlockSoundGroup.STONE));

        setDefaultState(getStateManager().getDefaultState()
                                         .with(WATERLOGGED, false)
                                         .with(NORTH, false)
                                         .with(SOUTH, false)
                                         .with(EAST, false)
                                         .with(WEST, false)
                                         .with(UP, false)
                                         .with(DOWN, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        World world = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();

        return this.getDefaultState()
                   .with(NORTH, isConnectable(world, blockPos.north()))
                   .with(EAST, isConnectable(world, blockPos.east()))
                   .with(SOUTH, isConnectable(world, blockPos.south()))
                   .with(WEST, isConnectable(world, blockPos.west()))
                   .with(UP, isConnectable(world, blockPos.up()))
                   .with(DOWN, isConnectable(world, blockPos.down()))
                   .with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER);
    }

    /**
     * checks if the {@link PipeBlock} should connect to a neighbouring block
     * @param world the world
     * @param pos block
     * @return should the PipeBlock connect to the given neighbouring block
     */
    protected boolean isConnectable(WorldAccess world, BlockPos pos) {
        Block block = world.getBlockState(pos).getBlock();
        return block instanceof PipeBlock || block instanceof NetworkControllerBlock;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return state.with(PROP_MAP.get(direction), isConnectable(world, neighborPos));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        // Base frame
        VoxelShape shape = VoxelShapes.cuboid(4 / 16f, 4 / 16f, 4 / 16f, 1f - 4 / 16f, 1f - 4 / 16f, 1f - 4 / 16f);

        // Side connectors
        if (state.get(NORTH)) shape = VoxelShapes.union(shape, VoxelShapes.cuboid(4 / 16f, 4 / 16f, 0 / 16f, 1 - 4 / 16f, 1 - 4 / 16f, 4 / 16f));
        if (state.get(SOUTH)) shape = VoxelShapes.union(shape, VoxelShapes.cuboid(4 / 16f, 4 / 16f, 1 - 4 / 16f, 1 - 4 / 16f, 1 - 4 / 16f, 16 / 16f));
        if (state.get(EAST)) shape = VoxelShapes.union(shape, VoxelShapes.cuboid(1 - 4 / 16f, 4 / 16f, 4 / 16f, 16 / 16f, 1 - 4 / 16f, 1 - 4 / 16f));
        if (state.get(WEST)) shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0 / 16f, 4 / 16f, 4 / 16f, 4 / 16f, 1 - 4 / 16f, 1 - 4 / 16f));
        if (state.get(UP)) shape = VoxelShapes.union(shape, VoxelShapes.cuboid(4 / 16f, 1 - 4 / 16f, 4 / 16f, 1 - 4 / 16f, 16 / 16f, 1 - 4 / 16f));
        if (state.get(DOWN)) shape = VoxelShapes.union(shape, VoxelShapes.cuboid(4 / 16f, 0 / 16f, 4 / 16f, 1 - 4 / 16f, 4 / 16f, 1 - 4 / 16f));

        return shape.simplify();
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PipeBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        // With inheriting from BlockWithEntity this defaults to INVISIBLE, so we need to change that!
        return BlockRenderType.MODEL;
    }
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, iPipes.PIPE_BLOCK_ENTITY, PipeBlockEntity::tick);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (hand == Hand.OFF_HAND || world.isClient) return super.onUse(state, world, pos, player, hand, hit);

        var be = world.getBlockEntity(pos, iPipes.ACTIVE_SUPPLIER_PIPE_BLOCK_ENTITY);
        if (be.isPresent()) {
            for (LinkedList<Node> e : be.get().getDestinations()) {
                iPipes.LOGGER.info("BEGIN LIST");
                e.forEach(x -> iPipes.LOGGER.info(x.toString()));
                iPipes.LOGGER.info("END LIST");
            }
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }
}

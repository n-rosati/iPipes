package lol.hydranoid620.ipipes.blocks;

import lol.hydranoid620.ipipes.blocks.entities.NetworkControllerBlockEntity;
import lol.hydranoid620.ipipes.iPipes;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("deprecation")
public class NetworkControllerBlock extends BlockWithEntity {
    public static final BooleanProperty NORTH = BooleanProperty.of("north");
    public static final BooleanProperty SOUTH = BooleanProperty.of("south");
    public static final BooleanProperty EAST = BooleanProperty.of("east");
    public static final BooleanProperty WEST = BooleanProperty.of("west");
    public static final BooleanProperty UP = BooleanProperty.of("up");
    public static final BooleanProperty DOWN = BooleanProperty.of("down");

    public static final EnumProperty<iPipes.Types> TYPES_ENUM_PROPERTY = EnumProperty.of("type", iPipes.Types.class);

    public static final Map<Direction, BooleanProperty> PROP_MAP = Util.make(new HashMap<>(), map -> {
        map.put(Direction.NORTH, NORTH);
        map.put(Direction.SOUTH, SOUTH);
        map.put(Direction.EAST, EAST);
        map.put(Direction.WEST, WEST);
        map.put(Direction.UP, UP);
        map.put(Direction.DOWN, DOWN);
    });

    public NetworkControllerBlock() {
        super(FabricBlockSettings.of(Material.STONE));

        setDefaultState(getStateManager().getDefaultState()
                                         .with(NORTH, false)
                                         .with(SOUTH, false)
                                         .with(EAST, false)
                                         .with(SOUTH, false)
                                         .with(UP, false)
                                         .with(DOWN, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
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
                   .with(DOWN, isConnectable(world, blockPos.down()));
    }

    protected boolean isConnectable(WorldAccess world, BlockPos pos) {
        Block block = world.getBlockState(pos).getBlock();
        return block instanceof PipeBlock;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return state.with(PROP_MAP.get(direction), isConnectable(world, neighborPos));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        // Base frame
        VoxelShape shape = VoxelShapes.cuboid(2 / 16f, 2 / 16f, 2 / 16f, 1f - 2 / 16f, 1f - 2 / 16f, 1f - 2 / 16f);

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
        return new NetworkControllerBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        // With inheriting from BlockWithEntity this defaults to INVISIBLE, so we need to change that!
        return BlockRenderType.MODEL;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, iPipes.NETWORK_CONTROLLER_BLOCK_ENTITY, NetworkControllerBlockEntity::tick);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        return super.onUse(state, world, pos, player, hand, hit);
    }

    public static List<Direction> getConnectedDirections(BlockState state) {
        List<Direction> directions = new ArrayList<>(6);

        if (state.get(NORTH)) directions.add(Direction.NORTH);
        if (state.get(SOUTH)) directions.add(Direction.SOUTH);
        if (state.get(EAST)) directions.add(Direction.EAST);
        if (state.get(WEST)) directions.add(Direction.WEST);
        if (state.get(UP)) directions.add(Direction.UP);
        if (state.get(DOWN)) directions.add(Direction.DOWN);

        return directions;
    }
}

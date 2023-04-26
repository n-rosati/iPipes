package lol.hydranoid620.ipipes.blocks.entities;

import lol.hydranoid620.ipipes.iPipes;
import lol.hydranoid620.ipipes.routing.*;
import lombok.Getter;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;
import java.util.stream.Collectors;

public class NetworkControllerBlockEntity extends BlockEntity {
    private static final int ACTIONS_PER_SECOND = 2;
    private int ticksUntilAction = 0;
    @Getter
    private Graph graph;
    @Getter
    private Map<iPipes.Types, List<Node>> connectedEndpoints = new HashMap<>();

    public NetworkControllerBlockEntity(BlockPos pos, BlockState state) {
        super(iPipes.NETWORK_CONTROLLER_BLOCK_ENTITY, pos, state);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("ticksUntilAction", ticksUntilAction);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        ticksUntilAction = nbt.getInt("ticksUntilAction");
    }
    private void makeGraph() {
        this.graph = GraphCreator.findAllNodesInNetwork(this.getPos(), this.getWorld());
    }

    public boolean shouldDoAction() {
        boolean retVal = false;
        if (this.ticksUntilAction <= 0) {
            this.ticksUntilAction = 20 / ACTIONS_PER_SECOND;
            retVal = true;
        } else {
            this.ticksUntilAction--;
        }

        this.markDirty();
        return retVal;
    }

    public static void tick(World world, BlockPos pos, BlockState state, NetworkControllerBlockEntity be) {
        if (world.isClient || !be.shouldDoAction()) return;

        if (be.getGraph() == null) {
            be.makeGraph();
            iPipes.LOGGER.info("Graph made!");
        }

        /*TODO:
                - find all requesters
                - find all active providers
                - find all passive providers
                - find all storages
                - try to satisfy requesters with active providers
                - try to satisfy requesters with passive providers
                - excess active provider capacity goes to storage*/

//        be.markDirty();
    }
}

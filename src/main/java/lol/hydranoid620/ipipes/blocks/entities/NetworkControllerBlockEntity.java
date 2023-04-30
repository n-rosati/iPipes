package lol.hydranoid620.ipipes.blocks.entities;

import lol.hydranoid620.ipipes.iPipes;
import lol.hydranoid620.ipipes.iPipes.Types;
import lol.hydranoid620.ipipes.routing.*;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;

import static lol.hydranoid620.ipipes.iPipes.Types.*;

public class NetworkControllerBlockEntity extends BlockEntity {
    private static final int ACTIONS_PER_SECOND = 2;
    private int ticksUntilAction = 0;
    private Graph graph;
    private final Map<Types, List<Node>> networkEndpoints = Map.of(
            ACTIVE_SUPPLIER_PIPE, new ArrayList<>(),
            PASSIVE_PROVIDER_PIPE, new ArrayList<>(),
            REQUESTER_PIPE, new ArrayList<>(),
            STORAGE_PIPE, new ArrayList<>()
    );

    /************************/

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

    public Graph getGraph() {
        if (this.graph == null) createNetworkModel();

        return this.graph;
    }

    public Map<Types, List<Node>> getNetworkEndpoints() {
        return this.networkEndpoints;
    }

    private void findEndpoints() {
        for (Node node : getGraph().getNodes()) {
            Types type = node.getTypeInWorld();
            if (type == null) continue;
            switch (type) {
                case ACTIVE_SUPPLIER_PIPE -> networkEndpoints.get(ACTIVE_SUPPLIER_PIPE).add(node);
                case PASSIVE_PROVIDER_PIPE -> networkEndpoints.get(PASSIVE_PROVIDER_PIPE).add(node);
                case REQUESTER_PIPE -> networkEndpoints.get(REQUESTER_PIPE).add(node);
                case STORAGE_PIPE -> networkEndpoints.get(STORAGE_PIPE).add(node);
            }
        }
    }

    private void createNetworkModel() {
        this.graph = Graph.create(this.getPos(), this.getWorld());

        findEndpoints();
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

    /************************/

    public static void tick(World world, BlockPos pos, BlockState state, NetworkControllerBlockEntity controllerBE) {
        if (world.isClient || !controllerBE.shouldDoAction()) return;

        //for debugging
        /* usage: - calculate path from source node
         *        - call getShortestPath() on target node to get the path from source as a linked list
         *        - call clearPath() on the graph to reset it*/
        boolean t = false;
        if (t) {
            var graph = controllerBE.getGraph();
            var endpoints = controllerBE.getNetworkEndpoints();
            for (var node : endpoints.get(ACTIVE_SUPPLIER_PIPE)) {
                PathFinder.calculatePathsFromNode(node);
                controllerBE.getGraph().clearPath();
            }

            for (var node : endpoints.get(PASSIVE_PROVIDER_PIPE)) {
                PathFinder.calculatePathsFromNode(node);
                controllerBE.getGraph().clearPath();
            }

            for (var node : endpoints.get(REQUESTER_PIPE)) {
                PathFinder.calculatePathsFromNode(node);
                controllerBE.getGraph().clearPath();
            }

            for (var node : endpoints.get(STORAGE_PIPE)) {
                PathFinder.calculatePathsFromNode(node);
                controllerBE.getGraph().clearPath();
            }
        }
//        be.markDirty();
    }
}

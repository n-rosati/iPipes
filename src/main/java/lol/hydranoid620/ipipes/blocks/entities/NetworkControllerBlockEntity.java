package lol.hydranoid620.ipipes.blocks.entities;

import lol.hydranoid620.ipipes.iPipes;
import lol.hydranoid620.ipipes.iPipes.Types;
import lol.hydranoid620.ipipes.routing.Graph;
import lol.hydranoid620.ipipes.routing.Node;
import lol.hydranoid620.ipipes.routing.PathFinder;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static lol.hydranoid620.ipipes.iPipes.Types.*;

public class NetworkControllerBlockEntity extends BlockEntity {
    private static final int ACTIONS_PER_SECOND = 2;
    private int ticksUntilAction = 20;
    private Graph graph;
    private final Map<Types, List<Node>> networkEndpoints = Map.of(
            ACTIVE_SUPPLIER_PIPE, new ArrayList<>(),
            PASSIVE_SUPPLIER_PIPE, new ArrayList<>(),
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
                case PASSIVE_SUPPLIER_PIPE -> networkEndpoints.get(PASSIVE_SUPPLIER_PIPE).add(node);
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

    @SuppressWarnings("unused")
    public static void tick(World world, BlockPos pos, BlockState state, NetworkControllerBlockEntity controllerBE) {
        if (world.isClient || !controllerBE.shouldDoAction()) return;

        var graph = controllerBE.getGraph();
        controllerBE.createNetworkModel();
        var endpoints = controllerBE.getNetworkEndpoints();

        controllerBE.assignPaths(world, endpoints.get(ACTIVE_SUPPLIER_PIPE), iPipes.ACTIVE_SUPPLIER_PIPE_BLOCK_ENTITY, endpoints.get(REQUESTER_PIPE));
        controllerBE.assignPaths(world, endpoints.get(ACTIVE_SUPPLIER_PIPE), iPipes.ACTIVE_SUPPLIER_PIPE_BLOCK_ENTITY, endpoints.get(STORAGE_PIPE));
        controllerBE.assignPaths(world, endpoints.get(PASSIVE_SUPPLIER_PIPE), iPipes.PASSIVE_SUPPLIER_PIPE_BLOCK_ENTITY, endpoints.get(REQUESTER_PIPE));
        controllerBE.assignPaths(world, endpoints.get(STORAGE_PIPE), iPipes.STORAGE_PIPE_BLOCK_ENTITY, endpoints.get(REQUESTER_PIPE));

//        be.markDirty();
    }

    private <T extends BlockEntity> void assignPaths(World world, List<Node> sourceNodes, BlockEntityType<T> sourceBEType, List<Node> targetNodes) {
        for (var sourceNode : sourceNodes) {
            PathFinder.calculatePathsFromNode(sourceNode);
            world.getBlockEntity(sourceNode.getBlockPos(), sourceBEType).ifPresent(be -> {
                if (!(be instanceof IPipeNetworkEndpoint)) return;
                var destinations = ((IPipeNetworkEndpoint) be).destinations;
                destinations.clear();
                for (var targetNode : targetNodes) {
                    var pathToAdd = targetNode.copyShortestPath();
                    pathToAdd.addLast(targetNode);
                    destinations.add(pathToAdd);
                    iPipes.LOGGER.info(destinations.toString());
                }
            });

            getGraph().clearAllPaths();
        }
    }
}

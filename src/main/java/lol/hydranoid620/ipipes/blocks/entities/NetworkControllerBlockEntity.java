package lol.hydranoid620.ipipes.blocks.entities;

import lol.hydranoid620.ipipes.iPipes;
import lol.hydranoid620.ipipes.iPipes.Types;
import lol.hydranoid620.ipipes.routing.Graph;
import lol.hydranoid620.ipipes.routing.Node;
import lol.hydranoid620.ipipes.routing.PathFinder;
import lombok.Getter;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static lol.hydranoid620.ipipes.iPipes.Types.*;

public class NetworkControllerBlockEntity extends BlockEntity {
    private static final int ACTIONS_PER_SECOND = 1;
    private int ticksUntilAction = 20;
    private Graph graph;
    @Getter
    private final Map<Types, List<Node>> networkEndpoints = Map.of(
            ACTIVE_SUPPLIER_PIPE, new ArrayList<>(),
            PASSIVE_SUPPLIER_PIPE, new ArrayList<>(),
            REQUESTER_PIPE, new ArrayList<>(),
            STORAGE_PIPE, new ArrayList<>()
    );

    @Getter
    private final Map<Node, List<Node>> connections = new HashMap<>();

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

    private boolean shouldDoAction() {
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

        // Clear old stuff
        controllerBE.networkEndpoints.forEach((x, y) -> y.clear());

        // Get all the current endpoints in the graph
        controllerBE.createNetworkModel();
        var graph = controllerBE.getGraph();
        var endpoints = controllerBE.getNetworkEndpoints();

        // Map Active Suppliers
        for (var node : endpoints.get(ACTIVE_SUPPLIER_PIPE)) {
            PathFinder.calculatePathsFromNode(node);
            var destinations = world.getBlockEntity(node.getBlockPos(), iPipes.ACTIVE_SUPPLIER_PIPE_BLOCK_ENTITY).get().getDestinations();
            destinations.clear();
            for (var targetNode : endpoints.get(REQUESTER_PIPE)) {
                var pathToAdd = targetNode.copyShortestPath();
                pathToAdd.addLast(targetNode);
                destinations.add(pathToAdd);
            }

            for (var targetNode : endpoints.get(STORAGE_PIPE)) {
                var pathToAdd = targetNode.copyShortestPath();
                pathToAdd.addLast(targetNode);
                destinations.add(pathToAdd);
            }

            graph.clearAllPaths();
        }

        // Map Passive Suppliers
        for (var node : endpoints.get(PASSIVE_SUPPLIER_PIPE)) {
            PathFinder.calculatePathsFromNode(node);
            var destinations =  world.getBlockEntity(node.getBlockPos(), iPipes.PASSIVE_SUPPLIER_PIPE_BLOCK_ENTITY).get().getDestinations();
            destinations.clear();
            for (var targetNode : endpoints.get(REQUESTER_PIPE)) {
                var pathToAdd = targetNode.copyShortestPath();
                pathToAdd.addLast(targetNode);
                destinations.add(pathToAdd);
            }

            graph.clearAllPaths();
        }

        // Map Storage pipes
        for (var node : endpoints.get(STORAGE_PIPE)) {
            PathFinder.calculatePathsFromNode(node);
            var destinations = world.getBlockEntity(node.getBlockPos(), iPipes.STORAGE_PIPE_BLOCK_ENTITY).get().getDestinations();
            destinations.clear();
            for (var targetNode : endpoints.get(REQUESTER_PIPE)) {
                var pathToAdd = targetNode.copyShortestPath();
                pathToAdd.addLast(targetNode);
                destinations.add(pathToAdd);
            }

            graph.clearAllPaths();
        }
//        be.markDirty();
    }
}

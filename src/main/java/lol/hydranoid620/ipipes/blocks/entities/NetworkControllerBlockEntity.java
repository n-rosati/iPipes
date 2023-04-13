package lol.hydranoid620.ipipes.blocks.entities;

import lol.hydranoid620.ipipes.iPipes;
import lol.hydranoid620.ipipes.routing.Edge;
import lol.hydranoid620.ipipes.routing.GraphCreator;
import lol.hydranoid620.ipipes.routing.Node;
import lol.hydranoid620.ipipes.routing.PathFinder;
import lombok.Getter;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.Set;

public class NetworkControllerBlockEntity extends BlockEntity {
    private int ticksUntilAction = 10;
    @Getter
    private Set<Node> nodes;
    @Getter
    private Set<Edge> edges;

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

    public boolean shouldDoAction() {
        boolean retVal = false;
        if (this.ticksUntilAction <= 0) {
            this.ticksUntilAction = 10;
            retVal = true;
        } else {
            this.ticksUntilAction--;
        }

        this.markDirty();
        return retVal;
    }

    private void findNodes(BlockPos pos, World world) {
        this.nodes = GraphCreator.findAllNodesInNetwork(pos, world);
    }
    private void findEdges(Set<Node> nodes) {
        this.edges = GraphCreator.findEdges(nodes);
    }

    public static void tick(World world, BlockPos pos, BlockState state, NetworkControllerBlockEntity be) {
        if (world.isClient || !be.shouldDoAction()) return;

        // gets all nodes in the pipe network connected to the network controller
        if (be.getNodes() == null || be.getNodes().isEmpty()) be.findNodes(pos, world);
        if (be.getEdges() == null || be.getEdges().isEmpty()) be.findEdges(be.getNodes());


        /*TODO:
                - find all requesters
                - find all active providers
                - find all passive providers
                - find all storages
                - try to satisfy requesters with active providers
                - try to satisfy requesters with passive providers
                - excess active provider capacity goes to storage*/
        Optional<Node> firstRequester = be.getNodes().stream().filter(x -> x.getPipeType() == iPipes.Types.ACTIVE_SUPPLIER_PIPE).findFirst();
        Optional<Node> firstStorage = be.getNodes().stream().filter(x -> x.getPipeType() == iPipes.Types.STORAGE_PIPE).findFirst();

        if (firstRequester.isPresent() && firstStorage.isPresent()) {
            PathFinder pathFinder = new PathFinder(world, be.getNodes(), be.getEdges());
            iPipes.LOGGER.info(pathFinder.findPath(firstRequester.get(), firstStorage.get()).toString());
        }



//        be.markDirty();
    }


}

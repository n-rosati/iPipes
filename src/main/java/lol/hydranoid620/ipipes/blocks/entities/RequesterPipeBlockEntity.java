package lol.hydranoid620.ipipes.blocks.entities;

import lol.hydranoid620.ipipes.iPipes;
import lol.hydranoid620.ipipes.routing.Node;
import lombok.Getter;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.LinkedList;

public class RequesterPipeBlockEntity extends BlockEntity implements IPipeNetworkEndpoint {
    @Getter
    private ArrayList<LinkedList<Node>> destinations = new ArrayList<>();

    public RequesterPipeBlockEntity(BlockPos pos, BlockState state) {
        super(iPipes.REQUESTER_PIPE_BLOCK_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, RequesterPipeBlockEntity be) {

    }


}

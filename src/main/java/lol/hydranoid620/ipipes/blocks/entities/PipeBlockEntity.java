package lol.hydranoid620.ipipes.blocks.entities;

import lol.hydranoid620.ipipes.iPipes;
import lol.hydranoid620.ipipes.routing.Graph;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PipeBlockEntity extends BlockEntity {
    // remember to call markDirty() when editing data

    @Getter @Setter
    private Graph graph;

    public PipeBlockEntity(BlockPos pos, BlockState state) {
        super(iPipes.PIPE_BLOCK_ENTITY, pos, state);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        // tag.put...
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        // val = tag.get...
    }

    public static void tick(World world, BlockPos pos, BlockState state, PipeBlockEntity be) {

    }
}

package lol.hydranoid620.ipipes.blocks.entities;

import lol.hydranoid620.ipipes.iPipes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NetworkControllerBlockEntity extends BlockEntity {
    // remember to call markDirty() when editing data
    private int ticksUntilAction = 10;

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
        if (this.ticksUntilAction == 0) {
            this.ticksUntilAction = 10;
            retVal = true;
        } else {
            this.ticksUntilAction--;
        }

        this.markDirty();
        return retVal;
    }

    public static void tick(World world, BlockPos pos, BlockState state, NetworkControllerBlockEntity be) {
        if (world.isClient) return;

        if (be.shouldDoAction()) {
            //do things and stuff
            iPipes.LOGGER.info("thing happened at " + pos);
            be.markDirty();
        }
    }
}

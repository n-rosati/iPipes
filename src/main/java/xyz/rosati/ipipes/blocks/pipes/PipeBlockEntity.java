package xyz.rosati.ipipes.blocks.pipes;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import xyz.rosati.ipipes.iPipes;

public class PipeBlockEntity extends BlockEntity {
    private int count = 0;

    public PipeBlockEntity(BlockPos pos, BlockState state) {
        super(iPipes.PIPE_BLOCK_ENTITY, pos, state);
    }

    public static void tick (World world, BlockPos pos, BlockState state, PipeBlockEntity be) {

    }

    @Override
    public void readNbt(NbtCompound nbt) {
        count = nbt.getInt("count");
        super.readNbt(nbt);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.putInt("count", count);
        super.writeNbt(nbt);
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    public void increment(){
        count++;
    }

    public int getCount() {
        return count;
    }
}

package lol.hydranoid620.ipipes.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.Util;
import net.minecraft.util.math.Direction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IPipeConnectable {
    BooleanProperty NORTH = BooleanProperty.of("north");
    BooleanProperty SOUTH = BooleanProperty.of("south");
    BooleanProperty EAST = BooleanProperty.of("east");
    BooleanProperty WEST = BooleanProperty.of("west");
    BooleanProperty UP = BooleanProperty.of("up");
    BooleanProperty DOWN = BooleanProperty.of("down");
    Map<Direction, BooleanProperty> PROP_MAP = Util.make(new HashMap<>(), map -> {
        map.put(Direction.NORTH, NORTH);
        map.put(Direction.SOUTH, SOUTH);
        map.put(Direction.EAST, EAST);
        map.put(Direction.WEST, WEST);
        map.put(Direction.UP, UP);
        map.put(Direction.DOWN, DOWN);
    });

    static List<Direction> getConnectedDirections(BlockState state) {
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

package lol.hydranoid620.ipipes.blocks;

import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.Util;
import net.minecraft.util.math.Direction;

import java.util.HashMap;
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
}

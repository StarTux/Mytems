package com.cavetale.mytems.item.wrench;

import com.cavetale.core.font.VanillaItems;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.item.font.Glyph;
import java.util.List;
import net.kyori.adventure.text.Component;
import org.bukkit.Axis;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Lightable;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.block.data.Orientable;
import org.bukkit.block.data.Rail;
import org.bukkit.block.data.Rotatable;
import org.bukkit.block.data.type.Chest;
import org.bukkit.block.data.type.Door;
import org.bukkit.block.data.type.Fence;
import org.bukkit.block.data.type.Furnace;
import org.bukkit.block.data.type.GlassPane;
import org.bukkit.block.data.type.Piston;
import org.bukkit.block.data.type.PistonHead;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.block.data.type.Wall;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import static com.cavetale.core.util.CamelCase.toCamelCase;
import static net.kyori.adventure.text.Component.join;
import static net.kyori.adventure.text.Component.space;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.JoinConfiguration.noSeparators;
import static net.kyori.adventure.text.JoinConfiguration.separator;
import static net.kyori.adventure.text.format.NamedTextColor.*;

public enum WrenchEdit {
    /** Facing one of four cardinal directions. */
    DIRECTION {
        @Override public Component getDisplayName() {
            return join(noSeparators(), VanillaItems.COMPASS, text("Direction", BLUE));
        }

        @Override public boolean canEdit(Player player, Block block, BlockData blockData) {
            if (!(blockData instanceof Directional)) return false;
            if (blockData instanceof Piston piston && piston.isExtended()) return false;
            if (blockData instanceof PistonHead) return false;
            if (blockData instanceof Door) return false;
            if (blockData instanceof Chest chest && chest.getType() != Chest.Type.SINGLE) return false;
            return true;
        }

        @Override public Component edit(Player player, Block block, BlockData blockData, PlayerInteractEvent event) {
            if (!(blockData instanceof Directional directional)) return null;
            final List<BlockFace> faces = List.copyOf(directional.getFaces());
            assert !faces.isEmpty();
            final BlockFace face = directional.getFacing();
            final int index = faces.indexOf(face);
            final int newIndex = index >= 0 && index < faces.size() - 1
                ? index + 1
                : 0;
            final BlockFace newFace = faces.get(newIndex);
            directional.setFacing(newFace);
            return blockFaceText(newFace);
        }
    },
    /** Facing one of 16 BlockFace values. */
    ROTATION {
        @Override public Component getDisplayName() {
            return join(noSeparators(), VanillaItems.COMPASS, text("Rotation", BLUE));
        }

        @Override public boolean canEdit(Player player, Block block, BlockData blockData) {
            return blockData instanceof Rotatable;
        }

        @Override public Component edit(Player player, Block block, BlockData blockData, PlayerInteractEvent event) {
            if (!(blockData instanceof Rotatable rotatable)) return null;
            List<BlockFace> faces = ROTATIONS;
            final BlockFace face = rotatable.getRotation();
            final int index = faces.indexOf(face);
            final int newIndex = index >= 0 && index < faces.size() - 1
                ? index + 1
                : 0;
            final BlockFace newFace = faces.get(newIndex);
            rotatable.setRotation(newFace);
            return blockFaceText(newFace);
        }
    },
    /** Aligned with one of 3 axes: x, y, z. */
    ORIENTATION {
        @Override public Component getDisplayName() {
            return join(noSeparators(), VanillaItems.COMPASS, text("Orientation", BLUE));
        }

        @Override public boolean canEdit(Player player, Block block, BlockData blockData) {
            return blockData instanceof Orientable;
        }

        @Override public Component edit(Player player, Block block, BlockData blockData, PlayerInteractEvent event) {
            if (!(blockData instanceof Orientable orientable)) return null;
            final List<Axis> axes = List.copyOf(orientable.getAxes());
            assert !axes.isEmpty();
            final Axis axis = orientable.getAxis();
            final int index = axes.indexOf(axis);
            final int newIndex = index >= 0 && index < axes.size() - 1
                ? index + 1
                : 0;
            final Axis newAxis = axes.get(newIndex);
            orientable.setAxis(newAxis);
            return Glyph.toComponent(newAxis.name().toLowerCase());
        }
    },
    SLAB_HALF {
        @Override public Component getDisplayName() {
            return join(noSeparators(), Mytems.HALF_HEART, text("Half", BLUE));
        }

        @Override public boolean canEdit(Player player, Block block, BlockData blockData) {
            return blockData instanceof Slab slab && slab.getType() != Slab.Type.DOUBLE;
        }

        @Override public Component edit(Player player, Block block, BlockData blockData, PlayerInteractEvent event) {
            if (!(blockData instanceof Slab slab)) return null;
            Slab.Type type = slab.getType();
            if (type == Slab.Type.DOUBLE) return null;
            Slab.Type newType = type == Slab.Type.BOTTOM ? Slab.Type.TOP : Slab.Type.BOTTOM;
            slab.setType(newType);
            return join(noSeparators(),
                        (newType == Slab.Type.TOP ? Mytems.ARROW_UP : Mytems.ARROW_DOWN),
                        text(toCamelCase(" ", newType)));
        }
    },
    STAIR_HALF {
        @Override public Component getDisplayName() {
            return join(noSeparators(), Mytems.HALF_HEART, text("Half", BLUE));
        }

        @Override public boolean canEdit(Player player, Block block, BlockData blockData) {
            return blockData instanceof Stairs;
        }

        @Override public Component edit(Player player, Block block, BlockData blockData, PlayerInteractEvent event) {
            if (!(blockData instanceof Stairs bisected)) return null;
            Bisected.Half half = bisected.getHalf();
            Bisected.Half newHalf = half == Bisected.Half.BOTTOM ? Bisected.Half.TOP : Bisected.Half.BOTTOM;
            bisected.setHalf(newHalf);
            return join(noSeparators(),
                        newHalf == Bisected.Half.TOP ? Mytems.ARROW_UP : Mytems.ARROW_DOWN,
                        text(toCamelCase(" ", newHalf)));
        }
    },
    STAIR_SHAPE {
        @Override public Component getDisplayName() {
            return join(noSeparators(), Mytems.EMPTY_HEART, text("Shape", BLUE));
        }

        @Override public boolean canEdit(Player player, Block block, BlockData blockData) {
            return blockData instanceof Stairs;
        }

        @Override public Component edit(Player player, Block block, BlockData blockData, PlayerInteractEvent event) {
            if (!(blockData instanceof Stairs stairs)) return null;
            List<Stairs.Shape> shapeList = List.of(Stairs.Shape.values());
            final Stairs.Shape shape = stairs.getShape();
            final int index = shapeList.indexOf(shape);
            final int newIndex = index >= 0 && index < shapeList.size() - 1
                ? index + 1
                : 0;
            final Stairs.Shape newShape = shapeList.get(newIndex);
            stairs.setShape(newShape);
            return text(toCamelCase(" ", newShape));
        }
    },
    RAIL_SHAPE {
        @Override public Component getDisplayName() {
            return join(noSeparators(), VanillaItems.RAIL, text("Shape", BLUE));
        }

        @Override public boolean canEdit(Player player, Block block, BlockData blockData) {
            return blockData instanceof Rail;
        }

        @Override public Component edit(Player player, Block block, BlockData blockData, PlayerInteractEvent event) {
            if (!(blockData instanceof Rail rail)) return null;
            final Rail.Shape shape = rail.getShape();
            List<Rail.Shape> shapeList = List.copyOf(rail.getShapes());
            final int index = shapeList.indexOf(shape);
            final int newIndex = index >= 0 && index < shapeList.size() - 1
                ? index + 1
                : 0;
            final Rail.Shape newShape = shapeList.get(newIndex);
            rail.setShape(newShape);
            return text(toCamelCase(" ", newShape));
        }
    },
    LIGHT {
        @Override public Component getDisplayName() {
            return join(noSeparators(), VanillaItems.CAMPFIRE, text("Light", BLUE));
        }

        @Override public boolean canEdit(Player player, Block block, BlockData blockData) {
            if (!(blockData instanceof Lightable)) return false;
            if (blockData instanceof Furnace) return true;
            switch (blockData.getMaterial()) {
            case DEEPSLATE_REDSTONE_ORE:
            case FURNACE:
            case REDSTONE_LAMP:
            case REDSTONE_ORE:
                return true;
            default: return false;
            }
        }

        @Override public Component edit(Player player, Block block, BlockData blockData, PlayerInteractEvent event) {
            if (!(blockData instanceof Lightable lightable)) return null;
            boolean newLit = !lightable.isLit();
            lightable.setLit(newLit);
            return booleanText(newLit);
        }
    },
    WALL_UP {
        @Override public Component getDisplayName() {
            return join(noSeparators(), Mytems.ARROW_UP, text("Height", BLUE));
        }

        /**
         * Walls can turn partially or completely invisible if set to
         * low while L-shaped or standing alone.
         */
        private static boolean isLong(Wall wall) {
            return (wall.getHeight(BlockFace.NORTH) != Wall.Height.NONE
                    && wall.getHeight(BlockFace.SOUTH) != Wall.Height.NONE)
                || (wall.getHeight(BlockFace.EAST) != Wall.Height.NONE
                    && wall.getHeight(BlockFace.WEST) != Wall.Height.NONE);
        }

        @Override public boolean canEdit(Player player, Block block, BlockData blockData) {
            return blockData instanceof Wall wall && isLong(wall);
        }

        @Override public Component edit(Player player, Block block, BlockData blockData, PlayerInteractEvent event) {
            if (!(blockData instanceof Wall wall)) return null;
            if (!isLong(wall)) return null;
            boolean newUp = !wall.isUp();
            wall.setUp(newUp);
            return newUp
                ? join(noSeparators(), Mytems.ON, text("Yes", GREEN))
                : join(noSeparators(), Mytems.OFF, text("No", GRAY));
        }
    },
    CONNECT {
        @Override public Component getDisplayName() {
            return join(noSeparators(), Mytems.MAGNET, text("Connection", BLUE));
        }

        @Override public boolean canEdit(Player player, Block block, BlockData blockData) {
            return blockData instanceof Fence
                || blockData instanceof GlassPane;
        }

        @Override public Component edit(Player player, Block block, BlockData blockData, PlayerInteractEvent event) {
            if (!(blockData instanceof MultipleFacing multipleFacing)) return null;
            Location point = event.getInteractionPoint();
            final BlockFace face;
            if (point != null) {
                final double dx = point.getX() - (double) block.getX() - 0.5;
                final double dz = point.getZ() - (double) block.getZ() - 0.5;
                if (Math.abs(dx) > Math.abs(dz)) {
                    face = dx < 0 ? BlockFace.WEST : BlockFace.EAST;
                } else {
                    face = dz < 0 ? BlockFace.NORTH : BlockFace.SOUTH;
                }
            } else {
                face = event.getBlockFace();
            }
            if (!multipleFacing.getAllowedFaces().contains(face)) return null;
            final boolean newValue = !multipleFacing.hasFace(face);
            multipleFacing.setFace(face, newValue);
            return join(separator(space()), blockFaceText(face), booleanText(newValue));
        }
    },
    ;

    private static final List<BlockFace> ROTATIONS = List.of(BlockFace.NORTH,
                                                             BlockFace.NORTH_NORTH_EAST,
                                                             BlockFace.NORTH_EAST,
                                                             BlockFace.EAST_NORTH_EAST,
                                                             BlockFace.EAST,
                                                             BlockFace.EAST_SOUTH_EAST,
                                                             BlockFace.SOUTH_EAST,
                                                             BlockFace.SOUTH_SOUTH_EAST,
                                                             BlockFace.SOUTH,
                                                             BlockFace.SOUTH_SOUTH_WEST,
                                                             BlockFace.SOUTH_WEST,
                                                             BlockFace.WEST_SOUTH_WEST,
                                                             BlockFace.WEST,
                                                             BlockFace.WEST_NORTH_WEST,
                                                             BlockFace.NORTH_WEST,
                                                             BlockFace.NORTH_NORTH_WEST);

    abstract Component getDisplayName();

    abstract boolean canEdit(Player player, Block block, BlockData blockData);

    abstract Component edit(Player player, Block block, BlockData blockData, PlayerInteractEvent event);

    private static Component blockFaceText(BlockFace face) {
        Component text = text(toCamelCase(" ", face));
        switch (face) {
        case NORTH: case UP: return join(noSeparators(), Mytems.ARROW_UP, text);
        case SOUTH: case DOWN: return join(noSeparators(), Mytems.ARROW_DOWN, text);
        case EAST: return join(noSeparators(), Mytems.ARROW_RIGHT, text);
        case WEST: return join(noSeparators(), Mytems.ARROW_LEFT, text);
        default: return join(noSeparators(), Mytems.REDO, text);
        }
    }

    private static Component booleanText(boolean value) {
        return value
            ? join(noSeparators(), Mytems.ON, text("On", GREEN))
            : join(noSeparators(), Mytems.OFF, text("Off", GRAY));
    }
}

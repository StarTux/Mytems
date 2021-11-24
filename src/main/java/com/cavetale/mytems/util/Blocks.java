package com.cavetale.mytems.util;

import com.cavetale.mytems.Mytems;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;

public final class Blocks {
    private Blocks() { }

    public static ArmorStand place(Mytems mytems, Block block, BlockFace facing) {
        return place(mytems, block.getLocation().add(0.5, 0.0, 0.5), facing);
    }

    public static ArmorStand place(Mytems mytems, Location location, BlockFace facing) {
        location = location.clone().setDirection(facing.getDirection());
        return location.getWorld().spawn(location, ArmorStand.class, as -> {
                as.setPersistent(false);
                as.setVisible(false);
                as.setSmall(true);
                as.setMarker(true);
                as.setGravity(false);
                as.setArms(false);
                as.setBasePlate(false);
                as.setCanTick(false);
                as.getEquipment().setHelmet(mytems.createItemStack());
            });
    }
}

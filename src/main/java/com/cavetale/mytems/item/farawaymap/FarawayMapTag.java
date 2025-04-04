package com.cavetale.mytems.item.farawaymap;

import com.cavetale.core.connect.Connect;
import com.cavetale.mytems.MytemTag;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsPlugin;
import com.cavetale.worldmarker.util.Tags;
import java.util.Objects;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;
import org.bukkit.persistence.PersistentDataContainer;

@Data @EqualsAndHashCode(callSuper = true)
public final class FarawayMapTag extends MytemTag {
    public static final String SERVER = "server";
    public static final String MAP_ID = "mapId";
    public static final String COLOR = "color";
    protected String server;
    protected Integer mapId;
    protected Integer color;

    public boolean isEmpty() {
        return super.isEmpty()
            && server == null
            && mapId == null
            && color == null;
    }

    @Override
    public void load(Mytems mytems, ItemStack itemStack) {
        super.load(mytems, itemStack);
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) return;
        PersistentDataContainer tag = meta.getPersistentDataContainer();
        if (tag == null) return;
        this.server = Tags.getString(tag, MytemsPlugin.namespacedKey(SERVER));
        this.mapId = Tags.getInt(tag, MytemsPlugin.namespacedKey(MAP_ID));
        this.color = Tags.getInt(tag, MytemsPlugin.namespacedKey(COLOR));
    }

    @Override
    public void store(Mytems mytems, ItemStack itemStack) {
        super.store(mytems, itemStack);
        itemStack.editMeta(meta -> {
                PersistentDataContainer tag = meta.getPersistentDataContainer();
                if (server != null) {
                    Tags.set(tag, MytemsPlugin.namespacedKey(SERVER), server);
                }
                if (mapId != null) {
                    Tags.set(tag, MytemsPlugin.namespacedKey(MAP_ID), mapId);
                }
                if (color != null) {
                    Tags.set(tag, MytemsPlugin.namespacedKey(COLOR), color);
                }
            });
    }

    public boolean isDismissable() {
        return isEmpty();
    }

    public void loadMap(ItemStack itemStack) {
        super.load(Mytems.FARAWAY_MAP, itemStack); // amount
        if (itemStack.getItemMeta() instanceof MapMeta meta) {
            if (meta.hasMapView()) {
                MapView mapView = meta.getMapView();
                this.mapId = mapView != null ? mapView.getId() : null;
            } else {
                this.mapId = null;
            }
            this.server = Connect.get().getServerName();
            this.color = meta.hasColor() ? meta.getColor().asRGB() : null;
        }
    }

    public void storeMap(ItemStack itemStack) {
        super.store(Mytems.FARAWAY_MAP, itemStack); // amount
        itemStack.editMeta(m -> {
                if (!(m instanceof MapMeta meta)) return;
                if (mapId != null) {
                    @SuppressWarnings("deprecation")
                    MapView mapView = Bukkit.getMap(mapId);
                    if (mapView != null) meta.setMapView(mapView);
                }
                if (color != null) {
                    meta.setColor(Color.fromRGB(color));
                }
            });
    }

    @Override
    public boolean isSimilar(MytemTag other) {
        return super.isSimilar(other)
            && other instanceof FarawayMapTag that
            && Objects.equals(this.server, that.server)
            && Objects.equals(this.mapId, that.mapId)
            && Objects.equals(this.color, that.color);
    }
}
